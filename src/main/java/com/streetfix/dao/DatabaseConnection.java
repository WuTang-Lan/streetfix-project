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
    private static final String DB_TYPE;

    static {
        // Detect database type based on environment variables
        String mysqlHost = System.getenv("MYSQL_HOST");
        String databaseUrl = System.getenv("DATABASE_URL");
        
        String tempUrl = null;
        String tempUser = null;
        String tempPass = null;
        String tempType = null;
        
        // If MYSQL_HOST is set, use MySQL (XAMPP)
        if (mysqlHost != null) {
            tempType = "MySQL";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("MySQL JDBC Driver not found", e);
            }
            
            String mysqlPort = System.getenv("MYSQL_PORT");
            String mysqlDatabase = System.getenv("MYSQL_DATABASE");
            String mysqlUser = System.getenv("MYSQL_USER");
            String mysqlPassword = System.getenv("MYSQL_PASSWORD");
            
            // Default values for XAMPP
            if (mysqlHost == null) mysqlHost = "localhost";
            if (mysqlPort == null) mysqlPort = "3306";
            if (mysqlDatabase == null) mysqlDatabase = "streetfix_nairobi";
            if (mysqlUser == null) mysqlUser = "root";
            if (mysqlPassword == null) mysqlPassword = "";
            
            tempUrl = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", 
                               mysqlHost, mysqlPort, mysqlDatabase);
            tempUser = mysqlUser;
            tempPass = mysqlPassword;
            
            System.out.println("MySQL connection configured: " + mysqlHost + ":" + mysqlPort + "/" + mysqlDatabase);
        } else {
            // Otherwise use PostgreSQL (Replit)
            tempType = "PostgreSQL";
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
            }
            
            if (databaseUrl != null && (databaseUrl.startsWith("postgres://") || 
                                         databaseUrl.startsWith("postgresql://") || 
                                         databaseUrl.startsWith("jdbc:postgresql://"))) {
                try {
                    // Normalize the URI to use postgres:// for parsing
                    String normalizedUrl = databaseUrl
                        .replace("jdbc:postgresql://", "postgres://")
                        .replace("postgresql://", "postgres://");
                    URI dbUri = new URI(normalizedUrl);
                    String userInfo = dbUri.getUserInfo();
                    
                    if (userInfo != null && userInfo.contains(":")) {
                        String[] credentials = userInfo.split(":", 2);
                        try {
                            tempUser = URLDecoder.decode(credentials[0], "UTF-8");
                            tempPass = URLDecoder.decode(credentials[1], "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException("Failed to decode DATABASE_URL credentials", e);
                        }
                    } else {
                        tempUser = System.getenv("PGUSER");
                        tempPass = System.getenv("PGPASSWORD");
                        if (tempUser == null || tempPass == null) {
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
                    tempUrl = jdbcUrl.toString();
                    
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
                
                tempUrl = String.format("jdbc:postgresql://%s:%s/%s", pgHost, pgPort, pgDatabase);
                tempUser = pgUser;
                tempPass = pgPassword;
                
                System.out.println("Database connection configured from PG* environment variables");
            }
        }
        
        URL = tempUrl;
        USERNAME = tempUser;
        PASSWORD = tempPass;
        DB_TYPE = tempType;
        
        System.out.println("Database configured: " + DB_TYPE);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
