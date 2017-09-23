package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.model.entity.Order;
import com.serhii.shutyi.model.service.SendOrderService;

import javax.servlet.http.HttpServletRequest;

public class SendOrder implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        Order order = (Order) request.getSession().getAttribute("order");

        if (order != null) {
            SendOrderService.getInstance().sendOrder(order);
        }

        return ConfigurationManager.getProperty("path.page.main");
    }
}
