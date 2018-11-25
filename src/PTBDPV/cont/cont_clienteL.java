package PTBDPV.cont;

import PTBDPV.bd.SQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datDetalles;
import PTBDPV.datos.datFactura;
import PTBDPV.datos.datPagos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.skins.JFXListViewSkin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class cont_clienteL implements Initializable {
    @FXML
    BorderPane BPL;
    @FXML
    JFXButton btnEsC,btnNuC,btnMoC,btnElC,btnSeC,btnVD,btnVP,btnAF;
    @FXML
    VBox VBEC,VBNC,VBE;
    @FXML
    JFXTextField txtBUC;
    @FXML
    JFXListView<String>ltClientes;
    @FXML
    Label lblTi;
    @FXML
    TableView<datFactura>tblFactura;
    @FXML
    TableView<datDetalles> tblDetalles;
    @FXML
    TableView <datPagos>tblPagos;
    String B;
    ObservableList<String> listItems = FXCollections.observableArrayList();
    TransactionDAO transactionDAO=new TransactionDAO(SQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnEsC.setOnAction(handler);
        btnNuC.setOnAction(handler);
        btnMoC.setOnAction(handler);
        btnElC.setOnAction(handler);
        btnSeC.setOnAction(handler);
        txtBUC.setOnKeyReleased(
                event -> {
                    B=txtBUC.getText().toString();
                    ltClientes.setItems(transactionDAO.LlenarClientes(B));
                }
        );
        txtBUC.setOnKeyPressed(
                event -> {
                    switch (event.getCode()){
                        case ENTER:
                        B=txtBUC.getText().toString();
                        ltClientes.setItems(transactionDAO.LlenarClientes(B));
                        break;
                    }

                }
        );
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnEsC){
                VBNC.setVisible(false);
                VBE.setVisible(false);
                VBEC.setVisible(true);
                lblTi.setText("Estado de cuenta");
            }
            if(event.getSource()==btnNuC){
                VBEC.setVisible(false);
                VBE.setVisible(false);
                VBNC.setVisible(true);


            }
            if(event.getSource()==btnMoC){
                VBNC.setVisible(false);
                VBE.setVisible(false);
                VBEC.setVisible(true);
                lblTi.setText("Modificar cliente");

            }
            if(event.getSource()==btnElC){
                VBE.setVisible(false);
                VBNC.setVisible(false);
                VBEC.setVisible(true);
                lblTi.setText("Eliminar cliente");
            }
            if(event.getSource()==btnSeC){
                VBNC.setVisible(false);
                VBEC.setVisible(false);
                VBE.setVisible(true);
            }
        }

    };
    private void closeStage( )
    {
        Stage stage = (Stage) BPL.getScene().getWindow();
        stage.close();
    }
}
