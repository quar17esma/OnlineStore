package com.serhii.shutyi.dao.impl;

import com.serhii.shutyi.dao.CategoryDAO;
import com.serhii.shutyi.model.entity.Category;
import com.serhii.shutyi.model.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCategoryDAO implements CategoryDAO {

    private Connection connection;

    public JDBCCategoryDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM categories")) {
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"));
                categories.add(category);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return categories;
    }

    @Override
    public Optional<Category> findById(int id) {

        Optional<Category> result = Optional.empty();

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "SELECT * FROM categories " +
                                     "WHERE categories.id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"));
                result = Optional.of(category);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public boolean update(Category category) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE categories " +
                                     "SET categories.title = ?, categories.description = ?" +
                                     "WHERE id = ?")) {
            query.setString(1, category.getTitle());
            query.setString(2,category.getDescription());
            query.setInt(3, category.getId());

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
                             "DELETE FROM categories " +
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
    public int insert(Category category) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "INSERT INTO categories (categories.title, categories.description) " +
                                     "VALUES(?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            query.setString(1, category.getTitle());
            query.setString(2, category.getDescription());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                category.setId(result);
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
