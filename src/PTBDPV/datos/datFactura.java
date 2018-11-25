package PTBDPV.datos;

import java.sql.Date;

public class datFactura {
    int noFactura,idClie;
    Date fecha;
    Double total;
    String idUsu,idMetPago;
    public datFactura(int noFactura, Date fecha, Double total, int idClie,String idUsu, String idMetPago) {
        this.noFactura = noFactura;
        this.idClie = idClie;
        this.fecha = fecha;
        this.total = total;
        this.idUsu = idUsu;
        this.idMetPago = idMetPago;
    }


    public int getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(int noFactura) {
        this.noFactura = noFactura;
    }

    public int getIdClie() {
        return idClie;
    }

    public void setIdClie(int idClie) {
        this.idClie = idClie;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(String idUsu) {
        this.idUsu = idUsu;
    }

    public String getIdMetPago() {
        return idMetPago;
    }

    public void setIdMetPago(String idMetPago) {
        this.idMetPago = idMetPago;
    }


}


