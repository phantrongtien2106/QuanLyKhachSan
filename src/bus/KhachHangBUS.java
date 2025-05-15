package bus;

import dao.KhachHangDAO;
import model.KhachHang;

import java.util.List;

public class KhachHangBUS {
    private KhachHangDAO dao = new KhachHangDAO();

    // Lấy toàn bộ danh sách khách hàng từ DAO
    public List<KhachHang> getAllKhachHang() {
        return dao.getAllKhachHang();
    }

    // Tìm khách hàng theo mã (KHxxx), đọc thẳng từ DAO để luôn có dữ liệu mới
    public KhachHang getKhachHangByMa(String maKH) {
        for (KhachHang kh : dao.getAllKhachHang()) {
            if (kh.getMaKhachHang().equalsIgnoreCase(maKH)) {
                return kh;
            }
        }
        return null;
    }

    public boolean themKhachHang(KhachHang kh) {
        return dao.insertFullKhachHang(kh, "123456"); // mặc định mật khẩu "123456"
    }

    public boolean suaKhachHang(KhachHang kh) {
        return dao.updateKhachHang(kh);
    }

    public boolean xoaKhachHang(String maKH) {
        return dao.deleteKhachHang(maKH);
    }

    public boolean isMaKhachHangTonTai(String maKH) {
        return getKhachHangByMa(maKH) != null;
    }


    // Sinh mã KH tự động: KH001, KH002,...
    public String getNextMaKhachHang() {
        List<KhachHang> ds = dao.getAllKhachHang();
        int max = 0;
        for (KhachHang kh : ds) {
            try {
                int so = Integer.parseInt(kh.getMaKhachHang().substring(2));
                if (so > max) max = so;
            } catch (Exception ignored) {}
        }
        return "KH" + String.format("%03d", max + 1);
    }
}
