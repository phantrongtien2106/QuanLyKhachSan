package dao;

import model.ChiTietHopDong;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHopDongDAO {

    // ✅ Thêm chi tiết hợp đồng
    public boolean insert(ChiTietHopDong ct) {
        String sql = "INSERT INTO chi_tiet_hop_dong (ma_hop_dong, ma_phong, ghi_chu) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ct.getMaHopDong());
            stmt.setString(2, ct.getMaPhong());
            stmt.setString(3, ct.getGhiChu());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Lấy danh sách theo mã hợp đồng
    public List<ChiTietHopDong> getByMaHopDong(String maHD) {
        List<ChiTietHopDong> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_hop_dong WHERE ma_hop_dong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maPhong = rs.getString("ma_phong");
                String ghiChu = rs.getString("ghi_chu");

                list.add(new ChiTietHopDong(maHD, maPhong, ghiChu));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ Xoá chi tiết hợp đồng (1 dòng)
    public boolean delete(String maHD, String maPhong) {
        String sql = "DELETE FROM chi_tiet_hop_dong WHERE ma_hop_dong = ? AND ma_phong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            stmt.setString(2, maPhong);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Xoá tất cả chi tiết của một hợp đồng
    public boolean deleteByMaHD(String maHD) {
        String sql = "DELETE FROM chi_tiet_hop_dong WHERE ma_hop_dong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Cập nhật ghi chú (nếu có)
    public boolean capNhatGhiChu(String maHD, String maPhong, String ghiChu) {
        String sql = "UPDATE chi_tiet_hop_dong SET ghi_chu = ? WHERE ma_hop_dong = ? AND ma_phong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ghiChu);
            stmt.setString(2, maHD);
            stmt.setString(3, maPhong);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Thêm vào ChiTietHopDongDAO.java
    public List<String> getPhongByMaHopDong(String maHopDong) {
        List<String> dsPhong = new ArrayList<>();
        String sql = "SELECT ma_phong FROM chi_tiet_hop_dong WHERE ma_hop_dong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHopDong);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dsPhong.add(rs.getString("ma_phong"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dsPhong;
    }
}