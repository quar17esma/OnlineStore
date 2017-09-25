package com.serhii.shutyi.dao.impl;


import com.serhii.shutyi.dao.*;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    final static Logger logger = Logger.getLogger(JDBCDaoFactory.class);

    Connection getConnection() {
        Config config = Config.getInstance();
        Connection connection;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(config.getUrl(), config.getProperties());
        } catch (SQLException e) {
            logger.error("Fail to get connection", e);
            throw new RuntimeException(e);
        }

        return connection;
    }

    @Override
    public ClientDAO createClientDAO() {
        return new JDBCClientDAO(getConnection());
    }

    @Override
    public UserDAO createUserDAO() {
        return new JDBCUserDAO(getConnection());
    }

    @Override
    public GoodDAO createGoodDAO() {
        return new JDBCGoodDAO(getConnection());
    }

    @Override
    public OrderDAO createOrderDAO() {
        return new JDBCOrderDAO(getConnection());
    }
}
