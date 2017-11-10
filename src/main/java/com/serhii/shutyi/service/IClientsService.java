package com.serhii.shutyi.service;

import com.serhii.shutyi.entity.Client;

import java.util.List;

public interface IClientsService {

    void registerClient(Client client);

    Client getClientByEmail(String email);

    List<Client> getClientsWithUnpaidOrders();

    void blockClientById(int clientId);
}
