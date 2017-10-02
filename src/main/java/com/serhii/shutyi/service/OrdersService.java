package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.enums.OrderStatus;
import com.serhii.shutyi.exceptions.NotEnoughGoodQuantity;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrdersService {
    final static Logger logger = Logger.getLogger(OrdersService.class);

    private DaoFactory factory;
    private ConnectionPool connectionPool;

    public OrdersService(DaoFactory factory, ConnectionPool connectionPool) {
        this.factory = factory;
        this.connectionPool = connectionPool;
    }

    private static class Holder {
        private static OrdersService INSTANCE = new OrdersService(DaoFactory.getInstance(), ConnectionPool.getInstance());
    }

    public static OrdersService getInstance() {
        return OrdersService.Holder.INSTANCE;
    }

    public List<Order> getOrdersByClientId(int clientId) {
        List<Order> orders = null;

        Connection connection = connectionPool.getConnection();
        try (OrderDAO orderDAO = factory.createOrderDAO(connection);
             GoodDAO goodDao = factory.createGoodDAO(connection)) {
            connection.setAutoCommit(false);

            orders = orderDAO.findAllByClientId(clientId);
            for (Order order : orders) {
                List<Good> goods = goodDao.findByOrderId(order.getId());
                order.setGoods(goods);
            }

            connection.commit();
        } catch (Exception e) {
            logger.error("Fail to get orders by client id", e);
            throw new RuntimeException(e);
        }

        return orders;
    }

    public boolean payOrder(int orderId) {
        boolean result = false;

        Connection connection = connectionPool.getConnection();
        try(OrderDAO orderDAO = factory.createOrderDAO(connection)) {
            connection.setAutoCommit(false);

            Optional<Order> order = orderDAO.findById(orderId);
            if (order.isPresent()) {
                order.get().setStatus(OrderStatus.PAID);
            }
            orderDAO.update(order.get());

            connection.commit();
            result = true;
        } catch (Exception e) {
            logger.error("Fail to pay order", e);
            throw new RuntimeException(e);
        }

        return result;
    }

    public void sendOrder(Order order) {

        Connection connection = connectionPool.getConnection();
        try (OrderDAO orderDAO = factory.createOrderDAO(connection);
             GoodDAO goodDAO = factory.createGoodDAO(connection)) {

            connection.setAutoCommit(false);

            order.setOrderedAt(LocalDateTime.now());
            orderDAO.insert(order);

            for (Good orderedGood:order.getGoods()) {
                Good storedGood = goodDAO.findById(orderedGood.getId()).get();
                int difference = storedGood.getQuantity() - orderedGood.getQuantity();
                if (difference >= 0) {
                    storedGood.setQuantity(difference);
                    goodDAO.update(storedGood);
                } else {
                    throw new NotEnoughGoodQuantity("Not enough good quantity", orderedGood);
                }
            }

            connection.commit();
        } catch (NotEnoughGoodQuantity e){
            logger.error("Fail to send order, not enough quantity", e);
            throw new NotEnoughGoodQuantity(e);
        } catch (Exception e) {
            logger.error("Fail to send order", e);
            throw new RuntimeException(e);
        }
    }

    public void addGoodToOrder(Order order, int goodId, int orderedQuantity) {
        Good good = GoodsService.getInstance().getGoodById(goodId);
        good.setQuantity(orderedQuantity);

        order.getGoods().add(good);
    }
}
