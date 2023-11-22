package com.beautysys.DAO;

import com.beautysys.Entity.ChiTietTraHang;
import com.beautysys.helper.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietTraHangDAO {

    public void insert(ChiTietTraHang model) {
        String sql = "INSERT INTO ChiTietPhieuTraHang (MaCTTH, MaTH, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaCTTH(),
                model.getMaTH(),
                model.getMaSP(),
                model.getSoLuong(),
                model.getDonGia());
    }

    public void update(ChiTietTraHang model) {
        String sql = "UPDATE ChiTietPhieuTraHang SET MaTH=?, MaSP=?, SoLuong=?, DonGia=? WHERE MaCTTH=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaTH(),
                model.getMaSP(),
                model.getSoLuong(),
                model.getDonGia(),
                model.getMaCTTH());
    }

    public void delete(String maCTTH) {
        String sql = "DELETE FROM ChiTietPhieuTraHang WHERE MaCTTH=?";
        JdbcHelper.executeUpdate(sql, maCTTH);
    }

    public List<ChiTietTraHang> select() {
        String sql = "SELECT * FROM ChiTietPhieuTraHang";
        return select(sql);
    }

    public ChiTietTraHang findById(String maCTTH) {
        String sql = "SELECT * FROM ChiTietPhieuTraHang WHERE MaCTTH=?";
        List<ChiTietTraHang> list = select(sql, maCTTH);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<ChiTietTraHang> select(String sql, Object... args) {
        List<ChiTietTraHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ChiTietTraHang model = readFromResultSet(rs);
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

    private ChiTietTraHang readFromResultSet(ResultSet rs) throws SQLException {
        ChiTietTraHang model = new ChiTietTraHang();
        model.setMaCTTH(rs.getString("MaCTTH"));
        model.setMaTH(rs.getString("MaTH"));
        model.setMaSP(rs.getString("MaSP"));
        model.setSoLuong(rs.getInt("SoLuong"));
        model.setDonGia(rs.getFloat("DonGia"));
        return model;
    }
}
