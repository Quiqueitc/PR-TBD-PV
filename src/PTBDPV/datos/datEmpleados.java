package PTBDPV.datos;

import java.sql.Date;

public class datEmpleados {
    String idUsu, nombre, domicilio, telefono, contrasena, idTip;

    public datEmpleados(String idUsu, String nombre, String domicilio, String telefono, String contrasena, String idTip, Date fecContrato) {
        this.idUsu = idUsu;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.idTip = idTip;
        this.fecContrato = fecContrato;
    }
    public datEmpleados(String idUsu, String nombre) {
        this.idUsu = idUsu;
        this.nombre = nombre;
    }
    public datEmpleados(String idUsu, String nombre,String domicilio,String idTip) {
        this.idUsu = idUsu;
        this.nombre = nombre;
        this.domicilio=domicilio;
        this.idTip=idTip;
    }

    java.sql.Date fecContrato;
    public String getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(String idUsu) {
        this.idUsu = idUsu;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getIdTip() {
        return idTip;
    }

    public void setIdTip(String idTip) {
        this.idTip = idTip;
    }

    public Date getFecContrato() {
        return fecContrato;
    }

    public void setFecContrato(Date fecContrato) {
        this.fecContrato = fecContrato;
    }



}
