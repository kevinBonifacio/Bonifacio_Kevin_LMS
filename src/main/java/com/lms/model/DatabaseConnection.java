package com.lms.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
    public Connection conn = null;

    public Connection connect() {
        String url = "jdbc:sqlite:C:/sqlite/Library.db";

        try {
            conn = DriverManager.getConnection(url);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return conn;
    }
}
