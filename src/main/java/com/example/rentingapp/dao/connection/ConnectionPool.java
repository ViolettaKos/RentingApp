package com.example.rentingapp.dao.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionPool {

    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);
    private static final String URL = "jdbc.url";
    private static final String USER = "user";
    private static final String PASS = "password";
    private static final String DRIVER = "db.driver";
    private static final String FILE = "app.properties";

    private static DataSource dataSource;
    private ConnectionPool(){}
    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            LOG.trace("Null datasource");
            Properties properties = new Properties();
            InputStream stream = ConnectionPool.class.getClassLoader().getResourceAsStream(FILE);
            try {
                properties.load(stream);
            } catch (IOException e) {
                LOG.error("Error while loading properties");
            }
            dataSource = new HikariDataSource(getHikariConfig(properties));
            LOG.trace("Connection done!");
        }
        return dataSource;
    }


    private static HikariConfig getHikariConfig(Properties properties) {
        HikariConfig hikaConfig = new HikariConfig();
        hikaConfig.setJdbcUrl(properties.getProperty(URL));
        hikaConfig.setUsername(properties.getProperty(USER));
        hikaConfig.setPassword(properties.getProperty(PASS));
        hikaConfig.setDriverClassName(properties.getProperty(DRIVER));
        hikaConfig.setDataSourceProperties(properties);
        return hikaConfig;
    }
}
