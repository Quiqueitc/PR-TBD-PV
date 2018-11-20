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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/inicio_Layout.fxml"));
        //primaryStage.setTitle("Hello World");
        primaryStage.setMaximized(true);
        primaryStage.initStyle(TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
       // TransactionDAO transactionDAO = new TransactionDAO(SQL.getConnection());

    }
    public static void main(String[] args) {
        launch(args);
    }
}
