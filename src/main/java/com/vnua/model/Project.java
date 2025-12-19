package com.vnua.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Project {
    private Integer projectId;
    private Integer userId;
<<<<<<< HEAD
    private Integer deptId;
=======
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
    private String name;
    private String description;
    private String startDate;
    private String endDate;
<<<<<<< HEAD
    private String role;
    private String wordFileName;

    private Integer status;
    private String rejectReason;

    private String userName;
    private String userEmail;
    private String deptName;

    // Constructors
    public Project() {}

    // Getters and Setters
=======
    private String wordFileName;
    private Integer deptId;
    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getWordFileName() { return wordFileName; }
    public void setWordFileName(String wordFileName) { this.wordFileName = wordFileName; }

>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
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

<<<<<<< HEAD
    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

=======
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
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

<<<<<<< HEAD
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getWordFileName() {
        return wordFileName;
    }

    public void setWordFileName(String wordFileName) {
        this.wordFileName = wordFileName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    // Helper method để hiển thị trạng thái
    public String getStatusText() {
        if (status == null) return "Chưa xác định";
        switch (status) {
            case 0: return "Chờ duyệt";
            case 1: return "Đã duyệt";
            case 2: return "Từ chối";
            default: return "Chưa xác định";
        }
    }
=======
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
}