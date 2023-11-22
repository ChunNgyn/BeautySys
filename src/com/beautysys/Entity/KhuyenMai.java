/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.beautysys.Entity;

import java.util.Date;

/**
 *
 * @author admin
 */
public class KhuyenMai {
    private String MaKM,TenKM,MoTa;
    private Date NgayBD,NgayKT;
    private Float PhanTramGG;

    public String getMaKM() {
        return MaKM;
    }

    public void setMaKM(String MaKM) {
        this.MaKM = MaKM;
    }

    public String getTenKM() {
        return TenKM;
    }

    public void setTenKM(String TenKM) {
        this.TenKM = TenKM;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public Date getNgayBD() {
        return NgayBD;
    }

    public void setNgayBD(Date NgayBD) {
        this.NgayBD = NgayBD;
    }

    public Date getNgayKT() {
        return NgayKT;
    }

    public void setNgayKT(Date NgayKT) {
        this.NgayKT = NgayKT;
    }

    public Float getPhanTramGG() {
        return PhanTramGG;
    }

    public void setPhanTramGG(Float PhanTramGG) {
        this.PhanTramGG = PhanTramGG;
    }
    
}
