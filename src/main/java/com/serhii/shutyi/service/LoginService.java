package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.*;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.exceptions.LoginException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Optional;

public class LoginService extends Service {
    private static final Logger LOGGER = Logger.getLogger(LoginService.class);

    private ClientsService clientsService;

    private LoginService(DaoFactory factory, ClientsService clientsService, ConnectionPool connectionPool) {
        super(factory, connectionPool);
        this.clientsService = clientsService;
    }

    private static class Holder {
        private static LoginService INSTANCE =
                new LoginService(DaoFactory.getInstance(), ClientsService.getInstance(), ConnectionPool.getInstance());
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

    private boolean checkLogin(String login, String password) {
        boolean result = false;

        if (login != null &&
                password != null &&
                !login.isEmpty() &&
                !password.isEmpty()) {

            try(Connection connection = connectionPool.getConnection();
                UserDAO userDAO = factory.createUserDAO(connection)) {
                connection.setAutoCommit(true);
                Optional<User> user = userDAO.findByEmail(login);
                if (user.isPresent()) {
                    result = user.get().getPassword().equals(password);
                }
            } catch (Exception e) {
                LOGGER.error("Fail to find user by email", e);
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}
