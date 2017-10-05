package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.service.OrdersService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MyOrders implements Action{
    private OrdersService ordersService;

    public MyOrders() {
        this.ordersService = OrdersService.getInstance();
    }

    public MyOrders(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("client");

        List<Order> orders = ordersService.getOrdersByClientId(client.getId());

        request.setAttribute("orders", orders);

        return ConfigurationManager.getProperty("path.page.my.orders");
    }
}
