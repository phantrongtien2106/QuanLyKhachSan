package System.dao;

import System.model.NhanVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private DBConnection dbConnection;

    public NhanVienDAO() {
        try {
            dbConnection = DBConnection.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO nhan_vien (ma_nhan_vien, ngay_sinh, luong) VALUES (?, ?, ?)";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, nv.getMaNhanVien());
            ps.setDate(2, nv.getNgaySinh());
            ps.setDouble(3, nv.getLuong());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NhanVien> layDanhSachNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM nhan_vien";
        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new NhanVien(
                        rs.getString("ma_nhan_vien"),
                        rs.getDate("ngay_sinh"),
                        rs.getDouble("luong")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public NhanVien timNhanVienTheoMa(String ma) {
        String sql = "SELECT * FROM nhan_vien WHERE ma_nhan_vien = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, ma);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new NhanVien(
                            rs.getString("ma_nhan_vien"),
                            rs.getDate("ngay_sinh"),
                            rs.getDouble("luong")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean capNhatNhanVien(NhanVien nv) {
        String sql = "UPDATE nhan_vien SET ngay_sinh = ?, luong = ? WHERE ma_nhan_vien = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setDate(1, nv.getNgaySinh());
            ps.setDouble(2, nv.getLuong());
            ps.setString(3, nv.getMaNhanVien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}