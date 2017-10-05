package com.serhii.shutyi.dao;

import com.serhii.shutyi.entity.Client;

import java.util.List;

public interface ClientDAO extends GenericDAO<Client> {
    List<Client> findWithUnpaidOrders();
}
