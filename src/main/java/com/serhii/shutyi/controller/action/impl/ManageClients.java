package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.service.ClientsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ManageClients implements Action {
    private ClientsService clientsService = ClientsService.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        List<Client> clients = clientsService.getClientsWithUnpaidOrders();

        request.setAttribute("clients", clients);

        return ConfigurationManager.getProperty("path.page.manage.clients");
    }
}
