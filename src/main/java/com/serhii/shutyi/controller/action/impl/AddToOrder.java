package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.model.entity.Client;
import com.serhii.shutyi.model.entity.Good;
import com.serhii.shutyi.model.entity.Order;
import com.serhii.shutyi.model.service.AddToOrderService;
import com.serhii.shutyi.model.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class AddToOrder implements Action{

//    DaoFactory factory;// в сервис
//
//    public AddToOrder(DaoFactory factory) {
//        this.factory = factory;
//    }

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
