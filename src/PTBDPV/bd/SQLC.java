package PTBDPV.bd;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Enrique Ramírez Palacios
 */
public class SQLC {
    private static Connection conn = null;
    private static String hostname   = "localhost";
    private static String dbname = "bdestacionamiento1";
    private static String dbuser = "estacionamiento1";
    private static String dbpass = "estaProgra";

     private static java.sql.Connection con = null;
   // jdbc:sqlserver://;database
     private static String url = "jdbc:sqlserver://";
     private static String serverName = "localhost";
     private static String portNumber = "1433";
     private static String databaseName = "alumnosREPASO";
     private static String userName = "benigno";
     private static String password = "ben1"; // Indica al controlador que debe utilizar un cursor de servidor,
     // lo que permite más de una instrucción activa // en una conexión. private final String selectMethod = "cursor";

     // Constructor public Connect(){}

    public static String getConnectionUrl() {
         return url + serverName + ":" + portNumber + ";databaseName=" + databaseName + ";";
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

     /* Mostrar las propiedades del controlador y los detalles de la base de datos */

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
        /*public static void Conectar(){
        try {
            String connectionUrl = "jdbc:microsoft:sqlserver://localhost:1433";userName;password;"jdbc:sqlserver://;database=DB_Name;integratedSecurity=true;";
            conect = DriverManager.getConnection(connectionUrl);
            System.out.println("Conectado.");
        }
        catch (SQLException ex)
        {
            System.out.println("Error.");
        }
        con = DriverManager.getConnection();
    }*/

    /*public static void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://"+ hostname +":3306/" + dbname, dbuser, dbpass);
                System.out.println("Se ha iniciado la conexión con el servidor de forma exitosa");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            }        
    }*/

     /* public static Connection getConnection()
      {
          if(conn == null) Connect();
          return conn;
      }*/
   /*private void closeConnection(){
       try{
           if(con!=null)
               con.close();
           con=null;
       }catch(Exception e){
           e.printStackTrace();
       }
   }*/
     public void closeConnection() {
         try {
             conn.close();
             System.out.println("Se ha finalizado la conexión con el servidor");
         } catch (SQLException ex) {
             Logger.getLogger(SQLC.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

    
}
