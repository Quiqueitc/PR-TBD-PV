package PTBDPV.bd;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Enrique Ramírez Palacios
 */
public class SQL {
     private static java.sql.Connection con = null;
     private static String url = "jdbc:sqlserver://";
     private static String serverName = "localhost";
     private static String portNumber = "1433";
     private static String databaseName = "bdPV";
     private static String userName = "adms";
     private static String password = "ADMIN";
     // Indica al controlador que debe utilizar un cursor de servidor,
     // lo que permite más de una instrucción activa // en una conexión. private final String selectMethod = "cursor";

     // Constructor public Connect(){}

    public static String getConnectionUrl() {
         return url + serverName + ":" + portNumber + ";databaseName=" + databaseName + ";";
     }
     public static void authentication( String userN,String pass){
        userName=userN;
        password=pass;
     }
     public static Connection getConnection() {
         try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             con = java.sql.DriverManager.getConnection(getConnectionUrl(), userName, password);
             if (con != null) System.out.println("Conexión correcta.");
         } catch (Exception e) {
             e.printStackTrace();
             System.out.println("Error de seguimiento en getConnection() : " + e.getMessage());
         }
         return con;
     }

     public void displayDbProperties() {
         java.sql.DatabaseMetaData dm = null;
         java.sql.ResultSet rs = null;
         try {
             con = this.getConnection();
             if (con != null) {
                 dm = con.getMetaData();
                 System.out.println("Información del controlador");
                 System.out.println("\tNombre del controlador: " + dm.getDriverName());
                 System.out.println("\tVersión del controlador: " + dm.getDriverVersion());
                 System.out.println("\nInformación de la base de datos ");
                 System.out.println("\tNombre de la base de datos: " + dm.getDatabaseProductName());
                 System.out.println("\tVersión de la base de datos: " + dm.getDatabaseProductVersion());
                 System.out.println("Catálogos disponibles ");
                 rs = dm.getCatalogs();
                 while (rs.next()) {
                     System.out.println("\tcatálogo: " + rs.getString(1));
                 }
                 rs.close();
                 rs = null;
                 closeConnection();
             } else System.out.println("Error: No hay ninguna conexión activa");
         } catch (Exception e) {
             e.printStackTrace();
         }
         dm = null;
     }
     public void closeConnection() {
         try {
             con.close();
             System.out.println("Se ha finalizado la conexión con el servidor");
         } catch (SQLException ex) {
             Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

    
}
