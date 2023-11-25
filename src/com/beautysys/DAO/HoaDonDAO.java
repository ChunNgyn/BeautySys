package com.beautysys.DAO;

import com.beautysys.Entity.HoaDon;
import com.beautysys.helper.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    public void insert(HoaDon model) {
        String sql = "INSERT INTO HoaDon (MaHD, MaNV, MaKM, NgayLap, TongSoTien, TrangThai, PhuongThucTT) VALUES (?, ?, ?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaHD(),
                model.getMaNV(),
                model.getMaKM(),
                model.getNgayLap(),
                0,
                model.getTrangThai(),
                model.getPhuongThucTT());
    }

    public void update(HoaDon model) {
        String sql = "UPDATE HoaDon SET MaNV=?, MaKM=?, NgayLap=?, TongSoTien=?, TrangThai=?, PhuongThucTT=? WHERE MaHD=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaNV(),
                model.getMaKM(),
                model.getNgayLap(),
                0,
                model.getTrangThai(),
                model.getPhuongThucTT(),
                model.getMaHD());
    }

    public void delete(String maHD) {
        String sql = "DELETE FROM HoaDon WHERE MaHD=?";
        JdbcHelper.executeUpdate(sql, maHD);
    }

    public List<HoaDon> select() {
        String sql = "SELECT * FROM HoaDon";
        return select(sql);
    }

    public HoaDon findById(String maHD) {
        String sql = "SELECT * FROM HoaDon WHERE MaHD=?";
        List<HoaDon> list = select(sql, maHD);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HoaDon> select(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HoaDon model = readFromResultSet(rs);
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

    private HoaDon readFromResultSet(ResultSet rs) throws SQLException {
        HoaDon model = new HoaDon();
        model.setMaHD(rs.getString("MaHD"));
        model.setMaNV(rs.getString("MaNV"));
        model.setMaKM(rs.getString("MaKM"));
        model.setNgayLap(rs.getDate("NgayLap"));
        model.setTongSoTien(rs.getFloat("TongSoTien"));
        model.setTrangThai(rs.getString("TrangThai"));
        model.setPhuongThucTT(rs.getString("PhuongThucTT"));
        return model;
    }
}
