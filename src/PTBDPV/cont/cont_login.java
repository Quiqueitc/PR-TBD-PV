package PTBDPV.cont;

import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datComp;
import PTBDPV.datos.datEmpleados;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import PTBDPV.bd.MySQL;

import static javafx.stage.StageStyle.TRANSPARENT;


public class cont_login implements Initializable {
    @FXML
    JFXButton btnInicio,btnSAL;
    @FXML
    JFXTextField JtxUsuario;
    @FXML
    JFXPasswordField JtxPas;
    String userName,passUser;
    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnInicio.setOnAction(handler);
        btnSAL.setOnAction(handler);
        JtxUsuario.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER:
                            JtxPas.requestFocus();
                            break;
                    }
                }
        );
        JtxPas.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER:
                            loginUs();
                        break;
                    }
                }
        );
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnInicio)
            {
                loginUs();
            }
            if(event.getSource()==btnSAL){
                closeStage();
                System.exit(0);
            }
        }
    };
    private void loginUs(){
        Alert alert;
        userName=JtxUsuario.getText().toString();
        passUser=JtxPas.getText().toString();
        if(userName.trim().length()>0 && passUser.trim().length()>0)
        {
            datEmpleados empleados=transactionDAO.datEmpleadoTT(userName,passUser);
            if(empleados!=null)
            {
                if(empleados.getIdTip().equalsIgnoreCase("A")) //es administrador
                {
                    MySQL mySQL=new MySQL("administrador","quique");
                    mySQL.conn=null;
                    transactionDAO=new TransactionDAO(MySQL.getConnection());
                    if(MySQL.getConnection()!=null){
                        Stage stage =new Stage();
                        FXMLLoader loader;
                        Parent parent;
                        cont_inicioL cont_inicioL=new cont_inicioL(transactionDAO.datEmpleado(userName));
                        datComp datComp=new datComp(transactionDAO.datEmpleado(userName));
                        // cont_inicioL.setDatEmpleados(transactionDAO.datEmpleado(userName));
                        stage.setResizable(true);
                        stage.setMaximized(true);
                        stage.initStyle(StageStyle.DECORATED);
                        loader=new FXMLLoader(getClass().getResource("../fxml/inicio_Layout.fxml"));
                        loader.setController(cont_inicioL);
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
                            alert=new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Usuario o contraseña incorrectos");
                            alert.show();
                        }
                    }
                    else{
                        alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Usuario o contraseña incorrectos");
                        alert.show();
                    }
                }
                else //es cajero
                {
                    MySQL mySQL=new MySQL("cajero","caj");
                    mySQL.conn=null;
                    transactionDAO=new TransactionDAO(MySQL.getConnection());
                    if(MySQL.getConnection()!=null){
                        Stage stage =new Stage();
                        FXMLLoader loader;
                        Parent parent;
                        cont_inicioL cont_inicioL=new cont_inicioL(transactionDAO.datEmpleado(userName));
                        datComp datComp=new datComp(transactionDAO.datEmpleado(userName));
                        // cont_inicioL.setDatEmpleados(transactionDAO.datEmpleado(userName));
                        stage.setResizable(true);
                        stage.setMaximized(true);
                        stage.initStyle(StageStyle.DECORATED);
                        loader=new FXMLLoader(getClass().getResource("../fxml/inicio_Layout.fxml"));
                        loader.setController(cont_inicioL);
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
                            alert=new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Usuario o contraseña incorrectos");
                            alert.show();
                        }
                    }
                    else{
                        alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Usuario o contraseña incorrectos");
                        alert.show();
                    }
                }
            }
            else //empleado no existente
                {
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Usuario o contraseña incorrectos");
                    alert.show();
            }
        }
        else {
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("¡Valores erroneos o vacíos!");
            alert.show();
        }
    }
   /* private void closeStage( )
    {
        Alert alert;
        alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("Confirmar");
        alert.setContentText("¿Desea salir?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            System.exit(0);
        } else if (option.get() == ButtonType.CANCEL) {
        }

    }*/

    private void closeStage( )
    {
        Stage stage = (Stage) btnInicio.getScene().getWindow();
        stage.close();
    }

}
