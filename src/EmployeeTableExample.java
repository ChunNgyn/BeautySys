import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeTableExample extends JFrame {
    private JTable table;
    private JComboBox<String> comboBox;

    public EmployeeTableExample() {
        setTitle("Employee Table Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Tạo bảng
        String[] columns = {"Mã NV", "Tên NV"};
        Object[][] data = {
                {"NV001", "Nguyen Van A"},
                {"NV002", "Tran Thi B"},
                {"NV003", "Le Van C"}
                // Thêm dữ liệu mẫu khác nếu cần
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        table = new JTable(model);

        // Tạo combobox và điền dữ liệu
        comboBox = new JComboBox<>();
        fillComboBox();

        // Thêm sự kiện cho việc chọn dòng trong bảng
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!selectionModel.isSelectionEmpty()) {
                int selectedRow = selectionModel.getMinSelectionIndex();
                // Lấy giá trị từ cột "Mã NV" và "Tên NV" trong dòng đã chọn
                String employeeCode = (String) table.getValueAt(selectedRow, 0);
                String employeeName = (String) table.getValueAt(selectedRow, 1);
                // Tạo chuỗi "Mã NV - Tên NV" và chọn nó trong combobox
                String fullInfo = employeeCode + " - " + employeeName;
                comboBox.setSelectedItem(fullInfo);
            }
        });

        // Thêm components vào frame
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        add(comboBox, BorderLayout.SOUTH);

        // Hiển thị frame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fillComboBox() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String employeeCode = (String) model.getValueAt(i, 0);
            String employeeName = (String) model.getValueAt(i, 1);
            String fullInfo = employeeCode + " - " + employeeName;
            comboBox.addItem(fullInfo);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeTableExample::new);
    }
}
