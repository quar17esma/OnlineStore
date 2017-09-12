package com.serhii.shutyi.dao;

import com.serhii.shutyi.Config;

public abstract class DaoFactory {
    public abstract ClientDAO createClientDAO();
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
