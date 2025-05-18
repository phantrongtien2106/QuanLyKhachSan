package Panel;

import dao.ChucNangDAO;
import model.ChucNang;

import javax.swing.*;
import java.awt.*;

public class ThemChucNangPanel extends JPanel {
    private JTextField txtMa, txtTen;
    private JButton btnThem;
    private ChucNangDAO chucNangDAO = new ChucNangDAO();

    public ThemChucNangPanel() {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        form.add(new JLabel("Mã chức năng:"));
        txtMa = new JTextField();
        form.add(txtMa);

        form.add(new JLabel("Tên chức năng:"));
        txtTen = new JTextField();
        form.add(txtTen);

        btnThem = new JButton("Thêm chức năng mới");
        btnThem.addActionListener(e -> {
            String ma = txtMa.getText().trim();
            String ten = txtTen.getText().trim();
            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
            ChucNang cn = new ChucNang(ma, ten);
            boolean ok = chucNangDAO.themChucNang(cn);
            JOptionPane.showMessageDialog(this, ok ? "Thêm thành công!" : "Mã đã tồn tại hoặc lỗi!");
        });

        add(form, BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.add(btnThem);
        add(bottom, BorderLayout.SOUTH);
    }
}
