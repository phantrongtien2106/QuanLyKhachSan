package Panel;

import bus.*;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class QuanLyHopDongPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(0x4682B4);
    private static final Color BACKGROUND_COLOR = new Color(0xF5F5F5);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);

    private JTable tableHopDong, tableChiTiet;
    private DefaultTableModel modelHopDong, modelChiTiet;
    private JTabbedPane tabbedPane;
    private JTextField txtSearch;
    private JTextField txtMaHopDong, txtMaKhachHang, txtNgayBatDau, txtNgayKetThuc;
    private JTextField txtTongTien, txtDatCoc, txtDaThanhToan;
    private JTextArea txtGhiChu;
    private JComboBox<String> cboTrangThai;

    private HopDongBUS hopDongBUS = new HopDongBUS();
    private ChiTietHopDongBUS cthdBUS = new ChiTietHopDongBUS();
    private PhongBUS phongBUS = new PhongBUS();
    private KhachHangBUS khachHangBUS = new KhachHangBUS();

    public QuanLyHopDongPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(BACKGROUND_COLOR);

        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("QUẢN LÝ HỢP ĐỒNG", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR.darker());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        // Panel chính với tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(LABEL_FONT);
        tabbedPane.add("Tất cả hợp đồng", createAllContractsPanel());
        tabbedPane.add("Hợp đồng đang đặt", createPendingContractsPanel());
        tabbedPane.add("Hợp đồng đang sử dụng", createActiveContractsPanel());
        tabbedPane.add("Hợp đồng đã trả", createCompletedContractsPanel());
        tabbedPane.addChangeListener(e -> refreshData());

        add(tabbedPane, BorderLayout.CENTER);

        // Panel chi tiết
        JPanel detailPanel = createDetailPanel();
        add(detailPanel, BorderLayout.EAST);

        loadAllContracts();
    }

    private JPanel createAllContractsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(BACKGROUND_COLOR);
        JLabel lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 12));
        txtSearch = new JTextField(20);
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemHopDong(txtSearch.getText().trim());
            }
        });
        JButton btnSearch = createStyledButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(80, 30));
        btnSearch.addActionListener(e -> timKiemHopDong(txtSearch.getText().trim()));

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        JButton btnRefresh = createStyledButton("Làm mới");
        btnRefresh.addActionListener(e -> loadAllContracts());
        searchPanel.add(btnRefresh);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Bảng hợp đồng
        modelHopDong = new DefaultTableModel(
                new String[]{"Mã HĐ", "Mã KH", "Ngày bắt đầu", "Ngày kết thúc", "Tổng tiền", "Đã thanh toán", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableHopDong = new JTable(modelHopDong);
        tableHopDong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setupTable(tableHopDong);

        // Sự kiện khi chọn hợp đồng
        tableHopDong.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableHopDong.getSelectedRow() >= 0) {
                hienThiChiTietHopDong();
            }
        });

        JScrollPane scroll = new JScrollPane(tableHopDong);
        panel.add(scroll, BorderLayout.CENTER);

        // Panel chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton btnThem = createStyledButton("Thêm hợp đồng");
        btnThem.addActionListener(e -> themHopDong());

        JButton btnSua = createStyledButton("Cập nhật");
        btnSua.addActionListener(e -> capNhatHopDong());

        JButton btnHuy = createStyledButton("Hủy hợp đồng");
        btnHuy.addActionListener(e -> huyHopDong());

        JButton btnXoa = createStyledButton("Xóa");
        btnXoa.addActionListener(e -> xoaHopDong());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnXoa);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPendingContractsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        // Bảng hợp đồng đang đặt (sử dụng lại model từ bảng tất cả)
        JTable tablePending = new JTable(modelHopDong);
        tablePending.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setupTable(tablePending);

        // Sự kiện khi chọn hợp đồng
        tablePending.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablePending.getSelectedRow() >= 0) {
                int modelRow = tablePending.convertRowIndexToModel(tablePending.getSelectedRow());
                tableHopDong.setRowSelectionInterval(modelRow, modelRow);
                hienThiChiTietHopDong();
            }
        });

        JScrollPane scroll = new JScrollPane(tablePending);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createActiveContractsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        // Bảng hợp đồng đang sử dụng (sử dụng lại model từ bảng tất cả)
        JTable tableActive = new JTable(modelHopDong);
        tableActive.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setupTable(tableActive);

        // Sự kiện khi chọn hợp đồng
        tableActive.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableActive.getSelectedRow() >= 0) {
                int modelRow = tableActive.convertRowIndexToModel(tableActive.getSelectedRow());
                tableHopDong.setRowSelectionInterval(modelRow, modelRow);
                hienThiChiTietHopDong();
            }
        });

        JScrollPane scroll = new JScrollPane(tableActive);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCompletedContractsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        // Bảng hợp đồng đã trả (sử dụng lại model từ bảng tất cả)
        JTable tableCompleted = new JTable(modelHopDong);
        tableCompleted.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setupTable(tableCompleted);

        // Sự kiện khi chọn hợp đồng
        tableCompleted.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableCompleted.getSelectedRow() >= 0) {
                int modelRow = tableCompleted.convertRowIndexToModel(tableCompleted.getSelectedRow());
                tableHopDong.setRowSelectionInterval(modelRow, modelRow);
                hienThiChiTietHopDong();
            }
        });

        JScrollPane scroll = new JScrollPane(tableCompleted);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                "Chi tiết hợp đồng",
                0,
                0,
                LABEL_FONT,
                PRIMARY_COLOR.darker()
        ));
        panel.setPreferredSize(new Dimension(300, 0));

        // Form thông tin hợp đồng
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Mã hợp đồng
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Mã hợp đồng:"), gbc);

        gbc.gridx = 1;
        txtMaHopDong = new JTextField(15);
        txtMaHopDong.setEditable(false);
        formPanel.add(txtMaHopDong, gbc);

        // Mã khách hàng
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Mã khách hàng:"), gbc);

        gbc.gridx = 1;
        txtMaKhachHang = new JTextField(15);
        formPanel.add(txtMaKhachHang, gbc);

        // Ngày bắt đầu
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Ngày bắt đầu:"), gbc);

        gbc.gridx = 1;
        txtNgayBatDau = new JTextField(15);
        formPanel.add(txtNgayBatDau, gbc);

        // Ngày kết thúc
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Ngày kết thúc:"), gbc);

        gbc.gridx = 1;
        txtNgayKetThuc = new JTextField(15);
        formPanel.add(txtNgayKetThuc, gbc);

        // Tổng tiền
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Tổng tiền:"), gbc);

        gbc.gridx = 1;
        txtTongTien = new JTextField(15);
        formPanel.add(txtTongTien, gbc);

        // Đặt cọc
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Đặt cọc:"), gbc);

        gbc.gridx = 1;
        txtDatCoc = new JTextField(15);
        formPanel.add(txtDatCoc, gbc);

        // Đã thanh toán
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Đã thanh toán:"), gbc);

        gbc.gridx = 1;
        txtDaThanhToan = new JTextField(15);
        formPanel.add(txtDaThanhToan, gbc);

        // Trạng thái
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Trạng thái:"), gbc);

        gbc.gridx = 1;
        cboTrangThai = new JComboBox<>(new String[]{"dang_dat", "dang_su_dung", "da_tra", "da_huy"});
        formPanel.add(cboTrangThai, gbc);

        // Ghi chú
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("Ghi chú:"), gbc);

        gbc.gridx = 1;
        txtGhiChu = new JTextArea(4, 15);
        txtGhiChu.setLineWrap(true);
        JScrollPane scrollGhiChu = new JScrollPane(txtGhiChu);
        formPanel.add(scrollGhiChu, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Bảng chi tiết phòng
        modelChiTiet = new DefaultTableModel(
                new String[]{"Mã phòng", "Loại phòng", "Giá"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableChiTiet = new JTable(modelChiTiet);
        setupTable(tableChiTiet);
        JScrollPane scrollChiTiet = new JScrollPane(tableChiTiet);
        scrollChiTiet.setBorder(BorderFactory.createTitledBorder("Danh sách phòng"));
        panel.add(scrollChiTiet, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton btnLuu = createStyledButton("Lưu thay đổi");
        btnLuu.addActionListener(e -> luuThayDoi());

        buttonPanel.add(btnLuu);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);

        btn.putClientProperty("Nimbus.Overrides", Boolean.TRUE);
        btn.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.FALSE);

        btn.setPreferredSize(new Dimension(120, 30));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(PRIMARY_COLOR.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(PRIMARY_COLOR);
            }
        });

        return btn;
    }

    private void setupTable(JTable table) {
        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(PRIMARY_COLOR);
        table.setSelectionForeground(TEXT_COLOR);
        table.setBackground(Color.WHITE);

        // Tạo header đẹp
        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setFont(new Font("Arial", Font.BOLD, 12));

        // Center text in table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void loadAllContracts() {
        modelHopDong.setRowCount(0);
        for (HopDong hd : hopDongBUS.getAllHopDong()) {
            modelHopDong.addRow(new Object[]{
                    hd.getMaHopDong(),
                    hd.getMaKhachHang(),
                    hd.getNgayBatDau(),
                    hd.getNgayKetThuc(),
                    String.format("%,d", hd.getTongTien()),
                    String.format("%,d", hd.getDaThanhToan()),
                    hd.getTrangThai()
            });
        }
        resetForm();
    }

    private void loadPendingContracts() {
        modelHopDong.setRowCount(0);
        for (HopDong hd : hopDongBUS.getHopDongChuaCheckIn()) {
            modelHopDong.addRow(new Object[]{
                    hd.getMaHopDong(),
                    hd.getMaKhachHang(),
                    hd.getNgayBatDau(),
                    hd.getNgayKetThuc(),
                    String.format("%,d", hd.getTongTien()),
                    String.format("%,d", hd.getDaThanhToan()),
                    hd.getTrangThai()
            });
        }
        resetForm();
    }

    private void loadActiveContracts() {
        modelHopDong.setRowCount(0);
        for (HopDong hd : hopDongBUS.getHopDongDangSuDung()) {
            modelHopDong.addRow(new Object[]{
                    hd.getMaHopDong(),
                    hd.getMaKhachHang(),
                    hd.getNgayBatDau(),
                    hd.getNgayKetThuc(),
                    String.format("%,d", hd.getTongTien()),
                    String.format("%,d", hd.getDaThanhToan()),
                    hd.getTrangThai()
            });
        }
        resetForm();
    }

    private void loadCompletedContracts() {
        modelHopDong.setRowCount(0);
        for (HopDong hd : hopDongBUS.getHopDongDaTra()) {
            modelHopDong.addRow(new Object[]{
                    hd.getMaHopDong(),
                    hd.getMaKhachHang(),
                    hd.getNgayBatDau(),
                    hd.getNgayKetThuc(),
                    String.format("%,d", hd.getTongTien()),
                    String.format("%,d", hd.getDaThanhToan()),
                    hd.getTrangThai()
            });
        }
        resetForm();
    }

    private void timKiemHopDong(String keyword) {
        modelHopDong.setRowCount(0);
        List<HopDong> results = hopDongBUS.timKiemHopDong(keyword);

        for (HopDong hd : results) {
            modelHopDong.addRow(new Object[]{
                    hd.getMaHopDong(),
                    hd.getMaKhachHang(),
                    hd.getNgayBatDau(),
                    hd.getNgayKetThuc(),
                    String.format("%,d", hd.getTongTien()),
                    String.format("%,d", hd.getDaThanhToan()),
                    hd.getTrangThai()
            });
        }
    }

    private void hienThiChiTietHopDong() {
        int selectedRow = tableHopDong.getSelectedRow();
        if (selectedRow < 0) return;

        String maHopDong = tableHopDong.getValueAt(selectedRow, 0).toString();
        HopDong hd = hopDongBUS.getHopDongByMa(maHopDong);

        if (hd != null) {
            txtMaHopDong.setText(hd.getMaHopDong());
            txtMaKhachHang.setText(hd.getMaKhachHang());
            txtNgayBatDau.setText(hd.getNgayBatDau());
            txtNgayKetThuc.setText(hd.getNgayKetThuc());
            txtTongTien.setText(String.valueOf(hd.getTongTien()));
            txtDatCoc.setText(String.valueOf(hd.getDatCoc()));
            txtDaThanhToan.setText(String.valueOf(hd.getDaThanhToan()));
            cboTrangThai.setSelectedItem(hd.getTrangThai());
            txtGhiChu.setText(hd.getGhiChu());

            // Hiển thị chi tiết phòng
            loadChiTietPhong(maHopDong);
        }
    }

    private void loadChiTietPhong(String maHopDong) {
        modelChiTiet.setRowCount(0);
        List<String> dsPhong = cthdBUS.getPhongByMaHopDong(maHopDong);

        for (String maPhong : dsPhong) {
            Phong p = phongBUS.getPhongByMa(maPhong);
            if (p != null) {
                modelChiTiet.addRow(new Object[]{
                        p.getMaPhong(),
                        p.getTenLoai(),
                        String.format("%,d VND", (int) p.getGia())
                });
            }
        }
    }

    private void themHopDong() {
        // Mở form tạo hợp đồng mới
        JOptionPane.showMessageDialog(this,
                "Chức năng thêm hợp đồng mới sẽ mở form đặt phòng/tạo hợp đồng",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        // Có thể mở form đặt phòng tại đây
        // new DatPhongForm().setVisible(true);
    }

    private void capNhatHopDong() {
        int selectedRow = tableHopDong.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hợp đồng để cập nhật",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Chuyển sang trạng thái chỉnh sửa
        txtMaKhachHang.setEditable(true);
        txtNgayBatDau.setEditable(true);
        txtNgayKetThuc.setEditable(true);
        txtTongTien.setEditable(true);
        txtDatCoc.setEditable(true);
        txtDaThanhToan.setEditable(true);
        txtGhiChu.setEditable(true);
    }

    private void huyHopDong() {
        int selectedRow = tableHopDong.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hợp đồng để hủy",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maHopDong = tableHopDong.getValueAt(selectedRow, 0).toString();
        String trangThai = tableHopDong.getValueAt(selectedRow, 6).toString();

        if (trangThai.equals("da_tra") || trangThai.equals("da_huy")) {
            JOptionPane.showMessageDialog(this,
                    "Hợp đồng này đã " + (trangThai.equals("da_tra") ? "trả" : "hủy") + " rồi!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn hủy hợp đồng " + maHopDong + "?",
                "Xác nhận hủy", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (hopDongBUS.capNhatTrangThai(maHopDong, "da_huy")) {
                // Cập nhật trạng thái phòng
                List<String> dsPhong = cthdBUS.getPhongByMaHopDong(maHopDong);
                for (String maPhong : dsPhong) {
                    phongBUS.capNhatTinhTrang(maPhong, "Trống");
                }

                JOptionPane.showMessageDialog(this,
                        "Hủy hợp đồng thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                refreshData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Hủy hợp đồng thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xoaHopDong() {
        int selectedRow = tableHopDong.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một hợp đồng để xóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maHopDong = modelHopDong.getValueAt(selectedRow, 0).toString();
        String trangThai = modelHopDong.getValueAt(selectedRow, 6).toString();

        // Kiểm tra nếu hợp đồng đang sử dụng
        if ("dang_su_dung".equals(trangThai)) {
            JOptionPane.showMessageDialog(this,
                    "Không thể xóa hợp đồng đang sử dụng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa hợp đồng " + maHopDong + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Xóa chi tiết hợp đồng trước
            boolean xoaChiTiet = cthdBUS.xoaTatCaChiTiet(maHopDong);

            if (!xoaChiTiet) {
                JOptionPane.showMessageDialog(this,
                        "Không thể xóa chi tiết hợp đồng. Hủy thao tác.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Sau đó xóa hợp đồng
            if (hopDongBUS.xoaHopDong(maHopDong)) {
                JOptionPane.showMessageDialog(this,
                        "Đã xóa hợp đồng thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);

                // Cập nhật lại danh sách
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không thể xóa hợp đồng. Vui lòng thử lại sau.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi xóa hợp đồng: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void refreshData() {
        int selectedTab = tabbedPane.getSelectedIndex();
        switch (selectedTab) {
            case 0: // Tất cả hợp đồng
                loadAllContracts();
                break;
            case 1: // Hợp đồng đang đặt
                loadPendingContracts();
                break;
            case 2: // Hợp đồng đang sử dụng
                loadActiveContracts();
                break;
            case 3: // Hợp đồng đã trả
                loadCompletedContracts();
                break;
        }
    }

    private void resetForm() {
        txtMaHopDong.setText("");
        txtMaKhachHang.setText("");
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        txtTongTien.setText("");
        txtDatCoc.setText("");
        txtDaThanhToan.setText("");
        txtGhiChu.setText("");
        cboTrangThai.setSelectedIndex(0);
        modelChiTiet.setRowCount(0);

        // Đặt trạng thái không được chỉnh sửa
        txtMaKhachHang.setEditable(false);
        txtNgayBatDau.setEditable(false);
        txtNgayKetThuc.setEditable(false);
        txtTongTien.setEditable(false);
        txtDatCoc.setEditable(false);
        txtDaThanhToan.setEditable(false);
        txtGhiChu.setEditable(false);
    }

    private void luuThayDoi() {
        if (txtMaHopDong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hợp đồng để cập nhật",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String maHopDong = txtMaHopDong.getText().trim();
            String maKhachHang = txtMaKhachHang.getText().trim();
            String ngayBatDau = txtNgayBatDau.getText().trim();
            String ngayKetThuc = txtNgayKetThuc.getText().trim();
            String ghiChu = txtGhiChu.getText().trim();
            String trangThai = cboTrangThai.getSelectedItem().toString();

            // Validate dữ liệu
            if (maKhachHang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng không được để trống",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra tồn tại khách hàng
            if (khachHangBUS.getKhachHangByMa(maKhachHang) == null) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng không tồn tại",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra định dạng ngày
            try {
                LocalDate.parse(ngayBatDau, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate.parse(ngayKetThuc, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ. Sử dụng format: yyyy-MM-dd",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse các giá trị số
            long tongTien, datCoc, daThanhToan;
            try {
                tongTien = Long.parseLong(txtTongTien.getText().trim());
                datCoc = Long.parseLong(txtDatCoc.getText().trim());
                daThanhToan = Long.parseLong(txtDaThanhToan.getText().trim());

                if (tongTien < 0 || datCoc < 0 || daThanhToan < 0) {
                    throw new NumberFormatException("Giá trị không được âm");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho các trường tiền",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật hợp đồng
            HopDong hopDong = new HopDong(maHopDong, maKhachHang, ngayBatDau, ngayKetThuc,
                    (int) tongTien, (int) datCoc, (int) daThanhToan, trangThai, ghiChu);

            if (hopDongBUS.capNhatHopDong(hopDong)) {
                JOptionPane.showMessageDialog(this, "Cập nhật hợp đồng thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                // Nếu thay đổi trạng thái thành đã hủy, cập nhật trạng thái phòng
                if ("da_huy".equals(trangThai)) {
                    List<String> dsPhong = cthdBUS.getPhongByMaHopDong(maHopDong);
                    for (String maPhong : dsPhong) {
                        phongBUS.capNhatTinhTrang(maPhong, "Trống");
                    }
                }

                refreshData();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật hợp đồng thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            // Reset lại trạng thái edit
            txtMaKhachHang.setEditable(false);
            txtNgayBatDau.setEditable(false);
            txtNgayKetThuc.setEditable(false);
            txtTongTien.setEditable(false);
            txtDatCoc.setEditable(false);
            txtDaThanhToan.setEditable(false);
            txtGhiChu.setEditable(false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadDanhSachHopDongDangSuDung() {
        // Đây là alias của loadActiveContracts
        loadActiveContracts();
    }
}