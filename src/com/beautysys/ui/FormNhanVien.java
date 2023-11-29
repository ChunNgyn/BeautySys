/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.beautysys.ui;

import com.beautysys.DAO.NhanVienDAO;
import com.beautysys.Entity.NhanVien;
import com.beautysys.helper.DateHelper;
import com.beautysys.helper.DialogHelper;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author taikh
 */
public class FormNhanVien extends javax.swing.JFrame {

    int index = -1;
    NhanVienDAO dao = new NhanVienDAO();

    /**
     * Creates new form FormNhanVien
     */
    public FormNhanVien() {
        initComponents();
        setExtendedState(FormNhanVien.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
    
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    private String TaoMaHD() {
        String MaNVCuoi = dao.MaNVCuoi();
        if (MaNVCuoi == null) {
            return "NV001";
        }
        int lastNumber = Integer.parseInt(MaNVCuoi.substring(2));
        int newNumber = lastNumber + 1;
        String MaHDMoi = String.format("NV%03d", newNumber);
        return MaHDMoi;
    }
    public boolean check() {

        if (txtEmail.getText().equals("") || txtNgaySinh.getText().equals("") || txtMaNV.getText().equals("") || txtPassword.getText().equals("") || txtSdt.getText().equals("") || txtTenNv.getText().equals("") || txtUsername.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Hãy nhập đủ dữ liệu sau đó ấn Save", "Error", 1);
            return false;

        } else if (!jRadioButton1.isSelected() && !jRadioButton2.isSelected()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn Vai trò", "Error", 1);
            return false;
        } else if (!(txtMaNV.getText()).matches("NV[0-9]{1,5}")) {
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng mã \n VD : NV01", "Error", 1);
            txtMaNV.requestFocus();
            return false;
        }
        List<NhanVien> list = dao.select();
        for (int i = 0; i < list.size(); i++) {
            if (txtUsername.getText().equalsIgnoreCase(list.get(i).getTenTK())) {
                JOptionPane.showMessageDialog(this, "Trùng Mã Nhân Viên", "Error", 1);
                return false;
            }
        }
        return true;
    }

    ;
    public boolean checkUpdate() {

        if (txtEmail.getText().equals("") || txtNgaySinh.getText().equals("") || txtMaNV.getText().equals("") || txtPassword.getText().equals("") || txtSdt.getText().equals("") || txtTenNv.getText().equals("") || txtUsername.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Hãy nhập đủ dữ liệu sau đó ấn Save", "Error", 1);
            return false;

        } else if (!jRadioButton1.isSelected() && !jRadioButton2.isSelected()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn tình trạng", "Error", 1);
            return false;
        } else if (!(txtMaNV.getText()).matches("NV[0-9]{1,5}")) {
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng mã \n VD : NV01", "Error", 1);
            txtMaNV.requestFocus();
            return false;
        }
        return true;
    }

    ;
    
    void load() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        tblNhanVien.setDefaultEditor(Object.class, null);
        try {
            List<NhanVien> list = dao.select();
            for (NhanVien nv : list) {
                Object[] row = {
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getEmail(),
                    nv.getSDT(),
                    DateHelper.toString(nv.getNgaySinh()),
                    nv.getChucVu() ? "Nhân viên" : "Quản lý",
                    nv.getTrangThai() ? "Hoạt động" : "Nghỉ việc",
                    nv.getTenTK(),
                    nv.getMatKhau()
                };
                model.addRow(row);
                model.fireTableDataChanged();
                ListSelectionModel selectionModel;
                selectionModel = tblNhanVien.getSelectionModel();
                selectionModel.setSelectionInterval(index, index);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void edit() {
        try {
            String manv = (String) tblNhanVien.getValueAt(this.index, 0);
            NhanVien model = dao.findById(manv);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception a) {
            DialogHelper.alert(this, "lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        NhanVien model = getModel();
        // String confirm = new String(txtxacnhanmk.getPassword());
        if (check()) {
            try {
                dao.insert(model);
                this.load();
                this.clear();
                DialogHelper.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Thêm mới thất bại!");
            }
        } else {
            DialogHelper.alert(this, "xác Nhập mật khẩu không đúng!");
        }
    }

    void update() {
        NhanVien model = getModel();
        if (checkUpdate()) {
            try {
                dao.update(model);
                this.load();
                DialogHelper.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa nhân viên này?")) {
            String manv = txtMaNV.getText();
            try {
                dao.delete(manv);
                this.load();
                this.clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    void setModel(NhanVien model) {
        txtMaNV.setText(model.getMaNV());
        txtTenNv.setText(model.getTenNV());
        txtEmail.setText(model.getEmail());
        txtSdt.setText(model.getSDT());
        txtNgaySinh.setText(DateHelper.toString(model.getNgaySinh()));
        if (model.getChucVu() == true) {
            jcbChucvu.setSelectedIndex(1);
        } else {
            jcbChucvu.setSelectedIndex(0);
        }
        jRadioButton1.setSelected(model.getTrangThai());
        jRadioButton2.setSelected(!model.getTrangThai());
        txtUsername.setText(model.getTenTK());
        txtPassword.setText(model.getMatKhau());

    }

    NhanVien getModel() {
        NhanVien model = new NhanVien();
        model.setMaNV(txtMaNV.getText());
        model.setTenNV(txtTenNv.getText());
        model.setEmail(txtEmail.getText());
        model.setSDT(txtSdt.getText());
        model.setNgaySinh(DateHelper.toDate(txtNgaySinh.getText()));
        String selectedChucVu = (String) jcbChucvu.getSelectedItem();
        if ("Quản lý".equals(selectedChucVu)) {
            model.setChucVu(false);
        } else {
            model.setChucVu(true);
        }
        model.setTrangThai(jRadioButton1.isSelected());
        model.setTenTK(txtUsername.getText());
        model.setMatKhau(new String(txtPassword.getPassword()));
        return model;
    }

    void clear() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(TaoMaHD());
        nv.setChucVu(true);
        nv.setTrangThai(true);
        nv.setNgaySinh(DateHelper.now());
        this.setModel(nv);
        this.setStatus(true);
        jRadioButton1.setSelected(true);
        index = -1;
        this.load();
    }

    void FindByid() {
        String keyword = txtKeySearch.getText();
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = dao.selectListKey(keyword);
            for (NhanVien nv : list) {
                Object[] row = {
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getEmail(),
                    nv.getSDT(),
                    DateHelper.toString(nv.getNgaySinh()),
                    nv.getChucVu() ? "Nhân viên" : "Quản lý",
                    nv.getTrangThai() ? "Hoạt động" : "Nghỉ việc",
                    nv.getTenTK(),
                    nv.getMatKhau()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setStatus(boolean insertable) {
        txtMaNV.setEditable(insertable);
        btnThemNhanVien.setEnabled(insertable);
        btnUpdated.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tblNhanVien.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPre.setEnabled(!insertable && first);
        btnNext.setEnabled(!insertable && last);
        btnLast.setEnabled(!insertable && last);
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
        jPanel2 = new javax.swing.JPanel();
        btnUpdated = new lgnvswing.lgnvButton();
        btnThemNhanVien = new lgnvswing.lgnvButton();
        btnNew = new lgnvswing.lgnvButton();
        btnDelete = new lgnvswing.lgnvButton();
        txtKeySearch = new lgnvswing.lgnvTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new lgnvswing.lgnvTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTenNv = new lgnvswing.lgnvTextField();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new lgnvswing.lgnvTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtSdt = new lgnvswing.lgnvTextField();
        txtNgaySinh = new lgnvswing.lgnvTextField();
        txtUsername = new lgnvswing.lgnvTextField();
        jLabel13 = new javax.swing.JLabel();
        txtPassword = new lgnvswing.lgnvTextField();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jcbChucvu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel1.setText("THÔNG TIN NHÂN VIÊN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(485, 485, 485)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnUpdated.setBackground(new java.awt.Color(66, 139, 202));
        btnUpdated.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdated.setText("Sửa");
        btnUpdated.setLGNV_ShadowAllow(true);
        btnUpdated.setLGNV_ShadownColor(50);
        btnUpdated.setLGNV_borderRadius(60);
        btnUpdated.setLGNV_lineAllow(false);
        btnUpdated.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdated.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatedActionPerformed(evt);
            }
        });

        btnThemNhanVien.setBackground(new java.awt.Color(66, 139, 202));
        btnThemNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnThemNhanVien.setText("Thêm Mới");
        btnThemNhanVien.setLGNV_ShadowAllow(true);
        btnThemNhanVien.setLGNV_ShadownColor(50);
        btnThemNhanVien.setLGNV_borderRadius(60);
        btnThemNhanVien.setLGNV_lineAllow(false);
        btnThemNhanVien.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanVienActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(66, 139, 202));
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("Mới");
        btnNew.setLGNV_ShadowAllow(true);
        btnNew.setLGNV_ShadownColor(50);
        btnNew.setLGNV_borderRadius(60);
        btnNew.setLGNV_lineAllow(false);
        btnNew.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(66, 139, 202));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Xóa");
        btnDelete.setLGNV_ShadowAllow(true);
        btnDelete.setLGNV_ShadownColor(50);
        btnDelete.setLGNV_borderRadius(60);
        btnDelete.setLGNV_lineAllow(false);
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnThemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdated, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdated, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtKeySearch.setLGNV_ShaDownAllow(true);
        txtKeySearch.setLGNV_iconLeftPadding(15);
        txtKeySearch.setLGNV_placeholderText("Nhập từ khóa tìm kiếm.....");
        txtKeySearch.setLGNV_radius(60);
        txtKeySearch.setLGNV_textPaddingL(30);
        txtKeySearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKeySearchKeyPressed(evt);
            }
        });

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Họ và Tên", "Email", "SĐT", "Ngày sinh", "Chức vụ", "Trạng thái", "Username", "Password"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPre.setText("<<");
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnFirst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(btnNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLast)
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFirst, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(btnPre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel4.setText("Email");

        txtMaNV.setLGNV_maxCharLen(12);
        txtMaNV.setLGNV_placeholderText("");
        txtMaNV.setEnabled(false);
        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });

        jLabel5.setText("Tên Nhân Viên");

        txtTenNv.setLGNV_maxCharLen(30);
        txtTenNv.setLGNV_placeholderText("Nhập tên nhân viên....");
        txtTenNv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNvActionPerformed(evt);
            }
        });

        jLabel6.setText("Mã Nhân Viên");

        txtEmail.setLGNV_maxCharLen(30);
        txtEmail.setLGNV_placeholderText("Nhập Email....");
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel7.setText("Số Điện Thoại");

        jLabel8.setText("Tên Tài Khoản");

        jLabel10.setText("Trạng thái");

        jLabel11.setText("Ngày Sinh");

        jLabel12.setText("Chức vụ");

        txtSdt.setLGNV_maxCharLen(12);
        txtSdt.setLGNV_placeholderText("Nhập số điện thoại....");
        txtSdt.setLGNV_textType(lgnvswing.lgnvTextField.textTypeEnum.NUMBER);
        txtSdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtActionPerformed(evt);
            }
        });

        txtNgaySinh.setLGNV_maxCharLen(12);
        txtNgaySinh.setLGNV_placeholderText("Nhập mm - dd - yyyy....");
        txtNgaySinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgaySinhActionPerformed(evt);
            }
        });

        txtUsername.setLGNV_maxCharLen(12);
        txtUsername.setLGNV_placeholderText("Nhập UserName....");
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        jLabel13.setText("Mật Khẩu");

        txtPassword.setLGNV_maxCharLen(12);
        txtPassword.setLGNV_placeholderText("Nhập Password....");
        txtPassword.setLGNV_textType(lgnvswing.lgnvTextField.textTypeEnum.PASSWORD);
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Đã nghỉ");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Đang hoạt động");

        jcbChucvu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quản lý", "Nhân Viên" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(365, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenNv, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbChucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(285, 285, 285))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenNv, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbChucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdatedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatedActionPerformed
        this.update();
    }//GEN-LAST:event_btnUpdatedActionPerformed

    private void btnThemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanVienActionPerformed
        // TODO add your handling code here:
        if (check()) {
            this.insert();
        }
    }//GEN-LAST:event_btnThemNhanVienActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        this.clear();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        String checkmanv = txtUsername.getText();
        if (checkmanv != null) {
            this.delete();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        this.index = 0;
        this.load();
        this.edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        // TODO add your handling code here:
        this.index--;
        this.load();
        this.edit();
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        this.index++;
        this.load();
        this.edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        this.index = (tblNhanVien.getRowCount() - 1);
        this.load();
        this.edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        index = 0;
        this.load();
        this.edit();
        this.setStatus(false);
    }//GEN-LAST:event_formWindowOpened

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVActionPerformed

    private void txtTenNvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNvActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtSdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSdtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSdtActionPerformed

    private void txtNgaySinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgaySinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgaySinhActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        this.index = tblNhanVien.getSelectedRow();
        if (this.index >= 0) {
            this.edit();
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void txtKeySearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKeySearchKeyPressed
        // TODO add your handling code here:
        FindByid();
    }//GEN-LAST:event_txtKeySearchKeyPressed

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
            java.util.logging.Logger.getLogger(FormNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormNhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private lgnvswing.lgnvButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private lgnvswing.lgnvButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private lgnvswing.lgnvButton btnThemNhanVien;
    private lgnvswing.lgnvButton btnUpdated;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jcbChucvu;
    private javax.swing.JTable tblNhanVien;
    public lgnvswing.lgnvTextField txtEmail;
    private lgnvswing.lgnvTextField txtKeySearch;
    public lgnvswing.lgnvTextField txtMaNV;
    public lgnvswing.lgnvTextField txtNgaySinh;
    public lgnvswing.lgnvTextField txtPassword;
    public lgnvswing.lgnvTextField txtSdt;
    public lgnvswing.lgnvTextField txtTenNv;
    public lgnvswing.lgnvTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
