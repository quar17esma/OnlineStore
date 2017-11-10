package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.service.impl.ClientsService;
import com.serhii.shutyi.service.IClientsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ManageClients implements Action {
    private IClientsService clientsService;

    public ManageClients() {
        this.clientsService = ClientsService.getInstance();
    }

    public ManageClients(IClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Client> clients = clientsService.getClientsWithUnpaidOrders();

        request.setAttribute("clients", clients);

        return ConfigurationManager.getProperty("path.page.manage.clients");
    }
}
