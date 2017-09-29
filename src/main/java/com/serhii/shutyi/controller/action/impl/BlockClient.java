package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.service.ClientsService;

import javax.servlet.http.HttpServletRequest;

public class BlockClient implements Action {
    private ClientsService clientsService = ClientsService.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String clientIdString = request.getParameter("clientId");
        if (clientIdString != null && !clientIdString.isEmpty()) {
            int clientId = Integer.parseInt(clientIdString);

            clientsService.blockClientById(clientId);

            request.setAttribute("successBlockClient", LabelManager.getProperty("message.success.block.client"));
        }

        return ConfigurationManager.getProperty("path.page.manage.clients");
    }
}
