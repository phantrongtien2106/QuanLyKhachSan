package bus;

import dao.HopDongDAO;
import model.HopDong;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public boolean capNhatThanhToan(String maHD, int soTien, String ngayThanhToan, int trangThaiThanhToan) {
    return dao.capNhatThanhToan(maHD, soTien, ngayThanhToan, trangThaiThanhToan);
    }

    public boolean capNhatThanhToanDayDu(String maHD, int soTien, String ngayThanhToan, int trangThaiThanhToan) {
    return dao.capNhatThanhToanDayDu(maHD, soTien, ngayThanhToan, trangThaiThanhToan);
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

    // Thêm vào HopDongBUS.java
    public boolean chuyenTrangThai(String maHopDong, String trangThaiMoi) {
        HopDong hopDong = dao.getByMa(maHopDong);
        if (hopDong == null) {
            return false;
        }

        String trangThaiHienTai = hopDong.getTrangThai();

        // Kiểm tra tính hợp lệ của quá trình chuyển trạng thái
        if (!kiemTraChuyenTrangThaiHopLe(trangThaiHienTai, trangThaiMoi)) {
            return false;
        }

        // Nếu chuyển sang trạng thái "dang_su_dung", cần cập nhật trạng thái phòng
        if (trangThaiMoi.equals("dang_su_dung")) {
            // Cập nhật trạng thái phòng trong hợp đồng
            PhongBUS phongBUS = new PhongBUS();
            ChiTietHopDongBUS cthdBUS = new ChiTietHopDongBUS();
            List<String> dsMaPhong = cthdBUS.getPhongByMaHopDong(maHopDong);

            for (String maPhong : dsMaPhong) {
                phongBUS.capNhatTinhTrangVaNguon(maPhong, "Đang sử dụng", "hop_dong");
            }
        }

        return dao.capNhatTrangThai(maHopDong, trangThaiMoi);
    }

    private boolean kiemTraChuyenTrangThaiHopLe(String trangThaiHienTai, String trangThaiMoi) {
        // Kiểm tra các luồng chuyển đổi hợp lệ
        switch (trangThaiHienTai) {
            case "dang_dat":
                // Từ "đang đặt" có thể chuyển sang "đang sử dụng" (check-in) hoặc "da_huy" (hủy)
                return trangThaiMoi.equals("dang_su_dung") || trangThaiMoi.equals("da_huy");
            case "dang_su_dung":
                // Từ "đang sử dụng" chỉ có thể chuyển sang "da_tra" (check-out)
                return trangThaiMoi.equals("da_tra");
            case "da_tra":
            case "da_huy":
                // Các trạng thái kết thúc không thể chuyển tiếp
                return false;
            default:
                return false;
        }
    }

    // Thêm vào HopDongBUS.java
    public boolean themHopDongVaChiTiet(HopDong hd, List<String> dsPhong, Map<String, List<String>> dichVuTheoPhong) {
        return dao.themHopDongVaChiTiet(hd, dsPhong, dichVuTheoPhong);
    }

}