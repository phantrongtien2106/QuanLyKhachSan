package dao;

import model.CheckOut;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckOutDAO {

    public boolean insert(CheckOut co) {
        String sql = "INSERT INTO check_out (ma_checkout, ma_phieu, ma_hop_dong, ngay_checkout, nhan_vien, ghi_chu) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, co.getMaCheckOut());

            if (co.getMaPhieu().startsWith("PDP")) {
                stmt.setString(2, co.getMaPhieu());
                stmt.setNull(3, Types.VARCHAR);
            } else {
                stmt.setNull(2, Types.VARCHAR);
                stmt.setString(3, co.getMaPhieu());
            }

            stmt.setString(4, co.getNgayCheckOut());
            stmt.setString(5, co.getNhanVien());
            stmt.setString(6, co.getGhiChu());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CheckOut> getAll() {
        List<CheckOut> list = new ArrayList<>();
        String sql = "SELECT * FROM check_out ORDER BY ngay_checkout DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String ma = rs.getString("ma_checkout");
                String maPhieu = rs.getString("ma_phieu");
                String maHopDong = rs.getString("ma_hop_dong");
                String maDoiTuong = (maPhieu != null) ? maPhieu : maHopDong;

                String ngay = rs.getString("ngay_checkout");
                String nv = rs.getString("nhan_vien");
                String note = rs.getString("ghi_chu");

                CheckOut co = new CheckOut(ma, maDoiTuong, ngay, nv, note);
                list.add(co);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public CheckOut getByMa(String maCheckOut) {
        String sql = "SELECT * FROM check_out WHERE ma_checkout = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maCheckOut);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String maPhieu = rs.getString("ma_phieu");
                String maHopDong = rs.getString("ma_hop_dong");
                String maDoiTuong = (maPhieu != null) ? maPhieu : maHopDong;

                return new CheckOut(
                        rs.getString("ma_checkout"),
                        maDoiTuong,
                        rs.getString("ngay_checkout"),
                        rs.getString("nhan_vien"),
                        rs.getString("ghi_chu")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<CheckOut> getByMaPhieuHoacHD(String maPhieuHoacHD) {
        List<CheckOut> list = new ArrayList<>();
        String sql = "SELECT * FROM check_out WHERE ma_phieu = ? OR ma_hop_dong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhieuHoacHD);
            stmt.setString(2, maPhieuHoacHD);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maPhieu = rs.getString("ma_phieu");
                String maHopDong = rs.getString("ma_hop_dong");
                String maDoiTuong = (maPhieu != null) ? maPhieu : maHopDong;

                CheckOut co = new CheckOut(
                        rs.getString("ma_checkout"),
                        maDoiTuong,
                        rs.getString("ngay_checkout"),
                        rs.getString("nhan_vien"),
                        rs.getString("ghi_chu")
                );
                list.add(co);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean delete(String maCheckOut) {
        String sql = "DELETE FROM check_out WHERE ma_checkout = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maCheckOut);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateGhiChu(String maCheckOut, String ghiChu) {
        String sql = "UPDATE check_out SET ghi_chu = ? WHERE ma_checkout = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ghiChu);
            stmt.setString(2, maCheckOut);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getSoLuongTheoNgay(String ngay) {
        String sql = "SELECT COUNT(*) FROM check_out WHERE ngay_checkout LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ngay + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}