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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.io.IOException;
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
    @FXML
    TableView<datProductos> tblBP;
    boolean  keyCommand=false,keyW=false;
    int NF;
    String PM="V";
    String NE;
    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    datProductos productos;
    datComp datCom=new datComp();
    datEmpleados dEmpleadoA=datCom.dr();
    String [] datSucu;
    boolean vD;
    double da[]=new double[2];
    boolean PMay=false;
    private static boolean Señal;
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
                ARTcomun();
               //Abrir varios productos

            }
            //both are pressed


        });
        BPV.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER: // AGREGAR EL PRODUCTO
                            ENTER(txtCodigo.getText().toString());
                            break;
                        case INSERT:  //INSERTAR VARIOS PROC
                            INSvarios();
                            ACTdatos();
                            break;
                        case F10: //BUSCAR PROC
                            BuscarP();
                            break;
                        case F11: //mayoreo
                            PM();
                            break;
                        case ESCAPE: // CERRAR
                            //closeStage();
                            break;
                        case F7: //RE ENTRADAS
                            ENSA("Entrada","E");
                            break;
                        case F8: //RE SALIDAS
                            ENSA("Salida","S");
                            break;
                        case DELETE: // ELI UN ARTICULO P LA VENTA
                            try {
                                DELregistro();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            break;
                        case F12: //COBRAR
                            F12cobrar();
                            break;
                        case F5: //actualizar
                            ACTdatos();
                            break;
                    }
                }
        );
        tblBP.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                    datProductos datProductos=tblBP.getSelectionModel().getSelectedItem();
                    ENTER(datProductos.getCodigo());
                    tblBP.setVisible(false);
                }
            }
        });

        //txtTotal.setText();
        txtCodigo.requestFocus();
        //datTrans.Ggg();
       //txtCodigo.setText("");


    }
    public void GUI_REFRESH(){
        //datSu=datCom.getDatSucu();
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
                ENTER(txtCodigo.getText().toString());
                tblBP.setVisible(false);
            }
            if(event.getSource()==btnInsV)
            {
                INSvarios();
                ACTdatos();
                tblBP.setVisible(false);
            }
            if(event.getSource()==btnArtC){
                ARTcomun();
                tblBP.setVisible(false);
            }
            if(event.getSource()==btnBus){
                BuscarP();
            }
            if(event.getSource()==btnMay){
                PM();
                tblBP.setVisible(false);
            }
            if(event.getSource()==btnEnt){
                tblBP.setVisible(false);
                ENSA("Entrada","E");
            }
            if (event.getSource()==btnSal){
                tblBP.setVisible(false);
                ENSA("Salida","S");
            }
            if(event.getSource()==btnBor){
                tblBP.setVisible(false);
                try {
                    DELregistro();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(event.getSource()==btnCob){
                tblBP.setVisible(false);
                F12cobrar();
            }
            if (event.getSource()==btnVDD){
                tblBP.setVisible(false);
                VDD();
            }
        }
    };
    private void PM(){
        Alert alert;
        String men=null,tp;
        if(tblVen.getSelectionModel().getSelectedIndex()>=0) {
                datProductos datProductos=tblVen.getSelectionModel().getSelectedItem();
            tp=transactionDAO.desPRO(NF,datProductos.getNumDetalle());
            System.out.println("nf "+NF+" nd "+datProductos.getNumDetalle()+"   es p "+tp);
                switch (tp)
                {
                    case "V":
                        men="Se aplicó precio de mayoreo";
                        tp="M";
                        break;
                    case "M":
                        men="Se quitó precio de mayoreo";
                        tp="V";
                        break;
                    case "C":
                        men="Se aplicó precio de costo";
                        tp="V";
                        break;
                }
                try {
                    if(transactionDAO.actPPRO(datProductos.getCodigo(),NF,datProductos.getNumDetalle(),tp))
                    {
                        alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(men);
                        alert.show();
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
            ACTdatos();
            }
    }
    private void producto(datProductos datProductos){
        try {
            if (transactionDAO.insFaP(datProductos)){
                System.out.println("estoy pasando");
                ACTdatos();
            }
        } catch (SQLException e) {
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
        Stage stage =new Stage();
        FXMLLoader loader;
        Parent parent;
        stage.setResizable(true);
        cont_INSvarios cont_inSvarios=new cont_INSvarios(NF);
        //stage.setTitle("Tarjetas Extraviadas");
        stage.setMaximized(false);
        stage.setTitle("Varios");
        stage.initStyle(StageStyle.DECORATED);
        loader=new FXMLLoader(getClass().getResource("../fxml/INSvarios_Layout.fxml"));
        loader.setController(cont_inSvarios);
        try {
            parent= loader.load();
            Scene scene=new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException et)
        {
            System.out.println(et);
        }
        stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ACTdatos();
            }
        });

       // txtCodigo.requestFocus();
        //ACTdatos();
    }
    private void ENTER(String codigo){
        Alert alert;
        try {
            switch (transactionDAO.VER_PRO(codigo)){
                case 0:
                    //System.out.println("es 0");
                    PROpza(codigo);
                    System.out.println("es 0");
                    txtCodigo.clear();
                    break;
                case 1:
                    PROgranel(codigo);
                    txtCodigo.clear();
                    break;
                default:
                    alert=new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Producto no encontrado");
                    alert.show();
                    txtCodigo.clear();
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    private void  PROpza(String codigo){
        productos=new datProductos(codigo,1,NF,PM);
        producto(productos);
        txtCodigo.clear();
    }
    private void ARTcomun(){
        Stage stage =new Stage();
        FXMLLoader loader;
        Parent parent;
        stage.setResizable(true);
        cont_ARTcomunL contArTcomunL=new cont_ARTcomunL(NF);
        //stage.setTitle("Tarjetas Extraviadas");
        stage.setMaximized(false);
        stage.setTitle("Comun");
        stage.initStyle(StageStyle.DECORATED);
        loader=new FXMLLoader(getClass().getResource("../fxml/ARTcomun_Layout.fxml"));
        loader.setController(contArTcomunL);
        try {
            parent= loader.load();
            Scene scene=new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException et)
        {
            System.out.println(et);
        }
        stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ACTdatos();
            }
        });

       // txtCodigo.requestFocus();
      //  ACTdatos();
    }
    private void ACTdatos(){
        tblVen.setItems(transactionDAO.LlenarTblVen(NF));
        da=transactionDAO.totFactura(NF);
        txtTotal.setText(da[0]+"");
        lblNPV.setText(da[1]+"");
    }
    double cant=1.0;
    private void PROgranel(String codigo){
        String p[];
        p=transactionDAO.DPproducto(codigo);
        double precio=Double.parseDouble(p[1]);

        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("¿Cantidad de produto?");

        // Set the button types.

        ButtonType loginButtonType = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        VBox vBoG=new VBox(10);
        vBoG.setAlignment(Pos.CENTER);
        vBoG.setStyle("-fx-background-color: #D6EAF8;");
         Label label=new Label(p[0]);
        label.setStyle("-fx-background-color: #ADCAF2; -fx-font-size: 15pt; -fx-text-fill:#FFFFFF; -fx-font-weight:bold;");
        label.setPrefWidth(500);
        label.setAlignment(Pos.CENTER);
        Label labelP=new Label("Precio por unidad = "+precio);
        labelP.setStyle("-fx-font-size: 15pt; -fx-text-fill:#000000; -fx-font-weight:bold;");

       // vBoG.getChildren().addAll(label,labelP);

        HBox hBG=new HBox(5);

        VBox vBC =new VBox(2);
        Label labelT=new Label("Cantidad del producto");
        labelT.setStyle("-fx-font-size: 15pt; -fx-text-fill:#1B5CFF; -fx-font-weight:bold;");
        JFXTextField txtC = new JFXTextField("1.0");
        txtC.setStyle("-fx-font-size: 15pt; -fx-text-fill:#000000; -fx-font-weight:bold;");
        vBC.getChildren().addAll(labelT,txtC);

        VBox vBI=new VBox(2);
        Label labelI =new Label("Importe");
        labelI.setStyle("-fx-font-size: 15pt; -fx-text-fill:#1B5CFF; -fx-font-weight:bold;");
        JFXTextField txtI = new JFXTextField(precio+"");
        txtI.setEditable(false);
        txtI.setStyle("-fx-font-size: 15pt; -fx-text-fill:#000000; -fx-font-weight:bold;");
        vBI.getChildren().addAll(labelI,txtI);

        Label lblX=new Label("x");
        lblX.setStyle("-fx-font-size: 15pt; -fx-text-fill:#1B5CFF; -fx-font-weight:bold;");
        hBG.getChildren().addAll(vBC,lblX,vBI);
        vBoG.getChildren().addAll(label,labelP,hBG);
        txtC.setOnKeyReleased(
                event -> {
                    try{
                        cant=Double.parseDouble(txtC.getText().toString());
                    }
                    catch (Exception e){}
                    txtI.setText((precio*cant)+"");
                }
        );

        gridPane.add(vBoG, 0, 0);
      //  gridPane.add(new Label("To:"), 1, 0);
       // gridPane.add(to, 2, 0);

        dialog.getDialogPane().setContent(vBoG);

        // Request focus on the username field by default.
        Platform.runLater(() -> txtC.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(txtC.getText(), txtI.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            productos=new datProductos(codigo,cant,NF,PM);
            producto(productos);
            txtCodigo.clear();
            System.out.println("CANT=" + pair.getKey() + ", IMP=" + pair.getValue());
        });
    }
    private void BuscarP(){
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Buscar producto");

        // Set the button types.

        ButtonType loginButtonType = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        HBox hBG=new HBox(5);

        VBox vBC =new VBox(2);
        Label labelT=new Label("Nombre/Código producto");
        labelT.setStyle("-fx-font-size: 15pt; -fx-text-fill:#1B5CFF; -fx-font-weight:bold;");
        JFXTextField txtN = new JFXTextField();
        txtN.setStyle("-fx-font-size: 15pt; -fx-text-fill:#000000; -fx-font-weight:bold;");
        vBC.getChildren().addAll(labelT,txtN);

        txtN.setOnKeyPressed(
                event -> {
                    switch (event.getCode()){
                        case ENTER:     }

                }
        );

        dialog.getDialogPane().setContent(vBC);

        // Request focus on the username field by default.
        Platform.runLater(() -> txtN.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(txtN.getText(),"");
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            //productos=new datProductos(codigo,cant,NF,PM);
            //producto(productos);
            //txtCodigo.clear();

            if(pair.getKey().trim().length()>0)
            {
                String BU=pair.getKey().toString()+"%";
                tblBP.setItems(transactionDAO.LlenarTblBus(BU));
            }
            System.out.println("CANT=" + pair.getKey() + ", IMP=" + pair.getValue());
        });
        tblBP.setVisible(true);
    }
    private void ENSA(String ti,String Tmov){
        NE=dEmpleadoA.getIdUsu(); //Nombre del usuario
        datSucu=transactionDAO.datSucu();
        Stage stage =new Stage();
        FXMLLoader loader;
        System.out.println(datSucu[0]+ "rfc");
        Parent parent;
        stage.setResizable(true);
        cont_ENSAL contEnsal=new cont_ENSAL(NE,datSucu[0],Tmov);
        //stage.setTitle("Tarjetas Extraviadas");
        stage.setMaximized(false);
        stage.setTitle(ti);
        stage.initStyle(StageStyle.DECORATED);
        loader=new FXMLLoader(getClass().getResource("../fxml/ENSA_Layout.fxml"));
        loader.setController(contEnsal);
        try {
            parent= loader.load();
            Scene scene=new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException et)
        {
            System.out.println(et);
        }
        stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ACTdatos();
            }
        });

    }
    private void DELregistro() throws SQLException {
        Alert alert;
       // String men=null,tp;
        if(tblVen.getSelectionModel().getSelectedIndex()>=0) {
            datProductos datProductos=tblVen.getSelectionModel().getSelectedItem();
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar");
            alert.setHeaderText("Confirmar");
            alert.setContentText("¿Desea eliminar el producto "+datProductos.getCodigo()+" "+datProductos.getDescripcion() +" de la venta?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                if(transactionDAO.delPRO(datProductos.getCodigo(),datProductos.getPreMayoreo(),NF,datProductos.getNumDetalle()))
                {
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Se quitó producto de la lista");
                    alert.show();
                }
                ACTdatos();
            } else if (option.get() == ButtonType.CANCEL) {
            }
        }
    }
    private void F12cobrar(){
        double to;
        to=Double.parseDouble(txtTotal.getText().toString());
        if(to!=0.0){
            Stage stage =new Stage();
            FXMLLoader loader;
            //System.out.println(datSucu[0]+ "rfc");
            Parent parent;
            stage.setResizable(false);
            cont_F12cobrarL cont_f12cobrarL=new cont_F12cobrarL(NF);
            //stage.setTitle("Tarjetas Extraviadas");
            stage.setMaximized(false);
            stage.setTitle("Cobrar");
            stage.initStyle(StageStyle.DECORATED);
            loader=new FXMLLoader(getClass().getResource("../fxml/F12cobrar_Layout.fxml"));
            loader.setController(cont_f12cobrarL);
            try {
                parent= loader.load();
                Scene scene=new Scene(parent);
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException et)
            {

                System.out.println(et);
            }
            stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    vD=datCom.dS();
                    System.out.println("valor    "+ vD);
                    if(vD)
                    {
                        GUI_REFRESH();
                        tblVen.getSelectionModel().clearSelection();
                        txtCodigo.clear();
                        txtTotal.setText("0.0");
                        lblNPV.setText("0.0");
                        reloadData();
                    }
                }
            });
        }
    }
    public void VDD() //VENTAS DEL DÍA Y DEVOLUCIONES
    {
        Stage stage =new Stage();
        FXMLLoader loader;
        //System.out.println(datSucu[0]+ "rfc");
        Parent parent;
        stage.setResizable(false);
        cont_VDDL cont_vddl=new cont_VDDL(/*NF*/);
        //stage.setTitle("Tarjetas Extraviadas");
        stage.setMaximized(false);
        stage.setTitle("Ventas del día");
        stage.initStyle(StageStyle.DECORATED);
        loader=new FXMLLoader(getClass().getResource("../fxml/VDD_Layout.fxml"));
     //   loader.setController(cont_f12cobrarL);
        try {
            parent= loader.load();
            Scene scene=new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException et)
        {

            System.out.println(et);
        }
        stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                vD=datCom.dS();
                System.out.println("valor    "+ vD);
                if(vD)
                {
                    GUI_REFRESH();
                    tblVen.getSelectionModel().clearSelection();
                    txtCodigo.clear();
                    txtTotal.setText("0.0");
                    lblNPV.setText("0.0");
                    reloadData();
                }
            }
        });
    }
    private void reloadData()
    {
        tblVen.getItems().clear();
        tblVen.setItems(transactionDAO.LlenarTblVen(NF));

    }
}
