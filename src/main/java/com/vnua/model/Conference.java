package com.vnua.model;

import java.time.LocalDate;

public class Conference {
    private Integer confId;
    private Integer userId;
    private String title;
    private String location;
    private String date;
    private String role;
    private String wordFileName;

    public String getWordFileName() { return wordFileName; }
    public void setWordFileName(String wordFileName) { this.wordFileName = wordFileName; }

    public Integer getConfId() {
        return confId;
    }

    public void setConfId(Integer confId) {
        this.confId = confId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}