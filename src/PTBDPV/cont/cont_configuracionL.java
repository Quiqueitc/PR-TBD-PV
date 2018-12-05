package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datUnidadMedida;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;


import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class cont_configuracionL implements Initializable {
    @FXML
    BorderPane BPC;

    @FXML
    JFXComboBox <datUnidadMedida>cbUM;

    @FXML
    JFXButton btnOP,btnCajeros,btnUM,btnGuardarCajero,btnCancel,btnGUM,btnCUM;

    @FXML
    VBox VBOG,VBU,VBUM;

    @FXML
    JFXTextField txtU,txtClave,txtNC,txtUM;

    char idUM;
    String descripcion;

    @FXML
    JFXCheckBox ChCredito,ChPC,ChRE,ChRS,ChAMD,ChRHV,ChCC,ChAM,ChVEM,ChAI,ChVMI,ChCP,ChMod,ChEl,ChVRV,ChCProm,ChACC,ChCME,ChCConf,ChRCD;

    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VBOG.setVisible(false);
        VBU.setVisible(false);
        VBUM.setVisible(false);
        btnOP.setOnAction(handler);
        btnCajeros.setOnAction(handler);
        btnUM.setOnAction(handler);
        btnGuardarCajero.setOnAction(handler);
        btnCancel.setOnAction(handler);
        btnGUM.setOnAction(handler);


    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            if(event.getSource()==btnOP)
            {
                VBOG.setVisible(true);
                VBU.setVisible(false);
                VBUM.setVisible(false);
            }
            if (event.getSource()==btnCajeros)
            {
                VBOG.setVisible(false);
                VBU.setVisible(true);
                VBUM.setVisible(false);
            }
            if (event.getSource()==btnUM)
            {
                VBOG.setVisible(false);
                VBU.setVisible(false);
                VBUM.setVisible(true);
                llenaCBidioma();

            }
            if(event.getSource()==btnGuardarCajero)
            {

            }
            if (event.getSource()==btnCancel){

            }
            if(event.getSource()==btnGUM)
            {
                if (cbUM.getSelectionModel().getSelectedIndex() > -1)
                {
                    idUM = cbUM.getValue().getIdUM();
                    descripcion=txtUM.getText();
                    transactionDAO.UPDATEUM(idUM,descripcion);
                }
                llenaCBidioma();



            }


        }
    };


    private void closeStage( )
    {
        Stage stage = (Stage) BPC.getScene().getWindow();
        stage.close();
    }

    public void llenaCBidioma() {
        cbUM.setItems(transactionDAO.UnidadesMedida());
        //Convierte ComboBox toString a StringConverter
        cbUM.setConverter(new StringConverter<datUnidadMedida>() {

            @Override
            public String toString(datUnidadMedida p) {
                return (p.getDecripcion());
            }

            @Override
            public datUnidadMedida fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public void showMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();

    }
}
