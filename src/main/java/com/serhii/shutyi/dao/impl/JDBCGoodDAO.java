package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCGoodDAO implements GoodDAO {
    final static Logger logger = Logger.getLogger(JDBCGoodDAO.class);

    private Connection connection;

    public JDBCGoodDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Good> findAll() {
        List<Good> goods = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM goods")) {
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Good good = createGood(rs);
                goods.add(good);
            }
        } catch (Exception ex) {
            logger.error("Fail to find goods", ex);
            throw new RuntimeException(ex);
        }

        return goods;
    }

    @Override
    public Optional<Good> findById(int id) {

        Optional<Good> result = Optional.empty();

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "SELECT * FROM goods " +
                                     "WHERE goods.id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            if (rs.next()) {
                Good good = createGood(rs);
                result = Optional.of(good);
            }
        } catch (Exception ex) {
            logger.error("Fail to find good by id", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    private Good createGood(ResultSet rs) throws SQLException {

        Good good = new Good.Builder()
                .setId(rs.getInt("goods.id"))
                .setName(rs.getString("goods.name"))
                .setDescription(rs.getString("goods.description"))
                .setPrice(rs.getInt("goods.price"))
                .setQuantity(rs.getInt("goods.quantity"))
                .build();

        return good;
    }

    @Override
    public boolean update(Good good) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE goods " +
                                     "SET name = ?, description = ?, price = ?, quantity = ? " +
                                     "WHERE id = ?")) {
            query.setString(1, good.getName());
            query.setString(2, good.getDescription());
            query.setInt(3, good.getPrice());
            query.setInt(4, good.getQuantity());
            query.setInt(5, good.getId());
            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
            logger.error("Fail to update good", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "DELETE FROM goods " +
                                     "WHERE id = ?")) {
            query.setInt(1, id);
            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
            logger.error("Fail to delete good", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public int insert(Good good) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "INSERT INTO goods (" +
                                     "name, description, price, quantity) " +
                                     "VALUES(?, ?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            query.setString(1, good.getName());
            query.setString(2, good.getDescription());
            query.setInt(3, good.getPrice());
            query.setInt(4, good.getQuantity());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                good.setId(result);
            }
        } catch (Exception ex) {
            logger.error("Fail to insert good", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
