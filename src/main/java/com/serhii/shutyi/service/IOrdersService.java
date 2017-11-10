package com.serhii.shutyi.service;

import com.serhii.shutyi.entity.Order;

import java.util.List;

public interface IOrdersService {

    List<Order> getOrdersByClientId(int clientId);

    boolean payOrder(int orderId);

    void sendOrder(Order order);


}
