package com.serhii.shutyi.dao;

public abstract class DaoFactory {
    public abstract ClientDAO createClientDAO();
    public abstract UserDAO createUserDAO();
    public abstract GoodDAO createGoodDAO();

    public static DaoFactory getInstance(){
        String className = Config.getInstance().getFactoryClassName();
        DaoFactory factory = null;
        try {
            factory = (DaoFactory) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }
}
