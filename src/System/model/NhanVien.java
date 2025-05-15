package System.model;

import java.sql.Date;

public class NhanVien {
    private String maNhanVien;
    private Date ngaySinh;
    private double luong;
    // Thêm tham chiếu đến TaiKhoan
    private TaiKhoan taiKhoan;

    public NhanVien(String maNhanVien, Date ngaySinh, double luong) {
        this.maNhanVien = maNhanVien;
        this.ngaySinh = ngaySinh;
        this.luong = luong;
    }

    // Constructor đầy đủ với taiKhoan
    public NhanVien(String maNhanVien, Date ngaySinh, double luong, TaiKhoan taiKhoan) {
        this.maNhanVien = maNhanVien;
        this.ngaySinh = ngaySinh;
        this.luong = luong;
        this.taiKhoan = taiKhoan;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }
}