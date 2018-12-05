package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datComp;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import PTBDPV.datos.datEmpleados;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class cont_inicioL implements Initializable {
    @FXML
    BorderPane BP1;
    @FXML
    JFXButton btnTE,btnPB,btnSalir,btnVenta,btnProducto,btnInventario,btnConfiguracion,btnCorte,btnCliente;;
    @FXML
    Label lblfecha,lblrfc,lblNom;
    @FXML
    VBox VBV,VBP,VBL,VBI,VBC,VBR;

    private static datEmpleados datEmpleados; //DATOS DEL USUARIO EN CURSO
    private static String datSucu[];
    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());

    //datTrans datTrans=new datTrans(datEmpleados);
    //datComp datComp= new datComp(datEmpleados);
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
        btnCliente.setOnAction(handler);
        btnTE.setText(datEmpleados.getNombre());
        datSucu=transactionDAO.datSucu();
        lblNom.setText(datSucu[1]);
        lblrfc.setText(datSucu[0]);
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
                if(!datEmpleados.getIdTip().equalsIgnoreCase("C"))
                {
                    MConfiguracion();
                }
                else {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("No tienes los permisos para está área");
                    alert.show();
                }
            }
            if (event.getSource()==btnCorte){
                if(!datEmpleados.getIdTip().equalsIgnoreCase("C"))
                {
                    MCorte();
                }
                else {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("No tienes los permisos para está área");
                    alert.show();
                }

            }
            if(event.getSource()==btnCliente){
                MCliente();
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
            MySQL mySQL=new MySQL();
            mySQL.Disconnect();
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
        //cont_ventasL cont_ventasL;
    }
    public  void MCliente(){
        VBI.setVisible(false);
        VBC.setVisible(false);
        VBR.setVisible(false);
        VBP.setVisible(false);
        VBV.setVisible(false);
        VBL.setVisible(true);
    }
    public  void MProducto(){
        VBI.setVisible(false);
        VBC.setVisible(false);
        VBR.setVisible(false);
        VBV.setVisible(false);
        VBL.setVisible(false);
        VBP.setVisible(true);
    }
    public  void MInventario(){
        VBC.setVisible(false);
        VBR.setVisible(false);
        VBV.setVisible(false);
        VBL.setVisible(false);
        VBP.setVisible(false);
        VBI.setVisible(true);
    }
    public  void MConfiguracion(){
        VBR.setVisible(false);
        VBV.setVisible(false);
        VBL.setVisible(false);
        VBP.setVisible(false);
        VBI.setVisible(false);
        VBC.setVisible(true);
    }
    public  void MCorte(){
        VBV.setVisible(false);
        VBL.setVisible(false);
        VBP.setVisible(false);
        VBI.setVisible(false);
        VBC.setVisible(false);
        VBR.setVisible(true);

    }
}
