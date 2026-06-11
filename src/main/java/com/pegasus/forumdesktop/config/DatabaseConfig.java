package com.pegasus.forumdesktop.config;

import com.pegasus.config.EnvLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConfig {
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        String url = value("PEGASUS_DB_URL", buildDefaultUrl());
        String user = value("PEGASUS_DB_USER", DEFAULT_USER);
        String password = value("PEGASUS_DB_PASSWORD", DEFAULT_PASSWORD);
        return DriverManager.getConnection(url, user, password);
    }

    private static String buildDefaultUrl() {
        String host = value("PEGASUS_DB_HOST", "127.0.0.1");
        String port = value("PEGASUS_DB_PORT", "3306");
        String database = value("PEGASUS_DB_NAME", "pegasus_new");
        return "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    }

    private static String value(String name, String fallback) {
        return EnvLoader.getOrDefault(name, fallback);
    }
}
