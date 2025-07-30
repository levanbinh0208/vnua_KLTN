package com.vnua.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CanBo")
public class CanBo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hoTen", nullable = false)
    private String hoTen;

    @Column(name = "ngay_Sinh")
    private LocalDate ngaySinh;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "don_Vi")
    private String donVi;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }
}