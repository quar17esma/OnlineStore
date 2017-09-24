package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.service.SendOrderService;

import javax.servlet.http.HttpServletRequest;

public class SendOrder implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        Order order = (Order) request.getSession().getAttribute("order");

        if (order != null) {
            SendOrderService.getInstance().sendOrder(order);
        }

        request.setAttribute("successSendOrderMessage", LabelManager.getProperty("message.success.send.order"));

        return ConfigurationManager.getProperty("path.page.main");
    }
}
