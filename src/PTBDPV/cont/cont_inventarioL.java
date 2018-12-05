package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datClientes;
import PTBDPV.datos.datDepartamento;
import PTBDPV.datos.datProductos;
import PTBDPV.datos.datUnidadMedida;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class cont_inventarioL implements Initializable {
    @FXML
    BorderPane BPI;
    @FXML
    JFXButton btnAI,btnAjI,btnPbI,btnRI,btnAC,btnSerch,btnTodos;
    @FXML
    Label lblTi,lblCostoI,lblCant;
    @FXML
    JFXTextField txtCP,txtDP,txtCaP,txtCsP;

    @FXML
    JFXComboBox <datDepartamento> cbDto;
    @FXML
    TableView <datProductos> tbReporteI;
    @FXML
    TableView <datProductos> tblProdBajosI;

    @FXML
    VBox VBRI,VBAI,VBPBI;

    int aux;

    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAI.setOnAction(handler);
        btnAjI.setOnAction(handler);
        btnPbI.setOnAction(handler);
        btnRI.setOnAction(handler);
        btnAC.setOnAction(handler);
        btnSerch.setOnAction(handler);
        btnTodos.setOnAction(handler);
        llenaCBDpto();
        BPI.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER:
                            obtenDatos();
                            break;
                    }
                }
        );
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnAI){
                VBAI.setVisible(true);
                VBPBI.setVisible(false);
                VBRI.setVisible(false);
                lblTi.setText("Agregar a inventario");
                txtCsP.setPromptText("Cantidad");
                btnAC.setText("Agregar cantidad a inventario");
                aux=1;
                limpieza();
            }
            if (event.getSource()==btnAjI){
                VBAI.setVisible(true);
                VBPBI.setVisible(false);
                VBRI.setVisible(false);
                lblTi.setText("Ajuste de inventario");
                txtCsP.setPromptText("Nueva Cantidad");
                btnAC.setText("Ajustar el inventario de este producto");
                aux=2;
                limpieza();
            }
            if(event.getSource()==btnPbI){
                VBAI.setVisible(false);
                VBPBI.setVisible(true);
                VBRI.setVisible(false);
                reloadDataPBI();
                limpieza();

            }
            if(event.getSource()==btnRI){
                VBAI.setVisible( false);
                VBPBI.setVisible(false);
                VBRI.setVisible(true);
                limpieza();
                reloadDataR();

            }
            if(event.getSource()==btnAC){
                if(txtCsP.getText()!=null && txtCP.getText()!=null) {
                    if(aux==1)
                        try {
                            transactionDAO.inventario(txtCP.getText().toString(), Float.valueOf(txtCsP.getText()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    else
                        try {
                            transactionDAO.inventarioUP(txtCP.getText().toString(), Double.valueOf(txtCsP.getText()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                }
                else
                    showMessage("Campos vacios");


                limpieza();
            }
            if (event.getSource()==btnSerch)
            {
                if (cbDto.getSelectionModel().getSelectedIndex() > -1)
            {
                int aux=cbDto.getValue().getIdDepartamento();
                tbReporteI.setItems(transactionDAO.InventarioRIXDpto(aux));
                lblCant.setText(String.valueOf(transactionDAO.TotalInventXDEP(aux)));
                lblCostoI.setText(String.valueOf(transactionDAO.CostoTotalXDep(aux)));
            }
            }
            if (event.getSource()==btnTodos)
            {
                reloadDataR();
            }



        }
    };

    public  void obtenDatos()
    {
        if(txtCP.getText()!=null) {
            datProductos recupera = transactionDAO.obtenerProduct(txtCP.getText());
            txtDP.setText(recupera.getDescripcion().toString());
            txtCaP.setText(String.valueOf(recupera.getNoExistencia()));
        }
        else
            showMessage("Introduzca el código del producto");
    }

    public void showMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();

    }
    private void closeStage( )
    {
        Stage stage = (Stage) BPI.getScene().getWindow();
        stage.close();
    }

    private void reloadDataPBI()
    {
        tblProdBajosI.getItems().clear();
        tblProdBajosI.setItems(transactionDAO.InventarioPBI());

    }

    private void reloadDataR()
    {
        tbReporteI.getItems().clear();
        tbReporteI.setItems(transactionDAO.InventarioRI());
        lblCant.setText(String.valueOf(transactionDAO.TotalInvent()));
        lblCostoI.setText(String.valueOf(transactionDAO.CostoTotal()));

    }



    public  void limpieza(){
        txtCP.clear();
        txtDP.clear();
        txtCaP.clear();
        txtCsP.clear();
    }

    public void llenaCBDpto() {
        cbDto.setItems(transactionDAO.llenaCBDep());
        //Convierte ComboBox toString a StringConverter
        cbDto.setConverter(new StringConverter<datDepartamento>() {

            @Override
            public String toString(datDepartamento p) {
                return (p.getDescripcion());
            }

            @Override
            public datDepartamento fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
}
