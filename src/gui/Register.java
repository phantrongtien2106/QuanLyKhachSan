package System.gui;

import System.bus.TaiKhoanBUS;
import System.helper.MaNguoiDungHelper;
import System.model.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Register extends JFrame implements ActionListener {
    private JTextField txtHoTen, txtCCCD, txtEmail, txtSoDienThoai, txtDiaChi;
    private JPasswordField txtMatKhau;
    private JButton btnDangKy;
    private TaiKhoanBUS taiKhoanBUS;
    private MaNguoiDungHelper maHelper;

    public Register () {
        taiKhoanBUS = new TaiKhoanBUS();
        maHelper = new MaNguoiDungHelper();

        setTitle("Đăng ký tài khoản");
        setSize(450, 420);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(30, 20, 150, 30);
        add(lblSDT);

        txtSoDienThoai = new JTextField();
        txtSoDienThoai.setBounds(180, 20, 200, 30);
        add(txtSoDienThoai);

        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(30, 60, 150, 30);
        add(lblHoTen);

        txtHoTen = new JTextField();
        txtHoTen.setBounds(180, 60, 200, 30);
        add(txtHoTen);

        JLabel lblCCCD = new JLabel("CCCD:");
        lblCCCD.setBounds(30, 100, 150, 30);
        add(lblCCCD);

        txtCCCD = new JTextField();
        txtCCCD.setBounds(180, 100, 200, 30);
        add(txtCCCD);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 140, 150, 30);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(180, 140, 200, 30);
        add(txtEmail);

        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setBounds(30, 180, 150, 30);
        add(lblDiaChi);

        txtDiaChi = new JTextField();
        txtDiaChi.setBounds(180, 180, 200, 30);
        add(txtDiaChi);

        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setBounds(30, 220, 150, 30);
        add(lblMatKhau);

        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(180, 220, 200, 30);
        add(txtMatKhau);

        btnDangKy = new JButton("Đăng ký");
        btnDangKy.setBounds(150, 280, 120, 40);
        btnDangKy.addActionListener(this);
        add(btnDangKy);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String loai = "khach_hang";
        String sdt = txtSoDienThoai.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String cccd = txtCCCD.getText().trim();
        String email = txtEmail.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword()).trim();

        if (sdt.isEmpty() || hoTen.isEmpty() || cccd.isEmpty() || email.isEmpty() || diaChi.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        String maNguoiDung = maHelper.taoMaTuDong(loai);

        TaiKhoan tk = new TaiKhoan(maNguoiDung, hoTen, new ArrayList<>());
        tk.setSoDienThoai(sdt);
        tk.setCccd(cccd);
        tk.setEmail(email);
        tk.setDiaChi(diaChi);
        tk.setMatKhau(matKhau);

        if (taiKhoanBUS.dangKyTaiKhoan(tk)) {
            taiKhoanBUS.ganVaiTroTheoLoai(maNguoiDung, loai);
            JOptionPane.showMessageDialog(this, "Đăng ký thành công! Mã người dùng: " + maNguoiDung);
            JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập để tiếp tục!");
            // Đóng cửa sổ đăng ký
            this.setVisible(false);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại hoặc CCCD trùng!");
        }
    }

    public static void main(String[] args) {
        new Register();
    }
}
