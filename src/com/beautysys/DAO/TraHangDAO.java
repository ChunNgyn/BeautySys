package com.beautysys.DAO;

import com.beautysys.Entity.TraHang;
import com.beautysys.helper.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TraHangDAO {

    public void insert(TraHang model) {
        String sql = "INSERT INTO PhieuTraHang (MaTH, MaNV, NgayTra, Lydo) VALUES (?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaTH(),
                model.getMaNV(),
                model.getNgayTra(),
                model.getLydo());
    }

    public void update(TraHang model) {
        String sql = "UPDATE PhieuTraHang SET MaNV=?, NgayTra=?, Lydo=? WHERE MaTH=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaNV(),
                model.getNgayTra(),
                model.getLydo(),
                model.getMaTH());
    }

    public void delete(String maTH) {
        String sql = "DELETE FROM PhieuTraHang WHERE MaTH=?";
        JdbcHelper.executeUpdate(sql, maTH);
    }

    public List<TraHang> select() {
        String sql = "SELECT * FROM PhieuTraHang";
        return select(sql);
    }

    public TraHang findById(String maTH) {
        String sql = "SELECT * FROM PhieuTraHang WHERE MaTH=?";
        List<TraHang> list = select(sql, maTH);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<TraHang> select(String sql, Object... args) {
        List<TraHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    TraHang model = readFromResultSet(rs);
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

    private TraHang readFromResultSet(ResultSet rs) throws SQLException {
        TraHang model = new TraHang();
        model.setMaTH(rs.getString("MaTH"));
        model.setMaNV(rs.getString("MaNV"));
        model.setNgayTra(rs.getDate("NgayTra"));
        model.setLydo(rs.getString("Lydo"));
        return model;
    }

    public List<TraHang> findByKeyword(String maTH) {
        String sql = "SELECT * FROM PhieuTraHang WHERE MaTH LIKE ? OR MaNV LIKE ? OR NgayTra LIKE ?";
        return select(sql, "%" + maTH + "%", "%" + maTH + "%", "%" + maTH + "%");
    }
}
