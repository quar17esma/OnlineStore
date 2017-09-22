package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.model.entity.Client;
import com.serhii.shutyi.model.entity.Good;
import com.serhii.shutyi.model.entity.User;
import com.serhii.shutyi.model.service.LoginChecker;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class Login implements Action {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if (LoginChecker.checkLogin(login, pass)) {
            Client client = getClientByEmail(login);
            request.getSession().setAttribute("client", client);

            request.setAttribute("user", login);
            request.setAttribute("goods", getAllGoods());

            page = ConfigurationManager.getProperty("path.page.main");
        } else {
            request.setAttribute("errorLoginPassMessage", LabelManager.getProperty("message.login.error"));
            page = ConfigurationManager.getProperty("path.page.login");
        }

        return page;
    }

    private Client getClientByEmail(String email) {
        Optional<User> user = Optional.empty();
        Optional<Client> client = null;
        DaoFactory daoFactory = DaoFactory.getInstance();
        try (UserDAO userDAO = daoFactory.createUserDAO();
             ClientDAO clientDAO = daoFactory.createClientDAO()) {
            user = userDAO.findByEmail(email);
            client = clientDAO.findById(user.get().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client.get();
    }

    private List<Good> getAllGoods() {
        List<Good> goods = null;

        try (GoodDAO goodDAO = DaoFactory.getInstance().createGoodDAO()) {
            goods = goodDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
    }
}