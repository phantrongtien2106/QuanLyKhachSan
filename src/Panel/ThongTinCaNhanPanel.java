package System.panel;

import System.bus.TaiKhoanBUS;
import System.model.TaiKhoan;

import javax.swing.*;
import java.awt.*;

public class ThongTinCaNhanPanel extends JPanel {
    private JTextField txtHoTen, txtEmail, txtSDT, txtDiaChi;
    private JPasswordField txtMatKhauCu, txtMatKhauMoi, txtXacNhanMatKhau;
    private JButton btnCapNhat, btnDoiMatKhau, btnToggleMatKhau;
    private JPanel matKhauPanel;
    private TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();

    public ThongTinCaNhanPanel(String maNguoiDung) {
        setLayout(new BorderLayout());
        TaiKhoan tk = taiKhoanBUS.getThongTinTheoMa(maNguoiDung);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        form.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField(tk != null ? tk.getHoTen() : "");
        form.add(txtHoTen);

        form.add(new JLabel("Email:"));
        txtEmail = new JTextField(tk != null ? tk.getEmail() : "");
        form.add(txtEmail);

        form.add(new JLabel("Số điện thoại:"));
        txtSDT = new JTextField(tk != null ? tk.getSoDienThoai() : "");
        form.add(txtSDT);

        form.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField(tk != null ? tk.getDiaChi() : "");
        form.add(txtDiaChi);

        matKhauPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        matKhauPanel.add(new JLabel("Mật khẩu hiện tại:"));
        txtMatKhauCu = new JPasswordField();
        matKhauPanel.add(txtMatKhauCu);

        matKhauPanel.add(new JLabel("Mật khẩu mới:"));
        txtMatKhauMoi = new JPasswordField();
        matKhauPanel.add(txtMatKhauMoi);

        matKhauPanel.add(new JLabel("Xác nhận mật khẩu mới:"));
        txtXacNhanMatKhau = new JPasswordField();
        matKhauPanel.add(txtXacNhanMatKhau);

        matKhauPanel.setVisible(false);
        form.add(new JLabel()); // trống để căn lề
        btnToggleMatKhau = new JButton("Hiện đổi mật khẩu");
        btnToggleMatKhau.addActionListener(e -> {
            matKhauPanel.setVisible(!matKhauPanel.isVisible());
            btnToggleMatKhau.setText(matKhauPanel.isVisible() ? "Ẩn đổi mật khẩu" : "Hiện đổi mật khẩu");
            revalidate();
            repaint();
        });
        form.add(btnToggleMatKhau);

        btnCapNhat = new JButton("Cập nhật thông tin");
        btnCapNhat.addActionListener(e -> {
            if (tk != null) {
                tk.setHoTen(txtHoTen.getText());
                tk.setEmail(txtEmail.getText());
                tk.setSoDienThoai(txtSDT.getText());
                tk.setDiaChi(txtDiaChi.getText());
                boolean ok = taiKhoanBUS.capNhatThongTin(tk);
                JOptionPane.showMessageDialog(this, ok ? "Cập nhật thành công!" : "Cập nhật thất bại!");
            }
        });

        btnDoiMatKhau = new JButton("Đổi mật khẩu");
        btnDoiMatKhau.addActionListener(e -> {
            if (tk != null) {
                String matKhauCu = new String(txtMatKhauCu.getPassword());
                String matKhauMoi = new String(txtMatKhauMoi.getPassword());
                String xacNhan = new String(txtXacNhanMatKhau.getPassword());

                if (!tk.getMatKhau().equals(matKhauCu)) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng!");
                } else if (!matKhauMoi.equals(xacNhan)) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu mới không khớp!");
                } else {
                    tk.setMatKhau(matKhauMoi);
                    boolean ok = taiKhoanBUS.capNhatMatKhau(tk);
                    JOptionPane.showMessageDialog(this, ok ? "Đổi mật khẩu thành công!" : "Đổi mật khẩu thất bại!");
                }
            }
        });

        JPanel bottom = new JPanel();
        bottom.add(btnCapNhat);
        bottom.add(btnDoiMatKhau);

        add(form, BorderLayout.NORTH);
        add(matKhauPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}
