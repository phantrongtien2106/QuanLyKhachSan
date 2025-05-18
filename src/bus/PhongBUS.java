package bus;

import dao.PhongDAO;
import model.Phong;

import java.util.List;
import java.util.stream.Collectors;

public class PhongBUS {
    private PhongDAO phongDAO;

    public PhongBUS() {
        phongDAO = new PhongDAO();
    }

    // Lấy danh sách toàn bộ phòng
    public List<Phong> getAllPhong() {
        return phongDAO.getAllPhong();
    }

    // Lấy danh sách phòng trống
    public List<Phong> getPhongTrong() {
        // Thêm log để debug
        List<Phong> allPhong = phongDAO.getAllPhong();
        System.out.println("Tổng số phòng: " + allPhong.size());

        // Xử lý cẩn thận hơn với null và chuẩn hóa chuỗi
        List<Phong> phongTrong = allPhong.stream()
                .filter(p -> {
                    String tinhTrang = p.getTinhTrang();
                    if (tinhTrang != null) {
                        tinhTrang = tinhTrang.trim();
                        System.out.println("Phòng " + p.getMaPhong() + " - Tình trạng: '" + tinhTrang + "'");
                        return tinhTrang.equalsIgnoreCase("Trống");
                    }
                    return false;
                })
                .collect(Collectors.toList());

        System.out.println("Tổng số phòng trống: " + phongTrong.size());
        return phongTrong;
    }

    // Thêm phòng mới (cần mã loại)
    public boolean themPhong(Phong phong, String maLoai) {
        return phongDAO.insertPhong(phong, maLoai);
    }

    // Cập nhật phòng (cần mã loại)
    public boolean suaPhong(Phong phong, String maLoai) {
        return phongDAO.updatePhong(phong, maLoai);
    }

    // Xóa phòng theo mã
    public boolean xoaPhong(String maPhong) {
        return phongDAO.deletePhong(maPhong);
    }

    // Cập nhật tình trạng
    public boolean capNhatTinhTrang(String maPhong, String tinhTrang) {
        return phongDAO.capNhatTinhTrang(maPhong, tinhTrang);
    }

    // Cập nhật tình trạng và nguồn đặt phòng
    public boolean capNhatTinhTrangVaNguon(String maPhong, String tinhTrang, String nguonDat) {
        return phongDAO.capNhatTinhTrangVaNguon(maPhong, tinhTrang, nguonDat);
    }

    // Lấy danh sách phòng theo nguồn đặt
    public List<Phong> getPhongTheoNguon(String nguonDat) {
        return phongDAO.getAllPhong()
                .stream()
                .filter(p -> {
                    // Nếu nguồn là null thì chỉ lấy những phòng chưa có nguồn
                    if (nguonDat == null) {
                        return p.getNguonDat() == null;
                    }
                    // Nếu không thì lấy những phòng có nguồn đặt trùng khớp
                    return nguonDat.equals(p.getNguonDat());
                })
                .collect(Collectors.toList());
    }

    // Tìm phòng theo mã (tùy chọn dùng cho tra cứu nhanh)
    public Phong timPhongTheoMa(String maPhong) {
        return phongDAO.getAllPhong()
                .stream()
                .filter(p -> p.getMaPhong().equalsIgnoreCase(maPhong))
                .findFirst()
                .orElse(null);
    }

    public Phong getPhongByMa(String maPhong) {
        for (Phong p : getAllPhong()) {
            if (p.getMaPhong().equals(maPhong)) {
                return p;
            }
        }
        return null;
    }

    public double getDonGiaByMaPhong(String maPhong) {
        return phongDAO.getDonGiaByMaPhong(maPhong);
    }
}