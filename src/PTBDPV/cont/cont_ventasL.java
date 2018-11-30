package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class cont_ventasL implements Initializable {
    @FXML
    BorderPane BPV;
    @FXML
    JFXButton btnEnter,btnInsV,btnArtC,btnBus,btnMay,btnEnt,btnSal,btnBor,btnCob,btnVDD;
    @FXML
    Label lbltic,lblNPV;
    @FXML
    JFXTextField txtCodigo,txtTotal;
    @FXML
    TableView<datProductos> tblVen;
    boolean  keyCommand=false,keyW=false;
    int NF;
    String PM="V";
    String NE;
    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    datProductos productos;
    datComp datCom=new datComp();
    datEmpleados dEmpleadoA=datCom.dr();


   // datComp datComp=new datComp(cont_inicioL.getDatEmpleados());
   // datEmpleados datEmpleados;
    //cont_inicioL cont_inicioL;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnEnter.setOnAction(handler);
        btnInsV.setOnAction(handler);
        btnArtC.setOnAction(handler);
        btnBus.setOnAction(handler);
        btnMay.setOnAction(handler);
        btnEnt.setOnAction(handler);
        btnSal.setOnAction(handler);
        btnBor.setOnAction(handler);
        btnCob.setOnAction(handler);
        btnVDD.setOnAction(handler);
        GUI_REFRESH();
        BPV.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.CONTROL) {
                keyCommand = true;
            }
            else if(e.getCode() == KeyCode.V)
                keyW = true;
            if(keyCommand && keyW) //INSERTAR ARTICULO NO COMUN
            {

               //Abrir varios productos

            }
            //both are pressed


        });
        BPV.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER: // AGREGAR EL PRODUCTO

                            break;
                        case INSERT:  //INSERTAR VARIOS PROC
                            INSvarios();
                            break;
                        case F10: //BUSCAR PROC

                            break;
                        case F11: //mayoreo
                            PM();
                            break;
                        case ESCAPE: // CERRAR
                            //closeStage();
                            break;
                        case F7: //RE ENTRADAS

                            break;
                        case F8: //RE SALIDAS

                            break;
                        case DELETE: // ELI UN ARTICULO P LA VENTA

                            break;
                        case F12: //COBRAR

                            break;
                        case F5: //actualizar

                            break;
                    }
                }
        );
        //txtTotal.setText();
        txtCodigo.requestFocus();
        //datTrans.Ggg();
       //txtCodigo.setText("");


    }
    public void GUI_REFRESH(){
        NE=dEmpleadoA.getIdUsu(); //Nombre del usuario
        NF=transactionDAO.NoFactura();
        lbltic.setText("Ticket - "+NF);
        try {
            transactionDAO.insFaPr(NE);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnEnter)
            {

            }
            if(event.getSource()==btnInsV)
            {
                INSvarios();
            }
            if(event.getSource()==btnArtC){

            }
            if(event.getSource()==btnBus){

            }
            if(event.getSource()==btnMay){
                PM();
            }
            if(event.getSource()==btnEnt){

            }
            if (event.getSource()==btnSal){

            }
            if(event.getSource()==btnBor){

            }
            if(event.getSource()==btnCob){

            }
            if (event.getSource()==btnVDD){

            }
        }
    };
    private void PM(){
        PM="M";
    }
    private void producto(datProductos datProductos){
        try {
            if (transactionDAO.insFaP(datProductos)){
                    tblVen.setItems(transactionDAO.LlenarTblVen(NF));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println(e);
        }
    }
    private void agrfactura(){

    }
    private void closeStage( )
    {
        Stage stage = (Stage) BPV.getScene().getWindow();
        stage.close();
    }
    private void INSvarios(){
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Varios productos...");
        dialog.setHeaderText("V A R I O S");

// Set the icon (must be included in the project).
      //  dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

// Set the button types.
        ButtonType loginButtonType = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtCP = new TextField();
        txtCP.setPromptText("Código del producto");
        TextField txtCaP = new TextField();
        txtCaP.setPromptText("Cantidad");

        grid.add(new Label("Código:"), 0, 0);
        grid.add(txtCP, 1, 0);
        grid.add(new Label("Cantidad:"), 0, 1);
        grid.add(txtCaP, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        txtCP.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> txtCP.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(txtCP.getText(), txtCaP.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            productos=new datProductos(usernamePassword.getKey().toString(),Double.parseDouble(usernamePassword.getValue()),NF,PM);
            producto(productos);
            System.out.println("Codigo=" + usernamePassword.getKey() + ", cantidad=" + usernamePassword.getValue());
        });
    }
}
