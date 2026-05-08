package com.pegasus.tools;

import com.pegasus.config.EnvLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class dbConnection {
    private static final String DB_NAME = EnvLoader.getOrDefault("PEGASUS_DB_NAME", "pegasus");
    private static final String DB_HOST = EnvLoader.getOrDefault("PEGASUS_DB_HOST", "127.0.0.1");
    private static final String DB_PORT = EnvLoader.getOrDefault("PEGASUS_DB_PORT", "3306");
    private static final String DB_USER = EnvLoader.getOrDefault("PEGASUS_DB_USER", "root");
    private static final String DB_PASSWORD = EnvLoader.getOrDefault("PEGASUS_DB_PASSWORD", "");
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

    public static String connectionSummary() {
        return "host=" + DB_HOST + ", port=" + DB_PORT + ", db=" + DB_NAME + ", user=" + DB_USER;
    }

    public static String buildConnectionErrorMessage(SQLException e) {
        String detail = e == null ? "Unknown SQL error." : e.getMessage();
        if (detail == null || detail.isBlank()) {
            detail = "Unknown SQL error.";
        }
        return "Unable to connect to database (" + connectionSummary() + "). " + detail;
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
            ensureArtReactionSchema(connection);
            schemaChecked = true;
        }
    }

    private static void ensureArtReactionSchema(Connection connection) throws SQLException {
        if (!tableExists(connection, "art")) {
            return;
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("ALTER TABLE art ADD COLUMN likes INT DEFAULT 0");
        } catch (SQLException e) {
            if (!isDuplicateColumn(e)) {
                throw e;
            }
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("ALTER TABLE art ADD COLUMN dislikes INT DEFAULT 0");
        } catch (SQLException e) {
            if (!isDuplicateColumn(e)) {
                throw e;
            }
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS art_like (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        art_id INT NOT NULL,
                        session_id VARCHAR(180) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_art_like_art FOREIGN KEY (art_id) REFERENCES art(id) ON DELETE CASCADE,
                        UNIQUE KEY unique_art_like_session (art_id, session_id)
                    )
                    """);
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS art_dislike (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        art_id INT NOT NULL,
                        session_id VARCHAR(180) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_art_dislike_art FOREIGN KEY (art_id) REFERENCES art(id) ON DELETE CASCADE,
                        UNIQUE KEY unique_art_dislike_session (art_id, session_id)
                    )
                    """);
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE art SET likes = COALESCE((SELECT COUNT(*) FROM art_like WHERE art_like.art_id = art.id), 0)");
            statement.executeUpdate("UPDATE art SET dislikes = COALESCE((SELECT COUNT(*) FROM art_dislike WHERE art_dislike.art_id = art.id), 0)");
        }
    }

    private static boolean isDuplicateColumn(SQLException e) {
        return e != null && (e.getErrorCode() == 1060
                || (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate column")));
    }

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) FROM information_schema.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?")) {
            statement.setString(1, DB_NAME);
            statement.setString(2, tableName);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
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
