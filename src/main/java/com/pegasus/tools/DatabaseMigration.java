package com.pegasus.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseMigration {
    
    private static final String OLD_DB_NAME = "pegasus";
    private static final String NEW_DB_NAME = "artwork";
    private static final String DB_HOST = System.getenv().getOrDefault("PEGASUS_DB_HOST", "localhost");
    private static final String DB_PORT = System.getenv().getOrDefault("PEGASUS_DB_PORT", "3306");
    private static final String DB_USER = System.getenv().getOrDefault("PEGASUS_DB_USER", "root");
    private static final String DB_PASSWORD = System.getenv().getOrDefault("PEGASUS_DB_PASSWORD", "");
    
    private static final String BASE_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/";
    
    public static void migrateDatabase() {
        try {
            System.out.println("Starting database migration from " + OLD_DB_NAME + " to " + NEW_DB_NAME);
            
            // Connect without specifying database
            try (Connection conn = DriverManager.getConnection(BASE_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement()) {
                
                // Create new database if it doesn't exist
                System.out.println("Creating new database: " + NEW_DB_NAME);
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + NEW_DB_NAME);
                
                // Check if old database exists
                boolean oldDbExists = false;
                var resultSet = stmt.executeQuery("SHOW DATABASES LIKE '" + OLD_DB_NAME + "'");
                if (resultSet.next()) {
                    oldDbExists = true;
                }
                resultSet.close();
                
                if (oldDbExists) {
                    System.out.println("Old database found. Copying data...");
                    
                    // Copy all tables and data from old database to new database
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + NEW_DB_NAME + ".users LIKE " + OLD_DB_NAME + ".users");
                    stmt.executeUpdate("INSERT INTO " + NEW_DB_NAME + ".users SELECT * FROM " + OLD_DB_NAME + ".users");
                    
                    // Copy art table if it exists
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + NEW_DB_NAME + ".art LIKE " + OLD_DB_NAME + ".art");
                    stmt.executeUpdate("INSERT INTO " + NEW_DB_NAME + ".art SELECT * FROM " + OLD_DB_NAME + ".art");
                    
                    // Copy art_favoris table if it exists
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + NEW_DB_NAME + ".art_favoris LIKE " + OLD_DB_NAME + ".art_favoris");
                    stmt.executeUpdate("INSERT INTO " + NEW_DB_NAME + ".art_favoris SELECT * FROM " + OLD_DB_NAME + ".art_favoris");
                    
                    // Copy art_like table if it exists
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + NEW_DB_NAME + ".art_like LIKE " + OLD_DB_NAME + ".art_like");
                    stmt.executeUpdate("INSERT INTO " + NEW_DB_NAME + ".art_like SELECT * FROM " + OLD_DB_NAME + ".art_like");
                    
                    // Copy art_view table if it exists
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + NEW_DB_NAME + ".art_view LIKE " + OLD_DB_NAME + ".art_view");
                    stmt.executeUpdate("INSERT INTO " + NEW_DB_NAME + ".art_view SELECT * FROM " + OLD_DB_NAME + ".art_view");
                    
                    // Add more table copies as needed based on your actual database schema
                    
                    System.out.println("Data migration completed successfully!");
                    
                    // Optionally, you can drop the old database after successful migration
                    // Uncomment the following line if you want to remove the old database
                    // stmt.executeUpdate("DROP DATABASE " + OLD_DB_NAME);
                    // System.out.println("Old database dropped: " + OLD_DB_NAME);
                    
                } else {
                    System.out.println("Old database not found. New database created empty.");
                }
            }
            
            System.out.println("Migration completed successfully!");
            
        } catch (SQLException e) {
            System.err.println("Migration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        migrateDatabase();
    }
}
