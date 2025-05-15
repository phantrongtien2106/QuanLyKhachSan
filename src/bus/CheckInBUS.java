package bus;

import dao.CheckInDAO;
import model.CheckIn;

import java.util.List;

public class CheckInBUS {
    private CheckInDAO dao = new CheckInDAO();

    public boolean themCheckIn(CheckIn ci) {
        return dao.insert(ci);
    }

    public List<CheckIn> layTatCaCheckIn() {
        return dao.getAll();
    }
}
