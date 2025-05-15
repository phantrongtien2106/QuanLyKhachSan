package model;

public class Phong {
    private String maPhong;
    private String tenLoai; // THAY vì maLoai
    private String tinhTrang;
    private double gia;
    private String nguonDat;

    public Phong() {}

    public Phong(String maPhong, String tenLoai, String tinhTrang, double gia) {
        this.maPhong = maPhong;
        this.tenLoai = tenLoai;
        this.tinhTrang = tinhTrang;
        this.gia = gia;
    }


    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    // Thêm getter và setter
    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    // And add getter/setter
    public String getNguonDat() {
        return nguonDat;
    }

    public void setNguonDat(String nguonDat) {
        this.nguonDat = nguonDat;
    }

    @Override
    public String toString() {
        return maPhong + " (" + tenLoai + ")";
    }
}
