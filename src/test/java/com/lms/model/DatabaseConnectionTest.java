package com.lms.model;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void testConnect_WithRealDatabaseFile() {
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn = dbConn.connect("test");

        assertNotNull(conn);

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS test (id INTEGER PRIMARY KEY, name TEXT)");
        } catch (Exception e) {
            fail("Failed to execute SQL on real SQLite file");
        }
    }
}