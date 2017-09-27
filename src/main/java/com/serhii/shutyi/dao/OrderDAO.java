package com.serhii.shutyi.dao;

import com.serhii.shutyi.entity.Order;

import java.util.List;

public interface OrderDAO extends GenericDAO<Order> {
    List<Order> findAllByClientId(int clientId);
}
