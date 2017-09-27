package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.service.LoginService;
import com.serhii.shutyi.service.ShowGoodsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowGoods implements Action {
    @Override
    public String execute(HttpServletRequest request) {

        List<Good> goods = ShowGoodsService.getInstance().getAllGoods();

        request.setAttribute("goods", goods);

        return ConfigurationManager.getProperty("path.page.goods");
    }
}
