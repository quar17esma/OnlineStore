package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.service.GoodsService;

import javax.servlet.http.HttpServletRequest;

public class DeleteGood implements Action {
    private GoodsService goodsService = GoodsService.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        int goodId = Integer.parseInt(request.getParameter("goodId"));

        goodsService.deleteGoodById(goodId);

        request.setAttribute("successDeleteGoodMessage", LabelManager.getProperty("message.success.delete.good"));

        return ConfigurationManager.getProperty("path.page.goods");
    }


}
