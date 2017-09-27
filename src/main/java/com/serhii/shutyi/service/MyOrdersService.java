package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;

import java.sql.Connection;
import java.util.List;

public class MyOrdersService {
    DaoFactory factory;
    Connection connection;

    public MyOrdersService(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    private static class Holder {
        private static MyOrdersService INSTANCE =
                new MyOrdersService(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static MyOrdersService getInstance() {
        return MyOrdersService.Holder.INSTANCE;
    }

    public List<Order> getOrdersByClientId(int clientId) {
        List<Order> orders = null;


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
}
