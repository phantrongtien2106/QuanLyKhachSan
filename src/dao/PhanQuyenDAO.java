package System.dao;

import System.model.ChucNang;
import System.model.LoaiQuyen;
import java.sql.*;
import java.util.*;

public class PhanQuyenDAO {
    private DBConnection dbConnection;

    public PhanQuyenDAO() {
        try {
            dbConnection = DBConnection.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LoaiQuyen> layLoaiQuyenTheoChucNang(String maVaiTro, String maChucNang) {
        List<LoaiQuyen> list = new ArrayList<>();
        String sql = "SELECT qcn.ma_loai_quyen FROM quyen_chuc_nang qcn " +
                "JOIN quyen_vai_tro qvt ON qcn.ma_quyen = qvt.ma_quyen " +
                "WHERE qvt.ma_vai_tro = ? AND qcn.ma_chuc_nang = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, maVaiTro);
            ps.setString(2, maChucNang);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(LoaiQuyen.valueOf(rs.getString("ma_loai_quyen")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ChucNang> layDanhSachChucNang() {
        List<ChucNang> list = new ArrayList<>();
        String sql = "SELECT * FROM chuc_nang";

        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ChucNang(rs.getString("ma_chuc_nang"), rs.getString("ten_chuc_nang")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean coQuyen(String maVaiTro, String maChucNang, LoaiQuyen loai) {
        String sql = "SELECT * FROM quyen_vai_tro qvt " +
                "JOIN quyen_chuc_nang qcn ON qvt.ma_quyen = qcn.ma_quyen " +
                "WHERE qvt.ma_vai_tro = ? AND qcn.ma_chuc_nang = ? AND qcn.ma_loai_quyen = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, maVaiTro);
            ps.setString(2, maChucNang);
            ps.setString(3, loai.name());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatLoaiQuyen(String maVaiTro, String maChucNang, LoaiQuyen loai, boolean coQuyen) {
        try {
            // Check if permission exists
            boolean exists;
            String maQuyen = null;

            String sqlCheck = "SELECT * FROM quyen_vai_tro qvt " +
                    "JOIN quyen_chuc_nang qcn ON qvt.ma_quyen = qcn.ma_quyen " +
                    "WHERE qvt.ma_vai_tro = ? AND qcn.ma_chuc_nang = ? AND qcn.ma_loai_quyen = ?";

            try (PreparedStatement checkStmt = dbConnection.getConnection().prepareStatement(sqlCheck)) {
                checkStmt.setString(1, maVaiTro);
                checkStmt.setString(2, maChucNang);
                checkStmt.setString(3, loai.name());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    exists = rs.next();
                }
            }

            // Get maQuyen for role
            String getQuyen = "SELECT ma_quyen FROM quyen_vai_tro WHERE ma_vai_tro = ?";
            try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(getQuyen)) {
                ps.setString(1, maVaiTro);
                try (ResultSet q = ps.executeQuery()) {
                    if (q.next()) maQuyen = q.getString("ma_quyen");
                }
            }

            if (coQuyen && !exists && maQuyen != null) {
                String insert = "INSERT INTO quyen_chuc_nang (ma_quyen, ma_chuc_nang, ma_loai_quyen) VALUES (?, ?, ?)";
                try (PreparedStatement is = dbConnection.getConnection().prepareStatement(insert)) {
                    is.setString(1, maQuyen);
                    is.setString(2, maChucNang);
                    is.setString(3, loai.name());
                    return is.executeUpdate() > 0;
                }
            } else if (!coQuyen && exists && maQuyen != null) {
                String del = "DELETE FROM quyen_chuc_nang WHERE ma_quyen = ? AND ma_chuc_nang = ? AND ma_loai_quyen = ?";
                try (PreparedStatement delps = dbConnection.getConnection().prepareStatement(del)) {
                    delps.setString(1, maQuyen);
                    delps.setString(2, maChucNang);
                    delps.setString(3, loai.name());
                    return delps.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}