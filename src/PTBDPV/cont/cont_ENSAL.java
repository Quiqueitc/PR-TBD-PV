package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datMovimientos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class cont_ENSAL implements Initializable {
    @FXML
    BorderPane BPES;
    @FXML
    Label lblTI;
    @FXML
    JFXTextField txtDescripcion,txtCantidad;
    @FXML
    JFXButton btnEnter,btnCancelar;
    String NU,RFC,TM;
    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnEnter.setOnAction(handler);
        btnCancelar.setOnAction(handler);
        if(TM.equalsIgnoreCase("E"))
            lblTI.setText(" Entrada de efectivo");
        else
            lblTI.setText(" Salida de efectivo");
        txtDescripcion.requestFocus();
        BPES.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ESCAPE: // Ir a siguiente txt
                            closeStage();
                            break;
                    }
                }
        );
        txtDescripcion.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER:
                            txtCantidad.requestFocus();
                            break;

                    }
                }
        );
        txtCantidad.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER:
                           insENSA();
                            break;

                    }
                }
        );
        txtCantidad.setOnKeyReleased(
                event -> {
                    if(event.getCode()== KeyCode.LESS || event.getCode()==KeyCode.MINUS)
                    {
                        txtCantidad.clear();
                    }
                }
        );

    }
    public cont_ENSAL(String NU,String RFC,String TM){
        this.NU=NU;
        this.RFC=RFC;
        this.TM=TM;
    }

    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnEnter)
            {
                insENSA();
            }
            if(event.getSource()==btnCancelar)
            {
                closeStage();
            }
        }
    };
    private void closeStage( )
    {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    private void insENSA(){
        Alert alert;
            String DES;
            double CAN;
            try {
                CAN=Double.parseDouble(txtCantidad.getText().toString());
                DES=txtDescripcion.getText().toString();
                if(DES.trim().length()>0 && CAN>0)
                {
                    datMovimientos datMovimientos=new datMovimientos(DES,CAN,NU,RFC,TM);
                    if(transactionDAO.insMOV(datMovimientos))
                    {
                        String men;
                        if(TM.equalsIgnoreCase("E"))
                            men="Entrada";
                        else  men ="Salida";
                        alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("La "+men+ " de efectivo fue registrada con éxito");
                        alert.show();
                        closeStage();
                    }
                    else
                    {
                        alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("No tiene los permisos para entrada o salida de efectivo");
                        alert.show();
                    }
                }
                else {
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("El campo descripción esta vacío");
                    alert.show();
                }
            }
            catch (Exception e)
            {
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Escriba valores correctos en cantidad");
                alert.show();
                System.out.println(e);
            }
    }
}
