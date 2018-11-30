package PTBDPV.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Enrique Ramírez Palacios
 */
public class MySQL {
    public static Connection conn = null;
    private static String hostname   = "localhost";
    //private static String dbname = "bdtopicos";
    private static String dbname = "bdPV";
    private static String dbuser = "adms";
    private static String dbpass = "ADMIN";
    //private static String dbuser = "topicosprogram";
    //private static String dbpass = "TopicosProgram";

    public MySQL( String userN,String pass){
        dbuser=userN;
        dbpass=pass;
    }
    public static void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            jdbc:mysql://ipaddress:3306/test?noAccessToProcedureBodies=true
                conn = DriverManager.getConnection("jdbc:mysql://"+ hostname +":3306/" + dbname+"?noAccessToProcedureBodies=true", dbuser, dbpass);
                System.out.println("Se ha iniciado la conexión con el servidor de forma exitosa");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            }        
    }
    
    public static Connection getConnection()
    {
        if(conn == null) Connect();
        return conn;
    }
    
    public static void Disconnect() {
        try {
            conn.close();
            System.out.println("Se ha finalizado la conexión con el servidor");
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
