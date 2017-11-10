package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.service.IOrdersService;
import com.serhii.shutyi.service.impl.OrdersService;

import javax.servlet.http.HttpServletRequest;

public class PayOrder implements Action {
    private IOrdersService ordersService;

    public PayOrder() {
        this.ordersService = OrdersService.getInstance();
    }

    public PayOrder(IOrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("locale");
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        boolean isPaid = ordersService.payOrder(orderId);

        if (isPaid) {
            request.setAttribute("successPayOrder",
                    LabelManager.getProperty("message.success.pay.order", locale));
        } else {
            request.setAttribute("errorPayOrder",
                    LabelManager.getProperty("message.error.pay.order", locale));
        }


        return ConfigurationManager.getProperty("path.page.my.orders");
    }
}
