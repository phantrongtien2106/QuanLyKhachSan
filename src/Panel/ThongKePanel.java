package System.panel;

import System.bus.ThongKeBUS;
import System.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ThongKePanel extends JPanel {

    private ThongKeBUS thongKeBUS;
    private JTabbedPane tabbedPane;
    private JPanel doanhThuPanel, phongPanel, khachHangPanel, dichVuPanel, hoaDonPanel;
    private JDateChooser tuNgayChooser, denNgayChooser;
    private JTextField namTextField;
    private JTable doanhThuTable, phongTable, khachHangTable, dichVuTable, hoaDonTable;
    private JLabel tongDoanhThuLabel;

    public ThongKePanel() {
        thongKeBUS = new ThongKeBUS();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        
        // Tạo các panel thống kê
        createDoanhThuPanel();
        createPhongPanel();
        createKhachHangPanel();
        createDichVuPanel();
        createHoaDonPanel();
        
        // Thêm các panel vào tabbedPane
        tabbedPane.addTab("Thống Kê Doanh Thu", doanhThuPanel);
        tabbedPane.addTab("Thống Kê Phòng", phongPanel);
        tabbedPane.addTab("Thống Kê Khách Hàng", khachHangPanel);
        tabbedPane.addTab("Thống Kê Dịch Vụ", dichVuPanel);
        tabbedPane.addTab("Thống Kê Hóa Đơn", hoaDonPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void createDoanhThuPanel() {
        doanhThuPanel = new JPanel(new BorderLayout());
        
        // Panel chứa các điều khiển
        JPanel controlPanel = new JPanel(new GridLayout(2, 1));
        
        // Panel cho thống kê theo khoảng thời gian
        JPanel dateRangePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dateRangePanel.setBorder(BorderFactory.createTitledBorder("Thống kê theo khoảng thời gian"));
        
        dateRangePanel.add(new JLabel("Từ ngày:"));
        tuNgayChooser = new JDateChooser();
        tuNgayChooser.setDateFormatString("dd/MM/yyyy");
        dateRangePanel.add(tuNgayChooser);
        
        dateRangePanel.add(new JLabel("Đến ngày:"));
        denNgayChooser = new JDateChooser();
        denNgayChooser.setDateFormatString("dd/MM/yyyy");
        dateRangePanel.add(denNgayChooser);
        
        JButton thongKeButton = new JButton("Thống Kê");
        thongKeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thongKeDoanhThuTheoKhoangThoiGian();
            }
        });
        dateRangePanel.add(thongKeButton);
        
        controlPanel.add(dateRangePanel);
        
        // Panel cho thống kê theo năm
        JPanel yearPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        yearPanel.setBorder(BorderFactory.createTitledBorder("Thống kê theo năm"));
        
        yearPanel.add(new JLabel("Năm:"));
        namTextField = new JTextField(10);
        namTextField.setText(String.valueOf(LocalDate.now().getYear()));
        yearPanel.add(namTextField);
        
        JButton thongKeTheoNamButton = new JButton("Thống Kê Theo Năm");
        thongKeTheoNamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thongKeDoanhThuTheoNam();
            }
        });
        yearPanel.add(thongKeTheoNamButton);
        
        controlPanel.add(yearPanel);
        
        doanhThuPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Panel hiển thị kết quả
        JPanel resultPanel = new JPanel(new BorderLayout());
        
        // Bảng doanh thu
        String[] doanhThuColumns = {"Tháng", "Số lượng hóa đơn", "Doanh thu"};
        DefaultTableModel doanhThuModel = new DefaultTableModel(doanhThuColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        doanhThuTable = new JTable(doanhThuModel);
        JScrollPane scrollPane = new JScrollPane(doanhThuTable);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel hiển thị tổng doanh thu
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tongDoanhThuLabel = new JLabel("Tổng doanh thu: 0 VND");
        tongDoanhThuLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalPanel.add(tongDoanhThuLabel);
        resultPanel.add(totalPanel, BorderLayout.SOUTH);
        
        doanhThuPanel.add(resultPanel, BorderLayout.CENTER);
    }

    private void createPhongPanel() {
        phongPanel = new JPanel(new BorderLayout());
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton thongKeLoaiPhongButton = new JButton("Thống Kê Loại Phòng");
        thongKeLoaiPhongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thongKeLoaiPhong();
            }
        });
        controlPanel.add(thongKeLoaiPhongButton);
        
        JButton thongKeTinhTrangButton = new JButton("Thống Kê Tình Trạng Phòng");
        thongKeTinhTrangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thongKeTinhTrangPhong();
            }
        });
        controlPanel.add(thongKeTinhTrangButton);
        
        phongPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Bảng phòng
        String[] phongColumns = {"Loại phòng/Tình trạng", "Số lượng", "Tỷ lệ (%)"};
        DefaultTableModel phongModel = new DefaultTableModel(phongColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        phongTable = new JTable(phongModel);
        JScrollPane scrollPane = new JScrollPane(phongTable);
        phongPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createKhachHangPanel() {
        khachHangPanel = new JPanel(new BorderLayout());
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton thongKeTop5Button = new JButton("Thống Kê Top 5 Khách Hàng");
        thongKeTop5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thongKeTop5KhachHang();
            }
        });
        controlPanel.add(thongKeTop5Button);
        
        khachHangPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Bảng khách hàng
        String[] khachHangColumns = {"Mã KH", "Tên khách hàng", "CCCD", "Số điện thoại", "Số lần đặt phòng", "Tổng chi tiêu"};
        DefaultTableModel khachHangModel = new DefaultTableModel(khachHangColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        khachHangTable = new JTable(khachHangModel);
        JScrollPane scrollPane = new JScrollPane(khachHangTable);
        khachHangPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createDichVuPanel() {
        dichVuPanel = new JPanel(new BorderLayout());
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton thongKeDichVuButton = new JButton("Thống Kê Dịch Vụ");
        thongKeDichVuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thongKeDichVu();
            }
        });
        controlPanel.add(thongKeDichVuButton);
        
        dichVuPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Bảng dịch vụ
        String[] dichVuColumns = {"Tên dịch vụ", "Số lượt sử dụng", "Doanh thu", "Tỷ lệ sử dụng (%)"};
        DefaultTableModel dichVuModel = new DefaultTableModel(dichVuColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dichVuTable = new JTable(dichVuModel);
        JScrollPane scrollPane = new JScrollPane(dichVuTable);
        dichVuPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createHoaDonPanel() {
        hoaDonPanel = new JPanel(new BorderLayout());
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        controlPanel.add(new JLabel("Từ ngày:"));
        JDateChooser tuNgayHDChooser = new JDateChooser();
        tuNgayHDChooser.setDateFormatString("dd/MM/yyyy");
        controlPanel.add(tuNgayHDChooser);
        
        controlPanel.add(new JLabel("Đến ngày:"));
        JDateChooser denNgayHDChooser = new JDateChooser();
        denNgayHDChooser.setDateFormatString("dd/MM/yyyy");
        controlPanel.add(denNgayHDChooser);
        
        JButton thongKeHoaDonButton = new JButton("Thống Kê Hóa Đơn");
        thongKeHoaDonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tuNgayHDChooser.getDate() == null || denNgayHDChooser.getDate() == null) {
                    JOptionPane.showMessageDialog(ThongKePanel.this,
                            "Vui lòng chọn khoảng thời gian",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                LocalDate tuNgay = convertToLocalDate(tuNgayHDChooser.getDate());
                LocalDate denNgay = convertToLocalDate(denNgayHDChooser.getDate());
                
                thongKeHoaDon(tuNgay, denNgay);
            }
        });
        controlPanel.add(thongKeHoaDonButton);
        
        hoaDonPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Bảng hóa đơn
        String[] hoaDonColumns = {"Mã hóa đơn", "Tên khách hàng", "Ngày", "Tổng tiền", "Trạng thái"};
        DefaultTableModel hoaDonModel = new DefaultTableModel(hoaDonColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        hoaDonTable = new JTable(hoaDonModel);
        JScrollPane scrollPane = new JScrollPane(hoaDonTable);
        hoaDonPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void thongKeDoanhThuTheoKhoangThoiGian() {
        if (tuNgayChooser.getDate() == null || denNgayChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn khoảng thời gian",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LocalDate tuNgay = convertToLocalDate(tuNgayChooser.getDate());
        LocalDate denNgay = convertToLocalDate(denNgayChooser.getDate());
        
        TongDoanhThuThongKe thongKe = thongKeBUS.thongKeDoanhThu(tuNgay, denNgay);
        
        DefaultTableModel model = (DefaultTableModel) doanhThuTable.getModel();
        model.setRowCount(0);
        
        model.addRow(new Object[]{
                "Hóa đơn",
                thongKe.getSoLuongHoaDon(),
                formatCurrency(thongKe.getDoanhThuHoaDon())
        });
        
        model.addRow(new Object[]{
                "Hợp đồng",
                thongKe.getSoLuongHopDong(),
                formatCurrency(thongKe.getDoanhThuHopDong())
        });
        
        model.addRow(new Object[]{
                "Dịch vụ",
                thongKe.getSoLuongDichVu(),
                formatCurrency(thongKe.getDoanhThuDichVu())
        });
        
        model.addRow(new Object[]{
                "Phòng",
                "",
                formatCurrency(thongKe.getDoanhThuPhong())
        });
        
        tongDoanhThuLabel.setText("Tổng doanh thu: " + formatCurrency(thongKe.getTongDoanhThu()));
    }

    private void thongKeDoanhThuTheoNam() {
        try {
            int nam = Integer.parseInt(namTextField.getText().trim());
            
            List<DoanhThuThongKe> list = thongKeBUS.thongKeDoanhThuTheoThang(nam);
            
            DefaultTableModel model = (DefaultTableModel) doanhThuTable.getModel();
            model.setRowCount(0);
            
            double tongDoanhThu = 0;
            
            for (DoanhThuThongKe item : list) {
                model.addRow(new Object[]{
                        "Tháng " + item.getThang(),
                        item.getSoLuong(),
                        formatCurrency(item.getDoanhThu())
                });
                tongDoanhThu += item.getDoanhThu();
            }
            
            tongDoanhThuLabel.setText("Tổng doanh thu năm " + nam + ": " + formatCurrency(tongDoanhThu));
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Năm không hợp lệ. Vui lòng nhập số nguyên",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void thongKeLoaiPhong() {
        List<PhongThongKe> list = thongKeBUS.thongKeLoaiPhong();
        
        DefaultTableModel model = (DefaultTableModel) phongTable.getModel();
        model.setRowCount(0);
        
        for (PhongThongKe item : list) {
            model.addRow(new Object[]{
                    item.getLoaiPhong(),
                    item.getSoLuong(),
                    ""
            });
        }
    }

    private void thongKeTinhTrangPhong() {
        List<PhongThongKe> list = thongKeBUS.thongKeTinhTrangPhong();
        
        DefaultTableModel model = (DefaultTableModel) phongTable.getModel();
        model.setRowCount(0);
        
        for (PhongThongKe item : list) {
            model.addRow(new Object[]{
                    item.getTinhTrang(),
                    item.getSoLuong(),
                    String.format("%.2f", item.getTyLe())
            });
        }
    }

    private void thongKeTop5KhachHang() {
        List<KhachHangThongKe> list = thongKeBUS.thongKeTop5KhachHang();
        
        DefaultTableModel model = (DefaultTableModel) khachHangTable.getModel();
        model.setRowCount(0);
        
        for (KhachHangThongKe item : list) {
            model.addRow(new Object[]{
                    item.getMaKhachHang(),
                    item.getTenKhachHang(),
                    item.getCccd(),
                    item.getSoDienThoai(),
                    item.getSoLanDatPhong(),
                    formatCurrency(item.getTongTien())
            });
        }
    }

    private void thongKeDichVu() {
        List<DichVuThongKe> list = thongKeBUS.thongKeDichVu();
        
        DefaultTableModel model = (DefaultTableModel) dichVuTable.getModel();
        model.setRowCount(0);
        
        for (DichVuThongKe item : list) {
            model.addRow(new Object[]{
                    item.getTenDichVu(),
                    item.getSoLuotSuDung(),
                    formatCurrency(item.getDoanhThu()),
                    String.format("%.2f", item.getTyLeSuDung())
            });
        }
    }

    private void thongKeHoaDon(LocalDate tuNgay, LocalDate denNgay) {
        List<HoaDonThongKe> list = thongKeBUS.thongKeHoaDon(tuNgay, denNgay);
        
        DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
        model.setRowCount(0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (HoaDonThongKe item : list) {
            model.addRow(new Object[]{
                    item.getMaHoaDon(),
                    item.getTenKhachHang(),
                    item.getNgay().format(formatter),
                    formatCurrency(item.getTongTien()),
                    formatTrangThai(item.getTrangThai())
            });
        }
    }

    private LocalDate convertToLocalDate(java.util.Date date) {
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    private String formatCurrency(double amount) {
        return String.format("%,.0f VNĐ", amount);
    }

    private String formatTrangThai(String trangThai) {
        switch (trangThai) {
            case "da_dat":
                return "Đã đặt";
            case "dang_su_dung":
                return "Đang sử dụng";
            case "da_thanh_toan":
                return "Đã thanh toán";
            case "huy":
                return "Đã hủy";
            default:
                return trangThai;
        }
    }

    private static class JDateChooser extends JPanel {
        private JTextField dateTextField;
        private JButton dateButton;
        private String datePattern;
        private java.util.Date selectedDate;

        public JDateChooser() {
            setLayout(new BorderLayout());
            dateTextField = new JTextField(10);
            dateButton = new JButton("...");
            add(dateTextField, BorderLayout.CENTER);
            add(dateButton, BorderLayout.EAST);

            dateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog dialog = new JDialog();
                    dialog.setModal(true);
                    dialog.setTitle("Chọn ngày");
                    JPanel panel = new JPanel(new BorderLayout());
                    
                    // Tạo một calendar đơn giản (ví dụ đơn giản, trong thực tế nên dùng thư viện)
                    JPanel calendarPanel = new JPanel(new GridLayout(7, 7));
                    String[] dayNames = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
                    for (String day : dayNames) {
                        calendarPanel.add(new JLabel(day, SwingConstants.CENTER));
                    }
                    
                    LocalDate now = LocalDate.now();
                    int daysInMonth = now.lengthOfMonth();
                    int firstDayOfMonth = now.withDayOfMonth(1).getDayOfWeek().getValue();
                    if (firstDayOfMonth == 7) firstDayOfMonth = 0; // Sunday = 0
                    
                    for (int i = 0; i < firstDayOfMonth; i++) {
                        calendarPanel.add(new JLabel(""));
                    }
                    
                    for (int i = 1; i <= daysInMonth; i++) {
                        final int day = i;
                        JButton dayButton = new JButton(String.valueOf(i));
                        dayButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                LocalDate selectedDate = now.withDayOfMonth(day);
                                setDate(java.sql.Date.valueOf(selectedDate));
                                dialog.dispose();
                            }
                        });
                        calendarPanel.add(dayButton);
                    }
                    
                    panel.add(calendarPanel, BorderLayout.CENTER);
                    dialog.add(panel);
                    dialog.pack();
                    dialog.setLocationRelativeTo(JDateChooser.this);
                    dialog.setVisible(true);
                }
            });
        }

        public void setDateFormatString(String pattern) {
            this.datePattern = pattern;
        }

        public java.util.Date getDate() {
            return selectedDate;
        }

        public void setDate(java.util.Date date) {
            this.selectedDate = date;
            if (date != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
                LocalDate localDate = convertToLocalDate(date);
                dateTextField.setText(localDate.format(formatter));
            } else {
                dateTextField.setText("");
            }
        }

        private LocalDate convertToLocalDate(java.util.Date date) {
            return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        }
    }
} 