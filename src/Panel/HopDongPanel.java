package Panel;

    import bus.*;
    import model.*;
    import com.github.lgooddatepicker.components.DatePicker;
    import javax.swing.*;
    import javax.swing.border.EmptyBorder;
    import javax.swing.table.DefaultTableCellRenderer;
    import javax.swing.table.DefaultTableModel;
    import javax.swing.table.JTableHeader;
    import java.awt.*;
    import java.awt.Font;
    import java.awt.event.ActionEvent;
    import java.sql.Connection;
    import java.time.LocalDate;
    import java.time.temporal.ChronoUnit;
    import java.util.*;
    import com.itextpdf.text.*;
    import com.itextpdf.text.pdf.*;
    import java.io.FileOutputStream;
    import java.util.List;

public class HopDongPanel extends JPanel {
        // UI Constants
        private static final Color PRIMARY_COLOR = Color.decode("#4682B4");
        private static final Color BACKGROUND_COLOR = Color.decode("#DFF6FF");
        private static final Color TEXT_COLOR = Color.WHITE;
        private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
        private static final int PADDING = 10;

        private JComboBox<String> cbKhachHang;
        private JComboBox<String> cbPhuongThucThanhToan;
        private DatePicker dateBD, dateKT;
        private JTable tablePhong;
        private DefaultTableModel model;
        private JTextArea txtGhiChu;
        private JLabel lblTongTien;
        private JLabel lblTienCoc;

        private Map<String, List<String>> dichVuTheoPhong = new HashMap<>();

        private KhachHangBUS khBUS = new KhachHangBUS();
        private PhongBUS phongBUS = new PhongBUS();
        private HopDongBUS hopDongBUS = new HopDongBUS();
        private ChiTietHopDongBUS ctBUS = new ChiTietHopDongBUS();
        private ChiTietDichVuHopDongBUS dvBUS = new ChiTietDichVuHopDongBUS();
        private DichVuBUS dichVuBUS = new DichVuBUS();

        public HopDongPanel() {
            setLayout(new BorderLayout());
            setBackground(BACKGROUND_COLOR);
            setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

            // Header panel with title
            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            titlePanel.setBackground(BACKGROUND_COLOR);
            JLabel titleLabel = new JLabel("QUẢN LÝ HỢP ĐỒNG THUÊ");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            titleLabel.setForeground(PRIMARY_COLOR.darker());
            titlePanel.add(titleLabel);
            add(titlePanel, BorderLayout.NORTH);

            // Main content panel
            JPanel mainPanel = new JPanel(new BorderLayout(PADDING, PADDING));
            mainPanel.setBackground(BACKGROUND_COLOR);

            // Top form section
            JPanel formPanel = createFormPanel();
            mainPanel.add(formPanel, BorderLayout.NORTH);

            // Room table section
            JPanel tablePanel = createTablePanel();
            mainPanel.add(tablePanel, BorderLayout.CENTER);

            // Bottom section with totals and buttons
            JPanel bottomPanel = createBottomPanel();
            mainPanel.add(bottomPanel, BorderLayout.SOUTH);

            add(mainPanel, BorderLayout.CENTER);

            // Initialize data and event listeners
            dateBD.addDateChangeListener(e -> tinhTongTien());
            dateKT.addDateChangeListener(e -> tinhTongTien());
            tablePhong.getModel().addTableModelListener(e -> {
                if (e.getColumn() == 0) tinhTongTien();
            });

            loadKhachHang();
            loadPhong();
        }

        private JPanel createFormPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(BACKGROUND_COLOR);
            panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                    BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
            ));

            // Customer information section
            JPanel customerPanel = new JPanel(new GridLayout(2, 4, PADDING, PADDING));
            customerPanel.setBackground(BACKGROUND_COLOR);

            JLabel khTitle = new JLabel("THÔNG TIN KHÁCH HÀNG", JLabel.LEFT);
            khTitle.setFont(LABEL_FONT);
            khTitle.setForeground(PRIMARY_COLOR.darker());

            cbKhachHang = new JComboBox<>();
            cbKhachHang.setPreferredSize(new Dimension(200, 30));

            customerPanel.add(createStyledLabel("Khách hàng:"));
            customerPanel.add(cbKhachHang);
            customerPanel.add(createStyledLabel("Phương thức thanh toán:"));

            cbPhuongThucThanhToan = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản", "Thẻ tín dụng"});
            customerPanel.add(cbPhuongThucThanhToan);

            // Contract dates section
            JPanel datesPanel = new JPanel(new GridLayout(1, 4, PADDING, PADDING));
            datesPanel.setBackground(BACKGROUND_COLOR);

            JLabel contractTitle = new JLabel("THÔNG TIN HỢP ĐỒNG", JLabel.LEFT);
            contractTitle.setFont(LABEL_FONT);
            contractTitle.setForeground(PRIMARY_COLOR.darker());

            // Create date pickers
            dateBD = new DatePicker();
            dateKT = new DatePicker();
            dateBD.setDate(LocalDate.now());
            dateKT.setDate(LocalDate.now().plusDays(7));

            datesPanel.add(createStyledLabel("Ngày bắt đầu:"));
            datesPanel.add(dateBD);
            datesPanel.add(createStyledLabel("Ngày kết thúc:"));
            datesPanel.add(dateKT);

            // Notes section
            JPanel notesPanel = new JPanel(new BorderLayout(PADDING, PADDING));
            notesPanel.setBackground(BACKGROUND_COLOR);

            txtGhiChu = new JTextArea(3, 20);
            txtGhiChu.setFont(new Font("Arial", Font.PLAIN, 13));
            txtGhiChu.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR.brighter()));
            JScrollPane scrollGhiChu = new JScrollPane(txtGhiChu);

            notesPanel.add(createStyledLabel("Ghi chú:"), BorderLayout.NORTH);
            notesPanel.add(scrollGhiChu, BorderLayout.CENTER);

            // Add all sections to the main panel
            panel.add(khTitle);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
            panel.add(customerPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(contractTitle);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
            panel.add(datesPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(notesPanel);

            return panel;
        }

        private JPanel createTablePanel() {
            JPanel panel = new JPanel(new BorderLayout(PADDING, PADDING));
            panel.setBackground(BACKGROUND_COLOR);
            panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                    BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
            ));

            JLabel tableTitle = new JLabel("DANH SÁCH PHÒNG TRỐNG", JLabel.LEFT);
            tableTitle.setFont(LABEL_FONT);
            tableTitle.setForeground(PRIMARY_COLOR.darker());
            panel.add(tableTitle, BorderLayout.NORTH);

            // Create table model with columns
            model = new DefaultTableModel(new String[]{"Chọn", "Mã phòng", "Loại", "Giá/ngày", "Tình trạng", "Dịch vụ"}, 0) {
                @Override
                public Class<?> getColumnClass(int col) {
                    return col == 0 ? Boolean.class : String.class;
                }

                @Override
                public boolean isCellEditable(int row, int col) {
                    return col == 0 || col == 5;
                }
            };

            tablePhong = new JTable(model);
            tablePhong.setRowHeight(25);
            tablePhong.setSelectionBackground(PRIMARY_COLOR);
            tablePhong.setSelectionForeground(TEXT_COLOR);
            tablePhong.setBackground(Color.WHITE);

            // Sử dụng custom header renderer
            JTableHeader header = tablePhong.getTableHeader();
            header.setDefaultRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                            boolean isSelected, boolean hasFocus,
                                                            int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    label.setBackground(PRIMARY_COLOR);
                    label.setForeground(TEXT_COLOR);
                    label.setFont(new Font("Arial", Font.BOLD, 12));
                    label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.LIGHT_GRAY));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    return label;
                }
            });

            // Căn giữa nội dung các cột (trừ cột checkbox)
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            for (int i = 1; i < tablePhong.getColumnCount() - 1; i++) {
                tablePhong.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // Thiết lập renderer và editor cho cột "Dịch vụ"
            tablePhong.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
            tablePhong.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

            // Điều chỉnh độ rộng cột
            tablePhong.getColumnModel().getColumn(0).setMaxWidth(50);
            tablePhong.getColumnModel().getColumn(5).setMaxWidth(100);

            // Thêm bảng vào scrollPane và panel
            JScrollPane scrollPane = new JScrollPane(tablePhong);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            panel.add(scrollPane, BorderLayout.CENTER);

            return panel;
        }


        private JPanel createBottomPanel() {
            JPanel panel = new JPanel(new BorderLayout(PADDING, PADDING));
            panel.setBackground(BACKGROUND_COLOR);

            // Price information panel
            JPanel pricePanel = new JPanel(new GridLayout(2, 2, PADDING, PADDING));
            pricePanel.setBackground(BACKGROUND_COLOR);
            pricePanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                    BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
            ));

            lblTongTien = new JLabel("0 VND", JLabel.RIGHT);
            lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));

            lblTienCoc = new JLabel("0 VND", JLabel.RIGHT);
            lblTienCoc.setFont(new Font("Arial", Font.BOLD, 14));

            pricePanel.add(createStyledLabel("Tổng tiền:"));
            pricePanel.add(lblTongTien);
            pricePanel.add(createStyledLabel("Tiền cọc (30%):"));
            pricePanel.add(lblTienCoc);

            panel.add(pricePanel, BorderLayout.CENTER);

            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING, PADDING));
            buttonPanel.setBackground(BACKGROUND_COLOR);

            JButton btnLap = createStyledButton("Lập hợp đồng");
            JButton btnReset = createStyledButton("Làm mới");
            JButton btnXuat = createStyledButton("Xuất PDF");

            btnLap.addActionListener(this::handleLapHopDong);
            btnReset.addActionListener(e -> {
                txtGhiChu.setText("");
                dichVuTheoPhong.clear();
                loadPhong();
                tinhTongTien();
            });
            btnXuat.addActionListener(e -> {
                // Method will be implemented to export selected contract
                JOptionPane.showMessageDialog(this, "Chức năng xuất PDF sẽ được triển khai sau.",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            });

            buttonPanel.add(btnLap);
            buttonPanel.add(btnReset);
            buttonPanel.add(btnXuat);

            panel.add(buttonPanel, BorderLayout.SOUTH);

            return panel;
        }

        private JLabel createStyledLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            return label;
        }

        private JButton createStyledButton(String text) {
            JButton button = new JButton(text) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
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
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(100, 35));
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0x2B5174), 2),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

            return button;
        }

        private void loadKhachHang() {
            cbKhachHang.removeAllItems();
            for (KhachHang kh : khBUS.getAllKhachHang()) {
                cbKhachHang.addItem(kh.getMaKhachHang() + " - " + kh.getHoTen());
            }
        }

        private void loadPhong() {
            model.setRowCount(0);
            for (Phong p : phongBUS.getPhongTrong()) {
                model.addRow(new Object[]{
                        false,
                        p.getMaPhong(),
                        p.getTenLoai(),
                        p.getGia(),
                        p.getTinhTrang(),
                        "Chọn"
                });
            }
        }

        private void loadHopDong() {
            model.setRowCount(0);
            List<HopDong> dsHopDong = hopDongBUS.getAllHopDong();
            for (HopDong hd : dsHopDong) {
                model.addRow(new Object[]{
                        hd.getMaHopDong(),
                        hd.getMaKhachHang(),
                        hd.getNgayBatDau(),
                        hd.getNgayKetThuc(),
                        hd.getGhiChu()
                });
            }
        }

        private void chonDichVu(int row) {
            // Check if room is selected
            Boolean isSelected = (Boolean) model.getValueAt(row, 0);
            if (isSelected == null || !isSelected) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng tích chọn phòng trước khi chọn dịch vụ!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maPhong = model.getValueAt(row, 1).toString();
            List<DichVu> dsDichVu = dichVuBUS.getAllDichVu();

            DefaultTableModel dvModel = new DefaultTableModel(
                    new String[]{"Chọn", "Mã DV", "Tên dịch vụ", "Giá (VND)"}, 0
            ) {
                @Override
                public Class<?> getColumnClass(int column) {
                    return column == 0 ? Boolean.class : String.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 0;
                }
            };

            // Add previously selected services (if any)
            for (DichVu dv : dsDichVu) {
                boolean selected = false;
                if (dichVuTheoPhong.containsKey(maPhong)) {
                    for (String maDV : dichVuTheoPhong.get(maPhong)) {
                        if (maDV.equals(dv.getMaDv())) {
                            selected = true;
                            break;
                        }
                    }
                }
                dvModel.addRow(new Object[]{
                        selected, dv.getMaDv(), dv.getTenDv(), String.format("%,.0f", dv.getGia())
                });
            }

            JTable tableDichVu = new JTable(dvModel);
            tableDichVu.getColumnModel().getColumn(0).setMaxWidth(50);
            JScrollPane scrollPane = new JScrollPane(tableDichVu);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            int result = JOptionPane.showConfirmDialog(this, scrollPane,
                    "Chọn dịch vụ cho phòng " + maPhong,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                List<String> maDVList = new ArrayList<>();
                for (int i = 0; i < dvModel.getRowCount(); i++) {
                    Boolean selected = (Boolean) dvModel.getValueAt(i, 0);
                    if (selected != null && selected) {
                        maDVList.add(dvModel.getValueAt(i, 1).toString());
                    }
                }
                dichVuTheoPhong.put(maPhong, maDVList);
                tinhTongTien(); // Recalculate total when services are selected
            }
        }

        private void tinhTongTien() {
            if (dateBD.getDate() == null || dateKT.getDate() == null) {
                lblTongTien.setText("0 VND");
                lblTienCoc.setText("0 VND");
                return;
            }

            LocalDate ngayBD = dateBD.getDate();
            LocalDate ngayKT = dateKT.getDate();
            if (ngayBD.isAfter(ngayKT)) {
                lblTongTien.setText("0 VND (Ngày không hợp lệ)");
                lblTienCoc.setText("0 VND");
                return;
            }

            long soNgay = ChronoUnit.DAYS.between(ngayBD, ngayKT);
            if (soNgay <= 0) {
                lblTongTien.setText("0 VND (Cần thuê ít nhất 1 ngày)");
                lblTienCoc.setText("0 VND");
                return;
            }

            double tongTienPhong = 0;
            double tongTienDichVu = 0;

            // Calculate room cost
            for (int i = 0; i < model.getRowCount(); i++) {
                Boolean chon = (Boolean) model.getValueAt(i, 0);
                if (chon != null && chon) {
                    String maPhong = model.getValueAt(i, 1).toString();
                    Object giaObj = model.getValueAt(i, 3);
                    double gia = 0;

                    if (giaObj instanceof Double) {
                        gia = (Double) giaObj;
                    } else if (giaObj instanceof Integer) {
                        gia = ((Integer) giaObj).doubleValue();
                    } else if (giaObj instanceof String) {
                        String giaStr = ((String) giaObj).replace(",", "").replace(".", "").replace("VND", "").trim();
                        try {
                            gia = Double.parseDouble(giaStr);
                        } catch (NumberFormatException e) {
                            System.err.println("Lỗi parse giá phòng: " + giaObj);
                        }
                    }

                    double tienPhong = gia * soNgay;
                    tongTienPhong += tienPhong;

                    // Calculate service cost
                    if (dichVuTheoPhong.containsKey(maPhong)) {
                        for (String maDV : dichVuTheoPhong.get(maPhong)) {
                            DichVu dv = dichVuBUS.getDichVuByMa(maDV);
                            if (dv != null) {
                                tongTienDichVu += dv.getGia();
                            }
                        }
                    }
                }
            }

            double tongTien = tongTienPhong + tongTienDichVu;
            double tienCoc = tongTien * 0.3;

            lblTongTien.setText(String.format("%,d VND", (int)tongTien));
            lblTienCoc.setText(String.format("%,d VND", (int)tienCoc));
        }

private void handleLapHopDong(ActionEvent e) {
    try {
        // 1. Kiểm tra đầu vào
        if (dateBD.getDate() == null || dateKT.getDate() == null) {
            throw new IllegalArgumentException("Vui lòng chọn ngày bắt đầu và kết thúc!");
        }

        // Lấy thông tin từ form
        String maKH = cbKhachHang.getSelectedItem().toString().split(" - ")[0];
        String phuongThucThanhToan = cbPhuongThucThanhToan.getSelectedItem().toString();
        String ghiChu = txtGhiChu.getText().trim();
        String maHD = "HD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        LocalDate ngayBD = dateBD.getDate();
        LocalDate ngayKT = dateKT.getDate();
        String ngayLap = LocalDate.now().toString();

        // Tính số ngày thuê
        int soNgay = (int) ChronoUnit.DAYS.between(ngayBD, ngayKT);
        if (soNgay <= 0) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu!");
        }

        // 2. Xử lý danh sách phòng và dịch vụ
        List<String> dsPhong = new ArrayList<>();
        Map<String, List<String>> dichVuTheoPhongCopy = new HashMap<>(dichVuTheoPhong);
        double tongTienPhong = 0;
        double tongTienDichVu = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean chon = (Boolean) model.getValueAt(i, 0);
            if (chon != null && chon) {
                String maPhong = model.getValueAt(i, 1).toString();
                dsPhong.add(maPhong);

                // Lấy giá phòng
                Object giaObj = model.getValueAt(i, 3);
                double gia = 0;

                if (giaObj instanceof Double) {
                    gia = (Double) giaObj;
                } else if (giaObj instanceof Integer) {
                    gia = ((Integer) giaObj).doubleValue();
                } else if (giaObj instanceof String) {
                    String giaStr = ((String) giaObj).replace(",", "").replace(".", "").replace("VND", "").trim();
                    try {
                        gia = Double.parseDouble(giaStr);
                    } catch (NumberFormatException ex) {
                        System.err.println("Lỗi định dạng giá: " + giaObj);
                    }
                }

                tongTienPhong += gia * soNgay;

                // Xử lý dịch vụ
                if (dichVuTheoPhong.containsKey(maPhong)) {
                    for (String maDV : dichVuTheoPhong.get(maPhong)) {
                        DichVu dv = dichVuBUS.getDichVuByMa(maDV);
                        if (dv != null) {
                            tongTienDichVu += dv.getGia();
                        }
                    }
                }
            }
        }

        // Kiểm tra số lượng phòng tối thiểu
        if (dsPhong.size() < 4) {
            throw new IllegalArgumentException("Hợp đồng phải thuê từ 4 phòng trở lên!");
        }

        // 3. Tính toán tổng tiền và tiền cọc
        double tongTien = tongTienPhong + tongTienDichVu;
        int tongTienInt = (int) tongTien;
        int datCocInt = (int) (tongTien * 0.3);

        // 4. Tạo đối tượng hợp đồng với daThanhToan = 0 (chưa thanh toán)
        HopDong hd = new HopDong(maHD, maKH, dsPhong.size(), ngayLap,
                ngayBD.toString(), ngayKT.toString(), soNgay,
                0, ngayLap, ghiChu,
                datCocInt, tongTienInt, phuongThucThanhToan, "dang_dat");

        // 5. Xác nhận thông tin với người dùng
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tổng tiền: " + String.format("%,d VND", tongTienInt) +
                        "\nTiền đặt cọc (30%): " + String.format("%,d VND", datCocInt) +
                        "\nPhương thức thanh toán: " + phuongThucThanhToan +
                        "\nBạn có chắc chắn muốn lập hợp đồng này?",
                "Xác nhận lập hợp đồng", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        // 6. Thực hiện lưu hợp đồng trong một transaction
        boolean success = hopDongBUS.themHopDongVaChiTiet(hd, dsPhong, dichVuTheoPhongCopy);

        if (!success) {
            throw new Exception("Không thể lưu hợp đồng và chi tiết!");
        }

        // 7. Cập nhật trạng thái thanh toán thành "đã cọc" (1)
        boolean depositSuccess = hopDongBUS.capNhatThanhToan(maHD, datCocInt, ngayLap, 1);
        if (!depositSuccess) {
            JOptionPane.showMessageDialog(this,
                    "Hợp đồng đã được tạo nhưng không thể cập nhật thông tin đặt cọc.",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }

        // 8. Hiển thị thông báo thành công
        JOptionPane.showMessageDialog(this,
                "Lập hợp đồng thành công!\nMã: " + maHD +
                        "\nTổng tiền: " + String.format("%,d VND", tongTienInt) +
                        "\nĐã đặt cọc: " + String.format("%,d VND", datCocInt) +
                        "\nCòn lại: " + String.format("%,d VND", tongTienInt - datCocInt),
                "Thành công", JOptionPane.INFORMATION_MESSAGE);

        // 9. Hỏi người dùng có muốn thanh toán đầy đủ ngay bây giờ không
        int payNow = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn thanh toán đầy đủ ngay bây giờ không?\n" +
                "Số tiền cần thanh toán: " + String.format("%,d VND", tongTienInt - datCocInt),
                "Thanh toán đầy đủ", JOptionPane.YES_NO_OPTION);

        if (payNow == JOptionPane.YES_OPTION) {
            // Cập nhật thành "đã thanh toán đầy đủ" (2)
            boolean paymentSuccess = hopDongBUS.capNhatThanhToanDayDu(
                    maHD, tongTienInt - datCocInt, LocalDate.now().toString(), 2);

            if (paymentSuccess) {
                JOptionPane.showMessageDialog(this,
                    "Đã thanh toán đầy đủ thành công!",
                    "Thanh toán hoàn tất", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Không thể cập nhật thông tin thanh toán đầy đủ.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

        // 10. Xóa form, làm mới dữ liệu
        txtGhiChu.setText("");
        dichVuTheoPhong.clear();
        loadPhong();
        tinhTongTien();

    } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this,
                "Lỗi dữ liệu: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Đã xảy ra lỗi không mong muốn: " + ex.getMessage() +
                        "\nVui lòng liên hệ quản trị viên.",
                "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}
// Inner class for button renderer in table
        class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
            public ButtonRenderer() {
                setText("Chọn");
            }

            public Component getTableCellRendererComponent(JTable table, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
                Boolean roomSelected = (Boolean) table.getValueAt(row, 0);
                if (roomSelected == null || !roomSelected) {
                    setEnabled(false);
                    setText("Chọn");
                    setToolTipText("Hãy chọn phòng trước");
                } else {
                    setEnabled(true);
                    setText("Chọn");
                    setToolTipText("Chọn dịch vụ cho phòng này");
                }
                return this;
            }
        }

        // Inner class for button editor in table
        class ButtonEditor extends DefaultCellEditor {
            private JButton button;
            private boolean isPushed;
            private int row;

            public ButtonEditor(JCheckBox checkBox) {
                super(checkBox);
                button = new JButton("Chọn");
                button.setOpaque(true);
                button.addActionListener(e -> fireEditingStopped());
            }

            public Component getTableCellEditorComponent(JTable table, Object value,
                                                       boolean isSelected, int row, int column) {
                this.row = row;
                isPushed = true;

                Boolean roomSelected = (Boolean) table.getValueAt(row, 0);
                if (roomSelected == null || !roomSelected) {
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }

                return button;
            }

            public Object getCellEditorValue() {
                if (isPushed) {
                    chonDichVu(row);
                }
                isPushed = false;
                return "Chọn";
            }

            public boolean stopCellEditing() {
                isPushed = false;
                return super.stopCellEditing();
            }
        }
    }