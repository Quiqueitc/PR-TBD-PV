package PTBDPV.datos;

public class datComp
{
  private static datEmpleados datEmpleados;
    private static String [] datSucu=new String[2];
    private static boolean Señal;
  public  datComp(datEmpleados datEmpleado){
      datEmpleados=datEmpleado;
  }
  public  datComp(boolean Se){
      Señal=Se;
      System.out.println("Valor se   "+ Señal);
  }
  public datComp(String[] datSucu){
      this.datSucu=datSucu;
  }
  public  datEmpleados dr(){
      return datEmpleados;
  }
  public boolean dS(){
      return Señal;
  }
  public datComp(){}
  public String[] getDatSucu(){
      return datSucu;
  }
}
