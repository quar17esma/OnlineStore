package com.serhii.shutyi.model.service;

import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.model.entity.Good;

public class AddGoodService {

//    DaoFactory factory = DaoFactory.getInstance();
    DaoFactory factory;

    public AddGoodService(DaoFactory factory) {
        this.factory = factory;
    }

    private static class Holder {
        private static AddGoodService INSTANCE = new AddGoodService(DaoFactory.getInstance());
    }

    public static AddGoodService getInstance(){
        return AddGoodService.Holder.INSTANCE;
    }

    public void addGood(Good good) {
        try (GoodDAO goodDAO = factory.createGoodDAO()) {
            goodDAO.insert(good);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
