package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.service.impl.ClientsService;
import com.serhii.shutyi.service.IClientsService;

import javax.servlet.http.HttpServletRequest;

public class BlockClient implements Action {
    private IClientsService clientsService;

    public BlockClient() {
        this.clientsService = ClientsService.getInstance();
    }

    public BlockClient(IClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("locale");
        String clientIdString = request.getParameter("clientId");

        if (clientIdString != null && !clientIdString.isEmpty()) {
            int clientId = Integer.parseInt(clientIdString);

            clientsService.blockClientById(clientId);

            request.setAttribute("successBlockClient",
                    LabelManager.getProperty("message.success.block.client", locale));
        }

        return ConfigurationManager.getProperty("path.page.manage.clients");
    }
}
