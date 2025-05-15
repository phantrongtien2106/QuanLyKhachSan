//package System.helper;
//
//import System.dao.DBConnection;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class MaNguoiDungHelper {
//    private Connection DBConnection;
//
//    public MaNguoiDungHelper() {
//        this.DBConnection = new DBConnection().connection;
//    }
//
//    public String taoMaTuDong(String loai) {
//        String prefix = switch (loai.toLowerCase()) {
//            case "admin" -> "NVA";
//            case "manager" -> "NVQ";
//            case "receptionist" -> "NV";
//            case "khach_hang" -> "KH";
//            default -> "TK";
//        };
//
//        String sql = "SELECT ma_nguoi_dung FROM tai_khoan WHERE ma_nguoi_dung LIKE ? ORDER BY ma_nguoi_dung DESC LIMIT 1";
//        try (PreparedStatement stmt = DBConnection.prepareStatement(sql)) {
//            stmt.setString(1, prefix + "%");
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                String lastId = rs.getString("ma_nguoi_dung");
//                String soStr = lastId.substring(prefix.length());
//
//                // ✅ Kiểm tra chuỗi có phải số không
//                if (soStr.matches("\\d+")) {
//                    int so = Integer.parseInt(soStr) + 1;
//                    return String.format("%s%03d", prefix, so);
//                } else {
//                    return prefix + "001"; // fallback nếu hậu tố không phải số
//                }
//            } else {
//                return prefix + "001";
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return prefix + "001";
//        }
//    }
//
//}
