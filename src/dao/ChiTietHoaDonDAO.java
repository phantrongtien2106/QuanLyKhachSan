package dao;

import model.ChiTietHoaDon;
import util.DBConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {

    public boolean insert(ChiTietHoaDon chiTietHoaDon) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO chi_tiet_hoa_don (ma_hoa_don, ma_item, loai_item, don_gia, so_luong, thanh_tien) " +
                     "VALUES (?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, chiTietHoaDon.getMaHoaDon());
            stmt.setString(2, chiTietHoaDon.getMaItem());
            stmt.setString(3, chiTietHoaDon.getLoaiItem());
            stmt.setDouble(4, chiTietHoaDon.getDonGia());
            stmt.setInt(5, chiTietHoaDon.getSoLuong());
            stmt.setDouble(6, chiTietHoaDon.getThanhTien());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu chi tiết hóa đơn: " + e.getMessage());
            return false;
        }
    }

    public List<ChiTietHoaDon> getByMaHoaDon(String maHoaDon) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM chi_tiet_hoa_don WHERE ma_hoa_don = ?")) {

            stmt.setString(1, maHoaDon);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ChiTietHoaDon item = new ChiTietHoaDon(
                    rs.getString("ma_hoa_don"),
                    rs.getString("ma_item"),
                    rs.getString("loai_item"),
                    rs.getDouble("don_gia"),
                    rs.getInt("so_luong"),
                    rs.getDouble("thanh_tien")
                );
                list.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy chi tiết hóa đơn: " + e.getMessage());
        }
        return list;
    }

    private String tenItem; // Thêm trường này

    // Thêm getter/setter
    public String getTenItem() {
        return tenItem;
    }

    public void setTenItem(String tenItem) {
        this.tenItem = tenItem;
    }
    // Other methods like update, delete, etc.
}