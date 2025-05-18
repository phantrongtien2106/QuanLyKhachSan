package gui;

                import Panel.QuanLyHoaDonPanel;
                import Panel.QuanLyHopDongPanel;
                import Panel.CheckInPanel;
                import Panel.CheckOutPanel;
                import Panel.DatPhongPanel;
                import Panel.HopDongPanel;
//                import Panel.KhachHangPanel;
                import Panel.PhongPanel;

                // Import other panels as needed

                import javax.swing.*;
                import java.awt.*;
                import java.awt.event.*;

                public class DashboardNhanVien extends JFrame {
                    // Hardcoded employee ID
                    private static final String EMPLOYEE_ID = "NV001";

                    // Colors
                    private static final Color PRIMARY_COLOR = new Color(0x4682B4);    // Steel Blue
                    private static final Color BACKGROUND_COLOR = new Color(0xF5F5F5); // Light gray
                    private static final Color TEXT_COLOR = Color.WHITE;
                    private static final Color MENU_BG_COLOR = new Color(0x2C3E50);    // Dark blue-gray

                    // Panels
                    private JPanel contentPanel;
                    private CardLayout cardLayout;

                    public DashboardNhanVien() {
                        setTitle("Quản lý khách sạn - Dashboard nhân viên (ID: " + EMPLOYEE_ID + ")");
                        setSize(1200, 700);
                        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        setLocationRelativeTo(null);
                        setLayout(new BorderLayout());

                        // Create sidebar menu (20% width)
                        JPanel menuPanel = createMenuPanel();
                        add(menuPanel, BorderLayout.WEST);

                        // Create content area (80% width) with CardLayout
                        cardLayout = new CardLayout();
                        contentPanel = new JPanel(cardLayout);
                        contentPanel.setBackground(BACKGROUND_COLOR);

                        // Add all panels to content area with unique identifiers
                        contentPanel.add(new PhongPanel(), "phong");           // Placeholder for Phòng panel
                        contentPanel.add(new DatPhongPanel(), "datPhong");        // Placeholder for Đặt phòng panel
                        contentPanel.add(new JPanel(), "khachHang");       // Placeholder for Quản lí khách hàng panel
                        contentPanel.add(new HopDongPanel(), "taoHopDong");      // Placeholder for Tạo hợp đồng panel
                        contentPanel.add(new QuanLyHopDongPanel(), "hopDong");  // Quản lí hợp đồng panel
                        contentPanel.add(new CheckInPanel(), "checkIn");         // Placeholder for Check In panel
                        contentPanel.add(new CheckOutPanel(), "checkOut");        // Placeholder for Checkout panel
                        contentPanel.add(new QuanLyHoaDonPanel(), "hoaDon");    // Quản lí hóa đơn panel

                        // Show default panel
                        cardLayout.show(contentPanel, "phong");
                        add(contentPanel, BorderLayout.CENTER);

                        // Add status bar with employee ID
                        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                        JLabel employeeLabel = new JLabel("Mã nhân viên: " + EMPLOYEE_ID);
                        statusBar.add(employeeLabel);
                        add(statusBar, BorderLayout.SOUTH);
                    }

                    private JPanel createMenuPanel() {
                        JPanel menuPanel = new JPanel();
                        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
                        menuPanel.setBackground(MENU_BG_COLOR);
                        menuPanel.setPreferredSize(new Dimension((int)(getWidth() * 0.2), getHeight()));
                        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

                        // Title label
                        JLabel titleLabel = new JLabel("MENU CHỨC NĂNG");
                        titleLabel.setForeground(TEXT_COLOR);
                        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
                        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        menuPanel.add(titleLabel);
                        menuPanel.add(Box.createVerticalStrut(20));

                        // Create all menu buttons
                        String[] buttonLabels = {
                            "Phòng", "Đặt phòng", "Quản lí khách hàng",
                            "Tạo hợp đồng", "Quản lí hợp đồng",
                            "Check In", "Checkout", "Quản lí hóa đơn"
                        };

                        String[] panelIds = {
                            "phong", "datPhong", "khachHang",
                            "taoHopDong", "hopDong",
                            "checkIn", "checkOut", "hoaDon"
                        };

                        // Add all buttons with action listeners
                        for (int i = 0; i < buttonLabels.length; i++) {
                            JButton button = createMenuButton(buttonLabels[i]);
                            final String panelId = panelIds[i];
                            button.addActionListener(e -> cardLayout.show(contentPanel, panelId));
                            menuPanel.add(button);
                            menuPanel.add(Box.createVerticalStrut(10));
                        }

                        // Add spacing to push logout button to bottom
                        menuPanel.add(Box.createVerticalGlue());

                        // Logout button at bottom
                        JButton btnDangXuat = new JButton("Đăng xuất");
                        btnDangXuat.setMaximumSize(new Dimension(180, 40));
                        btnDangXuat.setBackground(new Color(0xFF4D4D));
                        btnDangXuat.setForeground(Color.WHITE);
                        btnDangXuat.setFocusPainted(false);
                        btnDangXuat.setBorderPainted(false);
                        btnDangXuat.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        btnDangXuat.setAlignmentX(Component.CENTER_ALIGNMENT);
                        btnDangXuat.addActionListener(e -> logout());
                        menuPanel.add(btnDangXuat);

                        return menuPanel;
                    }

                    private JButton createMenuButton(String text) {
                        JButton button = new JButton(text);
                        button.setMaximumSize(new Dimension(180, 40));
                        button.setBackground(PRIMARY_COLOR);
                        button.setForeground(TEXT_COLOR);
                        button.setFocusPainted(false);
                        button.setBorderPainted(false);
                        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        button.setAlignmentX(Component.CENTER_ALIGNMENT);

                        // Add hover effect
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

                    private void logout() {
                        int confirm = JOptionPane.showConfirmDialog(
                            this,
                            "Bạn có chắc chắn muốn đăng xuất?",
                            "Xác nhận đăng xuất",
                            JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            this.dispose();
                            JOptionPane.showMessageDialog(null, "Đã đăng xuất khỏi hệ thống");
                            System.exit(0);
                        }
                    }

                    // Getter for employee ID to be used by other panels
                    public static String getEmployeeId() {
                        return EMPLOYEE_ID;
                    }

                    public static void main(String[] args) {
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        SwingUtilities.invokeLater(() -> {
                            new DashboardNhanVien().setVisible(true);
                        });
                    }
                }