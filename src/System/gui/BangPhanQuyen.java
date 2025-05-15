package System.gui;

import System.dao.TaiKhoanDAO;
import System.model.TaiKhoan;
import System.model.VaiTro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BangPhanQuyen extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> comboTaiKhoan;
    private JComboBox<String> comboVaiTro;
    private TaiKhoanDAO dao = new TaiKhoanDAO();

    public BangPhanQuyen() {
        setLayout(new BorderLayout());

        // Bảng tài khoản và vai trò
        String[] columns = {"Mã người dùng", "Họ tên", "Vai trò"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel chọn tài khoản và vai trò
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Tài khoản:"));
        comboTaiKhoan = new JComboBox<>();
        for (TaiKhoan tk : dao.layDanhSachTaiKhoan()) {
            comboTaiKhoan.addItem(tk.getMaNguoiDung());
        }
        controlPanel.add(comboTaiKhoan);

        controlPanel.add(new JLabel("Vai trò:"));
        comboVaiTro = new JComboBox<>();
        for (VaiTro vt : dao.layDanhSachVaiTro()) {
            comboVaiTro.addItem(vt.getMaVaiTro());
        }
        controlPanel.add(comboVaiTro);

        JButton btnGan = new JButton("Gán vai trò");
        btnGan.addActionListener(e -> {
            String user = (String) comboTaiKhoan.getSelectedItem();
            String role = (String) comboVaiTro.getSelectedItem();
            if (dao.ganVaiTroChoTaiKhoan(user, role)) {
                JOptionPane.showMessageDialog(this, "Gán vai trò thành công!");
                taiLaiBang();
            } else {
                JOptionPane.showMessageDialog(this, "Đã tồn tại hoặc lỗi!");
            }
        });
        controlPanel.add(btnGan);
        JButton btnXoa = new JButton("Xoá vai trò");
        btnXoa.addActionListener(e -> {
            String user = (String) comboTaiKhoan.getSelectedItem();
            String role = (String) comboVaiTro.getSelectedItem();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xoá vai trò \"" + role + "\" khỏi tài khoản \"" + user + "\"?",
                    "Xác nhận xoá",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.xoaVaiTroCuaTaiKhoan(user, role)) {
                    JOptionPane.showMessageDialog(this, "Xoá vai trò thành công!");
                    taiLaiBang();
                } else {
                    JOptionPane.showMessageDialog(this, "Xoá vai trò thất bại hoặc không tồn tại!");
                }
            }
        });
        controlPanel.add(btnXoa);

        JButton btnPhanQuyenChiTiet = new JButton("Phân quyền chi tiết");
        btnPhanQuyenChiTiet.addActionListener(e -> new PhanQuyenChiTiet());
        controlPanel.add(btnPhanQuyenChiTiet);

        add(controlPanel, BorderLayout.NORTH);
        taiLaiBang();
    }

    private void taiLaiBang() {
        tableModel.setRowCount(0);
        for (TaiKhoan tk : dao.layDanhSachTaiKhoan()) {
            List<VaiTro> vaiTros = dao.layVaiTroCuaTaiKhoan(tk.getMaNguoiDung());
            StringBuilder ds = new StringBuilder();
            for (VaiTro vt : vaiTros) {
                ds.append(vt.getMaVaiTro()).append(", ");
            }
            String danhSach = ds.length() > 0 ? ds.substring(0, ds.length() - 2) : "";
            tableModel.addRow(new Object[]{tk.getMaNguoiDung(), tk.getHoTen(), danhSach});
        }
    }

    public JPanel getPanel() {
        return this;
    }
}
