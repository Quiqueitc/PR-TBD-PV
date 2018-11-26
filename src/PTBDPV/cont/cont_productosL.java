package PTBDPV.cont;

import PTBDPV.datos.datDepartamento;
import com.jfoenix.controls.*;
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

public class cont_productosL implements Initializable {
    @FXML
    BorderPane BPP;
    @FXML
    JFXButton btnNP,btnMP,btnEP,btnDE,btnPR,btnPO,btnSeC,btnAD,btnAP,btnGP,btnCP, btnID,btnIP,btnIPr;//btnID ainsertar dtp pro --> btnIP prom--> btnIPr
    @FXML
    JFXTextField txtCP,txtDP,txtPCP,txtPVP,txtPMP,txtIP,txtMP,txtND,txtNP,txtTP, txtDeP,txtCoP,txtNoP,txtCiP,txtCaP,txtPP;//txtND no depto prover --> txtNP txtTP prom --> txtCoP,txtNoP,txtCiP,txtCaP,txtPP
    @FXML
    JFXRadioButton rdUP,rdAG;
    @FXML
    JFXComboBox<String>cbDP,cbPP;
    @FXML
    JFXListView<String> lvProductos;
    @FXML
    VBox VBBP,VBAP,VBDE,VBPR,VBPO; //VBPO proveedor
    @FXML
    TableView<datDepartamento> tblDepartamento; //tabla depto
    @FXML
    TableView<String> tblProveedor; //tabla prove
    @FXML
    Label lblTi;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnNP.setOnAction(handler);
        btnMP.setOnAction(handler);
        btnEP.setOnAction(handler);
        btnDE.setOnAction(handler);
        btnPR.setOnAction(handler);
        btnPO.setOnAction(handler);
        btnSeC.setOnAction(handler);
        btnAD.setOnAction(handler);
        btnAP.setOnAction(handler);
        btnGP.setOnAction(handler);
        btnCP.setOnAction(handler);
        btnID.setOnAction(handler);
        btnIP.setOnAction(handler);
        btnIPr.setOnAction(handler);
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnNP)
            {
                VBAP.setVisible(true);
                VBBP.setVisible(false);
                VBDE.setVisible(false);
                VBPO.setVisible(false);
            }
            if (event.getSource()==btnMP){
                lblTi.setText("Modificar Producto");
                VBBP.setVisible(true);
                VBDE.setVisible(false);
                VBPO.setVisible(false);
                VBAP.setVisible(false);

            }if (event.getSource()==btnEP){
                lblTi.setText("Eliminar Producto");
                VBBP.setVisible(true);
                VBPO.setVisible(false);
                VBDE.setVisible(false);
                VBAP.setVisible(false);
            }
            if(event.getSource()==btnDE){ //DEPTO
                VBDE.setVisible(true);
                VBPO.setVisible(false);
                VBPR.setVisible(false);

            }
            if(event.getSource()==btnPR){ //PROMOCION
                VBPR.setVisible(true);
                VBDE.setVisible(false);
                VBPO.setVisible(false);
            }
            if(event.getSource()==btnPO){ //PROVEDOR
                VBDE.setVisible(false);
                VBPO.setVisible(true);
                VBPR.setVisible(false);

            }if(event.getSource()==btnSeC){

            }
            if(event.getSource()==btnAD){
                VBDE.setVisible(true);
                VBPO.setVisible(false);
            }if(event.getSource()==btnAP){
                VBDE.setVisible(false);
                VBPO.setVisible(true);
            }
            if (event.getSource()==btnGP){

            }
            if(event.getSource()==btnCP){

            }
            if(event.getSource()==btnID){

            }
            if(event.getSource()==btnIP){

            }
            if(event.getSource()==btnIPr){

            }
        }
    };
    private void closeStage( )
    {
        Stage stage = (Stage) BPP.getScene().getWindow();
        stage.close();
    }
}
