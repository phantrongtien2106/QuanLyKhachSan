package model;

public class PhieuDatPhong {
    private String maPhieu;
    private String maKhachHang;
    private String ngayNhan;
    private String ngayTra;
    private String ghiChu;
    private String trangThai;
    private String ngayDat;

    public PhieuDatPhong() {}

    public PhieuDatPhong(String maPhieu, String maKhachHang, String ngayNhan, String ngayTra, String ghiChu, String trangThai, String ngayDat) {
        this.maPhieu = maPhieu;
        this.maKhachHang = maKhachHang;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
        this.ngayDat = ngayDat;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(String ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    @Override
    public String toString() {
        return "PhieuDatPhong{" +
                "maPhieu='" + maPhieu + '\'' +
                ", maKhachHang='" + maKhachHang + '\'' +
                ", ngayNhan='" + ngayNhan + '\'' +
                ", ngayTra='" + ngayTra + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", ngayDat='" + ngayDat + '\'' +
                '}';
    }
}
