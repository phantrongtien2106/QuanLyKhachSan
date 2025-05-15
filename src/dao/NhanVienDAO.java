package dao;

import model.NhanVien;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public List<NhanVien> getAll() {
        List<NhanVien> danhSachNhanVien = new ArrayList<>();
        String sql = "SELECT * FROM nhan_vien";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("ma_nhan_vien"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setChucVu(rs.getString("chuc_vu"));
                nv.setSoDienThoai(rs.getString("so_dien_thoai"));
                nv.setEmail(rs.getString("email"));
                nv.setDiaChi(rs.getString("dia_chi"));
                // Không lấy mật khẩu ra khỏi DB vì lý do bảo mật
                danhSachNhanVien.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachNhanVien;
    }

    public NhanVien getByMa(String maNhanVien) {
        String sql = "SELECT * FROM nhan_vien WHERE ma_nhan_vien = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNhanVien);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    NhanVien nv = new NhanVien();
                    nv.setMaNhanVien(rs.getString("ma_nhan_vien"));
                    nv.setHoTen(rs.getString("ho_ten"));
                    nv.setChucVu(rs.getString("chuc_vu"));
                    nv.setSoDienThoai(rs.getString("so_dien_thoai"));
                    nv.setEmail(rs.getString("email"));
                    nv.setDiaChi(rs.getString("dia_chi"));
                    return nv;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public NhanVien kiemTraDangNhap(String maNhanVien, String matKhau) {
        String sql = "SELECT * FROM nhan_vien WHERE ma_nhan_vien = ? AND mat_khau = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNhanVien);
            ps.setString(2, matKhau); // Trong thực tế nên dùng hàm băm mật khẩu

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    NhanVien nv = new NhanVien();
                    nv.setMaNhanVien(rs.getString("ma_nhan_vien"));
                    nv.setHoTen(rs.getString("ho_ten"));
                    nv.setChucVu(rs.getString("chuc_vu"));
                    nv.setSoDienThoai(rs.getString("so_dien_thoai"));
                    nv.setEmail(rs.getString("email"));
                    nv.setDiaChi(rs.getString("dia_chi"));
                    return nv;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean them(NhanVien nv) {
        String sql = "INSERT INTO nhan_vien (ma_nhan_vien, ho_ten, chuc_vu, so_dien_thoai, email, dia_chi, mat_khau) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getMaNhanVien());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getChucVu());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getEmail());
            ps.setString(6, nv.getDiaChi());
            ps.setString(7, nv.getMatKhau());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhat(NhanVien nv) {
        String sql = "UPDATE nhan_vien SET ho_ten = ?, chuc_vu = ?, so_dien_thoai = ?, " +
                    "email = ?, dia_chi = ? WHERE ma_nhan_vien = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChucVu());
            ps.setString(3, nv.getSoDienThoai());
            ps.setString(4, nv.getEmail());
            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getMaNhanVien());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(String maNhanVien) {
        String sql = "DELETE FROM nhan_vien WHERE ma_nhan_vien = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNhanVien);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatMatKhau(NhanVien nv) {
        String sql = "UPDATE nhan_vien SET mat_khau = ? WHERE ma_nhan_vien = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getMatKhau());
            ps.setString(2, nv.getMaNhanVien());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}