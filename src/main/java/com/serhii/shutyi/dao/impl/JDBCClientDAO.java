package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.model.entity.Client;
import com.serhii.shutyi.model.entity.User;
import com.serhii.shutyi.model.enums.Role;

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
                        "JOIN user ON client.id = user.id ")) {
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Client client = createClientWithUser(rs);
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
                        "WHERE client.id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Client client = createClientWithUser(rs);
                result = Optional.of(client);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    private Client createClientWithUser(ResultSet rs) throws SQLException {

        Client client = new Client(rs.getInt("client.id"),
                rs.getString("client.name"),
                rs.getInt("client.discount"),
                rs.getBoolean("client.is_in_black_list"),
                null);

        User user = new User(rs.getInt("user.id"),
                rs.getString("user.email"),
                rs.getString("user.password"),
                rs.getBoolean("user.enabled"),
                null,
                null);

        Role role = Role.valueOf(rs.getString("user.role"));

        user.setClient(client);
        user.setRole(role);
        client.setUser(user);

        return client;
    }

    @Override
    public boolean update(Client client) {
        boolean result = false;
        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE client " +
                                     "SET name = ?, discount = ?, is_in_black_list = ? " +
                                     "WHERE id = ?")) {
            query.setString(1, client.getName());
            query.setInt(2, client.getDiscount());
            query.setBoolean(3, client.isInBlackList());
            query.setInt(4, client.getId());

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
                             "DELETE FROM client " +
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
    public int insert(Client client) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                        "INSERT INTO client (id, name, discount, is_in_black_list) " +
                                "VALUES(?, ?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {
            query.setInt(1, client.getId());
            query.setString(2, client.getName());
            query.setInt(3, client.getDiscount());
            query.setBoolean(4, client.isInBlackList());

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
