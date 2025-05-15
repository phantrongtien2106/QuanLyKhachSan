package model;

    public class HopDong {
        private String maHopDong;
        private String maKhachHang;
        private int soLuongPhongMuonThue;
        private String lichDatPhong;
        private String ngayBatDau;
        private String ngayKetThuc;
        private int tongNgayThue;
        private int daThanhToan;
        private String ngayThanhToan;
        private String ghiChu;
        private int datCoc;
        private int tongTien;
        private String phuongThucThanhToan;
        private String trangThai;

        // Constructors

        // Full constructor
        public HopDong(String maHopDong, String maKhachHang, int soLuongPhongMuonThue,
                       String lichDatPhong, String ngayBatDau, String ngayKetThuc,
                       int tongNgayThue, int daThanhToan, String ngayThanhToan, String ghiChu,
                       int datCoc, int tongTien, String phuongThucThanhToan, String trangThai) {
            this.maHopDong = maHopDong;
            this.maKhachHang = maKhachHang;
            this.soLuongPhongMuonThue = soLuongPhongMuonThue;
            this.lichDatPhong = lichDatPhong;
            this.ngayBatDau = ngayBatDau;
            this.ngayKetThuc = ngayKetThuc;
            this.tongNgayThue = tongNgayThue;
            this.daThanhToan = daThanhToan;
            this.ngayThanhToan = ngayThanhToan;
            this.ghiChu = ghiChu;
            this.datCoc = datCoc;
            this.tongTien = tongTien;
            this.phuongThucThanhToan = phuongThucThanhToan;
            this.trangThai = trangThai;
        }

        // Constructor without ngayThanhToan
        public HopDong(String maHopDong, String maKhachHang, int soLuongPhongMuonThue,
                       String lichDatPhong, String ngayBatDau, String ngayKetThuc,
                       int tongNgayThue, int daThanhToan, String ghiChu,
                       int datCoc, int tongTien, String phuongThucThanhToan, String trangThai) {
            this(maHopDong, maKhachHang, soLuongPhongMuonThue, lichDatPhong, ngayBatDau, ngayKetThuc,
                    tongNgayThue, daThanhToan, null, ghiChu, datCoc, tongTien, phuongThucThanhToan, trangThai);
        }

        // Simplified constructor for form usage
        public HopDong(String maHopDong, String maKhachHang, String ngayBatDau, String ngayKetThuc,
                      int tongTien, int datCoc, int daThanhToan, String trangThai, String ghiChu) {
            this.maHopDong = maHopDong;
            this.maKhachHang = maKhachHang;
            this.ngayBatDau = ngayBatDau;
            this.ngayKetThuc = ngayKetThuc;
            this.tongTien = tongTien;
            this.datCoc = datCoc;
            this.daThanhToan = daThanhToan;
            this.trangThai = trangThai;
            this.ghiChu = ghiChu;
        }

        // Empty constructor
        public HopDong() {}

        // Getters and Setters
        public String getMaHopDong() { return maHopDong; }
        public void setMaHopDong(String maHopDong) { this.maHopDong = maHopDong; }

        public String getMaKhachHang() { return maKhachHang; }
        public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

        public int getSoLuongPhongMuonThue() { return soLuongPhongMuonThue; }
        public void setSoLuongPhongMuonThue(int soLuongPhongMuonThue) { this.soLuongPhongMuonThue = soLuongPhongMuonThue; }

        public String getLichDatPhong() { return lichDatPhong; }
        public void setLichDatPhong(String lichDatPhong) { this.lichDatPhong = lichDatPhong; }

        public String getNgayBatDau() { return ngayBatDau; }
        public void setNgayBatDau(String ngayBatDau) { this.ngayBatDau = ngayBatDau; }

        public String getNgayKetThuc() { return ngayKetThuc; }
        public void setNgayKetThuc(String ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }

        public int getTongNgayThue() { return tongNgayThue; }
        public void setTongNgayThue(int tongNgayThue) { this.tongNgayThue = tongNgayThue; }

        public int getDaThanhToan() { return daThanhToan; }
        public void setDaThanhToan(int daThanhToan) { this.daThanhToan = daThanhToan; }

        public String getNgayThanhToan() { return ngayThanhToan; }
        public void setNgayThanhToan(String ngayThanhToan) { this.ngayThanhToan = ngayThanhToan; }

        public String getGhiChu() { return ghiChu; }
        public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

        public int getDatCoc() { return datCoc; }
        public void setDatCoc(int datCoc) { this.datCoc = datCoc; }

        public int getTongTien() { return tongTien; }
        public void setTongTien(int tongTien) { this.tongTien = tongTien; }

        public String getPhuongThucThanhToan() { return phuongThucThanhToan; }
        public void setPhuongThucThanhToan(String phuongThucThanhToan) { this.phuongThucThanhToan = phuongThucThanhToan; }

        public String getTrangThai() { return trangThai; }
        public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

        @Override
        public String toString() {
            return "HopDong{" +
                    "maHopDong='" + maHopDong + '\'' +
                    ", maKhachHang='" + maKhachHang + '\'' +
                    ", soLuongPhongMuonThue=" + soLuongPhongMuonThue +
                    ", lichDatPhong='" + lichDatPhong + '\'' +
                    ", ngayBatDau='" + ngayBatDau + '\'' +
                    ", ngayKetThuc='" + ngayKetThuc + '\'' +
                    ", tongNgayThue=" + tongNgayThue +
                    ", daThanhToan=" + daThanhToan +
                    ", ngayThanhToan='" + ngayThanhToan + '\'' +
                    ", ghiChu='" + ghiChu + '\'' +
                    ", datCoc=" + datCoc +
                    ", tongTien=" + tongTien +
                    ", phuongThucThanhToan='" + phuongThucThanhToan + '\'' +
                    ", trangThai='" + trangThai + '\'' +
                    '}';
        }
    }