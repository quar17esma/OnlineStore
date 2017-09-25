package com.serhii.shutyi.dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    final static Logger logger = Logger.getLogger(ConnectionPool.class);

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

    public ConnectionPool() {}

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Fail to get connection", e);
            throw new RuntimeException(e);
        }
        return connection;
    }
}
