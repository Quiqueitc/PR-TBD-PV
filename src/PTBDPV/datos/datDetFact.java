package PTBDPV.datos;

public class datDetFact
{


    String descripcion;
    double precio;
    double cantidad;
    double total;
    int noFact;

    public datDetFact(String descripcion, double precio, double cantidad, double total) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.total = total;
        this.noFact = noFact;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getNoFact() {
        return noFact;
    }

    public void setNoFact(int noFact) {
        this.noFact = noFact;
    }
}
