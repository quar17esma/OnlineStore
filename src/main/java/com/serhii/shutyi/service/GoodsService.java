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

public class GoodsService {
    final static Logger logger = Logger.getLogger(GoodsService.class);

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
            logger.error("Fail to get all goods", e);
            throw new RuntimeException(e);
        }

        return goods;
    }

    public Good getGoodById(int goodId) {
        Connection connection = connectionPool.getConnection();
        try(GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            Optional<Good> goodOptional = goodDAO.findById(goodId);
            if (goodOptional.isPresent()) {
                return goodOptional.get();
            } else {
                throw new RuntimeException("Fail to find good by id");
            }
        } catch (Exception e) {
            logger.error("Fail to find good by id", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteGoodById(int goodId) {
        Connection connection = connectionPool.getConnection();
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goodDAO.delete(goodId);
        } catch (Exception e) {
            logger.error("Fail to delete good", e);
            throw new RuntimeException(e);
        }
    }

    public void addGood(Good good) {
        Connection connection = connectionPool.getConnection();
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goodDAO.insert(good);
        } catch (Exception e) {
            logger.error("Fail to add good", e);
            throw new RuntimeException(e);
        }
    }

    public void updateGood(Good good) {
        Connection connection = connectionPool.getConnection();
        try (GoodDAO goodDAO = factory.createGoodDAO(connection)) {
            goodDAO.update(good);
        } catch (Exception e) {
            logger.error("Fail to update good", e);
            throw new RuntimeException(e);
        }
    }

    public void addGoodToOrder(Order order, int goodId, int orderedQuantity) {
        Good good = getGoodById(goodId);
        good.setQuantity(orderedQuantity);

        order.getGoods().add(good);
    }
}
