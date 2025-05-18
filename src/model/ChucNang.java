package model;

public class ChucNang {
    private String maChucNang;
    private String tenChucNang;
    public ChucNang(String ma, String ten) {
        this.maChucNang = ma;
        this.tenChucNang = ten;
    }
    public String getMaChucNang() { return maChucNang; }
    public String getTenChucNang() { return tenChucNang; }
    @Override public String toString() { return maChucNang + " - " + tenChucNang; }
}