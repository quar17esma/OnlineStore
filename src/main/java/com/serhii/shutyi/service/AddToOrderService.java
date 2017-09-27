package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;

import java.sql.Connection;
import java.util.Optional;

public class AddToOrderService {
    DaoFactory factory;
    Connection connection;

    public AddToOrderService(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    private static class Holder {
        private static AddToOrderService INSTANCE =
                new AddToOrderService(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static AddToOrderService getInstance() {
        AddToOrderService addToOrderService = AddToOrderService.Holder.INSTANCE;
        addToOrderService.connection = ConnectionPool.getConnection();
        return addToOrderService;
    }

    public void addGoodToOrder(Order order, int goodId, int orderedQuantity) {
        Good good = getGoodById(goodId);
        good.setQuantity(orderedQuantity);

        order.getGoods().add(good);
    }


    private Good getGoodById(int goodId) {
        Optional<Good> good = Optional.empty();

        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            good = goodDAO.findById(goodId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return good.get();
    }
}
