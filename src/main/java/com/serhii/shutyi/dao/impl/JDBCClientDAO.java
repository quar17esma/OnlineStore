package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.enums.Role;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCClientDAO implements ClientDAO {
    final static Logger logger = Logger.getLogger(JDBCClientDAO.class);

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
            logger.error("Fail to find clients", ex);
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
            logger.error("Fail to find client by id", ex);
            throw new RuntimeException(ex);
        }
        return result;
    }

    private Client createClientWithUser(ResultSet rs) throws SQLException {

        Client client = new Client.Builder()
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
                .build();

        return client;
    }

    @Override
    public boolean update(Client client) {
        boolean result = false;
        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE client " +
                                     "SET name = ?, is_in_black_list = ? " +
                                     "WHERE id = ?")) {
            query.setString(1, client.getName());
            query.setBoolean(2, client.isInBlackList());
            query.setInt(3, client.getId());

            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
            logger.error("Fail to update client", ex);
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
            logger.error("Fail to delete client", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public int insert(Client client) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                        "INSERT INTO client (id, name) " +
                                "VALUES(?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {
            query.setInt(1, client.getId());
            query.setString(2, client.getName());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                client.setId(result);
            }
        } catch (Exception ex) {
            logger.error("Fail to insert client", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
