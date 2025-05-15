package System.gui;

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

public class ThongKeGUI extends JFrame {

    private ThongKeBUS thongKeBUS;
    private JTabbedPane tabbedPane;
    private JPanel doanhThuPanel, phongPanel, khachHangPanel, dichVuPanel, hoaDonPanel;
    private JDateChooser tuNgayChooser, denNgayChooser;
    private JTextField namTextField;
    private JTable doanhThuTable, phongTable, khachHangTable, dichVuTable, hoaDonTable;
    private JLabel tongDoanhThuLabel;

    public ThongKeGUI() {
        thongKeBUS = new ThongKeBUS();
        initComponents();
    }

    private void initComponents() {
        setTitle("Thống Kê Dữ Liệu");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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
                    JOptionPane.showMessageDialog(ThongKeGUI.this,
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
        List<HoaDonThongKe> dsHoaDon = thongKeBUS.thongKeHoaDon(tuNgay, denNgay);

        // Cập nhật model với các cột mới bổ sung
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã HĐ");
        model.addColumn("Khách hàng");
        model.addColumn("Ngày");
        model.addColumn("Tổng tiền");
        model.addColumn("Trạng thái");
        model.addColumn("Danh sách phòng");  // Cột mới
        model.addColumn("Mã phiếu");         // Cột mới
        model.addColumn("Mã hợp đồng");      // Cột mới

        // Tổng doanh thu từ hóa đơn
        double tongDoanhThu = 0;

        // Thêm dữ liệu vào model
        for (HoaDonThongKe hd : dsHoaDon) {
            model.addRow(new Object[]{
                    hd.getMaHoaDon(),
                    hd.getTenKhachHang(),
                    hd.getNgay().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    formatCurrency(hd.getTongTien()),
                    formatTrangThai(hd.getTrangThai()),
                    hd.getDanhSachPhong(),     // Thêm hiển thị danh sách phòng
                    hd.getMaPhieu(),           // Thêm hiển thị mã phiếu
                    hd.getMaHopDong()          // Thêm hiển thị mã hợp đồng
            });

            // Tính tổng doanh thu
            tongDoanhThu += hd.getTongTien();
        }

        // Cập nhật bảng với model mới
        hoaDonTable.setModel(model);

        // Cập nhật label hiển thị tổng doanh thu
        // Giả sử có một label để hiển thị tổng doanh thu
        // tongDoanhThuLabel.setText("Tổng doanh thu: " + formatCurrency(tongDoanhThu));
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
        private String datePattern = "dd/MM/yyyy"; // Định dạng mặc định
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

                    JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
                    mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                    // Hiển thị tháng và năm hiện tại
                    LocalDate currentDate = selectedDate != null ?
                            convertToLocalDate(selectedDate) : LocalDate.now();
                    final JLabel monthYearLabel = new JLabel(
                            currentDate.getMonth().toString() + " " + currentDate.getYear(),
                            SwingConstants.CENTER);
                    monthYearLabel.setFont(new Font(monthYearLabel.getFont().getName(), Font.BOLD, 14));

                    // Các nút điều hướng tháng
                    JPanel navigationPanel = new JPanel(new BorderLayout());
                    JButton prevButton = new JButton("<");
                    JButton nextButton = new JButton(">");
                    navigationPanel.add(prevButton, BorderLayout.WEST);
                    navigationPanel.add(monthYearLabel, BorderLayout.CENTER);
                    navigationPanel.add(nextButton, BorderLayout.EAST);

                    mainPanel.add(navigationPanel, BorderLayout.NORTH);

                    // Panel chứa lịch
                    final JPanel calendarPanel = new JPanel(new GridLayout(0, 7, 2, 2));

                    // Hiển thị tên các ngày trong tuần
                    String[] dayNames = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
                    for (String day : dayNames) {
                        JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
                        dayLabel.setFont(new Font(dayLabel.getFont().getName(), Font.BOLD, 12));
                        calendarPanel.add(dayLabel);
                    }

                    // Biến để theo dõi ngày đang hiển thị
                    final LocalDate[] displayDate = {currentDate};

                    // Hàm để cập nhật lịch
                    Runnable updateCalendar = new Runnable() {
                        @Override
                        public void run() {
                            // Xóa các nút ngày cũ
                            calendarPanel.removeAll();

                            // Thêm lại tên ngày
                            for (String day : dayNames) {
                                JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
                                dayLabel.setFont(new Font(dayLabel.getFont().getName(), Font.BOLD, 12));
                                calendarPanel.add(dayLabel);
                            }

                            // Cập nhật title tháng/năm
                            monthYearLabel.setText(
                                    displayDate[0].getMonth().toString() + " " + displayDate[0].getYear());

                            // Tính toán ngày đầu tiên của tháng
                            LocalDate firstOfMonth = displayDate[0].withDayOfMonth(1);
                            int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
                            if (dayOfWeek == 7) dayOfWeek = 0; // Chuyển chủ nhật từ 7 sang 0

                            // Thêm các ô trống cho ngày đầu tháng
                            for (int i = 0; i < dayOfWeek; i++) {
                                calendarPanel.add(new JLabel(""));
                            }

                            // Thêm nút cho từng ngày trong tháng
                            int daysInMonth = displayDate[0].lengthOfMonth();
                            for (int i = 1; i <= daysInMonth; i++) {
                                final int day = i;
                                JButton dayButton = new JButton(String.valueOf(i));

                                // Làm nổi bật ngày hiện tại
                                if (LocalDate.now().equals(
                                        displayDate[0].withDayOfMonth(i))) {
                                    dayButton.setBackground(new Color(173, 216, 230));
                                    dayButton.setForeground(Color.BLACK);
                                }

                                dayButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        LocalDate selectedLocalDate = displayDate[0].withDayOfMonth(day);

                                        // Chuyển đổi LocalDate sang java.util.Date thay vì java.sql.Date
                                        java.util.Date utilDate = java.util.Date.from(
                                                selectedLocalDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());

                                        setDate(utilDate);
                                        dialog.dispose();
                                    }
                                });
                                calendarPanel.add(dayButton);
                            }

                            calendarPanel.revalidate();
                            calendarPanel.repaint();
                        }
                    };

                    // Xử lý sự kiện cho nút tháng trước
                    prevButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            displayDate[0] = displayDate[0].minusMonths(1);
                            updateCalendar.run();
                        }
                    });

                    // Xử lý sự kiện cho nút tháng sau
                    nextButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            displayDate[0] = displayDate[0].plusMonths(1);
                            updateCalendar.run();
                        }
                    });

                    // Tạo lịch ban đầu
                    updateCalendar.run();

                    mainPanel.add(calendarPanel, BorderLayout.CENTER);

                    // Thêm nút Hôm nay và Đóng
                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    JButton todayButton = new JButton("Hôm nay");
                    JButton closeButton = new JButton("Đóng");

                    todayButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            displayDate[0] = LocalDate.now();
                            updateCalendar.run();
                        }
                    });

                    closeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialog.dispose();
                        }
                    });

                    buttonPanel.add(todayButton);
                    buttonPanel.add(closeButton);
                    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

                    dialog.add(mainPanel);
                    dialog.pack();
                    dialog.setLocationRelativeTo(JDateChooser.this);
                    dialog.setVisible(true);
                }
            });
        }

        public void setDateFormatString(String pattern) {
            this.datePattern = pattern;
            // Cập nhật lại text nếu đã có ngày được chọn
            if (selectedDate != null) {
                setDate(selectedDate);
            }
        }

        public java.util.Date getDate() {
            return selectedDate;
        }

        public void setDate(java.util.Date date) {
            this.selectedDate = date;
            if (date != null) {
                LocalDate localDate = convertToLocalDate(date);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
                dateTextField.setText(localDate.format(formatter));
            } else {
                dateTextField.setText("");
            }
        }

        private LocalDate convertToLocalDate(java.util.Date date) {
            if (date instanceof java.sql.Date) {
                // Sử dụng phương thức toLocalDate() cho java.sql.Date
                return ((java.sql.Date) date).toLocalDate();
            } else {
                // Đối với java.util.Date thông thường
                return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ThongKeGUI().setVisible(true);
            }
        });
    }
} 