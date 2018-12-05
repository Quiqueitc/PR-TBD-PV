package PTBDPV.datos;

public class datProductos {
    String codigo,descripcion,idUniMed;
    double preCosto,preVenta,preMayoreo,noExistencia,exiMinima,ganancia;
    int idDepartamento,idProv;
    int numDetalle;
    String nomDepartamento,nomProv;


    public String getDepartamento() {
        return departamento;
    }
    public datProductos(String codigo, String descripcion, double preVenta, double noExistencia, double exiMinima, String departamento) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.preVenta = preVenta;
        this.noExistencia = noExistencia;
        this.exiMinima = exiMinima;
        this.departamento = departamento;
    }

    public datProductos(String codigo, String descripcion,double preCosto, double preVenta, double noExistencia, double exiMinima) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.preCosto=preCosto;
        this.preVenta = preVenta;
        this.noExistencia = noExistencia;
        this.exiMinima = exiMinima;

    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    String departamento;
    public datProductos() {
    }

    public String getNomDepartamento() {
        return nomDepartamento;
    }

    public void setNomDepartamento(String nomDepartamento) {
        this.nomDepartamento = nomDepartamento;
    }

    public String getNomProv() {
        return nomProv;
    }

    public void setNomProv(String nomProv) {
        this.nomProv = nomProv;
    }


    public datProductos(String codigo, String descripcion, double preCosto, double preVenta, double preMayoreo, double noExistencia, double exiMinima,String idUniMed, int idDepartamento, int idProv) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.idUniMed = idUniMed;
        this.preCosto = preCosto;
        this.preVenta = preVenta;
        this.preMayoreo = preMayoreo;
        this.noExistencia = noExistencia;
        this.exiMinima = exiMinima;
        this.idDepartamento = idDepartamento;
        this.idProv = idProv;
    }


    public datProductos(String codigo, String descripcion, double preCosto, double preVenta, double preMayoreo, double noExistencia, double exiMinima,double ganancia,String idUniMed, String nomDepartamento, String nomProv) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.idUniMed = idUniMed;
        this.preCosto = preCosto;
        this.preVenta = preVenta;
        this.preMayoreo = preMayoreo;
        this.noExistencia = noExistencia;
        this.ganancia=ganancia;
        this.exiMinima = exiMinima;
        this.nomDepartamento = nomDepartamento;
        this.nomProv = nomProv;
    }





    public datProductos(String codigo, String descripcion, double preVenta, double preMayoreo, double exiMinima,double noExistencia,int numDetalle) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.preVenta = preVenta;
        this.preMayoreo = preMayoreo;
        this.noExistencia = noExistencia;
        this.exiMinima = exiMinima;
        this.numDetalle=numDetalle;
    }
    public datProductos(String codigo, double noExistencia, int idProv,String descripcion ) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.noExistencia = noExistencia;
        this.idProv = idProv;
    }

    public datProductos(String codigo, String descripcion, double noExistencia ) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.noExistencia = noExistencia;
    }
    /*public datProductos(String codigo, int IdProv, int IdDepartamento ,String Descripcion ) {
        this.codigo = codigo;
        this.idProv = IdProv;
        this.idDepartamento = IdDepartamento;
        this.descripcion=Descripcion;
    }*/

    public int getNumDetalle() {
        return numDetalle;
    }

    public void setNumDetalle(int numDetalle) {
        this.numDetalle = numDetalle;
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
