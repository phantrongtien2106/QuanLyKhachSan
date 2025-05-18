package Panel;

    import bus.PhongBUS;
    import model.Phong;

    import javax.swing.*;
    import javax.swing.border.EmptyBorder;
    import javax.swing.table.DefaultTableCellRenderer;
    import javax.swing.table.DefaultTableModel;
    import javax.swing.table.JTableHeader;
    import java.awt.*;
    import java.awt.event.*;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class PhongPanel extends JPanel {
        // UI Constants
        private static final Color PRIMARY_COLOR = Color.decode("#4682B4");
        private static final Color BACKGROUND_COLOR = Color.decode("#DFF6FF");
        private static final Color TEXT_COLOR = Color.WHITE;
        private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 13);
        private static final int PADDING = 10;

        private JTable table;
        private DefaultTableModel model;
        private JTextField txtMaPhong;
        private JComboBox<String> cboLoai;
        private JComboBox<String> cboTinhTrang;
        private PhongBUS phongBUS = new PhongBUS();

        private Map<String, String> loaiMap = new HashMap<>();

        public PhongPanel() {
            setLayout(new BorderLayout(PADDING, PADDING));
            setBackground(BACKGROUND_COLOR);
            setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

            // Panel tiêu đề
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setBackground(PRIMARY_COLOR);
            titlePanel.setPreferredSize(new Dimension(getWidth(), 50));

            JLabel titleLabel = new JLabel("QUẢN LÝ PHÒNG", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            titleLabel.setForeground(TEXT_COLOR);
            titlePanel.add(titleLabel, BorderLayout.CENTER);

            // Panel tìm kiếm
            JPanel searchPanel = createSearchPanel();

            // Panel kết hợp header và search
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.add(titlePanel, BorderLayout.NORTH);
            topPanel.add(searchPanel, BorderLayout.CENTER);
            add(topPanel, BorderLayout.NORTH);

            // Table
            JPanel tablePanel = createTablePanel();
            add(tablePanel, BorderLayout.CENTER);

            // Form input
            JPanel southPanel = createInputPanel();
            add(southPanel, BorderLayout.SOUTH);

            // Load data
            loadLoaiPhong();
            loadPhong();
        }

        private JPanel createSearchPanel() {
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, PADDING, PADDING));
            searchPanel.setBackground(BACKGROUND_COLOR);
            searchPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, PRIMARY_COLOR),
                    new EmptyBorder(5, 0, 10, 0)
            ));

            JLabel lblSearch = new JLabel("Tìm theo tình trạng:");
            lblSearch.setFont(LABEL_FONT);

            JComboBox<String> cboSearchStatus = new JComboBox<>(new String[]{
                    "Tất cả", "Trống", "Đang đặt", "Đang sử dụng", "Bảo trì"
            });
            cboSearchStatus.setPreferredSize(new Dimension(150, 30));

            JButton btnSearch = createStyledButton("Tìm kiếm");
            JButton btnRefresh = createStyledButton("Làm mới");

            // Add event listeners
            btnSearch.addActionListener(e -> searchByStatus((String) cboSearchStatus.getSelectedItem()));
            btnRefresh.addActionListener(e -> {
                cboSearchStatus.setSelectedItem("Tất cả");
                loadPhong();
            });

            searchPanel.add(lblSearch);
            searchPanel.add(cboSearchStatus);
            searchPanel.add(btnSearch);
            searchPanel.add(btnRefresh);

            return searchPanel;
        }

        private JPanel createTablePanel() {
            JPanel tablePanel = new JPanel(new BorderLayout());
            tablePanel.setBackground(BACKGROUND_COLOR);

            // Create table model with columns
            model = new DefaultTableModel(new String[]{"Mã phòng", "Tên loại", "Giá/ngày", "Tình trạng"}, 0);
            table = new JTable(model);
            table.setRowHeight(25);
            table.setSelectionBackground(PRIMARY_COLOR);
            table.setSelectionForeground(TEXT_COLOR);
            table.setGridColor(Color.LIGHT_GRAY);

            JTableHeader header = table.getTableHeader();
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
                    label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.LIGHT_GRAY));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    return label;
                }
            });
            // Set header style
            table.getTableHeader().setBackground(PRIMARY_COLOR);
            table.getTableHeader().setFont(LABEL_FONT);
            table.getTableHeader().setForeground(TEXT_COLOR);

            // Add selection listener
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        txtMaPhong.setText(model.getValueAt(row, 0).toString());
                        txtMaPhong.setEditable(false);
                        cboLoai.setSelectedItem(model.getValueAt(row, 1).toString());
                        cboTinhTrang.setSelectedItem(model.getValueAt(row, 3).toString());
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
            tablePanel.add(scrollPane, BorderLayout.CENTER);

            return tablePanel;
        }

        private JPanel createInputPanel() {
            JPanel southPanel = new JPanel(new BorderLayout(0, PADDING));
            southPanel.setBackground(BACKGROUND_COLOR);
            southPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1, 0, 0, 0, PRIMARY_COLOR),
                    new EmptyBorder(10, 0, 0, 0)
            ));

            // Input fields
            JPanel inputPanel = new JPanel(new GridLayout(3, 2, PADDING, PADDING));
            inputPanel.setBackground(BACKGROUND_COLOR);

            JLabel lblMaPhong = new JLabel("Mã phòng:");
            JLabel lblLoai = new JLabel("Loại phòng:");
            JLabel lblTinhTrang = new JLabel("Tình trạng:");

            lblMaPhong.setFont(LABEL_FONT);
            lblLoai.setFont(LABEL_FONT);
            lblTinhTrang.setFont(LABEL_FONT);

            txtMaPhong = new JTextField();
            txtMaPhong.setPreferredSize(new Dimension(150, 30));

            cboLoai = new JComboBox<>();
            cboLoai.setPreferredSize(new Dimension(150, 30));

            cboTinhTrang = new JComboBox<>(new String[]{"Trống", "Đang đặt", "Đang sử dụng", "Bảo trì"});
            cboTinhTrang.setPreferredSize(new Dimension(150, 30));

            inputPanel.add(lblMaPhong);
            inputPanel.add(txtMaPhong);
            inputPanel.add(lblLoai);
            inputPanel.add(cboLoai);
            inputPanel.add(lblTinhTrang);
            inputPanel.add(cboTinhTrang);

            // Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING, PADDING));
            buttonPanel.setBackground(BACKGROUND_COLOR);

            JButton btnThem = createStyledButton("Thêm");
            JButton btnSua = createStyledButton("Sửa");
            JButton btnXoa = createStyledButton("Xoá");
            JButton btnReset = createStyledButton("Làm mới");

            // Add event listeners
            btnThem.addActionListener(e -> {
                String maPhong = txtMaPhong.getText().trim();
                if (maPhong.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Mã phòng không được trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String tenLoai = cboLoai.getSelectedItem().toString();
                String tinhTrang = cboTinhTrang.getSelectedItem().toString();
                double gia = getGiaTheoLoai(tenLoai);

                // Lấy mã loại từ tên loại
                String maLoai = "";
                for (Map.Entry<String, String> entry : loaiMap.entrySet()) {
                    if (entry.getValue().equals(tenLoai)) {
                        maLoai = entry.getKey();
                        break;
                    }
                }

                Phong phong = new Phong(maPhong, tenLoai, tinhTrang, gia);
                if (phongBUS.themPhong(phong, maLoai)) {
                    JOptionPane.showMessageDialog(this, "Thêm phòng thành công!");
                    loadPhong();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể thêm phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnSua.addActionListener(e -> {
                if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String maPhong = txtMaPhong.getText().trim();
                String tenLoai = cboLoai.getSelectedItem().toString();
                String tinhTrang = cboTinhTrang.getSelectedItem().toString();
                double gia = getGiaTheoLoai(tenLoai);

                // Lấy mã loại từ tên loại
                String maLoai = "";
                for (Map.Entry<String, String> entry : loaiMap.entrySet()) {
                    if (entry.getValue().equals(tenLoai)) {
                        maLoai = entry.getKey();
                        break;
                    }
                }

                Phong phong = new Phong(maPhong, tenLoai, tinhTrang, gia);
                if (phongBUS.suaPhong(phong, maLoai)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật phòng thành công!");
                    loadPhong();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể cập nhật phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnXoa.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng để xoá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String maPhong = model.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc muốn xoá phòng " + maPhong + "?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (phongBUS.xoaPhong(maPhong)) {
                        JOptionPane.showMessageDialog(this, "Xoá phòng thành công!");
                        loadPhong();
                        clearForm();
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xoá phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            btnReset.addActionListener(e -> clearForm());

            buttonPanel.add(btnThem);
            buttonPanel.add(btnSua);
            buttonPanel.add(btnXoa);
            buttonPanel.add(btnReset);

            southPanel.add(inputPanel, BorderLayout.CENTER);
            southPanel.add(buttonPanel, BorderLayout.SOUTH);

            return southPanel;
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
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setPreferredSize(new Dimension(100, 35));
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0x2B5174), 2),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

            return button;
        }

        private void searchByStatus(String status) {
            model.setRowCount(0);
            List<Phong> dsPhong = phongBUS.getAllPhong();

            for (Phong p : dsPhong) {
                if (status.equals("Tất cả") || p.getTinhTrang().equals(status)) {
                    model.addRow(new Object[]{
                            p.getMaPhong(),
                            p.getTenLoai(),
                            String.format("%,.0f", p.getGia()),
                            p.getTinhTrang()
                    });
                }
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy phòng nào có tình trạng: " + status,
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        private void loadLoaiPhong() {
            loaiMap.clear();
            loaiMap.put("LP01", "Phòng đơn");
            loaiMap.put("LP02", "Phòng đôi");
            loaiMap.put("LP03", "VIP");

            cboLoai.removeAllItems();
            for (String tenLoai : loaiMap.values()) {
                cboLoai.addItem(tenLoai);
            }
        }

        private void loadPhong() {
            searchByStatus("Tất cả");
        }

        private void clearForm() {
            txtMaPhong.setText("");
            txtMaPhong.setEditable(true);
            cboLoai.setSelectedIndex(0);
            cboTinhTrang.setSelectedIndex(0);
            table.clearSelection();
        }

        private double getGiaTheoLoai(String tenLoai) {
            switch (tenLoai) {
                case "Phòng đơn": return 500000;
                case "Phòng đôi": return 750000;
                case "VIP": return 1200000;
                default: return 0;
            }
        }
    }