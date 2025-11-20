-- StreetFix Nairobi - MySQL Database Schema for XAMPP
-- Run this in phpMyAdmin or MySQL Workbench

-- Create database
CREATE DATABASE IF NOT EXISTS streetfix_nairobi;
USE streetfix_nairobi;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Issues table
CREATE TABLE IF NOT EXISTS issues (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    issue_type VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    latitude DOUBLE,
    longitude DOUBLE,
    status VARCHAR(50) DEFAULT 'pending',
    photo_path VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample data for testing
-- Passwords are BCrypt hashed (work factor: 12)
-- Original passwords: admin123, password123, password123
INSERT INTO users (full_name, email, password) VALUES
('Admin User', 'admin@streetfix.co.ke', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5eU7QvzqTEhKC'),
('John Kamau', 'john@example.com', '$2a$12$7FuZl.vGQXSsDwT4wPkO3.TN5VbNXU6T3jF3bUO3gkd9x.LvR8fxK'),
('Mary Wanjiku', 'mary@example.com', '$2a$12$7FuZl.vGQXSsDwT4wPkO3.TN5VbNXU6T3jF3bUO3gkd9x.LvR8fxK');

INSERT INTO issues (user_id, title, description, issue_type, location, latitude, longitude, status) VALUES
(2, 'Pothole on Kenyatta Avenue', 'Large pothole causing traffic issues', 'Road Damage', 'Kenyatta Avenue, Nairobi', -1.286389, 36.817223, 'pending'),
(2, 'Streetlight not working', 'Streetlight on Moi Avenue has been off for 3 days', 'Streetlight', 'Moi Avenue, Nairobi', -1.283333, 36.816667, 'in_progress'),
(3, 'Blocked drainage', 'Drainage blocked causing flooding', 'Drainage', 'Tom Mboya Street', -1.284444, 36.822222, 'resolved');

-- Show tables
SHOW TABLES;

-- Display data
SELECT * FROM users;
SELECT * FROM issues;
