package bus;

import dao.PhieuDatPhongDAO;
import model.PhieuDatPhong;
import java.util.List;
import java.util.ArrayList;

public class PhieuDatPhongBUS {
    private PhieuDatPhongDAO phieuDAO;

    public PhieuDatPhongBUS() {
        phieuDAO = new PhieuDatPhongDAO();
    }

    public boolean themPhieuDatPhong(PhieuDatPhong pdp) {
        return phieuDAO.insert(pdp);
    }

    public List<PhieuDatPhong> getAllPhieuDatPhong() {
        return phieuDAO.getAll();
    }

    public List<PhieuDatPhong> getPhieuChuaCheckIn() {
        return phieuDAO.getPhieuChuaCheckIn();
    }

    public List<PhieuDatPhong> getPhieuDangSuDung() {
        return phieuDAO.getPhieuTheoTrangThai("dang_su_dung");
    }

    public List<PhieuDatPhong> timKiemPhieuDangSuDung(String keyword) {
        return phieuDAO.timKiemPhieuTheoTrangThai(keyword, "dang_su_dung");
    }

    public boolean capNhatTrangThai(String maPhieu, String trangThai) {
        return phieuDAO.capNhatTrangThai(maPhieu, trangThai);
    }

    public PhieuDatPhong getPhieuByMaPhieu(String maPhieu) {
        return phieuDAO.getByMaPhieu(maPhieu);
    }

    public boolean hoanThanh(String maPhieu) {
        return capNhatTrangThai(maPhieu, "hoan_thanh");
    }

    public boolean huyPhieu(String maPhieu) {
        return capNhatTrangThai(maPhieu, "da_huy");
    }

    // Thêm vào PhieuDatPhongBUS.java
    public List<PhieuDatPhong> timKiemPhieuChuaCheckIn(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return getPhieuChuaCheckIn();
        }

        // Gọi trực tiếp phương thức từ DAO
        return phieuDAO.timKiemPhieuTheoTrangThai(keyword, "dang_dat");
    }


}