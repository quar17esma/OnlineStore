package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.model.entity.Client;
import com.serhii.shutyi.model.entity.Good;
import com.serhii.shutyi.model.entity.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class AddToOrder implements Action{
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.main");

        int goodId = Integer.parseInt(request.getParameter("goodId"));
        int orderedQuantity = Integer.parseInt(request.getParameter("ordered_quantity"));

        Optional<Good> good = getGoodById(goodId);
        good.get().setQuantity(orderedQuantity);

        addGoodToOrder(request, good);

        request.setAttribute("goods", getAllGoods());

        return page;
    }

    private void addGoodToOrder(HttpServletRequest request, Optional<Good> good) {
        Order order = (Order) request.getSession().getAttribute("order");
        if (order == null) {
            order = makeOrder(request);
        }
        order.getGoods().add(good.get());
    }

    private Order makeOrder(HttpServletRequest request) {
        Order order = new Order();

        int clientId = (int) request.getSession().getAttribute("clientId");
        Optional<Client> client = getClientById(clientId);
        order.setClient(client.get());

        request.getSession().setAttribute("order", order);

        return order;
    }

    private Optional<Good> getGoodById(int goodId) {
        Optional<Good> good = Optional.empty();

        try(GoodDAO goodDAO = DaoFactory.getInstance().createGoodDAO()) {
            good = goodDAO.findById(goodId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return good;
    }

    private Optional<Client> getClientById(int clientId) {
        Optional<Client> client = Optional.empty();

        try (ClientDAO clientDAO = DaoFactory.getInstance().createClientDAO()) {
            client = clientDAO.findById(clientId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }

    private List<Good> getAllGoods() {
        List<Good> goods = null;

        try (GoodDAO goodDAO = DaoFactory.getInstance().createGoodDAO()) {
            goods = goodDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
    }
}
