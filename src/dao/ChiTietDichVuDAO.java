package dao;

import model.ChiTietDichVu;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDichVuDAO {

    public boolean insert(ChiTietDichVu chiTietDichVu) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO chi_tiet_dich_vu (ma_dv, ma_phieu, so_luong) VALUES (?, ?, ?)")) {

            stmt.setString(1, chiTietDichVu.getMaDv());
            stmt.setString(2, chiTietDichVu.getMaPhieu());
            stmt.setInt(3, chiTietDichVu.getSoLuong());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm chi tiết dịch vụ: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

public List<ChiTietDichVu> getDichVuByMaPhieu(String maPhieu) {
        List<ChiTietDichVu> dsDichVu = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            // Replace "ChiTietDichVu" with the actual table name in your database
            String sql = "SELECT * FROM chi_tiet_dich_vu WHERE ma_phieu = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maPhieu);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ChiTietDichVu ctdv = new ChiTietDichVu(
                    rs.getString("ma_phieu"),
                    rs.getString("ma_dv"),
                    rs.getInt("so_luong")
                );
                dsDichVu.add(ctdv);
            }
        } catch (Exception e) {
            System.err.println("Lỗi truy vấn dịch vụ theo mã phiếu: " + e.getMessage());
        }
        return dsDichVu;
    }
    public List<ChiTietDichVu> getDichVuByMaHopDong(String maHopDong) {
        List<ChiTietDichVu> dsDichVu = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM chi_tiet_dich_vu WHERE ma_hop_dong = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maHopDong);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ChiTietDichVu ctdv = new ChiTietDichVu(
                    rs.getString("ma_hop_dong"),
                    rs.getString("ma_dv"),
                    rs.getInt("so_luong"),
                    true  // Mark as contract type
                );
                dsDichVu.add(ctdv);
            }
        } catch (Exception e) {
            System.err.println("Lỗi truy vấn dịch vụ theo mã hợp đồng: " + e.getMessage());
        }
        return dsDichVu;
    }
    public boolean update(ChiTietDichVu chiTietDichVu) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE chi_tiet_dich_vu SET so_luong = ? WHERE ma_dv = ? AND ma_phieu = ?")) {

            stmt.setInt(1, chiTietDichVu.getSoLuong());
            stmt.setString(2, chiTietDichVu.getMaDv());
            stmt.setString(3, chiTietDichVu.getMaPhieu());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật chi tiết dịch vụ: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(String maPhieu, String maDV) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM chi_tiet_dich_vu WHERE ma_phieu = ? AND ma_dv = ?")) {

            stmt.setString(1, maPhieu);
            stmt.setString(2, maDV);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa chi tiết dịch vụ: " + e.getMessage());
            return false;
        }
    }
}