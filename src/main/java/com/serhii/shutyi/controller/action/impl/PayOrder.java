package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.service.OrdersService;

import javax.servlet.http.HttpServletRequest;

public class PayOrder implements Action {
    private OrdersService ordersService = OrdersService.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        boolean isPaid = ordersService.payOrder(orderId);

        if (isPaid) {
            request.setAttribute("successPayOrder", LabelManager.getProperty("message.success.pay.order"));
        } else {
            request.setAttribute("errorPayOrder", LabelManager.getProperty("message.error.pay.order"));
        }


        return ConfigurationManager.getProperty("path.page.my.orders");
    }
}
