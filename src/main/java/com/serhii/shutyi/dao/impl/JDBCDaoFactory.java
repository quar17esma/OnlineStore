package com.serhii.shutyi.dao.impl;


import com.serhii.shutyi.dao.*;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public ClientDAO createClientDAO(Connection connection) {
        return new JDBCClientDAO(connection);
    }

    @Override
    public UserDAO createUserDAO(Connection connection) {
        return new JDBCUserDAO(connection);
    }

    @Override
    public GoodDAO createGoodDAO(Connection connection) {
        return new JDBCGoodDAO(connection);
    }

    @Override
    public OrderDAO createOrderDAO(Connection connection) {
        return new JDBCOrderDAO(connection);
    }
}
