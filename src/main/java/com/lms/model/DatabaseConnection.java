package com.lms.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/*
 * Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 07 April 2024
 * DatabaseConnection.java
 * This class establishes a connection to the database.
 */
public class DatabaseConnection {
    public Connection conn = null;

    public Connection connect() {
        String url = "jdbc:sqlite:database.db";

        try {
            conn = DriverManager.getConnection(url);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return conn;
    }

    public Connection connect(String dataBaseName) {
        String url = "jdbc:sqlite:" + dataBaseName + ".db";

        try {
            conn = DriverManager.getConnection(url);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return conn;
    }
}
