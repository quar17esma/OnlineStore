package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;

public abstract class Service {
    protected DaoFactory factory;
    protected ConnectionPool connectionPool;

    public Service(DaoFactory factory, ConnectionPool connectionPool) {
        this.factory = factory;
        this.connectionPool = connectionPool;
    }
}
