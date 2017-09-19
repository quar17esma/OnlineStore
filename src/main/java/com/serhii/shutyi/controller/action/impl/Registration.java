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

        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        Client client = new Client();
        client.setName(name);

        User user = new User();
        user.setEmail(login);
        user.setPassword(pass);
        user.setRole(Role.USER);


        DaoFactory daoFactory = DaoFactory.getInstance();
        try(UserDAO userDAO = daoFactory.createUserDAO();
                ClientDAO clientDAO = daoFactory.createClientDAO()) {
            int userId = userDAO.insert(user);
            client.setId(userId);
            clientDAO.insert(client);
            //set user client getid
            //user insert

        } catch (Exception e) {
            e.printStackTrace();
        }
        return page = ConfigurationManager.getProperty("path.page.login");
    }
}
