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
    private DaoFactory factory;
    private ClientsService clientsService;

    public LoginService(DaoFactory factory, ClientsService clientsService) {
        this.factory = factory;
        this.clientsService = clientsService;
    }

    private static class Holder {
        private static LoginService INSTANCE = new LoginService(DaoFactory.getInstance(), ClientsService.getInstance());
    }

    public static LoginService getInstance() {
        return LoginService.Holder.INSTANCE;
    }

    public Client login(String login, String password) throws LoginException {
        if (checkLogin(login, password)) {
            return clientsService.getClientByEmail(login);
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

            try(UserDAO userDAO = factory.createUserDAO(ConnectionPool.getConnection())) {
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
