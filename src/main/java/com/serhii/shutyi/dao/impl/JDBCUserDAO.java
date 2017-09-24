package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDAO implements UserDAO {

    private Connection connection;

    public JDBCUserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM user " +
                        "JOIN user ON client.id = user.id ")) {
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                User user = createUser(rs);

                users.add(user);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return users;
    }

    @Override
    public Optional<User> findById(int id) {

        Optional<User> result = Optional.empty();

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "SELECT * FROM user " +
                                     "JOIN client ON client.id = user.id " +
                                     "WHERE user.id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                User user = createUser(rs);

                result = Optional.of(user);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> result = Optional.empty();

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "SELECT * FROM user " +
                                     "JOIN client ON client.id = user.id " +
                                     "WHERE user.email = ? ")) {
            query.setString(1, email);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                User user = createUser(rs);

                result = Optional.of(user);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    private User createUser(ResultSet rs) throws SQLException {

        User user = new User.Builder()
                .setId(rs.getInt("user.id"))
                .setEmail(rs.getString("user.email"))
                .setPassword(rs.getString("user.password"))
                .setEnabled(rs.getBoolean("user.enabled"))
                .setRole(Role.valueOf(rs.getString("user.role").toUpperCase()))
                .build();
        
        return user;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE user " +
                                     "SET email = ?, password = ?, enabled  = ?, role = ?" +
                                     "WHERE id = ?")) {
            query.setString(1, user.getEmail());
            query.setString(2, user.getPassword());
            query.setBoolean(3, user.isEnabled());
            query.setString(4,user.getRole().name());
            query.setInt(5, user.getId());

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
                             "DELETE FROM user " +
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
    public int insert(User user) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "INSERT INTO user (email, password, enabled, role) " +
                                     "VALUES(?, ?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            query.setString(1, user.getEmail());
            query.setString(2, user.getPassword());
            query.setBoolean(3, user.isEnabled());
            query.setString(4, user.getRole().name());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                user.setId(result);
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
