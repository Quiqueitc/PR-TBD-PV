package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datClientes;
import PTBDPV.datos.datDetalles;
import PTBDPV.datos.datFactura;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class cont_VDDL implements Initializable {
    @FXML
    BorderPane BPVDD;
    @FXML
    TableView<datFactura> tblFac;
    @FXML
    TableView<datDetalles> tblDetalle;
    @FXML
    JFXComboBox<String>cbUsu;
    @FXML
    JFXDatePicker dpF;
    @FXML
    JFXCheckBox chbVen;
    java.util.Date fecha = new java.util.Date();
    String BUS= String.valueOf(fecha);
    boolean CH=false;
    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tblFac.setItems(transactionDAO.LlenarTblFAC(BUS));
        tblFac.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case DOWN:
                            if(tblFac.getSelectionModel().getSelectedIndex()>=0) {
                                tblDetalle.setVisible(true);
                                datFactura datFactura=tblFac.getSelectionModel().getSelectedItem();
                                tblDetalle.setItems(transactionDAO.LlenarTblDET(datFactura.getNoFactura()));
                            }
                            break;
                    }
                }
        );
        tblFac.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                    tblDetalle.setVisible(true);
                    datFactura datFactura=tblFac.getSelectionModel().getSelectedItem();
                    tblDetalle.setItems(transactionDAO.LlenarTblDET(datFactura.getNoFactura()));
                }
            }
        });
        cbUsu.setOnAction(handler);
        dpF.setOnAction(handler);
        chbVen.setOnAction(handler);
        cbUsu.setItems(transactionDAO.LlenarEMP());

    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==dpF)
            {
                try {
                    java.sql.Date da = java.sql.Date.valueOf(dpF.getValue());
                    tblFac.setItems(transactionDAO.LlenarTblFAC(da+""));
                }
                catch (Exception e){
                    System.out.println(e);
                }

            }
            if(event.getSource()==cbUsu)
            {
                try {
                    BUS=cbUsu.getValue().toString();
                    tblFac.setItems(transactionDAO.LlenarTblFAC(BUS));
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }

            if(event.getSource()==chbVen)
            {
               if(chbVen.isSelected()) {
                   BUS = "C";
                   chbVen.setSelected(true);
               }
               else {
                   BUS = "";
                   chbVen.setSelected(false);
                                  }
                tblFac.setItems(transactionDAO.LlenarTblFAC(BUS));
            }
        }
    };
    private void closeStage( )
    {
        Stage stage = (Stage) BPVDD.getScene().getWindow();
        stage.close();
    }
}
