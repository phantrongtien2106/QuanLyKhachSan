package System.bus;

import System.dao.ThongKeDAO;
import System.model.*;
import java.time.LocalDate;
import java.util.List;

public class ThongKeBUS {
    private ThongKeDAO thongKeDAO;
    
    public ThongKeBUS() {
        thongKeDAO = new ThongKeDAO();
    }
    
    public TongDoanhThuThongKe thongKeDoanhThu(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        return thongKeDAO.thongKeDoanhThu(ngayBatDau, ngayKetThuc);
    }
    
    public List<DoanhThuThongKe> thongKeDoanhThuTheoThang(int nam) {
        return thongKeDAO.thongKeDoanhThuTheoThang(nam);
    }
    
    public List<PhongThongKe> thongKeLoaiPhong() {
        return thongKeDAO.thongKeLoaiPhong();
    }
    
    public List<PhongThongKe> thongKeTinhTrangPhong() {
        return thongKeDAO.thongKeTinhTrangPhong();
    }
    
    public List<KhachHangThongKe> thongKeTop5KhachHang() {
        return thongKeDAO.thongKeTop5KhachHang();
    }
    
    public List<DichVuThongKe> thongKeDichVu() {
        return thongKeDAO.thongKeDichVu();
    }
    
    public List<HoaDonThongKe> thongKeHoaDon(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        return thongKeDAO.thongKeHoaDon(ngayBatDau, ngayKetThuc);
    }
    
    public void closeConnection() {
        thongKeDAO.closeConnection();
    }
} 