package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.model.service.LoginLogic;

import javax.servlet.http.HttpServletRequest;

public class Login implements Action {

        @Override
        public String execute(HttpServletRequest request) {
            String page = null;

            String login = request.getParameter("login");
            String pass = request.getParameter("password");

            if (LoginLogic.checkLogin(login, pass)) {
                request.setAttribute("user", login);
                page = ConfigurationManager.getProperty("path.page.main");
            } else {
                request.setAttribute("errorLoginPassMessage", LabelManager.getProperty("message.login.error"));
                page = ConfigurationManager.getProperty("path.page.login");
            }

            return page;
        }
}