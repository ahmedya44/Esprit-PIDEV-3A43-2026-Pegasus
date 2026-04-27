package com.pegasus.tools;

import java.sql.Connection;

public class DbConnectionCheck {
    public static void main(String[] args) {
        try (Connection connection = dbConnection.getConnection()) {
            System.out.println("Connected!");
            System.out.println("Database: " + connection.getCatalog());
            System.out.println("URL: " + connection.getMetaData().getURL());
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
