package System.bus;

import System.dao.NhanVienDAO;
import System.model.NhanVien;
import java.util.List;

public class NhanVienBUS {
    private NhanVienDAO dao;
    
    public NhanVienBUS() {
        this.dao = new NhanVienDAO();
    }
    
    public boolean themNhanVien(NhanVien nv) {
        return dao.themNhanVien(nv);
    }
    
    public List<NhanVien> layDanhSachNhanVien() {
        return dao.layDanhSachNhanVien();
    }
    
    public NhanVien timNhanVienTheoMa(String ma) {
        return dao.timNhanVienTheoMa(ma);
    }
    
    public boolean capNhatNhanVien(NhanVien nv) {
        return dao.capNhatNhanVien(nv);
    }
    
    public boolean xoaNhanVien(String maNhanVien) {
        // Cần thêm chức năng xóa trong DAO
        // Trả về false tạm thời
        return false;
    }
}