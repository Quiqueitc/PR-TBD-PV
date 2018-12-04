package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datProductos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class cont_INSvarios implements Initializable {
    @FXML
    BorderPane BPVP;
    @FXML
    JFXTextField txtCodigo,txtCantidad;
    @FXML
    JFXButton btnEnter,btnCancelar;
    datProductos productos;
    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    int NF;
    String PM="V";
    double cant=1.0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnEnter.setOnAction(handler);
        btnCancelar.setOnAction(handler);
        BPVP.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ESCAPE: // Ir a siguiente txt
                            closeStage();
                            break;
                    }
                }
        );

        txtCodigo.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER: // Ir a siguiente txt
                            txtCantidad.requestFocus();
                            break;
                    }
                }
        );
        txtCantidad.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER: // Ir a siguiente txt
                            INS();
                            break;
                    }
                }
        );
    }
    public cont_INSvarios(int NF){
        this.NF=NF;
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnCancelar)
            {
                closeStage();
            }
            if(event.getSource()==btnEnter){
               INS();
            }
        }
    };
    private void producto(datProductos datProductos){
        try {
            if (transactionDAO.insFaP(datProductos)){
                closeStage();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println(e);
        }
    }
    private void ENTER(String codigo,double can){
        Alert alert;
        try {
            switch (transactionDAO.VER_PRO(codigo)){
                case 0:
                    if (can % 1 == 0) {
                        int c=(int)(can);
                        PROpza(codigo,c);
                    } else {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Este producto no se vende a granel");
                        alert.show();
                        closeStage();
                    }
                    txtCodigo.clear();
                    txtCantidad.clear();
                    break;
                case 1:
                    PROgranel(codigo,can);
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
        closeStage();
    }
    private void  PROpza(String codigo,int cant){
        productos=new datProductos(codigo,cant,NF,PM);
        producto(productos);
        txtCodigo.clear();
    }

    private void PROgranel(String codigo,double can){
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
        JFXTextField txtC = new JFXTextField(can+"");
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

    private void INS(){
        String codigo;
        double  cant;
        try{
            codigo=txtCodigo.getText().toString();
            cant=Double.parseDouble(txtCantidad.getText().toString());
            ENTER(codigo,cant);
           /* if(codigo.trim().length()>0 && cant>0)
            {
                productos=new datProductos(codigo,cant,NF,PM);
                producto(productos);
            }
           else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Este producto no se vende a granel");
                alert.show();
                closeStage();
            }*/
        }
        catch (Exception e){
            closeStage();
            System.out.println(e);
        }
    }
    private void closeStage( )
    {
        Stage stage = (Stage) BPVP.getScene().getWindow();
        stage.close();
    }
}
