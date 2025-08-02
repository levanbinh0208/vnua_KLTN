package com.vnua.model;

public class Department {
    private Integer id;
    private String name;
    private String code;
    private String remark;

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
