package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import javax.servlet.http.HttpServletRequest;


public class Empty implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        page = ConfigurationManager.getProperty("path.page.login");

        return page;
    }
}
