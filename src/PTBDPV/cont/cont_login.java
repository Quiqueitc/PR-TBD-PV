package PTBDPV.cont;

import PTBDPV.bd.SQL;
import PTBDPV.bd.TransactionDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class cont_login implements Initializable {
    @FXML
    JFXButton btnInicio,btnSAL;
    @FXML
    JFXTextField JtxUsuario;
    @FXML
    JFXPasswordField JtxPas;
    String userName,passUser;
    TransactionDAO transactionDAO=new TransactionDAO(SQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnInicio.setOnAction(handler);
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnInicio)
            {
                Alert alert;
                userName=JtxUsuario.getText().toString();
                passUser=JtxPas.getText().toString();
                if(userName.trim().length()>0 && passUser.trim().length()>0)
                {
                    SQL.authentication(userName,passUser);
                    alert=new Alert(Alert.AlertType.INFORMATION);
                }
                else {
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("¡Valores erroneos o vacíos!");
                    alert.show();
                }
            }
        }
    };

}
