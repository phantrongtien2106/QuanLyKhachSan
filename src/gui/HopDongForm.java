package gui;

// Replace the existing imports with these
import bus.*;
import model.*;
import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; // Use java.util.List explicitly
import java.util.Map;
import java.util.UUID;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;

public class HopDongForm extends JFrame {
    private JComboBox<String> cbKhachHang;
    private JComboBox<String> cbPhuongThucThanhToan;
    private DatePicker dateBD, dateKT;
    private JTable tablePhong;
    private DefaultTableModel model;
    private JTextArea txtGhiChu;
    private JLabel lblTongTien;
    private JLabel lblTienCoc;

    private Map<String, java.util.List<String>> dichVuTheoPhong = new HashMap<>();

    private KhachHangBUS khBUS = new KhachHangBUS();
    private PhongBUS phongBUS = new PhongBUS();
    private HopDongBUS hopDongBUS = new HopDongBUS();
    private ChiTietHopDongBUS ctBUS = new ChiTietHopDongBUS();
    private ChiTietDichVuHopDongBUS dvBUS = new ChiTietDichVuHopDongBUS();
    private DichVuBUS dichVuBUS = new DichVuBUS();

    public HopDongForm() {
        setTitle("Tạo hợp đồng thuê có dịch vụ");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#DFF6FF"));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 5));
        form.setBackground(Color.decode("#DFF6FF"));

        cbKhachHang = new JComboBox<>();
        dateBD = new DatePicker();
        dateKT = new DatePicker();
        txtGhiChu = new JTextArea(3, 20);

        form.add(new JLabel("Khách hàng:"));
        form.add(cbKhachHang);
        form.add(new JLabel("Ngày bắt đầu:"));
        form.add(dateBD);
        form.add(new JLabel("Ngày kết thúc:"));
        form.add(dateKT);
        form.add(new JLabel("Ghi chú:"));
        form.add(new JScrollPane(txtGhiChu));
        cbPhuongThucThanhToan = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản", "Thẻ tín dụng"});
        form.add(new JLabel("Phương thức thanh toán:"));
        form.add(cbPhuongThucThanhToan);
        add(form, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"Chọn", "Mã phòng", "Loại", "Giá/ngày", "Tình trạng", "Dịch vụ"}, 0) {
            @Override public Class<?> getColumnClass(int col) {
                return col == 0 ? Boolean.class : String.class;
            }
            @Override public boolean isCellEditable(int row, int col) {
                return col == 0 || col == 5;
            }
        };

        tablePhong = new JTable(model);
        tablePhong.getColumn("Dịch vụ").setCellRenderer(new ButtonRenderer());
        tablePhong.getColumn("Dịch vụ").setCellEditor(new ButtonEditor(new JCheckBox()));
        add(new JScrollPane(tablePhong), BorderLayout.CENTER);

        JButton btnLap = new JButton("Lập hợp đồng");
        btnLap.addActionListener(this::handleLapHopDong);
        btnLap.setBackground(Color.decode("#4682B4"));
        btnLap.setForeground(Color.WHITE);

        JPanel pricePanel = new JPanel(new GridLayout(2, 2, 10, 5));
        pricePanel.setBackground(Color.decode("#DFF6FF"));
        lblTongTien = new JLabel("0 VND");
        lblTienCoc = new JLabel("0 VND");
        pricePanel.add(new JLabel("Tổng tiền:"));
        pricePanel.add(lblTongTien);
        pricePanel.add(new JLabel("Tiền cọc (30%):"));
        pricePanel.add(lblTienCoc);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.decode("#DFF6FF"));
        bottomPanel.add(pricePanel, BorderLayout.CENTER);
        bottomPanel.add(btnLap, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        dateBD.addDateChangeListener(e -> tinhTongTien());
        dateKT.addDateChangeListener(e -> tinhTongTien());
        tablePhong.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 0) tinhTongTien();
        });

        loadKhachHang();
        loadPhong();
        setVisible(true);
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
            // Kiểm tra và in giá phòng
            System.out.println("Đang tải phòng: " + p.getMaPhong() + ", Giá: " + p.getGia());

            model.addRow(new Object[]{
                    false,
                    p.getMaPhong(),
                    p.getTenLoai(),
                    Double.valueOf(p.getGia()), // Chuyển đổi rõ ràng sang Double
                    p.getTinhTrang(),
                    "Chọn"
            });
        }
    }

    private void chonDichVu(int row) {
        // Kiểm tra phòng đã được tích chọn chưa
        Boolean isSelected = (Boolean) model.getValueAt(row, 0);
        if (isSelected == null || !isSelected) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng tích chọn phòng trước khi chọn dịch vụ!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhong = model.getValueAt(row, 1).toString();
        List<DichVu> dsDichVu = dichVuBUS.getAllDichVu();

        // Phần code còn lại giữ nguyên
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

        // Thêm các dịch vụ đã chọn trước đó (nếu có)
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
                    selected, dv.getMaDv(), dv.getTenDv(), String.format("%.2f", dv.getGia())
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
            tinhTongTien(); // Tính lại tổng tiền khi chọn dịch vụ
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

        // Tính tiền phòng
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean chon = (Boolean) model.getValueAt(i, 0);
            if (chon != null && chon) {
                String maPhong = model.getValueAt(i, 1).toString();

                // Lấy giá phòng từ cột 3 (giá/ngày)
                Object giaObj = model.getValueAt(i, 3);
                double gia = 0;

                // Kiểm tra kiểu dữ liệu của giá
                if (giaObj instanceof Double) {
                    gia = (Double) giaObj;
                } else if (giaObj instanceof Integer) {
                    gia = ((Integer) giaObj).doubleValue();
                } else if (giaObj instanceof String) {
                    // Nếu là String, xử lý định dạng
                    String giaStr = ((String) giaObj).replace(",", "").replace(".", "").replace("VND", "").trim();
                    try {
                        gia = Double.parseDouble(giaStr);
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi parse giá phòng: " + giaObj);
                    }
                }

                // Debug
                System.out.println("Phòng: " + maPhong + ", Giá: " + gia + ", Số ngày: " + soNgay);

                double tienPhong = gia * soNgay;
                tongTienPhong += tienPhong;
                System.out.println("Tiền phòng " + maPhong + ": " + tienPhong);

                // Tính tiền dịch vụ
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

        System.out.println("Tổng tiền phòng: " + tongTienPhong);
        System.out.println("Tổng tiền dịch vụ: " + tongTienDichVu);
        System.out.println("Tổng tiền: " + tongTien);

        lblTongTien.setText(String.format("%,d VND", (int)tongTien));
        lblTienCoc.setText(String.format("%,d VND", (int)tienCoc));
    }

    private void xuatHoaDonPDF(HopDong hd, List<String> dsPhong) {
        try {
            String fileName = "HoaDon_" + hd.getMaHopDong() + ".pdf";
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Khai báo font trước khi sử dụng
            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12);
            com.itextpdf.text.Font boldFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.BOLD);

            // Tiêu đề
            Paragraph title = new Paragraph("HÓA ĐƠN THUÊ PHÒNG");
            title.setFont(titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Khoảng trống

            // Thông tin khách hàng

            KhachHang kh = khBUS.getKhachHangByMa(hd.getMaKhachHang());
            document.add(new Paragraph("Mã hợp đồng: " + hd.getMaHopDong(), boldFont));
            document.add(new Paragraph("Ngày lập: " + hd.getLichDatPhong(), normalFont));
            document.add(new Paragraph("Khách hàng: " + (kh != null ? kh.getHoTen() : hd.getMaKhachHang()), normalFont));
            document.add(new Paragraph("Thời gian thuê: " + hd.getNgayBatDau() + " đến " + hd.getNgayKetThuc(), normalFont));
            document.add(new Paragraph("Số ngày thuê: " + hd.getTongNgayThue(), normalFont));
            document.add(new Paragraph("Phương thức thanh toán: " + hd.getPhuongThucThanhToan(), normalFont));
            document.add(new Paragraph(" "));

            // Bảng thông tin phòng
            PdfPTable tablePhong = new PdfPTable(3);
            tablePhong.setWidthPercentage(100);
            tablePhong.setWidths(new int[]{1, 2, 1});

            tablePhong.addCell(new PdfPCell(new Phrase("Mã phòng", boldFont)));
            tablePhong.addCell(new PdfPCell(new Phrase("Loại phòng", boldFont)));
            tablePhong.addCell(new PdfPCell(new Phrase("Giá/ngày (VND)", boldFont)));

            double tongTienPhong = 0;
            for (String maPhong : dsPhong) {
                Phong p = phongBUS.getPhongByMa(maPhong);
                if (p != null) {
                    tablePhong.addCell(maPhong);
                    tablePhong.addCell(p.getTenLoai());
                    // Sửa định dạng để tránh lỗi
                    tablePhong.addCell(String.format("%,.0f", p.getGia()));
                    tongTienPhong += p.getGia() * hd.getTongNgayThue();
                }
            }

            document.add(new Paragraph("DANH SÁCH PHÒNG:", boldFont));
            document.add(tablePhong);
            document.add(new Paragraph(" "));

            // Bảng dịch vụ
            double tongTienDichVu = 0;
            boolean coDichVu = false;

            for (String maPhong : dsPhong) {
                if (dichVuTheoPhong.containsKey(maPhong) && !dichVuTheoPhong.get(maPhong).isEmpty()) {
                    coDichVu = true;
                    break;
                }
            }

            if (coDichVu) {
                document.add(new Paragraph("DỊCH VỤ SỬ DỤNG:", boldFont));
                PdfPTable tableDV = new PdfPTable(4);
                tableDV.setWidthPercentage(100);
                tableDV.setWidths(new int[]{1, 2, 2, 1});

                tableDV.addCell(new PdfPCell(new Phrase("Phòng", boldFont)));
                tableDV.addCell(new PdfPCell(new Phrase("Mã DV", boldFont)));
                tableDV.addCell(new PdfPCell(new Phrase("Tên dịch vụ", boldFont)));
                tableDV.addCell(new PdfPCell(new Phrase("Giá (VND)", boldFont)));

                for (String maPhong : dsPhong) {
                    if (dichVuTheoPhong.containsKey(maPhong)) {
                        for (String maDV : dichVuTheoPhong.get(maPhong)) {
                            DichVu dv = dichVuBUS.getDichVuByMa(maDV);
                            if (dv != null) {
                                tableDV.addCell(maPhong);
                                tableDV.addCell(dv.getMaDv());
                                tableDV.addCell(dv.getTenDv());
                                tableDV.addCell(String.format("%,.0f", dv.getGia()));
                                tongTienDichVu += dv.getGia();
                            }
                        }
                    }
                }

                document.add(tableDV);
                document.add(new Paragraph(" "));
            }

// Tổng kết
            document.add(new Paragraph("THANH TOÁN:", boldFont));
            document.add(new Paragraph("Tổng tiền phòng: " + String.format("%,.0f VND", tongTienPhong), normalFont));
            if (coDichVu) {
                document.add(new Paragraph("Tổng tiền dịch vụ: " + String.format("%,.0f VND", tongTienDichVu), normalFont));
            }
            document.add(new Paragraph("Tổng cộng: " + String.format("%,.0f VND", (tongTienPhong + tongTienDichVu)), boldFont));
            document.add(new Paragraph("Đặt cọc (30%): " + String.format("%,.0f VND", (double)hd.getDatCoc()), boldFont));

            document.close();

            JOptionPane.showMessageDialog(this,
                    "Đã xuất hóa đơn thành công!\nFile: " + fileName,
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tạo file PDF: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLapHopDong(ActionEvent e) {
        if (dateBD.getDate() == null || dateKT.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày bắt đầu và kết thúc!");
            return;
        }

        // Lấy thông tin
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
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu!");
            return;
        }

        // Danh sách phòng
        List<String> dsPhong = new ArrayList<>();
        double tongTienPhong = 0;
        double tongTienDichVu = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean chon = (Boolean) model.getValueAt(i, 0);
            if (chon != null && chon) {
                String maPhong = model.getValueAt(i, 1).toString();
                dsPhong.add(maPhong);

                // Giá phòng
                String giaText = model.getValueAt(i, 3).toString().replace(",", "").trim();
                try {
                    double gia = Double.parseDouble(giaText);
                    tongTienPhong += gia * soNgay;

                    // Dịch vụ phòng
                    if (dichVuTheoPhong.containsKey(maPhong)) {
                        for (String maDV : dichVuTheoPhong.get(maPhong)) {
                            DichVu dv = dichVuBUS.getDichVuByMa(maDV);
                            if (dv != null) tongTienDichVu += dv.getGia();
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Lỗi định dạng giá: " + giaText);
                }
            }
        }

        // ✅ Kiểm tra đủ 4 phòng
        if (dsPhong.size() < 4) {
            JOptionPane.showMessageDialog(this, "Hợp đồng phải thuê từ 4 phòng trở lên!");
            return;
        }

        double tongTien = tongTienPhong + tongTienDichVu;
        int tongTienInt = (int) tongTien;
        int datCocInt = (int) (tongTien * 0.3);

        HopDong hd = new HopDong(maHD, maKH, dsPhong.size(), ngayLap,
                ngayBD.toString(), ngayKT.toString(), soNgay,
                1, ngayLap, ghiChu,
                datCocInt, tongTienInt, phuongThucThanhToan, "dang_dat");


        // Xác nhận
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tổng tiền: " + String.format("%,d VND", tongTienInt) +
                        "\nTiền đặt cọc (30%): " + String.format("%,d VND", datCocInt) +
                        "\nPhương thức thanh toán: " + phuongThucThanhToan +
                        "\nBạn có chắc chắn muốn lập hợp đồng này?",
                "Xác nhận lập hợp đồng", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        // Thêm hợp đồng
        boolean success = hopDongBUS.themHopDong(hd);
        if (!success) {
            JOptionPane.showMessageDialog(this, "Không thể lưu hợp đồng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ✅ Lưu chi tiết hợp đồng & cập nhật trạng thái phòng
        for (String maPhong : dsPhong) {
            ctBUS.themChiTiet(new ChiTietHopDong(maHD, maPhong));
            phongBUS.capNhatTinhTrangVaNguon(maPhong, "Đang đặt", "hop_dong"); // ✅ cập nhật trạng thái và nguồn
            // Dịch vụ đi kèm
            if (dichVuTheoPhong.containsKey(maPhong)) {
                for (String maDV : dichVuTheoPhong.get(maPhong)) {
                    dvBUS.themChiTiet(new ChiTietDichVuHopDong(maHD, maPhong, maDV));
                }
            }
        }

        // Thông báo thành công
        JOptionPane.showMessageDialog(this,
                "Lập hợp đồng thành công!\nMã: " + maHD +
                        "\nTổng tiền: " + String.format("%,d VND", tongTienInt) +
                        "\nĐặt cọc: " + String.format("%,d VND", datCocInt),
                "Thành công", JOptionPane.INFORMATION_MESSAGE);

        // Gợi ý xuất PDF
        int xuatPDF = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xuất hóa đơn PDF không?", "Xuất hóa đơn",
                JOptionPane.YES_NO_OPTION);
        if (xuatPDF == JOptionPane.YES_OPTION) {
            xuatHoaDonPDF(hd, dsPhong);
        }

        // Reset form
        txtGhiChu.setText("");
        dichVuTheoPhong.clear();
        loadPhong();
        tinhTongTien();
    }


    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setText("Chọn");
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            // Kiểm tra phòng có được chọn không
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

            // Kiểm tra phòng có được chọn không
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

    public static void main(String[] args) {
        new HopDongForm();
    }
}
