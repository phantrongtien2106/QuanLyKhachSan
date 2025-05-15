package bus;

import dao.ChiTietPhieuDatPhongDAO;
import model.ChiTietPhieuDatPhong;
import java.util.List;

public class ChiTietPhieuDatPhongBUS {
    private ChiTietPhieuDatPhongDAO ctDAO;

    public ChiTietPhieuDatPhongBUS() {
        ctDAO = new ChiTietPhieuDatPhongDAO();
    }

    // Lấy danh sách chi tiết phiếu đặt phòng theo mã phiếu
    public List<ChiTietPhieuDatPhong> getChiTietByMaPhieu(String maPhieu) {
        return ctDAO.getByMaPhieu(maPhieu);
    }

    // Lấy danh sách mã phòng theo mã phiếu
    public List<String> getPhongByMaPhieu(String maPhieu) {
        return ctDAO.getPhongByMaPhieu(maPhieu);
    }

    // Thêm chi tiết phiếu đặt phòng
    public boolean themChiTiet(ChiTietPhieuDatPhong ct) {
        return ctDAO.insert(ct);
    }

    // Xóa chi tiết phiếu đặt phòng theo mã phiếu
    public boolean xoaChiTietTheoMaPhieu(String maPhieu) {
        return ctDAO.deleteByMaPhieu(maPhieu);
    }
}