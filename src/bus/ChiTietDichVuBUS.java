package bus;

    import dao.ChiTietDichVuDAO;
    import model.ChiTietDichVu;
    import java.util.List;

    public class ChiTietDichVuBUS {
        private ChiTietDichVuDAO chiTietDichVuDAO;

        public ChiTietDichVuBUS() {
            chiTietDichVuDAO = new ChiTietDichVuDAO();
        }

        public boolean themChiTietDichVu(ChiTietDichVu ctdv) {
            return chiTietDichVuDAO.insert(ctdv);
        }

        public List<ChiTietDichVu> getDichVuByMaPhieu(String maPhieu) {
            return chiTietDichVuDAO.getDichVuByMaPhieu(maPhieu);
        }

        public List<ChiTietDichVu> getDichVuByMaPhieuOrHopDong(String maPhieuHoacHD, boolean isHopDong) {
            if (isHopDong) {
                return chiTietDichVuDAO.getDichVuByMaHopDong(maPhieuHoacHD);
            } else {
                return chiTietDichVuDAO.getDichVuByMaPhieu(maPhieuHoacHD);
            }
        }

        // Phương thức này không cần thiết nữa vì đã sửa phương thức trên
        // public List<ChiTietDichVu> getByMaPhieu(String maPhieu) {
        //     return chiTietDichVuDAO.getByMaPhieu(maPhieu);
        // }

        public boolean capNhatChiTietDichVu(ChiTietDichVu ctdv) {
            return chiTietDichVuDAO.update(ctdv);
        }

        public boolean xoaChiTietDichVu(String maPhieu, String maDV) {
            return chiTietDichVuDAO.delete(maPhieu, maDV);
        }
    }