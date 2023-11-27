package com.beautysys.DAO;

import com.beautysys.Entity.KhuyenMai;
import com.beautysys.helper.DateHelper;
import com.beautysys.helper.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {

    public void insert(KhuyenMai model) {
        String sql = "INSERT INTO KhuyenMai (MaKM, TenKM, MoTa, NgayBD, NgayKT, PhanTramGG) VALUES (?, ?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaKM(),
                model.getTenKM(),
                model.getMoTa(),
                model.getNgayBD(),
                model.getNgayKT(),
                model.getPhanTramGG());
    }

    public void update(KhuyenMai model) {
        String sql = "UPDATE KhuyenMai SET TenKM=?, MoTa=?, NgayBD=?, NgayKT=?, PhanTramGG=? WHERE MaKM=?";
        JdbcHelper.executeUpdate(sql,
                model.getTenKM(),
                model.getMoTa(),
                model.getNgayBD(),
                model.getNgayKT(),
                model.getPhanTramGG(),
                model.getMaKM());
    }

    public void delete(String maKM) {
        String sql = "DELETE FROM KhuyenMai WHERE MaKM=?";
        JdbcHelper.executeUpdate(sql, maKM);
    }

    public List<KhuyenMai> select() {
        String sql = "SELECT * FROM KhuyenMai";
        return select(sql);
    }

    public KhuyenMai findById(String maKM) {
        String sql = "SELECT * FROM KhuyenMai WHERE MaKM=?";
        List<KhuyenMai> list = select(sql, maKM);
        return list.size() > 0 ? list.get(0) : null;
    }

    public KhuyenMai findByTenKM(String TenKM) {
        String sql = "SELECT * FROM KhuyenMai WHERE TenKM=?";
        List<KhuyenMai> list = select(sql, TenKM);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<KhuyenMai> selectNotExpired() {
        String sql = "SELECT * FROM KhuyenMai WHERE NgayKT >= ?";
        return select(sql, DateHelper.now());
    }

    private List<KhuyenMai> selectNotExpired(String sql, Object... args) {
        List<KhuyenMai> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    KhuyenMai model = readFromResultSet(rs);
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

    private List<KhuyenMai> select(String sql, Object... args) {
        List<KhuyenMai> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    KhuyenMai model = readFromResultSet(rs);
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

    private KhuyenMai readFromResultSet(ResultSet rs) throws SQLException {
        KhuyenMai model = new KhuyenMai();
        model.setMaKM(rs.getString("MaKM"));
        model.setTenKM(rs.getString("TenKM"));
        model.setMoTa(rs.getString("MoTa"));
        model.setNgayBD(rs.getDate("NgayBD"));
        model.setNgayKT(rs.getDate("NgayKT"));
        model.setPhanTramGG(rs.getFloat("PhanTramGG"));
        return model;
    }
}
