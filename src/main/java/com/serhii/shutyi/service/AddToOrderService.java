package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;

import java.util.Optional;

public class AddToOrderService {
    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static AddToOrderService INSTANCE = new AddToOrderService();
    }

    public static AddToOrderService getInstance() {
        return AddToOrderService.Holder.INSTANCE;
    }

    public void addGoodToOrder(Order order, int goodId, int orderedQuantity) {
        Good good = getGoodById(goodId);
        good.setQuantity(orderedQuantity);

        order.getGoods().add(good);
    }


    private Good getGoodById(int goodId) {
        Optional<Good> good = Optional.empty();

        try (GoodDAO goodDAO = factory.createGoodDAO(ConnectionPool.getConnection())) {
            good = goodDAO.findById(goodId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return good.get();
    }
}
