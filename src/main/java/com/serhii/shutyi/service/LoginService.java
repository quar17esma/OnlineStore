package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.*;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.exceptions.LoginException;
import javafx.fxml.LoadException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class LoginService {
    DaoFactory factory;
    Connection connection;

    public LoginService(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    private static class Holder {
        private static LoginService INSTANCE = new LoginService(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static LoginService getInstance() {
        LoginService loginService = LoginService.Holder.INSTANCE;
        loginService.connection = ConnectionPool.getConnection();
        return loginService;
    }

    public Client login(String login, String password) throws LoginException {

        if (checkLogin(login, password)) {
            return ClientsService.getInstance().getClientByEmail(login);
        } else {
            throw new LoginException("Fail to login", login);
        }
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
