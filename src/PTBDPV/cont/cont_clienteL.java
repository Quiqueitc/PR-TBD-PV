package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.misc.FloatingDecimal;


import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class cont_clienteL implements Initializable {
    @FXML
    BorderPane BPL;
    @FXML
    JFXButton btnEsC,btnNuC,btnMoC,btnElC,btnSeC,btnVD,btnVP,btnAF,btnAC,btnCC;
    @FXML
    VBox VBEC,VBNC,VBE;
    @FXML
    JFXTextField txtBUC,txtNC,txtDC,txtTC,txtEC,txtLC;

    @FXML
    TableView<datClientes>tblClientes;
    @FXML
    Label lblTi,lblC,lblCC,lblNC;
    @FXML
    TableView<datFactura>tblFactura;
    @FXML
    TableView<datDetFact> tblDetalles;
    @FXML
    TableView <datPagos>tblPagos;
    String B;
    ObservableList<datClientes> listItems = FXCollections.observableArrayList();
    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    int idC,noF;
    int idClie=0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initTableTransactions();
        btnEsC.setOnAction(handler);
        btnNuC.setOnAction(handler);
        btnMoC.setOnAction(handler);
        btnElC.setOnAction(handler);
        btnSeC.setOnAction(handler);
        btnAC.setOnAction(handler);
        btnCC.setOnAction(handler);
        btnVD.setOnAction(handler);
        btnVP.setOnAction(handler);
        btnAF.setOnAction(handler);


        txtBUC.setOnKeyReleased(
                event -> {
                    B=txtBUC.getText().toString();
                    tblClientes.setItems(transactionDAO.listClient(B));
                    }
        );
        txtBUC.setOnKeyPressed(
                event -> {
                    switch (event.getCode()){
                        case ENTER:
                        B=txtBUC.getText().toString();
                            tblClientes.setItems(transactionDAO.listClient(B));
                        break;
                    }

                }
        );
    }



    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnEsC){
                muestraEdoCliente();
            }
            if(event.getSource()==btnNuC){

                VBEC.setVisible(false);
                VBE.setVisible(false);
                VBNC.setVisible(true);
                limpieza();

            }
            if(event.getSource()==btnMoC){
                VBNC.setVisible(false);
                VBE.setVisible(false);
                VBEC.setVisible(true);
                lblTi.setText("Modificar cliente");

                //Verifica que esté seleccionado algún elemento de la tabla
                if(tblClientes.getSelectionModel().getSelectedIndex()>=0) {
                    datClientes datClientes=tblClientes.getSelectionModel().getSelectedItem();
                     idClie=datClientes.getIdClie();
                    System.out.println(idClie);
                    tblFactura.setItems(transactionDAO.infoFactura(idClie));
                    txtBUC.clear();


                    //MODIFICAR
                    //Muestra la interfaz para modificar
                    VBEC.setVisible(false);
                    VBE.setVisible(false);
                    VBNC.setVisible(true);
                    lblTi.setText("Modificar cliente");

                    //Recupera los datos
                    datClientes recupera=transactionDAO.obtenerCliente(idClie);
                    txtNC.setText(recupera.getNomCompleto().toString());
                    txtDC.setText(recupera.getDomicilio().toString());
                    txtTC.setText(recupera.getTelefono().toString());
                    txtEC.setText(recupera.getEmail().toString());
                    txtLC.setText(String.valueOf(recupera.getLimCredito()));
                }
                else
                {
                    showMessage("Seleccione algún registro");

                }



            }
            if(event.getSource()==btnElC) {
                VBE.setVisible(false);
                VBNC.setVisible(false);
                VBEC.setVisible(true);
                lblTi.setText("Eliminar cliente");
                //Verifica que un elemento esté seleccionado
                if (tblClientes.getSelectionModel().getSelectedIndex() >= 0) {
                    Alert alert;
                    datClientes datClientes = tblClientes.getSelectionModel().getSelectedItem();
                    int aux = datClientes.getIdClie();
                    if(transactionDAO.deleteCL(aux))
                    {
                        alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Se eliminó con éxito");
                        alert.show();
                    }
                    else {
                        alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("No tiene los permisos para hacer esta operación");
                        alert.show();
                    }
                    reloadData();//Recarga los registros de la tabla

                }
                else
                {
                    showMessage("Seleccione algún registro");
                    muestraEdoCliente();
                }


            }
            if(event.getSource()==btnSeC){
                VBNC.setVisible(false);
                VBEC.setVisible(false);
                VBE.setVisible(true);
                if(tblClientes.getSelectionModel().getSelectedIndex()>=0) {
                    datClientes datClientes=tblClientes.getSelectionModel().getSelectedItem();
                     idC=datClientes.getIdClie();
                    System.out.println(idC);
                    tblFactura.setItems(transactionDAO.infoFactura(idC));
                    txtBUC.clear();

                    //Asigna etiquetas
                    lblNC.setText(datClientes.getNomCompleto());
                    lblC.setText(String.valueOf(transactionDAO.obtenerCliente(idC).getLimCredito()));
                    lblCC.setText(String.valueOf(transactionDAO.saldo(idC)));
                    }
                else
                {
                    showMessage("Seleccione algún registro");
                    muestraEdoCliente();
                }


                }

            if(event.getSource()==btnAC) {
                datClientes dtsClientes = new datClientes();
                dtsClientes.setIdClie(idClie);
                dtsClientes.setNomCompleto(txtNC.getText().toString());
                dtsClientes.setDomicilio(txtDC.getText().toString());
                dtsClientes.setEmail(txtEC.getText().toString());

                dtsClientes.setLimCredito(Double.valueOf(txtLC.getText()));
                dtsClientes.setTelefono(txtTC.getText().toString());

                    try {
                        transactionDAO.insClie(dtsClientes);
                    }
                     catch(Exception e){
                    e.printStackTrace();
                }

                limpieza();
                showMessage("Registro exitoso");
                reloadData();
                muestraEdoCliente();
                idClie=0;


            }
            if(event.getSource()==btnCC)
            {
                muestraEdoCliente();
            }
            if(event.getSource()==btnVD) {

                if (tblFactura.getSelectionModel().getSelectedIndex() >= 0) {
                    datFactura datFactura = tblFactura.getSelectionModel().getSelectedItem();
                    int nofact = datFactura.getNoFactura();
                    tblDetalles.setVisible(true);
                    tblDetalles.setItems(transactionDAO.detallFactira(nofact));


                } else
                    showMessage("Seleccione algún registro");

            }
            if (event.getSource()==btnVP)
            {
                if (tblFactura.getSelectionModel().getSelectedIndex() >= 0) {
                    datFactura datFactura = tblFactura.getSelectionModel().getSelectedItem();
                        int noFact=datFactura.getNoFactura();
                        tblPagos.setVisible(true);
                        tblPagos.setItems(transactionDAO.pagos(noFact));
                }
                else
                    showMessage("Seleccione algún registro");
            }
            if(event.getSource()==btnAF)
            {

                if (tblFactura.getSelectionModel().getSelectedIndex() >= 0) {
                    datFactura datFactura = tblFactura.getSelectionModel().getSelectedItem();
                    noF=datFactura.getNoFactura();
                    TextInputDialog dialog = new TextInputDialog("Cantidad a abonar");
                    dialog.setTitle("Abono");
                    dialog.setHeaderText("Cantidad a abonar");
                    dialog.setContentText("$");
                    dialog.initStyle(StageStyle.DECORATED);
                    datPagos datPagos=new datPagos();
                    datPagos.setNoFactura(noF);
                    System.out.println("noFact"+noF);
                    datPagos.setIdClie(idC);
                    System.out.println("idClie"+idC);
                    datPagos.setNumPago(transactionDAO.noPago(noF));
                    System.out.println("no Pago"+transactionDAO.noPago(noF));

// Traditional way to get the response value.
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        datPagos.setAbono(Double.valueOf(result.get()));
                        transactionDAO.insAbono(datPagos);

                        //recarga tabla pagos
                        tblPagos.getItems().clear();
                        tblPagos.setItems(transactionDAO.pagos(noF));
                        //recarga tabla facturas
                        tblFactura.getItems().clear();
                        tblFactura.setItems(transactionDAO.infoFactura(idC));
                    }
                }
                else
                    showMessage("Seleccione algún registro");




            }
        }

    };
    private void limpieza()
    {
        txtNC.clear();
        txtDC.clear();
        txtEC.clear();
        txtTC.clear();
        txtLC.clear();

    }
    private void closeStage( )
    {
        Stage stage = (Stage) BPL.getScene().getWindow();
        stage.close();
    }
    private void muestraEdoCliente (){
        VBNC.setVisible(false);
        VBE.setVisible(false);
        VBEC.setVisible(true);
        lblTi.setText("Estado de cuenta");
        lblNC.setText("Nombre");
        lblC.setText("0.00");
        lblCC.setText("0.00");
        reloadData();

    }

    public void showMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();

    }

    private void reloadData()
    {
        tblClientes.getItems().clear();
        tblClientes.setItems(transactionDAO.listClient(B));

    }
    /*private boolean saveTransaction(datClientes Datos)
    {


    }*/


}
