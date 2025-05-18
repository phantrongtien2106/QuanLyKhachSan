
package gui;


import Panel.ThongTinCaNhanPanel;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {
    public UserDashboard(String maNguoiDung) {
        setTitle("Bảng điều khiển - Khách hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === Sidebar ===
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(223, 246, 255));
        sidebar.setPreferredSize(new Dimension(220, 0));

        // Avatar
        JLabel lblAvatar = new JLabel();
        lblAvatar.setPreferredSize(new Dimension(100, 100));
        lblAvatar.setMaximumSize(new Dimension(100, 100));
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAvatar.setIcon(new ImageIcon(new ImageIcon("src/images/user.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));

        JLabel lblTen = new JLabel("Khách hàng", SwingConstants.CENTER);
        lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 14));

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(lblAvatar);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(lblTen);
        sidebar.add(Box.createVerticalStrut(20));

        // Buttons
        JButton btnThongTin = taoNut("Thông tin cá nhân");
        JButton btnChatAI = taoNut("Trợ lý AI");
        JButton btnDangXuat = taoNut("Đăng xuất");
        btnDangXuat.setBackground(new Color(220, 53, 69));

        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel welcome = new JLabel("Xin chào quý khách!", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPanel.add(welcome, BorderLayout.CENTER);

        btnThongTin.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new ThongTinCaNhanPanel(maNguoiDung),SwingConstants.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        btnDangXuat.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        sidebar.add(btnThongTin);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnChatAI);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnDangXuat);
        sidebar.add(Box.createVerticalStrut(20));

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton taoNut(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 35));
        btn.setBackground(new Color(100, 149, 237));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserDashboard("KH001").setVisible(true));
    }
}
