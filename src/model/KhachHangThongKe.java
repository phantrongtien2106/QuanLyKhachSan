package model;

public class KhachHangThongKe {
    private String maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String cccd;
    private int soLanDatPhong;
    private double tongTien;
    private String loaiKhach; // mới/thường xuyên
    private int soLuong; // số lượng khách hàng theo loại
    private double tyLe; // tỷ lệ phần trăm

    // Constructor mặc định
    public KhachHangThongKe() {
    }

    // Constructor đầy đủ
    public KhachHangThongKe(String maKhachHang, String tenKhachHang, String soDienThoai, String cccd, int soLanDatPhong, double tongTien) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.cccd = cccd;
        this.soLanDatPhong = soLanDatPhong;
        this.tongTien = tongTien;
    }

    // Getters và Setters
    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public int getSoLanDatPhong() {
        return soLanDatPhong;
    }

    public void setSoLanDatPhong(int soLanDatPhong) {
        this.soLanDatPhong = soLanDatPhong;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getLoaiKhach() {
        return loaiKhach;
    }

    public void setLoaiKhach(String loaiKhach) {
        this.loaiKhach = loaiKhach;
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
        return "KhachHangThongKe{" +
                "maKhachHang='" + maKhachHang + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", cccd='" + cccd + '\'' +
                ", soLanDatPhong=" + soLanDatPhong +
                ", tongTien=" + tongTien +
                '}';
    }
}
