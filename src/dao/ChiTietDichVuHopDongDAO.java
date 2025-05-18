package dao;

import model.ChiTietDichVuHopDong;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDichVuHopDongDAO {

    /**
     * Thêm một chi tiết dịch vụ hợp đồng
     */
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

    /**
     * Thêm nhiều chi tiết dịch vụ cùng lúc (batch)
     */
    public boolean insertBatch(List<ChiTietDichVuHopDong> danhSach) {
        String sql = "INSERT INTO chi_tiet_dich_vu_hop_dong (ma_hop_dong, ma_phong, ma_dv) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (ChiTietDichVuHopDong ct : danhSach) {
                    stmt.setString(1, ct.getMaHopDong());
                    stmt.setString(2, ct.getMaPhong());
                    stmt.setString(3, ct.getMaDv());
                    stmt.addBatch();
                }

                int[] results = stmt.executeBatch();
                conn.commit();

                // Kiểm tra kết quả thực thi
                for (int result : results) {
                    if (result < 0 && result != Statement.SUCCESS_NO_INFO) {
                        return false;
                    }
                }
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy tất cả dịch vụ theo mã hợp đồng
     */
    public List<ChiTietDichVuHopDong> getByMaHopDong(String maHD) {
        List<ChiTietDichVuHopDong> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_dich_vu_hop_dong WHERE ma_hop_dong = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHD);
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

    /**
     * Lấy dịch vụ theo hợp đồng và phòng
     */
    public List<ChiTietDichVuHopDong> getByHopDongVaPhong(String maHD, String maPhong) {
        List<ChiTietDichVuHopDong> list = new ArrayList<>();
        String sql = "SELECT * FROM `chi_tiet_dich_vu_hop_dong` WHERE `ma_hop_dong` = ? AND `ma_phong` = ?";
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

    /**
     * Xóa dịch vụ theo hợp đồng và phòng
     */
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

    /**
     * Xóa một dịch vụ cụ thể của phòng trong hợp đồng
     */
    public boolean delete(String maHD, String maPhong, String maDv) {
        String sql = "DELETE FROM chi_tiet_dich_vu_hop_dong WHERE ma_hop_dong = ? AND ma_phong = ? AND ma_dv = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            stmt.setString(2, maPhong);
            stmt.setString(3, maDv);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Kiểm tra tồn tại dịch vụ
     */
    public boolean checkExists(String maHD, String maPhong, String maDv) {
        String sql = "SELECT COUNT(*) FROM chi_tiet_dich_vu_hop_dong WHERE ma_hop_dong = ? AND ma_phong = ? AND ma_dv = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            stmt.setString(2, maPhong);
            stmt.setString(3, maDv);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}