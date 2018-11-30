package PTBDPV.datos;

public class datProductos {
    String codigo,descripcion,idUniMed;
    double preCosto,preVenta,preMayoreo,noExistencia,exiMinima,ganancia;
    int idDepartamento,idProv;
    public datProductos(String codigo, String descripcion, double preVenta, double preMayoreo, double exiMinima,double noExistencia) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.preVenta = preVenta;
        this.preMayoreo = preMayoreo;
        this.noExistencia = noExistencia;
        this.exiMinima = exiMinima;
    }
    public datProductos(String codigo, double noExistencia, int idProv,String descripcion ) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.noExistencia = noExistencia;
        this.idProv = idProv;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdUniMed() {
        return idUniMed;
    }

    public void setIdUniMed(String idUniMed) {
        this.idUniMed = idUniMed;
    }

    public double getPreCosto() {
        return preCosto;
    }

    public void setPreCosto(double preCosto) {
        this.preCosto = preCosto;
    }

    public double getPreVenta() {
        return preVenta;
    }

    public void setPreVenta(double preVenta) {
        this.preVenta = preVenta;
    }

    public double getPreMayoreo() {
        return preMayoreo;
    }

    public void setPreMayoreo(double preMayoreo) {
        this.preMayoreo = preMayoreo;
    }

    public double getNoExistencia() {
        return noExistencia;
    }

    public void setNoExistencia(double noExistencia) {
        this.noExistencia = noExistencia;
    }

    public double getExiMinima() {
        return exiMinima;
    }

    public void setExiMinima(double exiMinima) {
        this.exiMinima = exiMinima;
    }

    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public int getIdProv() {
        return idProv;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
    }


}
