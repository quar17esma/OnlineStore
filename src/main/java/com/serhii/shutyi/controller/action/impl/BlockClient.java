package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.service.ClientsService;

import javax.servlet.http.HttpServletRequest;

public class BlockClient implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        String clientIdString = request.getParameter("clientId");
        if (clientIdString != null) {
            int clientId = Integer.parseInt(clientIdString);
            ClientsService.getInstance().blockClientById(clientId);

            request.setAttribute("successBlockClient", LabelManager.getProperty("message.success.block.client"));
        }


        return ConfigurationManager.getProperty("path.page.manage.clients");
    }
}
