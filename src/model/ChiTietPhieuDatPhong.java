package model;

public class ChiTietPhieuDatPhong {
    private String maPhieu;
    private String maPhong;
    private double donGia;

    // ✅ Constructor mặc định
    public ChiTietPhieuDatPhong() {}

    // ✅ Constructor rút gọn: dùng khi chỉ cần mã (hiển thị, kiểm tra, không lưu)
    public ChiTietPhieuDatPhong(String maPhieu, String maPhong) {
        this.maPhieu = maPhieu;
        this.maPhong = maPhong;
    }

    // ✅ Constructor đầy đủ: dùng khi tạo để lưu xuống CSDL
    public ChiTietPhieuDatPhong(String maPhieu, String maPhong, double donGia) {
        this.maPhieu = maPhieu;
        this.maPhong = maPhong;
        this.donGia = donGia;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuDatPhong{" +
                "maPhieu='" + maPhieu + '\'' +
                ", maPhong='" + maPhong + '\'' +
                ", donGia=" + donGia +
                '}';
    }
}
