package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;

import java.sql.Connection;

public class EditGoodService {
    DaoFactory factory;
    Connection connection;

    public EditGoodService(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    private static class Holder {
        private static EditGoodService INSTANCE =
                new EditGoodService(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static EditGoodService getInstance() {
        EditGoodService editGoodService = EditGoodService.Holder.INSTANCE;
        editGoodService.connection = ConnectionPool.getConnection();
        return editGoodService;
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
