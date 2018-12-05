package PTBDPV.datos;

public class datPromociones {
    String codigo,nomPromocion;

    public datPromociones(String codigo, String nomPromocion, double cantMinima, double cantMaxima, double preUnitario) {
        this.codigo = codigo;
        this.nomPromocion = nomPromocion;
        this.cantMinima = cantMinima;
        this.cantMaxima = cantMaxima;
        this.preUnitario = preUnitario;
    }

    double cantMinima;
    double cantMaxima;
    double preUnitario;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNomPromocion() {
        return nomPromocion;
    }

    public void setNomPromocion(String nomPromocion) {
        this.nomPromocion = nomPromocion;
    }

    public double getCantMinima() {
        return cantMinima;
    }

    public void setCantMinima(double cantMinima) {
        this.cantMinima = cantMinima;
    }

    public double getCantMaxima() {
        return cantMaxima;
    }

    public void setCantMaxima(double cantMaxima) {
        this.cantMaxima = cantMaxima;
    }

    public double getPreUnitario() {
        return preUnitario;
    }

    public void setPreUnitario(double preUnitario) {
        this.preUnitario = preUnitario;
    }

}

