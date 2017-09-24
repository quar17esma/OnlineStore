package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.entity.Order;

import java.time.LocalDateTime;

public class SendOrderService {
    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static SendOrderService INSTANCE = new SendOrderService();
    }

    public static SendOrderService getInstance() {
        return SendOrderService.Holder.INSTANCE;
    }

    public void sendOrder(Order order) {
        try (OrderDAO orderDAO = factory.createOrderDAO()) {
            order.setOrderedAt(LocalDateTime.now());
            orderDAO.insert(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
