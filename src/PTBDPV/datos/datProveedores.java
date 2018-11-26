package PTBDPV.datos;

public class datProveedores {
    int idProv;
    String nombre;
    String telefono;
    String descripcion;

    public datProveedores(int idProv, String nombre) {
        this.idProv = idProv;
        this.nombre = nombre;
    }


    public int getIdProv() {
        return idProv;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
