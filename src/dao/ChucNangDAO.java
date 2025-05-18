package dao;

import model.ChucNang;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChucNangDAO {
    private DBConnection dbConnection;

    public ChucNangDAO() {
        dbConnection = (DBConnection) DBConnection.getConnection();
    }

    public boolean themChucNang(ChucNang cn) {
        try {
            String sql = "INSERT INTO chuc_nang (ma_chuc_nang, ten_chuc_nang) VALUES (?, ?)";
            PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql);
            ps.setString(1, cn.getMaChucNang());
            ps.setString(2, cn.getTenChucNang());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ChucNang> layDanhSachChucNang() {
        List<ChucNang> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM chuc_nang";
            try (Statement stmt = dbConnection.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new ChucNang(rs.getString("ma_chuc_nang"), rs.getString("ten_chuc_nang")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean xoaChucNang(String maChucNang) {
        try {
            String sql = "DELETE FROM chuc_nang WHERE ma_chuc_nang = ?";
            PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql);
            ps.setString(1, maChucNang);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}