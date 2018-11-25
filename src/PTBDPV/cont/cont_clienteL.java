package PTBDPV.cont;

import PTBDPV.bd.SQL;
import PTBDPV.bd.TransactionDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class cont_clienteL implements Initializable {
    @FXML
    BorderPane BPL;
    @FXML
    JFXButton btnEsC,btnNuC,btnMoC,btnElC,btnSeC;
    @FXML
    VBox VBEC;
    @FXML
    JFXListView<String> ltClientes;
    ObservableList<String> listItems = FXCollections.observableArrayList();
    TransactionDAO transactionDAO=new TransactionDAO(SQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            ltClientes.setItems(transactionDAO.LlenarClientes());
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        }
    };
    private void closeStage( )
    {
        Stage stage = (Stage) BPL.getScene().getWindow();
        stage.close();
    }
}
