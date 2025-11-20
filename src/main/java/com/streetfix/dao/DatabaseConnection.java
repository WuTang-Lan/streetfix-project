package com.streetfix.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }

        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl != null && databaseUrl.startsWith("postgres://")) {
            databaseUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");
            URL = databaseUrl;
            USERNAME = null;
            PASSWORD = null;
        } else {
            URL = "jdbc:postgresql://" + System.getenv("PGHOST") + ":" + System.getenv("PGPORT") + "/" + System.getenv("PGDATABASE");
            USERNAME = System.getenv("PGUSER");
            PASSWORD = System.getenv("PGPASSWORD");
        }
    }

    public static Connection getConnection() throws SQLException {
        if (USERNAME != null && PASSWORD != null) {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } else {
            return DriverManager.getConnection(URL);
        }
    }
}