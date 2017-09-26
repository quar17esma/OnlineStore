package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.entity.User;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
@Ignore
public class LoginCheckerTest {
    @Test
    public void checkLogin() throws Exception {
        String login = "john@gmail.com";
        String password = "john";

        LoginChecker checker = LoginChecker.getInstance();
        when(any(LoginChecker.class).getConnection()).thenReturn(mock(Connection.class));

        Optional<User> user = Optional.of(new User.Builder()
                .setPassword("john")
                .build());

        UserDAO userDAO = mock(UserDAO.class);
        when(userDAO.findByEmail(login)).thenReturn(user);

        DaoFactory factory = mock(DaoFactory.class);
        checker.factory = factory;
        when(factory.createUserDAO(any(Connection.class))).thenReturn(userDAO);


        boolean result = checker.checkLogin(login, password);


        assertTrue(result);
    }

}