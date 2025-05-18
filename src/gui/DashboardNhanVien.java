package gui;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.MouseAdapter;
        import java.awt.event.MouseEvent;

        public class DashboardNhanVien extends JFrame {
            private CardLayout cardLayout;
            private JPanel contentPanel;
            private final Color PRIMARY_COLOR = Color.decode("#4682B4");
            private final Color HOVER_COLOR = Color.decode("#5693C5");
            private final Color BACKGROUND_COLOR = Color.decode("#DFF6FF");
            private final Color TEXT_COLOR = Color.WHITE;

            public DashboardNhanVien() {
                setTitle("Quản lý Khách sạn - Nhân viên");
                setSize(1200, 700);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Set background color
                getContentPane().setBackground(BACKGROUND_COLOR);
                setLayout(new BorderLayout());

                // Left menu panel (20% of width)
                JPanel menuPanel = createMenuPanel();

                // Right content panel (80% of width)
                contentPanel = new JPanel();
                cardLayout = new CardLayout();
                contentPanel.setLayout(cardLayout);
                contentPanel.setBackground(BACKGROUND_COLOR);

                // Add your actual form panels instead of placeholders
                // For example:
                contentPanel.add(new QuanLyPhongPanel(), "phong"); // Replace with your actual form class
                contentPanel.add(new DatPhongPanel(), "datphong"); // Replace with your actual form class
                contentPanel.add(new TaoHopDongPanel(), "taohopdong"); // Replace with your actual form class
                contentPanel.add(new QuanLyHopDongPanel(), "quanlyhopdong"); // Replace with your actual form class
                contentPanel.add(new CheckInPanel(), "checkin"); // Replace with your actual form class
                contentPanel.add(new CheckOutPanel(), "checkout"); // Replace with your actual form class
                contentPanel.add(new HoaDonPanel(), "hoadon"); // Replace with your actual form class
                contentPanel.add(new QuanLyHoaDonPanel(), "quanlyhoadon"); // Replace with your actual form class

                // Add panels to frame
                add(menuPanel, BorderLayout.WEST);
                add(contentPanel, BorderLayout.CENTER);

                // Show default panel
                cardLayout.show(contentPanel, "phong");

                setVisible(true);
            }
            private JPanel createMenuPanel() {
                JPanel menuPanel = new JPanel();
                menuPanel.setLayout(new BorderLayout());
                menuPanel.setBackground(BACKGROUND_COLOR);
                menuPanel.setPreferredSize(new Dimension(240, getHeight()));
                menuPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

                // User profile section
                JPanel profilePanel = new JPanel();
                profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
                profilePanel.setBackground(BACKGROUND_COLOR);
                profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

                // Avatar and name
                JLabel avatarLabel = new JLabel();
                ImageIcon avatarIcon = new ImageIcon(new ImageIcon("src/images/avatar.png").getImage()
                        .getScaledInstance(80, 80, Image.SCALE_SMOOTH));
                avatarLabel.setIcon(avatarIcon);
                avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel nameLabel = new JLabel("NV001");
                nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
                nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel roleLabel = new JLabel("Nhân viên");
                roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                profilePanel.add(avatarLabel);
                profilePanel.add(Box.createVerticalStrut(10));
                profilePanel.add(nameLabel);
                profilePanel.add(Box.createVerticalStrut(5));
                profilePanel.add(roleLabel);

                // Menu buttons
                JPanel buttonsPanel = new JPanel();
                buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
                buttonsPanel.setBackground(BACKGROUND_COLOR);
                buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

                JButton btnPhong = createMenuButton("Phòng", "phong");
                JButton btnDatPhong = createMenuButton("Đặt Phòng", "datphong");
                JButton btnTaoHopDong = createMenuButton("Tạo Hợp Đồng", "taohopdong");
                JButton btnQuanLyHopDong = createMenuButton("Quản lý Hợp Đồng", "quanlyhopdong");
                JButton btnCheckIn = createMenuButton("Check In", "checkin");
                JButton btnCheckOut = createMenuButton("Check Out", "checkout");
                JButton btnHoaDon = createMenuButton("Hóa Đơn", "hoadon");
                JButton btnQuanLyHoaDon = createMenuButton("Quản lý Hóa Đơn", "quanlyhoadon");

                buttonsPanel.add(btnPhong);
                buttonsPanel.add(Box.createVerticalStrut(10));
                buttonsPanel.add(btnDatPhong);
                buttonsPanel.add(Box.createVerticalStrut(10));
                buttonsPanel.add(btnTaoHopDong);
                buttonsPanel.add(Box.createVerticalStrut(10));
                buttonsPanel.add(btnQuanLyHopDong);
                buttonsPanel.add(Box.createVerticalStrut(10));
                buttonsPanel.add(btnCheckIn);
                buttonsPanel.add(Box.createVerticalStrut(10));
                buttonsPanel.add(btnCheckOut);
                buttonsPanel.add(Box.createVerticalStrut(10));
                buttonsPanel.add(btnHoaDon);
                buttonsPanel.add(Box.createVerticalStrut(10));
                buttonsPanel.add(btnQuanLyHoaDon);

                // Logout button
                JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                logoutPanel.setBackground(BACKGROUND_COLOR);

                JButton btnDangXuat = new JButton("Đăng xuất");
                btnDangXuat.setFont(new Font("Arial", Font.BOLD, 14));
                btnDangXuat.setBackground(Color.decode("#FF4D4D"));
                btnDangXuat.setForeground(TEXT_COLOR);
                btnDangXuat.setFocusPainted(false);
                btnDangXuat.setBorderPainted(false);
                btnDangXuat.setPreferredSize(new Dimension(200, 40));
                btnDangXuat.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnDangXuat.addActionListener(e -> System.exit(0));

                logoutPanel.add(btnDangXuat);

                // Add all sections to menu panel
                menuPanel.add(profilePanel, BorderLayout.NORTH);
                menuPanel.add(buttonsPanel, BorderLayout.CENTER);
                menuPanel.add(logoutPanel, BorderLayout.SOUTH);

                return menuPanel;
            }

            private JButton createMenuButton(String text, String cardName) {
                JButton button = new JButton(text);
                button.setFont(new Font("Arial", Font.BOLD, 14));
                button.setBackground(PRIMARY_COLOR);
                button.setForeground(TEXT_COLOR);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setMaximumSize(new Dimension(210, 40));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Add action to switch panels
                button.addActionListener(e -> cardLayout.show(contentPanel, cardName));

                // Add hover effect
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setBackground(HOVER_COLOR);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        button.setBackground(PRIMARY_COLOR);
                    }
                });

                return button;
            }

            // Placeholder panel for QuanLyPhongPanel
            static class QuanLyPhongPanel extends JPanel {
                public QuanLyPhongPanel() {
                    setLayout(new BorderLayout());
                    setBackground(Color.decode("#DFF6FF"));

                    // Title panel with CENTER alignment
                    JPanel titlePanel = new JPanel();
                    titlePanel.setBackground(Color.decode("#4682B4"));
                    titlePanel.setPreferredSize(new Dimension(getWidth(), 50));
                    titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Changed to CENTER

                    JLabel titleLabel = new JLabel("Quản lý Phòng");
                    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    titleLabel.setForeground(Color.WHITE);
                    titlePanel.add(titleLabel);

                    // Content panel with centered message
                    JPanel contentPanel = new JPanel(new GridBagLayout());
                    contentPanel.setBackground(Color.decode("#DFF6FF"));

                    JLabel messageLabel = new JLabel("Đang phát triển Quản lý Phòng...");
                    messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                    contentPanel.add(messageLabel);

                    add(titlePanel, BorderLayout.NORTH);
                    add(contentPanel, BorderLayout.CENTER);
                }
            }

            // Placeholder panel for DatPhongPanel
            static class DatPhongPanel extends JPanel {
                public DatPhongPanel() {
                    setLayout(new BorderLayout());
                    setBackground(Color.decode("#DFF6FF"));

                    // Title panel with CENTER alignment
                    JPanel titlePanel = new JPanel();
                    titlePanel.setBackground(Color.decode("#4682B4"));
                    titlePanel.setPreferredSize(new Dimension(getWidth(), 50));
                    titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Changed to CENTER

                    JLabel titleLabel = new JLabel("Đặt Phòng");
                    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    titleLabel.setForeground(Color.WHITE);
                    titlePanel.add(titleLabel);

                    // Content panel with centered message
                    JPanel contentPanel = new JPanel(new GridBagLayout());
                    contentPanel.setBackground(Color.decode("#DFF6FF"));

                    JLabel messageLabel = new JLabel("Đang phát triển Đặt Phòng...");
                    messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                    contentPanel.add(messageLabel);

                    add(titlePanel, BorderLayout.NORTH);
                    add(contentPanel, BorderLayout.CENTER);
                }
            }
            // Placeholder panel for TaoHopDongPanel
            static class TaoHopDongPanel extends JPanel {
                public TaoHopDongPanel() {
                    setLayout(new BorderLayout());
                    setBackground(Color.decode("#DFF6FF"));

                    // Title panel with CENTER alignment
                    JPanel titlePanel = new JPanel();
                    titlePanel.setBackground(Color.decode("#4682B4"));
                    titlePanel.setPreferredSize(new Dimension(getWidth(), 50));
                    titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Changed to CENTER

                    JLabel titleLabel = new JLabel("Tạo Hợp Đồng");
                    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    titleLabel.setForeground(Color.WHITE);
                    titlePanel.add(titleLabel);

                    // Content panel with centered message
                    JPanel contentPanel = new JPanel(new GridBagLayout());
                    contentPanel.setBackground(Color.decode("#DFF6FF"));

                    JLabel messageLabel = new JLabel("Đang phát triển Tạo Hợp Đồng...");
                    messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                    contentPanel.add(messageLabel);

                    add(titlePanel, BorderLayout.NORTH);
                    add(contentPanel, BorderLayout.CENTER);
                }
            }

            // Placeholder panel for QuanLyHopDongPanel
            static class QuanLyHopDongPanel extends JPanel {
                public QuanLyHopDongPanel() {
                    setLayout(new BorderLayout());
                    setBackground(Color.decode("#DFF6FF"));

                    // Title panel with CENTER alignment
                    JPanel titlePanel = new JPanel();
                    titlePanel.setBackground(Color.decode("#4682B4"));
                    titlePanel.setPreferredSize(new Dimension(getWidth(), 50));
                    titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Changed to CENTER

                    JLabel titleLabel = new JLabel("Quản lý Hợp Đồng");
                    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    titleLabel.setForeground(Color.WHITE);
                    titlePanel.add(titleLabel);

                    // Content panel with centered message
                    JPanel contentPanel = new JPanel(new GridBagLayout());
                    contentPanel.setBackground(Color.decode("#DFF6FF"));

                    JLabel messageLabel = new JLabel("Đang phát triển Quản lý Hợp Đồng...");
                    messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                    contentPanel.add(messageLabel);

                    add(titlePanel, BorderLayout.NORTH);
                    add(contentPanel, BorderLayout.CENTER);
                }
            }

            // Placeholder panel for CheckInPanel
            static class CheckInPanel extends JPanel {
                public CheckInPanel() {
                    setLayout(new BorderLayout());
                    setBackground(Color.decode("#DFF6FF"));

                    // Title panel with CENTER alignment
                    JPanel titlePanel = new JPanel();
                    titlePanel.setBackground(Color.decode("#4682B4"));
                    titlePanel.setPreferredSize(new Dimension(getWidth(), 50));
                    titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Changed to CENTER

                    JLabel titleLabel = new JLabel("Check In");
                    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    titleLabel.setForeground(Color.WHITE);
                    titlePanel.add(titleLabel);

                    // Content panel with centered message
                    JPanel contentPanel = new JPanel(new GridBagLayout());
                    contentPanel.setBackground(Color.decode("#DFF6FF"));

                    JLabel messageLabel = new JLabel("Đang phát triển Check In...");
                    messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                    contentPanel.add(messageLabel);

                    add(titlePanel, BorderLayout.NORTH);
                    add(contentPanel, BorderLayout.CENTER);
                }
            }

            // Placeholder panel for CheckOutPanel
            static class CheckOutPanel extends JPanel {
                public CheckOutPanel() {
                    setLayout(new BorderLayout());
                    setBackground(Color.decode("#DFF6FF"));

                    // Title panel with CENTER alignment
                    JPanel titlePanel = new JPanel();
                    titlePanel.setBackground(Color.decode("#4682B4"));
                    titlePanel.setPreferredSize(new Dimension(getWidth(), 50));
                    titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Changed to CENTER

                    JLabel titleLabel = new JLabel("Check Out");
                    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    titleLabel.setForeground(Color.WHITE);
                    titlePanel.add(titleLabel);

                    // Content panel with centered message
                    JPanel contentPanel = new JPanel(new GridBagLayout());
                    contentPanel.setBackground(Color.decode("#DFF6FF"));

                    JLabel messageLabel = new JLabel("Đang phát triển Check Out...");
                    messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                    contentPanel.add(messageLabel);

                    add(titlePanel, BorderLayout.NORTH);
                    add(contentPanel, BorderLayout.CENTER);
                }
            }
            // Placeholder panel for HoaDonPanel
            static class HoaDonPanel extends JPanel {
                public HoaDonPanel() {
                    setLayout(new BorderLayout());
                    setBackground(Color.decode("#DFF6FF"));

                    // Title panel with CENTER alignment
                    JPanel titlePanel = new JPanel();
                    titlePanel.setBackground(Color.decode("#4682B4"));
                    titlePanel.setPreferredSize(new Dimension(getWidth(), 50));
                    titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Changed to CENTER

                    JLabel titleLabel = new JLabel("Hóa Đơn");
                    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    titleLabel.setForeground(Color.WHITE);
                    titlePanel.add(titleLabel);

                    // Content panel with centered message
                    JPanel contentPanel = new JPanel(new GridBagLayout());
                    contentPanel.setBackground(Color.decode("#DFF6FF"));

                    JLabel messageLabel = new JLabel("Đang phát triển Hóa Đơn...");
                    messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                    contentPanel.add(messageLabel);

                    add(titlePanel, BorderLayout.NORTH);
                    add(contentPanel, BorderLayout.CENTER);
                }
            }
            // Placeholder panel for QuanLyHoaDonPanel
            static class QuanLyHoaDonPanel extends JPanel {
                public QuanLyHoaDonPanel() {
                    setLayout(new BorderLayout());
                    setBackground(Color.decode("#DFF6FF"));

                    // Title panel with CENTER alignment
                    JPanel titlePanel = new JPanel();
                    titlePanel.setBackground(Color.decode("#4682B4"));
                    titlePanel.setPreferredSize(new Dimension(getWidth(), 50));
                    titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Changed to CENTER

                    JLabel titleLabel = new JLabel("Quản lý Hóa Đơn");
                    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    titleLabel.setForeground(Color.WHITE);
                    titlePanel.add(titleLabel);

                    // Content panel with centered message
                    JPanel contentPanel = new JPanel(new GridBagLayout());
                    contentPanel.setBackground(Color.decode("#DFF6FF"));

                    JLabel messageLabel = new JLabel("Đang phát triển Quản lý Hóa Đơn...");
                    messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                    contentPanel.add(messageLabel);

                    add(titlePanel, BorderLayout.NORTH);
                    add(contentPanel, BorderLayout.CENTER);
                }
            }

            // Placeholder panel for content areas
            static class PlaceholderPanel extends JPanel {
                public PlaceholderPanel(String title) {
                    setLayout(new BorderLayout());
                    setBackground(Color.decode("#DFF6FF"));

                    // Title panel with CENTER alignment
                    JPanel titlePanel = new JPanel();
                    titlePanel.setBackground(Color.decode("#4682B4"));
                    titlePanel.setPreferredSize(new Dimension(getWidth(), 50));
                    titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Changed to CENTER

                    JLabel titleLabel = new JLabel(title);
                    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    titleLabel.setForeground(Color.WHITE);
                    titlePanel.add(titleLabel);

                    // Content panel with centered message
                    JPanel contentPanel = new JPanel(new GridBagLayout());
                    contentPanel.setBackground(Color.decode("#DFF6FF"));

                    JLabel messageLabel = new JLabel("Đang phát triển " + title + "...");
                    messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                    contentPanel.add(messageLabel);

                    add(titlePanel, BorderLayout.NORTH);
                    add(contentPanel, BorderLayout.CENTER);
                }
            }

            public static void main(String[] args) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                SwingUtilities.invokeLater(DashboardNhanVien::new);
            }
        }