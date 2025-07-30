package com.vnua.model;

import jakarta.persistence.*;


@Table(name = "LyLichKhoaHoc")
public class LyLichKhoaHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "canBoId", nullable = false)
    private CanBo canBo;

    @Column(name = "hocVi")
    private String hocVi;

    @Column(name = "nghienCuu", columnDefinition = "NVARCHAR(MAX)")
    private String nghienCuu;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CanBo getCanBo() {
        return canBo;
    }

    public void setCanBo(CanBo canBo) {
        this.canBo = canBo;
    }

    public String getHocVi() {
        return hocVi;
    }

    public void setHocVi(String hocVi) {
        this.hocVi = hocVi;
    }

    public String getNghienCuu() {
        return nghienCuu;
    }

    public void setNghienCuu(String nghienCuu) {
        this.nghienCuu = nghienCuu;
    }
}