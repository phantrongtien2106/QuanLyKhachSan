package gui;

import dao.PhanQuyenDAO;
import dao.TaiKhoanDAO;
import model.ChucNang;
import model.LoaiQuyen;
import Panel.ThemChucNangPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PhanQuyenChiTiet extends JFrame {
    private PhanQuyenDAO dao = new PhanQuyenDAO();
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    private JComboBox<String> comboVaiTro;
    private JPanel quyenPanel;

    public PhanQuyenChiTiet() {
        setTitle("Phân quyền chi tiết");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Chọn vai trò:"));
        comboVaiTro = new JComboBox<>();
        for (var vt : taiKhoanDAO.layDanhSachVaiTro()) {
            comboVaiTro.addItem(vt.getMaVaiTro());
        }
        topPanel.add(comboVaiTro);

        JButton btnThemChucNang = new JButton("Thêm chức năng mới");
        btnThemChucNang.addActionListener(e -> {
            JFrame frame = new JFrame("Thêm chức năng mới");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLocationRelativeTo(null);
            ThemChucNangPanel panel = new ThemChucNangPanel();
            frame.add(panel);
            frame.setVisible(true);
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    taiBangQuyen();
                }
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    taiBangQuyen();
                }
            });
        });
        topPanel.add(btnThemChucNang);



        add(topPanel, BorderLayout.NORTH);

        quyenPanel = new JPanel();
        quyenPanel.setLayout(new BoxLayout(quyenPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(quyenPanel);
        add(scroll, BorderLayout.CENTER);

        comboVaiTro.addActionListener(e -> taiBangQuyen());
        taiBangQuyen();
        setVisible(true);
    }

    private void taiBangQuyen() {
        quyenPanel.removeAll();
        String maVaiTro = (String) comboVaiTro.getSelectedItem();
        List<ChucNang> chucNangs = dao.layDanhSachChucNang();

        for (ChucNang cn : chucNangs) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.add(new JLabel(cn.getTenChucNang()));
            for (LoaiQuyen lq : LoaiQuyen.values()) {
                JCheckBox check = new JCheckBox(lq.name());
                check.setSelected(dao.coQuyen(maVaiTro, cn.getMaChucNang(), lq));
                check.addActionListener(e -> {
                    boolean state = check.isSelected();
                    dao.capNhatLoaiQuyen(maVaiTro, cn.getMaChucNang(), lq, state);
                });
                row.add(check);
            }
            quyenPanel.add(row);
        }
        revalidate();
        repaint();
    }
}
