package com.streetfix.dao;

import com.streetfix.model.Issue;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssueDAO {

    public boolean createIssue(Issue issue) {
        String sql = "INSERT INTO issues (user_id, title, description, issue_type, location, latitude, longitude, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, issue.getUserId());
            stmt.setString(2, issue.getTitle());
            stmt.setString(3, issue.getDescription());
            stmt.setString(4, issue.getIssueType());
            stmt.setString(5, issue.getLocation());

            if (issue.getLatitude() != null) {
                stmt.setDouble(6, issue.getLatitude());
            } else {
                stmt.setNull(6, Types.DOUBLE);
            }

            if (issue.getLongitude() != null) {
                stmt.setDouble(7, issue.getLongitude());
            } else {
                stmt.setNull(7, Types.DOUBLE);
            }

            stmt.setString(8, issue.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Issue> getAllIssues() {
        List<Issue> issues = new ArrayList<>();
        String sql = "SELECT i.*, u.full_name as user_name FROM issues i JOIN users u ON i.user_id = u.id ORDER BY i.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Issue issue = new Issue();
                issue.setId(rs.getInt("id"));
                issue.setUserId(rs.getInt("user_id"));
                issue.setUserName(rs.getString("user_name"));
                issue.setTitle(rs.getString("title"));
                issue.setDescription(rs.getString("description"));
                issue.setIssueType(rs.getString("issue_type"));
                issue.setLocation(rs.getString("location"));
                issue.setLatitude(rs.getDouble("latitude"));
                issue.setLongitude(rs.getDouble("longitude"));
                issue.setStatus(rs.getString("status"));
                issue.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                issues.add(issue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return issues;
    }

    public List<Issue> getUserIssues(int userId) {
        List<Issue> issues = new ArrayList<>();
        String sql = "SELECT i.*, u.full_name as user_name FROM issues i JOIN users u ON i.user_id = u.id WHERE i.user_id = ? ORDER BY i.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Issue issue = new Issue();
                issue.setId(rs.getInt("id"));
                issue.setUserId(rs.getInt("user_id"));
                issue.setUserName(rs.getString("user_name"));
                issue.setTitle(rs.getString("title"));
                issue.setDescription(rs.getString("description"));
                issue.setIssueType(rs.getString("issue_type"));
                issue.setLocation(rs.getString("location"));
                issue.setLatitude(rs.getDouble("latitude"));
                issue.setLongitude(rs.getDouble("longitude"));
                issue.setStatus(rs.getString("status"));
                issue.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                issues.add(issue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return issues;
    }

    public int getTotalIssues() {
        String sql = "SELECT COUNT(*) as total FROM issues";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getIssuesCountByStatus(String status) {
        String sql = "SELECT COUNT(*) as count FROM issues WHERE status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}