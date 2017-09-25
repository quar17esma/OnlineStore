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
    final static Logger logger = Logger.getLogger(JDBCDaoFactory.class);

    private static final String DATASOURCE_NAME = "jdbc/online_store";
    private static DataSource dataSource;

    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
        } catch (NamingException e) {
            logger.error("Fail to initialize datasource", e);
            throw new RuntimeException(e);
        }
    }

    public JDBCDaoFactory() {}

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

//    Connection getConnection() {
//        Config config = Config.getInstance();
//        Connection connection;
//        try {
//            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//            connection = DriverManager.getConnection(config.getUrl(), config.getProperties());
//        } catch (SQLException e) {
//            logger.error("Fail to get connection", e);
//            throw new RuntimeException(e);
//        }
//
//        return connection;
//    }

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
