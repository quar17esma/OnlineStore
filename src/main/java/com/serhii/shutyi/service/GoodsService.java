package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;

import java.sql.Connection;
import java.util.List;

public class GoodsService {
    DaoFactory factory;
    Connection connection;

    public GoodsService(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }



    private static class Holder {
        private static GoodsService INSTANCE =
                new GoodsService(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static GoodsService getInstance() {
        GoodsService goodsService = GoodsService.Holder.INSTANCE;
        goodsService.connection = ConnectionPool.getConnection();
        return goodsService;
    }

    public List<Good> getAllGoods() {
        List<Good> goods = null;

        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goods = goodDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
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

    public void deleteGoodById(int goodId) {
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goodDAO.delete(goodId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addGood(Good good) {
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goodDAO.insert(good);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGood(Good good) {
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goodDAO.update(good);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
