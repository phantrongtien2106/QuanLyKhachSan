package model;

public class PhongThongKe {
    private String loaiPhong;
    private String tinhTrang;
    private int soLuong;
    private double tyLe; // tỷ lệ phần trăm

    // Constructor mặc định
    public PhongThongKe() {
    }

    // Constructor đầy đủ cho thống kê theo loại phòng
    public PhongThongKe(String loaiPhong, int soLuong) {
        this.loaiPhong = loaiPhong;
        this.soLuong = soLuong;
    }

    // Constructor đầy đủ cho thống kê theo tình trạng
    public PhongThongKe(String tinhTrang, int soLuong, double tyLe) {
        this.tinhTrang = tinhTrang;
        this.soLuong = soLuong;
        this.tyLe = tyLe;
    }

    // Getters và Setters
    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getTyLe() {
        return tyLe;
    }

    public void setTyLe(double tyLe) {
        this.tyLe = tyLe;
    }

    @Override
    public String toString() {
        return "PhongThongKe{" +
                "loaiPhong='" + loaiPhong + '\'' +
                ", tinhTrang='" + tinhTrang + '\'' +
                ", soLuong=" + soLuong +
                ", tyLe=" + tyLe +
                '}';
    }
}
