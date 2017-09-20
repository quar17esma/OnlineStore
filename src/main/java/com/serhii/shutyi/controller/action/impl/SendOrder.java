package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.model.entity.Order;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class SendOrder implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.main");

        Order order = (Order) request.getSession().getAttribute("order");

        if (order != null) {
            try (OrderDAO orderDAO = DaoFactory.getInstance().createOrderDAO()) {
                order.setOrderedAt(LocalDateTime.now());
                orderDAO.insert(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return page;
    }
}
