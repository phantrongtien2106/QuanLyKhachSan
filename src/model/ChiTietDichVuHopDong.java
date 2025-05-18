package model;

    public class ChiTietDichVuHopDong {
        private String maHopDong;
        private String maPhong;
        private String maDv; // Đổi tên trường cho nhất quán với DichVu

        // Constructor không tham số
        public ChiTietDichVuHopDong() {
        }

        // Constructor đầy đủ
        public ChiTietDichVuHopDong(String maHopDong, String maPhong, String maDv) {
            this.maHopDong = maHopDong;
            this.maPhong = maPhong;
            this.maDv = maDv;
        }

        // Getters và setters
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
    }