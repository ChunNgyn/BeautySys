package com.beautysys.DAO;

import com.beautysys.Entity.DanhMucSanPham;
import com.beautysys.helper.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DanhMucSanPhamDAO {

    public void insert(DanhMucSanPham model) {
        String sql = "INSERT INTO DanhMucSanPham (MaDM, TenDanhMuc) VALUES (?, ?)";
        JdbcHelper.executeUpdate(sql, model.getMaDM(), model.getTenDM());
    }

    public void update(DanhMucSanPham model) {
        String sql = "UPDATE DanhMucSanPham SET TenDanhMuc=? WHERE MaDM=?";
        JdbcHelper.executeUpdate(sql, model.getTenDM(), model.getMaDM());
    }

    public void delete(String maDM) {
        String sql = "DELETE FROM DanhMucSanPham WHERE MaDM=?";
        JdbcHelper.executeUpdate(sql, maDM);
    }

    public List<DanhMucSanPham> select() {
        String sql = "SELECT * FROM DanhMucSanPham";
        return select(sql);
    }

    public DanhMucSanPham findById(String maDM) {
        String sql = "SELECT * FROM DanhMucSanPham WHERE MaDM=?";
        List<DanhMucSanPham> list = select(sql, maDM);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<DanhMucSanPham> select(String sql, Object... args) {
        List<DanhMucSanPham> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    DanhMucSanPham model = readFromResultSet(rs);
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

    private DanhMucSanPham readFromResultSet(ResultSet rs) throws SQLException {
        DanhMucSanPham model = new DanhMucSanPham();
        model.setMaDM(rs.getString("MaDM"));
        model.setTenDM(rs.getString("TenDM"));
        return model;
    }
}
