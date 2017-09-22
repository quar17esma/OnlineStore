package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.model.entity.Good;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class EditGood implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        String goodIdString = null;

        if ((goodIdString = request.getParameter("goodId")) != null) {
             int goodId = Integer.parseInt(goodIdString);
            try (GoodDAO goodDAO = DaoFactory.getInstance().createGoodDAO()) {
                Optional<Good> good = goodDAO.findById(goodId);
                request.setAttribute("good", good.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ConfigurationManager.getProperty("path.page.edit.good");
    }
}
