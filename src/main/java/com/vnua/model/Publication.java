package com.vnua.model;
import java.util.Date;

public class Publication {
    private Integer pubId;
    private Integer userId;

    private String title;
    private String authors;
    private String journal;
    private Integer year;
    private String abstract_;
    private String url;
    private String wordFileName;
    private Date createdAt;
    private Integer status;
    private String rejectReason;
    private String userName;
    private String userEmail;
    private String deptName;

    private Integer deptId;

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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

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

    public String getAbstract_() {
        return abstract_;
    }

    public void setAbstract_(String abstract_) {
        this.abstract_ = abstract_;
    }


        public String getWordFileName () {
            return wordFileName;
        }

        public void setWordFileName (String wordFileName){
            this.wordFileName = wordFileName;
        }

        public Integer getStatus () {
            return status;
        }

        public void setStatus (Integer status){
            this.status = status;
        }

        public String getRejectReason () {
            return rejectReason;
        }

        public void setRejectReason (String rejectReason){
            this.rejectReason = rejectReason;
        }

        public String getUserName () {
            return userName;
        }

        public void setUserName (String userName){
            this.userName = userName;
        }

        public String getUserEmail () {
            return userEmail;
        }

        public void setUserEmail (String userEmail){
            this.userEmail = userEmail;
        }

        public String getDeptName () {
            return deptName;
        }

        public void setDeptName (String deptName){
            this.deptName = deptName;
        }

        public String getStatusText () {
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
        public java.util.Date getCreatedAt () {
            return createdAt;
        }

        public void setCreatedAt (java.util.Date createdAt){
            this.createdAt = createdAt;
        }
}

