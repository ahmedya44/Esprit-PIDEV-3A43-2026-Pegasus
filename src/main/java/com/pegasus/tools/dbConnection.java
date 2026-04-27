package com.pegasus.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class dbConnection {
    private static final String DB_NAME = "artwork";
    private static final String DB_HOST = System.getenv().getOrDefault("PEGASUS_DB_HOST", "localhost");
    private static final String DB_PORT = System.getenv().getOrDefault("PEGASUS_DB_PORT", "3306");
    private static final String DB_USER = System.getenv().getOrDefault("PEGASUS_DB_USER", "root");
    private static final String DB_PASSWORD = System.getenv().getOrDefault("PEGASUS_DB_PASSWORD", "");
    private static final String JDBC_URL =
            "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private dbConnection() {
        // Utility class
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }
}
