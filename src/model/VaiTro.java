package System.model;

public class VaiTro {
    private String maVaiTro;
    private String moTa;
    public VaiTro(String ma, String moTa) {
        this.maVaiTro = ma;
        this.moTa = moTa;
    }
    public String getMaVaiTro() { return maVaiTro; }
    public String getMoTa() { return moTa; }
    @Override public String toString() { return maVaiTro + " - " + moTa; }
}
