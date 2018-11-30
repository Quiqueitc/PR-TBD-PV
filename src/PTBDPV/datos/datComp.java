package PTBDPV.datos;

public class datComp
{
  private static datEmpleados datEmpleados;

  public  datComp(datEmpleados datEmpleado){
      System.out.println("soy "+datEmpleado.getIdUsu());
      datEmpleados=datEmpleado;
  }
  public  datEmpleados dr(){
      return datEmpleados;
  }
  public datComp(){}
}
