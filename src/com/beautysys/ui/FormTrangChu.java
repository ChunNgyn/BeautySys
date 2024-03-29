/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.beautysys.ui;

import com.beautysys.helper.DialogHelper;
import com.beautysys.helper.ShareHelper;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;

/**
 *
 * @author admin
 */
public class FormTrangChu extends javax.swing.JFrame {

    CardLayout CL;

    /**
     * Creates new form FormTrangChu
     */
    public FormTrangChu() {
        initComponents();
        setExtendedState(FormTrangChu.MAXIMIZED_BOTH);
        init();
    }

    void OpenNVForm() {
        CL.show(jPanel2, "card3");
    }

    void OpenQLForm() {
        CL.show(jPanel2, "card2");
    }

    void init() {
        CL = (CardLayout) jPanel2.getLayout();
        new Timer(1000, new ActionListener() {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");

            @Override
            public void actionPerformed(ActionEvent e) {
                lblDH1.setText(format.format(new Date()));
                lblDH2.setText(format.format(new Date()));
            }
        }).start();
        this.openWelcome();
        this.openLogin();
        lblUsername.setText("Xin chào: "+ShareHelper.USER.getTenNV());
        lblUsername.setText("Xin chào: "+ShareHelper.USER.getTenNV());
    }

    void openWelcome() {
        new FormKhoiDong(this, true).setVisible(true);
    }

    void openLogin() {
        new FormDangNhap(this, true).setVisible(true);
        if (ShareHelper.USER.getChucVu() == true) {
            OpenQLForm();
        } else {
            OpenNVForm();
        }
    }

    void openCTKM() {
        if (ShareHelper.authenticated()) {
            new FormCTKM().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }

    void openQLHD() {
        if (ShareHelper.authenticated()) {
            new FormQLHoaDon().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }

    void openQLNH() {
        if (ShareHelper.authenticated()) {
            new FormQLNhapHang().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }

    void openQLTH() {
        if (ShareHelper.authenticated()) {
            new FormQLTraHang().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }

    void openQLDMSP() {
        if (ShareHelper.authenticated()) {
            new FormDanhMucSanPham().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }

    void openQLSP() {
        if (ShareHelper.authenticated()) {
            new FormSanPham().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }

    void openThongKe() {
        if (ShareHelper.authenticated()) {
            new FormThongKe().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }
    void openBanHang() {
        if (ShareHelper.authenticated()) {
            new FormBanHang().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }
    void openNhanVien() {
        if (ShareHelper.authenticated()) {
            new FormNhanVien().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        QLBH = new javax.swing.JButton();
        btnQLSP = new javax.swing.JButton();
        btnQLNV = new javax.swing.JButton();
        btnQLHD = new javax.swing.JButton();
        btnQLNH = new javax.swing.JButton();
        btnQLTH = new javax.swing.JButton();
        btnQLCTKM = new javax.swing.JButton();
        btnTKDT = new javax.swing.JButton();
        lblDH1 = new javax.swing.JLabel();
        btnQLDM1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblUsername2 = new javax.swing.JLabel();
        btnQLSP2 = new javax.swing.JButton();
        QLHD2 = new javax.swing.JButton();
        btnQLTH2 = new javax.swing.JButton();
        btnTKDT2 = new javax.swing.JButton();
        lblDH2 = new javax.swing.JLabel();
        btnQLBH2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tổng Quan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
        );

        jPanel2.setLayout(new java.awt.CardLayout());

        lblUsername.setText("Xin chào: null");

        QLBH.setText("Quản lý bán hàng");
        QLBH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        QLBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QLBHActionPerformed(evt);
            }
        });

        btnQLSP.setText("Quản lý sản phẩm");
        btnQLSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnQLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSPActionPerformed(evt);
            }
        });

        btnQLNV.setText("Quản lý thông tin nhân viên");
        btnQLNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnQLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLNVActionPerformed(evt);
            }
        });

        btnQLHD.setText("Quản lý hóa đơn");
        btnQLHD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnQLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLHDActionPerformed(evt);
            }
        });

        btnQLNH.setText("Quản lý nhập hàng");
        btnQLNH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnQLNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLNHActionPerformed(evt);
            }
        });

        btnQLTH.setText("Quản lý trả hàng");
        btnQLTH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnQLTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLTHActionPerformed(evt);
            }
        });

        btnQLCTKM.setText("Quản lý chương trình khuyến mãi");
        btnQLCTKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnQLCTKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLCTKMActionPerformed(evt);
            }
        });

        btnTKDT.setText("Thống kê doanh thu");
        btnTKDT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnTKDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKDTActionPerformed(evt);
            }
        });

        lblDH1.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        lblDH1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDH1.setText("12:00:00 PM");

        btnQLDM1.setText("Quản lý danh mục");
        btnQLDM1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnQLDM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLDM1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1248, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblDH1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnQLNH, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnQLTH, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnQLCTKM, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnTKDT, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(QLBH, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnQLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnQLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnQLDM1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(0, 836, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 802, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(QLBH, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnQLDM1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnQLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnQLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnQLNH, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnQLTH, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnQLCTKM, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnTKDT, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lblDH1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel2.add(jPanel4, "card2");

        lblUsername2.setText("Xin chào: null");

        btnQLSP2.setText("Quản lý sản phẩm");
        btnQLSP2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnQLSP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSP2ActionPerformed(evt);
            }
        });

        QLHD2.setText("Quản lý hóa đơn");
        QLHD2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        QLHD2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QLHD2ActionPerformed(evt);
            }
        });

        btnQLTH2.setText("Quản lý trả hàng");
        btnQLTH2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnQLTH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLTH2ActionPerformed(evt);
            }
        });

        btnTKDT2.setText("Thống kê doanh thu");
        btnTKDT2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnTKDT2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKDT2ActionPerformed(evt);
            }
        });

        lblDH2.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        lblDH2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDH2.setText("12:00:00 PM");

        btnQLBH2.setText("Quản lý bán hàng");
        btnQLBH2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnQLBH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLBH2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQLSP2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(QLHD2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQLTH2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsername2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTKDT2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQLBH2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(842, Short.MAX_VALUE))
            .addComponent(lblDH2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsername2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQLSP2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQLBH2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QLHD2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQLTH2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTKDT2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDH2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTKDT2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKDT2ActionPerformed
        // TODO add your handling code here:
        openThongKe();
        
    }//GEN-LAST:event_btnTKDT2ActionPerformed

    private void btnQLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLHDActionPerformed
        // TODO add your handling code here:
        openQLHD();
    }//GEN-LAST:event_btnQLHDActionPerformed

    private void btnTKDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKDTActionPerformed
        // TODO add your handling code here:
        openThongKe();
    }//GEN-LAST:event_btnTKDTActionPerformed

    private void QLBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QLBHActionPerformed
        // TODO add your handling code here:
        openBanHang();
    }//GEN-LAST:event_QLBHActionPerformed

    private void btnQLDM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLDM1ActionPerformed
        // TODO add your handling code here:
        openQLDMSP();
    }//GEN-LAST:event_btnQLDM1ActionPerformed

    private void btnQLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSPActionPerformed
        // TODO add your handling code here:
        openQLSP();
    }//GEN-LAST:event_btnQLSPActionPerformed

    private void btnQLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLNVActionPerformed
        // TODO add your handling code here:
        openNhanVien();
    }//GEN-LAST:event_btnQLNVActionPerformed

    private void btnQLNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLNHActionPerformed
        // TODO add your handling code here:
        openQLNH();
    }//GEN-LAST:event_btnQLNHActionPerformed

    private void btnQLTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLTHActionPerformed
        // TODO add your handling code here:
        openQLTH();
    }//GEN-LAST:event_btnQLTHActionPerformed

    private void btnQLCTKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLCTKMActionPerformed
        // TODO add your handling code here:
        openCTKM();
    }//GEN-LAST:event_btnQLCTKMActionPerformed

    private void btnQLSP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSP2ActionPerformed
        // TODO add your handling code here:
        openQLSP();
    }//GEN-LAST:event_btnQLSP2ActionPerformed

    private void btnQLBH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLBH2ActionPerformed
        // TODO add your handling code here:
        openBanHang();
    }//GEN-LAST:event_btnQLBH2ActionPerformed

    private void QLHD2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QLHD2ActionPerformed
        // TODO add your handling code here:
        openQLHD();
    }//GEN-LAST:event_QLHD2ActionPerformed

    private void btnQLTH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLTH2ActionPerformed
        // TODO add your handling code here:
        openQLTH();
    }//GEN-LAST:event_btnQLTH2ActionPerformed

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
            java.util.logging.Logger.getLogger(FormTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTrangChu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton QLBH;
    private javax.swing.JButton QLHD2;
    private javax.swing.JButton btnQLBH2;
    private javax.swing.JButton btnQLCTKM;
    private javax.swing.JButton btnQLDM1;
    private javax.swing.JButton btnQLHD;
    private javax.swing.JButton btnQLNH;
    private javax.swing.JButton btnQLNV;
    private javax.swing.JButton btnQLSP;
    private javax.swing.JButton btnQLSP2;
    private javax.swing.JButton btnQLTH;
    private javax.swing.JButton btnQLTH2;
    private javax.swing.JButton btnTKDT;
    private javax.swing.JButton btnTKDT2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblDH1;
    private javax.swing.JLabel lblDH2;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblUsername2;
    // End of variables declaration//GEN-END:variables
}
