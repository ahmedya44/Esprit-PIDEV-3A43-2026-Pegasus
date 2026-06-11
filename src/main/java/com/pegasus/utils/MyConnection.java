package com.pegasus.utils;

import com.pegasus.config.EnvLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    private static final String URL = EnvLoader.getOrDefault("PEGASUS_DB_URL", buildDefaultUrl());
    private static final String USER = EnvLoader.getOrDefault("PEGASUS_DB_USER", "root");
    private static final String PASSWORD = EnvLoader.getOrDefault("PEGASUS_DB_PASSWORD", "");

    public static Connection getConnection() {
        try {
            // fail fast if DB is unreachable to avoid blocking the JavaFX thread
            DriverManager.setLoginTimeout(5); // seconds
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("DB ERROR: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static String buildDefaultUrl() {
        String host = EnvLoader.getOrDefault("PEGASUS_DB_HOST", "localhost");
        String port = EnvLoader.getOrDefault("PEGASUS_DB_PORT", "3306");
        String database = EnvLoader.getOrDefault("PEGASUS_DB_NAME", "pegasus_new");
        return "jdbc:mysql://" + host + ":" + port + "/" + database
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
            + "&connectTimeout=5000&socketTimeout=5000";
    }
}
