package PTBDPV.datos;

import java.sql.Date;

public class datMovimientos {
    int idMovimiento;
    String descripción, idUsu,rfc,idTipo;
    double cantidad;
    Date fecha;
    public datMovimientos(String descripción, double cantidad,String idUsu, String rfc, String idTipo) {
        this.descripción = descripción;
        this.idUsu = idUsu;
        this.rfc = rfc;
        this.idTipo = idTipo;
        this.cantidad = cantidad;
    }


    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(String idUsu) {
        this.idUsu = idUsu;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


}
