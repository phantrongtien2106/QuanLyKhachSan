package dao;

import model.ChiTietDichVuHopDong;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDichVuHopDongDAO {

    public boolean insert(ChiTietDichVuHopDong ct) {
        String sql = "INSERT INTO chi_tiet_dich_vu_hop_dong (ma_hop_dong, ma_phong, ma_dv) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ct.getMaHopDong());
            stmt.setString(2, ct.getMaPhong());
            stmt.setString(3, ct.getMaDv());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ChiTietDichVuHopDong> getByHopDongVaPhong(String maHD, String maPhong) {
        List<ChiTietDichVuHopDong> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_dich_vu_hop_dong WHERE ma_hop_dong = ? AND ma_phong = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            stmt.setString(2, maPhong);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new ChiTietDichVuHopDong(
                        rs.getString("ma_hop_dong"),
                        rs.getString("ma_phong"),
                        rs.getString("ma_dv")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteByHopDongVaPhong(String maHD, String maPhong) {
        String sql = "DELETE FROM chi_tiet_dich_vu_hop_dong WHERE ma_hop_dong = ? AND ma_phong = ?";
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
}
