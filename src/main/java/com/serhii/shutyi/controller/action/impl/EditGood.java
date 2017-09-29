package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.service.GoodsService;

import javax.servlet.http.HttpServletRequest;

public class EditGood implements Action {
    private GoodsService goodsService = GoodsService.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String goodIdString = request.getParameter("goodId");

        if (goodIdString != null) {
            int goodId = Integer.parseInt(goodIdString);

            Good good = goodsService.getGoodById(goodId);

            request.setAttribute("good", good);
        }

        return ConfigurationManager.getProperty("path.page.edit.good");
    }
}
