package System.model;

import java.time.LocalDate;

public class DoanhThuThongKe {
    private String tenThongKe;
    private LocalDate ngay;
    private int thang;
    private int nam;
    private double doanhThu;
    private int soLuong;
    private String loaiPhong;

    // Constructor mặc định
    public DoanhThuThongKe() {
    }

    // Constructor cho thống kê theo ngày
    public DoanhThuThongKe(LocalDate ngay, double doanhThu, int soLuong) {
        this.ngay = ngay;
        this.doanhThu = doanhThu;
        this.soLuong = soLuong;
        this.tenThongKe = ngay.toString();
    }

    // Constructor cho thống kê theo tháng
    public DoanhThuThongKe(int thang, double doanhThu, int soLuong) {
        this.thang = thang;
        this.doanhThu = doanhThu;
        this.soLuong = soLuong;
        this.tenThongKe = "Tháng " + thang;
    }

    // Constructor cho thống kê theo loại phòng
    public DoanhThuThongKe(String loaiPhong, double doanhThu, int soLuong) {
        this.loaiPhong = loaiPhong;
        this.doanhThu = doanhThu;
        this.soLuong = soLuong;
        this.tenThongKe = loaiPhong;
    }

    // Getters và Setters
    public String getTenThongKe() {
        return tenThongKe;
    }

    public void setTenThongKe(String tenThongKe) {
        this.tenThongKe = tenThongKe;
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    @Override
    public String toString() {
        return "DoanhThuThongKe{" +
                "tenThongKe='" + tenThongKe + '\'' +
                ", ngay=" + ngay +
                ", thang=" + thang +
                ", nam=" + nam +
                ", doanhThu=" + doanhThu +
                ", soLuong=" + soLuong +
                ", loaiPhong='" + loaiPhong + '\'' +
                '}';
    }
}
