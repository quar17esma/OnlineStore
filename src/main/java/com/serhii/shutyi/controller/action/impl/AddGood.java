package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.checker.InputGoodChecker;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.service.AddGoodService;

import javax.servlet.http.HttpServletRequest;

public class AddGood implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String name = request.getParameter("name").trim();
        String description = request.getParameter("description").trim();
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        InputGoodChecker checker = new InputGoodChecker();
        boolean isDataCorrect = checker.isInputDataCorrect(name, description, price, quantity);

        if (isDataCorrect) {

            Good good = new Good.Builder()
                    .setName(name)
                    .setDescription(description)
                    .setPrice(price)
                    .setQuantity(quantity)
                    .build();

            AddGoodService.getInstance().addGood(good);

            request.setAttribute("successAddGoodMessage", LabelManager.getProperty("message.success.add.good"));

            page = ConfigurationManager.getProperty("path.page.goods");
        } else {
            request.setAttribute("errorAddGoodMessage", LabelManager.getProperty("message.error.wrong.data"));

            page = ConfigurationManager.getProperty("path.page.edit.good");
        }
        return page;
    }
}
