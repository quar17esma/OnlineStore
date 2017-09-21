package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.model.entity.Client;
import com.serhii.shutyi.model.entity.User;
import com.serhii.shutyi.model.enums.Role;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        registerClient(request);

        return page = ConfigurationManager.getProperty("path.page.login");
    }

    private void registerClient(HttpServletRequest request) {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String pass = request.getParameter("password");


        Client client = new Client.Builder()
                .setName(name)
                .setUser(new User.Builder()
                        .setEmail(login)
                        .setPassword(pass)
                        .build())
                .build();

        insertClientToDB(client);
    }

    private void insertClientToDB(Client client) {

        try (UserDAO userDAO = DaoFactory.getInstance().createUserDAO();
             ClientDAO clientDAO = DaoFactory.getInstance().createClientDAO()) {

            int userId = userDAO.insert(client.getUser());
            client.setId(userId);
            clientDAO.insert(client);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
