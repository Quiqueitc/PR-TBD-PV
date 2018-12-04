package PTBDPV.datos;

public class datClientes {
    int idClie;
    String nomCompleto,domicilio,telefono,email;
    double limCredito;
    public datClientes() {
    }

    public datClientes(int idClie, String nomCompleto) {
        this.idClie = idClie;
        this.nomCompleto = nomCompleto;
    }
    public datClientes(int idClie, String nomCompleto, String domicilio, String telefono, String email, double limCredito) {
        this.idClie = idClie;
        this.nomCompleto = nomCompleto;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.email = email;
        this.limCredito = limCredito;
    }

    public int getIdClie() {
        return idClie;
    }

    public void setIdClie(int idClie) {
        this.idClie = idClie;
    }

    public String getNomCompleto() {
        return nomCompleto;
    }

    public void setNomCompleto(String nomCompleto) {
        this.nomCompleto = nomCompleto;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLimCredito() {
        return limCredito;
    }

    public void setLimCredito(double limCredito) {
        this.limCredito = limCredito;
    }

}
