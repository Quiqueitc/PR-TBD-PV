package PTBDPV;

import PTBDPV.bd.TransactionDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import PTBDPV.bd.SQLC;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("inicio_Layout.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        TransactionDAO transactionDAO = new TransactionDAO(SQLC.getConnection());
    }
    public static void main(String[] args) {
        launch(args);
    }
}
