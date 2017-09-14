package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.model.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCOrderDAO implements OrderDAO {

    private Connection connection;

    public JDBCOrderDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM orders")) {
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Order order = new Order(rs.getInt("orders.id"),
                        rs.getTimestamp("orders.ordered_at").toLocalDateTime());
                orders.add(order);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return orders;
    }

    @Override
    public Optional<Order> findById(int id) {

        Optional<Order> result = Optional.empty();

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "SELECT * FROM orders " +
                                     "WHERE orders.id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Order order = new Order(rs.getInt("orders.id"),
                        rs.getTimestamp("orders.ordered_at").toLocalDateTime());
                result = Optional.of(order);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public boolean update(Order order) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE orders " +
                                     "SET orders.ordered_at = ?" +
                                     "WHERE id = ?")) {
            query.setString(1, String.valueOf(Timestamp.valueOf(order.getOrderedAt())));
            query.setInt(2, order.getId());

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
                             "DELETE FROM orders " +
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
    public int insert(Order order) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "INSERT INTO orders (orders.ordered_at) " +
                                     "VALUES(?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            query.setString(1, String.valueOf(Timestamp.valueOf(order.getOrderedAt())));

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                order.setId(result);
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
