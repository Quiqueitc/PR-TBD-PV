package PTBDPV.cont;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class cont_ventasL implements Initializable {
    @FXML
    BorderPane BPV;
    @FXML
    JFXButton btnEnter;
    @FXML
    JFXTextField txtCodigo;
    boolean  keyCommand=false,keyW=false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnEnter.setOnAction(handler);
        BPV.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.CONTROL)
                keyCommand = true;
            else if(e.getCode() == KeyCode.V)
                keyW = true;
            if(keyCommand && keyW)
            {
               //Abrir varios productos
            }
            //both are pressed


        });


    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnEnter)
            {

            }
        }
    };
    private void closeStage( )
    {
        Stage stage = (Stage) BPV.getScene().getWindow();
        stage.close();
    }
}
