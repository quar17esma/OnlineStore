package com.serhii.shutyi.dao;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigDaoFactory {
    final static Logger logger = Logger.getLogger(ConfigDaoFactory.class);

    private String factoryClassName;

    public ConfigDaoFactory() {
        load();
    }

    private static class Holder {
        private static ConfigDaoFactory INSTANCE = new ConfigDaoFactory();
    }

    public static ConfigDaoFactory getInstance() {
        return ConfigDaoFactory.Holder.INSTANCE;
    }

    private void load() {
        try (InputStream in = this.getClass().getResourceAsStream("/db.properties")) {
            Properties dbProperties = new Properties();
            dbProperties.load(in);

            factoryClassName = dbProperties.getProperty("db.factory.class");
        } catch (IOException e) {
            logger.error("Fail to load DB config dao factory", e);
            throw new RuntimeException(e);
        }
    }

    public String getFactoryClassName() {
        return factoryClassName;
    }
}
