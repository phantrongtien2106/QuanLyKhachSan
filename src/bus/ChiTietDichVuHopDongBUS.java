package bus;

import dao.ChiTietDichVuHopDongDAO;
import model.ChiTietDichVuHopDong;

import java.util.List;

public class ChiTietDichVuHopDongBUS {
    private ChiTietDichVuHopDongDAO dao = new ChiTietDichVuHopDongDAO();

    public boolean themChiTiet(ChiTietDichVuHopDong ct) {
        return dao.insert(ct);
    }

    public List<ChiTietDichVuHopDong> getDichVuTheoPhong(String maHD, String maPhong) {
        return dao.getByHopDongVaPhong(maHD, maPhong);
    }

    public boolean xoaDichVuTheoPhong(String maHD, String maPhong) {
        return dao.deleteByHopDongVaPhong(maHD, maPhong);
    }
}
