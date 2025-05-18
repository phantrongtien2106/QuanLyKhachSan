package bus;

import dao.NhanVienDAO;
import model.NhanVien;
<<<<<<< Updated upstream

import java.util.ArrayList;
=======
>>>>>>> Stashed changes
import java.util.List;
import java.util.stream.Collectors;

public class NhanVienBUS {
    private NhanVienDAO nhanVienDAO;
    private static NhanVien currentUser; // Lưu nhân viên đang đăng nhập

    public NhanVienBUS() {
        nhanVienDAO = new NhanVienDAO();
    }

    // Quản lý session đăng nhập
    public static NhanVien getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(NhanVien nv) {
        currentUser = nv;
    }

    public void dangXuat() {
        currentUser = null;
    }

    // Kiểm tra đăng nhập
    public NhanVien kiemTraDangNhap(String maNhanVien, String matKhau) {
        if (maNhanVien == null || matKhau == null ||
                maNhanVien.trim().isEmpty() || matKhau.trim().isEmpty()) {
            return null;
        }

        NhanVien nv = nhanVienDAO.kiemTraDangNhap(maNhanVien, matKhau);
        if (nv != null) {
            currentUser = nv; // Lưu thông tin người đăng nhập
        }
        return nv;
    }

    // Phương thức tạo mã nhân viên tự động
    public String taoMaNhanVien() {
        List<NhanVien> list = nhanVienDAO.getAll();
        if (list.isEmpty()) {
            return "NV001";
        }

        // Tìm mã lớn nhất
        int max = 0;
        for (NhanVien nv : list) {
            String ma = nv.getMaNhanVien();
            if (ma.startsWith("NV")) {
                try {
                    int id = Integer.parseInt(ma.substring(2));
                    if (id > max) {
                        max = id;
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua mã không đúng định dạng
                }
            }
        }

        // Tạo mã mới
        return String.format("NV%03d", max + 1);
    }

    // Tìm kiếm nhân viên
    public List<NhanVien> timKiem(String tuKhoa) {
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
            return getAll();
        }

        String keyword = tuKhoa.toLowerCase().trim();
        return nhanVienDAO.getAll().stream()
                .filter(nv -> nv.getMaNhanVien().toLowerCase().contains(keyword) ||
                        nv.getHoTen().toLowerCase().contains(keyword) ||
                        nv.getChucVu().toLowerCase().contains(keyword) ||
                        (nv.getSoDienThoai() != null && nv.getSoDienThoai().contains(keyword)) ||
                        (nv.getEmail() != null && nv.getEmail().toLowerCase().contains(keyword)))
                .collect(Collectors.toList());
    }

    // Lấy nhân viên theo chức vụ
    public List<NhanVien> getNhanVienTheoChucVu(String chucVu) {
        if (chucVu == null || chucVu.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return nhanVienDAO.getAll().stream()
                .filter(nv -> nv.getChucVu().equalsIgnoreCase(chucVu))
                .collect(Collectors.toList());
    }

    public boolean them(NhanVien nv) {
        // Kiểm tra dữ liệu hợp lệ
        if (!kiemTraDuLieuHopLe(nv)) {
            return false;
        }

        // Kiểm tra mã nhân viên đã tồn tại chưa
        if (nhanVienDAO.getByMa(nv.getMaNhanVien()) != null) {
            return false; // Mã đã tồn tại
        }

        return nhanVienDAO.them(nv);
    }

    public boolean capNhat(NhanVien nv) {
        if (!kiemTraDuLieuHopLe(nv)) {
            return false;
        }
        return nhanVienDAO.capNhat(nv);
    }

    // Đổi mật khẩu
    public boolean doiMatKhau(String maNhanVien, String matKhauCu, String matKhauMoi) {
        NhanVien nv = nhanVienDAO.kiemTraDangNhap(maNhanVien, matKhauCu);
        if (nv == null) {
            return false; // Mật khẩu cũ không đúng
        }

        nv.setMatKhau(matKhauMoi);
        return nhanVienDAO.capNhatMatKhau(nv);
    }

    // Phương thức kiểm tra dữ liệu chung
    private boolean kiemTraDuLieuHopLe(NhanVien nv) {
        if (nv.getMaNhanVien() == null || nv.getMaNhanVien().trim().isEmpty()) {
            return false;
        }
        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            return false;
        }
        if (nv.getChucVu() == null || nv.getChucVu().trim().isEmpty()) {
            return false;
        }

        // Kiểm tra số điện thoại
        if (nv.getSoDienThoai() != null && !nv.getSoDienThoai().trim().isEmpty()) {
            if (!nv.getSoDienThoai().matches("\\d{10,11}")) {
                return false;
            }
        }

        // Kiểm tra định dạng email
        if (nv.getEmail() != null && !nv.getEmail().trim().isEmpty()) {
            if (!nv.getEmail().contains("@") || !nv.getEmail().contains(".")) {
                return false;
            }
        }

        return true;
    }

    public List<NhanVien> getAll() {
        return nhanVienDAO.getAll();
    }

    public NhanVien getByMa(String maNhanVien) {
        return nhanVienDAO.getByMa(maNhanVien);
    }

    public boolean xoa(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.trim().isEmpty()) {
            return false;
        }
        return nhanVienDAO.xoa(maNhanVien);
    }
}