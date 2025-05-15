package System.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import System.panel.ChucNangPanel;
import System.bus.TaiKhoanBUS;
import System.model.TaiKhoan;
import System.panel.ThongTinCaNhanPanel;

public class Reception extends JFrame {
    public Reception(String tenDangNhap) {
        setTitle("Giao diện lễ tân");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Lấy thông tin người dùng từ DB
        TaiKhoanBUS bus = new TaiKhoanBUS();
        TaiKhoan tk = bus.getThongTinTheoMa(tenDangNhap);

        ChucNangPanel chucNangPanel = new ChucNangPanel(tenDangNhap);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(223, 246, 255));
        sidebar.setPreferredSize(new Dimension(220, 0));

        // Avatar hình tròn
        JLabel avatar = new JLabel();
        avatar.setPreferredSize(new Dimension(100, 100));
        avatar.setMaximumSize(new Dimension(100, 100));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        avatar.setIcon(new ImageIcon(new ImageIcon("src/images/user.png")
                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));

        String tenHienThi = (tk != null) ? tk.getHoTen() : "Lễ tân";
        JLabel lblTitle = new JLabel(tenHienThi, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMa = new JLabel("Mã: " + (tk != null ? tk.getMaNguoiDung() : ""));
        lblMa.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMa.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel lblSDT = new JLabel("SĐT: " + (tk != null ? tk.getSoDienThoai() : ""));
        lblSDT.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSDT.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel lblEmail = new JLabel("Email: " + (tk != null ? tk.getEmail() : ""));
        lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(avatar);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(lblTitle);
        sidebar.add(lblMa);
        sidebar.add(lblSDT);
        sidebar.add(lblEmail);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(chucNangPanel.getSidebar());

        JButton btnDangXuat = new JButton("Đăng xuất");
        btnDangXuat.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDangXuat.setMaximumSize(new Dimension(150, 40));
        btnDangXuat.setBackground(new Color(220, 53, 69));
        btnDangXuat.setForeground(Color.WHITE);
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDangXuat.addActionListener(e -> {
            dispose();
            new Login();
        });

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnDangXuat);
        sidebar.add(Box.createVerticalStrut(20));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(chucNangPanel.getContentPanel(), BorderLayout.CENTER);
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
