package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;

import java.sql.Connection;
import java.time.LocalDateTime;

public class SendOrderService {
    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static SendOrderService INSTANCE = new SendOrderService();
    }

    public static SendOrderService getInstance() {
        return SendOrderService.Holder.INSTANCE;
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
}
