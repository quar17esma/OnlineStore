package com.serhii.shutyi.dao.impl;


import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.Config;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    Connection getConnection() {
        Config config = Config.getInstance();
        Connection connection;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(config.getUrl(), config.getProperties());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    @Override
    public ClientDAO createClientDAO() {
        return new JDBCClientDAO(getConnection());
    }

    @Override
    public GoodDAO createGoodDAO() {
        return new JDBCGoodDAO(getConnection());
    }
}
