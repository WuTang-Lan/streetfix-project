package com.streetfix.model;

import java.time.LocalDateTime;

public class Issue {
    private int id;
    private int userId;
    private String userName;
    private String title;
    private String description;
    private String issueType;
    private String location;
    private Double latitude;
    private Double longitude;
    private String status;
    private String photoPath;
    private LocalDateTime createdAt;

    // Constructors
    public Issue() {}

    public Issue(int userId, String title, String description, String issueType, String location) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.issueType = issueType;
        this.location = location;
        this.status = "pending";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIssueType() { return issueType; }
    public void setIssueType(String issueType) { this.issueType = issueType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}