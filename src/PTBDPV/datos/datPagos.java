package PTBDPV.datos;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class datPagos {
    int noFactura,idClie,numPago;
    Date fecha;
    double abono;
    public datPagos() {

    }

    public datPagos(int noFactura, int idClie, int numPago, Date fecha, double abono) {
        this.noFactura = noFactura;
        this.idClie = idClie;
        this.numPago = numPago;
        this.fecha = fecha;
        this.abono = abono;
    }

    public datPagos(int numPago, Date fecha, double abono) {
        this.numPago = numPago;
        this.fecha = fecha;
        this.abono = abono;
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

    public int getNumPago() {
        return numPago;
    }

    public void setNumPago(int numPago) {
        this.numPago = numPago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getAbono() {
        return abono;
    }

    public void setAbono(double abono) {
        this.abono = abono;
    }





}
