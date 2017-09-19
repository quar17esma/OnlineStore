package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.model.entity.Good;
import com.serhii.shutyi.model.service.LoginChecker;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Login implements Action {

        @Override
        public String execute(HttpServletRequest request) {
            String page = null;

            String login = request.getParameter("login");
            String pass = request.getParameter("password");

            if (LoginChecker.checkLogin(login, pass)) {
                request.setAttribute("user", login);

                List<Good> goods = null;
                DaoFactory daoFactory = DaoFactory.getInstance();
                try(GoodDAO goodDAO = daoFactory.createGoodDAO()) {
                    goods = goodDAO.findAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                request.setAttribute("goods", goods);

                page = ConfigurationManager.getProperty("path.page.main");
            } else {
                request.setAttribute("errorLoginPassMessage", LabelManager.getProperty("message.login.error"));
                page = ConfigurationManager.getProperty("path.page.login");
            }

            return page;
        }
}