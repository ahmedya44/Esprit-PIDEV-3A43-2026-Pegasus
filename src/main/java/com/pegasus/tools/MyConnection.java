package com.pegasus.tools;

import com.pegasus.config.EnvLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    Connection connection;

    private static final String USERNAME = EnvLoader.getOrDefault("PEGASUS_DB_USER", "root");
    private static final String PASSWORD = EnvLoader.getOrDefault("PEGASUS_DB_PASSWORD", "");
    private static final String URL = EnvLoader.getOrDefault("PEGASUS_DB_URL", buildDefaultUrl());

    private static MyConnection instance;

    private MyConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connexion Etablie!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static MyConnection getInstance() {
        if (instance == null) {
            instance = new MyConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private static String buildDefaultUrl() {
        String host = EnvLoader.getOrDefault("PEGASUS_DB_HOST", "localhost");
        String port = EnvLoader.getOrDefault("PEGASUS_DB_PORT", "3306");
        String database = EnvLoader.getOrDefault("PEGASUS_DB_NAME", "pegasus");
        return "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }
}

