package dao;

import model.CheckIn;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckInDAO {

    public boolean insert(CheckIn checkin) {
        String sql = "INSERT INTO check_in (ma_checkin, ma_nguon, loai_nguon, ngay_checkin, nhan_vien, ghi_chu) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, checkin.getMaCheckIn());
            stmt.setString(2, checkin.getMaPhieu()); // PDPxxx hoặc HDxxx
            stmt.setString(3, checkin.getMaPhieu().startsWith("HD") ? "hop_dong" : "phieu");
            stmt.setString(4, checkin.getNgayCheckIn());
            stmt.setString(5, checkin.getNhanVien());
            stmt.setString(6, checkin.getGhiChu());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CheckIn> getAll() {
        List<CheckIn> list = new ArrayList<>();
        String sql = "SELECT * FROM check_in";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String maCheckIn = rs.getString("ma_checkin");
                String maNguon = rs.getString("ma_nguon");
                String ngay = rs.getString("ngay_checkin");
                String nv = rs.getString("nhan_vien");
                String note = rs.getString("ghi_chu");

                CheckIn ci = new CheckIn(maCheckIn, maNguon, ngay, nv, note);
                list.add(ci);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
