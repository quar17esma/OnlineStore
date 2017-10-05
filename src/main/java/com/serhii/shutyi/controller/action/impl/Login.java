package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.exceptions.LoginException;
import com.serhii.shutyi.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Login implements Action {
    private static final String DEFAULT_LOCALE = "en_US";

    private LoginService loginService;

    public Login() {
        this.loginService = LoginService.getInstance();
    }

    public Login(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            Client client = loginService.login(login, password);
            Order order = new Order.Builder()
                    .setClient(client)
                    .build();

            request.getSession().setAttribute("client", client);
            request.getSession().setAttribute("order", order);
            if (locale == null) {
                request.getSession().setAttribute("locale", DEFAULT_LOCALE);
            }

            page = ConfigurationManager.getProperty("path.page.welcome");
        } catch (LoginException e) {
            request.setAttribute("errorLoginPassMessage",
                    LabelManager.getProperty("message.login.error", request.getParameter("locale")));

            page = ConfigurationManager.getProperty("path.page.login");
        }

        return page;
    }
}