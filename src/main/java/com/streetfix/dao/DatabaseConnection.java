package com.streetfix.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
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
            try {
                URI dbUri = new URI(databaseUrl);
                String userInfo = dbUri.getUserInfo();
                
                if (userInfo != null && userInfo.contains(":")) {
                    String[] credentials = userInfo.split(":", 2);
                    try {
                        USERNAME = URLDecoder.decode(credentials[0], "UTF-8");
                        PASSWORD = URLDecoder.decode(credentials[1], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException("Failed to decode DATABASE_URL credentials", e);
                    }
                } else {
                    USERNAME = System.getenv("PGUSER");
                    PASSWORD = System.getenv("PGPASSWORD");
                    if (USERNAME == null || PASSWORD == null) {
                        throw new RuntimeException("DATABASE_URL does not contain credentials and PGUSER/PGPASSWORD are not set");
                    }
                }
                
                String host = dbUri.getHost();
                int port = dbUri.getPort();
                if (port == -1) {
                    port = 5432;
                }
                String path = dbUri.getPath();
                String query = dbUri.getQuery();
                
                if (host == null || path == null) {
                    throw new RuntimeException("DATABASE_URL is malformed");
                }
                
                StringBuilder jdbcUrl = new StringBuilder();
                jdbcUrl.append("jdbc:postgresql://").append(host).append(":").append(port).append(path);
                if (query != null && !query.isEmpty()) {
                    jdbcUrl.append("?").append(query);
                }
                URL = jdbcUrl.toString();
                
                System.out.println("Database connection configured from DATABASE_URL");
            } catch (URISyntaxException e) {
                throw new RuntimeException("Failed to parse DATABASE_URL", e);
            }
        } else {
            String pgHost = System.getenv("PGHOST");
            String pgPort = System.getenv("PGPORT");
            String pgDatabase = System.getenv("PGDATABASE");
            String pgUser = System.getenv("PGUSER");
            String pgPassword = System.getenv("PGPASSWORD");
            
            if (pgHost == null || pgPort == null || pgDatabase == null || pgUser == null || pgPassword == null) {
                throw new RuntimeException("Missing required PostgreSQL environment variables (PGHOST, PGPORT, PGDATABASE, PGUSER, PGPASSWORD)");
            }
            
            URL = String.format("jdbc:postgresql://%s:%s/%s", pgHost, pgPort, pgDatabase);
            USERNAME = pgUser;
            PASSWORD = pgPassword;
            
            System.out.println("Database connection configured from PG* environment variables");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}