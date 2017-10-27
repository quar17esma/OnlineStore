package com.serhii.shutyi.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;

public abstract class DaoFactory {
    private static final Logger LOGGER = Logger.getLogger(DaoFactory.class);

    public abstract ClientDAO createClientDAO(Connection connection);
    public abstract UserDAO createUserDAO(Connection connection);
    public abstract GoodDAO createGoodDAO(Connection connection);
    public abstract OrderDAO createOrderDAO(Connection connection);

    public static DaoFactory getInstance(){
        String className = ConfigDaoFactory.getInstance().getFactoryClassName();
        DaoFactory factory = null;
        try {
            factory = (DaoFactory) Class.forName(className).newInstance();
        } catch (Exception e) {
            LOGGER.error("Fail to get DaoFactory", e);
            throw new RuntimeException(e);
        }
        return factory;
    }
}
