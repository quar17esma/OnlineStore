package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.model.entity.Client;
import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCClientDAO implements ClientDAO {

    private Connection connection;

    public JDBCClientDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM client " +
                        "JOIN user ON client.id = user.id " +
                        "JOIN users_roles ON user.id = users_roles.user_id " +
                        "JOIN role ON role.id = users_roles.role_id")) {
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Client client = new Client(rs.getInt("client.id"),
                        rs.getString("client.name"),
                        rs.getInt("client.discount"),
                        new User(rs.getInt("user.id"),
                                rs.getString("user.email"),
                                rs.getString("user.password"),
                                rs.getBoolean("user.enabled"),
                                null,
                                null));
                clients.add(client);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return clients;
    }

    @Override
    public Optional<Client> findById(int id) {

        Optional<Client> result = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM client " +
                        "JOIN user ON client.id = user.id " +
                        "JOIN users_roles ON user.id = users_roles.user_id " +
                        "JOIN role ON role.id = users_roles.role_id" +
                        "WHERE client.id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Client client = new Client(rs.getInt("client.id"),
                        rs.getString("client.name"),
                        rs.getInt("client.discount"),
                        new User(rs.getInt("user.id"),
                                rs.getString("user.email"),
                                rs.getString("user.password"),
                                rs.getBoolean("user.enabled"),
                                null,
                                null));
                result = Optional.of(client);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public boolean update(Client item) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public int insert(Client client) {
        int result = -1;
        try (PreparedStatement query = connection
                .prepareStatement("INSERT INTO client (" +
                        "client.name, client.discount)"
                        + "VALUES( ? , ? ,?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            query.setString(1, client.getFirstName());
            query.setString(2, client.getFirstName());
            query.setString(3, client.getFirstName());
            query.setString(4, client.getFirstName());
            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                client.setId(result);
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
