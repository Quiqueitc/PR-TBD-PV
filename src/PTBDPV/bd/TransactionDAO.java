package PTBDPV.bd;;

import PTBDPV.datos.*;
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

    public  int usuarioNull () throws SQLException {
        try(CallableStatement cstmt = conn.prepareCall("{call usuNull(?)}");) {
           // cstmt.setInt(1, 5);
            cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
            cstmt.execute();
            System.out.println("En call es "+cstmt.getInt(1) );
            return cstmt.getInt(1);

            //System.out.println("MANAGER ID: " + cstmt.getInt(2));
        }
        catch (Exception e)
        {
            System.out.println("Cai");
        }
        return 0;
    }
    public   int sucursalNull () throws SQLException {
        try(CallableStatement cstmt = conn.prepareCall("{call bdpv.sucuNull(?)}");) {
           // cstmt.setInt(1, 5);
            cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
            cstmt.execute();
            System.out.println("En call es S"+cstmt.getInt(1) );
            return cstmt.getInt(1);
            //System.out.println("MANAGER ID: " + cstmt.getInt(2));
        }
    }
    public   Boolean aduser (datEmpleados datEmpleados) throws SQLException {
        try {
            conn.setAutoCommit(false);
          //  exec sp_addlogin @id,@con,'bdPV',null,null,null
            CallableStatement cstmt = conn.prepareCall("{ call sys.sp_addlogin (?,?,?,?,?,?) }");
            cstmt.setString(1, datEmpleados.getIdUsu());
            cstmt.setString(2, datEmpleados.getContrasena());
            cstmt.setString(3, "bdPV");
            cstmt.setString(4, null);
            cstmt.setString(5, null);
            cstmt.setString(6, null);
            //cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
            cstmt.execute();
            //conn.commit();
            return true;
            //System.out.println("MANAGER ID: " + cstmt.getInt(2));
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            System.out.println("Se deshicieron cambios");
            //conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            //conn.close();
            System.out.println("Se cerró conexión");
        }
        return false;
    }

    public Boolean insUsuari (datEmpleados datEmpleados) throws SQLException {
        try {
            conn.setAutoCommit(false);
            CallableStatement cstmt = conn.prepareCall("{ call dbo.insUsuario (?,?,?,?,?,?,?) }");
            cstmt.setString(1, datEmpleados.getIdUsu());
            // System.out.println("1");
            cstmt.setString(2, datEmpleados.getNombre());
            cstmt.setString(3, datEmpleados.getDomicilio());
            cstmt.setString(4, datEmpleados.getTelefono());
            cstmt.setString(5, datEmpleados.getContrasena());
            cstmt.setDate(6, datEmpleados.getFecContrato());
            cstmt.setString(7, datEmpleados.getIdTip());
            //cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
            // System.out.println("2"+datEmpleados.getIdUsu() + " "+ datEmpleados.getContrasena());
            cstmt.execute();
            conn.commit();
            return true;
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
           // conn.close();
        }
        return false;
    }
  /*  public   Boolean insFacP (datProductos datProductos) throws SQLException {
        try(CallableStatement cstmt = conn.prepareCall("{call dbo.ACT_PRO(?,?,?,?)}")) {
            cstmt.setString(1, datProductos.getCodigo());
            cstmt.setDouble(2, datProductos.getNoExistencia());
            cstmt.setInt(3, datProductos.getIdProv());
            cstmt.setString(4, datProductos.getDescripcion());
            cstmt.execute();
            return true;
        }
    }*/
  public  Boolean insFaPr (String NU) throws SQLException {
      try(CallableStatement cstmt = conn.prepareCall("{call INS_FAC(?)}");) {
          cstmt.setString(1, NU);
          //cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
          cstmt.execute();
          System.out.println("Estoy en call  ");
          return true;

          //System.out.println("MANAGER ID: " + cstmt.getInt(2));
      }
      catch (Exception e)
      {
          System.out.println("Cai");
      }
      return false;
  }

    public Boolean insFaP (datProductos datProductos) throws SQLException {
        try {
                conn.setAutoCommit(false);
                CallableStatement cstmt = conn.prepareCall("{call ACT_PRO(?,?,?,?)}");
                cstmt.setString(1, datProductos.getCodigo());
                cstmt.setDouble(2,datProductos.getNoExistencia());
                cstmt.setDouble(3,datProductos.getIdProv());
                cstmt.setString(4,datProductos.getDescripcion());
                cstmt.execute();
                conn.commit();
            return true;
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            System.out.println("Deshacer");
//            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
           // conn.close();
        }
        return false;
    }
    //@id varchar(15), @no varchar(60), @dom varchar(100), @ varchar (15), @ varchar(40), @ date, @ varchar(1)
    public   Boolean insUsuario (datEmpleados datEmpleados) throws SQLException {
        try(CallableStatement cstmt = conn.prepareCall("{call dbo.insUsuario(?,?,?,?,?,?,?)}")) {
            cstmt.setString("id", datEmpleados.getIdUsu());
            System.out.println("1");
            cstmt.setString("no", datEmpleados.getNombre());
            cstmt.setString("dom", datEmpleados.getDomicilio());
            cstmt.setString("tel", datEmpleados.getTelefono());
            cstmt.setString("con", datEmpleados.getContrasena());
            cstmt.setDate("fec", datEmpleados.getFecContrato());
            cstmt.setString("idT", datEmpleados.getIdTip());
            //cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
            System.out.println("2"+datEmpleados.getIdUsu() + " "+ datEmpleados.getContrasena());
            cstmt.execute();
            return true;
            //System.out.println("MANAGER ID: " + cstmt.getInt(2));
        }
    }
    public ObservableList<String> LlenarClientes(String B) {
        ObservableList<String> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT nomCompleto FROM clientes WHERE nomCompleto LIKE '"+B+"%'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            String p = null;
            while(rs.next()) {
                p=       rs.getString("nomCompleto");
                transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de clientes...");
        }
        return transactions;
    }
    public ObservableList<String> LlenarEncargados(char ti) {
        ObservableList<String> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT nombre FROM empleados where idTip='"+ti+"';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            String p = null;
            while(rs.next()) {
                p=                        rs.getString("nombre");
                transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de empleados...");
        }
        return transactions;
    }

    public datEmpleados datEmpleado(String IU) {
        // ObservableList<DatosAutoActivo> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT * FROM empleados WHERE idUsu='"+IU+"';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datEmpleados p = null;
            while(rs.next()) {
                p = new datEmpleados(rs.getString("idUsu"),
                        rs.getString("nombre"),
                        rs.getString("domicilio"),
                        rs.getString("telefono"),
                        rs.getString("contrasena"),
                        rs.getString("idTip"),
                        rs.getDate("fecContrato"));
                //  transactions.add(p);
            }
            rs.close();
            st.close();
            return p;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de empleados...");
        }
        return null;
    }
    public ObservableList<datProductos> LlenarTblVen(int NF) {
        ObservableList<datProductos> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT P.codigo,P.descripcion,D.precio,D.cantidad, (D.precio*D.cantidad) as Importe, P.noExistencia FROM productos P INNER JOIN detalle D ON P.codigo=D.codigo INNER JOIN factura F ON D.noFactura=F.noFactura WHERE F.noFactura='"+NF+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datProductos p = null;
            while(rs.next()) {
                p = new datProductos(rs.getString("P.codigo"),
                        rs.getString("P.descripcion"),
                        rs.getDouble("D.precio"),
                        rs.getDouble("D.cantidad"),
                        rs.getDouble("Importe"),
                        rs.getDouble("P.noExistencia"));
                  transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de empleados...");
        }
        return transactions;
    }

    public Boolean creUsuario(String id,String pas) {

       try {
           String query = "GRANT ALL PRIVILEGES ON bdpv.* TO '"+id+"'@'%' IDENTIFIED BY '"+pas+"' WITH GRANT OPTION;";
           PreparedStatement st = conn.prepareStatement(query);
         //  crePri(id,pas);
           st.execute();
           return true;
       } catch (SQLException e) {
           System.out.println("Error al crear usuario"+e.getMessage());
           alert=new Alert(Alert.AlertType.ERROR);
           if(e.getErrorCode()==1451)
           {
               alert.setContentText("Este pensionado no se puede eliminar porque está siendo usado en otras tablas");
               alert.show();
           }
       }
       return false;
   }
    public Boolean crePri(String id,String pas) {

       try {
           String query = "GRANT USAGE ON bdpv.* TO '"+id+"'@'%' IDENTIFIED BY '"+pas+"';";
           PreparedStatement st = conn.prepareStatement(query);
           creUsuario(id,pas);
           st.execute();
           return true;
       } catch (SQLException e) {
           System.out.println("Error al dar privi usuario   "+e.getMessage()+ "  "+e.getErrorCode());
           alert=new Alert(Alert.AlertType.ERROR);
           if(e.getErrorCode()==1451)
           {
               alert.setContentText("Este pensionado no se puede eliminar porque está siendo usado en otras tablas");
               alert.show();
           }
       }
       return false;
   }
    public Boolean insSucursal(datSucursal datSucursal) {
        try {
            String query = "INSERT INTO sucursal VALUES (?,?,?,?,?,(SELECT idUsu FROM empleados WHERE nombre=?));";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1,datSucursal.getRfc());
            System.out.println("Aqui 2");
            st.setString(2,datSucursal.getNombre());
            st.setString(3,datSucursal.getDomicilio());
            st.setString(4,datSucursal.getTelefono());
            st.setString(5,datSucursal.getCiudad());
            st.setString(6,datSucursal.getIdUsu());
            System.out.println("Aqui 3");
            st.execute();
            return true;
        } catch (SQLException e) {
            Alert alert;
            if(e.getErrorCode()==1062)
            {
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("La llave principal ya existe");
                alert.show();
            }
            if(e.getErrorCode()==1406){
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Algun(os) campos exceden el tamaño permitido de caracteres");
                alert.show();
            }
            System.out.println("error  "+ e);
        }
        return false;
    }
    public int NoFactura() //REgresa el numero de factura
    {
        int p=0;
        ResultSet rs = null;
        try {

            String query = "SELECT IFNULL((MAX(noFactura)+1),1) AS NF from factura;";
            Statement st = conn.createStatement();
             rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getInt("NF");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }

    public Boolean insEmpleado(datEmpleados datEmpleados) {
        try {
            String query = "INSERT INTO empleados VALUES(?,?,?,?,?,?,?);";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1, datEmpleados.getIdUsu());
            st.setString(2,datEmpleados.getNombre());
            st.setString(3,datEmpleados.getDomicilio());
            st.setString(4,datEmpleados.getTelefono());
            st.setString(5,datEmpleados.getContrasena());
            st.setDate(6,datEmpleados.getFecContrato());
            st.setString(7,datEmpleados.getIdTip());
            crePri(datEmpleados.getIdUsu(),datEmpleados.getContrasena());
//            creUsuario(datEmpleados.getIdUsu(),datEmpleados.getContrasena());
            st.execute();
            return true;
        } catch (SQLException e) {
            alert=new Alert(Alert.AlertType.ERROR);
            if(e.getErrorCode()==1062)
            {
                alert.setContentText("La clave de autor ya existe");
                alert.show();
            }
            if(e.getErrorCode()==1406)
            {
                alert.setContentText("La clave no debe ser mayor de 2 caracteres");
                alert.show();
            }
            System.out.println("Error "+e);
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
