//package System.bus;
//
//import System.dao.TaiKhoanDAO;
//import System.model.TaiKhoan;
//
//import java.util.List;
//
//public class TaiKhoanBUS {
//    private TaiKhoanDAO dao;
//
//    public TaiKhoanBUS() {
//        dao = new TaiKhoanDAO();
//    }
//
//    public boolean dangNhap(String soDienThoai, String password) {
//        return dao.kiemTraDangNhap(soDienThoai, password);
//    }
//
//    public boolean coVaiTro(String maNguoiDung, String role) {
//        return dao.coVaiTro(maNguoiDung, role);
//    }
//
//    public TaiKhoan getThongTin(String soDienThoai) {
//        return dao.layThongTinTaiKhoan(soDienThoai);
//    }
//
//    public boolean dangKyTaiKhoan(TaiKhoan tk) {
//        if (dao.kiemTraTonTai(tk.getSoDienThoai(), tk.getCccd())) {
//            return false;
//        }
//        return dao.themTaiKhoan(tk);
//    }
//    public boolean kiemTraQuyenChucNang(String maVaiTro, String maChucNang) {
//        return dao.kiemTraQuyenChucNang(maVaiTro, maChucNang);
//    }
//    public void ganVaiTroTheoLoai(String maNguoiDung, String loai) {
//        String vaiTro = switch (loai.toLowerCase()) {
//            case "admin" -> "ADMIN";
//            case "manager" -> "MANAGER";
//            case "receptionist" -> "RECEPTIONIST";
//            case "khach_hang" -> "USER";
//            default -> "USER";
//        };
//        dao.ganVaiTroChoTaiKhoan(maNguoiDung, vaiTro);
//    }
//    public boolean taoTaiKhoanNhanVien(TaiKhoan tk, String loaiVaiTro) {
//        if (dao.kiemTraTonTai(tk.getSoDienThoai(), tk.getCccd())) return false;
//        boolean themOK = dao.themTaiKhoan(tk);
//        if (themOK) {
//            ganVaiTroTheoLoai(tk.getMaNguoiDung(), loaiVaiTro);
//        }
//        return themOK;
//    }
//
//    public List<TaiKhoan> layDanhSachTaiKhoan() {
//        return dao.layDanhSachTaiKhoan();
//    }
//    public boolean capNhatMatKhau(TaiKhoan tk) {
//        return dao.capNhatMatKhau(tk);
//    }
//    public TaiKhoan getThongTinTheoMa(String maNguoiDung) {
//        return dao.layThongTinTheoMa(maNguoiDung);
//    }
//    public boolean capNhatThongTin(TaiKhoan tk) {
//        return dao.capNhatThongTin(tk);
//    }
//}
