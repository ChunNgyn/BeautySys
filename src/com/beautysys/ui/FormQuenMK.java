/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.beautysys.ui;

import com.beautysys.DAO.NhanVienDAO;
import com.beautysys.helper.DialogHelper;
import com.beautysys.helper.ShareHelper;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author admin
 */
public class FormQuenMK extends javax.swing.JDialog {

    NhanVienDAO dao = new NhanVienDAO();
    private String otp;
    private String userEmail;

    private enum FormState {
        ENTER_EMAIL,
        ENTER_OTP,
        RESET_PASSWORD
    }
    private FormState currentState;

    boolean validatePasswords() {
        String MK = new String(txtMK.getPassword()).trim();
        String XNMK = new String(txtXNMK.getPassword()).trim();

        if (MK.isEmpty() || XNMK.isEmpty()) {
            DialogHelper.alert(this, "Vui lòng nhập đầy đủ thông tin.");
            return false;
        }

        if (!MK.equals(XNMK)) {
            DialogHelper.alert(this, "Xác nhận mật khẩu mới không khớp.");
            return false;
        }

        return true;
    }

    void changePassword() {
        if (!validatePasswords()) {
            return;
        }

        String tenTK = ShareHelper.USER.getTenTK();
        String matKhauMoi = new String(txtMK.getPassword());

        try {
            dao.updatePassword(tenTK, matKhauMoi);
            DialogHelper.alert(this, "Đổi mật khẩu thành công!");
            this.dispose();
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void sendOTPEmail(String email) {
        otp = generateOTP();

        String fromEmail = "trungthien221003@gmail.com";
        String password = "sqok lnrf nasj wosh";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Your OTP Code");

            message.setText("Your OTP code is: " + otp);

            Transport.send(message);

            System.out.println("Email sent successfully with OTP.");

            currentState = FormState.ENTER_OTP;
            updateUI();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private boolean verifyOTP(String userEnteredOTP) {
        return userEnteredOTP.equals(otp);
    }

    private String generateOTP() {
        String numbers = "1234567890";
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int index = new Random().nextInt(numbers.length());
            sb.append(numbers.charAt(index));
        }

        return sb.toString();
    }

    private void updateUI() {
        pnlTong.removeAll();

        switch (currentState) {
            case ENTER_EMAIL:
                pnlTong.add(pnlGuiMa);
                break;
            case ENTER_OTP:
                pnlTong.add(pnlNhapMaXN);
                break;
            case RESET_PASSWORD:
                pnlTong.add(pnlDoiMK);
                break;
        }

        pnlTong.repaint();
        pnlTong.revalidate();
    }

    /**
     * Creates new form FormQuenMK1
     */
    public FormQuenMK(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        currentState = FormState.ENTER_EMAIL;
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
        jSeparator1 = new javax.swing.JSeparator();
        pnlTong = new javax.swing.JPanel();
        pnlGuiMa = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnGuiMa = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        pnlNhapMaXN = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtOTP = new javax.swing.JTextField();
        btnHuy = new javax.swing.JButton();
        btnNhapMaXN = new javax.swing.JButton();
        pnlDoiMK = new javax.swing.JPanel();
        btnDoiMK = new javax.swing.JButton();
        btnHuy2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMK = new javax.swing.JPasswordField();
        txtXNMK = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUÊN MẬT KHẨU");

        jSeparator1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        pnlTong.setBackground(new java.awt.Color(255, 0, 51));
        pnlTong.setLayout(new java.awt.CardLayout());

        jLabel2.setText("Nhập Email xác nhận:");

        btnGuiMa.setText("Gửi mã");
        btnGuiMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiMaActionPerformed(evt);
            }
        });

        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlGuiMaLayout = new javax.swing.GroupLayout(pnlGuiMa);
        pnlGuiMa.setLayout(pnlGuiMaLayout);
        pnlGuiMaLayout.setHorizontalGroup(
            pnlGuiMaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnlGuiMaLayout.createSequentialGroup()
                .addGroup(pnlGuiMaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGuiMaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(pnlGuiMaLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnGuiMa)
                        .addGap(18, 18, 18)
                        .addComponent(btnThoat)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        pnlGuiMaLayout.setVerticalGroup(
            pnlGuiMaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGuiMaLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlGuiMaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuiMa)
                    .addComponent(btnThoat))
                .addGap(44, 44, 44))
        );

        pnlTong.add(pnlGuiMa, "card2");

        jLabel3.setText("Nhập mã xác nhận");

        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnNhapMaXN.setText("Tiếp tục");
        btnNhapMaXN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapMaXNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNhapMaXNLayout = new javax.swing.GroupLayout(pnlNhapMaXN);
        pnlNhapMaXN.setLayout(pnlNhapMaXNLayout);
        pnlNhapMaXNLayout.setHorizontalGroup(
            pnlNhapMaXNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtOTP, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnlNhapMaXNLayout.createSequentialGroup()
                .addGroup(pnlNhapMaXNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNhapMaXNLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(pnlNhapMaXNLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btnHuy)
                        .addGap(18, 18, 18)
                        .addComponent(btnNhapMaXN)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        pnlNhapMaXNLayout.setVerticalGroup(
            pnlNhapMaXNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNhapMaXNLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtOTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlNhapMaXNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy)
                    .addComponent(btnNhapMaXN))
                .addGap(44, 44, 44))
        );

        pnlTong.add(pnlNhapMaXN, "card3");

        btnDoiMK.setText("Tiếp tục");
        btnDoiMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMKActionPerformed(evt);
            }
        });

        btnHuy2.setText("Hủy");
        btnHuy2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuy2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Mật khẩu mới");

        jLabel5.setText("Xác nhận mật khẩu mới");

        javax.swing.GroupLayout pnlDoiMKLayout = new javax.swing.GroupLayout(pnlDoiMK);
        pnlDoiMK.setLayout(pnlDoiMKLayout);
        pnlDoiMKLayout.setHorizontalGroup(
            pnlDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDoiMKLayout.createSequentialGroup()
                .addGroup(pnlDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDoiMKLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMK, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(txtXNMK)))
                    .addGroup(pnlDoiMKLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btnHuy2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDoiMK)))
                .addContainerGap())
        );
        pnlDoiMKLayout.setVerticalGroup(
            pnlDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDoiMKLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtXNMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy2)
                    .addComponent(btnDoiMK))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pnlTong.add(pnlDoiMK, "card3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuiMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiMaActionPerformed
        // TODO add your handling code here:
        userEmail = txtEmail.getText().trim();

        if (userEmail.isEmpty()) {
            DialogHelper.alert(this, "Vui lòng nhập địa chỉ email.");
            return;
        }

        // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
        if (!dao.isEmailExist(userEmail)) {
            DialogHelper.alert(this, "Email không tồn tại trong hệ thống.");
            return;
        }

        sendOTPEmail(userEmail);
    }//GEN-LAST:event_btnGuiMaActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        if (currentState == FormState.ENTER_OTP) {
            currentState = FormState.ENTER_EMAIL;
            updateUI();
            txtEmail.setText("");
        }
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnNhapMaXNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapMaXNActionPerformed
        // TODO add your handling code here:
        String userEnteredOTP = txtOTP.getText();

        // Kiểm tra xem mã OTP nhập vào có đúng không
        if (verifyOTP(userEnteredOTP)) {
            // Nếu mã OTP đúng, chuyển sang trạng thái đổi mật khẩu
            currentState = FormState.RESET_PASSWORD;
            updateUI();
        } else {
            // Nếu mã OTP sai, hiển thị thông báo lỗi và cho phép nhập lại
            DialogHelper.alert(this, "Sai mã OTP, vui lòng thử lại");
            txtOTP.setText(""); // Xóa trường nhập liệu để người dùng nhập lại
        }
    }//GEN-LAST:event_btnNhapMaXNActionPerformed

    private void btnDoiMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMKActionPerformed
        // TODO add your handling code here:
        changePassword();
    }//GEN-LAST:event_btnDoiMKActionPerformed

    private void btnHuy2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuy2ActionPerformed
        // TODO add your handling code here:
        if (currentState == FormState.RESET_PASSWORD) {
            currentState = FormState.ENTER_EMAIL;
            updateUI();
            txtEmail.setText("");
        }
    }//GEN-LAST:event_btnHuy2ActionPerformed

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
            java.util.logging.Logger.getLogger(FormQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormQuenMK dialog = new FormQuenMK(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoiMK;
    private javax.swing.JButton btnGuiMa;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnHuy2;
    private javax.swing.JButton btnNhapMaXN;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pnlDoiMK;
    private javax.swing.JPanel pnlGuiMa;
    private javax.swing.JPanel pnlNhapMaXN;
    private javax.swing.JPanel pnlTong;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtMK;
    private javax.swing.JTextField txtOTP;
    private javax.swing.JPasswordField txtXNMK;
    // End of variables declaration//GEN-END:variables
}
