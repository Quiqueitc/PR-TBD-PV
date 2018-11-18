package PTBDPV.bd;;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import jdk.nashorn.internal.runtime.ECMAErrors;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDAO {
    Connection conn;
    Alert alert;
    public TransactionDAO(Connection conn)
    {
        this.conn = conn;
    }
   public Boolean ELIMINARTARJETA(String NT) {
        if(ELIMINARTARHISTORIAL(NT)){
            try {
                String query = "DELETE FROM autoactivo WHERE NumTarjeta='"+NT+"'";
                PreparedStatement st = conn.prepareStatement(query);
                st.execute();
                return true;
            } catch (SQLException e) {
                //  System.out.println(e.getMessage());
                alert=new Alert(Alert.AlertType.ERROR);
                if(e.getErrorCode()==1451)
                {
                    alert.setContentText("Este tarjeta no se puede eliminar porque está siendo usado en otras tablas");
                    alert.show();
                }
            }

        }
       return false;
   }
   public Boolean ELIMINARTARHISTORIAL(String NT) {
       try {
           String query = "DELETE FROM autocobrado WHERE NumTarjeta='"+NT+"'";
           PreparedStatement st = conn.prepareStatement(query);
           st.execute();
           return true;
       } catch (SQLException e) {
         //  System.out.println(e.getMessage());
           alert=new Alert(Alert.AlertType.ERROR);
           if(e.getErrorCode()==1451)
           {
               alert.setContentText("Este tarjeta no se puede eliminar porque está siendo usado en otras tablas");
               alert.show();
           }
       }
       return false;
   }
   public Boolean ELIMINARPENSIONADO(int NP) {

       try {
           String query = "DELETE FROM pensionados WHERE NumPensionado='"+NP+"';";
           PreparedStatement st = conn.prepareStatement(query);
           st.execute();
           return true;
       } catch (SQLException e) {
         //  System.out.println(e.getMessage());
           alert=new Alert(Alert.AlertType.ERROR);
           if(e.getErrorCode()==1451)
           {
               alert.setContentText("Este pensionado no se puede eliminar porque está siendo usado en otras tablas");
               alert.show();
           }
       }
       return false;
   }
    public Boolean ELIMINARUSUARIO(String NN) {

        try {
            String query = "DELETE FROM usuario WHERE NickName='"+NN+"'";
            PreparedStatement st = conn.prepareStatement(query);
            st.execute();
            return true;
        } catch (SQLException e) {
            alert=new Alert(Alert.AlertType.ERROR);
            if(e.getErrorCode()==1451)
            {
                alert.setTitle("Error de eliminación");
                alert.setContentText("El usuario no se puede eliminar en este momento porque tiene registros en otras tablas");
                alert.show();
            }
        }
        return false;
    }

    public Boolean MARCAREXTRAVIADA(String NT,String ARG) {
        try {
            String query = "UPDATE autoactivo SET estatus=? WHERE NumTarjeta=?";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1,ARG);
            st.setString(2,NT);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public Boolean VERIFICARADMIN(String CC) {
        try {

            String query = "SELECT NickName FROM usuario WHERE CveTipUsuario='A' and contrasena=SHA('"+CC+"') LIMIT 1;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            String p = null;
            while(rs.next()) {
                p = rs.getString("NickName");
            }
            rs.close();
            st.close();
            if(p!=null)
                    return true;
                else
                    return false;

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return false;
    }


    /*
 *  NumPensionado | int(11)       | NO   | PRI | NULL    | auto_increment |
 | NomDueno      | varchar(250)  | YES  |     | NULL    |                |
 | Placa         | varchar(15)   | NO   |     | NULL    |                |
 | Modelo        | varchar(80)   | YES  |     | NULL    |                |
 | FecInicio     | date          | NO   |     | NULL    |                |
 | FecFin        | date          | NO   |     | NULL    |                |
 | FecRegistro   | date          | NO   |     | NULL    |                |
 | Costo         | decimal(10,0) | NO   |     | NULL    |                |
 | NickName*/
   /*
   NickName   | varchar(20)   | NO   | MUL | NULL    |       |
| NumTarjeta | varchar(100)  | NO   | MUL | NULL    |       |
| Placa      | varchar(15)   | NO   |     | NULL    |       |
| Modelo     | varchar(80)   | NO   |     | NULL    |       |
| FecEntrada | datetime      | NO   |     | NULL    |       |
| FecSalida  | datetime      | NO   |     | NULL    |       |
| CosCobrado | decimal(10,0)
   * */


    public Boolean CONSULTA() {
        ResultSet rs = null;
        String re;
        try {
            String query = "SELECT estatus FROM precio WHERE CvePrecio='DLDFD'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()) {
                re=rs.getString("estatus");
                if(re.equalsIgnoreCase("DF"))
                    return true;
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return false;
    }

    public Boolean SELECT(String NT) {
        try {
            String p;
            String query = "SELECT NumTarjeta FROM autoactivo WHERE NumTarjeta='"+NT+"'AND FecEntrada IS NULL AND estatus='D'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getString("NumTarjeta");
                if(p.equals(NT))
                    return  true;
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
                ex.printStackTrace();
        }

        return false;
    }
    public Boolean SELECTNN(String NT) {
        try {
            String p;
            String query = "SELECT NumTarjeta FROM autoactivo WHERE NumTarjeta='"+NT+"' AND FecEntrada IS NOT NULL AND estatus='D';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getString("NumTarjeta");
                if(p.equals(NT))
                    return  true;
            }
            rs.close();
            st.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }

        return false;
    }

    public double TOTALUSUARIO() {
        double p=0;
        try {

            String query = "SELECT SUM(total) AS TOTAL FROM usuario";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getDouble("TOTAL");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    public int TOTALPER(String ES) {
        int p=0;
        try {

            String query = "SELECT COUNT(NumTarjeta) AS TARJETAS FROM autoactivo WHERE estatus LIKE '"+ES+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getInt("TARJETAS");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    public int TOTALPEN() {
        int p=0;
        try {

            String query = "SELECT COUNT(NomDueno) AS PEN_COBRO FROM pensionados WHERE DATEDIFF(NOW(),FecFin)<=3 AND DATEDIFF(NOW(),FecFin) >=0";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getInt("PEN_COBRO");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    public int DIFERENCIAMESES(Date d1,Date d2) {
        int p=0;
        try {

            String query = "SELECT TIMESTAMPDIFF(MONTH ,'"+d1+"','"+d2+"') AS DIF;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getInt("DIF");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    public int DIFERENCIADIAS(int NP) {
        int p=0;
        try {
            String query = "SELECT DATEDIFF(NOW(),FecFin) AS DIF FROM pensionados WHERE NumPensionado='"+NP+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getInt("DIF");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return Math.abs(p);
    }

    public double TOTAL(String DE,String TG) {
        double p=0;
        try {

            String query = "SELECT SUM(Monto) AS TOTAL FROM otrosgastos WHERE DescripcionG like '"+DE+"' AND CveTipGasto='"+TG+"';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getDouble("TOTAL");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    public double TOTAL(String TG) {
        double p=0;
        try {

            String query = "SELECT SUM(Monto) AS TOTAL FROM otrosgastos WHERE CveTipGasto='"+TG+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getDouble("TOTAL");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    public double TOTALPENSIONADOS() {
        double p=0;
        try {

            String query = "SELECT SUM(costo) AS TOTAL FROM pensionados WHERE estatus='D'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getDouble("TOTAL");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }

    public Boolean UPDATEPRECIO(double PR,double TO,String NN,String CP) {
        try {
            String query = "UPDATE precio SET Precio=?,tolerancia=?,NickName=? WHERE CvePrecio=?";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setDouble(1,PR);
            st.setDouble(2,TO);
            st.setString(3,NN);
            st.setString(4,CP);
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            if(e.getErrorCode()==1366)
            {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Introduzca un valor numerico");
                alert.show();
            }
        }
        return false;
    }
    public Boolean UPDATEPREDF(String ES,String CP) {
        try {
            String query = "UPDATE precio SET estatus=? WHERE CvePrecio=?";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1,ES);
            st.setString(2,CP);
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            if(e.getErrorCode()==1366)
            {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Introduzca un valor numerico");
                alert.show();
            }
        }
        return false;
    }
    public Boolean UPDATENNELIUSUARIOAA(String NNN,String NNE) {
        try {
            String query = "UPDATE autoactivo SET NickName=? WHERE NickName=?;";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1,NNN);
            st.setString(2,NNE);
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            if(e.getErrorCode()==1366)
            {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Introduzca un valor numerico");
                alert.show();
            }
        }
        return false;
    }
    public Boolean UPDATENNELIUSUARIOAC(String NNN,String NNE) {
        try {
            String query = "UPDATE autocobrado SET NickName=? WHERE NickName=?;";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1,NNN);
            st.setString(2,NNE);
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            if(e.getErrorCode()==1366)
            {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Introduzca un valor numerico");
                alert.show();
            }
        }
        return false;
    }

    public void UPDATETOTAL(double CA,String NN) {
        try {
            String query = "UPDATE usuario SET total=total+? WHERE NickName=?";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setDouble(1,CA);
            st.setString(2,NN);
            st.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void UPDATECORTE() {
        try {
            String query = "UPDATE usuario SET total=0";
            PreparedStatement st =  conn.prepareStatement(query);
            st.execute();
            UPDATECORTE1();
            UPDATECORTE2();
            UPDATECORTE3();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void UPDATECORTE1() {
        try {
            String query = "UPDATE pensionados SET estatus='C';";
            PreparedStatement st =  conn.prepareStatement(query);
            st.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void UPDATECORTE2() {
        try {
            String query = "DELETE FROM otrosgastos;";
            PreparedStatement st =  conn.prepareStatement(query);
            st.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void UPDATECORTE3() {
        try {
            String query = "DELETE FROM autocobrado;";
            PreparedStatement st =  conn.prepareStatement(query);
            st.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Boolean UPDATEPENMES(int NP) {
        try {
            String query = "UPDATE pensionados SET FecInicio=FecFin,FecFin=(SELECT FecInicio + INTERVAL 1 MONTH),estatus='D' WHERE NumPensionado=?";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setInt(1,NP);
            st.execute();
            return true;
        } catch (SQLException e) {
            alert=new Alert(Alert.AlertType.ERROR);
            if(e.getErrorCode()==1451)
            {
                alert.setContentText("No se puede modificar la clave");
                alert.show();
            }
            else
            if(e.getErrorCode()==1062)
            {
                alert.setContentText("La clave del auto ya existe");
                alert.show();
            }
            System.out.println(e);
        }
        return false;
    }
    /*
     NickName      | varchar(20)   | NO   | PRI | NULL    |       |
| NomCompleto   | varchar(300)  | YES  |     | NULL    |       |
| contrasena    | varchar(40)   | NO   |     | NULL    |       |
| total         | decimal(10,0) | YES  |     | NULL    |       |
| CveTipUsuario | varchar(1)    | NO   | MUL | NULL    |
    * */
    /*
    NumTarjeta | varchar(100) | NO   | PRI | NULL    |       |
| Placa      | varchar(15)  | YES  |     | NULL    |       |
| Modelo     | varchar(80)  | YES  |     | NULL    |       |
| FecEntrada | datetime     | YES  |     | NULL    |       |
| estatus    | varchar(50)  | YES  |     | NULL    |       |
| NickName
    * */

    /**
     * NickName   | varchar(20)   | NO   | MUL | NULL    |       |
     | NumTarjeta | varchar(100)  | NO   | MUL | NULL    |       |
     | Placa      | varchar(15)   | YES  |     | NULL    |       |
     | Modelo     | varchar(15)   | YES  |     | NULL    |       |
     | FecEntrada | datetime      | NO   |     | NULL    |       |
     | FecSalida  | datetime      | NO   |     | NULL    |       |
     | CosCobrado | decimal(10,0) | NO
     */





}
