package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.model.entity.Good;
import com.serhii.shutyi.model.service.AddGoodService;

import javax.servlet.http.HttpServletRequest;

public class AddGood implements Action {
    @Override
    public String execute(HttpServletRequest request) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Good good = new Good.Builder()
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setQuantity(quantity)
                .build();

        AddGoodService.getInstance().addGood(good);

        return ConfigurationManager.getProperty("/pages/main.jsp");
    }
}
