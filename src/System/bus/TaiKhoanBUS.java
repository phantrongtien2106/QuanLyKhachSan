package System.bus;

import System.dao.TaiKhoanDAO;
import System.model.TaiKhoan;
import System.model.Quyen;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanBUS {
    private TaiKhoanDAO dao;

    public TaiKhoanBUS() {
        dao = new TaiKhoanDAO();
    }

    public boolean dangNhap(String soDienThoai, String password) {
        return dao.kiemTraDangNhap(soDienThoai, password);
    }

    public boolean coVaiTro(String maNguoiDung, String role) {
        return dao.coVaiTro(maNguoiDung, role);
    }

    public TaiKhoan getThongTin(String soDienThoai) {
        return dao.layThongTinTaiKhoan(soDienThoai);
    }

    public boolean dangKyTaiKhoan(TaiKhoan tk) {
        if (dao.kiemTraTonTai(tk.getSoDienThoai(), tk.getCccd())) {
            return false;
        }
        return dao.themTaiKhoan(tk);
    }

    public boolean kiemTraQuyenChucNang(String maVaiTro, String maChucNang) {
        return dao.kiemTraQuyenChucNang(maVaiTro, maChucNang);
    }

    public void ganVaiTroTheoLoai(String maNguoiDung, String loai) {
        String vaiTro = switch (loai.toLowerCase()) {
            case "admin" -> "ADMIN";
            case "manager" -> "MANAGER";
            case "receptionist" -> "RECEPTIONIST";
            case "khach_hang" -> "USER";
            default -> "USER";
        };
        dao.ganVaiTroChoTaiKhoan(maNguoiDung, vaiTro);
    }

    public boolean taoTaiKhoanNhanVien(TaiKhoan tk, String loaiVaiTro) {
        if (dao.kiemTraTonTai(tk.getSoDienThoai(), tk.getCccd())) return false;
        boolean themOK = dao.themTaiKhoan(tk);
        if (themOK) {
            ganVaiTroTheoLoai(tk.getMaNguoiDung(), loaiVaiTro);
        }
        return themOK;
    }

    public List<TaiKhoan> layDanhSachTaiKhoan() {
        return dao.layDanhSachTaiKhoan();
    }

    public boolean themTaiKhoan(TaiKhoan tk) {
        // Kiểm tra dữ liệu đầu vào
        if (tk.getMaNguoiDung() == null || tk.getMaNguoiDung().trim().isEmpty() ||
                tk.getHoTen() == null || tk.getHoTen().trim().isEmpty() ||
                tk.getSoDienThoai() == null || tk.getSoDienThoai().trim().isEmpty() ||
                tk.getMatKhau() == null || tk.getMatKhau().trim().isEmpty()) {
            return false;
        }

        // Kiểm tra số điện thoại và CCCD đã tồn tại chưa
        if (dao.kiemTraTonTai(tk.getSoDienThoai(), tk.getCccd())) {
            return false;
        }

        return dao.themTaiKhoan(tk);
    }

    public boolean capNhatTaiKhoan(TaiKhoan tk) {
        // Kiểm tra dữ liệu đầu vào
        if (tk.getMaNguoiDung() == null || tk.getMaNguoiDung().trim().isEmpty() ||
                tk.getHoTen() == null || tk.getHoTen().trim().isEmpty() ||
                tk.getSoDienThoai() == null || tk.getSoDienThoai().trim().isEmpty()) {
            return false;
        }

        return dao.capNhatThongTin(tk);
    }

    public boolean xoaTaiKhoan(String maNguoiDung) {
        if (maNguoiDung == null || maNguoiDung.trim().isEmpty()) {
            return false;
        }

        return dao.xoaTaiKhoan(maNguoiDung);
    }

    public TaiKhoan layThongTinTheoMa(String maNguoiDung) {
        if (maNguoiDung == null || maNguoiDung.trim().isEmpty()) {
            return null;
        }
        return dao.layThongTinTheoMa(maNguoiDung);
    }

    public boolean capNhatMatKhau(TaiKhoan tk) {
        return dao.capNhatMatKhau(tk);
    }

    public TaiKhoan getThongTinTheoMa(String maNguoiDung) {
        return dao.layThongTinTheoMa(maNguoiDung);
    }

    public boolean capNhatThongTin(TaiKhoan tk) {
        return dao.capNhatThongTin(tk);
    }

    /**
     * Tìm kiếm tài khoản dựa trên từ khóa
     * @param tuKhoa Từ khóa tìm kiếm (mã người dùng, họ tên, email, số điện thoại)
     * @return Danh sách tài khoản phù hợp với từ khóa tìm kiếm
     */
    public List<TaiKhoan> timKiemTaiKhoan(String tuKhoa) {
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
            return layDanhSachTaiKhoan();
        }

        List<TaiKhoan> ketQua = new ArrayList<>();
        List<TaiKhoan> danhSachTaiKhoan = layDanhSachTaiKhoan();

        String tuKhoaLowerCase = tuKhoa.toLowerCase().trim();

        for (TaiKhoan taiKhoan : danhSachTaiKhoan) {
            if ((taiKhoan.getMaNguoiDung() != null && taiKhoan.getMaNguoiDung().toLowerCase().contains(tuKhoaLowerCase)) ||
                    (taiKhoan.getHoTen() != null && taiKhoan.getHoTen().toLowerCase().contains(tuKhoaLowerCase)) ||
                    (taiKhoan.getEmail() != null && taiKhoan.getEmail().toLowerCase().contains(tuKhoaLowerCase)) ||
                    (taiKhoan.getSoDienThoai() != null && taiKhoan.getSoDienThoai().toLowerCase().contains(tuKhoaLowerCase))) {
                ketQua.add(taiKhoan);
            }
        }

        return ketQua;
    }

    /**
     * Lấy chuỗi tên quyền từ danh sách quyền
     * @param danhSachQuyen Danh sách các quyền
     * @return Chuỗi tên quyền, phân cách bởi dấu phẩy
     */
    public String getTenQuyen(List<Quyen> danhSachQuyen) {
        if (danhSachQuyen == null || danhSachQuyen.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (Quyen quyen : danhSachQuyen) {
            if (quyen.getTenQuyen() != null && !quyen.getTenQuyen().isEmpty()) {
                result.append(quyen.getTenQuyen()).append(", ");
            }
        }

        if (result.length() >= 2) {
            return result.substring(0, result.length() - 2);
        }

        return result.toString();
    }

    /**
     * Xác định loại người dùng dựa vào mã người dùng
     * @param maNguoiDung Mã người dùng cần xác định
     * @return Tên loại người dùng
     */
    public String getLoaiNguoiDung(String maNguoiDung) {
        if (maNguoiDung == null || maNguoiDung.length() < 2) {
            return "Không xác định";
        }

        String prefix = maNguoiDung.substring(0, 2).toUpperCase();
        switch (prefix) {
            case "KH":
                return "Khách hàng";
            case "NV":
                if (maNguoiDung.length() >= 3 && maNguoiDung.substring(0, 3).toUpperCase().equals("NVA")) {
                    return "Admin";
                }
                return "Nhân viên";
            default:
                return "Không xác định";
        }
    }
}