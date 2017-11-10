package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.service.impl.GoodsService;
import com.serhii.shutyi.service.IGoodsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowGoods implements Action {
    private static final int DEFAULT_PAGE = 1;
    private static final int GOODS_ON_PAGE = 2;
    private IGoodsService goodsService;

    public ShowGoods() {
        this.goodsService = GoodsService.getInstance();
    }

    public ShowGoods(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = setPageOrGetDefault(request.getParameter("page"));
        List<Good> goods = goodsService.getGoodsByPage(page, GOODS_ON_PAGE);

        int allGoodsQuantity = goodsService.getAllGoodsQuantity();
        int pagesQuantity = countPagesQuantity(allGoodsQuantity);

        request.setAttribute("goods", goods);
        request.setAttribute("pagesQuantity", pagesQuantity);
        return ConfigurationManager.getProperty("path.page.goods");
    }

    private int countPagesQuantity(int allGoodsQuantity){
        int pagesQuantity;

        if (allGoodsQuantity % GOODS_ON_PAGE != 0){
            pagesQuantity = allGoodsQuantity / GOODS_ON_PAGE + 1;
        } else {
            pagesQuantity = allGoodsQuantity / GOODS_ON_PAGE;
        }

        return pagesQuantity;
    }

    private int setPageOrGetDefault(String pageString){
        int page;

        if (pageString != null) {
            page = Integer.parseInt(pageString);
        } else {
            page = DEFAULT_PAGE;
        }

        return page;
    }
}
