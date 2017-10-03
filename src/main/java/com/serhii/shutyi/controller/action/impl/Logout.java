package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class Logout implements Action {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.login");

        request.getSession().invalidate();

        return page;
    }
}
