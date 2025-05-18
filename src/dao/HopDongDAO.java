package dao;

import model.HopDong;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public boolean capNhatThanhToan(String maHopDong, int soTienThanhToan, String ngayThanhToan, int trangThaiThanhToan) {
    String sql = "UPDATE hop_dong SET da_thanh_toan = ?, ngay_thanh_toan = ? WHERE ma_hop_dong = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, trangThaiThanhToan);  // Lưu trạng thái thay vì số tiền
        stmt.setString(2, ngayThanhToan);
        stmt.setString(3, maHopDong);

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public boolean capNhatThanhToanDayDu(String maHopDong, int soTienThanhToan, String ngayThanhToan, int trangThaiThanhToan) {
    String sql = "UPDATE hop_dong SET da_thanh_toan = ?, ngay_thanh_toan = ? WHERE ma_hop_dong = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, trangThaiThanhToan);  // Lưu trạng thái là 2 (đã thanh toán đầy đủ)
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

    public boolean themHopDongVaChiTiet(HopDong hd, List<String> dsPhong, Map<String, List<String>> dichVuTheoPhong) {
        Connection conn = null;
        PreparedStatement stmtHopDong = null;
        PreparedStatement stmtChiTiet = null;
        PreparedStatement stmtDichVu = null;
        PreparedStatement stmtPhong = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);  // Bắt đầu transaction

            // 1. Thêm hợp đồng
            String sqlHopDong = "INSERT INTO hop_dong (ma_hop_dong, ma_khach_hang, so_luong_phong_muon_thue, " +
                    "lich_dat_phong, ngay_bat_dau, ngay_ket_thuc, tong_ngay_thue, da_thanh_toan, " +
                    "ngay_thanh_toan, ghi_chu, dat_coc, tong_tien, phuong_thuc_thanh_toan, trang_thai) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            stmtHopDong = conn.prepareStatement(sqlHopDong);
            stmtHopDong.setString(1, hd.getMaHopDong());
            stmtHopDong.setString(2, hd.getMaKhachHang());
            stmtHopDong.setInt(3, hd.getSoLuongPhongMuonThue());
            stmtHopDong.setString(4, hd.getLichDatPhong());
            stmtHopDong.setString(5, hd.getNgayBatDau());
            stmtHopDong.setString(6, hd.getNgayKetThuc());
            stmtHopDong.setInt(7, hd.getTongNgayThue());
            stmtHopDong.setInt(8, hd.getDaThanhToan());
            stmtHopDong.setString(9, hd.getNgayThanhToan());
            stmtHopDong.setString(10, hd.getGhiChu());
            stmtHopDong.setInt(11, hd.getDatCoc());
            stmtHopDong.setInt(12, hd.getTongTien());
            stmtHopDong.setString(13, hd.getPhuongThucThanhToan());
            stmtHopDong.setString(14, hd.getTrangThai());

            int hopDongResult = stmtHopDong.executeUpdate();
            if (hopDongResult <= 0) {
                throw new SQLException("Không thể thêm hợp đồng");
            }

            // 2. Thêm chi tiết phòng và cập nhật trạng thái phòng
            String sqlChiTiet = "INSERT INTO chi_tiet_hop_dong (ma_hop_dong, ma_phong) VALUES (?, ?)";
            stmtChiTiet = conn.prepareStatement(sqlChiTiet);

            String sqlPhong = "UPDATE phong SET tinh_trang = ?, nguon_dat = ? WHERE ma_phong = ?";
            stmtPhong = conn.prepareStatement(sqlPhong);

            for (String maPhong : dsPhong) {
                // Thêm chi tiết hợp đồng
                stmtChiTiet.setString(1, hd.getMaHopDong());
                stmtChiTiet.setString(2, maPhong);
                stmtChiTiet.executeUpdate();

                // Cập nhật trạng thái phòng
                stmtPhong.setString(1, "Đang đặt");
                stmtPhong.setString(2, "hop_dong");
                stmtPhong.setString(3, maPhong);
                stmtPhong.executeUpdate();

                // 3. Thêm dịch vụ (nếu có)
                if (dichVuTheoPhong.containsKey(maPhong) && !dichVuTheoPhong.get(maPhong).isEmpty()) {
                    String sqlDichVu = "INSERT INTO chi_tiet_dich_vu_hop_dong (ma_hop_dong, ma_phong, ma_dv) VALUES (?, ?, ?)";
                    stmtDichVu = conn.prepareStatement(sqlDichVu);

                    for (String maDV : dichVuTheoPhong.get(maPhong)) {
                        stmtDichVu.setString(1, hd.getMaHopDong());
                        stmtDichVu.setString(2, maPhong);
                        stmtDichVu.setString(3, maDV);
                        stmtDichVu.executeUpdate();
                    }
                }
            }

            // Commit transaction nếu tất cả thành công
            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmtDichVu != null) stmtDichVu.close();
                if (stmtPhong != null) stmtPhong.close();
                if (stmtChiTiet != null) stmtChiTiet.close();
                if (stmtHopDong != null) stmtHopDong.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}