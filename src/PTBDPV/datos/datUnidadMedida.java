package PTBDPV.datos;

public class datUnidadMedida {

    char idUM;
    String decripcion;

    public datUnidadMedida() {
    }

    public datUnidadMedida(char idUM, String decripcion) {
        this.idUM = idUM;
        this.decripcion = decripcion;
    }

    public char getIdUM() {
        return idUM;
    }

    public void setIdUM(char idUM) {
        this.idUM = idUM;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }
}
