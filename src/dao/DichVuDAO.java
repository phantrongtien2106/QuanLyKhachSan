package dao;

import model.DichVu;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DichVuDAO {

    // Lấy toàn bộ danh sách dịch vụ
    public List<DichVu> getAll() {
        List<DichVu> ds = new ArrayList<>();
        String sql = "SELECT * FROM dich_vu";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DichVu dv = new DichVu();
                dv.setMaDv(rs.getString("ma_dv"));         // Sửa từ setMaDichVu sang setMaDv
                dv.setTenDv(rs.getString("ten_dv"));       // Sửa từ setTenDichVu sang setTenDv
                dv.setGia(rs.getDouble("gia"));
                ds.add(dv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }

    // Lấy dịch vụ theo mã
    public DichVu getByMaDv(String maDv) {
        String sql = "SELECT * FROM dich_vu WHERE ma_dv = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maDv);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                DichVu dv = new DichVu();
                dv.setMaDv(rs.getString("ma_dv"));
                dv.setTenDv(rs.getString("ten_dv"));
                dv.setGia(rs.getDouble("gia"));
                return dv;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Thêm dịch vụ
    public boolean insert(DichVu dv) {
        String sql = "INSERT INTO dich_vu (ma_dv, ten_dv, gia) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dv.getMaDv());              // Sửa từ getMaDichVu sang getMaDv
            stmt.setString(2, dv.getTenDv());             // Sửa từ getTenDichVu sang getTenDv
            stmt.setDouble(3, dv.getGia());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật dịch vụ
    public boolean update(DichVu dv) {
        String sql = "UPDATE dich_vu SET ten_dv = ?, gia = ? WHERE ma_dv = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dv.getTenDv());
            stmt.setDouble(2, dv.getGia());
            stmt.setString(3, dv.getMaDv());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa dịch vụ
    public boolean delete(String maDv) {
        String sql = "DELETE FROM dich_vu WHERE ma_dv = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maDv);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}