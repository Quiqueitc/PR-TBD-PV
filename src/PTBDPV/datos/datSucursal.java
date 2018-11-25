package PTBDPV.datos;

public class datSucursal {
    String rfc;
    String nombre;
    String domicilio;

    public datSucursal(String rfc, String nombre, String domicilio, String telefono, String ciudad, String idUsu) {
        this.rfc = rfc;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.idUsu = idUsu;
    }

    String telefono;
    String ciudad;
    String idUsu;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(String idUsu) {
        this.idUsu = idUsu;
    }



}
