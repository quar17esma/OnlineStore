package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.enums.OrderStatus;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCOrderDAO implements OrderDAO {
    private static final Logger LOGGER = Logger.getLogger(JDBCOrderDAO.class);

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
                Order order = createOrder(rs);
                orders.add(order);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find orders", ex);
            throw new RuntimeException(ex);
        }

        return orders;
    }

    public List<Order> findAllByClientId(int clientId) {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM orders " +
                        "WHERE orders.client_id = ?")) {
            query.setInt(1, clientId);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Order order = new Order.Builder()
                        .setId(rs.getInt("orders.id"))
                        .setOrderedAt(rs.getTimestamp("orders.ordered_at").toLocalDateTime())
                        .setStatus(OrderStatus.valueOf(rs.getString("orders.status")))
                        .build();
                orders.add(order);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find orders", ex);
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
                Order order = createOrder(rs);
                result = Optional.of(order);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find order by id", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    private Order createOrder(ResultSet rs) throws SQLException {
        Order order = new Order.Builder()
                .setId(rs.getInt("orders.id"))
                .setOrderedAt(rs.getTimestamp("orders.ordered_at").toLocalDateTime())
                .setStatus(OrderStatus.valueOf(rs.getString("orders.status")))
                .build();

        return order;
    }

    @Override
    public boolean update(Order order) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE orders " +
                                     "SET ordered_at = ?, status = ? " +
                                     "WHERE id = ?")) {
            query.setTimestamp(1, Timestamp.valueOf(order.getOrderedAt()));
            query.setString(2, order.getStatus().name());
            query.setInt(3, order.getId());

            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
            LOGGER.error("Fail to update order", ex);
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
            LOGGER.error("Fail to delete order", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public int insert(Order order) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "INSERT INTO orders (ordered_at, client_id) " +
                                     "VALUES(?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            query.setTimestamp(1, Timestamp.valueOf(order.getOrderedAt()));
            query.setInt(2, order.getClient().getId());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                order.setId(result);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to insert order", ex);
            throw new RuntimeException(ex);
        }


        for (Good good : order.getGoods()) {
            try (PreparedStatement query =
                         connection.prepareStatement(
                                 "INSERT INTO ordered_goods (good_id, order_id) " +
                                         "VALUES(?, ?)")) {

                query.setInt(1, good.getId());
                query.setInt(2, order.getId());

                query.executeUpdate();
            } catch (Exception ex) {
                LOGGER.error("Fail to insert ordered goods", ex);
                throw new RuntimeException(ex);
            }
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
