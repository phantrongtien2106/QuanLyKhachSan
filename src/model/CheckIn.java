package model;

public class CheckIn {
    private String maCheckIn;
    private String maPhieu;
    private String ngayCheckIn;
    private String nhanVien;
    private String ghiChu;

    public CheckIn() {}

    public CheckIn(String maCheckIn, String maPhieu, String ngayCheckIn, String nhanVien, String ghiChu) {
        this.maCheckIn = maCheckIn;
        this.maPhieu = maPhieu;
        this.ngayCheckIn = ngayCheckIn;
        this.nhanVien = nhanVien;
        this.ghiChu = ghiChu;
    }

    public String getMaCheckIn() {
        return maCheckIn;
    }

    public void setMaCheckIn(String maCheckIn) {
        this.maCheckIn = maCheckIn;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getNgayCheckIn() {
        return ngayCheckIn;
    }

    public void setNgayCheckIn(String ngayCheckIn) {
        this.ngayCheckIn = ngayCheckIn;
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

    @Override
    public String toString() {
        return "CheckIn{" +
                "maCheckIn='" + maCheckIn + '\'' +
                ", maPhieu='" + maPhieu + '\'' +
                ", ngayCheckIn='" + ngayCheckIn + '\'' +
                ", nhanVien='" + nhanVien + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}
