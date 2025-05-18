package Panel;

import bus.*;
import model.*;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatPhongPanel extends JPanel {
    // Hằng số cho thiết kế
    private static final Color PRIMARY_COLOR = new Color(0x4682B4);
    private static final Color BACKGROUND_COLOR = new Color(0xF5F5F5);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final int PADDING = 10;

    private JComboBox<String> cbKhachHang;
    private JTextField txtTen, txtCCCD, txtSDT, txtDiaChi;
    private DatePicker dpNgayNhan, dpNgayTra;
    private JTextArea txtGhiChu;
    private JTable tablePhong;
    private DefaultTableModel tableModel;

    private KhachHangBUS khBUS = new KhachHangBUS();
    private PhongBUS phongBUS = new PhongBUS();
    private PhieuDatPhongBUS phieuBUS = new PhieuDatPhongBUS();
    private ChiTietPhieuDatPhongBUS ctBUS = new ChiTietPhieuDatPhongBUS();

    public DatPhongPanel() {
        setLayout(new BorderLayout(PADDING, PADDING));
        setBackground(BACKGROUND_COLOR);

        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.setPreferredSize(new Dimension(getWidth(), 50));

        JLabel titleLabel = new JLabel("ĐẶT PHÒNG", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Panel chính chứa form và bảng phòng
        JPanel mainPanel = new JPanel(new BorderLayout(PADDING, PADDING));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // Form thông tin khách và đặt phòng
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Bảng chọn phòng
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Panel nút hành động
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        JButton btnDat = createStyledButton("Đặt phòng");
        btnDat.addActionListener(this::handleDatPhong);
        buttonPanel.add(btnDat);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Combine panels
        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Khởi tạo dữ liệu
        loadKhachHang();
        loadPhongTrong();
        if (cbKhachHang.getItemCount() > 0) {
            String maKH = cbKhachHang.getSelectedItem().toString().split(" - ")[0];
            toggleFields(maKH.equals("VL001"));
        }

        SwingUtilities.invokeLater(() -> {
            loadPhongTrong();
            System.out.println("Đã tải lại danh sách phòng trống");
        });
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));

        // Phần thông tin khách hàng - làm gọn hơn
        JPanel khachHangPanel = new JPanel(new GridLayout(2, 4, PADDING, PADDING));
        khachHangPanel.setBackground(BACKGROUND_COLOR);

        // Tiêu đề thông tin khách hàng
        JLabel khTitle = new JLabel("THÔNG TIN KHÁCH HÀNG", JLabel.LEFT);
        khTitle.setFont(LABEL_FONT);
        khTitle.setForeground(PRIMARY_COLOR.darker());

        // Các trường thông tin cơ bản
        cbKhachHang = new JComboBox<>();
        cbKhachHang.setPreferredSize(new Dimension(200, 30));
        txtTen = createStyledTextField();
        txtCCCD = createStyledTextField();
        txtSDT = createStyledTextField();
        txtDiaChi = createStyledTextField();

        // Thêm các thành phần vào panel - 2 dòng, 4 cột
        khachHangPanel.add(createStyledLabel("Khách hàng:"));
        khachHangPanel.add(cbKhachHang);
        khachHangPanel.add(createStyledLabel("Tên khách:"));
        khachHangPanel.add(txtTen);
        khachHangPanel.add(createStyledLabel("CCCD:"));
        khachHangPanel.add(txtCCCD);
        khachHangPanel.add(createStyledLabel("SĐT:"));
        khachHangPanel.add(txtSDT);

        // Thêm địa chỉ vào panel riêng
        JPanel diaChiPanel = new JPanel(new BorderLayout(PADDING, PADDING));
        diaChiPanel.setBackground(BACKGROUND_COLOR);
        diaChiPanel.add(createStyledLabel("Địa chỉ:"), BorderLayout.WEST);
        diaChiPanel.add(txtDiaChi, BorderLayout.CENTER);

        // Panel thông tin đặt phòng - sử dụng DatePicker
        JPanel datPhongPanel = new JPanel(new GridLayout(2, 2, PADDING, PADDING));
        datPhongPanel.setBackground(BACKGROUND_COLOR);

        // Tiêu đề thông tin đặt phòng
        JLabel dpTitle = new JLabel("THÔNG TIN ĐẶT PHÒNG", JLabel.LEFT);
        dpTitle.setFont(LABEL_FONT);
        dpTitle.setForeground(PRIMARY_COLOR.darker());

        // Tạo DatePicker với style phù hợp
        DatePickerSettings ngayNhanSettings = new DatePickerSettings();
        DatePickerSettings ngayTraSettings = new DatePickerSettings();

        ngayNhanSettings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, PRIMARY_COLOR);
        ngayNhanSettings.setColor(DatePickerSettings.DateArea.TextTodayLabel, PRIMARY_COLOR.darker());
        ngayTraSettings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, PRIMARY_COLOR);
        ngayTraSettings.setColor(DatePickerSettings.DateArea.TextTodayLabel, PRIMARY_COLOR.darker());

        dpNgayNhan = new DatePicker(ngayNhanSettings);
        dpNgayTra = new DatePicker(ngayTraSettings);
        dpNgayNhan.setDate(LocalDate.now());
        dpNgayTra.setDate(LocalDate.now().plusDays(1));

        txtGhiChu = new JTextArea(3, 20);
        txtGhiChu.setFont(new Font("Arial", Font.PLAIN, 13));
        txtGhiChu.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR.brighter(), 1));
        JScrollPane scrollGhiChu = new JScrollPane(txtGhiChu);

        datPhongPanel.add(createStyledLabel("Ngày nhận:"));
        datPhongPanel.add(dpNgayNhan);
        datPhongPanel.add(createStyledLabel("Ngày trả:"));
        datPhongPanel.add(dpNgayTra);

        JPanel ghiChuPanel = new JPanel(new BorderLayout(PADDING, PADDING));
        ghiChuPanel.setBackground(BACKGROUND_COLOR);
        ghiChuPanel.add(createStyledLabel("Ghi chú:"), BorderLayout.NORTH);
        ghiChuPanel.add(scrollGhiChu, BorderLayout.CENTER);

        // Thêm các tiêu đề và panel con vào panel chính
        panel.add(khTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(khachHangPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(diaChiPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(dpTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(datPhongPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(ghiChuPanel);

        // Xử lý sự kiện khi chọn khách hàng
        cbKhachHang.addActionListener(e -> {
            if (cbKhachHang.getSelectedItem() == null) return;

            String maKH = cbKhachHang.getSelectedItem().toString().split(" - ")[0];
            boolean isVL = maKH.equals("VL001");

            toggleFields(isVL);

            if (!isVL) {
                KhachHang kh = khBUS.getKhachHangByMa(maKH);
                if (kh != null) {
                    txtTen.setText(kh.getHoTen());
                    txtCCCD.setText(kh.getCccd());
                    txtSDT.setText(kh.getSdt());
                    txtDiaChi.setText(kh.getDiaChi());
                }
            } else {
                txtTen.setText("");
                txtCCCD.setText("");
                txtSDT.setText("");
                txtDiaChi.setText("");
            }
        });

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(PADDING, PADDING));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));

        // Tiêu đề bảng phòng
        JLabel tableTitle = new JLabel("DANH SÁCH PHÒNG TRỐNG", JLabel.LEFT);
        tableTitle.setFont(LABEL_FONT);
        tableTitle.setForeground(PRIMARY_COLOR.darker());
        panel.add(tableTitle, BorderLayout.NORTH);

        // Tạo model và table
        tableModel = new DefaultTableModel(new String[]{"Chọn", "Mã phòng", "Tên loại", "Tình trạng"}, 0) {
            @Override
            public Class<?> getColumnClass(int col) {
                return col == 0 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 0;
            }
        };

        tablePhong = new JTable(tableModel);
        tablePhong.setRowHeight(25);
        tablePhong.setSelectionBackground(PRIMARY_COLOR.darker());
        tablePhong.setSelectionForeground(TEXT_COLOR);
        tablePhong.setGridColor(Color.LIGHT_GRAY);
        tablePhong.setFont(new Font("Arial", Font.PLAIN, 13));
        tablePhong.setBackground(Color.WHITE);

        // Thiết lập header
        JTableHeader header = tablePhong.getTableHeader();
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setFont(LABEL_FONT);
        header.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1));
        header.setOpaque(true);

        // Áp dụng renderer tùy chỉnh cho header
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setBackground(PRIMARY_COLOR);
                label.setForeground(TEXT_COLOR);
                label.setFont(LABEL_FONT);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, PRIMARY_COLOR.darker()));
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablePhong);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR.brighter(), 1));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY_COLOR);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(TEXT_COLOR);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };

        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(150, 35));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        return button;
    }

    private void toggleFields(boolean isVL) {
        txtTen.setEnabled(isVL);
        txtCCCD.setEnabled(isVL);
        txtSDT.setEnabled(isVL);
        txtDiaChi.setEnabled(isVL);
    }

    private void loadKhachHang() {
        cbKhachHang.removeAllItems();
        for (KhachHang kh : khBUS.getAllKhachHang()) {
            cbKhachHang.addItem(kh.getMaKhachHang() + " - " + kh.getHoTen());
        }
    }

    private void loadPhongTrong() {
        tableModel.setRowCount(0);
        List<Phong> phongTrong = phongBUS.getPhongTrong();

        System.out.println("Loading " + phongTrong.size() + " available rooms");

        for (Phong p : phongTrong) {
            tableModel.addRow(new Object[]{false, p.getMaPhong(), p.getTenLoai(), p.getTinhTrang()});
        }

        // Force table refresh
        tablePhong.repaint();
    }

    private void handleDatPhong(ActionEvent e) {
        String maPhieu = autoGenerateMaPhieu();

        if (cbKhachHang.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!");
            return;
        }

        String maKH = cbKhachHang.getSelectedItem().toString().split(" - ")[0];

        // Kiểm tra ngày nhận và ngày trả từ DatePicker
        if (dpNgayNhan.getDate() == null || dpNgayTra.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày nhận và ngày trả!");
            return;
        }

        // Kiểm tra ngày trả phải sau ngày nhận
        if (!dpNgayTra.getDate().isAfter(dpNgayNhan.getDate())) {
            JOptionPane.showMessageDialog(this, "Ngày trả phải sau ngày nhận!");
            return;
        }

        if (maKH.equals("VL001")) {
            if (txtTen.getText().isBlank() || txtCCCD.getText().isBlank()
                    || txtSDT.getText().isBlank() || txtDiaChi.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khách vãng lai!");
                return;
            }

            // Sinh mã khách vãng lai mới
            maKH = autoGenerateMaKH();

            // Khởi tạo đối tượng KhachHang đúng tham số String
            KhachHang moi = new KhachHang(
                    maKH,
                    txtTen.getText(),
                    txtCCCD.getText(),
                    txtSDT.getText(),
                    txtDiaChi.getText()
            );

            // Thêm vào DB
            if (!khBUS.themKhachHang(moi)) {
                JOptionPane.showMessageDialog(this, "Không thể thêm khách vãng lai mới!");
                return;
            }
        }

        // Lấy ngày nhận và ngày trả từ DatePicker
        String ngayNhan = dpNgayNhan.getDate().toString();
        String ngayTra = dpNgayTra.getDate().toString();
        String ghiChu = txtGhiChu.getText();
        String ngayDat = LocalDate.now().toString();

        List<String> danhSachPhong = new java.util.ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean chon = (Boolean) tableModel.getValueAt(i, 0);
            if (chon != null && chon) {
                danhSachPhong.add(tableModel.getValueAt(i, 1).toString());
            }
        }

        if (danhSachPhong.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất 1 phòng!");
            return;
        }

        // Giới hạn tối đa 3 phòng nếu dùng phiếu đặt
        if (danhSachPhong.size() > 3) {
            JOptionPane.showMessageDialog(this, "Khách đặt hơn 3 phòng. Vui lòng dùng chức năng Hợp đồng thuê.");
            return;
        }

        // Tạo phiếu đặt phòng
        PhieuDatPhong pdp = new PhieuDatPhong(maPhieu, maKH, ngayNhan, ngayTra, ghiChu, "dang_dat", ngayDat);
        if (!phieuBUS.themPhieuDatPhong(pdp)) {
            JOptionPane.showMessageDialog(this, "Không thể tạo phiếu!");
            return;
        }

        // Theo dõi thành công/thất bại của từng phòng
        List<String> phongThanhCong = new ArrayList<>();
        List<String> phongThatBai = new ArrayList<>();

        // Cập nhật chi tiết và trạng thái phòng
        for (String maPhong : danhSachPhong) {
            try {
                // Lấy đơn giá phòng
                double donGia = phongBUS.getDonGiaByMaPhong(maPhong);

                // Thêm chi tiết phiếu đặt phòng
                boolean themChiTietOK = ctBUS.themChiTiet(new ChiTietPhieuDatPhong(maPhieu, maPhong, donGia));

                // Cập nhật trạng thái phòng
                boolean capNhatPhongOK = phongBUS.capNhatTinhTrangVaNguon(maPhong, "Đang đặt", "phieu");

                // Kiểm tra kết quả
                if (themChiTietOK && capNhatPhongOK) {
                    phongThanhCong.add(maPhong);
                    System.out.println("Đã cập nhật phòng " + maPhong + " thành Đang đặt");
                } else {
                    phongThatBai.add(maPhong);
                    System.err.println("Lỗi cập nhật phòng " + maPhong +
                        ": themChiTiet=" + themChiTietOK + ", capNhatPhong=" + capNhatPhongOK);
                }
            } catch (Exception ex) {
                phongThatBai.add(maPhong);
                System.err.println("Exception khi xử lý phòng " + maPhong + ": " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        // Hiển thị kết quả tổng hợp
        if (phongThatBai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Đặt phòng thành công!");
        } else {
            StringBuilder sb = new StringBuilder("Đặt phòng một phần thành công!\n\n");

            if (!phongThanhCong.isEmpty()) {
                sb.append("Phòng đã đặt thành công: ").append(String.join(", ", phongThanhCong)).append("\n\n");
            }

            sb.append("Phòng không thể cập nhật: ").append(String.join(", ", phongThatBai));
            sb.append("\n\nVui lòng kiểm tra lại trạng thái phòng và cập nhật thủ công nếu cần.");

            JOptionPane.showMessageDialog(this, sb.toString(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }

        // Làm mới danh sách phòng trống
        SwingUtilities.invokeLater(() -> {
            loadPhongTrong();
            System.out.println("Đã làm mới danh sách phòng trống sau khi đặt phòng");
        });
    }

    private String autoGenerateMaPhieu() {
        return "PDP" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
    }

    private String autoGenerateMaKH() {
        return khBUS.getNextMaKhachHang();
    }
}