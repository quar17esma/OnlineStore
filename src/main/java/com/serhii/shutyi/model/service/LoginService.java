package com.serhii.shutyi.model.service;

import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.model.entity.Client;
import com.serhii.shutyi.model.entity.Good;
import com.serhii.shutyi.model.entity.User;

import java.util.List;

public class LoginService {
    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static LoginService INSTANCE = new LoginService();
    }

    public static LoginService getInstance() {
        return LoginService.Holder.INSTANCE;
    }

    public Client login(String login, String password) {

        if (LoginChecker.checkLogin(login, password)) {
            return getClientByEmail(login);
        } else {
            //my exception
            throw new RuntimeException();
        }
    }

    private Client getClientByEmail(String email) {
        Client client = null;

        try (UserDAO userDAO = factory.createUserDAO();
             ClientDAO clientDAO = factory.createClientDAO()) {
            User user = userDAO.findByEmail(email).get();
            client = clientDAO.findById(user.getId()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }

    public List<Good> getAllGoods() {
        List<Good> goods = null;

        try (GoodDAO goodDAO = factory.createGoodDAO()) {
            goods = goodDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
    }
}
