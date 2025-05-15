package dao;

import model.KhachHang;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    // Lấy toàn bộ danh sách khách hàng
    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM khach_hang";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getString("ma_khach_hang"),
                        rs.getString("ten_khach_hang"),
                        rs.getString("cccd"),
                        rs.getString("so_dien_thoai"),
                        rs.getString("dia_chi")
                );
                list.add(kh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tìm khách hàng theo mã
    public KhachHang getKhachHangByMa(String maKH) {
        String sql = "SELECT * FROM khach_hang WHERE ma_khach_hang = ?";
        KhachHang kh = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                kh = new KhachHang(
                        rs.getString("ma_khach_hang"),
                        rs.getString("ten_khach_hang"),       // ✅ sửa đúng tên cột
                        rs.getString("cccd"),
                        rs.getString("so_dien_thoai"),        // ✅ sửa đúng tên cột
                        rs.getString("dia_chi")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kh;
    }

    // Thêm khách hàng mới
    public boolean insertKhachHang(KhachHang kh) {
        String sql = "INSERT INTO khach_hang (ma_khach_hang, ten_khach_hang, cccd, so_dien_thoai, dia_chi) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kh.getMaKhachHang());
            stmt.setString(2, kh.getHoTen());
            stmt.setString(3, kh.getCccd());
            stmt.setString(4, kh.getSdt());
            stmt.setString(5, kh.getDiaChi());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin khách hàng
    public boolean updateKhachHang(KhachHang kh) {
        String sql = "UPDATE khach_hang SET ten_khach_hang = ?, cccd = ?, so_dien_thoai = ?, dia_chi = ? WHERE ma_khach_hang = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kh.getHoTen());
            stmt.setString(2, kh.getCccd());
            stmt.setString(3, kh.getSdt());
            stmt.setString(4, kh.getDiaChi());
            stmt.setString(5, kh.getMaKhachHang());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa khách hàng theo mã
    public boolean deleteKhachHang(String maKH) {
        String sql = "DELETE FROM khach_hang WHERE ma_khach_hang = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maKH);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tạo mã khách hàng tiếp theo (VD: KH001, KH002,...)
    public String getNextMaKhachHang() {
        String sql = "SELECT ma_khach_hang FROM khach_hang WHERE ma_khach_hang LIKE 'KH%' ORDER BY ma_khach_hang DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastMa = rs.getString("ma_khach_hang"); // VD: "KH005"
                int num = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("KH%03d", num);
            } else {
                return "KH001";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "KH001"; // fallback
        }
    }


    public boolean insertFullKhachHang(KhachHang kh, String matKhau) {
        String insertTaiKhoan = "INSERT INTO tai_khoan (ma_nguoi_dung, so_dien_thoai, ho_ten, cccd, email, dia_chi, mat_khau) " +
                "VALUES (?, ?, ?, ?, NULL, ?, ?)";
        String insertVaiTro = "INSERT INTO tai_khoan_vai_tro (ma_nguoi_dung, ma_vai_tro) VALUES (?, 'USER')";
        String insertKhachHang = "INSERT INTO khach_hang (ma_khach_hang, ten_khach_hang, cccd, so_dien_thoai, dia_chi, loai_khach) " +
                "VALUES (?, ?, ?, ?, ?, 'Cá nhân')";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement stmt1 = conn.prepareStatement(insertTaiKhoan);
                    PreparedStatement stmt2 = conn.prepareStatement(insertVaiTro);
                    PreparedStatement stmt3 = conn.prepareStatement(insertKhachHang)
            ) {
                stmt1.setString(1, kh.getMaKhachHang());
                stmt1.setString(2, kh.getSdt());
                stmt1.setString(3, kh.getHoTen());
                stmt1.setString(4, kh.getCccd());
                stmt1.setString(5, kh.getDiaChi());
                stmt1.setString(6, matKhau);
                stmt1.executeUpdate();

                stmt2.setString(1, kh.getMaKhachHang());
                stmt2.executeUpdate();

                stmt3.setString(1, kh.getMaKhachHang());
                stmt3.setString(2, kh.getHoTen());
                stmt3.setString(3, kh.getCccd());
                stmt3.setString(4, kh.getSdt());
                stmt3.setString(5, kh.getDiaChi());
                stmt3.executeUpdate();

                conn.commit();
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
}
