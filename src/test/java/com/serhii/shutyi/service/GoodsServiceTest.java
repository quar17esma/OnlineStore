package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.entity.Good;
import com.serhii.shutyi.entity.Order;
import com.serhii.shutyi.service.impl.GoodsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GoodsServiceTest {
    @Mock
    private DaoFactory factory;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private Connection connection;
    @Mock
    private GoodDAO goodDAO;

    @InjectMocks
    private GoodsService goodsService;

    @Before
    public void setUp() {
        when(connectionPool.getConnection()).thenReturn(connection);
        when(factory.createGoodDAO(connection)).thenReturn(goodDAO);
    }

    @Test
    public void getAllGoods() throws Exception {
        goodsService.getAllGoods();
        verify(connectionPool).getConnection();
        verify(factory).createGoodDAO(connection);
        verify(goodDAO).findAll();
    }

    @Test
    public void getGoodById() throws Exception {
        int goodId = 3;
        Good good = mock(Good.class);
        when(goodDAO.findById(goodId)).thenReturn(Optional.of(good));

        Good resultGood = goodsService.getGoodById(goodId);

        verify(connectionPool).getConnection();
        verify(factory).createGoodDAO(connection);
        verify(goodDAO).findById(goodId);

        assertEquals(good, resultGood);
    }

    @Test(expected = RuntimeException.class)
    public void getGoodByIdException() throws Exception {
        int goodId = 3;
        when(goodDAO.findById(goodId)).thenReturn(Optional.empty());

        goodsService.getGoodById(goodId);

        verify(connectionPool).getConnection();
        verify(factory).createGoodDAO(connection);
        verify(goodDAO).findById(goodId);
    }

    @Test
    public void deleteGoodById() throws Exception {
        int goodId = 3;

        goodsService.deleteGoodById(goodId);

        verify(connectionPool).getConnection();
        verify(factory).createGoodDAO(connection);
        verify(goodDAO).delete(goodId);
    }

    @Test
    public void addGood() throws Exception {
        Good good = mock(Good.class);

        goodsService.addGood(good);

        verify(connectionPool).getConnection();
        verify(factory).createGoodDAO(connection);
        verify(goodDAO).insert(good);
    }

    @Test
    public void updateGood() throws Exception {
        Good good = mock(Good.class);

        goodsService.updateGood(good);

        verify(connectionPool).getConnection();
        verify(factory).createGoodDAO(connection);
        verify(goodDAO).update(good);
    }

    @Test
    public void addGoodToOrder() throws Exception {
        Order order = mock(Order.class);
        Good good = mock(Good.class);
        List<Good> goods = new ArrayList<>();
        int goodId = 3;
        int orderedQuantity = 5;
        GoodsService goodsServiceSpy = Mockito.spy(goodsService);
        doReturn(good).when(goodsServiceSpy).getGoodById(goodId);
        when(order.getGoods()).thenReturn(goods);

        goodsServiceSpy.addGoodToOrder(order, goodId, orderedQuantity);

        verify(goodsServiceSpy).getGoodById(goodId);
        verify(good).setQuantity(orderedQuantity);

        assertTrue(order.getGoods().contains(good));
    }
}