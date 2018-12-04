package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datClientes;
import PTBDPV.datos.datComp;
import PTBDPV.datos.datProductos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class cont_F12cobrarL implements Initializable {
    @FXML
    BorderPane BPF12;
    @FXML
    VBox VBEF,VBCR;
    @FXML
    TableView<datClientes> tblCC;
    @FXML
    JFXTextField txtPaga;
    @FXML
    Label lblCA,lblTO,lblTOTAL;
    @FXML
    JFXButton btnEfectivo,btnCredito,btnEnter,btnCancelar;
    cont_ventasL cont_ventasL=new cont_ventasL();
    int NF;
    double[] da=new double[2];

    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnEfectivo.setOnAction(handler);
        btnCredito.setOnAction(handler);
        btnEnter.setOnAction(handler);
        btnCancelar.setOnAction(handler);
        da=transactionDAO.totFactura(NF);
        lblTOTAL.setText(da[0]+"");
        lblTO.setText(da[1]+"");
        txtPaga.setOnKeyReleased(
                event -> {
                    double tot = 0.0;
                    try{
                        tot=Double.parseDouble(txtPaga.getText());
                    }
                    catch (Exception e){}
                    lblCA.setText(tot-(Double.parseDouble(lblTOTAL.getText()))+"");
                }
        );
        BPF12.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER: // AGREGAR EL PRODUCTO
                            MS(true);
                            closeStage();
                            break;
                        case ESCAPE:
                            closeStage();
                            break;

                    }
                }
        );

        tblCC.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                   datClientes datClientes=tblCC.getSelectionModel().getSelectedItem();
                    try {
                        if(transactionDAO.updFAC(datClientes.getIdClie(),NF))
                        {
                            MS(true);
                            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Compra agregada al cliente "+datClientes.getNomCompleto());
                            alert.show();
                            closeStage();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });


    }
    public cont_F12cobrarL(int NF){
        this.NF=NF;
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnEfectivo)
            {

               VBCR.setVisible(false);
               VBEF.setVisible(true);
            }
            if(event.getSource()==btnCredito)
            {
                VBCR.setVisible(true);
                VBEF.setVisible(false);
                tblCC.setItems(transactionDAO.LlenarTblCC());
            }
            if(event.getSource()==btnEnter)
            {
               MS(true);
                closeStage();
            }
            if(event.getSource()==btnCancelar)
            {
                MS(false);
                closeStage();
            }
        }
    };
    private void MS(boolean S){
        datComp datComp=new datComp(S);
///        cont_ventasL.VDD();
    }
    private void closeStage( )
    {
        Stage stage = (Stage) BPF12.getScene().getWindow();
        stage.close();
    }
}
