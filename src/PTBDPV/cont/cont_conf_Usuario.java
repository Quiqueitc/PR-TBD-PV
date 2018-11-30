package PTBDPV.cont;

import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datEmpleados;
import PTBDPV.datos.datSucursal;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import PTBDPV.bd.MySQL;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class cont_conf_Usuario implements Initializable{
    @FXML
    JFXTextField txtNickName,txtNombre,txtDomicilio,txtTelefono,txtrfc,txtNombreS,txtDomicilioS,
            txtTelefonoS,txtCiudad;
    @FXML
    JFXComboBox<String> cbEncargado;
    @FXML
    JFXPasswordField txtPassword;
    @FXML
    JFXButton btnInicio,btnInicioS,btnRegresar,btnCerrar;
    @FXML
    JFXDatePicker dpfe;
    @FXML
    VBox vbCU,vbCS;
    @FXML
    AnchorPane APU,APS;
    String NN,NO,DO,TE,PA, RFC,NOS,DOS,TES,CS,EN;
    char TU='A';
    java.sql.Date fecContra;
    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnInicio.setOnAction(handler);
        btnInicioS.setOnAction(handler);
        btnRegresar.setOnAction(handler);
        btnCerrar.setOnAction(handler);
        cbEncargado.setItems(transactionDAO.LlenarEncargados('A'));
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Alert alert;
            if(event.getSource()==btnInicio)
            {
                datEmpleados datEmpleados=llenarDatos();
                System.out.println(" use  "+NN+ "      con "+ PA);
               if(NN.trim().length()>0 && PA.trim().length()>0)
               {
                   try {
                       if(transactionDAO.insEmpleado(datEmpleados))
                       {
                           cbEncargado.setItems(transactionDAO.LlenarEncargados('A'));
                           vbCU.setVisible(false);
                           vbCS.setVisible(true);
                           APU.setVisible(false);
                           APS.setVisible(true);
                       }
                       else {
                           alert=new Alert(Alert.AlertType.ERROR);
                           alert.setContentText("Error al insertar");
                           alert.show();
                       }
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
               else
                   {
                        alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("El usuario o contraseña están vacíos");
                        alert.show();
                   }
            }
            if(event.getSource()==btnInicioS)
            {
                datSucursal datSucursal=llenarDatosS();
                if(RFC.trim().length()>0 && EN.trim().length()>0)
                {
                    if(transactionDAO.insSucursal(datSucursal)){
                        alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Se registraron con éxito las configuraciones principales");
                        alert.show();
                            MySQL mySQL=new MySQL(NN,PA);
                        //MySQL =new SQL(NN,PA);
                        transactionDAO=new TransactionDAO(MySQL.getConnection());
                     
                       // Stage primaryStage = new Stage();
                        Stage stage =new Stage();
                        FXMLLoader loader;
                        Parent parent;
                        //cont_inicioL cont_inicioL=new cont_inicioL(transactionDAO.datEmpleado(userName));
                        // datComp datComp=new datComp(transactionDAO.datEmpleado(userName));
                        // cont_inicioL.setDatEmpleados(transactionDAO.datEmpleado(userName));
                        stage.setResizable(true);
                        stage.setMaximized(false);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        loader=new FXMLLoader(getClass().getResource("../fxml/login_Register_Layout.fxml"));
                        // loader.setController(cont_inicioL);
                        try {
                            parent= loader.load();
                            Scene scene=new Scene(parent);
                            stage.setScene(scene);
                            stage.show();
                            closeStage();
                            //System.exit(0);
                        }
                        catch (IOException et)
                        {
                            System.out.println(et);
                        }
                           /* Parent root = FXMLLoader.load(getClass().getResource("fxml/login_Register_Layout.fxml"));
                            //primaryStage.setTitle("Hello World");
                            primaryStage.setMaximized(false);
                            primaryStage.initStyle(TRANSPARENT);
                            primaryStage.setResizable(false);
                            primaryStage.setScene(new Scene(root));
                            primaryStage.show();*/
                    }
                    else {
                        alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Error al insertar datos");
                    }
                }
                else
                {
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("RFC y encargado están vacíos");
                    alert.show();
                }
            }
            if(event.getSource()==btnRegresar)
            {
                vbCU.setVisible(true);
                vbCS.setVisible(false);
                APU.setVisible(true);
                APS.setVisible(false);
            }
            if(event.getSource()==btnCerrar)
            {
                closeStage();
            }
        }
    };
    private datEmpleados llenarDatos()
    {
        datEmpleados datEmpleados=null;
        try {
            NN=txtNickName.getText().toString();
            NO=txtNombre.getText().toString();
            DO=txtDomicilio.getText().toString();
            TE=txtTelefono.getText().toString();
            PA=txtPassword.getText().toString();
            fecContra= java.sql.Date.valueOf(dpfe.getValue());
            datEmpleados=new datEmpleados(NN,NO,DO,TE,PA,"A",fecContra);
        }
        catch (NullPointerException e)
        {
            System.out.println(e);
        }
        return datEmpleados;
    }
    private datSucursal llenarDatosS()
    {
        datSucursal datSucursal=null;
        try {
            RFC=txtrfc.getText().toString();
            NOS=txtNombreS.getText().toString();
            DOS=txtDomicilioS.getText().toString();
            TES=txtTelefonoS.getText().toString();
            CS=txtCiudad.getText().toString();
            EN=cbEncargado.getValue();
            datSucursal=new datSucursal(RFC,NOS,DOS,TES,CS,EN);
        }
        catch (NullPointerException e)
        {
            System.out.println(e);
        }
        return datSucursal;
    }
    private void closeStage( )
    {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

}
