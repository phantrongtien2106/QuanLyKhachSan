package model;

public class ChiTietHoaDon {
    private String maHoaDon;
    private String maItem;     // Could be room ID or service ID
    private String loaiItem;   // "phong" or "dich_vu"
    private double donGia;
    private int soLuong;       // For rooms, this could be days stayed
    private double thanhTien;
    private String tenItem;

        // Constructors, getters and setters
        public ChiTietHoaDon() {}

        public ChiTietHoaDon(String maHoaDon, String maItem, String loaiItem, double donGia, int soLuong, double thanhTien) {
            this.maHoaDon = maHoaDon;
            this.maItem = maItem;
            this.loaiItem = loaiItem;
            this.donGia = donGia;
            this.soLuong = soLuong;
            this.thanhTien = thanhTien;
        }

        public ChiTietHoaDon(String maHoaDon, String loaiItem, String maItem, String tenItem,
                             double donGia, int soLuong, double thanhTien) {
            this.maHoaDon = maHoaDon;
            this.loaiItem = loaiItem;
            this.maItem = maItem;
            // Lưu ý: tenItem có thể không được lưu vào DB, nhưng vẫn cần thiết lập
            this.tenItem = tenItem;
            this.donGia = donGia;
            this.soLuong = soLuong;
            this.thanhTien = thanhTien;
        }

        // Getters and setters
        // Getters
        public String getMaHoaDon() {
            return maHoaDon;
        }

        public String getMaItem() {
            return maItem;
        }

        public String getLoaiItem() {
            return loaiItem;
        }

        public double getDonGia() {
            return donGia;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public double getThanhTien() {
            return thanhTien;
        }

        public String getTenItem() {
            return tenItem;
        }

        // Setters
        public void setMaHoaDon(String maHoaDon) {
            this.maHoaDon = maHoaDon;
        }

        public void setMaItem(String maItem) {
            this.maItem = maItem;
        }

        public void setLoaiItem(String loaiItem) {
            this.loaiItem = loaiItem;
        }

        public void setDonGia(double donGia) {
            this.donGia = donGia;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }

        public void setThanhTien(double thanhTien) {
            this.thanhTien = thanhTien;
        }

        public String getLoai() {
            return null;
        }

        public void setTenItem(String tenItem) {
            this.tenItem = tenItem;
        }
    }