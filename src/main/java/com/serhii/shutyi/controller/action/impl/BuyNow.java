package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.service.GoodsService;

import javax.servlet.http.HttpServletRequest;

public class BuyNow implements Action {
    private GoodsService goodsService;

    public BuyNow() {
        this.goodsService = GoodsService.getInstance();
    }

    public BuyNow(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int goodId = Integer.parseInt(request.getParameter("goodId"));

        Good good = goodsService.getGoodById(goodId);

        request.setAttribute("good", good);

        return ConfigurationManager.getProperty("path.page.buy.now");
    }
}
