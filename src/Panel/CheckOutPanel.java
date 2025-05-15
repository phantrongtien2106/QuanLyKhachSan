package Panel;

    import bus.*;
    import gui.HoaDonForm;
    import model.*;

    import javax.swing.*;
    import javax.swing.border.EmptyBorder;
    import javax.swing.table.DefaultTableCellRenderer;
    import javax.swing.table.DefaultTableModel;
    import javax.swing.table.JTableHeader;
    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.border.*;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.time.temporal.ChronoUnit;
    import java.util.ArrayList;
    import java.util.List;

    public class CheckOutPanel extends JPanel {
        // Hằng số cho thiết kế
        private static final Color PRIMARY_COLOR = new Color(0x4682B4);
        private static final Color BACKGROUND_COLOR = new Color(0xF5F5F5);
        private static final Color TEXT_COLOR = Color.WHITE;
        private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
        private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
        private static final Font CONTENT_FONT = new Font("Arial", Font.PLAIN, 14);
        private static final int PADDING = 10;

        private JTabbedPane tabbedPane;

        // Phiếu đặt phòng
        private JTable tablePhieu;
        private DefaultTableModel modelPhieu;
        private JTextField txtSearchPhieu;

        // Hợp đồng
        private JTable tableHopDong;
        private DefaultTableModel modelHopDong;
        private JTextField txtSearchHopDong;

        private JTextArea txtGhiChu;
        private JLabel lblNgayThue, lblNgayTra, lblTinhTrang;

        private PhieuDatPhongBUS pdpBUS = new PhieuDatPhongBUS();
        private HopDongBUS hopDongBUS = new HopDongBUS();
        private CheckOutBUS checkOutBUS = new CheckOutBUS();
        private PhongBUS phongBUS = new PhongBUS();
        private ChiTietPhieuDatPhongBUS ctpdpBUS = new ChiTietPhieuDatPhongBUS();
        private ChiTietHopDongBUS cthdBUS = new ChiTietHopDongBUS();

        // Dịch vụ
        private JTable tblDichVu;
        private JLabel lblTongTienDichVu;

        public CheckOutPanel() {
            // Setup panel properties
            setBackground(BACKGROUND_COLOR);
            setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
            setLayout(new BorderLayout(PADDING, PADDING));

            // Panel chính chứa tất cả các thành phần
            JPanel contentPanel = new JPanel(new BorderLayout(PADDING, PADDING));
            contentPanel.setBackground(BACKGROUND_COLOR);
            contentPanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

            // Panel tiêu đề
            JPanel titlePanel = createTitlePanel();
            contentPanel.add(titlePanel, BorderLayout.NORTH);

            // Panel trung tâm chứa tabs và thông tin chi tiết
            JPanel centerPanel = new JPanel(new BorderLayout(PADDING, PADDING));
            centerPanel.setBackground(BACKGROUND_COLOR);

            // TabbedPane cho phiếu và hợp đồng
            tabbedPane = new JTabbedPane();
            tabbedPane.setFont(LABEL_FONT);
            tabbedPane.setBackground(BACKGROUND_COLOR);
            tabbedPane.setPreferredSize(new Dimension(850, 300));

            // Tạo các tab
            tabbedPane.addTab("Phiếu đặt phòng", createPhieuPanel());
            tabbedPane.addTab("Hợp đồng", createHopDongPanel());
            tabbedPane.addChangeListener(e -> hienThiThongTinPhieu());

            centerPanel.add(tabbedPane, BorderLayout.NORTH);

            // Tạo panel thông tin chi tiết
            JPanel infoPanel = createInfoPanel();
            infoPanel.setPreferredSize(new Dimension(850, 300));
            centerPanel.add(infoPanel, BorderLayout.CENTER);

            contentPanel.add(centerPanel, BorderLayout.CENTER);

            // Tạo và thêm panel nút
            JPanel buttonPanel = createButtonPanel();
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);

            // Thêm contentPanel vào panel chính
            add(contentPanel);

            // Load dữ liệu
            loadDanhSachPhieuDangSuDung();
            loadDanhSachHopDongDangSuDung();
        }

        private JPanel createTitlePanel() {
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setBackground(BACKGROUND_COLOR);
            titlePanel.setBorder(new EmptyBorder(0, 0, PADDING, 0));

            JLabel titleLabel = new JLabel("CHECK OUT", JLabel.CENTER);
            titleLabel.setFont(TITLE_FONT);
            titleLabel.setForeground(PRIMARY_COLOR.darker());
            titlePanel.add(titleLabel, BorderLayout.CENTER);

            return titlePanel;
        }

        private JPanel createPhieuPanel() {
            JPanel panel = new JPanel(new BorderLayout(PADDING, PADDING));
            panel.setBackground(BACKGROUND_COLOR);

            // Panel tìm kiếm
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            searchPanel.setBackground(BACKGROUND_COLOR);

            JLabel lblSearch = new JLabel("Tìm kiếm theo mã phiếu/mã khách:");
            lblSearch.setFont(new Font("Arial", Font.BOLD, 12));

            txtSearchPhieu = new JTextField(20);
            txtSearchPhieu.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    timKiemPhieu(txtSearchPhieu.getText().trim());
                }
            });

            JButton btnRefresh = createStyledButton("Làm mới");
            btnRefresh.setPreferredSize(new Dimension(100, 30));
            btnRefresh.addActionListener(e -> {
                txtSearchPhieu.setText("");
                loadDanhSachPhieuDangSuDung();
            });

            searchPanel.add(lblSearch);
            searchPanel.add(txtSearchPhieu);
            searchPanel.add(btnRefresh);
            panel.add(searchPanel, BorderLayout.NORTH);

            // Bảng phiếu đặt phòng
            modelPhieu = new DefaultTableModel(
                    new String[]{"Mã phiếu", "Mã khách hàng", "Ngày nhận", "Ngày trả", "Trạng thái"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            tablePhieu = new JTable(modelPhieu);
            tablePhieu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            setupTable(tablePhieu);

            // Add event listener for table selection
            tablePhieu.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (tablePhieu.getSelectedRow() >= 0) {
                        hienThiThongTinPhieu();
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(tablePhieu);
            panel.add(scrollPane, BorderLayout.CENTER);

            return panel;
        }

        private JPanel createHopDongPanel() {
            JPanel panel = new JPanel(new BorderLayout(PADDING, PADDING));
            panel.setBackground(BACKGROUND_COLOR);

            // Panel tìm kiếm
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            searchPanel.setBackground(BACKGROUND_COLOR);

            JLabel lblSearch = new JLabel("Tìm kiếm theo mã hợp đồng/mã khách:");
            lblSearch.setFont(new Font("Arial", Font.BOLD, 12));

            txtSearchHopDong = new JTextField(20);
            txtSearchHopDong.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    timKiemHopDong(txtSearchHopDong.getText().trim());
                }
            });

            JButton btnRefresh = createStyledButton("Làm mới");
            btnRefresh.setPreferredSize(new Dimension(100, 30));
            btnRefresh.addActionListener(e -> {
                txtSearchHopDong.setText("");
                loadDanhSachHopDongDangSuDung();
            });

            searchPanel.add(lblSearch);
            searchPanel.add(txtSearchHopDong);
            searchPanel.add(btnRefresh);
            panel.add(searchPanel, BorderLayout.NORTH);

            // Bảng hợp đồng
            modelHopDong = new DefaultTableModel(
                    new String[]{"Mã hợp đồng", "Mã khách hàng", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            tableHopDong = new JTable(modelHopDong);
            tableHopDong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            setupTable(tableHopDong);

            tableHopDong.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (tableHopDong.getSelectedRow() >= 0) {
                        hienThiThongTinHopDong();
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(tableHopDong);
            panel.add(scrollPane, BorderLayout.CENTER);

            return panel;
        }

        private JPanel createInfoPanel() {
            JPanel panel = new JPanel(new BorderLayout(PADDING, PADDING));
            panel.setBackground(BACKGROUND_COLOR);
            panel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                    "Thông tin chi tiết",
                    0,
                    0,
                    LABEL_FONT,
                    PRIMARY_COLOR.darker()
            ));

            // Panel chứa thông tin cơ bản và ghi chú
            JPanel topPanel = new JPanel(new BorderLayout(PADDING, PADDING));
            topPanel.setBackground(BACKGROUND_COLOR);

            // Thông tin ngày và tình trạng
            JPanel infoDetailsPanel = new JPanel(new GridLayout(3, 2, 10, 5));
            infoDetailsPanel.setBackground(BACKGROUND_COLOR);

            JLabel lblNgayThueTitle = new JLabel("Ngày thuê:");
            lblNgayThueTitle.setFont(LABEL_FONT);
            lblNgayThue = new JLabel("-");

            JLabel lblNgayTraTitle = new JLabel("Ngày trả:");
            lblNgayTraTitle.setFont(LABEL_FONT);
            lblNgayTra = new JLabel("-");

            JLabel lblTinhTrangTitle = new JLabel("Tình trạng:");
            lblTinhTrangTitle.setFont(LABEL_FONT);
            lblTinhTrang = new JLabel("-");

            infoDetailsPanel.add(lblNgayThueTitle);
            infoDetailsPanel.add(lblNgayThue);
            infoDetailsPanel.add(lblNgayTraTitle);
            infoDetailsPanel.add(lblNgayTra);
            infoDetailsPanel.add(lblTinhTrangTitle);
            infoDetailsPanel.add(lblTinhTrang);

            // Ghi chú
            JPanel notePanel = new JPanel(new BorderLayout(5, 5));
            notePanel.setBackground(BACKGROUND_COLOR);
            notePanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                    "Ghi chú",
                    0,
                    0,
                    LABEL_FONT,
                    PRIMARY_COLOR.darker()
            ));

            txtGhiChu = new JTextArea(3, 20);
            txtGhiChu.setLineWrap(true);
            txtGhiChu.setWrapStyleWord(true);
            JScrollPane scrollGhiChu = new JScrollPane(txtGhiChu);
            notePanel.add(scrollGhiChu, BorderLayout.CENTER);

            topPanel.add(infoDetailsPanel, BorderLayout.NORTH);
            topPanel.add(notePanel, BorderLayout.CENTER);

            // Panel dịch vụ
            JPanel servicePanel = new JPanel(new BorderLayout(5, 5));
            servicePanel.setBackground(BACKGROUND_COLOR);
            servicePanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                    "Dịch vụ đã sử dụng",
                    0,
                    0,
                    LABEL_FONT,
                    PRIMARY_COLOR.darker()
            ));

            // Bảng dịch vụ
            DefaultTableModel modelDichVu = new DefaultTableModel(
                    new String[]{"Mã DV", "Tên dịch vụ", "Đơn giá", "Số lượng", "Thành tiền"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            tblDichVu = new JTable(modelDichVu);
            setupTable(tblDichVu);
            JScrollPane scrollDichVu = new JScrollPane(tblDichVu);
            scrollDichVu.setPreferredSize(new Dimension(400, 150));
            servicePanel.add(scrollDichVu, BorderLayout.CENTER);

            // Tổng tiền dịch vụ
            JPanel serviceTotalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            serviceTotalPanel.setBackground(BACKGROUND_COLOR);
            serviceTotalPanel.add(new JLabel("Tổng tiền dịch vụ:"));
            lblTongTienDichVu = new JLabel("0 VND");
            lblTongTienDichVu.setFont(LABEL_FONT);
            serviceTotalPanel.add(lblTongTienDichVu);
            servicePanel.add(serviceTotalPanel, BorderLayout.SOUTH);

            // Tổ chức layout
            JPanel mainInfoPanel = new JPanel(new BorderLayout(PADDING, PADDING));
            mainInfoPanel.setBackground(BACKGROUND_COLOR);
            mainInfoPanel.add(topPanel, BorderLayout.NORTH);
            mainInfoPanel.add(servicePanel, BorderLayout.CENTER);

            panel.add(mainInfoPanel, BorderLayout.CENTER);
            return panel;
        }

        private JPanel createButtonPanel() {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(BACKGROUND_COLOR);

            JButton btnCheckOut = createStyledButton("Check Out");
            JButton btnHuy = createStyledButton("Hủy");
            JButton btnLichSu = createStyledButton("Lịch sử");

            btnCheckOut.addActionListener(e -> thucHienCheckOut());
            btnHuy.addActionListener(e -> huyPhieuHopDong());
            btnLichSu.addActionListener(e -> xemLichSuCheckOut());

            buttonPanel.add(btnLichSu);
            buttonPanel.add(btnHuy);
            buttonPanel.add(btnCheckOut);

            return buttonPanel;
        }

        private JButton createStyledButton(String text) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setBackground(PRIMARY_COLOR);
            button.setForeground(TEXT_COLOR);
            button.setFocusPainted(false);
            button.setOpaque(true);
            button.setBorderPainted(false);

            // Override UI để đảm bảo màu nền luôn được hiển thị
            button.putClientProperty("Nimbus.Overrides", Boolean.TRUE);
            button.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.FALSE);

            button.setPreferredSize(new Dimension(120, 35));
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(PRIMARY_COLOR.darker());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(PRIMARY_COLOR);
                }
            });

            return button;
        }

        private void setupTable(JTable table) {
            table.setRowHeight(25);
            table.setGridColor(Color.LIGHT_GRAY);
            table.setSelectionBackground(PRIMARY_COLOR);
            table.setSelectionForeground(TEXT_COLOR);
            table.setBackground(Color.WHITE);

            JTableHeader header = table.getTableHeader();
            header.setBackground(PRIMARY_COLOR);
            header.setForeground(TEXT_COLOR);
            header.setFont(new Font("Arial", Font.BOLD, 12));

            DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            headerRenderer.setHorizontalAlignment(JLabel.CENTER);
            headerRenderer.setForeground(TEXT_COLOR);
            headerRenderer.setBackground(PRIMARY_COLOR);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        // Method to load contracts that are currently in use
        private void loadDanhSachHopDongDangSuDung() {
            modelHopDong.setRowCount(0);
            List<HopDong> list = hopDongBUS.getHopDongDangSuDung(); // status = 'dang_su_dung'

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không có hợp đồng nào đang sử dụng.",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (HopDong hd : list) {
                    modelHopDong.addRow(new Object[]{
                            hd.getMaHopDong(),
                            hd.getMaKhachHang(),
                            hd.getNgayBatDau(),
                            hd.getNgayKetThuc(),
                            hd.getTrangThai()
                    });
                }
            }

            resetThongTinPhieu();
        }

        // Search for contracts that are in use
        private void timKiemHopDong(String keyword) {
            modelHopDong.setRowCount(0);
            List<HopDong> list = hopDongBUS.timKiemHopDongDangSuDung(keyword);

            if (!list.isEmpty()) {
                for (HopDong hd : list) {
                    modelHopDong.addRow(new Object[]{
                            hd.getMaHopDong(),
                            hd.getMaKhachHang(),
                            hd.getNgayBatDau(),
                            hd.getNgayKetThuc(),
                            hd.getTrangThai()
                    });
                }
            }
        }

        // Display contract information when selected
        private void hienThiThongTinHopDong() {
            int selectedRow = tableHopDong.getSelectedRow();
            if (selectedRow < 0) return;

            String maHopDong = modelHopDong.getValueAt(selectedRow, 0).toString();
            String ngayBatDauStr = modelHopDong.getValueAt(selectedRow, 2).toString();
            String ngayKetThucStr = modelHopDong.getValueAt(selectedRow, 3).toString();

            lblNgayThue.setText(ngayBatDauStr);
            lblNgayTra.setText(ngayKetThucStr);

            // Analyze expected return date vs current date
            try {
                LocalDate ngayKetThuc = LocalDate.parse(ngayKetThucStr.split(" ")[0]);
                LocalDate today = LocalDate.now();

                long daysDiff = ChronoUnit.DAYS.between(ngayKetThuc, today);

                if (daysDiff < 0) {
                    lblTinhTrang.setText("Trả sớm " + Math.abs(daysDiff) + " ngày");
                    lblTinhTrang.setForeground(new Color(0x2E8B57)); // Sea Green
                } else if (daysDiff > 0) {
                    lblTinhTrang.setText("Trả trễ " + daysDiff + " ngày");
                    lblTinhTrang.setForeground(Color.RED);
                } else {
                    lblTinhTrang.setText("Đúng hạn");
                    lblTinhTrang.setForeground(Color.BLUE);
                }
            } catch (Exception e) {
                lblTinhTrang.setText("Lỗi tính ngày");
                lblTinhTrang.setForeground(Color.RED);
                System.out.println("Lỗi phân tích ngày: " + e.getMessage());
            }

            hienThiChiTietDichVu(maHopDong);
        }

        // Load tickets that are currently in use
        private void loadDanhSachPhieuDangSuDung() {
            modelPhieu.setRowCount(0);
            List<PhieuDatPhong> danhSach = pdpBUS.getPhieuDangSuDung();

            System.out.println("Tìm thấy " + (danhSach != null ? danhSach.size() : 0) + " phiếu đang sử dụng");

            if (danhSach != null && !danhSach.isEmpty()) {
                for (PhieuDatPhong pdp : danhSach) {
                    System.out.println("Hiển thị phiếu: " + pdp.getMaPhieu() + " - Trạng thái: " + pdp.getTrangThai());
                    modelPhieu.addRow(new Object[]{
                            pdp.getMaPhieu(),
                            pdp.getMaKhachHang(),
                            pdp.getNgayNhan(),
                            pdp.getNgayTra(),
                            pdp.getTrangThai()
                    });
                }

                // Auto-select first row if available
                if (tablePhieu.getRowCount() > 0) {
                    tablePhieu.setRowSelectionInterval(0, 0);
                    hienThiThongTinPhieu();
                }
            }
        }

        // Search for tickets that are in use
        private void timKiemPhieu(String keyword) {
            modelPhieu.setRowCount(0);
            List<PhieuDatPhong> list = pdpBUS.timKiemPhieuDangSuDung(keyword);

            if (!list.isEmpty()) {
                for (PhieuDatPhong pdp : list) {
                    modelPhieu.addRow(new Object[]{
                            pdp.getMaPhieu(),
                            pdp.getMaKhachHang(),
                            pdp.getNgayNhan(),
                            pdp.getNgayTra(),
                            pdp.getTrangThai()
                    });
                }
            }
        }

        // Display ticket information when selected
        private void hienThiThongTinPhieu() {
            if (tabbedPane.getSelectedIndex() == 0) {
                // Ticket tab
                int selectedRow = tablePhieu.getSelectedRow();
                if (selectedRow < 0) return;

                String maPhieu = modelPhieu.getValueAt(selectedRow, 0).toString();
                String ngayNhanStr = modelPhieu.getValueAt(selectedRow, 2).toString();
                String ngayTraStr = modelPhieu.getValueAt(selectedRow, 3).toString();

                lblNgayThue.setText(ngayNhanStr);
                lblNgayTra.setText(ngayTraStr);

                // Analyze expected return date vs current date
                try {
                    LocalDate ngayTra = LocalDate.parse(ngayTraStr.split(" ")[0]);
                    LocalDate today = LocalDate.now();

                    long daysDiff = ChronoUnit.DAYS.between(ngayTra, today);

                    if (daysDiff < 0) {
                        lblTinhTrang.setText("Trả sớm " + Math.abs(daysDiff) + " ngày");
                        lblTinhTrang.setForeground(new Color(0x2E8B57)); // Sea Green
                    } else if (daysDiff > 0) {
                        lblTinhTrang.setText("Trả trễ " + daysDiff + " ngày");
                        lblTinhTrang.setForeground(Color.RED);
                    } else {
                        lblTinhTrang.setText("Đúng hạn");
                        lblTinhTrang.setForeground(Color.BLUE);
                    }
                } catch (Exception e) {
                    lblTinhTrang.setText("Lỗi tính ngày");
                    lblTinhTrang.setForeground(Color.RED);
                    System.out.println("Lỗi phân tích ngày: " + e.getMessage());
                }

                // Display service details for the selected ticket
                hienThiChiTietDichVu(maPhieu);
            } else {
                // Contract tab
                hienThiThongTinHopDong();
            }
        }

        // Reset displayed information
        private void resetThongTinPhieu() {
            lblNgayThue.setText("");
            lblNgayTra.setText("");
            lblTinhTrang.setText("");
            txtGhiChu.setText("");

            // Reset service details
            if (tblDichVu != null) {
                tblDichVu.setModel(new DefaultTableModel(
                        new String[]{"Mã DV", "Tên dịch vụ", "Đơn giá", "Số lượng", "Thành tiền"}, 0));
            }
            if (lblTongTienDichVu != null) {
                lblTongTienDichVu.setText("0 VND");
            }
        }

        // Handle checkout based on selected tab
        private void thucHienCheckOut() {
            if (tabbedPane.getSelectedIndex() == 0) {
                // Ticket tab
                thucHienCheckOutPhieu();
            } else {
                // Contract tab
                thucHienCheckOutHopDong();
            }
        }

        // Perform contract checkout
        private void thucHienCheckOutHopDong() {
            int selectedRow = tableHopDong.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hợp đồng để check-out!");
                return;
            }

            String maHopDong = modelHopDong.getValueAt(selectedRow, 0).toString();
            String ngayKetThucStr = modelHopDong.getValueAt(selectedRow, 3).toString().split(" ")[0];

            try {
                LocalDate ngayKetThuc = LocalDate.parse(ngayKetThucStr);
                LocalDate today = LocalDate.now();

                long daysDiff = ChronoUnit.DAYS.between(ngayKetThuc, today);
                String ghiChu = txtGhiChu.getText().trim();

                // Require note for early/late return
                if ((daysDiff != 0) && ghiChu.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Vui lòng nhập ghi chú khi trả phòng sớm/trễ!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Xác nhận check-out hợp đồng " + maHopDong + "?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                // Generate checkout ID
                String maCheckOut = "CO" + System.currentTimeMillis() % 1000000;
                String nhanVien = "NV001"; // TODO: Replace with logged-in employee ID

                // Create checkout record
                CheckOut checkOut = new CheckOut(
                        maCheckOut,
                        maHopDong,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        nhanVien,
                        ghiChu
                );

                // Execute checkout
                if (checkOutBUS.themCheckOut(checkOut)) {
                    // Update contract status
                    hopDongBUS.capNhatTrangThai(maHopDong, "da_tra");

                    // Get room list from contract details
                    List<String> danhSachPhong = cthdBUS.getPhongByMaHopDong(maHopDong);

                    // Update room status to "Empty"
                    for (String maPhong : danhSachPhong) {
                        phongBUS.capNhatTinhTrang(maPhong, "Trống");
                    }

                    // Success message
                    JOptionPane.showMessageDialog(this,
                            "Check-out hợp đồng thành công!\nMã check-out: " + maCheckOut,
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Ask to create invoice
                    int createInvoice = JOptionPane.showConfirmDialog(this,
                            "Bạn có muốn tạo hóa đơn thanh toán không?",
                            "Tạo hóa đơn", JOptionPane.YES_NO_OPTION);

                    if (createInvoice == JOptionPane.YES_OPTION) {
                        taoHoaDon(maHopDong, true);
                    }

                    // Refresh list
                    loadDanhSachHopDongDangSuDung();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Không thể thực hiện check-out. Vui lòng thử lại sau!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi xử lý: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        // Perform ticket checkout
        private void thucHienCheckOutPhieu() {
            int selectedRow = tablePhieu.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu để check-out!");
                return;
            }

            String maPhieu = modelPhieu.getValueAt(selectedRow, 0).toString();
            String ngayTraStr = modelPhieu.getValueAt(selectedRow, 3).toString().split(" ")[0];

            try {
                LocalDate ngayTra = LocalDate.parse(ngayTraStr);
                LocalDate today = LocalDate.now();

                long daysDiff = ChronoUnit.DAYS.between(ngayTra, today);
                String ghiChu = txtGhiChu.getText().trim();

                // Require note for early/late return
                if ((daysDiff != 0) && ghiChu.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Vui lòng nhập ghi chú khi trả phòng sớm/trễ!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận check-out phiếu " + maPhieu + "?\n" +
                                (daysDiff < 0 ? "Trả phòng sớm " + Math.abs(daysDiff) + " ngày.\n" :
                                        daysDiff > 0 ? "Trả phòng trễ " + daysDiff + " ngày.\n" :
                                                "Trả phòng đúng hạn.\n"),
                        "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                // Generate checkout ID
                String maCheckOut = "CO" + System.currentTimeMillis() % 1000000;
                String nhanVien = "NV001"; // TODO: Replace with logged-in employee ID

                // Create checkout record
                CheckOut checkOut = new CheckOut(
                        maCheckOut,
                        maPhieu,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        nhanVien,
                        ghiChu
                );

                // Execute checkout
                if (checkOutBUS.themCheckOut(checkOut)) {
                    // Update ticket status
                    if (pdpBUS.capNhatTrangThai(maPhieu, "da_tra")) {
                        // Update room status
                        List<String> dsPhong = ctpdpBUS.getPhongByMaPhieu(maPhieu);
                        boolean success = true;
                        for (String maPhong : dsPhong) {
                            if (!phongBUS.capNhatTinhTrangVaNguon(maPhong, "Trống", "")) {
                                success = false;
                            }
                        }

                        String message = "Check-out thành công phiếu " + maPhieu + "!";
                        if (!success) {
                            message += "\nMột số phòng không thể cập nhật trạng thái.";
                        }

                        JOptionPane.showMessageDialog(this,
                                message,
                                "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Ask to create invoice
                        int createInvoice = JOptionPane.showConfirmDialog(this,
                                "Bạn có muốn tạo hóa đơn thanh toán không?",
                                "Tạo hóa đơn", JOptionPane.YES_NO_OPTION);
                        if (createInvoice == JOptionPane.YES_OPTION) {
                            taoHoaDon(maPhieu, false); // false because it's a ticket
                        }

                        // Refresh list
                        loadDanhSachPhieuDangSuDung();
                        resetThongTinPhieu();
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Có lỗi khi cập nhật trạng thái phiếu!",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Có lỗi khi thực hiện check-out!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi xử lý: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        // Cancel ticket/contract
        private void huyPhieuHopDong() {
            int selectedIndex = tabbedPane.getSelectedIndex();

            if (selectedIndex == 0) {
                // Cancel ticket
                int selectedRow = tablePhieu.getSelectedRow();
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu để hủy!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maPhieu = modelPhieu.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn hủy phiếu đặt phòng " + maPhieu + "?",
                        "Xác nhận hủy", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (pdpBUS.capNhatTrangThai(maPhieu, "da_huy")) {
                        // Update room status
                        List<String> dsPhong = ctpdpBUS.getPhongByMaPhieu(maPhieu);
                        for (String maPhong : dsPhong) {
                            phongBUS.capNhatTinhTrang(maPhong, "Trống");
                        }
                        JOptionPane.showMessageDialog(this, "Đã hủy phiếu đặt phòng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadDanhSachPhieuDangSuDung();
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể hủy phiếu đặt phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                // Cancel contract
                int selectedRow = tableHopDong.getSelectedRow();
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn một hợp đồng để hủy!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maHopDong = modelHopDong.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn hủy hợp đồng " + maHopDong + "?",
                        "Xác nhận hủy", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (hopDongBUS.capNhatTrangThai(maHopDong, "da_huy")) {
                        // Update room status
                        List<String> dsPhong = cthdBUS.getPhongByMaHopDong(maHopDong);
                        for (String maPhong : dsPhong) {
                            phongBUS.capNhatTinhTrang(maPhong, "Trống");
                        }
                        JOptionPane.showMessageDialog(this, "Đã hủy hợp đồng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadDanhSachHopDongDangSuDung();
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể hủy hợp đồng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

        // View checkout history
        private void xemLichSuCheckOut() {
            JFrame historyFrame = new JFrame("Lịch sử Check-Out");
            historyFrame.setSize(700, 500);
            historyFrame.setLocationRelativeTo(this);
            historyFrame.setLayout(new BorderLayout(10, 10));
            historyFrame.getContentPane().setBackground(BACKGROUND_COLOR);

            // Create history table
            DefaultTableModel historyModel = new DefaultTableModel(
                    new String[]{"Mã Check-Out", "Mã phiếu/hợp đồng", "Thời gian", "Nhân viên", "Ghi chú"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            JTable historyTable = new JTable(historyModel);
            setupTable(historyTable);
            JScrollPane scrollPane = new JScrollPane(historyTable);

            // Search panel
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            searchPanel.setBackground(BACKGROUND_COLOR);
            JLabel lblSearch = new JLabel("Tìm kiếm:");
            JTextField txtSearch = new JTextField(20);
            JButton btnSearch = createStyledButton("Tìm");
            btnSearch.setPreferredSize(new Dimension(80, 30));

            searchPanel.add(lblSearch);
            searchPanel.add(txtSearch);
            searchPanel.add(btnSearch);

            // Load data into table
            List<CheckOut> listCheckOut = checkOutBUS.getLichSuCheckOut();
            for (CheckOut co : listCheckOut) {
                historyModel.addRow(new Object[]{
                        co.getMaCheckOut(),
                        co.getMaPhieuHoacHD(),
                        co.getThoiGian(),
                        co.getMaNhanVien(),
                        co.getGhiChu()
                });
            }

            // Search event handler
            ActionListener searchAction = e -> {
                String keyword = txtSearch.getText().trim();
                List<CheckOut> searchResult = checkOutBUS.timKiemCheckOut(keyword);
                historyModel.setRowCount(0);
                for (CheckOut co : searchResult) {
                    historyModel.addRow(new Object[]{
                            co.getMaCheckOut(),
                            co.getMaPhieuHoacHD(),
                            co.getThoiGian(),
                            co.getMaNhanVien(),
                            co.getGhiChu()
                    });
                }
            };

            btnSearch.addActionListener(searchAction);
            txtSearch.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        searchAction.actionPerformed(null);
                    }
                }
            });

            historyFrame.add(searchPanel, BorderLayout.NORTH);
            historyFrame.add(scrollPane, BorderLayout.CENTER);

            // Close button
            JButton btnClose = createStyledButton("Đóng");
            btnClose.addActionListener(e -> historyFrame.dispose());
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(BACKGROUND_COLOR);
            buttonPanel.add(btnClose);
            historyFrame.add(buttonPanel, BorderLayout.SOUTH);

            historyFrame.setVisible(true);
        }

        // Create invoice
        private void taoHoaDon(String maPhieuHoacHD, boolean isHopDong) {
            new HoaDonForm(maPhieuHoacHD, isHopDong);
        }

        // Display service details
        private void hienThiChiTietDichVu(String maPhieu) {
            // Clear old data
            DefaultTableModel model = (DefaultTableModel) tblDichVu.getModel();
            model.setRowCount(0);

            try {
                ChiTietDichVuBUS ctdvBUS = new ChiTietDichVuBUS();
                DichVuBUS dvBUS = new DichVuBUS();

                // Get service list for the ticket/contract
                List<ChiTietDichVu> listDV = ctdvBUS.getDichVuByMaPhieu(maPhieu);
                double tongTienDichVu = 0;

                if (listDV != null && !listDV.isEmpty()) {
                    for (ChiTietDichVu ctdv : listDV) {
                        DichVu dv = dvBUS.getDichVuByMa(ctdv.getMaDv());
                        if (dv != null) {
                            double thanhTien = dv.getGia() * ctdv.getSoLuong();
                            tongTienDichVu += thanhTien;

                            model.addRow(new Object[]{
                                    dv.getMaDv(),
                                    dv.getTenDv(),
                                    String.format("%,.0f VND", dv.getGia()),
                                    ctdv.getSoLuong(),
                                    String.format("%,.0f VND", thanhTien)
                            });
                        }
                    }
                }

                lblTongTienDichVu.setText(String.format("%,.0f VND", tongTienDichVu));
            } catch (Exception e) {
                System.err.println("Lỗi khi lấy chi tiết dịch vụ: " + e.getMessage());
                e.printStackTrace();
                lblTongTienDichVu.setText("0 VND");
            }
        }
    }