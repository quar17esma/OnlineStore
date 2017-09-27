package com.serhii.shutyi.service;


import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.entity.User;

import java.sql.Connection;
import java.util.Optional;

public class LoginChecker {
    DaoFactory factory;
    Connection connection;

    public LoginChecker(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    private static class Holder {
        private static LoginChecker INSTANCE = new LoginChecker(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static LoginChecker getInstance() {
        LoginChecker loginChecker = LoginChecker.Holder.INSTANCE;
        loginChecker.connection = ConnectionPool.getConnection();
        return loginChecker;
    }

    public boolean checkLogin(String login, String password) {
        boolean result = false;

        if (login != null &&
                password != null &&
                !login.isEmpty() &&
                !password.isEmpty()) {

            try(UserDAO userDAO = factory.createUserDAO(connection)) {
                Optional<User> user = userDAO.findByEmail(login);
                if (user.isPresent()) {
                    result = user.get().getPassword().equals(password);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}

