package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datClientes;
import PTBDPV.datos.datEmpleados;
import PTBDPV.datos.datVentaXDepartamento;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import sun.util.resources.sr.LocaleNames_sr_Latn;

import javax.swing.text.TabableView;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class cont_corteL implements Initializable {
    @FXML
    BorderPane BPC;
    @FXML
    DatePicker dpDate;

    @FXML
    ComboBox <datEmpleados>CBusuario;

    @FXML
    JFXButton btnC,btnAcp;

    @FXML
    Label lblDIC,lblEfec,lblTG,lblVLD,lblTotPC,lblFec;
    @FXML
    Label lblPCl,lblPagoProv,lblVE,lblEn,lblPP,lblTotDC,lblVT,lblGT;

    @FXML
    TableView<datVentaXDepartamento> tblVentaD;

    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDateTime Date=LocalDateTime.now();
        lblFec.setText(String.valueOf(Date));
        lblDIC.setText(String.valueOf(transactionDAO.DinInCaj()));

        lblPCl.setText(String.valueOf(transactionDAO.totPagos()));
        lblEfec.setText(String.valueOf(transactionDAO.totEfectivo()));
        lblVE.setText(String.valueOf(transactionDAO.totEfectivo()));
        lblEn.setText(String.valueOf(transactionDAO.totEntradas()));
        lblVLD.setText(String.valueOf(transactionDAO.totVales()));
        lblTG.setText(String.valueOf(transactionDAO.totTarjetas()));
        lblPagoProv.setText(String.valueOf(transactionDAO.totPagoProve()));
        lblPP.setText(String.valueOf(transactionDAO.totPagoProve()));
        Double TotalPC=Double.valueOf(lblEfec.getText())+Double.valueOf(lblVLD.getText())+Double.valueOf(lblTG.getText());
        lblTotPC.setText(String.valueOf(TotalPC));
        Double TotalDC=Double.valueOf(lblVE.getText())+Double.valueOf(lblEn.getText())-Double.valueOf(lblPagoProv.getText());
        lblTotDC.setText(String.valueOf(TotalDC));
        Double totalVentas=TotalPC+transactionDAO.totPagos();
        lblVT.setText(String.valueOf(totalVentas));
        Double TotGan=transactionDAO.ganancia();
        System.out.println(TotGan);
        lblGT.setText(String.valueOf(TotGan));
        tblVentaD.setItems(transactionDAO.ventasXDep());
        }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==btnC)
            {
                dpDate.setVisible(true);
                btnAcp.setVisible(true);
                CBusuario.setVisible(true);
                //llenaCBUsuario();


            }
        }
    };
    private void closeStage( )
    {
        Stage stage = (Stage) BPC.getScene().getWindow();
        stage.close();
    }

    /*public void llenaCBUsuario() {
        CBusuario.setItems(transactionDAO.datEmpleados());
        //Convierte ComboBox toString a StringConverter
        CBusuario.setConverter(new StringConverter<datEmpleados>() {

            @Override
            public String toString(datEmpleados p) {
                return (p.getNombre());
            }

            @Override
            public datEmpleados fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }*/
}
