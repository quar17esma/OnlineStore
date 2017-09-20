package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.model.entity.Client;
import com.serhii.shutyi.model.entity.Good;
import com.serhii.shutyi.model.entity.Order;
import com.serhii.shutyi.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AddToOrder implements Action{
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.main");

        int goodId = Integer.parseInt(request.getParameter("goodId"));
        int orderedQuantity = Integer.parseInt(request.getParameter("ordered_quantity"));

        Optional<Good> good = Optional.empty();

        DaoFactory daoFactory = DaoFactory.getInstance();
        try(GoodDAO goodDAO = daoFactory.createGoodDAO()) {
            good = goodDAO.findById(goodId);
            good.get().setQuantity(orderedQuantity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Order order = (Order) request.getSession().getAttribute("order");
        if (order == null) {
            order = new Order();
            request.setAttribute("order", order);
        }
        order.getGoods().add(good.get());

        Optional<Client> client = Optional.empty();
        DaoFactory daoFactory1 = DaoFactory.getInstance();
        try (ClientDAO clientDAO = daoFactory1.createClientDAO()) {
            client = clientDAO.findById((Integer) request.getSession().getAttribute("clientId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        order.setClient(client.get());
        System.out.println(order.toString());
        return page;
    }
}
