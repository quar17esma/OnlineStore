package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.controller.manager.LabelManager;
import com.serhii.shutyi.service.ClientsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BlockClientTest {
    @Mock
    private ClientsService clientsService;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private BlockClient blockClient;

    @Test
    public void callClientsService() throws Exception {
        String clientIdString = "1";
        int clientId = Integer.parseInt(clientIdString);
        when(request.getParameter("clientId")).thenReturn(clientIdString);

        blockClient.execute(request);

        verify(clientsService).blockClientById(clientId);
    }

    @Test
    public void notCallClientsServiceClientIdNull() throws Exception {
        String clientIdString = null;

        when(request.getParameter("clientId")).thenReturn(clientIdString);

        blockClient.execute(request);

        verify(clientsService, never()).blockClientById(anyInt());
    }

    @Test
    public void notCallClientsServiceClientIdEmpty() throws Exception {
        String clientIdString = "";

        when(request.getParameter("clientId")).thenReturn(clientIdString);

        blockClient.execute(request);

        verify(clientsService, never()).blockClientById(anyInt());
    }

    @Test
    public void setRequestAttributeSuccessBlockClient() throws Exception {
        when(request.getParameter("clientId")).thenReturn("1");

        blockClient.execute(request);

        verify(request).setAttribute(matches("successBlockClient"), anyString());
    }

}