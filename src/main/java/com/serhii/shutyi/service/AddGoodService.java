package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;

import java.sql.Connection;

public class AddGoodService {
    DaoFactory factory;
    Connection connection;

    public AddGoodService(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    private static class Holder {
        private static AddGoodService INSTANCE =
                new AddGoodService(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static AddGoodService getInstance(){
        AddGoodService addGoodService = AddGoodService.Holder.INSTANCE;
        addGoodService.connection = ConnectionPool.getConnection();
        return addGoodService;
    }

    public void addGood(Good good) {
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goodDAO.insert(good);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
