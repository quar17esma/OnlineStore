package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.entity.User;
import org.junit.Test;

import java.sql.Connection;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class LoginServiceTest {
    @Test
    public void checkLoginCorrectDataUserExists() throws Exception {
        String login = "john@gmail.com";
        String password = "john";

        Optional<User> user = Optional.of(new User.Builder()
                .setPassword("john")
                .build());

        UserDAO userDAO = mock(UserDAO.class);
        when(userDAO.findByEmail(login)).thenReturn(user);
        DaoFactory factory = mock(DaoFactory.class);
        when(factory.createUserDAO(any(Connection.class))).thenReturn(userDAO);
        Connection connection = mock(Connection.class);


        LoginService checker = new LoginService(factory, connection);
        boolean result = checker.checkLogin(login, password);


        assertTrue(result);
    }

    @Test
    public void checkLoginReturnFalsePasswordNull() throws Exception {
        String login = "john@gmail.com";
        String password = null;

        DaoFactory factory = mock(DaoFactory.class);
        Connection connection = mock(Connection.class);


        LoginService checker = new LoginService(factory, connection);
        boolean result = checker.checkLogin(login, password);


        assertFalse(result);
    }

    @Test
    public void checkLoginReturnFalseLoginNull() throws Exception {
        String login = null;
        String password = "john";

        DaoFactory factory = mock(DaoFactory.class);
        Connection connection = mock(Connection.class);


        LoginService checker = new LoginService(factory, connection);
        boolean result = checker.checkLogin(login, password);


        assertFalse(result);
    }
}