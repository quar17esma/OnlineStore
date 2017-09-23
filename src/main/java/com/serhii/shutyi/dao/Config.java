package com.serhii.shutyi.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private String url;
    private String factoryClassName;
    private Properties properties;

    public Config() {
        load();
    }

    private static class Holder {
        private static Config INSTANCE = new Config();
    }

    public static Config getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        try (InputStream in = this.getClass().getResourceAsStream("/database.properties")) {
            Properties databaseProperties = new Properties();
            databaseProperties.load(in);

            properties = new Properties();
            properties.put("user", databaseProperties.getProperty("db.user"));
            properties.put("password", databaseProperties.getProperty("db.pass"));
            properties.put("autoReconnect", databaseProperties.getProperty("db.autoReconnect"));
            properties.put("characterEncoding", databaseProperties.getProperty("db.characterEncoding"));
            properties.put("useUnicode", databaseProperties.getProperty("db.useUnicode"));
            properties.put("useJDBCCompliantTimezoneShift", databaseProperties.getProperty("db.useJDBCCompliantTimezoneShift"));
            properties.put("useLegacyDatetimeCode", databaseProperties.getProperty("db.useLegacyDatetimeCode"));
            properties.put("serverTimezone", databaseProperties.getProperty("db.serverTimezone"));

            url = databaseProperties.getProperty("db.url");

            factoryClassName = databaseProperties.getProperty("db.factory.class");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getFactoryClassName() {
        return factoryClassName;
    }

    public Properties getProperties() {
        return properties;
    }
}
