package com.beautysys.DAO;

import com.beautysys.Entity.NhanVien;
import com.beautysys.helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public void insert(NhanVien model) {
        String sql = "INSERT INTO NhanVien (MaNV, TenNV, Email, SDT, NgaySinh, ChucVu, TrangThai, TenTK, MatKhau) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaNV(),
                model.getTenNV(),
                model.getEmail(),
                model.getSDT(),
                model.getNgaySinh(),
                model.getChucVu(),
                model.getTrangThai(),
                model.getTenTK(),
                model.getMatKhau());
    }

    public void update(NhanVien model) {
        String sql = "UPDATE NhanVien SET TenNV=?, Email=?, SDT=?, NgaySinh=?, ChucVu=?, TrangThai=?, TenTK=?, MatKhau=? WHERE MaNV=?";
        JdbcHelper.executeUpdate(sql,
                model.getTenNV(),
                model.getEmail(),
                model.getSDT(),
                model.getNgaySinh(),
                model.getChucVu(),
                model.getTrangThai(),
                model.getTenTK(),
                model.getMatKhau(),
                model.getMaNV());
    }

    public void delete(String MaNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        JdbcHelper.executeUpdate(sql, MaNV);
    }

    public List<NhanVien> select() {
        String sql = "SELECT * FROM NhanVien";
        return select(sql);
    }

    public NhanVien findById(String manv) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVien> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }
    public NhanVien findByTenTK(String tenTK) {
        String sql = "SELECT * FROM NhanVien WHERE TenTK=?";
        List<NhanVien> list = select(sql, tenTK);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    private List<NhanVien> select(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NhanVien model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private NhanVien readFromResultSet(ResultSet rs) throws SQLException {
        NhanVien model = new NhanVien();
        model.setMaNV(rs.getString("MaNV"));
        model.setTenNV(rs.getString("TenNV"));
        model.setEmail(rs.getString("Email"));
        model.setSDT(rs.getString("SDT"));
        model.setNgaySinh(rs.getDate("NgaySinh"));
        model.setChucVu(rs.getString("ChucVu"));
        model.setTrangThai(rs.getBoolean("TrangThai"));
        model.setTenTK(rs.getString("TenTK"));
        model.setMatKhau(rs.getString("MatKhau"));
        return model;
    }
}
