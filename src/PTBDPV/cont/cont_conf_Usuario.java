package PTBDPV.cont;

import PTBDPV.bd.SQL;
import PTBDPV.bd.TransactionDAO;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class cont_conf_Usuario implements Initializable{
    @FXML
    JFXTextField txtNickName,txtNombre,txtDomicilio,txtTelefono,txtrfc,txtNombreS,txtDomicilioS,txtTelefonoS,txtCiudad;
    @FXML
    JFXComboBox<String> cbEncargado;
    @FXML
    JFXPasswordField txtPassword;
    @FXML
    JFXButton btnInicio,btnInicioS,btnRegresar,btnCerrar;
    @FXML
    JFXDatePicker dpfe;
    @FXML
    VBox vbCU,vbCS;
    @FXML
    AnchorPane APU,APS;
    String NN,NO,DO,TE,PA;
    char TU='A';
    java.sql.Date fecContra;
    TransactionDAO transactionDAO=new TransactionDAO(SQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnInicio.setOnAction(handler);
        btnInicioS.setOnAction(handler);
        btnRegresar.setOnAction(handler);
        btnCerrar.setOnAction(handler);
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Alert alert;
            if(event.getSource()==btnInicio)
            {
                llenarDatos();
               if(NN.trim().length()>0 && PA.trim().length()>0)
               {
                        vbCU.setVisible(false);
                        vbCS.setVisible(true);
                        APU.setVisible(false);
                        APS.setVisible(true);
               }
               else
                   {
                        alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("El usuario o contraseña están vacíos");
                        alert.show();
                   }
            }
            if(event.getSource()==btnInicioS)
            {

            }
            if(event.getSource()==btnRegresar)
            {
                vbCU.setVisible(true);
                vbCS.setVisible(false);
                APU.setVisible(true);
                APS.setVisible(false);
            }
            if(event.getSource()==btnCerrar)
            {
                closeStage();
            }
        }
    };
    private void llenarDatos()
    {
        try {
            NN=txtNickName.getText().toString();
            NO=txtNombre.getText().toString();
            DO=txtDomicilio.getText().toString();
            TE=txtTelefono.getText().toString();
            PA=txtPassword.getText().toString();
            fecContra= java.sql.Date.valueOf(dpfe.getValue());
        }
        catch (NullPointerException e)
        {
            System.out.println(e);
        }
    }
    private void closeStage( )
    {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

}
