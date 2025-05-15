package dao;

import model.ChiTietPhieuDatPhong;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuDatPhongDAO {

    // Lấy danh sách chi tiết phiếu đặt phòng theo mã phiếu
    public List<ChiTietPhieuDatPhong> getByMaPhieu(String maPhieu) {
        List<ChiTietPhieuDatPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM chitiet_phieu_dat_phong WHERE ma_phieu = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhieu);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuDatPhong ct = new ChiTietPhieuDatPhong();
                    ct.setMaPhieu(rs.getString("ma_phieu"));
                    ct.setMaPhong(rs.getString("ma_phong"));
                    ct.setDonGia(rs.getDouble("don_gia"));
                    list.add(ct);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy danh sách mã phòng theo mã phiếu
    public List<String> getPhongByMaPhieu(String maPhieu) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT ma_phong FROM chitiet_phieu_dat_phong WHERE ma_phieu = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhieu);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("ma_phong"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm chi tiết phiếu đặt phòng
    public boolean insert(ChiTietPhieuDatPhong ct) {
        String sql = "INSERT INTO chitiet_phieu_dat_phong (ma_phieu, ma_phong, don_gia) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ct.getMaPhieu());
            stmt.setString(2, ct.getMaPhong());
            stmt.setDouble(3, ct.getDonGia());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa chi tiết phiếu đặt phòng theo mã phiếu
    public boolean deleteByMaPhieu(String maPhieu) {
        String sql = "DELETE FROM chitiet_phieu_dat_phong WHERE ma_phieu = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhieu);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}