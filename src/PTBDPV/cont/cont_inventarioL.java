package PTBDPV.cont;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class cont_inventarioL implements Initializable {
    @FXML
    BorderPane BPI;
    @FXML
    JFXButton btnAI,btnAjI,btnPbI,btnRI,btnAC;
    @FXML
    Label lblTi;
    @FXML
    JFXTextField txtCP,txtDP,txtCaP,txtCsP;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAI.setOnAction(handler);
        btnAjI.setOnAction(handler);
        btnPbI.setOnAction(handler);
        btnRI.setOnAction(handler);
        btnAC.setOnAction(handler);
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnAI){
                lblTi.setText("Agregar a inventario");
                txtCsP.setText("Cantidad");
                btnAC.setText("Agregar cantidad a inventario");
            }
            if (event.getSource()==btnAjI){
                lblTi.setText("Ajuste de inventario");
                txtCsP.setText("Nueva Cantidad");
                btnAC.setText("Ajustar el inventario de este producto");
            }
            if(event.getSource()==btnPbI){

            }
            if(event.getSource()==btnRI){

            }
            if(event.getSource()==btnAC){

            }

        }
    };
    private void closeStage( )
    {
        Stage stage = (Stage) BPI.getScene().getWindow();
        stage.close();
    }
}
