package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;

import javax.servlet.http.HttpServletRequest;

public class DeleteGood implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        int goodId = Integer.parseInt(request.getParameter("goodId"));

        deleteGoodById(goodId);

        return ConfigurationManager.getProperty("path.page.main");
    }

    private void deleteGoodById(int goodId) {
        try (GoodDAO goodDAO = DaoFactory.getInstance().createGoodDAO()) {
            goodDAO.delete(goodId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
