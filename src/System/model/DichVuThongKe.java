package System.model;

public class DichVuThongKe {
    private String tenDichVu;
    private int soLuotSuDung;
    private double doanhThu;
    private double tyLeSuDung; // tỷ lệ phần trăm

    // Constructor mặc định
    public DichVuThongKe() {
    }

    // Constructor đầy đủ
    public DichVuThongKe(String tenDichVu, int soLuotSuDung, double doanhThu) {
        this.tenDichVu = tenDichVu;
        this.soLuotSuDung = soLuotSuDung;
        this.doanhThu = doanhThu;
    }

    // Getters và Setters
    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public int getSoLuotSuDung() {
        return soLuotSuDung;
    }

    public void setSoLuotSuDung(int soLuotSuDung) {
        this.soLuotSuDung = soLuotSuDung;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }

    public double getTyLeSuDung() {
        return tyLeSuDung;
    }

    public void setTyLeSuDung(double tyLeSuDung) {
        this.tyLeSuDung = tyLeSuDung;
    }

    @Override
    public String toString() {
        return "DichVuThongKe{" +
                "tenDichVu='" + tenDichVu + '\'' +
                ", soLuotSuDung=" + soLuotSuDung +
                ", doanhThu=" + doanhThu +
                ", tyLeSuDung=" + tyLeSuDung +
                '}';
    }
}
