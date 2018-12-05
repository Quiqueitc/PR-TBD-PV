package PTBDPV.bd;;

import PTBDPV.datos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import jdk.nashorn.internal.runtime.ECMAErrors;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public   int VER_PRO (String codi) throws SQLException {
        try(CallableStatement cstmt = conn.prepareCall("{CALL VER_PRO (?,?)}");) {
            cstmt.setString(1, codi);
            cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(2);
        }
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
      Alert alert;
        try {
                conn.setAutoCommit(false);
                CallableStatement cstmt = conn.prepareCall("{call ACT_PRO(?,?,?,?,?)}");
                cstmt.setString(1, datProductos.getCodigo());
                cstmt.setDouble(2,datProductos.getNoExistencia());
                cstmt.setDouble(3,datProductos.getIdProv());
                cstmt.setString(4,datProductos.getDescripcion());
                cstmt.registerOutParameter(5, Types.VARCHAR);
                cstmt.execute();
                conn.commit();
                if(!cstmt.getNString(5).equalsIgnoreCase("e"))
                {
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(cstmt.getNString(5));
                    alert.show();
                    return false;
                }
                else
                    return true;
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            System.out.println("Deshacer");
//            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            conn.setAutoCommit(true);
        }
        return false;
    }
    public Boolean insPROMOCION (datPromociones datPromociones) throws SQLException {
      Alert alert;
        try {
                conn.setAutoCommit(false);
                CallableStatement cstmt = conn.prepareCall("{call ADD_PROMOCION(?,?,?,?,?,?)}");
                cstmt.setString(1, datPromociones.getCodigo());
                cstmt.setString(2,datPromociones.getNomPromocion());
                cstmt.setDouble(3,datPromociones.getCantMinima());
                cstmt.setDouble(4,datPromociones.getCantMaxima());
                cstmt.setDouble(5,datPromociones.getPreUnitario());
                cstmt.registerOutParameter(6, Types.VARCHAR);
                cstmt.execute();
                conn.commit();

                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(cstmt.getNString(6));
                    alert.show();

                    return true;
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            System.out.println("Deshacer");
//            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            conn.setAutoCommit(true);
        }
        return false;
    }
    public Boolean insPRO (datProductos datProductos) throws SQLException {
      Alert alert;
        try {
                conn.setAutoCommit(false);
                CallableStatement cstmt = conn.prepareCall("{call INS_PRO(?,?,?,?,?,?,?,?,?,?,?)}");
                cstmt.setString(1, datProductos.getCodigo());
                cstmt.setString(2,datProductos.getDescripcion());
                cstmt.setDouble(3,datProductos.getPreCosto());
                cstmt.setDouble(4,datProductos.getPreVenta());
                cstmt.setDouble(5,datProductos.getPreMayoreo());
                cstmt.setDouble(6,datProductos.getNoExistencia());
                cstmt.setDouble(7,datProductos.getExiMinima());
                cstmt.setString(8,datProductos.getIdUniMed());
                cstmt.setString(9,datProductos.getNomDepartamento());
                cstmt.setString(10,datProductos.getNomProv());
                cstmt.registerOutParameter(11, Types.VARCHAR);
                cstmt.execute();
                conn.commit();
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(cstmt.getNString(11));
                    alert.show();
                    return true;
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            System.out.println("Deshacer");
//            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            conn.setAutoCommit(true);
        }
        return false;
    }
    public Boolean updFAC (int NC,int NF) throws SQLException {
      Alert alert;
        try {
                conn.setAutoCommit(false);
                CallableStatement cstmt = conn.prepareCall("{call VER_CRE(?,?,?)}");
                cstmt.setInt(1, NC);
                cstmt.setInt(2,NF);
                cstmt.registerOutParameter(3, Types.VARCHAR);
                cstmt.execute();
                conn.commit();
                if(!cstmt.getNString(3).equalsIgnoreCase("e"))
                {
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(cstmt.getNString(3));
                    alert.show();
                    return false;
                }
                else
                    return true;
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            System.out.println("Deshacer");
//            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            conn.setAutoCommit(true);
        }
        return false;
    }
    public Boolean delPRO (String cod, double cant,int NF,int ND) throws SQLException {
      Alert alert;
        try {
                conn.setAutoCommit(false);
                CallableStatement cstmt = conn.prepareCall("{call DEL_DP(?,?,?,?,?)}");
                cstmt.setString(1, cod);
                cstmt.setDouble(2,cant);
                cstmt.setInt(3,NF);
                cstmt.setInt(4,ND);
                cstmt.registerOutParameter(5, Types.VARCHAR);
                cstmt.execute();
                conn.commit();
                if(!cstmt.getNString(5).equalsIgnoreCase("e"))
                {
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(cstmt.getNString(5));
                    alert.show();
                    return false;
                }
                else
                    return true;
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            System.out.println("Deshacer");
//            conn.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            conn.setAutoCommit(true);
        }
        return false;
    }
    public Boolean actPPRO (String codi,int NF,int ND,String TP) throws SQLException {
      Alert alert;
        try {
                conn.setAutoCommit(false);
                CallableStatement cstmt = conn.prepareCall("{call ACT_PPRO(?,?,?,?,?)}");
                cstmt.setString(1, codi);//CODIGO
                cstmt.setInt(2,NF); //NUMERO FACTURA
                cstmt.setInt(3,ND); //NUMERO DETALLE
                cstmt.setString(4,TP); //TIPO DE PRECIO
                cstmt.registerOutParameter(5, Types.VARCHAR);
                cstmt.execute();
                conn.commit();
                if(!cstmt.getNString(5).equalsIgnoreCase("e"))
                {
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(cstmt.getNString(5));
                    alert.show();
                    return false;
                }
                else
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
    public Boolean insARTcomun (double cant,double precio,int NF) throws SQLException {
      Alert alert;
        try {
                conn.setAutoCommit(false);
                CallableStatement cstmt = conn.prepareCall("{call ACT_PROC(?,?,?,?)}");
                cstmt.setDouble(1, cant);
                cstmt.setDouble(2,precio);
                cstmt.setInt(3,NF);
                cstmt.registerOutParameter(4, Types.VARCHAR);
                cstmt.execute();
                conn.commit();
                if(!cstmt.getNString(4).equalsIgnoreCase("e"))
                {
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(cstmt.getNString(4));
                    alert.show();
                }
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
    public ObservableList<String> LlenarProductos(String B) {
        ObservableList<String> transactions = FXCollections.observableArrayList();
        try {

            String query = "select codigo from productos WHERE codigo LIKE '"+B+"%' and descripcion !='Producto común';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            String p = null;
            while(rs.next()) {
                p=       rs.getString("codigo");
                transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de productos...");
        }
        return transactions;
    }
    public ObservableList<String> LlenarDepartamentos() {
        ObservableList<String> transactions = FXCollections.observableArrayList();
        try {

            String query = "select descripcion from departamento;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            String p = null;
            while(rs.next()) {
                p=       rs.getString("descripcion");
                transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de departamento...");
        }
        return transactions;
    }
    public ObservableList<String> LlenarProveedores() {
        ObservableList<String> transactions = FXCollections.observableArrayList();
        try {

            String query = "select nombre from proveedores;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            String p = null;
            while(rs.next()) {
                p=       rs.getString("nombre");
                transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de proveedores...");
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
    public ObservableList<String> LlenarEMP() {
        ObservableList<String> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT idUsu FROM empleados;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            String p = null;
            while(rs.next()) {
                p=rs.getString("idUsu");
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

    public datProductos dtProductos(String CO) {
        // ObservableList<DatosAutoActivo> transactions = FXCollections.observableArrayList();
        try {

            String query = "select P.codigo,P.descripcion,P.preCosto,P.preVenta,P.preMayoreo,P.noExistencia,P.exiMinima,P.ganancia, P.idUniMed, D.descripcion,Pr.nombre from productos P INNER JOIN departamento D ON P.idDepartamento=D.idDepartamento INNER JOIN proveedores Pr ON P.idProv=Pr.idProv WHERE codigo='"+CO+"';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datProductos p = null;
            while(rs.next()) {
                p = new datProductos(rs.getString("P.codigo"),
                        rs.getString("P.descripcion"),
                        rs.getDouble("P.preCosto"),
                        rs.getDouble("P.preVenta"),
                        rs.getDouble("P.preMayoreo"),
                        rs.getDouble("P.noExistencia"),
                        rs.getDouble("P.exiMinima"),
                        rs.getDouble("P.ganancia"),
                        rs.getString("P.idUniMed"),
                        rs.getString("D.descripcion"),
                        rs.getString("Pr.nombre"));
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

            String query = "SELECT P.codigo,P.descripcion,D.precio,D.cantidad, (D.precio*D.cantidad) as Importe, P.noExistencia,D.numDetalle FROM productos P INNER JOIN detalle D ON P.codigo=D.codigo INNER JOIN factura F ON D.noFactura=F.noFactura WHERE F.noFactura='"+NF+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datProductos p = null;
            while(rs.next()) {
                p = new datProductos(rs.getString("P.codigo"),
                        rs.getString("P.descripcion"),
                        rs.getDouble("D.precio"),
                        rs.getDouble("D.cantidad"),
                        rs.getDouble("Importe"),
                        rs.getDouble("P.noExistencia"),
                        rs.getInt("D.numDetalle"));
                  transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de detalles...");
        }
        return transactions;
    }
    public ObservableList<datDepartamento> LlenarTblDepto() {
        ObservableList<datDepartamento> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT * FRom departamento";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datDepartamento p = null;
            while(rs.next()) {
                p = new datDepartamento(rs.getInt("idDepartamento"),
                        rs.getString("descripcion"));
                  transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de departamento...");
        }
        return transactions;
    }
    public ObservableList<datProveedores> LlenarTblProvee() {
        ObservableList<datProveedores> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT idProv,nombre FRom proveedores;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datProveedores p = null;
            while(rs.next()) {
                p = new datProveedores(rs.getInt("idProv"),
                        rs.getString("nombre"));
                  transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de proveedores...");
        }
        return transactions;
    }
    public ObservableList<datPromociones> LlenarTblPromo() {
        ObservableList<datPromociones> transactions = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FRom promociones;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datPromociones p = null;
            while(rs.next()) {
                p = new datPromociones(rs.getString("codigo"),
                        rs.getString("nomPromocion"),
                        rs.getDouble("cantMinima"),
                        rs.getDouble("cantMaxima"),
                        rs.getDouble("preUnitario"));
                  transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de proveedores...");
        }
        return transactions;
    }
    public ObservableList<datFactura> LlenarTblFAC(String B) {
        ObservableList<datFactura> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT F.NoFactura, F.fecha,F.total,F.idUsu,C.nomCompleto,M.descripcion,CASE estatus\n" +
                    "\t\t\t\t\t\t\t\tWHEN 'P' THEN 'Pagado'\n" +
                    "\t\t\t\t\t\t\t\tWHEN 'V' THEN 'Vendido'\n" +
                    "\t\t\t\t\t\t\t\tWHEN 'F' THEN 'Fiado'\n" +
                    "                                END AS estatus\n" +
                    "FROM factura F INNER JOIN clientes C ON F.idClie=C.idClie\n" +
                    "\t\t\t\tINNER JOIN MetodoPago M ON M.idMetPago=F.idMetPago\n" +
                    "                WHERE F.fecha LIKE '"+B+"%' or F.idUsu LIKE '"+B+"%' or F.idMetPAgo LIKE '"+B+"%' ORDER BY  F.NoFactura ASC;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datFactura p = null;
            while(rs.next()) {
                p = new datFactura(rs.getInt("NoFactura"),
                        rs.getDate("fecha"),
                        rs.getDouble("total"),
                        rs.getString("idUsu"),
                        rs.getString("nomCompleto"),
                        rs.getString("descripcion"),
                        rs.getString("estatus"));
                  transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de facturas...");
        }
        return transactions;
    }
    public ObservableList<datDetalles> LlenarTblDET(int NF) {
        ObservableList<datDetalles> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT  D.noFactura,D.numDetalle, D.cantidad, D.precio, CASE D.descuento WHEN 'V' THEN 'Precio Venta' WHEN 'C' THEN 'Precio Costo' WHEN 'M' THEN 'Precio Mayoreo' END AS descuento, P.descripcion FROM detalle D INNER JOIN productos P ON D.codigo=P.codigo WHERE noFactura='"+NF+"' ORDER BY numDetalle ASC;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datDetalles p = null;
            while(rs.next()) {
                p = new datDetalles(rs.getInt("noFactura"),
                        rs.getInt("numDetalle"),
                        rs.getDouble("cantidad"),
                        rs.getDouble("precio"),
                        rs.getString("descuento"),
                        rs.getString("descripcion"));
                  transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de facturas...");
        }
        return transactions;
    }
    public ObservableList<datClientes> LlenarTblCC() {
        ObservableList<datClientes> transactions = FXCollections.observableArrayList();
        try {

            String query = "select idClie, nomCompleto from clientes;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datClientes p = null;
            while(rs.next()) {
                p = new datClientes(rs.getInt("idClie"),
                        rs.getString("nomCompleto"));
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
    public ObservableList<datProductos> LlenarTblBus(String BU) {
        ObservableList<datProductos> transactions = FXCollections.observableArrayList();
        try {

            String query = "select codigo, descripcion,noExistencia from productos WHERE codigo LIKE '"+BU+"' OR descripcion LIKE '"+BU+"';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datProductos p = null;
            while(rs.next()) {
                p = new datProductos(rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("noExistencia"));
                  transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información de productos...");
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
    public String exiDepto(String ND) //REgresa Existencia de depto
    {
        String p=null;
        ResultSet rs = null;
        try {

            String query = "select descripcion from departamento where descripcion LIKE '"+ND+"';";
            Statement st = conn.createStatement();
             rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getString("descripcion");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    public String exiProvee(String NP) //REgresa Existencia de proveedor
    {
        String p=null;
        ResultSet rs = null;
        try {

            String query = "SELECT nombre FROM proveedores WHERE nombre LIKE '"+NP+"';";
            Statement st = conn.createStatement();
             rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getString("nombre");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    public String[]  DPproducto(String NP) //REgresa la descripcion de producto
    {
        String p[]=new String[2];
        ResultSet rs = null;
        try {
            String query = "select descripcion, preVenta from productos WHERE codigo='"+NP+"';";
            Statement st = conn.createStatement();
             rs = st.executeQuery(query);
            while (rs.next()) {
                p[0]=rs.getString("descripcion");
                p[1]=rs.getString("preVenta");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    public String[]  datSucu() //REgresa la descripcion de producto
    {
        String p[]=new String[2];
        ResultSet rs = null;
        try {
            String query = "select rfc,nombre from SUCURSAL;";
            Statement st = conn.createStatement();
             rs = st.executeQuery(query);
            while (rs.next()) {
                p[0]=rs.getString("rfc");
                p[1]=rs.getString("nombre");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    public double [] totFactura(int NF) {
        double p[]=new double[2];
        try {

            String query = "select SUM(cantidad*precio) as total, CEILING(SUM(cantidad)) as cantidad from detalle WHERE noFactura='"+NF+"';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p[0]=rs.getDouble("total");
                p[1]=rs.getDouble("cantidad");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    public String desPRO(int NF,int ND) {
        String p=null;
        try {

            String query = "SELECT descuento FROM detalle WHERE noFactura='"+NF+"' and numDetalle='"+ND+"';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getString("descuento");
                System.out.println("Es p"+p);
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
                alert.setContentText("El id ya existe");
                alert.show();
            }
            if(e.getErrorCode()==1406)
            {
                alert.setContentText("Algunos valores exceden la capacidad permitida");
                alert.show();
            }
            System.out.println("Error "+e);
        }
        return false;
    }
    public Boolean insDepartamento(String ND) {
        try {
            String query = "INSERT INTO departamento (descripcion)values(?);";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1, ND);
            st.execute();
            return true;
        } catch (SQLException e) {
            alert=new Alert(Alert.AlertType.ERROR);
            if(e.getErrorCode()==1062)
            {
                alert.setContentText("El id ya existe");
                alert.show();
            }
            if(e.getErrorCode()==1406)
            {
                alert.setContentText("Algunos valores exceden la capacidad permitida");
                alert.show();
            }
            System.out.println("Error "+e);
        }
        return false;
    }
    public Boolean insProveedores(String NP,String TP,String DP) {
        try {
            String query = "INSERT INTO proveedores (nombre,telefono,descripcion) VALUES (?,?,?);";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1, NP);
            st.setString(2, TP);
            st.setString(3, DP);
            st.execute();
            return true;
        } catch (SQLException e) {
            alert=new Alert(Alert.AlertType.ERROR);
            if(e.getErrorCode()==1062)
            {
                alert.setContentText("El id ya existe");
                alert.show();
            }
            if(e.getErrorCode()==1406)
            {
                alert.setContentText("Algunos valores exceden la capacidad permitida");
                alert.show();
            }
            System.out.println("Error "+e);
        }
        return false;
    }
    public Boolean insMOV(datMovimientos datMovimientos) throws SQLException {
        try {
            conn.setAutoCommit(false);
            String query = "INSERT INTO movimiento (descripción,cantidad,fecha,idUsu,rfc,idTipo) Values(?,?,now(),?,?,?);;";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1, datMovimientos.getDescripción());
            st.setDouble(2,datMovimientos.getCantidad());
            st.setString(3,datMovimientos.getIdUsu());
            st.setString(4,datMovimientos.getRfc());
            st.setString(5,datMovimientos.getIdTipo());
            st.execute();
            return true;
        } catch (SQLException e) {
            alert=new Alert(Alert.AlertType.ERROR);
            if(e.getErrorCode()==1062)
            {
                alert.setContentText("La clave ya existe");
                alert.show();
            }
            if(e.getErrorCode()==1406)
            {
                alert.setContentText("Se exceden los caracteres permitidos");
                alert.show();
            }
            System.out.println("Error "+e);
        }
        finally {
            conn.setAutoCommit(true);
        }
        return false;
    }

    public Boolean eliPRO(String CO) {

        try {
            String query = "DELETE FROM productos WHERE codigo='"+CO+"';";
            PreparedStatement st = conn.prepareStatement(query);
            st.execute();
            return true;
        } catch (SQLException e) {
            alert=new Alert(Alert.AlertType.ERROR);
            if(e.getErrorCode()==1451)
            {
                alert.setTitle("Error de eliminación");
                alert.setContentText("No se puede eliminar porque el producto tiene detalles existentes...");
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

    /******************************Agregado THALIA******************************/
    public Boolean insClie (datClientes datClientes) throws SQLException {
        try {
            conn.setAutoCommit(false);
            CallableStatement cstmt = conn.prepareCall("{ call veriCliente (?,?,?,?,?,?) }");
            cstmt.setInt(1,datClientes.getIdClie());
            cstmt.setString(2, datClientes.getNomCompleto());
            cstmt.setString(3, datClientes.getDomicilio());
            cstmt.setString(4, datClientes.getTelefono());
            cstmt.setString(5, datClientes.getEmail());
            cstmt.setDouble(6, datClientes.getLimCredito());

            //cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
            // System.out.println("2"+datEmpleados.getIdUsu() + " "+ datEmpleados.getContrasena());
            cstmt.execute();
            conn.commit();
            return true;
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            System.out.println(e);//conn.rollback();

            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            // conn.close();
        }
        return false;
    }




    public ObservableList<datFactura> infoFactura(int code) {
        ObservableList<datFactura> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT noFactura,fecha,total FROM factura where idClie="+code+";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datFactura p = null;
            while(rs.next()) {
                p = new datFactura(
                        rs.getInt("noFactura"),
                        rs.getDate("fecha"),
                        rs.getDouble("total"));

                transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar informaciÃ³n de clientes...");
        }
        return transactions;
    }

    public ObservableList<datClientes> listClient(String B) {
        ObservableList<datClientes> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT idClie,nomCompleto FROM clientes WHERE nomCompleto LIKE '"+B+"%' OR idClie LIKE '"+B+"';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datClientes p = null;
            while(rs.next()) {
                p = new datClientes(
                        rs.getInt("idClie"),
                        rs.getString("nomCompleto"));
                transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar informaciÃ³n de clientes...");
        }
        return transactions;
    }

    public Boolean deleteCL(int id) {
        Alert messaje=new Alert(Alert.AlertType.CONFIRMATION);
        messaje.setContentText("Desea eliminar este usuario");
        Optional<ButtonType> Acepta=messaje.showAndWait();

        if(Acepta.get()==ButtonType.OK)
        {
            try {
                String query = "delete from clientes where idClie = ?";
                PreparedStatement st = conn.prepareStatement(query);
                st.setInt(1, id);
                st.execute();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getErrorCode());
                if (e.getErrorCode()==1205)
                {
                    showMessage("Este usuario tiene facturas");
                }


            }
        }


        return false;
    }

    public datClientes obtenerCliente(int idCl)
    {
        List<datClientes> movimiento = new ArrayList<datClientes>();
        datClientes objeto = null;
        try {

            String nomCompleto,domicilio,telefono,email;
            double LimCredito;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM clientes where idClie="+idCl +";");
            while (rs.next())
            {
                objeto=new datClientes();
                nomCompleto=rs.getString("nomCompleto");
                domicilio=rs.getString("domicilio");
                telefono=rs.getString("telefono");
                email=rs.getString("email");
                LimCredito= rs.getDouble("limCredito");


                objeto.setNomCompleto(nomCompleto);
                objeto.setDomicilio(domicilio);
                objeto.setEmail(email);
                objeto.setTelefono(telefono);
                objeto.setLimCredito(LimCredito);

                movimiento.add(objeto);

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objeto;
    }


    public ObservableList<datDetFact> detallFactira(int code) {
        ObservableList<datDetFact> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT descripcion, D.precio,D.cantidad, (D.precio*D.cantidad) AS total\n" +
                    "FROM detalle D INNER JOIN factura F on D.noFactura=F.noFactura " +
                    "INNER JOIN productos P ON D.codigo=P.codigo where F.noFactura="+code+";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datDetFact p = null;
            while(rs.next()) {
                p = new datDetFact(
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getDouble("cantidad"),
                        rs.getDouble("total"));

                transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar informaciÃ³n de clientes...");
        }
        return transactions;
    }

    public ObservableList<datPagos> pagos(int code) {
        ObservableList<datPagos> transactions = FXCollections.observableArrayList();
        try {

            String query = "SELECT numPago,fecha,abono\n" +
                    "FROM pagos WHERE noFactura="+code+";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datPagos p = null;
            while(rs.next()) {
                p = new datPagos(
                        rs.getInt("numPago"),
                        rs.getDate("fecha"),
                        rs.getDouble("abono"));

                transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar informaciÃ³n de clientes...");
        }
        return transactions;
    }
    /***********Registrar PAGO*************/
//INSERT INTO pagos VALUES  (3,2,(select MAX(numPago) + 1 as Num from pagos WHERE noFactura=2),getDate(),200);

    public int noPago(int noFact) {
        int p=0;
        try {

            String query = "SELECT IFNULL((MAX(numPago)+1),1) AS noPago from pagos WHERE noFactura="+noFact+";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getInt("noPago");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }

    public Boolean insAbono(datPagos datPagos) {
        try {
            //INSERT INTO factura (fecha,total,idClie,idUsu,idMetPago,estatus) VALUES (curdate(),1600,1,'C1','C','V') 1 row(s) affected       0.031 sec
            String query = "INSERT INTO pagos (noFactura,idClie,numPago,fecha,abono) VALUES  (?,?,?,curdate(),?);";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setInt(1,datPagos.getNoFactura());
            System.out.println("Aqui 2");
            st.setInt(2,datPagos.getIdClie());
            st.setInt(3,datPagos.getNumPago());
            //st.setDate(4,datPagos.getFecha());
            st.setDouble(4,datPagos.getAbono());
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
                alert.setContentText("Algun(os) campos exceden el tamaÃ±o permitido de caracteres");
                alert.show();
            }
            System.out.println("error  "+ e);
        }
        return false;
    }

    public double saldo(int idClie) {
        double p=0;
        try {

            String query = "SELECT SUM(F.total) AS TOTAL FROM factura F inner join clientes C on C.idClie=F.idClie \n" +
                    "where idMetPago=(Select idMetPago from metodoPago where descripcion='CrÃ©dito') AND C.idClie="+idClie+";";
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
    //Total de pagos realizados en el dia
    public double totPagos() {
        double p=0;
        try {

            String query = "SELECT SUM(abono) AS TOTAL from pagos WHERE fecha = curdate();";
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

    public double totEfectivo() {
        double p=0;
        try {

            String query = "SELECT SUM(total) AS TOTAL from factura WHERE fecha = curdate() AND idMetPago=(Select idMetPago from metodoPago Where descripcion='Efectivo');";
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

    public double totVales() {
        double p=0;
        try {

            String query = "SELECT SUM(total) AS TOTAL from factura WHERE fecha = curdate() AND idMetPago=(Select idMetPago from metodoPago Where descripcion='Vales Despensa');";
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

    public double totTarjetas() {
        double p=0;
        try {

            String query = "SELECT SUM(total) AS TOTAL from factura WHERE fecha = curdate() AND idMetPago=(Select idMetPago from metodoPago Where descripcion='Tarjeta Credito/Debito');";
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

    public double totEntradas() {
        double p=0;
        try {

            String query = "SELECT SUM(cantidad) AS TOTAL from movimiento WHERE fecha = curdate() " +
                    "AND idTipo=(Select idTipMov from tipMov Where descripción='Entrada');;";
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

    public double totPagoProve() {
        double p=0;
        try {

            String query = "SELECT SUM(cantidad) AS TOTAL from movimiento WHERE fecha = curdate() " +
                    "AND idTipo=(Select idTipMov from tipMov Where descripción='Salida');;";
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

    public double ganancia() {
        double p=0;
        try {

            String query = "select sum((d.cantidad*(d.precio-p.preCosto))-d.descuento) AS GANANCIA from detalle d " +
                    "inner join factura f on d.noFactura=f.noFactura \n" +
                    "inner join productos p on p.codigo=d.codigo where fecha=curdate();";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getDouble("GANANCIA");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }

    public ObservableList<datVentaXDepartamento> ventasXDep() {
        ObservableList<datVentaXDepartamento> transactions = FXCollections.observableArrayList();
        try {

            String query = "select dt.descripcion AS descripcion,SUM(d.cantidad*d.precio-d.descuento) AS ventas from detalle d inner join factura f on d.noFactura=f.noFactura \n" +
                    "inner join productos p on p.codigo=d.codigo inner join departamento dt on dt.idDepartamento=p.idDepartamento where fecha=curdate() group by descripcion;;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            datVentaXDepartamento p = null;
            while(rs.next()) {
                p = new datVentaXDepartamento(
                        rs.getString("descripcion"),
                        rs.getDouble("ventas"));

                transactions.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar informaciÃ³n de clientes...");
        }
        return transactions;
    }
    public double ECambio() {
        double p=0;
        try {

            String query = "select SUM(cantidad) As cambio from movimiento where idTipo='C' AND fecha =curdate();";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getDouble("cambio");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }

    public double DinInCaj() {
        double p=0;
        try {

            String query = "select cantidad As Inicial from movimiento where idTipo='I' AND fecha =curdate();";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p=rs.getDouble("Inicial");
            }
            rs.close();
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }



    public ObservableList<datUnidadMedida> UnidadesMedida (){

        ObservableList<datUnidadMedida> transactions = FXCollections.observableArrayList();
        datUnidadMedida p=null;
        try {
            String query = "select * from unidadmedida;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                //Mientras haya resultados obtengo el valor.
                p=new datUnidadMedida(rs.getString("idUniMed").charAt(0),
                        rs.getString("descripcion"));
                transactions.add(p);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar informaciÃ³n...");
        }
        return transactions;
    }

    public void UPDATEUM(char id,String desc) {
        try {
            String query = "UPDATE unidadmedida set descripcion='"+desc+"' where idUniMed='"+id+"'";
            PreparedStatement st =  conn.prepareStatement(query);
            st.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void showMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();

    }



}
