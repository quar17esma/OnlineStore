package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.enums.OrderStatus;
import com.serhii.shutyi.enums.Role;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCOrderDAO implements OrderDAO {
    final static Logger logger = Logger.getLogger(JDBCOrderDAO.class);

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
            logger.error("Fail to find orders", ex);
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
            logger.error("Fail to find orders", ex);
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
            logger.error("Fail to find order by id", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    private Order createOrder(ResultSet rs) throws SQLException {
        Order order = new Order.Builder()
                .setId(rs.getInt("orders.id"))
                .setOrderedAt(rs.getTimestamp("orders.ordered_at").toLocalDateTime())
                .setStatus(OrderStatus.valueOf(rs.getString("orders.status")))
                .setClient(new Client.Builder()
                        .setId(rs.getInt("client.id"))
                        .setName(rs.getString("client.name"))
                        .setIsInBlackList(rs.getBoolean("client.is_in_black_list"))
                        .setUser(new User.Builder()
                                .setId(rs.getInt("user.id"))
                                .setEmail(rs.getString("user.email"))
                                .setPassword(rs.getString("user.password"))
                                .setEnabled(rs.getBoolean("user.enabled"))
                                .setRole(Role.valueOf(rs.getString("user.role").toUpperCase()))
                                .build())
                        .build())
                .build();

        return order;
    }

    @Override
    public boolean update(Order order) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE orders " +
                                     "SET ordered_at = ? " +
                                     "WHERE id = ?")) {
            query.setString(1, String.valueOf(Timestamp.valueOf(order.getOrderedAt())));
            query.setInt(2, order.getId());

            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
            logger.error("Fail to update order", ex);
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
            logger.error("Fail to delete order", ex);
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

            String orderedAt = String.valueOf(Timestamp.valueOf(order.getOrderedAt()));
            query.setString(1, orderedAt);
            query.setInt(2, order.getClient().getId());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                order.setId(result);
            }
        } catch (Exception ex) {
            logger.error("Fail to insert order", ex);
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
                logger.error("Fail to insert ordered goods", ex);
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
