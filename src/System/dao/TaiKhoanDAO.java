package System.dao;

import java.sql.*;
import java.util.*;
import System.model.TaiKhoan;
import System.model.ChucNang;
import System.model.VaiTro;
import System.model.LoaiQuyen;

public class TaiKhoanDAO {
    private DBConnection dbConnection;

    public TaiKhoanDAO() {
        try {
            dbConnection = DBConnection.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==== TÀI KHOẢN ====
    public List<TaiKhoan> layDanhSachTaiKhoan() {
        List<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
        String sql = "SELECT * FROM tai_khoan";

        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                        rs.getString("ma_nguoi_dung"),
                        rs.getString("ho_ten"),
                        new ArrayList<>()
                );
                tk.setSoDienThoai(rs.getString("so_dien_thoai"));
                tk.setEmail(rs.getString("email"));
                tk.setDiaChi(rs.getString("dia_chi"));
                danhSachTaiKhoan.add(tk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachTaiKhoan;
    }

    public boolean ganVaiTroChoTaiKhoan(String maNguoiDung, String maVaiTro) {
        // Kiểm tra xem vai trò đã được gán cho người dùng chưa
        String checkSql = "SELECT COUNT(*) FROM tai_khoan_vai_tro WHERE ma_nguoi_dung = ? AND ma_vai_tro = ?";
        String insertSql = "INSERT INTO tai_khoan_vai_tro (ma_nguoi_dung, ma_vai_tro) VALUES (?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            // Kiểm tra xem vai trò đã tồn tại cho người dùng này chưa
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, maNguoiDung);
                checkStmt.setString(2, maVaiTro);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    // Vai trò đã tồn tại cho người dùng này
                    return false;
                }
            }

            // Nếu chưa tồn tại, thực hiện thêm mới
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, maNguoiDung);
                insertStmt.setString(2, maVaiTro);
                return insertStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<VaiTro> layVaiTroCuaTaiKhoan(String maNguoiDung) {
        List<VaiTro> danhSach = new ArrayList<>();
        String sql = "SELECT vt.ma_vai_tro, vt.mo_ta FROM vai_tro vt " +
                "JOIN tai_khoan_vai_tro tvt ON vt.ma_vai_tro = tvt.ma_vai_tro " +
                "WHERE tvt.ma_nguoi_dung = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, maNguoiDung);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    danhSach.add(new VaiTro(rs.getString("ma_vai_tro"), rs.getString("mo_ta")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public boolean coVaiTro(String maNguoiDung, String maVaiTro) {
        String sql = "SELECT * FROM tai_khoan_vai_tro WHERE ma_nguoi_dung = ? AND ma_vai_tro = ?";

        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, maNguoiDung);
            stmt.setString(2, maVaiTro);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean themTaiKhoan(TaiKhoan tk) {
        String sql = "INSERT INTO tai_khoan (ma_nguoi_dung, ho_ten, cccd, email, so_dien_thoai, dia_chi, mat_khau) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, tk.getMaNguoiDung());
            ps.setString(2, tk.getHoTen());
            ps.setString(3, tk.getCccd());
            ps.setString(4, tk.getEmail());
            ps.setString(5, tk.getSoDienThoai());
            ps.setString(6, tk.getDiaChi());
            ps.setString(7, tk.getMatKhau());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean kiemTraTonTai(String soDienThoai, String cccd) {
        String sql = "SELECT * FROM tai_khoan WHERE so_dien_thoai = ? OR cccd = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, soDienThoai);
            ps.setString(2, cccd);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean kiemTraDangNhap(String soDienThoai, String matKhau) {
        String sql = "SELECT * FROM tai_khoan WHERE so_dien_thoai = ? AND mat_khau = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, soDienThoai);
            ps.setString(2, matKhau);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public TaiKhoan layThongTinTaiKhoan(String soDienThoai) {
        String sql = "SELECT * FROM tai_khoan WHERE so_dien_thoai = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, soDienThoai);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TaiKhoan tk = new TaiKhoan(
                            rs.getString("ma_nguoi_dung"),
                            rs.getString("ho_ten"),
                            new ArrayList<>()
                    );
                    tk.setSoDienThoai(rs.getString("so_dien_thoai"));
                    tk.setEmail(rs.getString("email"));
                    tk.setCccd(rs.getString("cccd"));
                    tk.setDiaChi(rs.getString("dia_chi"));
                    return tk;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean xoaTaiKhoan(String maNguoiDung) {
        String sql = "DELETE FROM tai_khoan WHERE ma_nguoi_dung = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, maNguoiDung);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<VaiTro> layDanhSachVaiTro() {
        List<VaiTro> list = new ArrayList<>();
        String sql = "SELECT * FROM vai_tro";

        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new VaiTro(rs.getString("ma_vai_tro"), rs.getString("mo_ta")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean kiemTraQuyenChucNang(String maVaiTro, String maChucNang) {
        String sql = "SELECT * FROM quyen_vai_tro qvt " +
                "JOIN quyen_chuc_nang qcn ON qvt.ma_quyen = qcn.ma_quyen " +
                "WHERE qvt.ma_vai_tro = ? AND qcn.ma_chuc_nang = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, maVaiTro);
            ps.setString(2, maChucNang);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean coQuyen(String maNguoiDung, String maChucNang, LoaiQuyen loaiQuyen) {
        String sql = "SELECT * FROM tai_khoan_vai_tro tvt " +
                "JOIN quyen_vai_tro qvt ON tvt.ma_vai_tro = qvt.ma_vai_tro " +
                "JOIN quyen_chuc_nang qcn ON qvt.ma_quyen = qcn.ma_quyen " +
                "WHERE tvt.ma_nguoi_dung = ? AND qcn.ma_chuc_nang = ? AND qcn.ma_loai_quyen = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, maNguoiDung);
            ps.setString(2, maChucNang);
            ps.setString(3, loaiQuyen.name());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatMatKhau(TaiKhoan tk) {
        String sql = "UPDATE tai_khoan SET mat_khau = ? WHERE ma_nguoi_dung = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, tk.getMatKhau());
            ps.setString(2, tk.getMaNguoiDung());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public TaiKhoan layThongTinTheoMa(String maNguoiDung) {
        String sql = "SELECT * FROM tai_khoan WHERE ma_nguoi_dung = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, maNguoiDung);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TaiKhoan tk = new TaiKhoan(
                            rs.getString("ma_nguoi_dung"),
                            rs.getString("ho_ten"),
                            new ArrayList<>()
                    );
                    tk.setSoDienThoai(rs.getString("so_dien_thoai"));
                    tk.setEmail(rs.getString("email"));
                    tk.setCccd(rs.getString("cccd"));
                    tk.setDiaChi(rs.getString("dia_chi"));
                    tk.setMatKhau(rs.getString("mat_khau"));
                    return tk;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean capNhatThongTin(TaiKhoan tk) {
        String sql = "UPDATE tai_khoan SET ho_ten = ?, email = ?, so_dien_thoai = ?, dia_chi = ? WHERE ma_nguoi_dung = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, tk.getHoTen());
            ps.setString(2, tk.getEmail());
            ps.setString(3, tk.getSoDienThoai());
            ps.setString(4, tk.getDiaChi());
            ps.setString(5, tk.getMaNguoiDung());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaVaiTroCuaTaiKhoan(String maNguoiDung, String maVaiTro) {
        String sql = "DELETE FROM tai_khoan_vai_tro WHERE ma_nguoi_dung = ? AND ma_vai_tro = ?";

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, maNguoiDung);
            ps.setString(2, maVaiTro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}