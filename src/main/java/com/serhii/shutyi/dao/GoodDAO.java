package com.serhii.shutyi.dao;

import com.serhii.shutyi.entity.Good;

import java.util.List;

public interface GoodDAO extends GenericDAO<Good> {
    List<Good> findByOrderId(int orderId);
}
