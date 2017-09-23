package com.serhii.shutyi.model.service;

import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.model.entity.Good;
import com.serhii.shutyi.model.entity.Order;

import java.util.Optional;

public class AddToOrderService {
    DaoFactory factory;

    public AddToOrderService(DaoFactory factory) {
        this.factory = factory;
    }

    private static class Holder {
        private static AddToOrderService INSTANCE = new AddToOrderService(DaoFactory.getInstance());
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

        try (GoodDAO goodDAO = factory.createGoodDAO()) {
            good = goodDAO.findById(goodId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return good.get();
    }
}
