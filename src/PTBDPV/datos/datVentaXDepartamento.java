package PTBDPV.datos;

public class datVentaXDepartamento {

    String descripcion;
    double total;

    public datVentaXDepartamento() {

    }

    public datVentaXDepartamento(String descripcion, double total) {
        this.descripcion = descripcion;
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
