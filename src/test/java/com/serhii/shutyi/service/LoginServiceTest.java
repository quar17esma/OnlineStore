package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.entity.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@Ignore
@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionPool.class)
public class LoginServiceTest {
    @Mock
    private DaoFactory factory;
    @Mock
    private ClientsService clientsService;

    @InjectMocks
    private LoginService loginService;

    @Test
    public void checkLoginCorrectDataUserExists() throws Exception {
        String login = "john@gmail.com";
        String password = "john";

        Optional<User> user = Optional.of(new User.Builder()
                .setPassword("john")
                .build());

        UserDAO userDAO = mock(UserDAO.class);
        when(userDAO.findByEmail(login)).thenReturn(user);

        when(factory.createUserDAO(any(Connection.class))).thenReturn(userDAO);
//        when(clientsService.getClientByEmail(login)).thenReturn()

        Connection connection = mock(Connection.class);
        PowerMockito.mockStatic(ConnectionPool.class);
        when(ConnectionPool.getConnection()).thenReturn(connection);


        boolean result = loginService.checkLogin(login, password);


        assertTrue(result);
    }

    @Test
    public void checkLoginReturnFalsePasswordNull() throws Exception {
        String login = "john@gmail.com";
        String password = null;

        boolean result = loginService.checkLogin(login, password);

        assertFalse(result);
    }

    @Test
    public void checkLoginReturnFalseLoginNull() throws Exception {
        String login = null;
        String password = "john";

        boolean result = loginService.checkLogin(login, password);

        assertFalse(result);
    }
}