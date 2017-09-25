package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.service.AddToOrderService;
import com.serhii.shutyi.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AddToOrder implements Action {

    @Override
    public String execute(HttpServletRequest request) {
        Order order = (Order) request.getSession().getAttribute("order");
        int goodId = Integer.parseInt(request.getParameter("goodId"));
        int orderedQuantity = Integer.parseInt(request.getParameter("ordered_quantity"));

        AddToOrderService.getInstance().addGoodToOrder(order, goodId, orderedQuantity);
        List<Good> goods = LoginService.getInstance().getAllGoods();

        request.setAttribute("goods", goods);

        return ConfigurationManager.getProperty("path.page.main");
    }
}
