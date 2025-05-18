package helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaNguoiDungHelper {
    private Connection connection;

    public MaNguoiDungHelper() {
        this.connection = util.DBConnection.getConnection();
    }

    public String taoMaTuDong(String loai) {
        // Cập nhật switch với nhiều vai trò hơn
        String prefix = switch (loai.toLowerCase()) {
            case "admin" -> "NVA";
            case "manager", "quanly" -> "NVQ";
            case "receptionist", "letan" -> "NVL";
            case "staff", "nhanvien" -> "NV";
            case "khach_hang", "khachhang" -> "KH";
            default -> "TK";
        };

        String sql = "SELECT ma_nguoi_dung FROM tai_khoan WHERE ma_nguoi_dung LIKE ? ORDER BY ma_nguoi_dung DESC LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, prefix + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String lastId = rs.getString("ma_nguoi_dung");
                    String soStr = lastId.substring(prefix.length());

                    if (soStr.matches("\\d+")) {
                        int so = Integer.parseInt(soStr) + 1;
                        return String.format("%s%03d", prefix, so);
                    } else {
                        return prefix + "001";
                    }
                } else {
                    return prefix + "001";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý tốt hơn khi có lỗi
            System.err.println("Lỗi khi tạo mã tự động: " + e.getMessage());
            return prefix + "001";
        }
    }
}