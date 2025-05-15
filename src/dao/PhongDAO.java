package dao;

import model.Phong;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongDAO {

    // Lấy tất cả phòng, JOIN để lấy tên loại phòng
    public List<Phong> getAllPhong() {
        List<Phong> dsPhong = new ArrayList<>();
        String sql = """
    SELECT p.ma_phong, lp.ten_loai, p.tinh_trang, lp.gia
    FROM phong p
    JOIN loai_phong lp ON p.ma_loai = lp.ma_loai
""";


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String maPhong = rs.getString("ma_phong");
                String tenLoai = rs.getString("ten_loai");
                String tinhTrang = rs.getString("tinh_trang");
                double gia = rs.getDouble("gia");

                dsPhong.add(new Phong(maPhong, tenLoai, tinhTrang, gia));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dsPhong;
    }

    // Thêm phòng (vẫn dùng ma_loai)
    public boolean insertPhong(Phong phong, String maLoai) {
        String sql = "INSERT INTO phong (ma_phong, ma_loai, tinh_trang) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phong.getMaPhong());
            stmt.setString(2, maLoai);
            stmt.setString(3, phong.getTinhTrang());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật phòng (cũng cần truyền lại ma_loai)
    public boolean updatePhong(Phong phong, String maLoai) {
        String sql = "UPDATE phong SET ma_loai = ?, tinh_trang = ? WHERE ma_phong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLoai);
            stmt.setString(2, phong.getTinhTrang());
            stmt.setString(3, phong.getMaPhong());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xoá phòng
    public boolean deletePhong(String maPhong) {
        String sql = "DELETE FROM phong WHERE ma_phong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật tình trạng phòng
    public boolean capNhatTinhTrang(String maPhong, String tinhTrang) {
        if (tinhTrang.length() > 50) { // Adjust this to the actual database column length
            System.err.println("Error: tinh_trang value exceeds maximum allowed length.");
            return false;
        }

        String sql = "UPDATE phong SET tinh_trang = ? WHERE ma_phong = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Cập nhật trạng thái phòng " + maPhong + " -> " + tinhTrang);

            stmt.setString(1, tinhTrang);
            stmt.setString(2, maPhong);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Kết quả cập nhật: " + rowsAffected + " dòng");

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi cập nhật tình trạng phòng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatTinhTrangVaNguon(String maPhong, String tinhTrang, String nguonDat) {
        String sql = "UPDATE phong SET tinh_trang = ?, nguon_dat = ? WHERE ma_phong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tinhTrang);
            stmt.setString(2, nguonDat);
            stmt.setString(3, maPhong);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getDonGiaByMaPhong(String maPhong) {
        String sql = "SELECT gia FROM loai_phong WHERE ma_loai = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("gia");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
