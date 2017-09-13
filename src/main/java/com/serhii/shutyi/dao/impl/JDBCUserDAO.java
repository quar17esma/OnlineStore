package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.model.entity.Client;
import com.serhii.shutyi.model.entity.Role;
import com.serhii.shutyi.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
                        "JOIN user ON client.id = user.id " +
                        "JOIN users_roles ON user.id = users_roles.user_id " +
                        "JOIN role ON role.id = users_roles.role_id")) {
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Client client = new Client(rs.getInt("client.id"),
                        rs.getString("client.name"),
                        rs.getInt("client.discount"),
                        null);

                User user = new User(rs.getInt("user.id"),
                        rs.getString("user.email"),
                        rs.getString("user.password"),
                        rs.getBoolean("user.enabled"),
                        null,
                        null);

                Role role = new Role(rs.getInt("role.id"),
                        rs.getString("role.role"));

                user.setClient(client);
                user.setRole(role);
                client.setUser(user);

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
                                     "JOIN users_roles ON user.id = users_roles.user_id " +
                                     "JOIN role ON role.id = users_roles.role_id " +
                                     "WHERE user.id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Client client = new Client(rs.getInt("client.id"),
                        rs.getString("client.name"),
                        rs.getInt("client.discount"),
                        null);

                User user = new User(rs.getInt("user.id"),
                        rs.getString("user.email"),
                        rs.getString("user.password"),
                        rs.getBoolean("user.enabled"),
                        null,
                        null);

                Role role = new Role(rs.getInt("role.id"),
                        rs.getString("role.role"));

                user.setClient(client);
                user.setRole(role);
                client.setUser(user);

                result = Optional.of(user);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE user " +
                                     "SET user.email = ?, user.password = ?, user.enabled  = ?" +
                                     "WHERE id = ?")) {
            query.setString(1, user.getEmail());
            query.setString(2, user.getPassword());
            query.setBoolean(3, user.isEnabled());
            query.setInt(4, user.getId());

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
                             "INSERT INTO user (user.email, user.password, user.enabled) " +
                                     "VALUES(?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            query.setString(1, user.getEmail());
            query.setString(2, user.getPassword());
            query.setBoolean(3, user.isEnabled());

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
