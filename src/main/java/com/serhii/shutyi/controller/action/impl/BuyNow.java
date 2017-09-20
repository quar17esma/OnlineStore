package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.model.entity.Good;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class BuyNow implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.buy.now");

        int goodId = Integer.parseInt(request.getParameter("goodId"));

        DaoFactory daoFactory = DaoFactory.getInstance();
        try(GoodDAO goodDAO = daoFactory.createGoodDAO()) {
            Optional<Good> good = goodDAO.findById(goodId);
            request.setAttribute("good", good.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return page;
    }
}
