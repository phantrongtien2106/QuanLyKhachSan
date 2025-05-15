package dao;

import model.PhieuDatPhong;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuDatPhongDAO {

    // Thêm mới phiếu đặt phòng
    public boolean insert(PhieuDatPhong pdp) {
        String sql = "INSERT INTO phieu_dat_phong (ma_phieu, ma_khach_hang, ngay_nhan, ngay_tra, ghi_chu, trang_thai, ngay_dat) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pdp.getMaPhieu());
            stmt.setString(2, pdp.getMaKhachHang());
            stmt.setString(3, pdp.getNgayNhan());
            stmt.setString(4, pdp.getNgayTra());
            stmt.setString(5, pdp.getGhiChu());
            stmt.setString(6, pdp.getTrangThai());
            stmt.setString(7, pdp.getNgayDat());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả phiếu đặt phòng
    public List<PhieuDatPhong> getAll() {
        List<PhieuDatPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM phieu_dat_phong";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PhieuDatPhong pdp = new PhieuDatPhong();
                pdp.setMaPhieu(rs.getString("ma_phieu"));
                pdp.setMaKhachHang(rs.getString("ma_khach_hang"));
                pdp.setNgayNhan(rs.getString("ngay_nhan"));
                pdp.setNgayTra(rs.getString("ngay_tra"));
                pdp.setGhiChu(rs.getString("ghi_chu"));
                pdp.setTrangThai(rs.getString("trang_thai"));
                pdp.setNgayDat(rs.getString("ngay_dat"));
                list.add(pdp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy danh sách phiếu chưa check-in
    public List<PhieuDatPhong> getPhieuChuaCheckIn() {
        List<PhieuDatPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM phieu_dat_phong WHERE trang_thai = 'dang_dat'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PhieuDatPhong pdp = new PhieuDatPhong();
                pdp.setMaPhieu(rs.getString("ma_phieu"));
                pdp.setMaKhachHang(rs.getString("ma_khach_hang"));
                pdp.setNgayNhan(rs.getString("ngay_nhan"));
                pdp.setNgayTra(rs.getString("ngay_tra"));
                pdp.setGhiChu(rs.getString("ghi_chu"));
                pdp.setTrangThai(rs.getString("trang_thai"));
                pdp.setNgayDat(rs.getString("ngay_dat"));
                list.add(pdp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Cập nhật trạng thái phiếu đặt phòng
    public boolean capNhatTrangThai(String maPhieu, String trangThaiMoi) {
        String sql = "UPDATE phieu_dat_phong SET trang_thai = ? WHERE ma_phieu = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trangThaiMoi);
            stmt.setString(2, maPhieu);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy phiếu đặt phòng theo trạng thái
    public List<PhieuDatPhong> getPhieuTheoTrangThai(String trangThai) {
        List<PhieuDatPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM phieu_dat_phong WHERE trang_thai = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trangThai);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PhieuDatPhong pdp = new PhieuDatPhong();
                    pdp.setMaPhieu(rs.getString("ma_phieu"));
                    pdp.setMaKhachHang(rs.getString("ma_khach_hang"));
                    pdp.setNgayNhan(rs.getString("ngay_nhan"));
                    pdp.setNgayTra(rs.getString("ngay_tra"));
                    pdp.setGhiChu(rs.getString("ghi_chu"));
                    pdp.setTrangThai(rs.getString("trang_thai"));
                    pdp.setNgayDat(rs.getString("ngay_dat"));
                    list.add(pdp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tìm kiếm phiếu đặt phòng theo từ khóa và trạng thái
    public List<PhieuDatPhong> timKiemPhieuTheoTrangThai(String keyword, String trangThai) {
        List<PhieuDatPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM phieu_dat_phong WHERE trang_thai = ? AND (ma_phieu LIKE ? OR ma_khach_hang LIKE ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trangThai);
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PhieuDatPhong pdp = new PhieuDatPhong();
                    pdp.setMaPhieu(rs.getString("ma_phieu"));
                    pdp.setMaKhachHang(rs.getString("ma_khach_hang"));
                    pdp.setNgayNhan(rs.getString("ngay_nhan"));
                    pdp.setNgayTra(rs.getString("ngay_tra"));
                    pdp.setGhiChu(rs.getString("ghi_chu"));
                    pdp.setTrangThai(rs.getString("trang_thai"));
                    pdp.setNgayDat(rs.getString("ngay_dat"));
                    list.add(pdp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy phiếu đặt phòng theo mã phiếu
    public PhieuDatPhong getByMaPhieu(String maPhieu) {
        String sql = "SELECT * FROM phieu_dat_phong WHERE ma_phieu = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhieu);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PhieuDatPhong pdp = new PhieuDatPhong();
                    pdp.setMaPhieu(rs.getString("ma_phieu"));
                    pdp.setMaKhachHang(rs.getString("ma_khach_hang"));
                    pdp.setNgayNhan(rs.getString("ngay_nhan"));
                    pdp.setNgayTra(rs.getString("ngay_tra"));
                    pdp.setGhiChu(rs.getString("ghi_chu"));
                    pdp.setTrangThai(rs.getString("trang_thai"));
                    pdp.setNgayDat(rs.getString("ngay_dat"));
                    return pdp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}