package bus;

     import dao.CheckOutDAO;
     import model.CheckOut;
     import model.HoaDon;
     import model.PhieuDatPhong;
     import model.Phong;
     import java.time.LocalDate;
     import java.time.temporal.ChronoUnit;
     import java.time.format.DateTimeFormatter;
     import java.util.ArrayList;
     import java.util.List;
     import java.util.stream.Collectors;

     public class CheckOutBUS {
         private CheckOutDAO dao = new CheckOutDAO();
         private PhieuDatPhongBUS phieuDatPhongBUS = new PhieuDatPhongBUS();
         private ChiTietPhieuDatPhongBUS ctpdpBUS = new ChiTietPhieuDatPhongBUS();
         private PhongBUS phongBUS = new PhongBUS();
         private ChiTietDichVuBUS chiTietDichVuBUS = new ChiTietDichVuBUS();

         /**
          * Thêm mới một bản ghi check-out
          */
         public boolean themCheckOut(CheckOut checkOut) {
             return dao.insert(checkOut);
         }

         /**
          * Lấy toàn bộ lịch sử check-out
          */
         public List<CheckOut> getLichSuCheckOut() {
             return dao.getAll();
         }

         /**
          * Tìm kiếm check-out theo từ khóa
          */
         public List<CheckOut> timKiemCheckOut(String keyword) {
             if (keyword == null || keyword.trim().isEmpty()) {
                 return dao.getAll();
             }

             String search = keyword.toLowerCase().trim();
             return dao.getAll().stream()
                     .filter(co ->
                             co.getMaCheckOut().toLowerCase().contains(search) ||
                                     co.getMaPhieu().toLowerCase().contains(search) ||  // Changed from getMaPhieuHopDong
                                     co.getMaNhanVien().toLowerCase().contains(search) ||
                                     co.getGhiChu().toLowerCase().contains(search))
                     .collect(Collectors.toList());
         }

         /**
          * Lấy bản ghi check-out theo mã
          */
         public CheckOut getCheckOutByMa(String maCheckOut) {
             return dao.getByMa(maCheckOut);
         }

         /**
          * Lấy danh sách check-out theo mã phiếu hoặc hợp đồng
          */
         public List<CheckOut> getCheckOutByMaPhieuHD(String maPhieuHoacHD) {
             return dao.getByMaPhieuHoacHD(maPhieuHoacHD);
         }

         /**
          * Kiểm tra đã check-out chưa
          */
         public boolean daCheckOut(String maPhieuHoacHD) {
             List<CheckOut> list = getCheckOutByMaPhieuHD(maPhieuHoacHD);
             return !list.isEmpty();
         }

         /**
          * Xóa bản ghi check-out
          */
         public boolean xoaCheckOut(String maCheckOut) {
             return dao.delete(maCheckOut);
         }

         /**
          * Cập nhật ghi chú
          */
         public boolean capNhatGhiChu(String maCheckOut, String ghiChu) {
             return dao.updateGhiChu(maCheckOut, ghiChu);
         }

         /**
          * Thống kê số lượng theo ngày
          */
         public int getSoLuongCheckOutTheoNgay(String ngay) {
             return dao.getSoLuongTheoNgay(ngay);
         }

         /**
          * Lọc các bản ghi check-out trong khoảng ngày
          */
         public List<CheckOut> locTheoNgay(String tuNgay, String denNgay) {
             return dao.getAll().stream()
                     .filter(co -> {
                         String ngay = co.getNgayCheckOut().split(" ")[0];  // Changed from getThoiGianCheckOut
                         return ngay.compareTo(tuNgay) >= 0 && ngay.compareTo(denNgay) <= 0;
                     })
                     .collect(Collectors.toList());
         }

         /**
          * Lọc theo mã nhân viên
          */
         public List<CheckOut> getTheoNhanVien(String maNV) {
             return dao.getAll().stream()
                     .filter(co -> co.getMaNhanVien().equals(maNV))
                     .collect(Collectors.toList());
         }

         /**
          * Lấy ngày nhận phòng từ phiếu đặt phòng
          */
         public String getNgayNhanPhong(String maPhieu) {
             PhieuDatPhong phieu = phieuDatPhongBUS.getPhieuByMaPhieu(maPhieu);  // Changed from getPhieuByMa
             if (phieu != null) {
                 return phieu.getNgayNhan();
             }
             return null;
         }

         /**
          * Tính tiền phòng dựa trên số ngày lưu trú
          */
         public double tinhTienPhong(String maPhieu, String ngayNhan, String ngayTra) {
             // Chuyển đổi chuỗi ngày thành LocalDate
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             LocalDate dateNhan = LocalDate.parse(ngayNhan, formatter);
             LocalDate dateTra = LocalDate.parse(ngayTra, formatter);

             // Tính số ngày lưu trú
             long soNgay = ChronoUnit.DAYS.between(dateNhan, dateTra);
             if (soNgay < 1) soNgay = 1; // Tối thiểu 1 ngày

             // Lấy danh sách phòng của phiếu
             List<String> dsPhong = ctpdpBUS.getPhongByMaPhieu(maPhieu);
             double tongTien = 0;

             // Tính tiền từng phòng
             for (String maPhong : dsPhong) {
                 Phong phong = phongBUS.getPhongByMa(maPhong);
                 if (phong != null) {
                     tongTien += phong.getGia() * soNgay;
                 }
             }

             return tongTien;
         }

         /**
          * Tính tiền dịch vụ cho phiếu
          */
// Fix for DichVu and ChiTietDichVu in tinhTienDichVu method
         public double tinhTienDichVu(String maPhieu) {
             double tongTien = 0;
             List<model.ChiTietDichVu> dsDichVu = chiTietDichVuBUS.getDichVuByMaPhieu(maPhieu);

             for (model.ChiTietDichVu ctdv : dsDichVu) {
                 DichVuBUS dichVuBUS = new DichVuBUS();
                 model.DichVu dichVu = dichVuBUS.getDichVuByMa(ctdv.getMaDv());
                 if (dichVu != null) {
                     tongTien += dichVu.getGia() * ctdv.getSoLuong();
                 }
             }

             return tongTien;
         }

         /**
          * Thực hiện checkout và tính tiền
          */
// Fix the HoaDon constructor parameters in thucHienCheckout method
/**
          * Thực hiện checkout và tính tiền
          */
         public boolean thucHienCheckout(String maPhieu) {
             try {
                 // Tạo hóa đơn mới nếu chưa có
                 HoaDonBUS hoaDonBUS = new HoaDonBUS();
                 HoaDon hoaDon = hoaDonBUS.getHoaDonByMaPhieu(maPhieu);

                 if (hoaDon == null) {
                     // Tạo mã hóa đơn mới
                     String maHoaDon = hoaDonBUS.taoMaHoaDon();
                     String ngayNhan = getNgayNhanPhong(maPhieu);
                     String ngayTra = hoaDonBUS.getNgayHienTai();

                     // Tạo hóa đơn mới - Fix constructor parameters to match HoaDon class
                     hoaDon = new HoaDon(
                             maHoaDon,           // id
                             ngayNhan,           // ngayNhanPhong
                             ngayTra,            // ngayTraPhong
                             ngayTra,            // ngayThanhToan (cùng với ngày trả)
                             0.0,                // tongTien ban đầu
                             "Tiền mặt",         // phuongThucThanhToan
                             "da_thanh_toan",    // trangThai
                             maPhieu,            // maPhieu
                             null                // maHopDong (không có)
                     );
                     hoaDonBUS.themHoaDon(hoaDon);
                 }

                 // Tính tiền phòng
                 double tienPhong = tinhTienPhong(maPhieu, hoaDon.getNgayNhan(), hoaDon.getNgayTra());

                 // Tính tiền dịch vụ
                 double tienDichVu = tinhTienDichVu(maPhieu);

                 // Cập nhật tổng tiền
                 double tongTien = tienPhong + tienDichVu;
                 hoaDon.setTongTien(tongTien);

                 // Cập nhật hóa đơn
                 return hoaDonBUS.capNhatHoaDon(hoaDon);
             } catch (Exception e) {
                 e.printStackTrace();
                 return false;
             }
         }     }