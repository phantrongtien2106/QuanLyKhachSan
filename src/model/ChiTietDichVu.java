package model;

    public class ChiTietDichVu {
        private String maPhieu;
        private String maHopDong;  // Add this field
        private String maDv;
        private int soLuong;

        public ChiTietDichVu() {
        }

        // Constructor for reservation services
        public ChiTietDichVu(String maPhieu, String maDv, int soLuong) {
            this.maPhieu = maPhieu;
            this.maDv = maDv;
            this.soLuong = soLuong;
        }

        // Constructor for contract services
        public ChiTietDichVu(String maHopDong, String maDv, int soLuong, boolean isHopDong) {
            if (isHopDong) {
                this.maHopDong = maHopDong;
            } else {
                this.maPhieu = maHopDong;
            }
            this.maDv = maDv;
            this.soLuong = soLuong;
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

        public String getMaDv() {
            return maDv;
        }

        public void setMaDv(String maDv) {
            this.maDv = maDv;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }
    }