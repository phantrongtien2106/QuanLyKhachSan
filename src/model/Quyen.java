package model;

public class Quyen {
    private String maQuyen;
    private String tenQuyen;
    public Quyen(String maQuyen, String tenQuyen) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
    }

    public String getMaQuyen() {
        return maQuyen;
    }
    public String getTenQuyen() {
        return tenQuyen;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Quyen quyen = (Quyen) obj;
        return maQuyen.equals(quyen.maQuyen);
    }
    @Override
    public int hashCode() {
        return maQuyen.hashCode();
    }
}
