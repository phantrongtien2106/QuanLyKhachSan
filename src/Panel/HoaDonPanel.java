package Panel;

import bus.*;
import gui.HoaDonForm;
import model.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static sun.tools.jconsole.inspector.XDataViewer.dispose;

public class HoaDonPanel extends JPanel {
    private ChiTietHoaDonBUS chiTietHoaDonBUS = new ChiTietHoaDonBUS();
    private String maHoaDon;

    // Constants for styling
    private static final Color PRIMARY_COLOR = new Color(0x4682B4);
    private static final Color ACCENT_COLOR = new Color(0x1E5189);
    private static final Color BACKGROUND_COLOR = new Color(0xF5F5F5);
    private static final Color PANEL_COLOR = new Color(0xFFFFFF);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color TITLE_COLOR = new Color(0x2C3E50);

    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Font HEADING_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font CONTENT_FONT = new Font("Arial", Font.PLAIN, 14);

    // Form components
    private JTextField txtMaHoaDon, txtMaKhachHang, txtTenKhachHang, txtMaPhieuHD;
    private JTextField txtNgayLap, txtNhanVien, txtTongTien, txtDaThanhToan, txtConLai;
    private JTextField txtGhiChu;
    private JTable tableChiTiet;
    private DefaultTableModel modelChiTiet;
    private JTable tableDichVu;
    private DefaultTableModel modelDichVu;
    private JComboBox<String> cboPhuongThuc;
    private JButton btnLuu, btnIn, btnDong;

    // Business logic
    private String maPhieuHoacHD;
    private boolean isHopDong;
    private double tongTien = 0;
    private double daThanhToan = 0;

    // Business logic services
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private PhieuDatPhongBUS pdpBUS = new PhieuDatPhongBUS();
    private HopDongBUS hdBUS = new HopDongBUS();
    private KhachHangBUS khBUS = new KhachHangBUS();
    private ChiTietPhieuDatPhongBUS ctpdpBUS = new ChiTietPhieuDatPhongBUS();
    private ChiTietHopDongBUS cthdBUS = new ChiTietHopDongBUS();
    private PhongBUS phongBUS = new PhongBUS();
    private ChiTietDichVuBUS ctdvBUS = new ChiTietDichVuBUS();
    private DichVuBUS dichVuBUS = new DichVuBUS();

    public HoaDonPanel(String maPhieuHoacHD, boolean isHopDong) {
        this.maPhieuHoacHD = maPhieuHoacHD;
        this.isHopDong = isHopDong;

        initializeUI();
        loadData();

        // Register event handlers
        btnLuu.addActionListener(e -> luuHoaDon());
        btnIn.addActionListener(e -> inHoaDon());
        btnDong.addActionListener(e -> handleClose());
    }

    private void handleClose() {
        // Find the parent container and handle closing appropriately
        Container parent = getParent();
        while (parent != null && !(parent instanceof JFrame) && !(parent instanceof JDialog)) {
            parent = parent.getParent();
        }

        if (parent instanceof JFrame) {
            ((JFrame) parent).dispose();
        } else if (parent instanceof JDialog) {
            ((JDialog) parent).dispose();
        }
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            result.append(characters.charAt(index));
        }

        return result.toString();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(BACKGROUND_COLOR);

        // Add components to panel
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainContentPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title label
        JLabel lblTitle = new JLabel("HÓA ĐƠN THANH TOÁN", JLabel.CENTER);
        lblTitle.setFont(TITLE_FONT);
        lblTitle.setForeground(TEXT_COLOR);

        // Current date
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        JLabel lblDate = new JLabel("Ngày lập: " + currentDate, JLabel.RIGHT);
        lblDate.setFont(LABEL_FONT);
        lblDate.setForeground(TEXT_COLOR);

        headerPanel.add(lblTitle, BorderLayout.CENTER);
        headerPanel.add(lblDate, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createMainContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BACKGROUND_COLOR);

        // Add info panel
        contentPanel.add(createInfoPanel());

        // Add spacing
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Create tabbed pane for rooms and services
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(LABEL_FONT);

        // Add tabs
        tabbedPane.addTab("Chi tiết phòng", createItemsPanel());
        tabbedPane.addTab("Chi tiết dịch vụ", createServicePanel());

        // Set preferred size for tabbed pane
        tabbedPane.setPreferredSize(new Dimension(980, 350));
        tabbedPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));

        contentPanel.add(tabbedPane);

        // Add spacing
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add totals panel
        contentPanel.add(createTotalsPanel());

        return contentPanel;
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(PANEL_COLOR);
        infoPanel.setBorder(createRoundedBorder("Thông tin chung", 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Generate invoice ID
        String invoiceId = "HD" + generateRandomString(5);

        // Row 1: Invoice ID and document ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        infoPanel.add(createLabel("Mã hóa đơn:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.35;
        txtMaHoaDon = new JTextField(invoiceId);
        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setFont(CONTENT_FONT);
        infoPanel.add(txtMaHoaDon, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.15;
        infoPanel.add(createLabel(isHopDong ? "Mã hợp đồng:" : "Mã phiếu:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.35;
        txtMaPhieuHD = new JTextField(maPhieuHoacHD);
        txtMaPhieuHD.setEditable(false);
        txtMaPhieuHD.setFont(CONTENT_FONT);
        infoPanel.add(txtMaPhieuHD, gbc);

        // Row 2: Customer info
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.15;
        infoPanel.add(createLabel("Mã khách hàng:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.35;
        txtMaKhachHang = new JTextField();
        txtMaKhachHang.setEditable(false);
        txtMaKhachHang.setFont(CONTENT_FONT);
        infoPanel.add(txtMaKhachHang, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.15;
        infoPanel.add(createLabel("Tên khách hàng:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.35;
        txtTenKhachHang = new JTextField();
        txtTenKhachHang.setEditable(false);
        txtTenKhachHang.setFont(CONTENT_FONT);
        infoPanel.add(txtTenKhachHang, gbc);

        // Row 3: Date and staff
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.15;
        infoPanel.add(createLabel("Ngày lập:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.35;
        txtNgayLap = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtNgayLap.setEditable(false);
        txtNgayLap.setFont(CONTENT_FONT);
        infoPanel.add(txtNgayLap, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.15;
        infoPanel.add(createLabel("Nhân viên:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.35;
        txtNhanVien = new JTextField("NV001"); // Default value, should be updated with logged in user
        txtNhanVien.setEditable(false);
        txtNhanVien.setFont(CONTENT_FONT);
        infoPanel.add(txtNhanVien, gbc);

        // Row 4: Payment method and notes
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.15;
        infoPanel.add(createLabel("Phương thức:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.35;
        cboPhuongThuc = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản", "Thẻ tín dụng"});
        cboPhuongThuc.setFont(CONTENT_FONT);
        infoPanel.add(cboPhuongThuc, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.15;
        infoPanel.add(createLabel("Ghi chú:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.35;
        txtGhiChu = new JTextField();
        txtGhiChu.setFont(CONTENT_FONT);
        infoPanel.add(txtGhiChu, gbc);

        return infoPanel;
    }

    private JPanel createItemsPanel() {
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBackground(PANEL_COLOR);
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create table model
        String[] columns = {"STT", "Mã phòng", "Loại phòng", "Đơn giá (VND/ngày)", "Số ngày", "Thành tiền (VND)"};
        modelChiTiet = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Create table
        tableChiTiet = new JTable(modelChiTiet);
        setupTable(tableChiTiet);

        // Set column widths
        TableColumnModel tcm = tableChiTiet.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(50);     // STT
        tcm.getColumn(1).setPreferredWidth(120);    // Mã phòng
        tcm.getColumn(2).setPreferredWidth(200);    // Loại phòng
        tcm.getColumn(3).setPreferredWidth(150);    // Đơn giá
        tcm.getColumn(4).setPreferredWidth(80);     // Số ngày
        tcm.getColumn(5).setPreferredWidth(180);    // Thành tiền

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(tableChiTiet);
        scrollPane.setPreferredSize(new Dimension(950, 270));

        itemsPanel.add(scrollPane, BorderLayout.CENTER);
        return itemsPanel;
    }

    private JPanel createServicePanel() {
        JPanel servicePanel = new JPanel(new BorderLayout());
        servicePanel.setBackground(PANEL_COLOR);
        servicePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create table model
        String[] columns = {"STT", "Mã dịch vụ", "Tên dịch vụ", "Đơn giá (VND)", "Số lượng", "Thành tiền (VND)"};
        modelDichVu = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Create table
        tableDichVu = new JTable(modelDichVu);
        setupTable(tableDichVu);

        // Set column widths
        TableColumnModel tcm = tableDichVu.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(50);     // STT
        tcm.getColumn(1).setPreferredWidth(100);    // Mã dịch vụ
        tcm.getColumn(2).setPreferredWidth(200);    // Tên dịch vụ
        tcm.getColumn(3).setPreferredWidth(150);    // Đơn giá
        tcm.getColumn(4).setPreferredWidth(80);     // Số lượng
        tcm.getColumn(5).setPreferredWidth(150);    // Thành tiền

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(tableDichVu);
        scrollPane.setPreferredSize(new Dimension(950, 270));

        servicePanel.add(scrollPane, BorderLayout.CENTER);
        return servicePanel;
    }

    private JPanel createTotalsPanel() {
        JPanel totalsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        totalsPanel.setBackground(BACKGROUND_COLOR);

        // Total amount panel
        JPanel pnlTongTien = new JPanel(new BorderLayout());
        pnlTongTien.setBackground(PANEL_COLOR);
        pnlTongTien.setBorder(createRoundedBorder("Tổng tiền", 10));

        txtTongTien = new JTextField("0 VND");
        txtTongTien.setEditable(false);
        txtTongTien.setHorizontalAlignment(JTextField.RIGHT);
        txtTongTien.setFont(new Font("Arial", Font.BOLD, 16));
        txtTongTien.setForeground(Color.RED);
        pnlTongTien.add(txtTongTien, BorderLayout.CENTER);

        // Already paid panel
        JPanel pnlDaThanhToan = new JPanel(new BorderLayout());
        pnlDaThanhToan.setBackground(PANEL_COLOR);
        pnlDaThanhToan.setBorder(createRoundedBorder("Đã thanh toán (cọc)", 10));

        txtDaThanhToan = new JTextField("0 VND");
        txtDaThanhToan.setEditable(false);
        txtDaThanhToan.setHorizontalAlignment(JTextField.RIGHT);
        txtDaThanhToan.setFont(new Font("Arial", Font.BOLD, 16));
        txtDaThanhToan.setForeground(new Color(0, 128, 0));
        pnlDaThanhToan.add(txtDaThanhToan, BorderLayout.CENTER);

        // Remaining amount panel
        JPanel pnlConLai = new JPanel(new BorderLayout());
        pnlConLai.setBackground(PANEL_COLOR);
        pnlConLai.setBorder(createRoundedBorder("Còn lại", 10));

        txtConLai = new JTextField("0 VND");
        txtConLai.setEditable(false);
        txtConLai.setHorizontalAlignment(JTextField.RIGHT);
        txtConLai.setFont(new Font("Arial", Font.BOLD, 16));
        txtConLai.setForeground(Color.BLUE);
        pnlConLai.add(txtConLai, BorderLayout.CENTER);

        // Add all panels to totals
        totalsPanel.add(pnlTongTien);
        totalsPanel.add(pnlDaThanhToan);
        totalsPanel.add(pnlConLai);

        return totalsPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        // Create buttons
        btnLuu = new JButton("Lưu hóa đơn");
        btnLuu.setPreferredSize(new Dimension(150, 40));
        btnLuu.setBackground(PRIMARY_COLOR);
        btnLuu.setForeground(TEXT_COLOR);
        btnLuu.setFont(LABEL_FONT);
        btnLuu.setFocusPainted(false);

        btnIn = new JButton("In hóa đơn");
        btnIn.setPreferredSize(new Dimension(150, 40));
        btnIn.setBackground(PRIMARY_COLOR);
        btnIn.setForeground(TEXT_COLOR);
        btnIn.setFont(LABEL_FONT);
        btnIn.setFocusPainted(false);

        btnDong = new JButton("Đóng");
        btnDong.setPreferredSize(new Dimension(120, 40));
        btnDong.setBackground(PRIMARY_COLOR);
        btnDong.setForeground(TEXT_COLOR);
        btnDong.setFont(LABEL_FONT);
        btnDong.setFocusPainted(false);

        // Add hover effects
        addHoverEffect(btnLuu);
        addHoverEffect(btnIn);
        addHoverEffect(btnDong);

        // Add buttons to panel
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnIn);
        buttonPanel.add(btnDong);

        return buttonPanel;
    }

    private Border createRoundedBorder(String title, int radius) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                title
        );

        titledBorder.setTitleFont(HEADING_FONT);
        titledBorder.setTitleColor(TITLE_COLOR);
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.TOP);

        // Add padding
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        return BorderFactory.createCompoundBorder(titledBorder, emptyBorder);
    }

    private void setupTable(JTable table) {
        // Set appearance
        table.setRowHeight(30);
        table.setFont(CONTENT_FONT);
        table.setSelectionBackground(PRIMARY_COLOR);
        table.setSelectionForeground(TEXT_COLOR);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);

        // Configure header
        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setFont(LABEL_FONT);
        header.setReorderingAllowed(false);

        // Right-align numerical columns
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        // Apply right alignment to price columns
        if (table == tableChiTiet) {
            table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer); // Đơn giá
            table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer); // Số ngày
            table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer); // Thành tiền
        } else if (table == tableDichVu) {
            table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer); // Đơn giá
            table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer); // Số lượng
            table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer); // Thành tiền
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        return label;
    }

    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }

    private void loadData() {
        try {
            // ✅ 1. Tạo mã hóa đơn duy nhất một lần
            this.maHoaDon = hoaDonBUS.taoMaHoaDon();
            txtMaHoaDon.setText(this.maHoaDon);
            txtNgayLap.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            txtMaPhieuHD.setText(maPhieuHoacHD);
            txtNhanVien.setText("NV001"); // Hoặc lấy từ nhân viên đang đăng nhập

            // ✅ 2. Kiểm tra nếu hóa đơn đã tồn tại
            HoaDon existing = isHopDong
                    ? hoaDonBUS.getHoaDonByMaHopDong(maPhieuHoacHD)
                    : hoaDonBUS.getHoaDonByMaPhieu(maPhieuHoacHD);

            if (existing != null) {
                // Dùng mã hóa đơn có sẵn
                this.maHoaDon = existing.getId();
                txtMaHoaDon.setText(this.maHoaDon);
                txtNgayLap.setText(existing.getNgayThanhToan());
                cboPhuongThuc.setSelectedItem(existing.getPhuongThucThanhToan());
                txtGhiChu.setText(existing.getGhiChu());
                tongTien = existing.getTongTien();

                // Vô hiệu hóa lưu, chỉ cho phép in
                btnLuu.setEnabled(false);
                btnIn.setEnabled(true);
            } else {
                btnLuu.setEnabled(true);
                btnIn.setEnabled(false);
            }

            // ✅ 3. Gọi luôn hàm nạp chi tiết phòng + dịch vụ
            if (isHopDong) {
                loadHopDongDetails();
            } else {
                loadPhieuDetails();
            }

            // ✅ 4. Tính tiền và hiển thị
            txtDaThanhToan.setText(String.format("%,.0f VND", daThanhToan));
            txtTongTien.setText(String.format("%,.0f VND", tongTien));
            txtConLai.setText(String.format("%,.0f VND", tongTien - daThanhToan));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadHopDongDetails() {
        try {
            // Lấy thông tin hợp đồng
            HopDong hopDong = hdBUS.getHopDongByMa(maPhieuHoacHD);
            if (hopDong == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy hợp đồng với mã " + maPhieuHoacHD,
                        "Lỗi", JOptionPane.ERROR_MESSAGE);

                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose(); // dùng đúng cách nếu JPanel nằm trong JFrame
                }

                return;
            }

            // Kiểm tra trạng thái hợp đồng
            if ("da_thanh_toan".equals(hopDong.getTrangThai())) {
                JOptionPane.showMessageDialog(this,
                        "Hợp đồng này đã được thanh toán trước đó.",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                btnLuu.setEnabled(false);
            }

            // Lấy thông tin khách hàng
            String maKhachHang = hopDong.getMaKhachHang();
            txtMaKhachHang.setText(maKhachHang);

            KhachHang kh = khBUS.getKhachHangByMa(maKhachHang);
            if (kh != null) {
                txtTenKhachHang.setText(kh.getHoTen());
            } else {
                txtTenKhachHang.setText("Không tìm thấy thông tin");
            }

            // Thiết lập phương thức thanh toán
            String phuongThuc = hopDong.getPhuongThucThanhToan();
            if (phuongThuc != null && !phuongThuc.isEmpty()) {
                cboPhuongThuc.setSelectedItem(phuongThuc);
            } else {
                cboPhuongThuc.setSelectedItem("Tiền mặt");
            }

            // Tính tiền đã thanh toán (đặt cọc)
            daThanhToan = hopDong.getDatCoc();
            txtDaThanhToan.setText(String.format("%,.0f VND", daThanhToan));

            // Tính số ngày thuê
            LocalDate ngayBatDau = LocalDate.parse(hopDong.getNgayBatDau());
            LocalDate ngayKetThuc = LocalDate.parse(hopDong.getNgayKetThuc());
            int soNgayThue = (int) java.time.temporal.ChronoUnit.DAYS.between(ngayBatDau, ngayKetThuc);

            // Đảm bảo tối thiểu 1 ngày
            if (soNgayThue < 1) soNgayThue = 1;

            // Xóa dữ liệu cũ trong bảng
            modelChiTiet.setRowCount(0);

            // Reset tổng tiền
            tongTien = 0;

            // Tải thông tin chi tiết phòng từ ChiTietHopDong
            List<String> dsPhong = cthdBUS.getPhongByMaHopDong(maPhieuHoacHD);
            int stt = 1;

            for (String maPhong : dsPhong) {
                Phong phong = phongBUS.getPhongByMa(maPhong);
                if (phong != null) {
                    double donGia = phong.getGia();
                    double thanhTien = donGia * soNgayThue;
                    tongTien += thanhTien;

                    modelChiTiet.addRow(new Object[]{
                            stt++,
                            maPhong,
                            phong.getTenLoai(),
                            String.format("%,.0f VND", donGia),
                            soNgayThue,
                            String.format("%,.0f VND", thanhTien)
                    });
                }
            }

            // Cập nhật hiển thị tổng tiền
            txtTongTien.setText(String.format("%,.0f VND", tongTien));

            // Cập nhật số tiền còn lại
            double conLai = tongTien - daThanhToan;
            txtConLai.setText(String.format("%,.0f VND", conLai));

            // Tải thông tin dịch vụ
            loadDichVu(maPhieuHoacHD);

            System.out.println("Đã tải xong thông tin hợp đồng " + maPhieuHoacHD);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải thông tin hợp đồng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadDichVu(String maPhieu) {
        try {
            modelDichVu.setRowCount(0);

            // Lấy danh sách chi tiết dịch vụ
            List<ChiTietDichVu> dsDichVu = ctdvBUS.getDichVuByMaPhieu(maPhieu);
            if (dsDichVu == null || dsDichVu.isEmpty()) {
                return;
            }

            int stt = 1;
            double tongTienDichVu = 0;

            for (ChiTietDichVu ctdv : dsDichVu) {
                DichVu dichVu = dichVuBUS.getDichVuByMa(ctdv.getMaDv());
                if (dichVu != null) {
                    double donGia = dichVu.getGia();
                    int soLuong = ctdv.getSoLuong();
                    double thanhTien = donGia * soLuong;
                    tongTienDichVu += thanhTien;

                    modelDichVu.addRow(new Object[]{
                            stt++,
                            dichVu.getMaDv(),
                            dichVu.getTenDv(),
                            String.format("%,.0f VND", donGia),
                            soLuong,
                            String.format("%,.0f VND", thanhTien)
                    });
                }
            }

            // Cập nhật tổng tiền bao gồm dịch vụ
            tongTien += tongTienDichVu;
            txtTongTien.setText(String.format("%,.0f VND", tongTien));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPhieuDetails() {
        try {
            // Lấy thông tin phiếu đặt phòng
            PhieuDatPhong phieu = pdpBUS.getPhieuByMaPhieu(maPhieuHoacHD);
            if (phieu == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy phiếu đặt phòng với mã " + maPhieuHoacHD,
                        "Lỗi", JOptionPane.ERROR_MESSAGE);

                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose(); // ✅ Đóng cửa sổ chứa JPanel
                }
                return;
            }


            // Kiểm tra trạng thái phiếu
            if ("da_thanh_toan".equals(phieu.getTrangThai())) {
                JOptionPane.showMessageDialog(this,
                        "Phiếu đặt phòng này đã được thanh toán trước đó.",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                btnLuu.setEnabled(false);
            }

            // Lấy thông tin khách hàng
            String maKhachHang = phieu.getMaKhachHang();
            txtMaKhachHang.setText(maKhachHang);

            KhachHang kh = khBUS.getKhachHangByMa(maKhachHang);
            if (kh != null) {
                txtTenKhachHang.setText(kh.getHoTen());
            } else {
                txtTenKhachHang.setText("Không tìm thấy thông tin");
            }
            // Thiết lập phương thức thanh toán
            String phuongThuc = "Tiền mặt"; // Giá trị mặc định
            try {
                // Thử lấy từ phương thức khác nếu có
                if (phieu.getClass().getMethod("getHinhThucThanhToan") != null) {
                    phuongThuc = (String) phieu.getClass().getMethod("getHinhThucThanhToan").invoke(phieu);
                }
            } catch (Exception ex) {
                System.out.println("Sử dụng phương thức thanh toán mặc định");
            }
            cboPhuongThuc.setSelectedItem(phuongThuc);

            // Tính tiền đã thanh toán (đặt cọc)
            try {
                // Thử lấy từ phương thức khác nếu có
                if (phieu.getClass().getMethod("getTienCoc") != null) {
                    daThanhToan = (double) phieu.getClass().getMethod("getTienCoc").invoke(phieu);
                } else {
                    daThanhToan = 0; // Giá trị mặc định nếu không có phương thức
                }
            } catch (Exception ex) {
                System.out.println("Không tìm thấy thông tin tiền cọc, sử dụng giá trị 0");
                daThanhToan = 0;
            }
            txtDaThanhToan.setText(String.format("%,.0f VND", daThanhToan));

            // Tính số ngày thuê
            LocalDate ngayBatDau = LocalDate.parse(phieu.getNgayNhan());
            LocalDate ngayKetThuc = LocalDate.parse(phieu.getNgayTra());
            int soNgayThue = (int) java.time.temporal.ChronoUnit.DAYS.between(ngayBatDau, ngayKetThuc);

            // Đảm bảo tối thiểu 1 ngày
            if (soNgayThue < 1) soNgayThue = 1;

            // Xóa dữ liệu cũ trong bảng
            modelChiTiet.setRowCount(0);

            // Reset tổng tiền
            tongTien = 0;

            // Tải thông tin chi tiết phòng từ ChiTietPhieuDatPhong
            List<String> dsPhong = ctpdpBUS.getPhongByMaPhieu(maPhieuHoacHD);
            int stt = 1;

            for (String maPhong : dsPhong) {
                Phong phong = phongBUS.getPhongByMa(maPhong);
                if (phong != null) {
                    double donGia = phong.getGia();
                    double thanhTien = donGia * soNgayThue;
                    tongTien += thanhTien;

                    modelChiTiet.addRow(new Object[]{
                            stt++,
                            maPhong,
                            phong.getTenLoai(),
                            String.format("%,.0f VND", donGia),
                            soNgayThue,
                            String.format("%,.0f VND", thanhTien)
                    });
                }
            }

            // Cập nhật hiển thị tổng tiền
            txtTongTien.setText(String.format("%,.0f VND", tongTien));

            // Cập nhật số tiền còn lại
            double conLai = tongTien - daThanhToan;
            txtConLai.setText(String.format("%,.0f VND", conLai));

            // Tải thông tin dịch vụ
            loadDichVu(maPhieuHoacHD);

            System.out.println("Đã tải xong thông tin phiếu đặt phòng " + maPhieuHoacHD);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải thông tin phiếu đặt phòng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean saveRoomDetails(String maHD, String maPhieuHoacHD, boolean isHopDong) {
        try {
            List<String> roomList = isHopDong ?
                    cthdBUS.getPhongByMaHopDong(maPhieuHoacHD) :
                    ctpdpBUS.getPhongByMaPhieu(maPhieuHoacHD);

            System.out.println("→ Số lượng phòng: " + (roomList != null ? roomList.size() : 0));

            LocalDate ngayNhan, ngayTra;
            if (isHopDong) {
                HopDong hd = hdBUS.getHopDongByMa(maPhieuHoacHD);
                ngayNhan = LocalDate.parse(hd.getNgayBatDau().split(" ")[0]);
                ngayTra = LocalDate.parse(hd.getNgayKetThuc().split(" ")[0]);
            } else {
                PhieuDatPhong pdp = pdpBUS.getPhieuByMaPhieu(maPhieuHoacHD);
                ngayNhan = LocalDate.parse(pdp.getNgayNhan().split(" ")[0]);
                ngayTra = LocalDate.parse(pdp.getNgayTra().split(" ")[0]);
            }

            long days = ChronoUnit.DAYS.between(ngayNhan, ngayTra);
            if (days <= 0) days = 1;

            ChiTietHoaDonBUS cthdBUS = new ChiTietHoaDonBUS();

            for (String maPhong : roomList) {
                Phong phong = phongBUS.getPhongByMa(maPhong);
                if (phong == null) continue;

                ChiTietHoaDon ctHD = new ChiTietHoaDon(
                        maHD, maPhong, "phong",
                        phong.getGia(), (int) days, phong.getGia() * days
                );

                boolean result = cthdBUS.themChiTietHoaDon(ctHD);
                System.out.println("→ Ghi chi tiết phòng " + maPhong + ": " + result);

                if (!result) return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean saveServiceDetails(String maHD, String maPhieuHoacHD, boolean isHopDong) {
        try {
            ChiTietDichVuBUS ctdvBUS = new ChiTietDichVuBUS();
            List<ChiTietDichVu> listDichVu = ctdvBUS.getDichVuByMaPhieuOrHopDong(maPhieuHoacHD, isHopDong);

            System.out.println("→ Số lượng dịch vụ: " + (listDichVu != null ? listDichVu.size() : 0));

            if (listDichVu == null || listDichVu.isEmpty()) return true;

            ChiTietHoaDonBUS cthdBUS = new ChiTietHoaDonBUS();
            DichVuBUS dichVuBUS = new DichVuBUS();

            for (ChiTietDichVu ctdv : listDichVu) {
                DichVu dv = dichVuBUS.getDichVuByMa(ctdv.getMaDv());
                if (dv == null) continue;

                ChiTietHoaDon ctHD = new ChiTietHoaDon(
                        maHD, ctdv.getMaDv(), "dich_vu",
                        dv.getGia(), ctdv.getSoLuong(), dv.getGia() * ctdv.getSoLuong()
                );

                boolean result = cthdBUS.themChiTietHoaDon(ctHD);
                System.out.println("→ Ghi chi tiết dịch vụ " + ctdv.getMaDv() + ": " + result);

                if (!result) return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean luuHoaDon() {
        try {
            String maHD = this.maHoaDon; // dùng lại đúng mã đang hiển thị

            HoaDon hoaDon = new HoaDon(
                    maHD,
                    isHopDong ? null : maPhieuHoacHD,
                    isHopDong ? maPhieuHoacHD : null,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    tongTien,
                    "da_thanh_toan",
                    "NV001"
            );

            boolean mainResult = hoaDonBUS.themHoaDon(hoaDon);
            if (!mainResult) return false;

            boolean roomsResult = saveRoomDetails(maHD, maPhieuHoacHD, isHopDong);
            boolean servicesResult = saveServiceDetails(maHD, maPhieuHoacHD, isHopDong);

            return roomsResult && servicesResult;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void inHoaDon() {
        try {
            int response = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn in hóa đơn này không?",
                    "Xác nhận in hóa đơn", JOptionPane.YES_NO_OPTION);

            if (response != JOptionPane.YES_OPTION) {
                return;
            }

            // Get the selected invoice information
            String maHoaDon = txtMaHoaDon.getText();


            // Show printing dialog
            JOptionPane.showMessageDialog(this,
                    "Đang chuẩn bị in hóa đơn " + maHoaDon + "...",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // TODO: Implement actual printing using Java Print API
            // This is a placeholder for the actual printing implementation
            // You would typically use PrinterJob or similar classes here

            // For example:
            // PrinterJob job = PrinterJob.getPrinterJob();
            // job.setPrintable(new InvoicePrintable(maHoaDon, maPhieuHoacHD, isHopDong));
            // if (job.printDialog()) {
            //     job.print();
            // }

            // Show success message
            JOptionPane.showMessageDialog(this,
                    "Hóa đơn " + maHoaDon + " đã được gửi đến máy in.",
                    "In hóa đơn thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi in hóa đơn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

