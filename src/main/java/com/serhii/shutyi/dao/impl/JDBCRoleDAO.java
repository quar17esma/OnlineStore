package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.dao.RoleDAO;
import com.serhii.shutyi.model.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCRoleDAO implements RoleDAO {

    private Connection connection;

    public JDBCRoleDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM role")) {
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Role role = new Role(rs.getInt("role.id"),
                        rs.getString("role.role"));
                roles.add(role);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return roles;
    }

    @Override
    public Optional<Role> findById(int id) {

        Optional<Role> result = Optional.empty();

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "SELECT * FROM role " +
                                     "WHERE role.id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Role role = new Role(rs.getInt("role.id"),
                        rs.getString("role.role"));
                result = Optional.of(role);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public boolean update(Role role) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE role " +
                                     "SET role.role = ?" +
                                     "WHERE id = ?")) {
            query.setString(1, role.getRole());
            query.setInt(2, role.getId());

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
                             "DELETE FROM role " +
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
    public int insert(Role role) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "INSERT INTO role (role.role) " +
                                     "VALUES(?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            query.setString(1, role.getRole());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                role.setId(result);
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
