package bus;

import dao.ChiTietHoaDonDAO;
import model.ChiTietHoaDon;
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

    // Other business methods
}