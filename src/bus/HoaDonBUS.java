package bus;

import dao.HoaDonDAO;
import model.HoaDon;
import model.ChiTietDichVu;  // Thêm import này
import model.DichVu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class HoaDonBUS {
    private HoaDonDAO dao = new HoaDonDAO();

    public boolean themHoaDon(HoaDon hoaDon) {
        return dao.insert(hoaDon);
    }

    public List<HoaDon> getAllHoaDon() {
        return dao.getAll();
    }

    public HoaDon getHoaDonById(String id) {
        return dao.getById(id);
    }

    public HoaDon getHoaDonByMaPhieu(String maPhieu) {
        return dao.getByMaPhieu(maPhieu);
    }

    public HoaDon getHoaDonByMaHopDong(String maHopDong) {
        return dao.getByMaHopDong(maHopDong);
    }

    public boolean capNhatHoaDon(HoaDon hoaDon) {
        return dao.update(hoaDon);
    }

    public boolean capNhatTrangThai(String id, String trangThai) {
        return dao.updateTrangThai(id, trangThai);
    }

    public boolean capNhatThanhToan(String id, String ngayThanhToan, String phuongThucThanhToan) {
        return dao.updateThanhToan(id, ngayThanhToan, phuongThucThanhToan);
    }

    public boolean xoaHoaDon(String id) {
        return dao.delete(id);
    }

    public List<HoaDon> getHoaDonTheoTrangThai(String trangThai) {
        return dao.getByTrangThai(trangThai);
    }

    public List<HoaDon> getHoaDonTheoKhoangThoiGian(String ngayBatDau, String ngayKetThuc) {
        return dao.getByDateRange(ngayBatDau, ngayKetThuc);
    }



    public List<HoaDon> timKiemHoaDon(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllHoaDon();
        }

        String search = keyword.toLowerCase().trim();
        return dao.getAll().stream()
                .filter(hd ->
                        (hd.getId() != null && hd.getId().toLowerCase().contains(search)) ||
                                (hd.getMaPhieu() != null && hd.getMaPhieu().toLowerCase().contains(search)) ||
                                (hd.getMaHopDong() != null && hd.getMaHopDong().toLowerCase().contains(search)) ||
                                (hd.getPhuongThucThanhToan() != null && hd.getPhuongThucThanhToan().toLowerCase().contains(search)) ||
                                (hd.getTrangThai() != null && hd.getTrangThai().toLowerCase().contains(search))
                )
                .collect(Collectors.toList());
    }

    public List<HoaDon> getHoaDonDaThanhToan() {
        return getHoaDonTheoTrangThai("da_thanh_toan");
    }

    public List<HoaDon> getHoaDonChuaThanhToan() {
        return getHoaDonTheoTrangThai("chua_thanh_toan");
    }

    public double tinhTongDoanhThu(String ngayBatDau, String ngayKetThuc) {
        return getHoaDonTheoKhoangThoiGian(ngayBatDau, ngayKetThuc).stream()
                .filter(hd -> "da_thanh_toan".equals(hd.getTrangThai()))
                .mapToDouble(HoaDon::getTongTien)
                .sum();
    }

    public String taoMaHoaDon() {
        return "HD" + System.currentTimeMillis() % 1000000;
    }

    public String getNgayHienTai() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
