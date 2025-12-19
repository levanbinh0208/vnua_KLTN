package com.vnua.model;

import java.util.Date;

public class Notification {
    private Integer notificationId;
    private Integer userId;
    private String type;
    private Integer refId;
    private String title;
    private String message;
    private Integer status;
    private Date createdAt;

    public Notification() {}

    public Notification(Integer userId, String type, Integer refId, String title, String message) {
        this.userId = userId;
        this.type = type;
        this.refId = refId;
        this.title = title;
        this.message = message;
        this.status = 0;
    }

    // Getters and Setters
    public Integer getNotificationId() { return notificationId; }
    public void setNotificationId(Integer notificationId) { this.notificationId = notificationId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getRefId() { return refId; }
    public void setRefId(Integer refId) { this.refId = refId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}