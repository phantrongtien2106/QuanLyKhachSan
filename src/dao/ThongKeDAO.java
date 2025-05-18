package dao;

import model.*;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    public ThongKeDAO() {
        // Không cần khởi tạo gì trong constructor
    }


    // Thống kê doanh thu theo khoảng thời gian
    public TongDoanhThuThongKe thongKeDoanhThu(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        TongDoanhThuThongKe result = new TongDoanhThuThongKe();
        result.setNgayBatDau(ngayBatDau);
        result.setNgayKetThuc(ngayKetThuc);

        Connection conn = null;
        PreparedStatement stmtHoaDon = null;
        PreparedStatement stmtHopDong = null;
        PreparedStatement stmtDichVu = null;
        PreparedStatement stmtPhong = null;
        ResultSet rsHoaDon = null;
        ResultSet rsHopDong = null;
        ResultSet rsDichVu = null;
        ResultSet rsPhong = null;

        try {
            // Lấy kết nối từ DBConnection
            conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return result;
            }

            // Thống kê doanh thu từ hóa đơn
            String sqlHoaDon = "SELECT COUNT(*) as soLuong, SUM(tong_tien) as doanhThu " +
                               "FROM hoa_don " +
                               "WHERE ngay_tra_phong BETWEEN ? AND ? " +
                               "AND trang_thai = 'da_thanh_toan'";

            stmtHoaDon = conn.prepareStatement(sqlHoaDon);
            stmtHoaDon.setDate(1, java.sql.Date.valueOf(ngayBatDau));
            stmtHoaDon.setDate(2, java.sql.Date.valueOf(ngayKetThuc));
            rsHoaDon = stmtHoaDon.executeQuery();

            if (rsHoaDon.next()) {
                result.setSoLuongHoaDon(rsHoaDon.getInt("soLuong"));
                result.setDoanhThuHoaDon(rsHoaDon.getDouble("doanhThu"));
            }

            // Thống kê doanh thu từ hợp đồng
            String sqlHopDong = "SELECT COUNT(*) as soLuong, SUM(tong_tien) as doanhThu " +
                                "FROM hop_dong " +
                                "WHERE ngay_ket_thuc BETWEEN ? AND ? " +
                                "AND trang_thai = 'da_thanh_toan'";

            stmtHopDong = conn.prepareStatement(sqlHopDong);
            stmtHopDong.setDate(1, java.sql.Date.valueOf(ngayBatDau));
            stmtHopDong.setDate(2, java.sql.Date.valueOf(ngayKetThuc));
            rsHopDong = stmtHopDong.executeQuery();

            if (rsHopDong.next()) {
                result.setSoLuongHopDong(rsHopDong.getInt("soLuong"));
                result.setDoanhThuHopDong(rsHopDong.getDouble("doanhThu"));
            }

            // Thống kê doanh thu từ dịch vụ
            String sqlDichVu = "SELECT COUNT(ct.ma_dv) as soLuong, SUM(dv.gia) as doanhThu " +
                               "FROM chi_tiet_dich_vu ct " +
                               "JOIN dich_vu dv ON ct.ma_dv = dv.ma_dv " +
                               "JOIN phieu_dat_phong pdp ON ct.ma_phieu = pdp.ma_phieu " +
                               "WHERE pdp.ngay_tra BETWEEN ? AND ? " +
                               "AND pdp.trang_thai = 'da_thanh_toan'";

            stmtDichVu = conn.prepareStatement(sqlDichVu);
            stmtDichVu.setDate(1, java.sql.Date.valueOf(ngayBatDau));
            stmtDichVu.setDate(2, java.sql.Date.valueOf(ngayKetThuc));
            rsDichVu = stmtDichVu.executeQuery();

            if (rsDichVu.next()) {
                result.setSoLuongDichVu(rsDichVu.getInt("soLuong"));
                result.setDoanhThuDichVu(rsDichVu.getDouble("doanhThu"));
            }

            // Thống kê doanh thu từ phòng (tính từ chi tiết phiếu đặt phòng)
            String sqlPhong = "SELECT SUM(ctpdp.don_gia) as doanhThuPhong " +
                    "FROM phieu_dat_phong pdp " +
                    "JOIN chitiet_phieu_dat_phong ctpdp ON pdp.ma_phieu = ctpdp.ma_phieu " +
                    "WHERE pdp.ngay_tra BETWEEN ? AND ? " +
                    "AND pdp.trang_thai = 'da_thanh_toan'";

            stmtPhong = conn.prepareStatement(sqlPhong);
            stmtPhong.setDate(1, java.sql.Date.valueOf(ngayBatDau));
            stmtPhong.setDate(2, java.sql.Date.valueOf(ngayKetThuc));
            rsPhong = stmtPhong.executeQuery();

            if (rsPhong.next()) {
                result.setDoanhThuPhong(rsPhong.getDouble("doanhThuPhong"));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng tất cả các tài nguyên sử dụng DBConnection
            DBConnection.closeResultSet(rsHoaDon);
            DBConnection.closeResultSet(rsHopDong);
            DBConnection.closeResultSet(rsDichVu);
            DBConnection.closeResultSet(rsPhong);
            DBConnection.closeStatement(stmtHoaDon);
            DBConnection.closeStatement(stmtHopDong);
            DBConnection.closeStatement(stmtDichVu);
            DBConnection.closeStatement(stmtPhong);
            DBConnection.closeConnection(conn);
        }

        return result;
    }

    // Thống kê doanh thu theo tháng
    public List<DoanhThuThongKe> thongKeDoanhThuTheoThang(int nam) {
        List<DoanhThuThongKe> result = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Lấy kết nối từ DBConnection
            conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return result;
            }

            String sql = "SELECT MONTH(ngay_tra_phong) as thang, " +
                         "COUNT(*) as soLuong, " +
                         "SUM(tong_tien) as doanhThu " +
                         "FROM hoa_don " +
                         "WHERE YEAR(ngay_tra_phong) = ? " +
                         "AND trang_thai = 'da_thanh_toan' " +
                         "GROUP BY MONTH(ngay_tra_phong) " +
                         "ORDER BY MONTH(ngay_tra_phong)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, nam);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int thang = rs.getInt("thang");
                int soLuong = rs.getInt("soLuong");
                double doanhThu = rs.getDouble("doanhThu");

                DoanhThuThongKe doanhThuThongKe = new DoanhThuThongKe(thang, doanhThu, soLuong);
                doanhThuThongKe.setNam(nam);
                result.add(doanhThuThongKe);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng tất cả các tài nguyên
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(stmt);
            DBConnection.closeConnection(conn);
        }

        return result;
    }

    // Thống kê loại phòng
    public List<PhongThongKe> thongKeLoaiPhong() {
        List<PhongThongKe> result = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Lấy kết nối từ DBConnection
            conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return result;
            }

            String sql = "SELECT lp.ten_loai as loaiPhong, COUNT(p.ma_phong) as soLuong " +
                         "FROM phong p " +
                         "JOIN loai_phong lp ON p.ma_loai = lp.ma_loai " +
                         "GROUP BY lp.ten_loai";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String loaiPhong = rs.getString("loaiPhong");
                int soLuong = rs.getInt("soLuong");

                PhongThongKe phongThongKe = new PhongThongKe(loaiPhong, soLuong);
                result.add(phongThongKe);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng tất cả các tài nguyên
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement((PreparedStatement) stmt);
            DBConnection.closeConnection(conn);
        }

        return result;
    }

    // Thống kê tình trạng phòng
    public List<PhongThongKe> thongKeTinhTrangPhong() {
        List<PhongThongKe> result = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Lấy kết nối từ DBConnection
            conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return result;
            }

            String sql = "SELECT tinh_trang, COUNT(*) as soLuong " +
                         "FROM phong " +
                         "GROUP BY tinh_trang";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            int tongSoPhong = 0;
            List<PhongThongKe> tempList = new ArrayList<>();

            while (rs.next()) {
                String tinhTrang = rs.getString("tinh_trang");
                int soLuong = rs.getInt("soLuong");
                tongSoPhong += soLuong;

                PhongThongKe phongThongKe = new PhongThongKe();
                phongThongKe.setTinhTrang(tinhTrang);
                phongThongKe.setSoLuong(soLuong);
                tempList.add(phongThongKe);
            }

            // Tính tỷ lệ và cập nhật kết quả
            for (PhongThongKe item : tempList) {
                item.setTyLe((double) item.getSoLuong() / tongSoPhong * 100);
                result.add(item);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng tất cả các tài nguyên
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement((PreparedStatement) stmt);
            DBConnection.closeConnection(conn);
        }

        return result;
    }

    // Thống kê top 5 khách hàng
    public List<KhachHangThongKe> thongKeTop5KhachHang() {
        List<KhachHangThongKe> result = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Lấy kết nối từ DBConnection
            conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return result;
            }

            String sql = "SELECT kh.ma_khach_hang, kh.ten_khach_hang, kh.cccd, kh.so_dien_thoai, " +
                         "COUNT(pdp.ma_phieu) as soLanDatPhong, " +
                         "SUM(hd.tong_tien) as tongTien " +
                         "FROM khach_hang kh " +
                         "JOIN phieu_dat_phong pdp ON kh.ma_khach_hang = pdp.ma_khach_hang " +
                         "JOIN hoa_don hd ON pdp.ma_phieu = hd.id " +
                         "WHERE pdp.trang_thai = 'da_thanh_toan' " +
                         "GROUP BY kh.ma_khach_hang " +
                         "ORDER BY tongTien DESC " +
                         "LIMIT 5";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String maKH = rs.getString("ma_khach_hang");
                String tenKH = rs.getString("ten_khach_hang");
                String cccd = rs.getString("cccd");
                String sdt = rs.getString("so_dien_thoai");
                int soLanDat = rs.getInt("soLanDatPhong");
                double tongTien = rs.getDouble("tongTien");

                KhachHangThongKe khachHangThongKe = new KhachHangThongKe(maKH, tenKH, sdt, cccd, soLanDat, tongTien);
                result.add(khachHangThongKe);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng tất cả các tài nguyên
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement((PreparedStatement) stmt);
            DBConnection.closeConnection(conn);
        }

        return result;
    }

    // Thống kê dịch vụ
    public List<DichVuThongKe> thongKeDichVu() {
        List<DichVuThongKe> result = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Lấy kết nối từ DBConnection
            conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return result;
            }

            String sql = "SELECT dv.ten_dv, " +
                         "COUNT(ct.ma_dv) as soLuotSuDung, " +
                         "SUM(dv.gia) as doanhThu " +
                         "FROM dich_vu dv " +
                         "LEFT JOIN chi_tiet_dich_vu ct ON dv.ma_dv = ct.ma_dv " +
                         "LEFT JOIN phieu_dat_phong pdp ON ct.ma_phieu = pdp.ma_phieu " +
                         "WHERE pdp.trang_thai = 'da_thanh_toan' OR pdp.trang_thai IS NULL " +
                         "GROUP BY dv.ma_dv";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            int tongSoLuot = 0;
            List<DichVuThongKe> tempList = new ArrayList<>();

            while (rs.next()) {
                String tenDV = rs.getString("ten_dv");
                int soLuot = rs.getInt("soLuotSuDung");
                double doanhThu = rs.getDouble("doanhThu");
                tongSoLuot += soLuot;

                DichVuThongKe dichVuThongKe = new DichVuThongKe();
                dichVuThongKe.setTenDichVu(tenDV);
                dichVuThongKe.setSoLuotSuDung(soLuot);
                dichVuThongKe.setDoanhThu(doanhThu);
                tempList.add(dichVuThongKe);
            }

            // Tính tỷ lệ và cập nhật kết quả
            for (DichVuThongKe item : tempList) {
                if (tongSoLuot > 0) {
                    item.setTyLeSuDung((double) item.getSoLuotSuDung() / tongSoLuot * 100);
                } else {
                    item.setTyLeSuDung(0);
                }
                result.add(item);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng tất cả các tài nguyên
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement((PreparedStatement) stmt);
            DBConnection.closeConnection(conn);
        }

        return result;
    }

    // Thống kê hóa đơn
    public List<HoaDonThongKe> thongKeHoaDon(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        List<HoaDonThongKe> result = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Lấy kết nối từ DBConnection
            conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return result;
            }

            String sql = "SELECT hd.id as maHoaDon, " +
                    "COALESCE(kh_phieu.ten_khach_hang, COALESCE(kh_hop_dong.ten_khach_hang, '')) as tenKhachHang, " +
                    "hd.ngay_tra_phong as ngay, hd.tong_tien, hd.trang_thai, " +
                    "GROUP_CONCAT(DISTINCT p.ma_phong SEPARATOR ', ') as danhSachPhong, " +
                    "COALESCE(hd.ma_phieu, '') as maPhieu, " +
                    "COALESCE(hd.ma_hop_dong, '') as maHopDong " +
                    "FROM hoa_don hd " +
                    // Join để lấy thông tin khi có mã phiếu
                    "LEFT JOIN phieu_dat_phong pdp ON hd.ma_phieu = pdp.ma_phieu " +
                    "LEFT JOIN chitiet_phieu_dat_phong ctpdp ON pdp.ma_phieu = ctpdp.ma_phieu " +
                    "LEFT JOIN phong p ON ctpdp.ma_phong = p.ma_phong " +
                    "LEFT JOIN khach_hang kh_phieu ON pdp.ma_khach_hang = kh_phieu.ma_khach_hang " +
                    // Join để lấy thông tin khi có mã hợp đồng
                    "LEFT JOIN hop_dong hdg ON hd.ma_hop_dong = hdg.ma_hop_dong " +
                    "LEFT JOIN khach_hang kh_hop_dong ON hdg.ma_khach_hang = kh_hop_dong.ma_khach_hang " +
                    "WHERE hd.ngay_tra_phong BETWEEN ? AND ? " +
                    "GROUP BY hd.id, kh_phieu.ten_khach_hang, kh_hop_dong.ten_khach_hang, " +
                    "hd.ngay_tra_phong, hd.tong_tien, hd.trang_thai, hd.ma_phieu, hd.ma_hop_dong " +
                    "ORDER BY hd.ngay_tra_phong";

            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(ngayBatDau));
            stmt.setDate(2, java.sql.Date.valueOf(ngayKetThuc));
            rs = stmt.executeQuery();

            while (rs.next()) {
                String maHD = rs.getString("maHoaDon");
                String tenKH = rs.getString("tenKhachHang");
                LocalDate ngay = rs.getDate("ngay").toLocalDate();
                double tongTien = rs.getDouble("tong_tien");
                String trangThai = rs.getString("trang_thai");
                String danhSachPhong = rs.getString("danhSachPhong");
                String maPhieu = rs.getString("maPhieu");
                String maHopDong = rs.getString("maHopDong");

                // Sử dụng constructor mới của HoaDonThongKe
                HoaDonThongKe hoaDonThongKe = new HoaDonThongKe(maHD, tenKH, ngay, tongTien, trangThai,
                        danhSachPhong, maPhieu, maHopDong);
                result.add(hoaDonThongKe);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng tất cả các tài nguyên
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatement(stmt);
            DBConnection.closeConnection(conn);
        }

        return result;
    }

    // Phương thức này không cần thiết nữa vì mỗi phương thức đã tự đóng kết nối
    // Giữ lại để tương thích ngược với mã cũ
    public void closeConnection() {
        // Không cần làm gì vì mỗi phương thức đã tự đóng kết nối
        System.out.println("Phương thức này không cần thiết nữa vì mỗi phương thức đã tự đóng kết nối.");
    }
}