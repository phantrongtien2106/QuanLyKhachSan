package bus;

import dao.ChiTietDichVuHopDongDAO;
import model.ChiTietDichVuHopDong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChiTietDichVuHopDongBUS {
    private ChiTietDichVuHopDongDAO dao;

    public ChiTietDichVuHopDongBUS() {
        dao = new ChiTietDichVuHopDongDAO();
    }
    public boolean themChiTiet(ChiTietDichVuHopDong ct) {
        return dao.insert(ct);
    }
    public boolean themNhieuChiTiet(List<ChiTietDichVuHopDong> danhSach) {
        return dao.insertBatch(danhSach);
    }
    public List<ChiTietDichVuHopDong> getDichVuByMaHopDong(String maHopDong) {
        return dao.getByMaHopDong(maHopDong);
    }
    public List<ChiTietDichVuHopDong> getDichVuTheoPhong(String maHopDong, String maPhong) {
        return dao.getByHopDongVaPhong(maHopDong, maPhong);
    }
    public boolean xoaDichVuTheoPhong(String maHopDong, String maPhong) {
        return dao.deleteByHopDongVaPhong(maHopDong, maPhong);
    }
    public boolean xoaDichVu(String maHopDong, String maPhong, String maDv) {
        return dao.delete(maHopDong, maPhong, maDv);
    }
    public boolean kiemTraTonTaiDichVu(String maHopDong, String maPhong, String maDv) {
        return dao.checkExists(maHopDong, maPhong, maDv);
    }
    public boolean themDichVuChoNhieuPhong(String maHopDong, Map<String, List<String>> dichVuTheoPhong) {
        List<ChiTietDichVuHopDong> danhSachChiTiet = new ArrayList<>();

        for (String maPhong : dichVuTheoPhong.keySet()) {
            List<String> dsDichVu = dichVuTheoPhong.get(maPhong);

            for (String maDv : dsDichVu) {
                danhSachChiTiet.add(new ChiTietDichVuHopDong(maHopDong, maPhong, maDv));
            }
        }

        return dao.insertBatch(danhSachChiTiet);
    }
    public Map<String, List<String>> getDichVuTheoPhongMap(String maHopDong) {
        List<ChiTietDichVuHopDong> dsDichVu = dao.getByMaHopDong(maHopDong);
        Map<String, List<String>> result = new HashMap<>();

        for (ChiTietDichVuHopDong ctdv : dsDichVu) {
            String maPhong = ctdv.getMaPhong();
            if (!result.containsKey(maPhong)) {
                result.put(maPhong, new ArrayList<>());
            }
            result.get(maPhong).add(ctdv.getMaDv());
        }

        return result;
    }
}