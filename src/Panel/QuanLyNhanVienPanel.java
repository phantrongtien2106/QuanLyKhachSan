package Panel;

import bus.NhanVienBUS;
import bus.TaiKhoanBUS;
import dao.NhanVienDAO;
import helper.MaNguoiDungHelper;
import model.NhanVien;
import model.TaiKhoan;
import model.Quyen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class QuanLyNhanVienPanel extends JPanel {
    // Các thành phần UI cho bảng và tìm kiếm
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTimKiem;
    private JButton btnThem, btnXoa, btnSua, btnLamMoi;
    private JTabbedPane tabbedPane;

    // Các thành phần UI cho form nhập liệu
    private JPanel formPanel;
    private JTextField txtMaNguoiDung, txtHoTen, txtSoDienThoai, txtCCCD, txtEmail, txtDiaChi, txtLuong, txtNgaySinh;
    private JPasswordField txtMatKhau;
    private JComboBox<String> comboVaiTro;

    // BUS và DAO
    private TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private MaNguoiDungHelper maHelper = new MaNguoiDungHelper();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();
    // Biến để theo dõi trạng thái thêm/sửa
    private boolean isEditing = false;

    public QuanLyNhanVienPanel() {
        setLayout(new BorderLayout());

        // Tạo panel chính
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        // Tab Danh sách nhân viên
        JPanel listPanel = createListPanel();
        tabbedPane.addTab("Danh sách nhân viên", listPanel);

        // Tab Thêm/Sửa nhân viên
        formPanel = createFormPanel();
        tabbedPane.addTab("Thêm/Sửa nhân viên", formPanel);

        // Load dữ liệu ban đầu
        loadData();
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTimKiem = new JTextField(20);
        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemNhanVien(txtTimKiem.getText());
            }
        });
        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        searchPanel.add(lblTimKiem);
        searchPanel.add(txtTimKiem);
        panel.add(searchPanel, BorderLayout.NORTH);

        // Tạo bảng
        tableModel = new DefaultTableModel(
                new Object[]{"Mã NV", "Họ tên", "Số điện thoại", "Email", "Ngày sinh", "Lương", "Vai trò"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                hienThiThongTinNhanVien(table.getSelectedRow());
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnThem = new JButton("Thêm mới");
        btnThem.addActionListener(e -> {
            lamMoiForm();
            isEditing = false;
            tabbedPane.setSelectedIndex(1);
        });

        btnSua = new JButton("Sửa");
        btnSua.addActionListener(e -> suaThongTin(e));

        btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(e -> xoaNhanVien(e));

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.addActionListener(e -> loadData());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLamMoi);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Khởi tạo các trường nhập liệu
        txtMaNguoiDung = new JTextField(20);
        txtMaNguoiDung.setEditable(false);
        txtHoTen = new JTextField(20);
        txtSoDienThoai = new JTextField(20);
        txtCCCD = new JTextField(20);
        txtEmail = new JTextField(20);
        txtDiaChi = new JTextField(20);
        txtLuong = new JTextField(20);
        txtNgaySinh = new JTextField(20);
        txtMatKhau = new JPasswordField(20);
        comboVaiTro = new JComboBox<>(new String[]{"Nhân viên", "Admin"});

        // Thêm các trường vào panel
        addField(inputPanel, "Mã nhân viên:", txtMaNguoiDung, 0, gbc);
        addField(inputPanel, "Họ tên:", txtHoTen, 1, gbc);
        addField(inputPanel, "Số điện thoại:", txtSoDienThoai, 2, gbc);
        addField(inputPanel, "CCCD:", txtCCCD, 3, gbc);
        addField(inputPanel, "Email:", txtEmail, 4, gbc);
        addField(inputPanel, "Địa chỉ:", txtDiaChi, 5, gbc);
        addField(inputPanel, "Lương:", txtLuong, 6, gbc);
        addField(inputPanel, "Ngày sinh (yyyy-MM-dd):", txtNgaySinh, 7, gbc);
        addField(inputPanel, "Mật khẩu:", txtMatKhau, 8, gbc);
        addField(inputPanel, "Vai trò:", comboVaiTro, 9, gbc);

        JScrollPane scrollPane = new JScrollPane(inputPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnLuu = new JButton("Lưu");
        btnLuu.addActionListener(e -> luuNhanVien(e));

        JButton btnHuy = new JButton("Hủy");
        btnHuy.addActionListener(e -> {
            lamMoiForm();
            tabbedPane.setSelectedIndex(0);
        });

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addField(JPanel panel, String label, JComponent field, int y, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(field, gbc);
    }

    private void loadData() {
        tableModel.setRowCount(0);

        List<TaiKhoan> danhSachTK = taiKhoanBUS.layDanhSachTaiKhoan();
        for (TaiKhoan tk : danhSachTK) {
            String maNguoiDung = tk.getMaNguoiDung();
            // Kiểm tra xem có phải là nhân viên không
            if (maNguoiDung.startsWith("NV")) {
                NhanVien nv = nhanVienDAO.timNhanVienTheoMa(maNguoiDung);
                if (nv != null) {
                    String loaiNguoiDung = taiKhoanBUS.getLoaiNguoiDung(maNguoiDung);
                    tableModel.addRow(new Object[]{
                            maNguoiDung,
                            tk.getHoTen(),
                            tk.getSoDienThoai(),
                            tk.getEmail(),
                            nv.getNgaySinh(),
                            nv.getLuong(),
                            loaiNguoiDung
                    });
                }
            }
        }
    }

    private void timKiemNhanVien(String tuKhoa) {
        tableModel.setRowCount(0);

        List<TaiKhoan> danhSachTK = taiKhoanBUS.timKiemTaiKhoan(tuKhoa);
        for (TaiKhoan tk : danhSachTK) {
            String maNguoiDung = tk.getMaNguoiDung();
            // Chỉ hiển thị nhân viên
            if (maNguoiDung.startsWith("NV")) {
                NhanVien nv = nhanVienDAO.timNhanVienTheoMa(maNguoiDung);
                if (nv != null) {
                    String loaiNguoiDung = taiKhoanBUS.getLoaiNguoiDung(maNguoiDung);
                    tableModel.addRow(new Object[]{
                            maNguoiDung,
                            tk.getHoTen(),
                            tk.getSoDienThoai(),
                            tk.getEmail(),
                            nv.getNgaySinh(),
                            nv.getLuong(),
                            loaiNguoiDung
                    });
                }
            }
        }
    }

    private void hienThiThongTinNhanVien(int selectedRow) {
        String maNV = (String) tableModel.getValueAt(selectedRow, 0);

        TaiKhoan tk = taiKhoanBUS.layThongTinTheoMa(maNV);
        NhanVien nv = nhanVienDAO.timNhanVienTheoMa(maNV);

        if (tk != null && nv != null) {
            txtMaNguoiDung.setText(tk.getMaNguoiDung());
            txtHoTen.setText(tk.getHoTen());
            txtSoDienThoai.setText(tk.getSoDienThoai());
            txtCCCD.setText(tk.getCccd());
            txtEmail.setText(tk.getEmail());
            txtDiaChi.setText(tk.getDiaChi());
            txtLuong.setText(String.valueOf(nv.getLuong()));
            txtNgaySinh.setText(nv.getNgaySinh().toString());
            txtMatKhau.setText(""); // Không hiển thị mật khẩu vì lý do bảo mật

            // Thiết lập combobox vai trò
            if (maNV.startsWith("NVA")) {
                comboVaiTro.setSelectedItem("Admin");
            } else {
                comboVaiTro.setSelectedItem("Nhân viên");
            }
        }
    }

    private void lamMoiForm() {
        txtMaNguoiDung.setText("");
        txtHoTen.setText("");
        txtSoDienThoai.setText("");
        txtCCCD.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtLuong.setText("");
        txtNgaySinh.setText("");
        txtMatKhau.setText("");
        comboVaiTro.setSelectedIndex(0);
    }

    private void luuNhanVien(ActionEvent e) {
        // Kiểm tra dữ liệu đầu vào
        if (txtHoTen.getText().isEmpty() || txtSoDienThoai.getText().isEmpty() ||
                txtCCCD.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                txtDiaChi.getText().isEmpty() || txtLuong.getText().isEmpty() ||
                txtNgaySinh.getText().isEmpty() || txtMatKhau.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Lấy loại vai trò từ combobox
            String loaiVaiTro = comboVaiTro.getSelectedItem().toString().toLowerCase();

            // Tạo tài khoản
            TaiKhoan tk = new TaiKhoan(
                    txtMaNguoiDung.getText(),
                    txtHoTen.getText(),
                    new ArrayList<>() // Danh sách quyền sẽ được cập nhật trong TaiKhoanBUS
            );

            tk.setCccd(txtCCCD.getText());
            tk.setEmail(txtEmail.getText());
            tk.setSoDienThoai(txtSoDienThoai.getText());
            tk.setDiaChi(txtDiaChi.getText());
            tk.setMatKhau(new String(txtMatKhau.getPassword()));

            // Tạo nhân viên
            double luong = Double.parseDouble(txtLuong.getText());
            java.sql.Date ngaySinh = java.sql.Date.valueOf(txtNgaySinh.getText()); // Định dạng yyyy-MM-dd

            NhanVien nv = new NhanVien(
                    txtMaNguoiDung.getText(),
                    ngaySinh,
                    luong
            );
            nv.setTaiKhoan(tk);

            boolean success;

            if (isEditing) {
                // Cập nhật tài khoản và nhân viên
                success = taiKhoanBUS.capNhatTaiKhoan(tk) && nhanVienBUS.capNhatNhanVien(nv);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Tạo mới tài khoản và nhân viên
                // Tạo mã tự động nếu trường mã đang trống
                if (txtMaNguoiDung.getText().isEmpty()) {
                    String maTuDong = maHelper.taoMaTuDong(loaiVaiTro);
                    txtMaNguoiDung.setText(maTuDong);
                    tk.setMaNguoiDung(maTuDong);
                    nv.setMaNhanVien(maTuDong);
                }

                // Lưu tài khoản với vai trò phù hợp
                success = taiKhoanBUS.taoTaiKhoanNhanVien(tk, loaiVaiTro) && nhanVienBUS.themNhanVien(nv);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công");
                    lamMoiForm(); // Làm mới form để tiếp tục thêm
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Cập nhật lại bảng dữ liệu
            loadData();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lương phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ (định dạng: yyyy-MM-dd)", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    private void suaThongTin(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!");
            return;
        }

        hienThiThongTinNhanVien(row);
        isEditing = true;
        tabbedPane.setSelectedIndex(1);
    }

    private void xoaNhanVien(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
            return;
        }

        String maNV = (String) tableModel.getValueAt(row, 0);
        String hoTen = (String) tableModel.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhân viên " + hoTen + " (" + maNV + ")?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Xóa tài khoản sẽ xóa luôn dữ liệu của nhân viên do ràng buộc khóa ngoại
            boolean success = taiKhoanBUS.xoaTaiKhoan(maNV);
            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại! Vui lòng thử lại sau.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}