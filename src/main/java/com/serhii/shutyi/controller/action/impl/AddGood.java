package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.checker.InputGoodChecker;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.service.GoodsService;

import javax.servlet.http.HttpServletRequest;

public class AddGood implements Action {
    private GoodsService goodsService;
    private InputGoodChecker checker;

    public AddGood() {
        this.goodsService = GoodsService.getInstance();
        this.checker = new InputGoodChecker();
    }

    public AddGood(GoodsService goodsService, InputGoodChecker checker) {
        this.goodsService = goodsService;
        this.checker = checker;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        String goodIdString =  request.getParameter("goodId");
        String name = request.getParameter("name").trim();
        String description = request.getParameter("description").trim();
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        boolean isDataCorrect = checkInputData(name, description, price, quantity);

        if (isDataCorrect) {
            Good good = makeGood(name, description, price, quantity);

            if (goodIdString != null && !goodIdString.isEmpty()) {
                int goodId = Integer.parseInt(goodIdString);
                good.setId(goodId);
                goodsService.updateGood(good);
            } else {
                goodsService.addGood(good);
            }

            request.setAttribute("successAddGoodMessage",
                    LabelManager.getProperty("message.success.add.good", locale));
            page = ConfigurationManager.getProperty("path.page.goods");
        } else {
            request.setAttribute("errorAddGoodMessage",
                    LabelManager.getProperty("message.error.wrong.data", locale));
            page = ConfigurationManager.getProperty("path.page.edit.good");
        }

        return page;
    }

    private boolean checkInputData(String name, String description, int price, int quantity) {
        return checker.isInputDataCorrect(name, description, price, quantity);
    }

    private Good makeGood(String name, String description, int price, int quantity) {
        return new Good.Builder()
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setQuantity(quantity)
                .build();
    }
}
