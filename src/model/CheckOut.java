package model;

public class CheckOut {
    private String maCheckOut;
    private String maDoiTuong; // có thể là ma_phieu hoặc ma_hop_dong
    private String ngayCheckOut;
    private String nhanVien;
    private String ghiChu;

    public CheckOut() {}

    public CheckOut(String maCheckOut, String maDoiTuong, String ngayCheckOut, String nhanVien, String ghiChu) {
        this.maCheckOut = maCheckOut;
        this.maDoiTuong = maDoiTuong;
        this.ngayCheckOut = ngayCheckOut;
        this.nhanVien = nhanVien;
        this.ghiChu = ghiChu;
    }

    public String getMaCheckOut() {
        return maCheckOut;
    }

    public void setMaCheckOut(String maCheckOut) {
        this.maCheckOut = maCheckOut;
    }

    public String getMaPhieu() {
        return maDoiTuong;
    }

    public void setMaPhieu(String maDoiTuong) {
        this.maDoiTuong = maDoiTuong;
    }

    public String getNgayCheckOut() {
        return ngayCheckOut;
    }

    public void setNgayCheckOut(String ngayCheckOut) {
        this.ngayCheckOut = ngayCheckOut;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    // ALIAS METHODS để khớp với tên dùng trong CheckOutForm
    public String getMaPhieuHoacHD() {
        return getMaPhieu();
    }

    public String getThoiGian() {
        return getNgayCheckOut();
    }

    public String getMaNhanVien() {
        return getNhanVien();
    }

    @Override
    public String toString() {
        return "CheckOut{" +
                "maCheckOut='" + maCheckOut + '\'' +
                ", maDoiTuong='" + maDoiTuong + '\'' +
                ", ngayCheckOut='" + ngayCheckOut + '\'' +
                ", nhanVien='" + nhanVien + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}
