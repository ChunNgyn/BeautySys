package com.beautysys.DAO;

import com.beautysys.Entity.ChiTietHoaDon;
import com.beautysys.helper.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {
     public String MaCTHDCuoi() {
        String sql = "SELECT MAX(MaCTHD) AS MaCTHDCuoi FROM ChiTietHoaDon";
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql);
            if (rs.next()) {
                return rs.getString("MaCTHDCuoi");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
    public void insert(ChiTietHoaDon model) {
        String sql = "INSERT INTO ChiTietHoaDon (MaCTHD, MaHD, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaCTHD(),
                model.getMaHD(),
                model.getMaSP(),
                model.getSoLuong(),
                model.getDonGia());
    }

    public void update(ChiTietHoaDon model) {
        String sql = "UPDATE ChiTietHoaDon SET MaHD=?, MaSP=?, SoLuong=?, DonGia=? WHERE MaCTHD=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaHD(),
                model.getMaSP(),
                model.getSoLuong(),
                model.getDonGia(),
                model.getMaCTHD());
    }

    public void delete(String maCTHD) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE MaCTHD=?";
        JdbcHelper.executeUpdate(sql, maCTHD);
    }

    public List<ChiTietHoaDon> select() {
        String sql = "SELECT * FROM ChiTietHoaDon";
        return select(sql);
    }

    public ChiTietHoaDon findById(String maCTHD) {
        String sql = "SELECT * FROM ChiTietHoaDon WHERE MaCTHD=?";
        List<ChiTietHoaDon> list = select(sql, maCTHD);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<ChiTietHoaDon> select(String sql, Object... args) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ChiTietHoaDon model = readFromResultSet(rs);
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

    private ChiTietHoaDon readFromResultSet(ResultSet rs) throws SQLException {
        ChiTietHoaDon model = new ChiTietHoaDon();
        model.setMaCTHD(rs.getString("MaCTHD"));
        model.setMaHD(rs.getString("MaHD"));
        model.setMaSP(rs.getString("MaSP"));
        model.setSoLuong(rs.getInt("SoLuong"));
        model.setDonGia(rs.getFloat("DonGia"));
        return model;
    }
}
