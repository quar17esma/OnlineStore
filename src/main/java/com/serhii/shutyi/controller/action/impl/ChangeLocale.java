package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;

import javax.servlet.http.HttpServletRequest;

public class ChangeLocale implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        String locale = request.getParameter("locale");

        request.getSession().setAttribute("locale", locale);

        return ConfigurationManager.getProperty("path.page.login");
    }
}
