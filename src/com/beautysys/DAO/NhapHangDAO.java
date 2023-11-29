package com.beautysys.DAO;

import com.beautysys.Entity.NhapHang;
import com.beautysys.helper.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhapHangDAO {

    public void insert(NhapHang model) {
        String sql = "INSERT INTO NhapHang (MaNH, MaNV, NhaCC, NgayNhap, TongTien) VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaNH(),
                model.getMaNV(),
                model.getNhaCC(),
                model.getNgayNhap(),
                0);
    }

    public void update(NhapHang model) {
        String sql = "UPDATE NhapHang SET MaNV=?, NhaCC=?, NgayNhap=?, TongTien=? WHERE MaNH=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaNV(),
                model.getNhaCC(),
                model.getNgayNhap(),
                model.getTongTien(),
                model.getMaNH());
    }

    public void delete(String maNH) {
        String sql = "DELETE FROM NhapHang WHERE MaNH=?";
        JdbcHelper.executeUpdate(sql, maNH);
    }

    public List<NhapHang> select() {
        String sql = "SELECT * FROM NhapHang";
        return select(sql);
    }

    public NhapHang findById(String maNH) {
        String sql = "SELECT * FROM NhapHang WHERE MaNH=?";
        List<NhapHang> list = select(sql, maNH);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<NhapHang> select(String sql, Object... args) {
        List<NhapHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NhapHang model = readFromResultSet(rs);
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

    private NhapHang readFromResultSet(ResultSet rs) throws SQLException {
        NhapHang model = new NhapHang();
        model.setMaNH(rs.getString("MaNH"));
        model.setMaNV(rs.getString("MaNV"));
        model.setNhaCC(rs.getString("NhaCC"));
        model.setNgayNhap(rs.getDate("NgayNhap"));
        model.setTongTien(rs.getFloat("TongTien"));
        return model;
    }

    public List<NhapHang> findByKeyword(String maNH) {
        String sql = "SELECT * FROM NhapHang WHERE MaNH LIKE ? OR MaNV LIKE ? OR NgayNhap LIKE ?";
        return select(sql, "%" + maNH + "%", "%" + maNH + "%", "%" + maNH + "%");
    }
}
