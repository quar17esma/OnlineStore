package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.service.RegistrationService;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Action {
    @Override
    public String execute(HttpServletRequest request) {
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

        RegistrationService.getInstance().registerClient(client);

        return ConfigurationManager.getProperty("path.page.login");
    }


}
