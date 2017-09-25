package com.serhii.shutyi.dao;

import org.apache.log4j.Logger;

public abstract class DaoFactory {
    final static Logger logger = Logger.getLogger(DaoFactory.class);

    public abstract ClientDAO createClientDAO();
    public abstract UserDAO createUserDAO();
    public abstract GoodDAO createGoodDAO();
    public abstract OrderDAO createOrderDAO();

    public static DaoFactory getInstance(){
        String className = Config.getInstance().getFactoryClassName();
        DaoFactory factory = null;
        try {
            factory = (DaoFactory) Class.forName(className).newInstance();
        } catch (Exception e) {
            logger.error("Fail to get DaoFactory", e);
            throw new RuntimeException(e);
        }
        return factory;
    }
}
