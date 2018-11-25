package PTBDPV.cont;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class cont_corteL implements Initializable {
    @FXML
    BorderPane BPC;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        }
    };
    private void closeStage( )
    {
        Stage stage = (Stage) BPC.getScene().getWindow();
        stage.close();
    }
}
