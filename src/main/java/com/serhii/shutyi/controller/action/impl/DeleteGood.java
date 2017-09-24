package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.service.DeleteGoodService;

import javax.servlet.http.HttpServletRequest;

public class DeleteGood implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        int goodId = Integer.parseInt(request.getParameter("goodId"));

        DeleteGoodService.getInstance().deleteGoodById(goodId);

        return ConfigurationManager.getProperty("path.page.main");
    }


}
