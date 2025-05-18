package dao;

import model.HoaDon;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

public boolean insert(HoaDon hd) {
        String sql = "INSERT INTO hoa_don (id, ngay_nhan, ngay_tra, ngay_thanh_toan, tong_tien, phuong_thuc_thanh_toan, trang_thai, ma_phieu, ma_hop_dong) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hd.getId());
            stmt.setString(2, hd.getNgayNhan());         // Changed from getNgayNhanPhong()
            stmt.setString(3, hd.getNgayTra());          // Changed from getNgayTraPhong()
            stmt.setString(4, hd.getNgayThanhToan());
            stmt.setDouble(5, hd.getTongTien());
            stmt.setString(6, hd.getPhuongThucThanhToan());
            stmt.setString(7, hd.getTrangThai());
            stmt.setString(8, hd.getMaPhieu());
            stmt.setString(9, hd.getMaHopDong());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean update(HoaDon hd) {
        String sql = "UPDATE hoa_don SET ngay_nhan=?, ngay_tra=?, ngay_thanh_toan=?, tong_tien=?, phuong_thuc_thanh_toan=?, trang_thai=?, ma_phieu=?, ma_hop_dong=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hd.getNgayNhan());
            stmt.setString(2, hd.getNgayTra());
            stmt.setString(3, hd.getNgayThanhToan());
            stmt.setDouble(4, hd.getTongTien());
            stmt.setString(5, hd.getPhuongThucThanhToan());
            stmt.setString(6, hd.getTrangThai());
            stmt.setString(7, hd.getMaPhieu());
            stmt.setString(8, hd.getMaHopDong());
            stmt.setString(9, hd.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM hoa_don WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTrangThai(String id, String trangThai) {
        String sql = "UPDATE hoa_don SET trang_thai=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trangThai);
            stmt.setString(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateThanhToan(String id, String ngayThanhToan, String phuongThuc) {
        String sql = "UPDATE hoa_don SET ngay_thanh_toan=?, phuong_thuc_thanh_toan=?, trang_thai='da_thanh_toan' WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ngayThanhToan);
            stmt.setString(2, phuongThuc);
            stmt.setString(3, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM hoa_don";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getString("id"),
                        rs.getString("ngay_nhan"),
                        rs.getString("ngay_tra"),
                        rs.getString("ngay_thanh_toan"),
                        rs.getDouble("tong_tien"),
                        rs.getString("phuong_thuc_thanh_toan"),
                        rs.getString("trang_thai"),
                        rs.getString("ma_phieu"),
                        rs.getString("ma_hop_dong")
                );
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public HoaDon getById(String id) {
        String sql = "SELECT * FROM hoa_don WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new HoaDon(
                        rs.getString("id"),
                        rs.getString("ngay_nhan"),
                        rs.getString("ngay_tra"),
                        rs.getString("ngay_thanh_toan"),
                        rs.getDouble("tong_tien"),
                        rs.getString("phuong_thuc_thanh_toan"),
                        rs.getString("trang_thai"),
                        rs.getString("ma_phieu"),
                        rs.getString("ma_hop_dong")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HoaDon getByMaPhieu(String maPhieu) {
        String sql = "SELECT * FROM hoa_don WHERE ma_phieu=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhieu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new HoaDon(
                        rs.getString("id"),
                        rs.getString("ngay_nhang"),
                        rs.getString("ngay_tra"),
                        rs.getString("ngay_thanh_toan"),
                        rs.getDouble("tong_tien"),
                        rs.getString("phuong_thuc_thanh_toan"),
                        rs.getString("trang_thai"),
                        rs.getString("ma_phieu"),
                        rs.getString("ma_hop_dong")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HoaDon getByMaHopDong(String maHD) {
        String sql = "SELECT * FROM hoa_don WHERE ma_hop_dong=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new HoaDon(
                        rs.getString("id"),
                        rs.getString("ngay_nhan"),
                        rs.getString("ngay_tra"),
                        rs.getString("ngay_thanh_toan"),
                        rs.getDouble("tong_tien"),
                        rs.getString("phuong_thuc_thanh_toan"),
                        rs.getString("trang_thai"),
                        rs.getString("ma_phieu"),
                        rs.getString("ma_hop_dong")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDon> getByTrangThai(String trangThai) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM hoa_don WHERE trang_thai=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trangThai);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getString("id"),
                        rs.getString("ngay_nhan"),
                        rs.getString("ngay_tra"),
                        rs.getString("ngay_thanh_toan"),
                        rs.getDouble("tong_tien"),
                        rs.getString("phuong_thuc_thanh_toan"),
                        rs.getString("trang_thai"),
                        rs.getString("ma_phieu"),
                        rs.getString("ma_hop_dong")
                );
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDon> getByDateRange(String tuNgay, String denNgay) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM hoa_don WHERE ngay_thanh_toan BETWEEN ? AND ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tuNgay);
            stmt.setString(2, denNgay);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getString("id"),
                        rs.getString("ngay_nhan"),
                        rs.getString("ngay_tra"),
                        rs.getString("ngay_thanh_toan"),
                        rs.getDouble("tong_tien"),
                        rs.getString("phuong_thuc_thanh_toan"),
                        rs.getString("trang_thai"),
                        rs.getString("ma_phieu"),
                        rs.getString("ma_hop_dong")
                );
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}