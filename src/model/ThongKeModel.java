package model;

import java.time.LocalDate;

public class ThongKeModel {
    private String maThongKe;
    private String tenThongKe;
    private int soLuong;
    private double doanhThu;
    private double tyLe;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    
    // Constructor mặc định
    public ThongKeModel() {
    }
    
    // Constructor đầy đủ
    public ThongKeModel(String maThongKe, String tenThongKe, int soLuong, double doanhThu, double tyLe) {
        this.maThongKe = maThongKe;
        this.tenThongKe = tenThongKe;
        this.soLuong = soLuong;
        this.doanhThu = doanhThu;
        this.tyLe = tyLe;
    }
    
    // Constructor với ngày
    public ThongKeModel(String maThongKe, String tenThongKe, int soLuong, double doanhThu, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.maThongKe = maThongKe;
        this.tenThongKe = tenThongKe;
        this.soLuong = soLuong;
        this.doanhThu = doanhThu;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }
    
    // Getters và Setters
    public String getMaThongKe() {
        return maThongKe;
    }
    
    public void setMaThongKe(String maThongKe) {
        this.maThongKe = maThongKe;
    }
    
    public String getTenThongKe() {
        return tenThongKe;
    }
    
    public void setTenThongKe(String tenThongKe) {
        this.tenThongKe = tenThongKe;
    }
    
    public int getSoLuong() {
        return soLuong;
    }
    
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    public double getDoanhThu() {
        return doanhThu;
    }
    
    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }
    
    public double getTyLe() {
        return tyLe;
    }
    
    public void setTyLe(double tyLe) {
        this.tyLe = tyLe;
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
    
    @Override
    public String toString() {
        return "ThongKeModel{" +
                "maThongKe='" + maThongKe + '\'' +
                ", tenThongKe='" + tenThongKe + '\'' +
                ", soLuong=" + soLuong +
                ", doanhThu=" + doanhThu +
                ", tyLe=" + tyLe +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                '}';
    }
}
