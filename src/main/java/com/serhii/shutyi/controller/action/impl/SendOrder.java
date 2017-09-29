package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.service.OrdersService;

import javax.servlet.http.HttpServletRequest;

public class SendOrder implements Action {
    private  OrdersService ordersService = OrdersService.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        Order order = (Order) request.getSession().getAttribute("order");


        if (order != null) {
            ordersService.sendOrder(order);

            Order emptyOrder = new Order.Builder()
                    .setClient(order.getClient())
                    .build();
            request.getSession().setAttribute("order", emptyOrder);

            request.setAttribute("successSendOrderMessage", LabelManager.getProperty("message.success.send.order"));
        }

        return ConfigurationManager.getProperty("path.page.goods");
    }
}
