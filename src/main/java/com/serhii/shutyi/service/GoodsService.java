package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;

import java.sql.Connection;
import java.util.List;

public class GoodsService {
    private DaoFactory factory;
    private ConnectionPool connectionPool;

    public GoodsService(DaoFactory factory, ConnectionPool connectionPool) {
        this.factory = factory;
        this.connectionPool = connectionPool;
    }

    private static class Holder {
        private static GoodsService INSTANCE = new GoodsService(DaoFactory.getInstance(), ConnectionPool.getInstance());
    }

    public static GoodsService getInstance() {
        return GoodsService.Holder.INSTANCE;
    }

    public List<Good> getAllGoods() {
        List<Good> goods = null;

        Connection connection = connectionPool.getConnection();
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goods = goodDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
    }

    public Good getGoodById(int goodId) {
        Good good = null;

        Connection connection = connectionPool.getConnection();
        try(GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            good = goodDAO.findById(goodId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return good;
    }

    public void deleteGoodById(int goodId) {
        Connection connection = connectionPool.getConnection();
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goodDAO.delete(goodId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addGood(Good good) {
        Connection connection = connectionPool.getConnection();
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goodDAO.insert(good);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGood(Good good) {
        Connection connection = connectionPool.getConnection();
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goodDAO.update(good);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
