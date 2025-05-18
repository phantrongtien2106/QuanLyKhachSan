
package model;

import java.util.Date;
import java.util.List;

public class TaiKhoan {
    private String maNguoiDung;
    private String hoTen;
    private String cccd;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String matKhau;
    private List<Quyen> danhSachQuyen;

    public TaiKhoan(String maNguoiDung, String hoTen, List<Quyen> danhSachQuyen) {
        this.maNguoiDung = maNguoiDung;
        this.hoTen = hoTen;
        this.danhSachQuyen = danhSachQuyen;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public List<Quyen> getDanhSachQuyen() {
        return danhSachQuyen;
    }

    public void themQuyen(Quyen quyen) {
        if (!danhSachQuyen.contains(quyen)) {
            danhSachQuyen.add(quyen);
        }
    }

    public void xoaQuyen(Quyen quyen) {
        danhSachQuyen.remove(quyen);
    }

    public boolean coQuyen(String maQuyen) {
        for (Quyen quyen : danhSachQuyen) {
            if (quyen.getMaQuyen().equals(maQuyen)) {
                return true;
            }
        }
        return false;
    }

}

