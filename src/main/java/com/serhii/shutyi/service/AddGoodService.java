package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;

public class AddGoodService {

    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static AddGoodService INSTANCE = new AddGoodService();
    }

    public static AddGoodService getInstance(){
        return AddGoodService.Holder.INSTANCE;
    }

    public void addGood(Good good) {
        try (GoodDAO goodDAO = factory.createGoodDAO(ConnectionPool.getConnection())) {
            goodDAO.insert(good);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
