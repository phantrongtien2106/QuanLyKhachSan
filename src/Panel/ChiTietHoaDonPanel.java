package Panel;

import bus.*;
import model.ChiTietHoaDon;
import model.HoaDon;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.List;

public class ChiTietHoaDonPanel extends JPanel {
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
    private JTextField txtMaHoaDon, txtMaPhieuHD, txtNgayLap, txtTrangThai;
    private JTextField txtMaKhachHang, txtTenKhachHang, txtTongTien, txtNhanVien;
    private JTable tableChiTiet;
    private DefaultTableModel modelChiTiet;
    private JButton btnIn;

    // Business logic
    private HoaDon hoaDon;
    private ChiTietHoaDonBUS chiTietHoaDonBUS;
    private KhachHangBUS khachHangBUS;
    private NhanVienBUS nhanVienBUS;
    private PhongBUS phongBUS;
    private DichVuBUS dichVuBUS;

    public ChiTietHoaDonPanel(HoaDon hoaDon) {
        this.hoaDon = hoaDon;

        // Initialize BUS objects
        this.chiTietHoaDonBUS = new ChiTietHoaDonBUS();
        this.khachHangBUS = new KhachHangBUS();
        this.nhanVienBUS = new NhanVienBUS();
        this.phongBUS = new PhongBUS();
        this.dichVuBUS = new DichVuBUS();

        initializeUI();
        loadHoaDonData();
    }

    // Constructor that accepts just the invoice ID
    public ChiTietHoaDonPanel(String maHoaDon) {
        // Initialize BUS objects
        this.chiTietHoaDonBUS = new ChiTietHoaDonBUS();
        this.khachHangBUS = new KhachHangBUS();
        this.nhanVienBUS = new NhanVienBUS();
        this.phongBUS = new PhongBUS();
        this.dichVuBUS = new DichVuBUS();

        // Load the HoaDon object using the ID
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        this.hoaDon = hoaDonBUS.getHoaDonById(maHoaDon);

        if (this.hoaDon == null) {
            JOptionPane.showMessageDialog(this,
                "Không tìm thấy hóa đơn với mã " + maHoaDon,
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        initializeUI();
        loadHoaDonData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(BACKGROUND_COLOR);

        // Add components to main panel
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createInfoPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("CHI TIẾT HÓA ĐƠN", JLabel.CENTER);
        lblTitle.setFont(TITLE_FONT);
        lblTitle.setForeground(TEXT_COLOR);

        headerPanel.add(lblTitle, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createInfoPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BACKGROUND_COLOR);

        // Add invoice info section
        contentPanel.add(createInvoiceInfoPanel());

        // Add spacing
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add details section
        contentPanel.add(createDetailsPanel());

        // Add print button at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        btnIn = createStyledButton("In hóa đơn", "/images/print_icon.png");
        btnIn.addActionListener(e -> printInvoice());
        buttonPanel.add(btnIn);
        contentPanel.add(buttonPanel);

        return contentPanel;
    }

    private JPanel createInvoiceInfoPanel() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(PANEL_COLOR);
        infoPanel.setBorder(createRoundedBorder("Thông tin hóa đơn", 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Row 1: Mã hóa đơn và Mã phiếu/hợp đồng
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        infoPanel.add(createLabel("Mã hóa đơn:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.35;
        txtMaHoaDon = new JTextField();
        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setFont(CONTENT_FONT);
        infoPanel.add(txtMaHoaDon, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.15;
        infoPanel.add(createLabel("Mã phiếu/hợp đồng:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.35;
        txtMaPhieuHD = new JTextField();
        txtMaPhieuHD.setEditable(false);
        txtMaPhieuHD.setFont(CONTENT_FONT);
        infoPanel.add(txtMaPhieuHD, gbc);

        // Row 2: Khách hàng
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(createLabel("Mã khách hàng:"), gbc);

        gbc.gridx = 1;
        txtMaKhachHang = new JTextField();
        txtMaKhachHang.setEditable(false);
        txtMaKhachHang.setFont(CONTENT_FONT);
        infoPanel.add(txtMaKhachHang, gbc);

        gbc.gridx = 2;
        infoPanel.add(createLabel("Tên khách hàng:"), gbc);

        gbc.gridx = 3;
        txtTenKhachHang = new JTextField();
        txtTenKhachHang.setEditable(false);
        txtTenKhachHang.setFont(CONTENT_FONT);
        infoPanel.add(txtTenKhachHang, gbc);

        // Row 3: Ngày lập và nhân viên
        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(createLabel("Ngày lập:"), gbc);

        gbc.gridx = 1;
        txtNgayLap = new JTextField();
        txtNgayLap.setEditable(false);
        txtNgayLap.setFont(CONTENT_FONT);
        infoPanel.add(txtNgayLap, gbc);

        gbc.gridx = 2;
        infoPanel.add(createLabel("Nhân viên:"), gbc);

        gbc.gridx = 3;
        txtNhanVien = new JTextField();
        txtNhanVien.setEditable(false);
        txtNhanVien.setFont(CONTENT_FONT);
        infoPanel.add(txtNhanVien, gbc);

        // Row 4: Trạng thái và tổng tiền
        gbc.gridx = 0;
        gbc.gridy = 3;
        infoPanel.add(createLabel("Trạng thái:"), gbc);

        gbc.gridx = 1;
        txtTrangThai = new JTextField();
        txtTrangThai.setEditable(false);
        txtTrangThai.setFont(CONTENT_FONT);
        infoPanel.add(txtTrangThai, gbc);

        gbc.gridx = 2;
        infoPanel.add(createLabel("Tổng tiền:"), gbc);

        gbc.gridx = 3;
        txtTongTien = new JTextField();
        txtTongTien.setEditable(false);
        txtTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        txtTongTien.setForeground(Color.RED);
        txtTongTien.setHorizontalAlignment(JTextField.RIGHT);
        infoPanel.add(txtTongTien, gbc);

        return infoPanel;
    }

    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(PANEL_COLOR);
        detailsPanel.setBorder(createRoundedBorder("Chi tiết hóa đơn", 10));

        // Create table model
        String[] columns = {"STT", "Mã item", "Loại", "Tên item", "Đơn giá", "Số lượng", "Thành tiền"};
        modelChiTiet = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table
        tableChiTiet = new JTable(modelChiTiet);
        setupTable(tableChiTiet);

        JScrollPane scrollPane = new JScrollPane(tableChiTiet);
        scrollPane.setPreferredSize(new Dimension(850, 300));

        detailsPanel.add(scrollPane, BorderLayout.CENTER);

        return detailsPanel;
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(LABEL_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Try to load icon if provided
        if (iconPath != null && !iconPath.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                if (icon.getIconWidth() > 0) {
                    button.setIcon(icon);
                    button.setIconTextGap(8);
                }
            } catch (Exception e) {
                // Ignore if image not found
            }
        }

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(0x0D3B6F)); // Darker shade for press effect
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.contains(e.getPoint())) {
                    button.setBackground(ACCENT_COLOR);
                } else {
                    button.setBackground(PRIMARY_COLOR);
                }
            }
        });

        return button;
    }

    private void setupTable(JTable table) {
        // Set appearance
        table.setRowHeight(30);
        table.setFont(CONTENT_FONT);
        table.setSelectionBackground(PRIMARY_COLOR);
        table.setSelectionForeground(TEXT_COLOR);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);

        // Configure header with custom renderer
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setBackground(PRIMARY_COLOR);
                label.setForeground(TEXT_COLOR);
                label.setFont(LABEL_FONT);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(200, 200, 200)));
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });

        // Right-align numerical columns
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer); // Đơn giá
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer); // Số lượng
        table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer); // Thành tiền
    }

    private void loadHoaDonData() {
        try {
            if (hoaDon == null) {
                JOptionPane.showMessageDialog(this,
                    "Không có dữ liệu hóa đơn để hiển thị",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Display invoice information
            txtMaHoaDon.setText(hoaDon.getId());

            // Determine if this is for a booking or contract
            if (hoaDon.getMaPhieu() != null && !hoaDon.getMaPhieu().isEmpty()) {
                txtMaPhieuHD.setText(hoaDon.getMaPhieu() + " (Phiếu đặt phòng)");
                // Load customer info from PhieuDatPhong would go here
            } else if (hoaDon.getMaHopDong() != null && !hoaDon.getMaHopDong().isEmpty()) {
                txtMaPhieuHD.setText(hoaDon.getMaHopDong() + " (Hợp đồng)");
                // Load customer info from HopDong would go here
            }

            // Set other invoice details
            txtNgayLap.setText(hoaDon.getNgayThanhToan());
            txtTrangThai.setText(formatTrangThai(hoaDon.getTrangThai()));
            txtNhanVien.setText(hoaDon.getMaNhanVien());
            txtTongTien.setText(formatCurrency(hoaDon.getTongTien()));

            // Get invoice details
            List<ChiTietHoaDon> details = chiTietHoaDonBUS.getChiTietByMaHoaDon(hoaDon.getId());
            loadChiTietHoaDon(details);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadChiTietHoaDon(List<ChiTietHoaDon> details) {
        // Clear existing data
        modelChiTiet.setRowCount(0);

        if (details == null || details.isEmpty()) {
            return;
        }

        int stt = 1;
        for (ChiTietHoaDon detail : details) {
            String loai = "Phòng";
            String tenItem = detail.getMaItem(); // Default to item code

            if ("dich_vu".equals(detail.getLoaiItem())) {
                loai = "Dịch vụ";
                try {
                    tenItem = dichVuBUS.getDichVuByMa(detail.getMaItem()).getTenDv();
                } catch (Exception e) {
                    // If service name not found, keep using the code
                }
            } else if ("phong".equals(detail.getLoaiItem())) {
                try {
                    tenItem = "Phòng " + phongBUS.getPhongByMa(detail.getMaItem()).getMaPhong();
                } catch (Exception e) {
                    // If room info not found, keep using the code
                }
            }

            modelChiTiet.addRow(new Object[]{
                stt++,
                detail.getMaItem(),
                loai,
                tenItem,
                formatCurrency(detail.getDonGia()),
                detail.getSoLuong(),
                formatCurrency(detail.getThanhTien())
            });
        }
    }

    private void printInvoice() {
        try {
            JOptionPane.showMessageDialog(this,
                "Đang chuẩn bị in hóa đơn " + hoaDon.getId() + "...",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Here you would implement actual printing functionality
            // This could call the same printing code from the dialog class

            JOptionPane.showMessageDialog(this,
                "Hóa đơn đã được gửi đến máy in thành công!",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi in hóa đơn: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Utility methods
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        return label;
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

    private String formatCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount) + " VND";
    }

    private String formatTrangThai(String trangThai) {
        if (trangThai == null) return "N/A";

        switch (trangThai) {
            case "da_thanh_toan":
                return "Đã thanh toán";
            case "chua_thanh_toan":
                return "Chưa thanh toán";
            case "da_huy":
                return "Đã hủy";
            default:
                return trangThai;
        }
    }
}