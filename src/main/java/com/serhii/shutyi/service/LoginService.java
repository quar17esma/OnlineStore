package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.*;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.exceptions.LoginException;
import javafx.fxml.LoadException;

import java.sql.Connection;
import java.util.List;

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

        LoginChecker checker = LoginChecker.getInstance();
        if (checker.checkLogin(login, password)) {
            return getClientByEmail(login);
        } else {
            throw new LoginException("Fail to login", login);
        }
    }

    private Client getClientByEmail(String email) {
        Client client = null;

        try (UserDAO userDAO = factory.createUserDAO(connection);
             ClientDAO clientDAO = factory.createClientDAO(connection)) {
            connection.setAutoCommit(false);

            User user = userDAO.findByEmail(email).get();
            client = clientDAO.findById(user.getId()).get();

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }
}
