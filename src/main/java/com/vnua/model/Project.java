package com.vnua.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Project {
    private Integer projectId;
    private Integer userId;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String role;
    private String wordFileName;

    public String getWordFileName() { return wordFileName; }
    public void setWordFileName(String wordFileName) { this.wordFileName = wordFileName; }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}