package bus;

    import dao.ChiTietHopDongDAO;
    import model.ChiTietHopDong;

    import java.util.List;

    public class ChiTietHopDongBUS {
        private ChiTietHopDongDAO dao = new ChiTietHopDongDAO();

        // ✅ Thêm chi tiết hợp đồng cho từng phòng
        public boolean themChiTiet(ChiTietHopDong ct) {
            return dao.insert(ct);
        }

        // ✅ Lấy toàn bộ danh sách chi tiết theo mã hợp đồng
        public List<ChiTietHopDong> getByMaHopDong(String maHD) {
            return dao.getByMaHopDong(maHD);
        }

        // ✅ Lấy danh sách chi tiết theo mã hợp đồng (alias, để sử dụng khi xóa hợp đồng)
        public List<ChiTietHopDong> getChiTietByMaHD(String maHD) {
            return dao.getByMaHopDong(maHD);
        }

        // ✅ Xoá chi tiết theo mã hợp đồng và mã phòng (nếu cần)
        public boolean xoaChiTiet(String maHD, String maPhong) {
            return dao.delete(maHD, maPhong);
        }

        // ✅ Cập nhật giá hoặc ghi chú (nếu muốn bổ sung sau)
        public boolean capNhatGhiChu(String maHD, String maPhong, String ghiChu) {
            return dao.capNhatGhiChu(maHD, maPhong, ghiChu);
        }

        // Thêm vào lớp ChiTietHopDongBUS
        public boolean xoaTatCaChiTiet(String maHD) {
            return dao.deleteByMaHD(maHD);
        }

        public List<String> getPhongByMaHopDong(String maHopDong) {
            return dao.getPhongByMaHopDong(maHopDong);
        }



    }