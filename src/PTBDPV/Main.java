package PTBDPV;

import PTBDPV.bd.TransactionDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import PTBDPV.bd.SQL;

import static javafx.stage.StageStyle.TRANSPARENT;

public class Main extends Application {
    TransactionDAO transactionDAO=new TransactionDAO(SQL.getConnection());
    @Override
    public void start(Stage primaryStage) throws Exception {
        if(transactionDAO.usuarioNull()==0) //Instala por primera vez la app, debe configurar usuario
        {

            if(transactionDAO.sucursalNull()==0) // Debe configurar caracteristicas de la sucursal
                {
                    Parent root = FXMLLoader.load(getClass().getResource("fxml/conf_Usuario.fxml"));
                    //primaryStage.setTitle("Hello World");
                    primaryStage.setMaximized(false);
                    primaryStage.initStyle(TRANSPARENT);
                    primaryStage.setResizable(false);
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                }
        }
        else //La app ya fue instalada
            {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/login_Register_Layout.fxml"));
            //primaryStage.setTitle("Hello World");
            primaryStage.setMaximized(false);
            primaryStage.initStyle(TRANSPARENT);
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
