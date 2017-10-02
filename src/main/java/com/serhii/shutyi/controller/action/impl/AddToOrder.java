package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.service.GoodsService;
import com.serhii.shutyi.service.OrdersService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AddToOrder implements Action {
    private OrdersService ordersService;

    public AddToOrder() {
        this.ordersService = OrdersService.getInstance();
    }

    public AddToOrder(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("locale");
        Order order = (Order) request.getSession().getAttribute("order");
        int goodId = Integer.parseInt(request.getParameter("goodId"));
        int orderedQuantity = Integer.parseInt(request.getParameter("ordered_quantity"));

        ordersService.addGoodToOrder(order, goodId, orderedQuantity);

        request.setAttribute("successAddToCart",
                LabelManager.getProperty("message.success.add.to.cart", locale));

        return ConfigurationManager.getProperty("path.page.buy.now");
    }
}
