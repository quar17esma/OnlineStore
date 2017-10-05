package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.service.GoodsService;

import javax.servlet.http.HttpServletRequest;

public class AddToOrder implements Action {
    private GoodsService goodsService;

    public AddToOrder() {
        this.goodsService = GoodsService.getInstance();
    }

    public AddToOrder(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("locale");
        Order order = (Order) request.getSession().getAttribute("order");
        int goodId = Integer.parseInt(request.getParameter("goodId"));
        int orderedQuantity = Integer.parseInt(request.getParameter("ordered_quantity"));

        goodsService.addGoodToOrder(order, goodId, orderedQuantity);

        request.setAttribute("successAddToCart",
                LabelManager.getProperty("message.success.add.to.cart", locale));

        return ConfigurationManager.getProperty("path.page.buy.now");
    }
}
