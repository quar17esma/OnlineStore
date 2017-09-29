package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.checker.InputClientChecker;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.exceptions.BusyEmailException;
import com.serhii.shutyi.service.ClientsService;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Action {
    private ClientsService clientsService = ClientsService.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String name = request.getParameter("name").trim();
        String login = request.getParameter("login").trim();
        String pass = request.getParameter("password").trim();

        InputClientChecker checker = new InputClientChecker();
        boolean isDataCorrect = checker.isInputDataCorrect(name, login);

        if (isDataCorrect){
            Client client = new Client.Builder()
                    .setName(name)
                    .setUser(new User.Builder()
                            .setEmail(login)
                            .setPassword(pass)
                            .build())
                    .build();

            try {
                clientsService.registerClient(client);
                request.setAttribute("successRegistrationMessage", LabelManager.getProperty("message.success.registration"));
                page = ConfigurationManager.getProperty("path.page.login");

            } catch (BusyEmailException e) {
                request.setAttribute("name", name);
                request.setAttribute("login", login);
                request.setAttribute("errorBusyEmailMessage", LabelManager.getProperty("message.error.busy.email"));
                page = ConfigurationManager.getProperty("path.page.registration");
            }
        } else {
            request.setAttribute("errorRegistrationMessage", LabelManager.getProperty("message.error.wrong.data"));
            page = ConfigurationManager.getProperty("path.page.registration");
        }

        return page;
    }


}
