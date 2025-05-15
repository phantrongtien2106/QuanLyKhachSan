package dao;

import model.HopDong;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HopDongDAO {

    public boolean insert(HopDong hd) {
        String sql = """
        INSERT INTO hop_dong (
            ma_hop_dong, ma_khach_hang, so_luong_phong_muon_thue,
            lich_dat_phong, ngay_bat_dau, ngay_ket_thuc,
            tong_ngay_thue, da_thanh_toan, ngay_thanh_toan, ghi_chu,
            dat_coc, tong_tien, phuong_thuc_thanh_toan, trang_thai
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hd.getMaHopDong());
            stmt.setString(2, hd.getMaKhachHang());
            stmt.setInt(3, hd.getSoLuongPhongMuonThue());
            stmt.setString(4, hd.getLichDatPhong());
            stmt.setString(5, hd.getNgayBatDau());
            stmt.setString(6, hd.getNgayKetThuc());
            stmt.setInt(7, hd.getTongNgayThue());
            stmt.setInt(8, hd.getDaThanhToan());
            stmt.setString(9, hd.getNgayThanhToan());
            stmt.setString(10, hd.getGhiChu());
            stmt.setInt(11, hd.getDatCoc());
            stmt.setInt(12, hd.getTongTien());
            stmt.setString(13, hd.getPhuongThucThanhToan());
            stmt.setString(14, hd.getTrangThai());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HopDong> getAll() {
        List<HopDong> list = new ArrayList<>();
        String sql = "SELECT * FROM hop_dong";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                HopDong hd = new HopDong(
                        rs.getString("ma_hop_dong"),
                        rs.getString("ma_khach_hang"),
                        rs.getInt("so_luong_phong_muon_thue"),
                        rs.getString("lich_dat_phong"),
                        rs.getString("ngay_bat_dau"),
                        rs.getString("ngay_ket_thuc"),
                        rs.getInt("tong_ngay_thue"),
                        rs.getInt("da_thanh_toan"),
                        rs.getString("ngay_thanh_toan"),
                        rs.getString("ghi_chu"),
                        rs.getInt("dat_coc"),
                        rs.getInt("tong_tien"),
                        rs.getString("phuong_thuc_thanh_toan"),
                        rs.getString("trang_thai")
                );
                list.add(hd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public HopDong getByMa(String maHD) {
        String sql = "SELECT * FROM hop_dong WHERE ma_hop_dong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new HopDong(
                        rs.getString("ma_hop_dong"),
                        rs.getString("ma_khach_hang"),
                        rs.getInt("so_luong_phong_muon_thue"),
                        rs.getString("lich_dat_phong"),
                        rs.getString("ngay_bat_dau"),
                        rs.getString("ngay_ket_thuc"),
                        rs.getInt("tong_ngay_thue"),
                        rs.getInt("da_thanh_toan"),
                        rs.getString("ngay_thanh_toan"),
                        rs.getString("ghi_chu"),
                        rs.getInt("dat_coc"),
                        rs.getInt("tong_tien"),
                        rs.getString("phuong_thuc_thanh_toan"),
                        rs.getString("trang_thai")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean capNhatThanhToan(String maHopDong, int daThanhToan, String ngayThanhToan) {
        String sql = "UPDATE hop_dong SET da_thanh_toan = ?, ngay_thanh_toan = ? WHERE ma_hop_dong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, daThanhToan);
            stmt.setString(2, ngayThanhToan);
            stmt.setString(3, maHopDong);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatTrangThai(String maHopDong, String trangThai) {
        String sql = "UPDATE hop_dong SET trang_thai = ? WHERE ma_hop_dong = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trangThai);
            stmt.setString(2, maHopDong);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maHopDong) {
        String sql = "DELETE FROM hop_dong WHERE ma_hop_dong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHopDong);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(HopDong hd) {
        String sql = """
        UPDATE hop_dong SET 
            ma_khach_hang = ?, 
            so_luong_phong_muon_thue = ?,
            lich_dat_phong = ?, 
            ngay_bat_dau = ?, 
            ngay_ket_thuc = ?,
            tong_ngay_thue = ?, 
            da_thanh_toan = ?, 
            ngay_thanh_toan = ?, 
            ghi_chu = ?,
            dat_coc = ?, 
            tong_tien = ?, 
            phuong_thuc_thanh_toan = ?, 
            trang_thai = ?
        WHERE ma_hop_dong = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hd.getMaKhachHang());
            stmt.setInt(2, hd.getSoLuongPhongMuonThue());
            stmt.setString(3, hd.getLichDatPhong());
            stmt.setString(4, hd.getNgayBatDau());
            stmt.setString(5, hd.getNgayKetThuc());
            stmt.setInt(6, hd.getTongNgayThue());
            stmt.setInt(7, hd.getDaThanhToan());
            stmt.setString(8, hd.getNgayThanhToan());
            stmt.setString(9, hd.getGhiChu());
            stmt.setInt(10, hd.getDatCoc());
            stmt.setInt(11, hd.getTongTien());
            stmt.setString(12, hd.getPhuongThucThanhToan());
            stmt.setString(13, hd.getTrangThai());
            stmt.setString(14, hd.getMaHopDong());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}