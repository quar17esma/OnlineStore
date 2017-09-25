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
    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static LoginService INSTANCE = new LoginService();
    }

    public static LoginService getInstance() {
        return LoginService.Holder.INSTANCE;
    }

    public Client login(String login, String password) throws LoginException {

        LoginChecker checker = new LoginChecker();
        if (checker.checkLogin(login, password)) {
            return getClientByEmail(login);
        } else {
            throw new LoginException("Fail to login", login);
        }
    }

    private Client getClientByEmail(String email) {
        Client client = null;

        Connection connection = ConnectionPool.getConnection();
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

    public List<Good> getAllGoods() {
        List<Good> goods = null;

        try (GoodDAO goodDAO = factory.createGoodDAO(ConnectionPool.getConnection())) {
            goods = goodDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
    }
}
