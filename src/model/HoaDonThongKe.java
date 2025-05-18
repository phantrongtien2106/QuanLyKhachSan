package model;

import java.time.LocalDate;

public class HoaDonThongKe {
    private String maHoaDon;
    private String tenKhachHang;
    private LocalDate ngay;
    private double tongTien;
    private String trangThai;
    private String danhSachPhong;
    private String maPhieu;      // Thêm trường mới
    private String maHopDong;    // Thêm trường mới

    // Constructor mặc định
    public HoaDonThongKe() {
    }

    // Constructor không có các trường mới (giữ lại để tương thích)
    public HoaDonThongKe(String maHoaDon, String tenKhachHang, LocalDate ngay, double tongTien, String trangThai) {
        this.maHoaDon = maHoaDon;
        this.tenKhachHang = tenKhachHang;
        this.ngay = ngay;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    // Constructor có danhSachPhong
    public HoaDonThongKe(String maHoaDon, String tenKhachHang, LocalDate ngay, double tongTien, String trangThai, String danhSachPhong) {
        this.maHoaDon = maHoaDon;
        this.tenKhachHang = tenKhachHang;
        this.ngay = ngay;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.danhSachPhong = danhSachPhong;
    }

    // Constructor đầy đủ với tất cả các trường mới
    public HoaDonThongKe(String maHoaDon, String tenKhachHang, LocalDate ngay, double tongTien, String trangThai,
                         String danhSachPhong, String maPhieu, String maHopDong) {
        this.maHoaDon = maHoaDon;
        this.tenKhachHang = tenKhachHang;
        this.ngay = ngay;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.danhSachPhong = danhSachPhong;
        this.maPhieu = maPhieu;
        this.maHopDong = maHopDong;
    }

    // Getters và Setters đã có
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDanhSachPhong() {
        return danhSachPhong;
    }

    public void setDanhSachPhong(String danhSachPhong) {
        this.danhSachPhong = danhSachPhong;
    }

    // Getter và Setter cho các trường mới
    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    @Override
    public String toString() {
        return "HoaDonThongKe{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", ngay=" + ngay +
                ", tongTien=" + tongTien +
                ", trangThai='" + trangThai + '\'' +
                ", danhSachPhong='" + danhSachPhong + '\'' +
                ", maPhieu='" + maPhieu + '\'' +
                ", maHopDong='" + maHopDong + '\'' +
                '}';
    }
}