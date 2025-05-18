package model;

public class ChiTietDichVuHopDong {
    private String maHopDong;
    private String maPhong;
    private String maDv;

    public ChiTietDichVuHopDong() {}

    public ChiTietDichVuHopDong(String maHopDong, String maPhong, String maDv) {
        this.maHopDong = maHopDong;
        this.maPhong = maPhong;
        this.maDv = maDv;
    }

    public String getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaDv() {
        return maDv;
    }

    public void setMaDv(String maDv) {
        this.maDv = maDv;
    }

    @Override
    public String toString() {
        return "ChiTietDichVuHopDong{" +
                "maHopDong='" + maHopDong + '\'' +
                ", maPhong='" + maPhong + '\'' +
                ", maDv='" + maDv + '\'' +
                '}';
    }
}
