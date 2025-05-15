package gui;

import bus.KhachHangBUS;
import model.KhachHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class KhachHangForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMa, txtTen, txtCCCD, txtSDT, txtDiaChi;
    private KhachHangBUS khBUS = new KhachHangBUS();

    public KhachHangForm() {
        setTitle("Quản lý khách hàng");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(new String[]{"Mã KH", "Họ tên", "CCCD", "SĐT", "Địa chỉ"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Form input
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 5));
        txtMa = new JTextField();
        txtTen = new JTextField();
        txtCCCD = new JTextField();
        txtSDT = new JTextField();
        txtDiaChi = new JTextField();

        inputPanel.add(new JLabel("Mã KH:"));
        inputPanel.add(txtMa);
        inputPanel.add(new JLabel("Họ tên:"));
        inputPanel.add(txtTen);
        inputPanel.add(new JLabel("CCCD:"));
        inputPanel.add(txtCCCD);
        inputPanel.add(new JLabel("SĐT:"));
        inputPanel.add(txtSDT);
        inputPanel.add(new JLabel("Địa chỉ:"));
        inputPanel.add(txtDiaChi);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xoá");
        JButton btnReset = new JButton("Reset");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnReset);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);

        // Load dữ liệu
        loadKhachHang();

        // Chọn dòng
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                txtMa.setText(model.getValueAt(i, 0).toString());
                txtTen.setText(model.getValueAt(i, 1).toString());
                txtCCCD.setText(model.getValueAt(i, 2).toString());
                txtSDT.setText(model.getValueAt(i, 3).toString());
                txtDiaChi.setText(model.getValueAt(i, 4).toString());
                txtMa.setEditable(false); // không cho sửa mã
            }
        });

        // Thêm
        btnThem.addActionListener(e -> {
            String maKH = txtMa.getText().trim();
            if (khBUS.isMaKhachHangTonTai(maKH)) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại!");
                return;
            }

            KhachHang kh = new KhachHang(
                    maKH, txtTen.getText(), txtCCCD.getText(),
                    txtSDT.getText(), txtDiaChi.getText()
            );
            if (khBUS.themKhachHang(kh)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadKhachHang();  // hoặc loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại! Kiểm tra rằng mã KH có tồn tại trong bảng tài khoản nếu chưa có.");
            }
        });


        // Sửa
        btnSua.addActionListener(e -> {
            KhachHang kh = new KhachHang(
                    txtMa.getText(), txtTen.getText(), txtCCCD.getText(),
                    txtSDT.getText(), txtDiaChi.getText()
            );
            if (khBUS.suaKhachHang(kh)) {
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
                loadKhachHang();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại!");
            }
        });

        // Xoá
        btnXoa.addActionListener(e -> {
            String ma = txtMa.getText();
            if (khBUS.xoaKhachHang(ma)) {
                JOptionPane.showMessageDialog(this, "Xoá thành công!");
                loadKhachHang();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xoá thất bại!");
            }
        });

        // Reset form
        btnReset.addActionListener(e -> {
            clearForm();
            txtMa.setText(khBUS.getNextMaKhachHang()); // tự sinh mã mới
        });


        setVisible(true);
    }

    private void loadKhachHang() {
        model.setRowCount(0);
        List<KhachHang> ds = khBUS.getAllKhachHang();
        for (KhachHang kh : ds) {
            model.addRow(new Object[]{
                    kh.getMaKhachHang(), kh.getHoTen(), kh.getCccd(), kh.getSdt(), kh.getDiaChi()
            });
        }
    }

    private void clearForm() {
        txtMa.setText("");
        txtTen.setText("");
        txtCCCD.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtMa.setEditable(true);
    }

    private void loadTable() {
        model.setRowCount(0);
        for (KhachHang kh : khBUS.getAllKhachHang()) {
            model.addRow(new Object[]{
                    kh.getMaKhachHang(),
                    kh.getHoTen(),
                    kh.getCccd(),
                    kh.getSdt(),
                    kh.getDiaChi()
            });
        }
    }


    public static void main(String[] args) {
        new KhachHangForm();
    }
}
