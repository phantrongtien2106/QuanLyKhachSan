package bus;

import dao.HopDongDAO;
import model.HopDong;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HopDongBUS {
    private HopDongDAO dao = new HopDongDAO();

    public boolean themHopDong(HopDong hd) {
        return dao.insert(hd);
    }

    public List<HopDong> getAllHopDong() {
        return dao.getAll();
    }

    public HopDong getHopDongByMa(String maHD) {
        return dao.getByMa(maHD);
    }

    public boolean capNhatThanhToan(String maHD, int daThanhToan, String ngayThanhToan) {
        return dao.capNhatThanhToan(maHD, daThanhToan, ngayThanhToan);
    }

    public boolean capNhatTrangThai(String maHD, String trangThai) {
        return dao.capNhatTrangThai(maHD, trangThai);
    }

    public boolean xoaHopDong(String maHD) {
        return dao.delete(maHD);
    }

    public boolean capNhatHopDong(HopDong hd) {
        return dao.update(hd);
    }

    public List<HopDong> getHopDongChuaCheckIn() {
        return dao.getAll().stream()
                .filter(hd -> "dang_dat".equalsIgnoreCase(hd.getTrangThai()))
                .collect(Collectors.toList());
    }

    public List<HopDong> getHopDongDangSuDung() {
        return dao.getAll().stream()
                .filter(hd -> "dang_su_dung".equalsIgnoreCase(hd.getTrangThai()))
                .collect(Collectors.toList());
    }

    public List<HopDong> getHopDongDaTra() {
        return dao.getAll().stream()
                .filter(hd -> "da_tra".equalsIgnoreCase(hd.getTrangThai()))
                .collect(Collectors.toList());
    }

    public List<HopDong> timKiemHopDong(String keyword) {
        List<HopDong> allHopDong = dao.getAll();
        List<HopDong> result = new ArrayList<>();

        keyword = keyword.toLowerCase();
        for (HopDong hd : allHopDong) {
            if (hd.getMaHopDong().toLowerCase().contains(keyword) ||
                    hd.getMaKhachHang().toLowerCase().contains(keyword)) {
                result.add(hd);
            }
        }

        return result;
    }

    public List<HopDong> timKiemHopDongDangSuDung(String keyword) {
        List<HopDong> allHopDong = getHopDongDangSuDung();
        List<HopDong> result = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            return allHopDong;
        }

        keyword = keyword.toLowerCase();
        for (HopDong hd : allHopDong) {
            if (hd.getMaHopDong().toLowerCase().contains(keyword) ||
                    hd.getMaKhachHang().toLowerCase().contains(keyword)) {
                result.add(hd);
            }
        }

        return result;
    }


}