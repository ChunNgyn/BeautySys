/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.beautysys.ui;

import com.beautysys.DAO.ChiTietHoaDonDAO;
import com.beautysys.DAO.HoaDonDAO;
import com.beautysys.DAO.KhuyenMaiDAO;
import com.beautysys.DAO.SanPhamDAO;
import com.beautysys.Entity.ChiTietHoaDon;
import com.beautysys.Entity.HoaDon;
import com.beautysys.Entity.KhuyenMai;
import com.beautysys.Entity.SanPham;
import com.beautysys.helper.DateHelper;
import com.beautysys.helper.DialogHelper;
import com.beautysys.helper.ShareHelper;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class FormBanHang extends javax.swing.JFrame {
    SanPhamDAO daoSP = new SanPhamDAO();
    KhuyenMaiDAO daoKM = new KhuyenMaiDAO();
    HoaDonDAO daoHD = new HoaDonDAO();
    ChiTietHoaDonDAO daoCTHD = new ChiTietHoaDonDAO();
    /**
     * Creates new form FormBanHang
     */
    private boolean CheckGioHang() {
        DefaultTableModel donHangModel = (DefaultTableModel) tblDonHang.getModel();
        return donHangModel.getRowCount() == 0;
    }
    private String TaoMaCTHD() {
        String MaCTHD = daoCTHD.MaCTHDCuoi();
        if (MaCTHD == null) {
            return "CTHD001";
        }
        int lastNumber = Integer.parseInt(MaCTHD.substring(4));
        int newNumber = lastNumber + 1;
        String MaHDMoi = String.format("CTHD%03d", newNumber);
        return MaHDMoi;
    }
    private String TaoMaHD() {
        String MaHDCuoi = daoHD.MaHDCuoi();
        if (MaHDCuoi == null) {
            return "HD001";
        }
        int lastNumber = Integer.parseInt(MaHDCuoi.substring(2));
        int newNumber = lastNumber + 1;
        String MaHDMoi = String.format("HD%03d", newNumber);
        return MaHDMoi;
    }
    void TaoCTHD(String maHD) {
        DefaultTableModel donHangModel = (DefaultTableModel) tblDonHang.getModel();
        for (int i = 0; i < donHangModel.getRowCount(); i++) {
            String maCTHD = TaoMaCTHD();
            ChiTietHoaDon CTHD = new ChiTietHoaDon();
            String maSP = (String) donHangModel.getValueAt(i, 0);
            int soLuong = (int) donHangModel.getValueAt(i, 2);
            float donGia = (float) donHangModel.getValueAt(i, 1);

            // Set các thông tin cho chi tiết hóa đơn
            CTHD.setMaCTHD(maCTHD);
            CTHD.setMaHD(maHD);
            CTHD.setMaSP(maSP);
            CTHD.setSoLuong(soLuong);
            CTHD.setDonGia(donGia);

            // Thực hiện insert vào database
            daoCTHD.insert(CTHD);
        }
    }
    void TaoHD() {
        String maHD = TaoMaHD();
        String maNV = "NV001";
        String maKM = cbbKM.getSelectedIndex() == 0 ? null : daoKM.findByTenKM(cbbKM.getSelectedItem().toString()).getMaKM();
        Date ngayLap = DateHelper.now();
        Float tongSoTien = 0.0f;
        String trangThai = "Đã thanh toán";
        String phuongThucTT = "";
        if (rdoMomo.isSelected()) {
            phuongThucTT = "Momo";
        } else if (rdoCC.isSelected()) {
            phuongThucTT = "Credit Card";
        } else if (rdoTM.isSelected()) {
            phuongThucTT = "Tiền mặt";
        }
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHD(maHD);
        hoaDon.setMaNV(maNV);
        hoaDon.setMaKM(maKM);
        hoaDon.setNgayLap(ngayLap);
        hoaDon.setTongSoTien(tongSoTien);
        hoaDon.setTrangThai(trangThai);
        hoaDon.setPhuongThucTT(phuongThucTT);
        daoHD.insert(hoaDon);
        TaoCTHD(maHD);
        DialogHelper.alert(this, "Thanh toán thành công!");
    }

    void fillKhuyenMaiComboBox() {
        List<KhuyenMai> list = daoKM.selectNotExpired();
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        comboBoxModel.addElement("(Không mã KM)");
        for (KhuyenMai khuyenMai : list) {
            comboBoxModel.addElement(khuyenMai.getTenKM());
        }
        cbbKM.setModel(comboBoxModel);
    }
    void TinhSoTien() {
        DefaultTableModel donHangModel = (DefaultTableModel) tblDonHang.getModel();

        Float tongGiaTri = 0.0f;

        if (donHangModel.getRowCount() > 0) {
            for (int i = 0; i < donHangModel.getRowCount(); i++) {
                int soLuong = (int) donHangModel.getValueAt(i, 2);
                Float donGia = (Float) donHangModel.getValueAt(i, 1);
                tongGiaTri += soLuong * donGia;
            }
            NumberFormat NF = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String tienVN = NF.format(tongGiaTri);
            txtTongSoTien.setText(String.valueOf(tienVN));
        } else {
            txtTongSoTien.setText("0");
        }
    }
    void ThemSP() {
        DefaultTableModel donHangModel = (DefaultTableModel) tblDonHang.getModel();
        DefaultTableModel sanPhamModel = (DefaultTableModel) tblSanPham.getModel();

        // Lấy dòng được chọn từ tblSanPham
        int selectedRow = tblSanPham.getSelectedRow();

        if (selectedRow != -1) {
            // Lấy mã sản phẩm từ dòng được chọn trong tblSanPham
            Object maSPObject = sanPhamModel.getValueAt(selectedRow, 1);

            // Kiểm tra xem giá trị có null không
            if (maSPObject != null) {
                Object maSP = maSPObject;

                // Kiểm tra xem mã sản phẩm đã tồn tại trong tblDonHang chưa
                boolean isDuplicated = false;
                int duplicatedRowIndex = -1;
                for (int i = 0; i < donHangModel.getRowCount(); i++) {
                    if (maSP.equals(donHangModel.getValueAt(i, 0))) {
                        isDuplicated = true;
                        duplicatedRowIndex = i;
                        break;
                    }
                }

                if (isDuplicated) {
                    // Nếu sản phẩm đã tồn tại, tăng số lượng lên 1
                    int currentQuantity = (int) donHangModel.getValueAt(duplicatedRowIndex, 2);
                    donHangModel.setValueAt(currentQuantity + 1, duplicatedRowIndex, 2);
                } else {
                    // Nếu không trùng lặp, thêm dòng mới vào tblDonHang
                    Object[] rowData = {
                        maSP, // Mã sản phẩm
                        sanPhamModel.getValueAt(selectedRow, 4), // Đơn giá
                        1 // Số lượng (mặc định là 1)
                    };
                    donHangModel.addRow(rowData);
                }
            } else {
                DialogHelper.alert(this, "Giá trị của mã sản phẩm là null.");
            }
        } else {
            DialogHelper.alert(this, "Vui lòng chọn sản phẩm từ danh sách.");
        }
        TinhSoTien();
    }
    void XoaSP() {
        DefaultTableModel donHangModel = (DefaultTableModel) tblDonHang.getModel();

        // Lấy dòng được chọn từ tblDonHang
        int selectedRow = tblDonHang.getSelectedRow();

        if (selectedRow != -1) {
            // Xóa dòng được chọn khỏi tblDonHang
            donHangModel.removeRow(selectedRow);
        } else {
            DialogHelper.alert(this, "Vui lòng chọn sản phẩm từ đơn hàng để xóa.");
        }
        TinhSoTien();
    }
    void FillTableSP() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        DefaultTableModel model2 = (DefaultTableModel) tblDonHang.getModel();
        model2.setRowCount(0);
        model.setRowCount(0);
        try {
            List<SanPham> list = daoSP.selectProductInfo();
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaDM(),
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getThuongHieu(),
                    sp.getDonGia()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    public FormBanHang() {
        initComponents();
        setExtendedState(FormBanHang.MAXIMIZED_BOTH);
        FillTableSP();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDonHang = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbbKM = new javax.swing.JComboBox<>();
        rdoMomo = new javax.swing.JRadioButton();
        rdoCC = new javax.swing.JRadioButton();
        rdoTM = new javax.swing.JRadioButton();
        btnThanhToan = new javax.swing.JButton();
        btnThemSP = new javax.swing.JButton();
        btnXoaSP = new javax.swing.JButton();
        txtTongSoTien = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BÁN HÀNG");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        tblDonHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Đơn giá", "Số lượng"
            }
        ));
        jScrollPane1.setViewportView(tblDonHang);

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Loại sản phẩm", "Mã sản phẩm", "Tên sản phẩm", "Thương Hiệu", "Đơn giá"
            }
        ));
        jScrollPane2.setViewportView(tblSanPham);

        jLabel2.setText("Mã KM:");

        jLabel3.setText("Phương thức:");

        cbbKM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buttonGroup1.add(rdoMomo);
        rdoMomo.setText("Momo");

        buttonGroup1.add(rdoCC);
        rdoCC.setText("Credit Card");

        buttonGroup1.add(rdoTM);
        rdoTM.setText("Tiền mặt");

        btnThanhToan.setBackground(new java.awt.Color(51, 102, 255));
        btnThanhToan.setFont(new java.awt.Font("Arial Black", 1, 20)); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnThemSP.setBackground(new java.awt.Color(51, 102, 255));
        btnThemSP.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnThemSP.setText(">>>");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnXoaSP.setBackground(new java.awt.Color(51, 102, 255));
        btnXoaSP.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnXoaSP.setText("<<<");
        btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPActionPerformed(evt);
            }
        });

        txtTongSoTien.setEditable(false);
        txtTongSoTien.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTongSoTien.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemSP, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnXoaSP, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoMomo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoCC)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoTM))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbbKM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTongSoTien, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(btnThemSP)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaSP))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbbKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongSoTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(rdoMomo)
                    .addComponent(rdoCC)
                    .addComponent(rdoTM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThanhToan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
            if (CheckGioHang()) {
            DialogHelper.alert(this, "Chưa có sản phẩm trong giỏ hàng. Vui lòng thêm sản phẩm trước khi thanh toán.");
            return;
        }
            TaoHD();
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.fillKhuyenMaiComboBox();
        this.FillTableSP();
        rdoTM.setSelected(true);
    }//GEN-LAST:event_formWindowOpened

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
        ThemSP();
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnXoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPActionPerformed
        // TODO add your handling code here:
        XoaSP();
    }//GEN-LAST:event_btnXoaSPActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormBanHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnXoaSP;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbKM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoCC;
    private javax.swing.JRadioButton rdoMomo;
    private javax.swing.JRadioButton rdoTM;
    private javax.swing.JTable tblDonHang;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtTongSoTien;
    // End of variables declaration//GEN-END:variables
}
