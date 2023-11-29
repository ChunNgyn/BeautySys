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

    public String MaSPCuoi() {
        String sql = "SELECT MAX(MaSP) AS MaSPCuoi FROM SanPham";
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql);
            if (rs.next()) {
                return rs.getString("MaSPCuoi");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public List<SanPham> selectSortedByName() {
        String sql = "SELECT * FROM SanPham WHERE TrangThai = 1 ORDER BY TenSP ASC";
        return select(sql);
    }

    public List<SanPham> selectSortedByNameDesc() {
        String sql = "SELECT * FROM SanPham WHERE TrangThai = 1 ORDER BY TenSP DESC";
        return select(sql);
    }

    public List<SanPham> selectSortedByDonGiaAsc() {
        String sql = "SELECT * FROM SanPham WHERE TrangThai = 1 ORDER BY DonGia ASC";
        return select(sql);
    }

    public List<SanPham> selectSortedByDonGiaDesc() {
        String sql = "SELECT * FROM SanPham WHERE TrangThai = 1 ORDER BY DonGia DESC";
        return select(sql);
    }

    public List<SanPham> selectSortedByStatus0() {
        String sql = "SELECT * FROM SanPham WHERE TrangThai = 0";
        return select(sql);
    }

    public List<SanPham> selectSortedByStatus1() {
        String sql = "SELECT * FROM SanPham WHERE TrangThai = 1";
        return select(sql);
    }

    public SanPham findById(String maSP) {
        String sql = "SELECT * FROM SanPham WHERE MaSP=?";
        List<SanPham> list = select(sql, maSP);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<SanPham> selectListKey(String key) {
        String sql = "SELECT * FROM SanPham WHERE TenSP LIKE ? OR MaDM LIKE ? OR ThuongHieu LIKE ? WHERE TrangThai = 1";
        String likeKeyword = "%" + key + "%";
        return select(sql, likeKeyword, likeKeyword, likeKeyword);
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

    public List<SanPham> selectProductInfo() {
        String sql = "SELECT MaSP, MaDM, TenSP, ThuongHieu, DonGia FROM SanPham WHERE TrangThai = 1";
        return selectProductInfo(sql);
    }

    private List<SanPham> selectProductInfo(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    SanPham model = readProductInfoFromResultSet(rs);
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

    private SanPham readProductInfoFromResultSet(ResultSet rs) throws SQLException {
        SanPham model = new SanPham();
        model.setMaSP(rs.getString("MaSP"));
        model.setMaDM(rs.getString("MaDM"));
        model.setTenSP(rs.getString("TenSP"));
        model.setThuongHieu(rs.getString("ThuongHieu"));
        model.setDonGia(rs.getFloat("DonGia"));
        return model;
    }
}
