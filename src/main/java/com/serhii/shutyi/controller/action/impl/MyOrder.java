package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.entity.Order;

import javax.servlet.http.HttpServletRequest;

public class MyOrder implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        Order order = (Order) request.getSession().getAttribute("order");
        if (order != null && !order.getGoods().isEmpty()) {
            request.setAttribute("goods", order.getGoods());
        } else {
            request.setAttribute("emptyOrderMessage",
                    LabelManager.getProperty("message.empty.order"));
            //корзина пуста
        }

        return ConfigurationManager.getProperty("path.page.my.order");
    }
}
