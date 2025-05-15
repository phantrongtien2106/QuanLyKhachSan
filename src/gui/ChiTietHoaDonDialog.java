package gui;

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
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.util.List;

public class ChiTietHoaDonDialog extends JDialog {
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
    private JButton btnIn, btnDong;

    // Business logic
    private HoaDon hoaDon;
    private ChiTietHoaDonBUS chiTietHoaDonBUS;
    private KhachHangBUS khachHangBUS;
    private System.bus.NhanVienBUS nhanVienBUS;
    private PhongBUS phongBUS;
    private DichVuBUS dichVuBUS;

    // Constructor that accepts a HoaDon object directly
    public ChiTietHoaDonDialog(Frame parent, HoaDon hoaDon) {
        super(parent, "Chi Tiết Hóa Đơn", true);
        this.hoaDon = hoaDon;

        // Initialize BUS objects
        this.chiTietHoaDonBUS = new ChiTietHoaDonBUS();
        this.khachHangBUS = new KhachHangBUS();
        this.nhanVienBUS = new NhanVienBUS();
        this.phongBUS = new PhongBUS();
        this.dichVuBUS = new DichVuBUS();

        initializeUI();
        loadHoaDonData();

        pack();
        setLocationRelativeTo(parent);
    }

    // Constructor that accepts just the invoice ID
    public ChiTietHoaDonDialog(Frame parent, String maHoaDon) {
        super(parent, "Chi Tiết Hóa Đơn", true);

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
            dispose();
            return;
        }

        initializeUI();
        loadHoaDonData();

        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeUI() {
        setSize(900, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Add components to main panel
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createInfoPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
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

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        btnIn = new JButton("In hóa đơn");
        btnIn.setPreferredSize(new Dimension(150, 40));
        btnIn.setBackground(PRIMARY_COLOR);
        btnIn.setForeground(TEXT_COLOR);
        btnIn.setFont(LABEL_FONT);
        btnIn.setFocusPainted(false);
        btnIn.setBorderPainted(false);
        btnIn.setOpaque(true);
        btnIn.setContentAreaFilled(true);
        btnIn.addActionListener(e -> printInvoice());

        btnDong = new JButton("Đóng");
        btnDong.setPreferredSize(new Dimension(120, 40));
        btnDong.setBackground(PRIMARY_COLOR);
        btnDong.setForeground(TEXT_COLOR);
        btnDong.setFont(LABEL_FONT);
        btnDong.setFocusPainted(false);
        btnDong.setBorderPainted(false);
        btnDong.setOpaque(true);
        btnDong.setContentAreaFilled(true);
        btnDong.addActionListener(e -> dispose());

        // Add hover effects with fixed implementation
        addButtonHoverEffect(btnIn);
        addButtonHoverEffect(btnDong);

        buttonPanel.add(btnIn);
        buttonPanel.add(btnDong);

        return buttonPanel;
    }

    private void addButtonHoverEffect(JButton button) {
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
                dispose();
                return;
            }

            // Display invoice information
            txtMaHoaDon.setText(hoaDon.getId());

            // Determine if this is for a booking or contract
            if (hoaDon.getMaPhieu() != null && !hoaDon.getMaPhieu().isEmpty()) {
                txtMaPhieuHD.setText(hoaDon.getMaPhieu() + " (Phiếu đặt phòng)");
                // Load customer info from PhieuDatPhong
                // Code to get customer info from PhieuDatPhong would go here
            } else if (hoaDon.getMaHopDong() != null && !hoaDon.getMaHopDong().isEmpty()) {
                txtMaPhieuHD.setText(hoaDon.getMaHopDong() + " (Hợp đồng)");
                // Load customer info from HopDong
                // Code to get customer info from HopDong would go here
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
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(new InvoicePrintable());

            if (job.printDialog()) {
                job.print();
                JOptionPane.showMessageDialog(this,
                    "Hóa đơn đã được gửi đến máy in thành công!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi in hóa đơn: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Helper class for printing
    private class InvoicePrintable implements Printable {
        @Override
        public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());

            // Scale to fit the page
            double pageWidth = pf.getImageableWidth();
            double pageHeight = pf.getImageableHeight();

            // Draw report header
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("HÓA ĐƠN THANH TOÁN", (int)(pageWidth/2) - 100, 30);

            // Draw invoice info
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString("Mã hóa đơn: " + txtMaHoaDon.getText(), 50, 60);
            g2d.drawString("Khách hàng: " + txtTenKhachHang.getText(), 50, 80);
            g2d.drawString("Ngày lập: " + txtNgayLap.getText(), 50, 100);
            g2d.drawString("Nhân viên: " + txtNhanVien.getText(), 50, 120);

            // Draw a line separator
            g2d.drawLine(50, 140, (int)pageWidth - 50, 140);

            // Draw table header
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("STT", 50, 160);
            g2d.drawString("Mã item", 100, 160);
            g2d.drawString("Loại", 200, 160);
            g2d.drawString("Tên", 250, 160);
            g2d.drawString("Đơn giá", 350, 160);
            g2d.drawString("SL", 430, 160);
            g2d.drawString("Thành tiền", 470, 160);

            // Draw another line
            g2d.drawLine(50, 170, (int)pageWidth - 50, 170);

            // Draw table data
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            int y = 190;
            for (int i = 0; i < tableChiTiet.getRowCount(); i++) {
                g2d.drawString(tableChiTiet.getValueAt(i, 0).toString(), 50, y);
                g2d.drawString(tableChiTiet.getValueAt(i, 1).toString(), 100, y);
                g2d.drawString(tableChiTiet.getValueAt(i, 2).toString(), 200, y);
                g2d.drawString(tableChiTiet.getValueAt(i, 3).toString(), 250, y);
                g2d.drawString(tableChiTiet.getValueAt(i, 4).toString(), 350, y);
                g2d.drawString(tableChiTiet.getValueAt(i, 5).toString(), 430, y);
                g2d.drawString(tableChiTiet.getValueAt(i, 6).toString(), 470, y);
                y += 20;
            }

            // Draw a line separator
            g2d.drawLine(50, y, (int)pageWidth - 50, y);
            y += 20;

            // Draw total
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("Tổng tiền: " + txtTongTien.getText(), (int)pageWidth - 250, y);

            // Draw footer
            y += 40;
            g2d.setFont(new Font("Arial", Font.ITALIC, 12));
            g2d.drawString("Cảm ơn quý khách đã sử dụng dịch vụ!", (int)(pageWidth/2) - 120, y);

            return Printable.PAGE_EXISTS;
        }
    }

    // Utility methods
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
//package gui;
//
//import bus.ChiTietHoaDonBUS;
//import model.ChiTietHoaDon;
//import model.HoaDon;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.util.List;
//
//public class ChiTietHoaDonDialog extends JDialog {
//    private JTable tblChiTiet;
//    private DefaultTableModel tableModel;
//    private JLabel lblTongTien;
//
//    private ChiTietHoaDonBUS cthdBUS = new ChiTietHoaDonBUS();
//
//    public ChiTietHoaDonDialog(JFrame parent, HoaDon hoaDon) {
//        super(parent, "Chi tiết hóa đơn: " + hoaDon.getId(), true);
//        setSize(700, 400);
//        setLocationRelativeTo(parent);
//
//        JPanel content = new JPanel(new BorderLayout(10, 10));
//        content.setBorder(new EmptyBorder(10, 10, 10, 10));
//
//        String[] cols = {"Loại", "Mã", "Đơn giá", "Số lượng", "Thành tiền"};
//        tableModel = new DefaultTableModel(cols, 0);
//        tblChiTiet = new JTable(tableModel);
//        content.add(new JScrollPane(tblChiTiet), BorderLayout.CENTER);
//
//        lblTongTien = new JLabel("Tổng tiền: 0 VND", JLabel.RIGHT);
//        content.add(lblTongTien, BorderLayout.SOUTH);
//
//        setContentPane(content);
//        loadChiTiet(hoaDon);
//    }
//
//    private void loadChiTiet(HoaDon hoaDon) {
//        List<ChiTietHoaDon> list = cthdBUS.getChiTietByMaHoaDon(hoaDon.getId());
//        double tong = 0;
//
//        for (ChiTietHoaDon ct : list) {
//            tableModel.addRow(new Object[]{
//                    ct.getLoaiItem(), ct.getMaItem(), ct.getDonGia(), ct.getSoLuong(), ct.getThanhTien()
//            });
//            tong += ct.getThanhTien();
//        }
//
//        lblTongTien.setText(String.format("Tổng tiền: %,.0f VND", tong));
//    }
//}
