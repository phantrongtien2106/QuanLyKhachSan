package Panel;

import bus.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import model.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JComponent;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.io.FileOutputStream;
import javax.swing.filechooser.FileNameExtensionFilter;

public class QuanLyHoaDonPanel extends JPanel {
    // UI Constants
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

    // UI Components
    private JTextField txtTuKhoa;
    private JTable tblHoaDon;
    private DefaultTableModel tableModel;
    private JLabel lblTongSo, lblTongTien;
    private JComboBox<String> cboTrangThai;
    private JSpinner spTuNgay, spDenNgay;
    private JButton btnLoc, btnTim, btnXem, btnLamMoi, btnXuatPDF;

    // Business Logic
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();

    public QuanLyHoaDonPanel() {
        setLayout(new BorderLayout()); // Bắt buộc với JPanel
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER); // <- thay cho setContentPane
        initializeDatePickers();
        loadData();
    }
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        // Create title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("QUẢN LÝ HÓA ĐƠN", JLabel.CENTER);
        lblTitle.setFont(TITLE_FONT);
        lblTitle.setForeground(TEXT_COLOR);
        titlePanel.add(lblTitle, BorderLayout.CENTER);

        headerPanel.add(titlePanel, BorderLayout.NORTH);
        headerPanel.add(createFilterPanel(), BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBackground(PANEL_COLOR);
        filterPanel.setBorder(createRoundedBorder("Tìm kiếm và lọc"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // First row - date filters
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setFont(LABEL_FONT);
        filterPanel.add(lblTuNgay, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.2;
        spTuNgay = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorTuNgay = new JSpinner.DateEditor(spTuNgay, "dd/MM/yyyy");
        spTuNgay.setEditor(editorTuNgay);
        spTuNgay.setFont(CONTENT_FONT);
        filterPanel.add(spTuNgay, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.1;
        JLabel lblDenNgay = new JLabel("Đến ngày:");
        lblDenNgay.setFont(LABEL_FONT);
        filterPanel.add(lblDenNgay, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.2;
        spDenNgay = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorDenNgay = new JSpinner.DateEditor(spDenNgay, "dd/MM/yyyy");
        spDenNgay.setEditor(editorDenNgay);
        spDenNgay.setFont(CONTENT_FONT);
        filterPanel.add(spDenNgay, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0.1;
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(LABEL_FONT);
        filterPanel.add(lblTrangThai, gbc);

        gbc.gridx = 5;
        gbc.weightx = 0.2;
        cboTrangThai = new JComboBox<>(new String[]{"Tất cả", "Đã thanh toán", "Chưa thanh toán"});
        cboTrangThai.setFont(CONTENT_FONT);
        filterPanel.add(cboTrangThai, gbc);

        gbc.gridx = 6;
        gbc.weightx = 0.1;
        btnLoc = createStyledButton("Lọc", "/images/filter_icon.png");
        btnLoc.addActionListener(this::onLocClicked);
        filterPanel.add(btnLoc, gbc);

        // Second row - search
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        JLabel lblTuKhoa = new JLabel("Từ khóa:");
        lblTuKhoa.setFont(LABEL_FONT);
        filterPanel.add(lblTuKhoa, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 0.7;
        txtTuKhoa = new JTextField();
        txtTuKhoa.setFont(CONTENT_FONT);
        filterPanel.add(txtTuKhoa, gbc);

        gbc.gridx = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
        btnTim = createStyledButton("Tìm kiếm", "/images/search_icon.png");
        btnTim.addActionListener(e -> onTimKiemClicked());
        filterPanel.add(btnTim, gbc);

        gbc.gridx = 6;
        gbc.weightx = 0.1;
        btnLamMoi = createStyledButton("Làm mới", "/images/refresh_icon.png");
        btnLamMoi.addActionListener(e -> loadData());
        filterPanel.add(btnLamMoi, gbc);

        return filterPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setBorder(createRoundedBorder("Danh sách hóa đơn"));

        // Create table with custom model
        String[] cols = {"STT", "Mã HĐ", "Ngày lập", "Tổng tiền", "Mã phiếu", "Mã hợp đồng", "NV lập", "Trạng thái", "PT thanh toán"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        tblHoaDon = new JTable(tableModel);
        setupTable(tblHoaDon);

        // Add to scroll pane
        JScrollPane scrollPane = new JScrollPane(tblHoaDon);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(PANEL_COLOR);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        return centerPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout(10, 0));
        footerPanel.setBackground(BACKGROUND_COLOR);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        btnXem = createStyledButton("Xem chi tiết", "/images/view_icon.png");
        btnXem.addActionListener(e -> onXemChiTietClicked());
        buttonPanel.add(btnXem);

        btnXuatPDF = createStyledButton("Xuất PDF", "/images/pdf_icon.png");
        btnXuatPDF.addActionListener(e -> onXuatPDFClicked());
        buttonPanel.add(btnXuatPDF);

        // Summary panel
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        summaryPanel.setBackground(PANEL_COLOR);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        lblTongSo = new JLabel("Tổng số hóa đơn: 0");
        lblTongSo.setFont(LABEL_FONT);
        lblTongSo.setForeground(TITLE_COLOR);

        lblTongTien = new JLabel("Tổng doanh thu: 0 VND");
        lblTongTien.setFont(LABEL_FONT);
        lblTongTien.setForeground(TITLE_COLOR);

        summaryPanel.add(lblTongSo);
        summaryPanel.add(lblTongTien);

        footerPanel.add(buttonPanel, BorderLayout.WEST);
        footerPanel.add(summaryPanel, BorderLayout.EAST);

        return footerPanel;
    }

    private void setupTable(JTable table) {
        // Set appearance
        table.setRowHeight(35);
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

        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);  // STT
        columnModel.getColumn(1).setPreferredWidth(80);  // Mã HĐ
        columnModel.getColumn(2).setPreferredWidth(120); // Ngày lập
        columnModel.getColumn(3).setPreferredWidth(100); // Tổng tiền

        // Rest of the code remains the same...
    }

    private void initializeDatePickers() {
        // Set default date range (last 30 days to today)
        Date today = new Date();

        // Configure date editors with proper formatting
        JSpinner.DateEditor dateEditorTo = new JSpinner.DateEditor(spDenNgay, "dd/MM/yyyy");
        JSpinner.DateEditor dateEditorFrom = new JSpinner.DateEditor(spTuNgay, "dd/MM/yyyy");

        spDenNgay.setEditor(dateEditorTo);
        spTuNgay.setEditor(dateEditorFrom);

        // Set current date as the end date
        spDenNgay.setValue(today);

        // Set "from date" to 30 days ago
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date fromDate = calendar.getTime();
        spTuNgay.setValue(fromDate);

        // Add focus listeners for better UX
        JComponent editorFrom = spTuNgay.getEditor();
        JComponent editorTo = spDenNgay.getEditor();

        editorFrom.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                    JTextField textField = dateEditorFrom.getTextField();
                    textField.selectAll();
                });
            }
        });

        editorTo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                    JTextField textField = dateEditorTo.getTextField();
                    textField.selectAll();
                });
            }
        });
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
        });

        return button;
    }

    private Border createRoundedBorder(String title) {
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

    private void updateTable(List<HoaDon> list) {
        tableModel.setRowCount(0);
        int stt = 1;
        double tongTien = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat moneyFormat = new DecimalFormat("#,##0 VND");

        for (HoaDon hd : list) {
            String ngayLap = "";
            try {
                if (hd.getNgayThanhToan() != null && !hd.getNgayThanhToan().isEmpty()) {
                    ngayLap = hd.getNgayThanhToan(); // Assume this is already formatted
                }
            } catch (Exception e) {
                // Handle date parsing error
                ngayLap = "N/A";
            }

            tableModel.addRow(new Object[]{
                    stt++,
                    hd.getId(),
                    ngayLap,
                    hd.getTongTien(),
                    hd.getMaPhieu() != null ? hd.getMaPhieu() : "N/A",
                    hd.getMaHopDong() != null ? hd.getMaHopDong() : "N/A",
                    hd.getMaNhanVien() != null ? hd.getMaNhanVien() : "N/A",
                    hd.getTrangThai(),
                    formatPaymentMethod(hd.getPhuongThucThanhToan())
            });

            tongTien += hd.getTongTien();
        }

        // Update summary labels
        lblTongSo.setText("Tổng số hóa đơn: " + list.size());
        lblTongTien.setText("Tổng doanh thu: " + moneyFormat.format(tongTien));
    }

    // Load all invoice data with better error handling
    private void loadData() {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            List<HoaDon> list = hoaDonBUS.getAllHoaDon();
            updateTable(list);

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không có dữ liệu hóa đơn để hiển thị",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    // Update table with better formatting

    // Enhanced search function
    private void onTimKiemClicked() {
        String keyword = txtTuKhoa.getText().trim();

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập từ khóa tìm kiếm",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            List<HoaDon> result = hoaDonBUS.timKiemHoaDon(keyword);
            updateTable(result);

            if (result.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy hóa đơn nào phù hợp với từ khóa: " + keyword,
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void onXemChiTietClicked() {
        // Check if a row is selected
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một hóa đơn để xem chi tiết",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Get invoice ID from the selected row (column 1 contains the invoice ID)
            String maHD = tableModel.getValueAt(row, 1).toString();

            // Retrieve the full invoice object from the database
            HoaDon hoaDon = hoaDonBUS.getHoaDonById(maHD);

            if (hoaDon == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy thông tin hóa đơn " + maHD,
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            JDialog dialog = new JDialog(parent, "Chi tiết hóa đơn", true);
            dialog.setContentPane(new ChiTietHoaDonPanel(hoaDon));
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi hiển thị chi tiết hóa đơn: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Enhanced filter function
    private void onLocClicked(ActionEvent e) {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // Get dates from spinners
            Date dateFrom = (Date) spTuNgay.getValue();
            Date dateTo = (Date) spDenNgay.getValue();

            // Validate date range
            if (dateFrom.after(dateTo)) {
                JOptionPane.showMessageDialog(this,
                        "Ngày bắt đầu không thể sau ngày kết thúc",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convert to LocalDate properly
            LocalDate tuNgay = dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate denNgay = dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Format dates to ISO format string (yyyy-MM-dd)
            String tuNgayStr = tuNgay.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String denNgayStr = denNgay.format(DateTimeFormatter.ISO_LOCAL_DATE);

            // First get invoices within date range
            List<HoaDon> result = hoaDonBUS.getHoaDonTheoKhoangThoiGian(tuNgayStr, denNgayStr);

            // Then filter by status if not "Tất cả"
            String trangThai = cboTrangThai.getSelectedItem().toString();
            if (!"Tất cả".equals(trangThai)) {
                // Using a separate method to handle status filtering
                result = filterByStatus(result, trangThai);
            }

            updateTable(result);

            if (result.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy hóa đơn nào phù hợp với bộ lọc",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lọc dữ liệu: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    // Helper method to filter invoices by status
    private List<HoaDon> filterByStatus(List<HoaDon> invoices, String displayStatus) {
        // Convert display status to database status value
        final String statusCode;
        switch (displayStatus) {
            case "Đã thanh toán":
                statusCode = "da_thanh_toan";
                break;
            case "Chưa thanh toán":
                statusCode = "chua_thanh_toan";
                break;
            default:
                return invoices; // Return unfiltered list for unknown status
        }

        // Now we use the final statusCode variable in the lambda
        return invoices.stream()
                .filter(hd -> statusCode.equals(hd.getTrangThai()))
                .collect(java.util.stream.Collectors.toList());
    }
    private void onXuatPDFClicked() {
        // Check if an invoice is selected
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một hóa đơn để xuất PDF",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maHD = tableModel.getValueAt(row, 1).toString();

        try {
            // Get the full invoice object with details
            HoaDon hoaDon = hoaDonBUS.getHoaDonById(maHD);
            if (hoaDon == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy thông tin hóa đơn " + maHD,
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get line items
            List<ChiTietHoaDon> chiTietList = new ChiTietHoaDonBUS().getChiTietByMaHoaDon(maHD);

            // Show file chooser dialog
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu file PDF");
            fileChooser.setSelectedFile(new File("HoaDon_" + maHD + ".pdf"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                // Ensure file has .pdf extension
                String filePath = file.getPath();
                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                    file = new File(filePath);
                }

                // Show progress cursor
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                // Generate PDF file
                Document document = new Document(PageSize.A4);
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                    document.open();

                    // Add header with company info
                    com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD);
                    com.itextpdf.text.Font subTitleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 14, com.itextpdf.text.Font.BOLD);
                    com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 11, com.itextpdf.text.Font.NORMAL);
                    com.itextpdf.text.Font smallFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 9, com.itextpdf.text.Font.NORMAL);

                    // Title
                    Paragraph title = new Paragraph("HÓA ĐƠN THANH TOÁN", titleFont);
                    title.setAlignment(Element.ALIGN_CENTER);
                    title.setSpacingAfter(20);
                    document.add(title);

                    // Invoice info
                    PdfPTable infoTable = new PdfPTable(2);
                    infoTable.setWidthPercentage(100);
                    infoTable.setSpacingAfter(20);

                    infoTable.addCell(createInfoCell("Mã hóa đơn:", subTitleFont));
                    infoTable.addCell(createInfoCell(hoaDon.getId(), normalFont));

                    infoTable.addCell(createInfoCell("Ngày lập:", subTitleFont));
                    infoTable.addCell(createInfoCell(hoaDon.getNgayThanhToan(), normalFont));

                    infoTable.addCell(createInfoCell("Nhân viên:", subTitleFont));
                    infoTable.addCell(createInfoCell(hoaDon.getMaNhanVien(), normalFont));

                    infoTable.addCell(createInfoCell("Phiếu đặt phòng:", subTitleFont));
                    infoTable.addCell(createInfoCell(hoaDon.getMaPhieu(), normalFont)); // Changed from getMaPhieuDatPhong to getMaPhieu

                    infoTable.addCell(createInfoCell("Trạng thái:", subTitleFont));
                    infoTable.addCell(createInfoCell(formatTrangThaiHoaDon(hoaDon.getTrangThai()), normalFont));

                    infoTable.addCell(createInfoCell("Phương thức thanh toán:", subTitleFont));
                    infoTable.addCell(createInfoCell(formatPaymentMethod(hoaDon.getPhuongThucThanhToan()), normalFont));

                    document.add(infoTable);

                    // Line items table
                    PdfPTable itemsTable = new PdfPTable(new float[]{0.5f, 2f, 1.5f, 1f, 1.5f});
                    itemsTable.setWidthPercentage(100);
                    itemsTable.setSpacingBefore(10);
                    itemsTable.setSpacingAfter(10);

                    // Table header
                    itemsTable.addCell(createHeaderCell("STT"));
                    itemsTable.addCell(createHeaderCell("Mã dịch vụ"));
                    itemsTable.addCell(createHeaderCell("Loại"));
                    itemsTable.addCell(createHeaderCell("Số lượng"));
                    itemsTable.addCell(createHeaderCell("Thành tiền"));

                    // Table data
                    int stt = 1;
                    DecimalFormat df = new DecimalFormat("#,##0 VND");
                    for (ChiTietHoaDon ct : chiTietList) {
                        itemsTable.addCell(createCell(String.valueOf(stt++), Element.ALIGN_CENTER));
                        itemsTable.addCell(createCell(ct.getMaItem(), Element.ALIGN_LEFT));
                        itemsTable.addCell(createCell(ct.getLoaiItem(), Element.ALIGN_LEFT));
                        itemsTable.addCell(createCell(String.valueOf(ct.getSoLuong()), Element.ALIGN_CENTER));
                        itemsTable.addCell(createCell(df.format(ct.getThanhTien()), Element.ALIGN_RIGHT));
                    }

                    document.add(itemsTable);

                    // Total
                    Paragraph totalLine = new Paragraph();
                    totalLine.add(new Chunk(new LineSeparator()));
                    totalLine.setSpacingBefore(10);
                    totalLine.setSpacingAfter(10);
                    document.add(totalLine);

                    Paragraph total = new Paragraph("Tổng tiền: " + df.format(hoaDon.getTongTien()), subTitleFont);
                    total.setAlignment(Element.ALIGN_RIGHT);
                    document.add(total);

                    // Footer
                    Paragraph footer = new Paragraph("Cảm ơn quý khách đã sử dụng dịch vụ!", smallFont);
                    footer.setSpacingBefore(30);
                    footer.setAlignment(Element.ALIGN_CENTER);
                    document.add(footer);
                } finally {
                    if (document.isOpen()) {
                        document.close();
                    }

                    JOptionPane.showMessageDialog(this,
                            "Xuất PDF thành công: " + file.getPath(),
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Open the PDF file
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(file);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi xuất PDF: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    // Helper methods for PDF creation
    private PdfPCell createHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 11, com.itextpdf.text.Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(PRIMARY_COLOR.getRGB()));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        cell.setBorderWidth(0.5f);
        return cell;
    }

    private PdfPCell createCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 10, com.itextpdf.text.Font.NORMAL)));
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        cell.setBorderWidth(0.5f);
        return cell;
    }

    private PdfPCell createInfoCell(String text, com.itextpdf.text.Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        cell.setPadding(3);
        return cell;
    }

    // Format trạng thái for invoice display
    private String formatTrangThaiHoaDon(String trangThai) {
        if (trangThai == null) return "N/A";

        switch (trangThai) {
            case "da_thanh_toan": return "Đã thanh toán";
            case "chua_thanh_toan": return "Chưa thanh toán";
            case "da_huy": return "Đã hủy";
            default: return trangThai;
        }
    }
    // Format payment method for better display
    private String formatPaymentMethod(String method) {
        if (method == null) return "N/A";

        switch (method) {
            case "tien_mat":
                return "Tiền mặt";
            case "chuyen_khoan":
                return "Chuyển khoản";
            case "the_tin_dung":
                return "Thẻ tín dụng";
            default:
                return method;
        }
    }

    // Calculate statistics by status
    private void calculateStatistics() {
        int totalInvoices = 0;
        int paidInvoices = 0;
        int unpaidInvoices = 0;
        int canceledInvoices = 0;
        double totalRevenue = 0;
        double paidRevenue = 0;

        List<HoaDon> allInvoices = hoaDonBUS.getAllHoaDon();
        for (HoaDon hd : allInvoices) {
            totalInvoices++;
            totalRevenue += hd.getTongTien();

            if ("da_thanh_toan".equals(hd.getTrangThai())) {
                paidInvoices++;
                paidRevenue += hd.getTongTien();
            } else if ("chua_thanh_toan".equals(hd.getTrangThai())) {
                unpaidInvoices++;
            } else if ("da_huy".equals(hd.getTrangThai())) {
                canceledInvoices++;
            }
        }

        // These statistics could be displayed in a separate dialog or panel
        System.out.println("Tổng số hóa đơn: " + totalInvoices);
        System.out.println("- Đã thanh toán: " + paidInvoices);
        System.out.println("- Chưa thanh toán: " + unpaidInvoices);
        System.out.println("- Đã hủy: " + canceledInvoices);
        System.out.println("Tổng doanh thu: " + formatCurrency(totalRevenue));
        System.out.println("Doanh thu đã thanh toán: " + formatCurrency(paidRevenue));
    }

    // Helper method to format currency values
    private String formatCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount) + " VND";
    }

    // Generate a report of invoices for the current view
    private void generateReport() {
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                // Get all visible invoices from the table
                List<HoaDon> invoicesToReport = new ArrayList<>();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String maHD = tableModel.getValueAt(i, 1).toString();
                    HoaDon hd = hoaDonBUS.getHoaDonById(maHD);
                    if (hd != null) {
                        invoicesToReport.add(hd);
                    }
                }

                // Create a report file - this would use a reporting library
                // For now, we'll just export to CSV as an example

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Lưu báo cáo");
                fileChooser.setSelectedFile(new File("BaoCaoHoaDon.csv"));

                if (fileChooser.showSaveDialog(QuanLyHoaDonPanel.this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    // Ensure file has .csv extension
                    String filePath = file.getPath();
                    if (!filePath.toLowerCase().endsWith(".csv")) {
                        filePath += ".csv";
                        file = new File(filePath);
                    }

                    // Write CSV file
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        // CSV header
                        String header = "Mã HĐ,Ngày lập,Tổng tiền,Mã phiếu,Mã hợp đồng,NV lập,Trạng thái,PT thanh toán\n";
                        fos.write(header.getBytes());

                        // CSV rows
                        for (HoaDon hd : invoicesToReport) {
                            String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n",
                                    hd.getId(),
                                    hd.getNgayThanhToan(),
                                    hd.getTongTien(),
                                    hd.getMaPhieu() != null ? hd.getMaPhieu() : "",
                                    hd.getMaHopDong() != null ? hd.getMaHopDong() : "",
                                    hd.getMaNhanVien() != null ? hd.getMaNhanVien() : "",
                                    hd.getTrangThai(),
                                    hd.getPhuongThucThanhToan() != null ? hd.getPhuongThucThanhToan() : ""
                            );
                            fos.write(line.getBytes());
                        }
                    }

                    return true;
                }

                return false;
            }

            @Override
            protected void done() {
                try {
                    boolean success = get();
                    if (success) {
                        JOptionPane.showMessageDialog(QuanLyHoaDonPanel.this,
                                "Xuất báo cáo thành công!",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(QuanLyHoaDonPanel.this,
                            "Lỗi khi xuất báo cáo: " + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        try {
            // Set system look and feel for better UI appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý hóa đơn");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new QuanLyHoaDonPanel());
            frame.pack();
            frame.setLocationRelativeTo(null); // Canh giữa màn hình
            frame.setVisible(true);
        });

    }
}