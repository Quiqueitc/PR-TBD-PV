package PTBDPV.datos;

import java.sql.Date;

public class datFactura {
    int noFactura,idClie;
    Date fecha;
    Double total;
    String idUsu,idMetPago;
    String estatus;
    String NomCompleto;
    public String getNomCompleto() {
        return NomCompleto;
    }

    public void setNomCompleto(String nomCompleto) {
        NomCompleto = nomCompleto;
    }





    public datFactura(int noFactura, Date fecha, Double total, String idUsu,String nomCompleto, String idMetPago, String estatus) {
        this.noFactura = noFactura;
        //this.idClie = idClie;
        this.fecha = fecha;
        this.total = total;
        this.idUsu = idUsu;
        this.NomCompleto=nomCompleto;
        this.idMetPago = idMetPago;
        this.estatus=estatus;
    }
    public datFactura(int noFactura, Date fecha, Double total, int idClie,String idUsu, String idMetPago) {
        this.noFactura = noFactura;
        this.idClie = idClie;
        this.fecha = fecha;
        this.total = total;
        this.idUsu = idUsu;
        this.idMetPago = idMetPago;
    }

    public datFactura(int noFactura, Date fecha, Double total) {
        this.noFactura = noFactura;
        this.fecha = fecha;
        this.total = total;
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

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }


}


