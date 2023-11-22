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
public class TraHang {
    private String MaTH,MaNV,Lydo;
    private Date NgayTra;

    public String getMaTH() {
        return MaTH;
    }

    public void setMaTH(String MaTH) {
        this.MaTH = MaTH;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getLydo() {
        return Lydo;
    }

    public void setLydo(String Lydo) {
        this.Lydo = Lydo;
    }

    public Date getNgayTra() {
        return NgayTra;
    }

    public void setNgayTra(Date NgayTra) {
        this.NgayTra = NgayTra;
    }
    
}
