// System/model/TongDoanhThuThongKe.java
package System.model;

import java.time.LocalDate;

public class TongDoanhThuThongKe {
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private int soLuongHoaDon;
    private double doanhThuHoaDon;
    private int soLuongHopDong;
    private double doanhThuHopDong;
    private int soLuongDichVu;
    private double doanhThuDichVu;
    private double doanhThuPhong;

    public TongDoanhThuThongKe() {
        this.soLuongHoaDon = 0;
        this.doanhThuHoaDon = 0;
        this.soLuongHopDong = 0;
        this.doanhThuHopDong = 0;
        this.soLuongDichVu = 0;
        this.doanhThuDichVu = 0;
        this.doanhThuPhong = 0;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getSoLuongHoaDon() {
        return soLuongHoaDon;
    }

    public void setSoLuongHoaDon(int soLuongHoaDon) {
        this.soLuongHoaDon = soLuongHoaDon;
    }

    public double getDoanhThuHoaDon() {
        return doanhThuHoaDon;
    }

    public void setDoanhThuHoaDon(double doanhThuHoaDon) {
        this.doanhThuHoaDon = doanhThuHoaDon;
    }

    public int getSoLuongHopDong() {
        return soLuongHopDong;
    }

    public void setSoLuongHopDong(int soLuongHopDong) {
        this.soLuongHopDong = soLuongHopDong;
    }

    public double getDoanhThuHopDong() {
        return doanhThuHopDong;
    }

    public void setDoanhThuHopDong(double doanhThuHopDong) {
        this.doanhThuHopDong = doanhThuHopDong;
    }

    public int getSoLuongDichVu() {
        return soLuongDichVu;
    }

    public void setSoLuongDichVu(int soLuongDichVu) {
        this.soLuongDichVu = soLuongDichVu;
    }

    public double getDoanhThuDichVu() {
        return doanhThuDichVu;
    }

    public void setDoanhThuDichVu(double doanhThuDichVu) {
        this.doanhThuDichVu = doanhThuDichVu;
    }

    public double getDoanhThuPhong() {
        return doanhThuPhong;
    }

    public void setDoanhThuPhong(double doanhThuPhong) {
        this.doanhThuPhong = doanhThuPhong;
    }

    public double getTongDoanhThu() {
        return doanhThuHoaDon + doanhThuHopDong + doanhThuDichVu + doanhThuPhong;
    }

    @Override
    public String toString() {
        return "TongDoanhThuThongKe{" +
                "ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", doanhThuHoaDon=" + doanhThuHoaDon +
                ", doanhThuHopDong=" + doanhThuHopDong +
                ", tongDoanhThu=" + getTongDoanhThu() +
                ", soLuongHoaDon=" + soLuongHoaDon +
                ", soLuongHopDong=" + soLuongHopDong +
                ", soLuongDichVu=" + soLuongDichVu +
                ", doanhThuDichVu=" + doanhThuDichVu +
                ", doanhThuPhong=" + doanhThuPhong +
                '}';
    }
}
