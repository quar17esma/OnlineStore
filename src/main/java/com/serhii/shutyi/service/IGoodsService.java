package com.serhii.shutyi.service;

import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;

import java.util.List;

public interface IGoodsService {

    List<Good> getAllGoods();

    List<Good> getGoodsByPage(int page, int goodsOnPage);

    int getAllGoodsQuantity();

    Good getGoodById(int goodId);

    void deleteGoodById(int goodId);

    void addGood(Good good);

    void updateGood(Good good);

    void addGoodToOrder(Order order, int goodId, int orderedQuantity);
}
