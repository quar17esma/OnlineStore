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

public class RegisterClient implements Action {
    private ClientsService clientsService;
    private InputClientChecker checker;

    public RegisterClient() {
        this.clientsService = ClientsService.getInstance();
        this.checker = new InputClientChecker();
    }

    public RegisterClient(ClientsService clientsService, InputClientChecker checker) {
        this.clientsService = clientsService;
        this.checker = checker;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        if (locale == null) {
            locale = "en_US";
        }
        String name = request.getParameter("name").trim();
        String login = request.getParameter("login").trim();
        String password = request.getParameter("password").trim();

        boolean isDataCorrect = checkInputData(name, login);

        if (isDataCorrect) {
            Client client = makeClient(name, login, password);
            page = registerClient(client, request, locale);
        } else {
            request.setAttribute("errorRegistrationMessage",
                    LabelManager.getProperty("message.error.wrong.data", locale));
            page = ConfigurationManager.getProperty("path.page.registration");
        }

        return page;
    }

    private String registerClient(Client client, HttpServletRequest request, String locale) {
        String page = null;

        try {
            clientsService.registerClient(client);
            request.setAttribute("successRegistrationMessage",
                    LabelManager.getProperty("message.success.registration", locale));
            page = ConfigurationManager.getProperty("path.page.login");

        } catch (BusyEmailException e) {
            request.setAttribute("name", client.getName());
            request.setAttribute("login", client.getUser().getEmail());
            request.setAttribute("errorBusyEmailMessage",
                    LabelManager.getProperty("message.error.busy.email", locale));
            page = ConfigurationManager.getProperty("path.page.registration");
        }

        return page;
    }

    private boolean checkInputData(String name, String login) {
        return checker.isInputDataCorrect(name, login);
    }

    private Client makeClient(String name, String login, String password) {
        return new Client.Builder()
                .setName(name)
                .setUser(new User.Builder()
                        .setEmail(login)
                        .setPassword(password)
                        .build())
                .build();
    }

}
