package bus;

import dao.DichVuDAO;
import model.DichVu;
import java.util.List;

public class DichVuBUS {
    private DichVuDAO dao = new DichVuDAO();

    public List<DichVu> getAllDichVu() {
        return dao.getAll();
    }

    public DichVu getDichVuByMa(String maDV) {
        for (DichVu dv : getAllDichVu()) {
            if (dv.getMaDv().equals(maDV)) {
                return dv;
            }
        }
        return null;
    }
}
