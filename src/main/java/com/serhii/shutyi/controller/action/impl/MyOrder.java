package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.model.entity.Order;

import javax.servlet.http.HttpServletRequest;

public class MyOrder implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.my.order");

        Order order = (Order) request.getSession().getAttribute("order");
        if (order != null) {
            request.setAttribute("goods", order.getGoods());
        } else {
            //корзина пуста
        }

        return page;
    }
}
