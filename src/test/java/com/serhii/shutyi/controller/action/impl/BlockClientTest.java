package com.serhii.shutyi.controller.action.impl;

import com.serhii.shutyi.service.impl.ClientsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BlockClientTest {
    @Mock
    private ClientsService clientsService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    @InjectMocks
    private BlockClient blockClient;

    @Test
    public void callClientsService() throws Exception {
        String clientIdString = "3";
        int clientId = Integer.parseInt(clientIdString);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("clientId")).thenReturn(clientIdString);
        when(session.getAttribute("locale")).thenReturn("en_US");

        blockClient.execute(request);

        verify(clientsService).blockClientById(clientId);
    }

    @Test
    public void notCallClientsServiceClientIdNull() throws Exception {
        String clientIdString = null;

        when(request.getParameter("clientId")).thenReturn(clientIdString);
        when(request.getSession()).thenReturn(session);

        blockClient.execute(request);

        verify(clientsService, never()).blockClientById(anyInt());
    }

    @Test
    public void notCallClientsServiceClientIdEmpty() throws Exception {
        String clientIdString = "";

        when(request.getParameter("clientId")).thenReturn(clientIdString);
        when(request.getSession()).thenReturn(session);

        blockClient.execute(request);

        verify(clientsService, never()).blockClientById(anyInt());
    }

    @Test
    public void setRequestAttributeSuccessBlockClient() throws Exception {
        when(request.getParameter("clientId")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("locale")).thenReturn("en_US");

        blockClient.execute(request);

        verify(request).setAttribute(matches("successBlockClient"), anyString());
    }

}