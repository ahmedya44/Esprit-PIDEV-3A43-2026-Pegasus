package com.pegasus.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class dbConnection {
    private static final String DB_NAME = "pegasus";
    private static final String DB_HOST = System.getenv().getOrDefault("PEGASUS_DB_HOST", "localhost");
    private static final String DB_PORT = System.getenv().getOrDefault("PEGASUS_DB_PORT", "3306");
    private static final String DB_USER = System.getenv().getOrDefault("PEGASUS_DB_USER", "root");
    private static final String DB_PASSWORD = System.getenv().getOrDefault("PEGASUS_DB_PASSWORD", "");
    private static final String JDBC_URL =
            "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static volatile boolean schemaChecked;

    private dbConnection() {
        // Utility class
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        ensureSchema(connection);
        return connection;
    }

    private static void ensureSchema(Connection connection) throws SQLException {
        if (schemaChecked) {
            return;
        }
        synchronized (dbConnection.class) {
            if (schemaChecked) {
                return;
            }
            ensureUserIdAutoIncrement(connection);
            schemaChecked = true;
        }
    }

    private static void ensureUserIdAutoIncrement(Connection connection) throws SQLException {
        boolean hasPrimaryKey = false;
        boolean isAutoIncrement = false;

        String sql = "SELECT COLUMN_KEY, EXTRA FROM information_schema.COLUMNS "
                + "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = 'user' AND COLUMN_NAME = 'id'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, DB_NAME);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    hasPrimaryKey = "PRI".equalsIgnoreCase(rs.getString("COLUMN_KEY"));
                    String extra = rs.getString("EXTRA");
                    isAutoIncrement = extra != null && extra.toLowerCase().contains("auto_increment");
                }
            }
        }

        if (!hasPrimaryKey) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("ALTER TABLE `user` ADD PRIMARY KEY (`id`)");
            }
        }

        if (!isAutoIncrement) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("ALTER TABLE `user` MODIFY COLUMN `id` INT NOT NULL AUTO_INCREMENT");
            }
        }
    }
}
