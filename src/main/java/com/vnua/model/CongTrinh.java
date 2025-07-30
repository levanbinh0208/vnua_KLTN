package com.vnua.model;

import javax.persistence.*;

@Entity
@Table(name = "CongTrinh")
public class CongTrinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lyLichId", nullable = false)
    private LyLichKhoaHoc lyLich;

    @Column(name = "tenCongTrinh", nullable = false)
    private String tenCongTrinh;

    @Column(name = "namXuatBan")
    private Integer namXuatBan;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LyLichKhoaHoc getLyLich() {
        return lyLich;
    }

    public void setLyLich(LyLichKhoaHoc lyLich) {
        this.lyLich = lyLich;
    }

    public String getTenCongTrinh() {
        return tenCongTrinh;
    }

    public void setTenCongTrinh(String tenCongTrinh) {
        this.tenCongTrinh = tenCongTrinh;
    }

    public Integer getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(Integer namXuatBan) {
        this.namXuatBan = namXuatBan;
    }
}