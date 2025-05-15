package System.panel;

import System.dao.TaiKhoanDAO;
import System.bus.DichVuKiemTraQuyen;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import System.model.VaiTro;

public class ChucNangPanel extends JPanel {
    private String tenDangNhap;
    private JPanel sidebar, contentPanel;

    public ChucNangPanel(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
        setLayout(new BorderLayout());

        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(223, 246, 255));

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        System.out.println("[DEBUG] Người dùng hiện tại: " + tenDangNhap);
        DichVuKiemTraQuyen.getInstance().setNguoiDungHienTai(tenDangNhap);
        addChucNangButton("Thông tin cá nhân", "CAP_NHAT_TT", new ThongTinCaNhanPanel(tenDangNhap));
        addChucNangButton("Đặt phòng", "DAT_PHONG", new JLabel("Đây là giao diện Đặt phòng"));
        addChucNangButton("Hủy đặt phòng", "HUY_DAT_PHONG", new JLabel("Giao diện hủy đặt phòng"));
        addChucNangButton("Thêm khách hàng", "THEM_KHACH_HANG", new JLabel("Form thêm khách hàng"));
        addChucNangButton("Xem danh sách phòng", "XEM_PHONG", new JLabel("Danh sách phòng"));
    }

    private void addChucNangButton(String title, String maChucNang, JComponent componentToShow) {
        boolean quyen = DichVuKiemTraQuyen.getInstance().coQuyen(maChucNang);
        System.out.println("[DEBUG] Chức năng " + maChucNang + ": " + (quyen ? "ĐƯỢC PHÉP" : "KHÔNG CÓ QUYỀN"));
        if (quyen) {
            JButton btn = new JButton(title);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setFocusPainted(false);
            btn.setBackground(new Color(100, 149, 237));
            btn.setForeground(Color.WHITE);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    contentPanel.removeAll();
                    contentPanel.add(componentToShow, BorderLayout.CENTER);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
            });
            sidebar.add(Box.createVerticalStrut(10));
            sidebar.add(btn);
        }
    }

    public JPanel getSidebar() {
        return sidebar;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
