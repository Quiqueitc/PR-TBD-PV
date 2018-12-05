package PTBDPV.datos;

public class datDetalles {
    int  noFactura,numDetalle;
    double cantidad,precio,descuento;
    String codigo;
    String desc;
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public datDetalles(int noFactura, int numDetalle, double cantidad, double precio, String desc, String codigo) {
        this.noFactura = noFactura;
        this.numDetalle = numDetalle;
        this.cantidad = cantidad;
        this.precio = precio;
        this.desc = desc;
        this.codigo = codigo;
    }

    public int getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(int noFactura) {
        this.noFactura = noFactura;
    }

    public int getNumDetalle() {
        return numDetalle;
    }

    public void setNumDetalle(int numDetalle) {
        this.numDetalle = numDetalle;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
