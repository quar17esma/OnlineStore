package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.enums.OrderStatus;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrdersService {
    private DaoFactory factory;

    public OrdersService(DaoFactory factory) {
        this.factory = factory;
    }

    private static class Holder {
        private static OrdersService INSTANCE = new OrdersService(DaoFactory.getInstance());
    }

    public static OrdersService getInstance() {
        OrdersService ordersService = OrdersService.Holder.INSTANCE;

        return ordersService;
    }

    public List<Order> getOrdersByClientId(int clientId) {
        List<Order> orders = null;

        Connection connection = ConnectionPool.getConnection();
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

        }

        return orders;
    }

    public boolean payOrder(int orderId) {
        boolean result = false;

        Connection connection = ConnectionPool.getConnection();
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
            e.printStackTrace();
        }

        return result;
    }

    public void sendOrder(Order order) {

        Connection connection = ConnectionPool.getConnection();
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
                    // sorry not enough quantity
                    throw new RuntimeException();
                }
            }

            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addGoodToOrder(Order order, int goodId, int orderedQuantity) {
        Good good = GoodsService.getInstance().getGoodById(goodId);
        good.setQuantity(orderedQuantity);

        order.getGoods().add(good);
    }
}
