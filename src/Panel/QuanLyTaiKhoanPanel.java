package Panel;

import bus.TaiKhoanBUS;
import model.TaiKhoan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class QuanLyTaiKhoanPanel extends JPanel {
    private JTable tblTaiKhoan;
    private DefaultTableModel modelTaiKhoan;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLamMoi;

    private TaiKhoanBUS taiKhoanBUS;

    public QuanLyTaiKhoanPanel() {
        taiKhoanBUS = new TaiKhoanBUS();
        initComponents();
        loadDanhSachTaiKhoan();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel tìm kiếm
        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTimKiem = new JLabel("Tìm kiếm theo số điện thoại:");
        txtTimKiem = new JTextField(20);
        btnTimKiem = new JButton("Tìm kiếm");
        btnLamMoi = new JButton("Làm mới");

        pnlTimKiem.add(lblTimKiem);
        pnlTimKiem.add(txtTimKiem);
        pnlTimKiem.add(btnTimKiem);
        pnlTimKiem.add(btnLamMoi);

        // Panel danh sách
        String[] columns = {"Mã người dùng", "Họ tên", "CCCD", "Email", "Số điện thoại", "Địa chỉ"};
        modelTaiKhoan = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblTaiKhoan = new JTable(modelTaiKhoan);
        JScrollPane scrollPane = new JScrollPane(tblTaiKhoan);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        // Panel chức năng
        JPanel pnlChucNang = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnThem = new JButton("Thêm tài khoản");
        btnSua = new JButton("Sửa tài khoản");
        btnXoa = new JButton("Xóa tài khoản");

        pnlChucNang.add(btnThem);
        pnlChucNang.add(btnSua);
        pnlChucNang.add(btnXoa);

        // Panel chính
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlTimKiem, BorderLayout.NORTH);
        pnlMain.add(scrollPane, BorderLayout.CENTER);
        pnlMain.add(pnlChucNang, BorderLayout.SOUTH);

        add(pnlMain, BorderLayout.CENTER);

        // Thêm sự kiện
        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKiemTaiKhoan();
            }
        });

        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtTimKiem.setText("");
                loadDanhSachTaiKhoan();
            }
        });

        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themTaiKhoan();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suaTaiKhoan();
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xoaTaiKhoan();
            }
        });
    }

    private void loadDanhSachTaiKhoan() {
        modelTaiKhoan.setRowCount(0);
        List<TaiKhoan> danhSachTaiKhoan = taiKhoanBUS.layDanhSachTaiKhoan();

        for (TaiKhoan tk : danhSachTaiKhoan) {
            Object[] row = {
                    tk.getMaNguoiDung(),
                    tk.getHoTen(),
                    tk.getCccd(),
                    tk.getEmail(),
                    tk.getSoDienThoai(),
                    tk.getDiaChi()
            };
            modelTaiKhoan.addRow(row);
        }
    }

    private void timKiemTaiKhoan() {
        String tuKhoa = txtTimKiem.getText().trim();
        if (tuKhoa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại cần tìm kiếm");
            return;
        }

        List<TaiKhoan> ketQua = taiKhoanBUS.timKiemTaiKhoan(tuKhoa);

        modelTaiKhoan.setRowCount(0);
        for (TaiKhoan tk : ketQua) {
            Object[] row = {
                    tk.getMaNguoiDung(),
                    tk.getHoTen(),
                    tk.getCccd(),
                    tk.getEmail(),
                    tk.getSoDienThoai(),
                    tk.getDiaChi()
            };
            modelTaiKhoan.addRow(row);
        }

        if (ketQua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản phù hợp");
        }
    }

    private void themTaiKhoan() {
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Thêm tài khoản", true);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

        JTextField txtMaNguoiDung = new JTextField();
        JTextField txtHoTen = new JTextField();
        JTextField txtCCCD = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtSoDienThoai = new JTextField();
        JTextField txtDiaChi = new JTextField();
        JPasswordField txtMatKhau = new JPasswordField();

        panel.add(new JLabel("Mã người dùng:"));
        panel.add(txtMaNguoiDung);
        panel.add(new JLabel("Họ tên:"));
        panel.add(txtHoTen);
        panel.add(new JLabel("CCCD:"));
        panel.add(txtCCCD);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtSoDienThoai);
        panel.add(new JLabel("Địa chỉ:"));
        panel.add(txtDiaChi);
        panel.add(new JLabel("Mật khẩu:"));
        panel.add(txtMatKhau);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maNguoiDung = txtMaNguoiDung.getText().trim();
                String hoTen = txtHoTen.getText().trim();
                String cccd = txtCCCD.getText().trim();
                String email = txtEmail.getText().trim();
                String soDienThoai = txtSoDienThoai.getText().trim();
                String diaChi = txtDiaChi.getText().trim();
                String matKhau = new String(txtMatKhau.getPassword());

                if (maNguoiDung.isEmpty() || hoTen.isEmpty() || soDienThoai.isEmpty() || matKhau.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin bắt buộc");
                    return;
                }

                // Tạo đối tượng tài khoản mới
                TaiKhoan taiKhoan = new TaiKhoan(maNguoiDung, hoTen, null);
                taiKhoan.setCccd(cccd);
                taiKhoan.setEmail(email);
                taiKhoan.setSoDienThoai(soDienThoai);
                taiKhoan.setDiaChi(diaChi);
                taiKhoan.setMatKhau(matKhau);

                // Thêm tài khoản mới
                boolean ketQua = taiKhoanBUS.themTaiKhoan(taiKhoan);

                if (ketQua) {
                    JOptionPane.showMessageDialog(dialog, "Thêm tài khoản thành công");
                    dialog.dispose();
                    loadDanhSachTaiKhoan();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm tài khoản thất bại");
                }
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void suaTaiKhoan() {
        int selectedRow = tblTaiKhoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần sửa");
            return;
        }

        String maNguoiDung = tblTaiKhoan.getValueAt(selectedRow, 0).toString();
        TaiKhoan taiKhoanHienTai = taiKhoanBUS.layThongTinTheoMa(maNguoiDung);

        if (taiKhoanHienTai == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin tài khoản");
            return;
        }

        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Sửa tài khoản", true);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

        JTextField txtMaNguoiDung = new JTextField(taiKhoanHienTai.getMaNguoiDung());
        txtMaNguoiDung.setEditable(false);
        JTextField txtHoTen = new JTextField(taiKhoanHienTai.getHoTen());
        JTextField txtCCCD = new JTextField(taiKhoanHienTai.getCccd());
        JTextField txtEmail = new JTextField(taiKhoanHienTai.getEmail());
        JTextField txtSoDienThoai = new JTextField(taiKhoanHienTai.getSoDienThoai());
        JTextField txtDiaChi = new JTextField(taiKhoanHienTai.getDiaChi());
        JPasswordField txtMatKhau = new JPasswordField();

        panel.add(new JLabel("Mã người dùng:"));
        panel.add(txtMaNguoiDung);
        panel.add(new JLabel("Họ tên:"));
        panel.add(txtHoTen);
        panel.add(new JLabel("CCCD:"));
        panel.add(txtCCCD);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtSoDienThoai);
        panel.add(new JLabel("Địa chỉ:"));
        panel.add(txtDiaChi);
        panel.add(new JLabel("Mật khẩu mới (để trống nếu không đổi):"));
        panel.add(txtMatKhau);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hoTen = txtHoTen.getText().trim();
                String cccd = txtCCCD.getText().trim();
                String email = txtEmail.getText().trim();
                String soDienThoai = txtSoDienThoai.getText().trim();
                String diaChi = txtDiaChi.getText().trim();
                String matKhau = new String(txtMatKhau.getPassword());

                if (hoTen.isEmpty() || soDienThoai.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin bắt buộc");
                    return;
                }

                // Cập nhật thông tin tài khoản
                taiKhoanHienTai.setHoTen(hoTen);
                taiKhoanHienTai.setCccd(cccd);
                taiKhoanHienTai.setEmail(email);
                taiKhoanHienTai.setSoDienThoai(soDienThoai);
                taiKhoanHienTai.setDiaChi(diaChi);

                boolean ketQua;

                // Nếu có nhập mật khẩu mới
                if (!matKhau.isEmpty()) {
                    taiKhoanHienTai.setMatKhau(matKhau);
                    ketQua = taiKhoanBUS.capNhatMatKhau(taiKhoanHienTai);
                } else {
                    ketQua = taiKhoanBUS.capNhatTaiKhoan(taiKhoanHienTai);
                }

                if (ketQua) {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật tài khoản thành công");
                    dialog.dispose();
                    loadDanhSachTaiKhoan();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật tài khoản thất bại");
                }
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void xoaTaiKhoan() {
        int selectedRow = tblTaiKhoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần xóa");
            return;
        }

        String maNguoiDung = tblTaiKhoan.getValueAt(selectedRow, 0).toString();
        String hoTen = tblTaiKhoan.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa tài khoản của " + hoTen + " (" + maNguoiDung + ")?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean ketQua = taiKhoanBUS.xoaTaiKhoan(maNguoiDung);

            if (ketQua) {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công");
                loadDanhSachTaiKhoan();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại");
            }
        }
    }
}