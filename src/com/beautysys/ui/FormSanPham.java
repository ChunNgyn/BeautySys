/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.beautysys.ui;

import com.beautysys.DAO.DanhMucSanPhamDAO;
import com.beautysys.DAO.SanPhamDAO;
import com.beautysys.Entity.DanhMucSanPham;
import com.beautysys.Entity.SanPham;
import com.beautysys.helper.DateHelper;
import com.beautysys.helper.DialogHelper;
import com.beautysys.helper.ShareHelper;
import java.awt.Image;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author taikh
 */
public class FormSanPham extends javax.swing.JFrame {

    int index = -1;
    SanPhamDAO dao = new SanPhamDAO();
    DanhMucSanPhamDAO dmspdao = new DanhMucSanPhamDAO();
    String imageName = null;

    /**
     * Creates new form FormNhanVien
     */
    public FormSanPham() {
        initComponents();
        setExtendedState(FormSanPham.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        FillCbbDm();
    }

    private String TaoMaSP() {
        String MaSPCuoi = dao.MaSPCuoi();
        if (MaSPCuoi == null) {
            return "NV001";
        }
        int lastNumber = Integer.parseInt(MaSPCuoi.substring(2));
        int newNumber = lastNumber + 1;
        String MaSPMoi = String.format("SP%03d", newNumber);
        return MaSPMoi;
    }

    public boolean check() {
        if (txtMaSP.getText().equals("") || txtTenSP.getText().equals("") || txtThuongHieu.getText().equals("") || txtSoluongTon.getText().equals("") || txtDonGia.getText().equals("") || txtMota.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Hãy nhập đủ dữ liệu sau đó ấn Save", "Error", 1);
            return false;

        } else if (!jRadioButton1.isSelected() && !jRadioButton2.isSelected()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn Vai trò", "Error", 1);
            return false;
        } else if (!(txtMaSP.getText()).matches("SP[0-9]{1,5}")) {
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng mã \n VD : SP001", "Error", 1);
            txtMaSP.requestFocus();
            return false;
        }

        List<SanPham> list = dao.select();
        for (int i = 0; i < list.size(); i++) {
            if (txtMaSP.getText().equalsIgnoreCase(list.get(i).getMaSP())) {
                JOptionPane.showMessageDialog(this, "Trùng Mã Nhân Viên", "Error", 1);
                return false;
            }
        }
        return true;
    }

    ;
    public boolean checkUpdate() {
        if (txtMaSP.getText().equals("") || txtTenSP.getText().equals("") || txtThuongHieu.getText().equals("") || txtSoluongTon.getText().equals("") || txtDonGia.getText().equals("") || txtMota.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Hãy nhập đủ dữ liệu sau đó ấn Save", "Error", 1);
            return false;

        } else if (!jRadioButton1.isSelected() && !jRadioButton2.isSelected()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn Vai trò", "Error", 1);
            return false;
        } else if (!(txtMaSP.getText()).matches("SP[0-9]{1,5}")) {
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng mã \n VD : SP001", "Error", 1);
            txtMaSP.requestFocus();
            return false;
        }
        return true;
    }

    ;
    
    public void ResizeImage(String imageName) {
        ImageIcon icon = new ImageIcon("src\\com\\beautysys\\icon\\imageSP\\" + imageName);
        Image image = icon.getImage();
        ImageIcon icon1 = new ImageIcon(image.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), image.SCALE_SMOOTH));
        lblImage.setIcon(icon1);

    }

    void load() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        tblSanPham.setDefaultEditor(Object.class, null);
        try {
            List<SanPham> list = dao.selectSortedByStatus1();
            for (SanPham nv : list) {
                Object[] row = {
                    nv.getMaSP(),
                    nv.getMaDM(),
                    nv.getTenSP(),
                    nv.getThuongHieu(),
                    nv.getDonGia(),
                    nv.getMoTa(),
                    nv.getSLTonKho(),
                    nv.getTrangThai() ? "Còn hàng" : "Hết hàng"

                };
                model.addRow(row);
                model.fireTableDataChanged();
                ListSelectionModel selectionModel;
                selectionModel = tblSanPham.getSelectionModel();
                selectionModel.setSelectionInterval(index, index);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void loadByKey() {
        String keyword = txtKeySearch.getText();
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        tblSanPham.setDefaultEditor(Object.class, null);
        try {
            List<SanPham> list = dao.selectListKey(keyword);
            for (SanPham nv : list) {
                Object[] row = {
                    nv.getMaSP(),
                    nv.getMaDM(),
                    nv.getTenSP(),
                    nv.getThuongHieu(),
                    nv.getDonGia(),
                    nv.getMoTa(),
                    nv.getSLTonKho(),
                    nv.getTrangThai() ? "Còn hàng" : "Hết hàng"

                };
                model.addRow(row);
                model.fireTableDataChanged();
                ListSelectionModel selectionModel;
                selectionModel = tblSanPham.getSelectionModel();
                selectionModel.setSelectionInterval(index, index);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void edit() {
        try {
            String manv = (String) tblSanPham.getValueAt(this.index, 0);
            SanPham model = dao.findById(manv);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception a) {
            DialogHelper.alert(this, "lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        SanPham model = getModel();
        if (check()) {
            if (model.getHinhSP() != null) {
                try {
                    dao.insert(model);
                    this.load();
                    this.clear();
                    DialogHelper.alert(this, "Thêm mới thành công!");
                } catch (Exception e) {
                    DialogHelper.alert(this, "Thêm mới thất bại!");
                }
            } else {
                DialogHelper.alert(this, "Bạn chưa chọn hình cho sản phẩm!");

            }

        } else {
            DialogHelper.alert(this, "Có lỗi sai gì đó ở bước thêm sản phẩm!");
        }
    }

    void update() {
        SanPham model = getModel();
        if (checkUpdate()) {
            try {
                dao.update(model);
                this.load();
                DialogHelper.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                System.out.println(e);
                DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa nhân viên này?")) {
            String manv = txtMaSP.getText();
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

    void setModel(SanPham model) {
        txtMaSP.setText(model.getMaSP());
        selectComboBoxItemByMaDM(model.getMaDM());
        txtThuongHieu.setText(model.getThuongHieu());
        txtDonGia.setText(String.valueOf(model.getDonGia()));
        txtTenSP.setText(model.getTenSP());
        txtMota.setText(model.getMoTa());
        txtSoluongTon.setText(String.valueOf(model.getSLTonKho()));
        jRadioButton1.setSelected(model.getTrangThai());
        jRadioButton2.setSelected(!model.getTrangThai());
        if (model.getHinhSP() != null) {
            lblImage.setIcon(ShareHelper.readLogo(model.getHinhSP()));
        }
    }

    SanPham getModel() {
        SanPham model = new SanPham();
        model.setMaSP(txtMaSP.getText());
        String selectedComboItem = (String) cbbMaDM.getSelectedItem();
        if (selectedComboItem != null) {
            String maDM = selectedComboItem.split(" - ")[0];
            model.setMaDM(maDM);
        }
        model.setTenSP(txtTenSP.getText());
        model.setThuongHieu(txtThuongHieu.getText());
        model.setDonGia(Float.valueOf(txtDonGia.getText()));
        model.setMoTa(txtMota.getText());
        model.setSLTonKho(Integer.valueOf(txtSoluongTon.getText()));
        model.setTrangThai(jRadioButton1.isSelected());
        model.setHinhSP(lblImage.getToolTipText());
        return model;
    }

    void clear() {
        SanPham nv = new SanPham();
        nv.setMaSP(TaoMaSP());
        cbbMaDM.setSelectedIndex(0);
        nv.setHinhSP("");
        nv.setDonGia(0.0f);
        nv.setTrangThai(true);
        nv.setSLTonKho(0);
        this.setModel(nv);
        this.setStatus(true);
        index = -1;
        this.load();
    }

    void setStatus(boolean insertable) {
        txtMaSP.setEditable(insertable);
        btnThemNhanVien.setEnabled(insertable);
        btnUpdated.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tblSanPham.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPre.setEnabled(!insertable && first);
        btnNext.setEnabled(!insertable && last);
        btnLast.setEnabled(!insertable && last);
    }

    void softingListProduct() {
        String selectedItem = (String) jcbbSapxep.getSelectedItem();
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        List<SanPham> list = null;
        if (selectedItem != null) {
            switch (selectedItem) {
                case "Mặc định":
                    list = dao.selectSortedByStatus1();
                    index = 0;
                    break;
                case "Tên Tăng Dần":
                    list = dao.selectSortedByName();
                    index = 0;
                    break;
                case "Tên Giảm Dần":
                    list = dao.selectSortedByNameDesc();
                    index = 0;
                    break;
                case "Giá Tăng Dần":
                    list = dao.selectSortedByDonGiaAsc();
                    index = 0;
                    break;
                case "Giá Giảm Dần":
                    list = dao.selectSortedByDonGiaDesc();
                    index = 0;
                    break;
                case "Hết Hàng":
                    list = dao.selectSortedByStatus0();
                    index = 0;
                    break;
            }
        } else {
            list = dao.select();
        }
        for (SanPham nv : list) {
            Object[] row = {
                nv.getMaSP(),
                nv.getMaDM(),
                nv.getTenSP(),
                nv.getThuongHieu(),
                nv.getDonGia(),
                nv.getMoTa(),
                nv.getSLTonKho(),
                nv.getTrangThai() ? "Còn hàng" : "Hết hàng"
            };
            model.addRow(row);
            model.fireTableDataChanged();
            ListSelectionModel selectionModel;
            selectionModel = tblSanPham.getSelectionModel();
            selectionModel.setSelectionInterval(index, index);

        }
    }

    void FillCbbDm() {
        List<DanhMucSanPham> list = dmspdao.select();
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        for (DanhMucSanPham dmsp : list) {
            String cbbDM = dmsp.toString();
            comboBoxModel.addElement(cbbDM);
        }
        cbbMaDM.setModel(comboBoxModel);
    }

    private void selectComboBoxItemByMaDM(String maDM) {
        if (maDM != null) {
            for (int i = 0; i < cbbMaDM.getItemCount(); i++) {
                String[] parts = ((String) cbbMaDM.getItemAt(i)).split(" - ");
                if (parts.length > 0 && maDM.equals(parts[0])) {
                    cbbMaDM.setSelectedIndex(i);
                    break;
                }
            }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnUpdated = new lgnvswing.lgnvButton();
        btnThemNhanVien = new lgnvswing.lgnvButton();
        btnNew = new lgnvswing.lgnvButton();
        btnDelete = new lgnvswing.lgnvButton();
        txtKeySearch = new lgnvswing.lgnvTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtMaSP = new lgnvswing.lgnvTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTenSP = new lgnvswing.lgnvTextField();
        txtMota = new lgnvswing.lgnvTextField();
        jLabel13 = new javax.swing.JLabel();
        txtSoluongTon = new lgnvswing.lgnvTextField();
        jPanel4 = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        txtThuongHieu = new lgnvswing.lgnvTextField();
        txtDonGia = new lgnvswing.lgnvTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        jcbbSapxep = new javax.swing.JComboBox<>();
        cbbMaDM = new javax.swing.JComboBox<>();

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(517, 517, 517))
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
        btnThemNhanVien.setText("Thêm");
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
                .addContainerGap()
                .addComponent(btnThemNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdated, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThemNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUpdated, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Mã DM", "Tên SP", "Hãng", "Giá", "Mô tả", "Số lượng tồn kho", "Tình trạng"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

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
                .addContainerGap()
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFirst)))
        );

        jLabel4.setText("MaSP");

        txtMaSP.setLGNV_maxCharLen(30);
        txtMaSP.setLGNV_placeholderText("");
        txtMaSP.setEnabled(false);
        txtMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSPActionPerformed(evt);
            }
        });

        jLabel7.setText("MaDM");

        jLabel8.setText("Môtả");

        jLabel10.setText("Giá");

        jLabel11.setText("TênSP");

        jLabel12.setText("Hãng");

        txtTenSP.setLGNV_maxCharLen(100);
        txtTenSP.setLGNV_placeholderText("Nhập Tên Sản Phẩm...");
        txtTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSPActionPerformed(evt);
            }
        });

        txtMota.setLGNV_maxCharLen(120);
        txtMota.setLGNV_placeholderText("Nhập Mô Tả....");
        txtMota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMotaActionPerformed(evt);
            }
        });

        jLabel13.setText("SL Tồn");

        txtSoluongTon.setLGNV_maxCharLen(13);
        txtSoluongTon.setLGNV_placeholderText("Nhập Password....");
        txtSoluongTon.setLGNV_textType(lgnvswing.lgnvTextField.textTypeEnum.NUMBER);
        txtSoluongTon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoluongTonActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });
        lblImage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lblImageKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtThuongHieu.setLGNV_maxCharLen(100);
        txtThuongHieu.setLGNV_placeholderText("Nhập Hãng Sản Phẩm....");
        txtThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThuongHieuActionPerformed(evt);
            }
        });

        txtDonGia.setLGNV_maxCharLen(12);
        txtDonGia.setLGNV_placeholderText("Nhập Giá SP....");
        txtDonGia.setLGNV_textType(lgnvswing.lgnvTextField.textTypeEnum.NUMBER);
        txtDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Còn hàng");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Hết hàng");

        jLabel14.setText("Tình trạng");

        jcbbSapxep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mặc định", "Tên Tăng Dần", "Tên Giảm Dần", "Giá Tăng Dần", "Giá Giảm Dần", "Hết Hàng" }));
        jcbbSapxep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbbSapxepActionPerformed(evt);
            }
        });

        cbbMaDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMaDMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtThuongHieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDonGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoluongTon, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(36, 36, 36)
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbMaDM, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jcbbSapxep, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKeySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbbSapxep, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbMaDM, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMota, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoluongTon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );

        txtTenSP.getAccessibleContext().setAccessibleDescription("");

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
        String checkmanv = txtMota.getText();
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
        this.index = (tblSanPham.getRowCount() - 1);
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

    private void txtMotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMotaActionPerformed

    private void txtSoluongTonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoluongTonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoluongTonActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        this.index = tblSanPham.getSelectedRow();
        if (this.index >= 0) {
            this.edit();
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void txtKeySearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKeySearchKeyPressed
        // TODO add your handling code here:
        loadByKey();
    }//GEN-LAST:event_txtKeySearchKeyPressed

    private void txtMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSPActionPerformed

    private void txtTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSPActionPerformed

    private void lblImageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblImageKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblImageKeyPressed

    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked
        // TODO add your handling code here:
        try {
            JFileChooser file = new JFileChooser("src\\com\\beautysys\\icon\\imageSP\\");
            int kq = file.showOpenDialog(file);
            if (kq == JFileChooser.APPROVE_OPTION) {
                imageName = file.getSelectedFile().getName();
                ResizeImage(imageName);
                lblImage.setToolTipText(imageName);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn ảnh...");
            }

        } catch (Exception a) {
        }
    }//GEN-LAST:event_lblImageMouseClicked

    private void txtThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThuongHieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThuongHieuActionPerformed

    private void txtDonGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaActionPerformed

    private void jcbbSapxepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbbSapxepActionPerformed
        // TODO add your handling code here:
        softingListProduct();
    }//GEN-LAST:event_jcbbSapxepActionPerformed

    private void cbbMaDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMaDMActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbbMaDMActionPerformed

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
            java.util.logging.Logger.getLogger(FormSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormSanPham().setVisible(true);
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
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbbMaDM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jcbbSapxep;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTable tblSanPham;
    public lgnvswing.lgnvTextField txtDonGia;
    private lgnvswing.lgnvTextField txtKeySearch;
    public lgnvswing.lgnvTextField txtMaSP;
    public lgnvswing.lgnvTextField txtMota;
    public lgnvswing.lgnvTextField txtSoluongTon;
    public lgnvswing.lgnvTextField txtTenSP;
    public lgnvswing.lgnvTextField txtThuongHieu;
    // End of variables declaration//GEN-END:variables
}
