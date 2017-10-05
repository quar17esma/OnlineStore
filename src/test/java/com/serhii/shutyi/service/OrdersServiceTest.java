package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.dao.OrderDAO;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.enums.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrdersServiceTest {
    @Mock
    private DaoFactory factory;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private Connection connection;
    @Mock
    private OrderDAO orderDAO;
    @Mock
    private GoodDAO goodDAO;

    @InjectMocks
    private OrdersService ordersService;

    @Before
    public void setUp() {
        when(connectionPool.getConnection()).thenReturn(connection);
        when(factory.createOrderDAO(connection)).thenReturn(orderDAO);
        when(factory.createGoodDAO(connection)).thenReturn(goodDAO);
    }

    @Test
    public void getOrdersByClientIdCorrect() throws Exception {
        List<Order> orders = new ArrayList<>();
        Order order = mock(Order.class);
        when(order.getId()).thenReturn(3);
        orders.add(order);
        when(orderDAO.findAllByClientId(anyInt())).thenReturn(orders);
        List<Good> goods = new ArrayList<>();
        when(goodDAO.findByOrderId(order.getId())).thenReturn(goods);

        ordersService.getOrdersByClientId(1);

        verify(connectionPool).getConnection();
        verify(factory).createOrderDAO(connection);
        verify(factory).createGoodDAO(connection);
        verify(connection).setAutoCommit(false);
        verify(orderDAO).findAllByClientId(1);
        verify(goodDAO).findByOrderId(3);
        verify(order, times(1)).setGoods(goods);
        verify(connection).commit();
    }

    @Test
    public void getOrdersByClientIdEmptyOrders() throws Exception {
        List<Order> orders = new ArrayList<>();
        when(orderDAO.findAllByClientId(anyInt())).thenReturn(orders);

        List<Order> resultOrders = ordersService.getOrdersByClientId(1);

        verify(connectionPool).getConnection();
        verify(factory).createOrderDAO(connection);
        verify(factory).createGoodDAO(connection);
        verify(connection).setAutoCommit(false);
        verify(orderDAO).findAllByClientId(1);
        verify(goodDAO, never()).findByOrderId(anyInt());
        verify(connection).commit();

        assertTrue(resultOrders.isEmpty());
    }

    @Test
    public void payOrderCorrect() throws Exception {
        int orderId = 3;
        Order order = mock(Order.class);
        when(orderDAO.findById(anyInt())).thenReturn(Optional.of(order));

        boolean result = ordersService.payOrder(orderId);

        verify(connectionPool).getConnection();
        verify(factory).createOrderDAO(connection);
        verify(connection).setAutoCommit(false);
        verify(orderDAO).findById(orderId);
        verify(order).setStatus(OrderStatus.PAID);
        verify(orderDAO).update(order);
        verify(connection).commit();

        assertTrue(result);
    }

    @Test
    public void payOrderNoOrderById() throws Exception {
        int orderId = 3;
        Optional<Order> order = Optional.empty();
        when(orderDAO.findById(anyInt())).thenReturn(order);

        boolean result = ordersService.payOrder(orderId);

        verify(connectionPool).getConnection();
        verify(factory).createOrderDAO(connection);
        verify(connection).setAutoCommit(false);
        verify(orderDAO).findById(orderId);
        verify(orderDAO, never()).update(any());
        verify(connection, never()).commit();

        assertFalse(result);
    }

    @Test
    public void sendOrderCorrect() throws Exception {
        Order order = mock(Order.class);

        ordersService.sendOrder(order);

        verify(connectionPool).getConnection();
        verify(factory).createOrderDAO(connection);
        verify(factory).createGoodDAO(connection);
        verify(connection).setAutoCommit(false);
        verify(order).setOrderedAt(any(LocalDateTime.class));
        verify(orderDAO).insert(order);
        verify(connection).commit();
    }
}