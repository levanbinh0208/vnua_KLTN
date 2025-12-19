package com.vnua.model;

<<<<<<< HEAD
public class Book {
    private Integer bookId;
    private Integer userId;
    private Integer deptId;
=======

public class Book {
    private Integer bookId;
    private Integer userId;
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
    private String title;
    private String publisher;
    private Integer year;
    private String isbn;
    private String wordFileName;
<<<<<<< HEAD
    private Integer status;
    private String rejectReason;

    private String userName;
    private String userEmail;
    private String deptName;

    public Book() {}

    // Getters and Setters
    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getDeptId() { return deptId; }
    public void setDeptId(Integer deptId) { this.deptId = deptId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
=======
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9

    public String getWordFileName() { return wordFileName; }
    public void setWordFileName(String wordFileName) { this.wordFileName = wordFileName; }

<<<<<<< HEAD
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getRejectReason() { return rejectReason; }
    public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public String getStatusText() {
        if (status == null) return "Chưa xác định";
        switch (status) {
            case 0: return "Chờ duyệt";
            case 1: return "Đã duyệt";
            case 2: return "Từ chối";
            default: return "Chưa xác định";
        }
    }
}
=======
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
