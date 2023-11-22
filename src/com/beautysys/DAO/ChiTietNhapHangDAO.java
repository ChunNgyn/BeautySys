package com.beautysys.DAO;

import com.beautysys.Entity.ChiTietNhapHang;
import com.beautysys.helper.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietNhapHangDAO {

    public void insert(ChiTietNhapHang model) {
        String sql = "INSERT INTO ChiTietPhieuNhapHang (MaCTNH, MaNH, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaCTNH(),
                model.getMaNH(),
                model.getMaSP(),
                model.getSoLuong(),
                model.getDonGia());
    }

    public void update(ChiTietNhapHang model) {
        String sql = "UPDATE ChiTietPhieuNhapHang SET MaNH=?, MaSP=?, SoLuong=?, DonGia=? WHERE MaCTNH=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaNH(),
                model.getMaSP(),
                model.getSoLuong(),
                model.getDonGia(),
                model.getMaCTNH());
    }

    public void delete(String maCTNH) {
        String sql = "DELETE FROM ChiTietPhieuNhapHang WHERE MaCTNH=?";
        JdbcHelper.executeUpdate(sql, maCTNH);
    }

    public List<ChiTietNhapHang> select() {
        String sql = "SELECT * FROM ChiTietPhieuNhapHang";
        return select(sql);
    }

    public ChiTietNhapHang findById(String maCTNH) {
        String sql = "SELECT * FROM ChiTietPhieuNhapHang WHERE MaCTNH=?";
        List<ChiTietNhapHang> list = select(sql, maCTNH);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<ChiTietNhapHang> select(String sql, Object... args) {
        List<ChiTietNhapHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ChiTietNhapHang model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                if (rs != null) {
                    rs.getStatement().getConnection().close();
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private ChiTietNhapHang readFromResultSet(ResultSet rs) throws SQLException {
        ChiTietNhapHang model = new ChiTietNhapHang();
        model.setMaCTNH(rs.getString("MaCTNH"));
        model.setMaNH(rs.getString("MaNH"));
        model.setMaSP(rs.getString("MaSP"));
        model.setSoLuong(rs.getInt("SoLuong"));
        model.setDonGia(rs.getFloat("DonGia"));
        return model;
    }
}
