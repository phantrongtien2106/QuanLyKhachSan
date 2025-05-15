package gui;

import bus.*;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent; // Thêm import này
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import bus.DichVuBUS;
import model.DichVu;
import model.ChiTietDichVu;

class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
    private JSpinner spinner;

    public SpinnerEditor(int value, int min, int max, int step) {
        spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
    }

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                               int row, int column) {
        spinner.setValue(value);
        return spinner;
    }
}

public class CheckInForm extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(0x4682B4);
    private static final Color BACKGROUND_COLOR = new Color(0xF5F5F5);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private DichVuBUS dichVuBUS = new DichVuBUS();
    private ChiTietDichVuBUS chiTietDichVuBUS = new ChiTietDichVuBUS();

    private JTable tablePhieu, tableHopDong, tablePhong;
    private DefaultTableModel modelPhieu, modelHopDong, modelPhong;
    private JComboBox<String> cboReason;
    private JTabbedPane tabbedPane;
    private JPanel phongPanel;
    private JLabel lblThongTinChiTiet;

    private PhieuDatPhongBUS pdpBUS = new PhieuDatPhongBUS();
    private HopDongBUS hopDongBUS = new HopDongBUS();
    private CheckInBUS checkInBUS = new CheckInBUS();
    private ChiTietPhieuDatPhongBUS ctpdpBUS = new ChiTietPhieuDatPhongBUS();
    private ChiTietHopDongBUS cthdBUS = new ChiTietHopDongBUS();
    private PhongBUS phongBUS = new PhongBUS();

    public CheckInForm() {
        setTitle("Quản lý Check-In");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("QUẢN LÝ CHECK-IN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR.darker());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        // Panel chính với tabs và thông tin phòng
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        // Tabs cho phiếu và hợp đồng
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(LABEL_FONT);
        tabbedPane.add("Phiếu đặt phòng", createPhieuPanel());
        tabbedPane.add("Hợp đồng thuê", createHopDongPanel());
        tabbedPane.addChangeListener(e -> hieuThiThongTinChiTiet());

        // Panel hiển thị phòng
        phongPanel = createPhongPanel();

        // Chia main panel thành 2 phần
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, phongPanel);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.6);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Lý do check-in và nút
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        JPanel reasonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        reasonPanel.setBackground(BACKGROUND_COLOR);
        JLabel lblReason = new JLabel("Lý do check-in:");
        lblReason.setFont(LABEL_FONT);
        reasonPanel.add(lblReason);

        cboReason = new JComboBox<>(new String[]{"Check-in theo lịch", "Khách đến sớm", "Check-in sớm theo yêu cầu", "Khác"});
        cboReason.setEditable(true);
        cboReason.setPreferredSize(new Dimension(250, 30));
        reasonPanel.add(cboReason);

        JButton btnCheckIn = createStyledButton("Thực hiện Check-in");
        btnCheckIn.addActionListener(e -> thucHienCheckIn());

        JButton btnRefresh = createStyledButton("Làm mới");
        btnRefresh.setPreferredSize(new Dimension(120, 35));
        btnRefresh.addActionListener(e -> lamMoi());

        // Tạo nút thêm dịch vụ
        JButton btnThemDichVu = createStyledButton("Thêm dịch vụ");
        btnThemDichVu.setPreferredSize(new Dimension(150, 35));
        btnThemDichVu.addActionListener(e -> {
            int selectedTab = tabbedPane.getSelectedIndex();
            if (selectedTab == 0) {
                int selectedRow = tablePhieu.getSelectedRow();
                if (selectedRow >= 0) {
                    String maPhieu = tablePhieu.getValueAt(selectedRow, 0).toString();
                    themDichVuVaoPhieu(maPhieu);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Vui lòng chọn phiếu để thêm dịch vụ",
                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Chức năng thêm dịch vụ hiện chỉ áp dụng cho phiếu đặt phòng",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(BACKGROUND_COLOR);
        btnPanel.add(btnThemDichVu);  // Thêm nút dịch vụ vào panel
        btnPanel.add(btnRefresh);
        btnPanel.add(btnCheckIn);

        bottomPanel.add(reasonPanel, BorderLayout.WEST);
        bottomPanel.add(btnPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);


        // Load dữ liệu ban đầu
        loadPhieuChuaCheckIn();
        loadHopDongChuaCheckIn();

        setVisible(true);
    }

    private JPanel createPhieuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(BACKGROUND_COLOR);
        JLabel lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField txtSearch = new JTextField(20);
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemPhieu(txtSearch.getText().trim());
            }
        });
        JButton btnSearch = createStyledButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(80, 30));
        btnSearch.addActionListener(e -> timKiemPhieu(txtSearch.getText().trim()));

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Bảng phiếu đặt phòng
        modelPhieu = new DefaultTableModel(
                new String[]{"Mã phiếu", "Mã KH", "Ngày nhận", "Ngày trả", "Trạng thái", "Số phòng"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePhieu = new JTable(modelPhieu);
        tablePhieu.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setupTable(tablePhieu);

        // Sự kiện khi chọn phiếu
        tablePhieu.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablePhieu.getSelectedRow() >= 0) {
                hienThiPhongTheoPhieu();
            }
        });

        JScrollPane scroll = new JScrollPane(tablePhieu);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createHopDongPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(BACKGROUND_COLOR);
        JLabel lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField txtSearch = new JTextField(20);
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemHopDong(txtSearch.getText().trim());
            }
        });
        JButton btnSearch = createStyledButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(80, 30));
        btnSearch.addActionListener(e -> timKiemHopDong(txtSearch.getText().trim()));

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Bảng hợp đồng
        modelHopDong = new DefaultTableModel(
                new String[]{"Mã HĐ", "Mã KH", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái", "Số phòng"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableHopDong = new JTable(modelHopDong);
        tableHopDong.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setupTable(tableHopDong);

        // Sự kiện khi chọn hợp đồng
        tableHopDong.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableHopDong.getSelectedRow() >= 0) {
                hienThiPhongTheoHopDong();
            }
        });

        JScrollPane scroll = new JScrollPane(tableHopDong);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPhongPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                "Danh sách phòng",
                0,
                0,
                LABEL_FONT,
                PRIMARY_COLOR.darker()
        ));

        // Label hiển thị thông tin chi tiết
        lblThongTinChiTiet = new JLabel("Chọn một phiếu hoặc hợp đồng để xem phòng");
        lblThongTinChiTiet.setFont(new Font("Arial", Font.ITALIC, 12));
        panel.add(lblThongTinChiTiet, BorderLayout.NORTH);

        // Bảng phòng
        modelPhong = new DefaultTableModel(
                new String[]{"Mã phòng", "Loại phòng", "Giá", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePhong = new JTable(modelPhong);
        setupTable(tablePhong);

        JScrollPane scroll = new JScrollPane(tablePhong);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);  // Quan trọng: đảm bảo màu nền được vẽ
        btn.setBorderPainted(false);

        // Override UI để đảm bảo màu nền luôn được hiển thị
        btn.putClientProperty("Nimbus.Overrides", Boolean.TRUE);
        btn.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.FALSE);

        btn.setPreferredSize(new Dimension(150, 35));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(PRIMARY_COLOR.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(PRIMARY_COLOR);
            }
        });

        return btn;
    }
    private void setupTable(JTable table) {
        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(PRIMARY_COLOR);
        table.setSelectionForeground(TEXT_COLOR);
        table.setBackground(Color.WHITE);

        // Tạo header đẹp
        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setFont(new Font("Arial", Font.BOLD, 12));

        // Chỉnh header renderer để đảm bảo màu nền và chữ
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setForeground(TEXT_COLOR);
        headerRenderer.setBackground(PRIMARY_COLOR);

        // Căn giữa cho tất cả các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void loadPhieuChuaCheckIn() {
        modelPhieu.setRowCount(0);
        for (PhieuDatPhong p : pdpBUS.getPhieuChuaCheckIn()) {
            // Lấy số phòng của phiếu
            List<String> dsPhong = ctpdpBUS.getPhongByMaPhieu(p.getMaPhieu());

            modelPhieu.addRow(new Object[]{
                    p.getMaPhieu(),
                    p.getMaKhachHang(),
                    p.getNgayNhan(),
                    p.getNgayTra(),
                    p.getTrangThai(),
                    dsPhong.size()
            });
        }
    }

    private void loadHopDongChuaCheckIn() {
        modelHopDong.setRowCount(0);
        for (HopDong h : hopDongBUS.getHopDongChuaCheckIn()) {
            // Lấy số phòng của hợp đồng
            List<String> dsPhong = cthdBUS.getPhongByMaHopDong(h.getMaHopDong());

            modelHopDong.addRow(new Object[]{
                    h.getMaHopDong(),
                    h.getMaKhachHang(),
                    h.getNgayBatDau(),
                    h.getNgayKetThuc(),
                    h.getTrangThai(),
                    dsPhong.size()
            });
        }
    }

    private void timKiemPhieu(String keyword) {
        modelPhieu.setRowCount(0);
        List<PhieuDatPhong> list = pdpBUS.timKiemPhieuChuaCheckIn(keyword);
        for (PhieuDatPhong p : list) {
            List<String> dsPhong = ctpdpBUS.getPhongByMaPhieu(p.getMaPhieu());
            modelPhieu.addRow(new Object[]{
                    p.getMaPhieu(),
                    p.getMaKhachHang(),
                    p.getNgayNhan(),
                    p.getNgayTra(),
                    p.getTrangThai(),
                    dsPhong.size()
            });
        }
    }

    private void timKiemHopDong(String keyword) {
        modelHopDong.setRowCount(0);
        List<HopDong> list = hopDongBUS.timKiemHopDong(keyword);
        for (HopDong h : list) {
            // Chỉ hiển thị hợp đồng chưa check-in
            if (h.getTrangThai() != null && h.getTrangThai().equals("dang_dat")) {
                List<String> dsPhong = cthdBUS.getPhongByMaHopDong(h.getMaHopDong());
                modelHopDong.addRow(new Object[]{
                        h.getMaHopDong(),
                        h.getMaKhachHang(),
                        h.getNgayBatDau(),
                        h.getNgayKetThuc(),
                        h.getTrangThai(),
                        dsPhong.size()
                });
            }
        }
    }

    private void hienThiPhongTheoPhieu() {
        int selectedRow = tablePhieu.getSelectedRow();
        if (selectedRow < 0) return;

        String maPhieu = tablePhieu.getValueAt(selectedRow, 0).toString();
        String maKH = tablePhieu.getValueAt(selectedRow, 1).toString();
        String ngayNhan = tablePhieu.getValueAt(selectedRow, 2).toString();
        String ngayTra = tablePhieu.getValueAt(selectedRow, 3).toString();

        lblThongTinChiTiet.setText(
                "Phiếu đặt phòng: " + maPhieu + " | " +
                        "Khách hàng: " + maKH + " | " +
                        "Từ " + ngayNhan + " đến " + ngayTra
        );

        // Hiển thị phòng
        modelPhong.setRowCount(0);
        List<String> dsPhong = ctpdpBUS.getPhongByMaPhieu(maPhieu);
        for (String maPhong : dsPhong) {
            Phong p = phongBUS.getPhongByMa(maPhong);
            if (p != null) {
                modelPhong.addRow(new Object[]{
                        p.getMaPhong(),
                        p.getTenLoai(),
                        String.format("%,.0f VND", p.getGia()),
                        p.getTinhTrang()
                });
            }
        }
    }

    private void hienThiPhongTheoHopDong() {
        int selectedRow = tableHopDong.getSelectedRow();
        if (selectedRow < 0) return;

        String maHD = tableHopDong.getValueAt(selectedRow, 0).toString();
        String maKH = tableHopDong.getValueAt(selectedRow, 1).toString();
        String ngayBD = tableHopDong.getValueAt(selectedRow, 2).toString();
        String ngayKT = tableHopDong.getValueAt(selectedRow, 3).toString();

        lblThongTinChiTiet.setText(
                "Hợp đồng: " + maHD + " | " +
                        "Khách hàng: " + maKH + " | " +
                        "Từ " + ngayBD + " đến " + ngayKT
        );

        // Hiển thị phòng
        modelPhong.setRowCount(0);
        List<String> dsPhong = cthdBUS.getPhongByMaHopDong(maHD);
        for (String maPhong : dsPhong) {
            Phong p = phongBUS.getPhongByMa(maPhong);
            if (p != null) {
                modelPhong.addRow(new Object[]{
                        p.getMaPhong(),
                        p.getTenLoai(),
                        String.format("%,.0f VND", p.getGia()),
                        p.getTinhTrang()
                });
            }
        }
    }

    private void hieuThiThongTinChiTiet() {
        modelPhong.setRowCount(0);
        lblThongTinChiTiet.setText("Chọn một phiếu hoặc hợp đồng để xem phòng");

        if (tabbedPane.getSelectedIndex() == 0) {
            if (tablePhieu.getSelectedRow() >= 0) {
                hienThiPhongTheoPhieu();
            }
        } else {
            if (tableHopDong.getSelectedRow() >= 0) {
                hienThiPhongTheoHopDong();
            }
        }
    }

private void themDichVuVaoPhieu(String maPhieu) {
        JPanel dichVuPanel = new JPanel(new BorderLayout(10, 10));
        dichVuPanel.setBackground(BACKGROUND_COLOR);

        // Bảng dịch vụ hiện có
        DefaultTableModel modelDichVu = new DefaultTableModel(
                new String[]{"Mã DV", "Tên dịch vụ", "Đơn giá", "Số lượng", "Thành tiền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Chỉ cho phép sửa cột số lượng
            }
        };

        JTable tableDichVu = new JTable(modelDichVu);
        setupTable(tableDichVu);

        // Thêm SpinnerEditor cho cột số lượng
        tableDichVu.getColumnModel().getColumn(3).setCellEditor(
                new SpinnerEditor(1, 1, 100, 1));

        // Panel chọn dịch vụ
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(BACKGROUND_COLOR);

        JComboBox<DichVu> cboDichVu = new JComboBox<>();
        cboDichVu.setPreferredSize(new Dimension(250, 30));

        // Lấy danh sách dịch vụ từ database
        List<DichVu> dsDichVu = dichVuBUS.getAllDichVu();

        for (DichVu dv : dsDichVu) {
            cboDichVu.addItem(dv);
        }

        JButton btnThem = createStyledButton("Thêm dịch vụ");
        btnThem.setPreferredSize(new Dimension(150, 30));

        topPanel.add(new JLabel("Chọn dịch vụ:"));
        topPanel.add(cboDichVu);
        topPanel.add(btnThem);

        // Thêm dịch vụ khi nhấn nút
        btnThem.addActionListener(e -> {
            DichVu selectedDV = (DichVu) cboDichVu.getSelectedItem();
            if (selectedDV != null) {
                // Kiểm tra đã có dịch vụ này chưa
                boolean exists = false;
                for (int i = 0; i < modelDichVu.getRowCount(); i++) {
                    if (modelDichVu.getValueAt(i, 0).equals(selectedDV.getMaDv())) {
                        // Tăng số lượng
                        int soLuong = (int) modelDichVu.getValueAt(i, 3) + 1;
                        modelDichVu.setValueAt(soLuong, i, 3);
                        double thanhTien = soLuong * selectedDV.getGia();
                        modelDichVu.setValueAt(String.format("%,.0f VND", thanhTien), i, 4);
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    modelDichVu.addRow(new Object[]{
                        selectedDV.getMaDv(),
                        selectedDV.getTenDv(),
                        String.format("%,.0f VND", selectedDV.getGia()),
                        1,
                        String.format("%,.0f VND", selectedDV.getGia())
                    });
                }
            }
        });

        // Cập nhật thành tiền khi thay đổi số lượng
        // Thêm import: import javax.swing.event.TableModelEvent;
        tableDichVu.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
                int row = e.getFirstRow();
                int soLuong = (int) modelDichVu.getValueAt(row, 3);

                // Lấy đơn giá từ cột đơn giá, cần parse chuỗi VND
                String donGiaStr = modelDichVu.getValueAt(row, 2).toString();
                double donGia = Double.parseDouble(donGiaStr.replaceAll("[^\\d]", ""));

                // Cập nhật thành tiền
                double thanhTien = soLuong * donGia;
                modelDichVu.setValueAt(String.format("%,.0f VND", thanhTien), row, 4);
            }
        });

        // Panel lưu dịch vụ
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        bottomPanel.setBackground(BACKGROUND_COLOR);

        JButton btnLuu = createStyledButton("Lưu dịch vụ");
        JButton btnHuy = createStyledButton("Hủy");

        bottomPanel.add(btnLuu);
        bottomPanel.add(btnHuy);

        btnLuu.addActionListener(e -> {
            try {
                // Lưu dịch vụ vào database
                for (int i = 0; i < modelDichVu.getRowCount(); i++) {
                    String maDV = modelDichVu.getValueAt(i, 0).toString();
                    int soLuong = (int) modelDichVu.getValueAt(i, 3);

                    // Tạo đối tượng ChiTietDichVu với constructor đầy đủ tham số
                    ChiTietDichVu ctdv = new ChiTietDichVu(maPhieu, maDV, soLuong);

                    // Gọi BUS để lưu
                    chiTietDichVuBUS.themChiTietDichVu(ctdv);
                }

                JOptionPane.showMessageDialog(null,
                    "Đã thêm dịch vụ thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

                // Đóng dialog
                ((Window) dichVuPanel.getTopLevelAncestor()).dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                    "Lỗi khi lưu dịch vụ: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnHuy.addActionListener(e -> {
            ((Window) dichVuPanel.getTopLevelAncestor()).dispose();
        });

        dichVuPanel.add(topPanel, BorderLayout.NORTH);
        dichVuPanel.add(new JScrollPane(tableDichVu), BorderLayout.CENTER);
        dichVuPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Hiển thị dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm dịch vụ cho phiếu " + maPhieu);
        dialog.setModal(true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());
        dialog.add(dichVuPanel);
        dialog.setVisible(true);
    }
    private void lamMoi() {
        loadPhieuChuaCheckIn();
        loadHopDongChuaCheckIn();
        modelPhong.setRowCount(0);
        lblThongTinChiTiet.setText("Chọn một phiếu hoặc hợp đồng để xem phòng");
    }

    private void thucHienCheckIn() {
        // Lấy tab hiện tại
        int selectedTab = tabbedPane.getSelectedIndex();
        if (selectedTab == 0) {
            thucHienCheckInPhieu();
        } else {
            thucHienCheckInHopDong();
        }
    }

    private void thucHienCheckInPhieu() {
        int[] rows = tablePhieu.getSelectedRows();
        if (rows.length == 0) {
            showError("Chọn ít nhất một phiếu để check-in!");
            return;
        }

        if (!validateInput()) return;

        // Xác nhận check-in nhiều phiếu
        if (rows.length > 1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn đã chọn " + rows.length + " phiếu. Xác nhận check-in tất cả?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
        }

        String reason = cboReason.getSelectedItem().toString();
        String nhanVien = "NV001"; // TODO: Lấy mã nhân viên đăng nhập

        List<String> successList = new ArrayList<>();
        List<String> earlyCheckInList = new ArrayList<>();
        List<String> failList = new ArrayList<>();

        for (int row : rows) {
            String maPhieu = modelPhieu.getValueAt(row, 0).toString();
            String ngayNhan = modelPhieu.getValueAt(row, 2).toString();

            // Kiểm tra check-in sớm
            LocalDate ngayNhanDate = LocalDate.parse(ngayNhan);
            if (ngayNhanDate.isAfter(LocalDate.now())) {
                earlyCheckInList.add(maPhieu + " (ngày " + ngayNhan + ")");
                continue;
            }

            // Thực hiện check-in
            if (thucHienCheckInPhieuDon(maPhieu, reason, nhanVien)) {
                successList.add(maPhieu);
            } else {
                failList.add(maPhieu);
            }
        }

        // Xử lý các phiếu check-in sớm
        if (!earlyCheckInList.isEmpty()) {
            StringBuilder sb = new StringBuilder("Các phiếu check-in sớm:\n");
            for (String item : earlyCheckInList) {
                sb.append("- ").append(item).append("\n");
            }
            sb.append("\nBạn có muốn tiếp tục check-in cho những phiếu này không?");

            int confirm = JOptionPane.showConfirmDialog(this,
                    sb.toString(), "Check-in sớm", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Thực hiện check-in cho các phiếu sớm
                for (String item : earlyCheckInList) {
                    String maPhieu = item.split(" ")[0];
                    if (thucHienCheckInPhieuDon(maPhieu, reason + " (check-in sớm)", nhanVien)) {
                        successList.add(maPhieu);
                    } else {
                        failList.add(maPhieu);
                    }
                }
            }
        }

        // Hiển thị kết quả
        hienThiKetQuaCheckIn(successList, failList);

        // Làm mới dữ liệu
        loadPhieuChuaCheckIn();
    }

    private boolean thucHienCheckInPhieuDon(String maPhieu, String reason, String nhanVien) {
        try {
            // Tạo check-in record
            String maCheckIn = "CI" + System.currentTimeMillis() % 1000000;
            CheckIn checkin = new CheckIn(maCheckIn, maPhieu,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    nhanVien, reason);

            if (checkInBUS.themCheckIn(checkin)) {
                if (pdpBUS.capNhatTrangThai(maPhieu, "dang_su_dung")) {
                    // Cập nhật trạng thái phòng
                    List<String> dsPhong = ctpdpBUS.getPhongByMaPhieu(maPhieu);
                    for (String maPhong : dsPhong) {
                        phongBUS.capNhatTinhTrang(maPhong, "Đang sử dụng");
                    }
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void thucHienCheckInHopDong() {
        int[] rows = tableHopDong.getSelectedRows();
        if (rows.length == 0) {
            showError("Chọn ít nhất một hợp đồng để check-in!");
            return;
        }

        if (!validateInput()) return;

        // Xác nhận check-in nhiều hợp đồng
        if (rows.length > 1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn đã chọn " + rows.length + " hợp đồng. Xác nhận check-in tất cả?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
        }

        String reason = cboReason.getSelectedItem().toString();
        String nhanVien = "NV001"; // TODO: Lấy mã nhân viên đăng nhập

        List<String> successList = new ArrayList<>();
        List<String> earlyCheckInList = new ArrayList<>();
        List<String> failList = new ArrayList<>();

        for (int row : rows) {
            String maHD = modelHopDong.getValueAt(row, 0).toString();
            String ngayBD = modelHopDong.getValueAt(row, 2).toString();

            // Kiểm tra check-in sớm
            LocalDate ngayBDDate = LocalDate.parse(ngayBD);
            if (ngayBDDate.isAfter(LocalDate.now())) {
                earlyCheckInList.add(maHD + " (ngày " + ngayBD + ")");
                continue;
            }

            // Thực hiện check-in
            if (thucHienCheckInHopDongDon(maHD, reason, nhanVien)) {
                successList.add(maHD);
            } else {
                failList.add(maHD);
            }
        }

        // Xử lý các hợp đồng check-in sớm
        if (!earlyCheckInList.isEmpty()) {
            StringBuilder sb = new StringBuilder("Các hợp đồng check-in sớm:\n");
            for (String item : earlyCheckInList) {
                sb.append("- ").append(item).append("\n");
            }
            sb.append("\nBạn có muốn tiếp tục check-in cho những hợp đồng này không?");

            int confirm = JOptionPane.showConfirmDialog(this,
                    sb.toString(), "Check-in sớm", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Thực hiện check-in cho các hợp đồng sớm
                for (String item : earlyCheckInList) {
                    String maHD = item.split(" ")[0];
                    if (thucHienCheckInHopDongDon(maHD, reason + " (check-in sớm)", nhanVien)) {
                        successList.add(maHD);
                    } else {
                        failList.add(maHD);
                    }
                }
            }
        }

        // Hiển thị kết quả
        hienThiKetQuaCheckIn(successList, failList);

        // Làm mới dữ liệu
        loadHopDongChuaCheckIn();
    }

    private boolean thucHienCheckInHopDongDon(String maHD, String reason, String nhanVien) {
        try {
            // Tạo check-in record
            String maCheckIn = "CI" + System.currentTimeMillis() % 1000000;
            CheckIn checkin = new CheckIn(maCheckIn, maHD,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    nhanVien, reason + " (hợp đồng)");

            if (checkInBUS.themCheckIn(checkin)) {
                if (hopDongBUS.capNhatTrangThai(maHD, "dang_su_dung")) {
                    // Cập nhật trạng thái phòng
                    List<String> dsPhong = cthdBUS.getPhongByMaHopDong(maHD);
                    boolean success = true;
                    for (String maPhong : dsPhong) {
                        if (!phongBUS.capNhatTinhTrang(maPhong, "Đang sử dụng")) {
                            success = false;
                        }
                    }
                    return success;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void hienThiKetQuaCheckIn(List<String> successList, List<String> failList) {
        StringBuilder message = new StringBuilder("Kết quả check-in:\n");
        if (!successList.isEmpty()) {
            message.append("Thành công: ").append(successList.size()).append(" phiếu/hợp đồng\n");
            for (String id : successList) {
                message.append("- ").append(id).append("\n");
            }
        }
        if (!failList.isEmpty()) {
            message.append("\nThất bại: ").append(failList.size()).append(" phiếu/hợp đồng\n");
            for (String id : failList) {
                message.append("- ").append(id).append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, message.toString(),
                successList.isEmpty() ? "Thất bại" : "Thành công",
                successList.isEmpty() ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private boolean validateInput() {
        String reason = cboReason.getSelectedItem().toString().trim();
        if (reason.isEmpty()) {
            showError("Vui lòng nhập lý do check-in!");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            // Set Nimbus Look and Feel với màu tùy chỉnh
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());

                    // Thiết lập màu xanh 4682B4 cho các thành phần UI
                    Color blueColor = new Color(0x4682B4);
                    UIManager.put("nimbusBase", blueColor);
                    UIManager.put("nimbusBlueGrey", blueColor);
                    UIManager.put("control", new Color(0xF5F5F5));

                    // Đảm bảo màu cho button và header
                    UIManager.put("Button.background", blueColor);
                    UIManager.put("Button.foreground", Color.WHITE);
                    UIManager.put("TableHeader.background", blueColor);
                    UIManager.put("TableHeader.foreground", Color.WHITE);
                    break;
                }
            }
        } catch (Exception ex) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Create and display the form
        java.awt.EventQueue.invokeLater(() -> {
            new CheckInForm().setVisible(true);
        });
    }
}
