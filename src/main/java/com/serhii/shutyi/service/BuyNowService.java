package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;

import java.sql.Connection;

public class BuyNowService {
    DaoFactory factory;
    Connection connection;

    public BuyNowService(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    private static class Holder {
        private static BuyNowService INSTANCE =
                new BuyNowService(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static BuyNowService getInstance() {
        BuyNowService buyNowService = BuyNowService.Holder.INSTANCE;
        buyNowService.connection = ConnectionPool.getConnection();
        return buyNowService;
    }

    public Good getGoodById(int goodId) {
        Good good = null;
        try(GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            good = goodDAO.findById(goodId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return good;
    }
}
