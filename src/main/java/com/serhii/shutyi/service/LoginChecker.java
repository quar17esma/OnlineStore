package com.serhii.shutyi.service;


import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.entity.User;

import java.sql.Connection;
import java.util.Optional;

public class LoginChecker {
    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static LoginChecker INSTANCE = new LoginChecker();
    }

    public static LoginChecker getInstance() {
        return LoginChecker.Holder.INSTANCE;
    }

    public boolean checkLogin(String enterLogin, String enterPass) {
        boolean result = false;

        try(UserDAO userDAO = factory.createUserDAO(getConnection())) {
            Optional<User> user = userDAO.findByEmail(enterLogin);
            if (user.isPresent()) {
                result = user.get().getPassword().equals(enterPass);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public Connection getConnection() {
        return ConnectionPool.getConnection();
    }
}

