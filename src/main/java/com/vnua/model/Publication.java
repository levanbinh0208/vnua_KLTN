package com.vnua.model;

<<<<<<< HEAD
import java.util.Date;

public class Publication {
    private Integer pubId;
    private Integer userId;
    private Integer deptId;
=======
public class Publication {
    private Integer pubId;
    private Integer userId;
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
    private String title;
    private String authors;
    private String journal;
    private Integer year;
<<<<<<< HEAD
    private String abstract_;
    private String url;
    private String wordFileName;
    private Date createdAt;
    private Integer status;
    private String rejectReason;

    // JOIN fields
    private String userName;
    private String userEmail;
    private String deptName;

    public Publication() {
    }

    // Getters and Setters
=======
    private String abstractText;
    private String url;
    private java.util.Date createdAt;
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
    public Integer getPubId() {
        return pubId;
    }

    public void setPubId(Integer pubId) {
        this.pubId = pubId;
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
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

<<<<<<< HEAD
    public String getAbstract_() {
        return abstract_;
    }

    public void setAbstract_(String abstract_) {
        this.abstract_ = abstract_;
=======
    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

<<<<<<< HEAD
    public String getWordFileName() {
        return wordFileName;
    }

    public void setWordFileName(String wordFileName) {
        this.wordFileName = wordFileName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
=======
    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }
}
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
