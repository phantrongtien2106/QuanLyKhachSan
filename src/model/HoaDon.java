package model;

public class HoaDon {
    private String id;                    // mã hóa đơn
    private String ngayNhanPhong;
    private String ngayTraPhong;
    private String ngayThanhToan;
    private double tongTien;
    private String phuongThucThanhToan;
    private String trangThai;
    private String maPhieu;              // có thể null nếu là hợp đồng
    private String maHopDong;
    private String ghiChu;
    private int soNgayThucTe;// có thể null nếu là phiếu
    private String maNhanVien;

    public HoaDon() {}

    public HoaDon(String id, String ngayNhanPhong, String ngayTraPhong, String ngayThanhToan,
                  double tongTien, String phuongThucThanhToan, String trangThai,
                  String maPhieu, String maHopDong) {
        this.id = id;
        this.ngayNhanPhong = ngayNhanPhong;
        this.ngayTraPhong = ngayTraPhong;
        this.ngayThanhToan = ngayThanhToan;
        this.tongTien = tongTien;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.trangThai = trangThai;
        this.maPhieu = maPhieu;
        this.maHopDong = maHopDong;
    }


    public HoaDon(String id, String maPhieu, String maHopDong, String ngayThanhToan,
                  double tongTien, String trangThai, String maNhanVien) {
        this.id = id;
        this.maPhieu = maPhieu;
        this.maHopDong = maHopDong;
        this.ngayThanhToan = ngayThanhToan;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.maNhanVien = maNhanVien;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNgayNhanPhong() {
        return ngayNhanPhong;
    }

    public void setNgayNhanPhong(String ngayNhanPhong) {
        this.ngayNhanPhong = ngayNhanPhong;
    }

    public String getNgayTraPhong() {
        return ngayTraPhong;
    }

    public void setNgayTraPhong(String ngayTraPhong) {
        this.ngayTraPhong = ngayTraPhong;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    public String getMaKhachHang() {
        return maPhieu != null ? maPhieu : maHopDong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getSoNgayThucTe() {
        return soNgayThucTe;
    }

    public void setSoNgayThucTe(int soNgayThucTe) {
        this.soNgayThucTe = soNgayThucTe;
    }

    public String getMaPhieuHoacHopDong() {
        return getMaKhachHang();
    }

    public String getNgayLapHoaDon() {
        return ngayThanhToan;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "id='" + id + '\'' +
                ", ngayNhanPhong='" + ngayNhanPhong + '\'' +
                ", ngayTraPhong='" + ngayTraPhong + '\'' +
                ", ngayThanhToan='" + ngayThanhToan + '\'' +
                ", tongTien=" + tongTien +
                ", phuongThucThanhToan='" + phuongThucThanhToan + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", maPhieu='" + maPhieu + '\'' +
                ", maHopDong='" + maHopDong + '\'' +
                '}';
    }
}