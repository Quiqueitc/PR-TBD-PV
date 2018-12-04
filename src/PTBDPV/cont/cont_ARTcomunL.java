package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class cont_ARTcomunL implements Initializable {
    @FXML
    BorderPane BDAC;
    @FXML
    JFXTextField txtCantidad,txtPrecio;
    @FXML
    JFXButton btnEnter,btnCancelar;
    int NF;
    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnEnter.setOnAction(handler);
        btnCancelar.setOnAction(handler);
        BDAC.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ESCAPE: // Ir a siguiente txt
                            closeStage();
                            break;
                    }
                }
        );
        txtCantidad.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER: // Ir a siguiente txt
                            txtPrecio.requestFocus();
                            break;
                    }
                }
        );
        txtPrecio.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case ENTER: // Ir a siguiente txt
                            txtCantidad.requestFocus();
                            ARTcomun();
                            break;
                    }
                }
        );
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnCancelar)
            {
                closeStage();
            }
            if(event.getSource()==btnEnter)
            {
                ARTcomun();
            }
        }
    };
    private void closeStage( )
    {
        Stage stage = (Stage) BDAC.getScene().getWindow();
        stage.close();
    }
    public cont_ARTcomunL(int NF){
            this.NF=NF;
    }
    private void ARTcomun(){
        double cant,pre;
        try {
            cant=Double.parseDouble(txtCantidad.getText().toString());
            pre=Double.parseDouble(txtPrecio.getText().toString());
            if(transactionDAO.insARTcomun(cant,pre,NF))
            {
                closeStage();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
