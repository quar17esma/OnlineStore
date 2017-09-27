package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.enums.OrderStatus;

import java.sql.Connection;
import java.util.Optional;

public class PayOrderService {
    DaoFactory factory;
    Connection connection;

    public PayOrderService(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    private static class Holder {
        private static PayOrderService INSTANCE =
                new PayOrderService(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static PayOrderService getInstance() {
        return PayOrderService.Holder.INSTANCE;
    }

    public boolean payOrder(int orderId) {
        boolean result = false;

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
}
