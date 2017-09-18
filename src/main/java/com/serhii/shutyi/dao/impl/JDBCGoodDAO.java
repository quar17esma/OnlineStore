package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.model.entity.Good;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCGoodDAO implements GoodDAO {

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
                Good good = new Good(rs.getInt("goods.id"),
                        rs.getString("goods.name"),
                        rs.getString("goods.description"),
                        rs.getInt("goods.price"));
                goods.add(good);
            }
        } catch (Exception ex) {
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

            while (rs.next()) {
                Good good = new Good(rs.getInt("goods.id"),
                        rs.getString("goods.name"),
                        rs.getString("goods.description"),
                        rs.getInt("goods.price"));
                result = Optional.of(good);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public boolean update(Good good) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE goods " +
                                     "SET goods.name = ?, goods.description, goods.price = ?" +
                                     "WHERE id = ?")) {
            query.setString(1, good.getName());
            query.setString(2, good.getDescription());
            query.setInt(3, good.getPrice());
            query.setInt(4, good.getId());

            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
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
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public int insert(Good good) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "INSERT INTO goods (goods.name, goods.description, goods.price) " +
                                     "VALUES(?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            query.setString(1, good.getName());
            query.setString(2, good.getDescription());
            query.setInt(3, good.getPrice());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                good.setId(result);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}