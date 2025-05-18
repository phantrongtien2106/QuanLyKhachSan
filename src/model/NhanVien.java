package model;
import java.sql.Date;

public class NhanVien {
    private String maNhanVien;
    private String hoTen;
    private String chucVu;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private String matKhau;
    private Date ngaySinh;
    private double luong;
    private TaiKhoan taiKhoan; // Thêm thuộc tính taiKhoan

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String hoTen, String chucVu,
                    String soDienThoai, String email, String diaChi) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.chucVu = chucVu;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
    }

    public NhanVien(String maNhanVien, String hoTen, String chucVu,
                    String soDienThoai, String email, String diaChi, String matKhau) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.chucVu = chucVu;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.matKhau = matKhau;
    }

    // Constructor mới cho ngày sinh và lương
    public NhanVien(String maNhanVien, Date ngaySinh, double luong) {
        this.maNhanVien = maNhanVien;
        this.ngaySinh = ngaySinh;
        this.luong = luong;
    }

    // Getters and Setters cho ngaySinh và luong
    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public double getLuong() { return luong; }
    public void setLuong(double luong) { this.luong = luong; }

    // Getter và Setter cho taiKhoan
    public TaiKhoan getTaiKhoan() { return taiKhoan; }
    
    /**
     * Thiết lập tài khoản cho nhân viên
     * @param taiKhoan Đối tượng TaiKhoan cần gán cho nhân viên
     */
    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
        
        // Đồng bộ thông tin cơ bản từ tài khoản sang nhân viên
        if (taiKhoan != null) {
            this.maNhanVien = taiKhoan.getMaNguoiDung();
            this.hoTen = taiKhoan.getHoTen();
            this.soDienThoai = taiKhoan.getSoDienThoai();
            this.email = taiKhoan.getEmail();
            this.diaChi = taiKhoan.getDiaChi();
        }
    }

    // Các getters và setters khác giữ nguyên
    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
}
