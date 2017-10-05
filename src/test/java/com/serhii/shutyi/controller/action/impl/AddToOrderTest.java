package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.service.GoodsService;
import com.serhii.shutyi.service.OrdersService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class AddToOrderTest {
    @Mock
    private GoodsService goodsService;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AddToOrder addToOrder;

    @Test
    public void execute() throws Exception {

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("order")).thenReturn(new Order());
        when(session.getAttribute("locale")).thenReturn("en_US");
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("goodId")).thenReturn("5");
        when(request.getParameter("ordered_quantity")).thenReturn("3");


        addToOrder.execute(request);


        verify(goodsService).addGoodToOrder(any(Order.class), anyInt(), anyInt());
    }

}