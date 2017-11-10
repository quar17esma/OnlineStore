package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.service.impl.GoodsService;
import com.serhii.shutyi.service.IGoodsService;

import javax.servlet.http.HttpServletRequest;

public class DeleteGood implements Action {
    private IGoodsService goodsService;

    public DeleteGood() {
        this.goodsService = GoodsService.getInstance();
    }

    public DeleteGood(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("locale");
        int goodId = Integer.parseInt(request.getParameter("goodId"));

        goodsService.deleteGoodById(goodId);

        request.setAttribute("successDeleteGoodMessage",
                LabelManager.getProperty("message.success.delete.good", locale));

        return ConfigurationManager.getProperty("path.page.goods");
    }


}
