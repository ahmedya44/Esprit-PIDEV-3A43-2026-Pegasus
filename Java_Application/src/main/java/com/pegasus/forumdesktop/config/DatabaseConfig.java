package com.pegasus.forumdesktop.config;

import com.pegasus.config.EnvLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConfig {
    private static final String DEFAULT_URL = "jdbc:mysql://127.0.0.1:3306/pegasus?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        String url = value("PEGASUS_DB_URL", DEFAULT_URL);
        String user = value("PEGASUS_DB_USER", DEFAULT_USER);
        String password = value("PEGASUS_DB_PASSWORD", DEFAULT_PASSWORD);
        return DriverManager.getConnection(url, user, password);
    }

    private static String value(String name, String fallback) {
        return EnvLoader.getOrDefault(name, fallback);
    }
}
