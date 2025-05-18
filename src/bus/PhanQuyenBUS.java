package bus;


import dao.PhanQuyenDAO;
import model.LoaiQuyen;
import model.ChucNang;

import java.util.*;

public class PhanQuyenBUS {
    private PhanQuyenDAO dao = new PhanQuyenDAO();

    public List<ChucNang> layDanhSachChucNang() {
        return dao.layDanhSachChucNang();
    }

    public List<LoaiQuyen> layLoaiQuyenTheoChucNang(String maVaiTro, String maChucNang) {
        return dao.layLoaiQuyenTheoChucNang(maVaiTro, maChucNang);
    }

    public boolean capNhatLoaiQuyen(String maVaiTro, String maChucNang, LoaiQuyen loaiQuyen, boolean capNhat) {
        return dao.capNhatLoaiQuyen(maVaiTro, maChucNang, loaiQuyen, capNhat);
    }
}
