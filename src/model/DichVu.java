package model;

public class DichVu {
    private String maDv;
    private String tenDv;
    private double gia;

    public DichVu() {}

    public DichVu(String maDv, String tenDv, double gia) {
        this.maDv = maDv;
        this.tenDv = tenDv;
        this.gia = gia;
    }

    public String getMaDv() {
        return maDv;
    }

    public void setMaDv(String maDv) {
        this.maDv = maDv;
    }

    public String getTenDv() {
        return tenDv;
    }

    public void setTenDv(String tenDv) {
        this.tenDv = tenDv;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return maDv + " - " + tenDv;
    }
}
