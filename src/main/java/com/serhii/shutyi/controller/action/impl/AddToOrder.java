package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.service.GoodsService;
import com.serhii.shutyi.service.OrdersService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AddToOrder implements Action {
    private OrdersService ordersService = OrdersService.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        Order order = (Order) request.getSession().getAttribute("order");
        int goodId = Integer.parseInt(request.getParameter("goodId"));
        int orderedQuantity = Integer.parseInt(request.getParameter("ordered_quantity"));

        ordersService.addGoodToOrder(order, goodId, orderedQuantity);
        List<Good> goods = GoodsService.getInstance().getAllGoods();

        request.setAttribute("goods", goods);

        return ConfigurationManager.getProperty("path.page.goods");
    }
}
