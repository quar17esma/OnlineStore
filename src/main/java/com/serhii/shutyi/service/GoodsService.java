package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class GoodsService extends Service {
    private static final Logger LOGGER = Logger.getLogger(GoodsService.class);

    private GoodsService(DaoFactory factory, ConnectionPool connectionPool) {
        super(factory, connectionPool);
    }

    private static class Holder {
        private static GoodsService INSTANCE = new GoodsService(DaoFactory.getInstance(), ConnectionPool.getInstance());
    }

    public static GoodsService getInstance() {
        return GoodsService.Holder.INSTANCE;
    }

    public List<Good> getAllGoods() {
        List<Good> goods = null;

        try (Connection connection = connectionPool.getConnection();
             GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            connection.setAutoCommit(true);
            goods = goodDAO.findAll();
        } catch (Exception e) {
            LOGGER.error("Fail to get all goods", e);
            throw new RuntimeException(e);
        }

        return goods;
    }

    public List<Good> getGoodsByPage(int page, int goodsOnPage) {
        List<Good> goods = null;

        try (Connection connection = connectionPool.getConnection();
             GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            connection.setAutoCommit(true);
            goods = goodDAO.findByPage(page, goodsOnPage);
        } catch (Exception e) {
            LOGGER.error("Fail to get all goods", e);
            throw new RuntimeException(e);
        }

        return goods;
    }

    public int getAllGoodsQuantity() {
        int goodsCounter;
        try (Connection connection = connectionPool.getConnection();
             GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            connection.setAutoCommit(true);
            goodsCounter = goodDAO.countAllGoods();
        } catch (Exception e) {
            LOGGER.error("Fail to get all goods", e);
            throw new RuntimeException(e);
        }
        return goodsCounter;
    }

    public Good getGoodById(int goodId) {
        try(Connection connection = connectionPool.getConnection();
            GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            connection.setAutoCommit(true);
            Optional<Good> good = goodDAO.findById(goodId);
            return good.get();
        } catch (Exception e) {
            LOGGER.error("Fail to find good by id", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteGoodById(int goodId) {
        try (Connection connection = connectionPool.getConnection();
             GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            connection.setAutoCommit(true);
            goodDAO.delete(goodId);
        } catch (Exception e) {
            LOGGER.error("Fail to delete good", e);
            throw new RuntimeException(e);
        }
    }

    public void addGood(Good good) {
        try (Connection connection = connectionPool.getConnection();
             GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            connection.setAutoCommit(true);
            goodDAO.insert(good);
        } catch (Exception e) {
            LOGGER.error("Fail to add good", e);
            throw new RuntimeException(e);
        }
    }

    public void updateGood(Good good) {
        try (Connection connection = connectionPool.getConnection();
             GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            connection.setAutoCommit(true);
            goodDAO.update(good);
        } catch (Exception e) {
            LOGGER.error("Fail to update good", e);
            throw new RuntimeException(e);
        }
    }

    public void addGoodToOrder(Order order, int goodId, int orderedQuantity) {
        Good good = getGoodById(goodId);
        good.setQuantity(orderedQuantity);

        order.getGoods().add(good);
    }
}
