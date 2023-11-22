package com.beautysys.DAO;

import com.beautysys.Entity.SanPham;
import com.beautysys.helper.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {

    public void insert(SanPham model) {
        String sql = "INSERT INTO SanPham (MaSP, MaDM, TenSP, ThuongHieu, DonGia, MoTa, HinhSP, SLTonKho, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaSP(),
                model.getMaDM(),
                model.getTenSP(),
                model.getThuongHieu(),
                model.getDonGia(),
                model.getMoTa(),
                model.getHinhSP(),
                model.getSLTonKho(),
                model.getTrangThai());
    }

    public void update(SanPham model) {
        String sql = "UPDATE SanPham SET MaDM=?, TenSP=?, ThuongHieu=?, DonGia=?, MoTa=?, HinhSP=?, SLTonKho=?, TrangThai=? WHERE MaSP=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaDM(),
                model.getTenSP(),
                model.getThuongHieu(),
                model.getDonGia(),
                model.getMoTa(),
                model.getHinhSP(),
                model.getSLTonKho(),
                model.getTrangThai(),
                model.getMaSP());
    }

    public void delete(String maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP=?";
        JdbcHelper.executeUpdate(sql, maSP);
    }

    public List<SanPham> select() {
        String sql = "SELECT * FROM SanPham";
        return select(sql);
    }

    public SanPham findById(String maSP) {
        String sql = "SELECT * FROM SanPham WHERE MaSP=?";
        List<SanPham> list = select(sql, maSP);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<SanPham> select(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    SanPham model = readFromResultSet(rs);
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

    private SanPham readFromResultSet(ResultSet rs) throws SQLException {
        SanPham model = new SanPham();
        model.setMaSP(rs.getString("MaSP"));
        model.setMaDM(rs.getString("MaDM"));
        model.setTenSP(rs.getString("TenSP"));
        model.setThuongHieu(rs.getString("ThuongHieu"));
        model.setDonGia(rs.getFloat("DonGia"));
        model.setMoTa(rs.getString("MoTa"));
        model.setHinhSP(rs.getString("HinhSP"));
        model.setSLTonKho(rs.getInt("SLTonKho"));
        model.setTrangThai(rs.getBoolean("TrangThai"));
        return model;
    }
}
