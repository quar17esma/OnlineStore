package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;

public class BuyNowService {
    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static BuyNowService INSTANCE = new BuyNowService();
    }

    public static BuyNowService getInstance() {
        return BuyNowService.Holder.INSTANCE;
    }

    public Good getGoodById(int goodId) {
        Good good = null;
        try(GoodDAO goodDAO = factory.createGoodDAO()) {
            good = goodDAO.findById(goodId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return good;
    }
}
