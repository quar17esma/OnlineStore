package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.model.entity.Client;
import com.serhii.shutyi.dao.ClientDAO;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JDBCClientDAO implements ClientDAO {

    private Connection connection;

    public JDBCClientDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Optional<Client> findById(int id) {

        Optional<Client> result = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM client WHERE id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Client client = new Client(rs.getInt("id"),
                        rs.getString("first_name"));
                result = Optional.of(client);
            }
        }catch (Exception ex){
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
                .prepareStatement("INSERT INTO client (first_name," +
                        "  last_name ,ident_code, telephone)"
                        + "VALUES( ? , ? ,?, ?)" , Statement.RETURN_GENERATED_KEYS )) {

            query.setString(1, client.getFirstName());
            query.setString(2, client.getFirstName());
            query.setString(3, client.getFirstName());
            query.setString(4, client.getFirstName());
            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if( rsId.next()){
                result = rsId.getInt(1);
                client.setId(result);
            }

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
