package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.entity.Order;

import javax.servlet.http.HttpServletRequest;

public class ShowCart implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("locale");
        Order order = (Order) request.getSession().getAttribute("order");

        if (order != null && !order.getGoods().isEmpty()) {
            request.setAttribute("order", order);
        } else {
            request.setAttribute("emptyOrderMessage",
                    LabelManager.getProperty("message.empty.order", locale));
        }

        return ConfigurationManager.getProperty("path.page.my.order");
    }
}
