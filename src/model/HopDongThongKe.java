package model;

import java.time.LocalDate;

public class HopDongThongKe {
    private String maHopDong;
    private String tenKhachHang;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private int tongNgayThue;
    private double tongTien;
    private String trangThai;

    // Constructor mặc định
    public HopDongThongKe() {
    }

    // Constructor đầy đủ
    public HopDongThongKe(String maHopDong, String tenKhachHang, LocalDate ngayBatDau, 
                          LocalDate ngayKetThuc, int tongNgayThue, double tongTien, 
                          String trangThai) {
        this.maHopDong = maHopDong;
        this.tenKhachHang = tenKhachHang;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.tongNgayThue = tongNgayThue;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    // Getters và Setters
    public String getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getTongNgayThue() {
        return tongNgayThue;
    }

    public void setTongNgayThue(int tongNgayThue) {
        this.tongNgayThue = tongNgayThue;
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

    @Override
    public String toString() {
        return "HopDongThongKe{" +
                "maHopDong='" + maHopDong + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", tongNgayThue=" + tongNgayThue +
                ", tongTien=" + tongTien +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
