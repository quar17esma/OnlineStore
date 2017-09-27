package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.service.MyOrdersService;
import com.serhii.shutyi.service.ShowGoodsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MyOrders implements Action{
    @Override
    public String execute(HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("client");

        List<Order> orders = MyOrdersService.getInstance().getOrdersByClientId(client.getId());

        request.setAttribute("orders", orders);

        return ConfigurationManager.getProperty("path.page.my.orders");
    }
}
