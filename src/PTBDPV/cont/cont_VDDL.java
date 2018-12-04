package PTBDPV.cont;

import PTBDPV.datos.datFactura;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class cont_VDDL implements Initializable {
    @FXML
    BorderPane BPVDD;
    @FXML
    TableView<datFactura> tblFac;
    @FXML
    JFXComboBox<String>cbUsu;
    @FXML
    JFXDatePicker dpFec;
    @FXML
    JFXCheckBox chbVen;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==chbVen)
            {

            }
        }
    };
    private void closeStage( )
    {
        Stage stage = (Stage) BPVDD.getScene().getWindow();
        stage.close();
    }
}
