package bus;

import dao.ChiTietHoaDonDAO;
import model.ChiTietHoaDon;
import java.util.List;
import dao.ChiTietHopDongDAO;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonBUS {
    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    public ChiTietHoaDonBUS() {
        chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    }

    public boolean themChiTietHoaDon(ChiTietHoaDon cthd) {
        return chiTietHoaDonDAO.insert(cthd);
    }

    public List<ChiTietHoaDon> getChiTietByMaHoaDon(String maHoaDon) {
        return chiTietHoaDonDAO.getByMaHoaDon(maHoaDon);
    }

    public List<String> getPhongByMaHopDong(String maHopDong) {
        // Sử dụng ChiTietHopDongDAO vì thông tin này đến từ bảng chi_tiet_hop_dong
        ChiTietHopDongDAO chiTietHopDongDAO = new ChiTietHopDongDAO();
        return chiTietHopDongDAO.getPhongByMaHopDong(maHopDong);
    }

    // Other business methods
}