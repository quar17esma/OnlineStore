package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.action.Action;
import com.serhii.shutyi.controller.manager.ConfigurationManager;
import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.exceptions.NotEnoughGoodQuantity;
import com.serhii.shutyi.service.IOrdersService;
import com.serhii.shutyi.service.impl.OrdersService;

import javax.servlet.http.HttpServletRequest;

public class SendOrder implements Action {
    private IOrdersService ordersService;

    public SendOrder() {
        this.ordersService = OrdersService.getInstance();
    }

    public SendOrder(IOrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String locale = (String) request.getSession().getAttribute("locale");
        Order order = (Order) request.getSession().getAttribute("order");

        if (order.getClient().isInBlackList()) {
            request.setAttribute("errorUserBlockedMessage",
                    LabelManager.getProperty("message.error.user.blocked", locale));
            page = ConfigurationManager.getProperty("path.page.cart");

            return page;
        }

        if (order != null) {
            try {
                ordersService.sendOrder(order);

                Order emptyOrder = new Order.Builder()
                        .setClient(order.getClient())
                        .build();
                request.getSession().setAttribute("order", emptyOrder);

                request.setAttribute("successSendOrderMessage",
                        LabelManager.getProperty("message.success.send.order", locale));
                page = ConfigurationManager.getProperty("path.page.goods");
            } catch (NotEnoughGoodQuantity e) {
                request.setAttribute("errorSendOrderMessage",
                        LabelManager.getProperty("message.error.send.order", locale));
                request.setAttribute("errorGood", e.getGood());
                page = ConfigurationManager.getProperty("path.page.cart");
            }
        }

        return page;
    }
}
