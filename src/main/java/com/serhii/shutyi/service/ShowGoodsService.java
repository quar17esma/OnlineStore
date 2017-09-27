package com.serhii.shutyi.service;

import com.serhii.shutyi.controller.action.impl.ShowGoods;
import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;

import java.sql.Connection;
import java.util.List;

public class ShowGoodsService {
    DaoFactory factory;
    Connection connection;

    public ShowGoodsService(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    private static class Holder {
        private static ShowGoodsService INSTANCE =
                new ShowGoodsService(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static ShowGoodsService getInstance() {
        return ShowGoodsService.Holder.INSTANCE;
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
}
