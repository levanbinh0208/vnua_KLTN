package com.vnua.model;

public class Supervision {
    private Integer supId;
    private Integer userId;
    private Integer deptId;
    private String studentName;
    private String thesisTitle;
    private String level;
    private Integer year;
    private String wordFileName;
    private Integer status;
    private String rejectReason;
    private String userName;
    private String userEmail;
    private String deptName;

    public Supervision() {
    }

    // Getters and Setters
    public Integer getSupId() {
        return supId;
    }

    public void setSupId(Integer supId) {
        this.supId = supId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getThesisTitle() {
        return thesisTitle;
    }

    public void setThesisTitle(String thesisTitle) {
        this.thesisTitle = thesisTitle;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public String getStatusText() {
        if (status == null) return "Chưa xác định";
        switch (status) {
            case 0:
                return "Chờ duyệt";
            case 1:
                return "Đã duyệt";
            case 2:
                return "Từ chối";
            default:
                return "Chưa xác định";
        }
    }
}