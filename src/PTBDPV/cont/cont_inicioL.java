package PTBDPV.cont;

import com.jfoenix.controls.JFXButton;
import com.sun.org.glassfish.gmbal.IncludeSubclass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import PTBDPV.datos.datEmpleados;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class cont_inicioL implements Initializable {
    @FXML
    BorderPane BP1;
    @FXML
    JFXButton btnTE,btnPB,btnSalir,btnVenta,btnProducto,btnInventario,btnConfiguracion,btnCorte;;
    @FXML
    Label lblfecha;

    @FXML
    MenuItem EDC,NUC,ACC,ELC;
    @FXML
    VBox VBV,VBP,VBL,VBI,VBC,VBR;
    static datEmpleados datEmpleados; //DATOS DEL USUARIO EN CURSO

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnTE.setOnAction(handler);
        btnPB.setOnAction(handler);
        btnSalir.setOnAction(handler);
        btnVenta.setOnAction(handler);
        btnProducto.setOnAction(handler);
        btnInventario.setOnAction(handler);
        btnConfiguracion.setOnAction(handler);
        btnCorte.setOnAction(handler);
        EDC.setOnAction(handler);
        NUC.setOnAction(handler);
        ACC.setOnAction(handler);
        ELC.setOnAction(handler);
        btnTE.setText(datEmpleados.getNombre());
        BP1.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case F1:
                            MVenta();
                            break;
                        case F2:
                            MCliente();
                            break;
                        case F3:
                            MProducto();
                            break;
                        case F4:
                            MInventario();
                            break;
                        case ESCAPE:
                            closeStage();
                            break;
                    }
                }
        );

    }
    public cont_inicioL(datEmpleados datEmpleados){
        this.datEmpleados=datEmpleados;
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnSalir)
            {
                closeStage();
            }
            if(event.getSource()==btnTE){

            }
            if(event.getSource()==btnVenta){
               MVenta();
            }
            if(event.getSource()==btnProducto) {
               MProducto();
            }
            if(event.getSource()==btnInventario){
               MInventario();
            }
            if(event.getSource()==btnConfiguracion){
               MConfiguracion();
            }
            if (event.getSource()==btnCorte){
                MCorte();
            }
            if(event.getSource()==EDC){
                VBI.setVisible(false);
                VBC.setVisible(false);
                VBR.setVisible(false);
                VBP.setVisible(false);
                VBV.setVisible(false);
                VBL.setVisible(true);
            }
            if(event.getSource()==NUC){

            }
            if(event.getSource()==ACC){

            }
            if(event.getSource()==ELC){

            }
        }
    };
    private void closeStage( ) {
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("Confirmar");
        alert.setContentText("¿Desea salir?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            System.exit(0);
        } else if (option.get() == ButtonType.CANCEL) {
        }
    }
    public void MVenta(){
        VBI.setVisible(false);
        VBC.setVisible(false);
        VBL.setVisible(false);
        VBR.setVisible(false);
        VBP.setVisible(false);
        VBV.setVisible(true);
    }
    public  void MCliente(){

    }
    public  void MProducto(){

    }
    public  void MInventario(){

    }
    public  void MConfiguracion(){

    }
    public  void MCorte(){

    }
}
