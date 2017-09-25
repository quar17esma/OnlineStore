package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;

public class DeleteGoodService {
    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static DeleteGoodService INSTANCE = new DeleteGoodService();
    }

    public static DeleteGoodService getInstance() {
        return DeleteGoodService.Holder.INSTANCE;
    }

    public void deleteGoodById(int goodId) {
        try (GoodDAO goodDAO = factory.createGoodDAO(ConnectionPool.getConnection())) {
            goodDAO.delete(goodId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
