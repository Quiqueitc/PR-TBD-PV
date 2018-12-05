package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datEmpleados;
import PTBDPV.datos.datUnidadMedida;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;


import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class cont_configuracionL implements Initializable {
    @FXML
    BorderPane BPC;

    @FXML
    JFXComboBox <datUnidadMedida>cbUM;
    @FXML
    JFXRadioButton rdA,rdC;
    @FXML
    JFXButton btnOP,btnCajeros,btnUM,btnGuardarCajero,btnCancel,btnGUM,btnCUM,btnInicio,btnCerrar;

    @FXML
    VBox VBOG,VBUM,VBP;
    @FXML
    HBox VBU;

    @FXML
    JFXTextField txtU,txtClave,txtNC,txtUM,txtNickName,txtNombre,txtDomicilio,txtTelefono;
    @FXML
    JFXPasswordField txtPassword;
    @FXML
    TableView<datEmpleados> tblBP;
    char idUM;
    String descripcion;

    @FXML
    JFXCheckBox ChCredito,ChPC,ChRE,ChAMD,ChRHV,ChCC,ChAM,ChVEM,ChAI,ChVMI,ChCP,ChMod,ChEl,ChVRV,ChCProm,ChACC,ChCME,ChCConf,ChRCD;
    String NN,NO,DO,TE,PA,IdTip;
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
        btnInicio.setOnAction(handler);
        btnCerrar.setOnAction(handler);
        tblBP.setItems(transactionDAO.LlenarTblEmp());

    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Alert alert;
            if(event.getSource()==btnOP)
            {
                VBOG.setVisible(true);
                VBU.setVisible(false);
                VBUM.setVisible(false);
                VBP.setVisible(false);
                tblBP.setVisible(false);
            }
            if (event.getSource()==btnCajeros)
            {
                VBOG.setVisible(false);
                VBP.setVisible(false);
                VBU.setVisible(true);
                VBUM.setVisible(false);
                tblBP.setVisible(true);
            }
            if (event.getSource()==btnUM)
            {
                VBOG.setVisible(false);
                VBP.setVisible(false);
                VBU.setVisible(false);
                VBUM.setVisible(true);
                llenaCBidioma();
                tblBP.setVisible(false);

            }
            if(event.getSource()==btnInicio)
            {
                datEmpleados datEmpleados=llenarDatos();
                System.out.println(" use  "+NN+ "      con "+ PA);
                if(NN.trim().length()>0 && PA.trim().length()>0)
                {
                    try {
                        if(transactionDAO.insEmpleado(datEmpleados))
                        {
                            alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Se agregó correctamente el empleado "+ datEmpleados.getNombre());
                            alert.show();
                            CLEAR_GUI();
                            tblBP.setItems(transactionDAO.LlenarTblEmp());
                        }
                        else {
                            alert=new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Error al insertar");
                            alert.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("El usuario o contraseña están vacíos");
                    alert.show();
                }
            }
            if (event.getSource()==btnCerrar){
                CLEAR_GUI();

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
    private datEmpleados llenarDatos()
    {
        datEmpleados datEmpleados=null;
        try {
            NN=txtNickName.getText().toString();
            NO=txtNombre.getText().toString();
            DO=txtDomicilio.getText().toString();
            TE=txtTelefono.getText().toString();
            PA=txtPassword.getText().toString();
            if(rdA.isSelected())
                IdTip="A";
            else
            if(rdC.isSelected())
                IdTip="C";
            java.sql.Date inicioLocal = new java.sql.Date(0);
            datEmpleados=new datEmpleados(NN,NO,DO,TE,PA,IdTip,inicioLocal);
        }
        catch (NullPointerException e)
        {
            System.out.println(e);
        }
        return datEmpleados;
    }
    public void showMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();

    }
    private void CLEAR_GUI()
    {
        txtNickName.clear();
        txtNombre.clear();
        txtDomicilio.clear();
        txtTelefono.clear();
        txtPassword.clear();
        rdC.setSelected(true);

    }
}
