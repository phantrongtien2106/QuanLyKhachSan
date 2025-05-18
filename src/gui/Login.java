
package gui;

import bus.TaiKhoanBUS;
import model.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    private JTextField txtSoDienThoai;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap, btnDangKy;
    private TaiKhoanBUS taiKhoanBUS;

    public Login() {
        taiKhoanBUS = new TaiKhoanBUS();

        setTitle("Đăng nhập hệ thống");
        setSize(500, 360);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ KHÁCH SẠN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(50, 10, 400, 40);
        add(lblTitle);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(70, 70, 100, 30);
        add(lblSDT);

        txtSoDienThoai = new JTextField();
        txtSoDienThoai.setBounds(180, 70, 220, 30);
        txtSoDienThoai.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(txtSoDienThoai);

        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setBounds(70, 120, 100, 30);
        add(lblMatKhau);

        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(180, 120, 220, 30);
        txtMatKhau.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(txtMatKhau);

        btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setBounds(100, 190, 120, 40);
        btnDangNhap.setBackground(new Color(30, 144, 255));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDangNhap.addActionListener(this);
        add(btnDangNhap);

        btnDangKy = new JButton("Đăng ký");
        btnDangKy.setBounds(260, 190, 120, 40);
        btnDangKy.setBackground(new Color(34, 139, 34));
        btnDangKy.setForeground(Color.WHITE);
        btnDangKy.setFocusPainted(false);
        btnDangKy.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDangKy.addActionListener(e -> {
            new Register();
        });
        add(btnDangKy);

        getContentPane().setBackground(new Color(250, 250, 250));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sdt = txtSoDienThoai.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword()).trim();

        if (sdt.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (taiKhoanBUS.dangNhap(sdt, matKhau)) {
            TaiKhoan tk = taiKhoanBUS.getThongTin(sdt);
            String maNguoiDung = tk.getMaNguoiDung();

            JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Xin chào: " + maNguoiDung);

            if (taiKhoanBUS.coVaiTro(maNguoiDung, "ADMIN")) {
                new AdminDashboard(maNguoiDung);
            } else if (taiKhoanBUS.coVaiTro(maNguoiDung, "RECEPTIONIST")) {
                new DashboardNhanVien();
            } else {
                // tạo ở đây nè
            }
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai số điện thoại hoặc mật khẩu!");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
