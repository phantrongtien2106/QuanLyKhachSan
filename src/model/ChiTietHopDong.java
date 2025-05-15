package model;

public class ChiTietHopDong {
    private String maHopDong;
    private String maPhong;
    private String ghiChu; // thêm ghi chú
    private double donGia; // thêm đơn giá nếu cần

    public ChiTietHopDong() {}

    // Constructor ghi chú
    public ChiTietHopDong(String maHopDong, String maPhong, String ghiChu) {
        this.maHopDong = maHopDong;
        this.maPhong = maPhong;
        this.ghiChu = ghiChu;
    }

    public ChiTietHopDong(String maHopDong, String maPhong) {
        this.maHopDong = maHopDong;
        this.maPhong = maPhong;
    }

    // Constructor đơn giá
    public ChiTietHopDong(String maHopDong, String maPhong, double donGia) {
        this.maHopDong = maHopDong;
        this.maPhong = maPhong;
        this.donGia = donGia;
    }

    public String getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    @Override
    public String toString() {
        return "ChiTietHopDong{" +
                "maHopDong='" + maHopDong + '\'' +
                ", maPhong='" + maPhong + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                ", donGia=" + donGia +
                '}';
    }
}
