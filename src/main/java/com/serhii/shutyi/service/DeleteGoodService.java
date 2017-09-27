package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;

import java.sql.Connection;

public class DeleteGoodService {
    DaoFactory factory;
    Connection connection;

    private static class Holder {
        private static DeleteGoodService INSTANCE = new DeleteGoodService();
    }

    public static DeleteGoodService getInstance() {
        DeleteGoodService deleteGoodService = DeleteGoodService.Holder.INSTANCE;
        deleteGoodService.connection = ConnectionPool.getConnection();
        return deleteGoodService;
    }

    public void deleteGoodById(int goodId) {
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goodDAO.delete(goodId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
