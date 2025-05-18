package System.bus;

import System.dao.TaiKhoanDAO;
import System.model.VaiTro;

import java.util.*;

public class DichVuKiemTraQuyen {
    private TaiKhoanDAO dao = new TaiKhoanDAO();
    private static DichVuKiemTraQuyen instance;
    private String nguoiDungHienTai;

    private DichVuKiemTraQuyen() {}

    public static DichVuKiemTraQuyen getInstance() {
        if (instance == null) {
            instance = new DichVuKiemTraQuyen();
        }
        return instance;
    }

    public void setNguoiDungHienTai(String tenDangNhap) {
        this.nguoiDungHienTai = tenDangNhap;
    }

    public boolean coQuyen(String maChucNang) {
        // Kiểm tra nếu người dùng hiện tại có quyền truy cập chức năng này không
        List<VaiTro> danhSachVaiTro = dao.layVaiTroCuaTaiKhoan(nguoiDungHienTai);
        for (VaiTro vaiTro : danhSachVaiTro) {
            if (dao.kiemTraQuyenChucNang(vaiTro.getMaVaiTro(), maChucNang)) {
                return true;
            }
        }
        return false;
    }
}