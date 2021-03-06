package com.serhii.shutyi.service.impl;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.enums.OrderStatus;
import com.serhii.shutyi.exceptions.NotEnoughGoodQuantity;
import com.serhii.shutyi.service.IOrdersService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrdersService extends Service implements IOrdersService {
    private static final Logger LOGGER = Logger.getLogger(OrdersService.class);

    private OrdersService(DaoFactory factory, ConnectionPool connectionPool) {
        super(factory, connectionPool);
    }

    private static class Holder {
        private static OrdersService INSTANCE = new OrdersService(DaoFactory.getInstance(), ConnectionPool.getInstance());
    }

    public static OrdersService getInstance() {
        return OrdersService.Holder.INSTANCE;
    }

    public List<Order> getOrdersByClientId(int clientId) {
        List<Order> orders = null;

        try (Connection connection = connectionPool.getConnection();
             OrderDAO orderDAO = factory.createOrderDAO(connection);
             GoodDAO goodDao = factory.createGoodDAO(connection)) {
            connection.setAutoCommit(false);

            orders = orderDAO.findAllByClientId(clientId);
            for (Order order : orders) {
                List<Good> goods = goodDao.findByOrderId(order.getId());
                order.setGoods(goods);
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            LOGGER.error("Fail to get orders by client id", e);
            throw new RuntimeException(e);
        }

        return orders;
    }

    public boolean payOrder(int orderId) {
        boolean result = false;

        try(Connection connection = connectionPool.getConnection();
            OrderDAO orderDAO = factory.createOrderDAO(connection)) {
            connection.setAutoCommit(false);

            Optional<Order> order = orderDAO.findById(orderId);
            if (order.isPresent()) {
                order.get().setStatus(OrderStatus.PAID);
                orderDAO.update(order.get());
                connection.commit();
                connection.setAutoCommit(true);
                result = true;
            }
        } catch (Exception e) {
            LOGGER.error("Fail to pay order", e);
            throw new RuntimeException(e);
        }

        return result;
    }

    public void sendOrder(Order order) {
        try (Connection connection = connectionPool.getConnection();
             OrderDAO orderDAO = factory.createOrderDAO(connection);
             GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            connection.setAutoCommit(false);

            order.setOrderedAt(LocalDateTime.now());
            orderDAO.insert(order);
            writeDownGoodsByOrder(order, goodDAO);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (NotEnoughGoodQuantity e){
            LOGGER.error("Fail to send order, not enough quantity", e);
            throw new NotEnoughGoodQuantity(e);
        } catch (Exception e) {
            LOGGER.error("Fail to send order", e);
            throw new RuntimeException(e);
        }
    }

    private void writeDownGoodsByOrder(Order order, GoodDAO goodDAO) throws NotEnoughGoodQuantity {
        for (Good orderedGood:order.getGoods()) {

            int orderedGoodId = orderedGood.getId();
            Optional<Good> storedGood = goodDAO.findById(orderedGoodId);
            if (storedGood.isPresent()) {
                writeDownGood(storedGood.get(), orderedGood, goodDAO);
            }
        }
    }

    private void writeDownGood(Good storedGood, Good orderedGood, GoodDAO goodDAO) throws NotEnoughGoodQuantity {
        int difference = storedGood.getQuantity() - orderedGood.getQuantity();
        if (difference >= 0) {
            storedGood.setQuantity(difference);
            goodDAO.update(storedGood);
        } else {
            throw new NotEnoughGoodQuantity("Not enough good quantity", orderedGood);
        }
    }
}
