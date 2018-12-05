package PTBDPV.cont;

import PTBDPV.bd.MySQL;
import PTBDPV.bd.TransactionDAO;
import PTBDPV.datos.datDepartamento;
import PTBDPV.datos.datProductos;
import PTBDPV.datos.datPromociones;
import PTBDPV.datos.datProveedores;
import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class cont_productosL implements Initializable {
    @FXML
    BorderPane BPP;
    @FXML
    JFXButton btnNP,btnMP,btnEP,btnDE,btnPR,btnPO,btnSeC,btnAD,btnAP,btnGP,btnCP, btnID,btnIP,btnIPr;//btnID ainsertar dtp pro --> btnIP prom--> btnIPr
    @FXML
    JFXTextField txtBUC,txtCP,txtDP,txtPCP,txtPVP,txtPMP,txtIP,txtMP,txtND,txtNP,txtTP, txtDeP,txtCoP,txtNoP,txtCiP,txtCaP,txtPP;//txtND no depto prover --> txtNP txtTP prom --> txtCoP,txtNoP,txtCiP,txtCaP,txtPP
    @FXML
    JFXRadioButton rdUP,rdAG;
    @FXML
    JFXComboBox<String>cbDP,cbPP;
    @FXML
    JFXListView<String> lvProductos;
    @FXML
    VBox VBBP,VBAP,VBDE,VBPR,VBPO; //VBPO proveedor
    @FXML
    TableView<datDepartamento> tblDepartamento; //tabla depto
    @FXML
    TableView<datProveedores> tblProveedor; //tabla prove
    @FXML
    TableView<datPromociones> tblPromocion;
    @FXML
    Label lblTi,lblNP;
    boolean AC=false,AM=false;
    String Codig;
    datProductos datProductos;
    TransactionDAO transactionDAO=new TransactionDAO(MySQL.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnNP.setOnAction(handler);
        btnMP.setOnAction(handler);
        btnEP.setOnAction(handler);
        btnDE.setOnAction(handler);
        btnPR.setOnAction(handler);
        btnPO.setOnAction(handler);
        btnSeC.setOnAction(handler);
        btnAD.setOnAction(handler);
        btnAP.setOnAction(handler);
        btnGP.setOnAction(handler);
        btnCP.setOnAction(handler);
        btnID.setOnAction(handler);
        btnIP.setOnAction(handler);
        btnIPr.setOnAction(handler);
        REFRESH();
        txtBUC.setOnKeyReleased(
                event -> {
                    try{
                        lvProductos.setItems(transactionDAO.LlenarProductos(txtBUC.getText().toString()));
                    }
                    catch (Exception e){ System.out.println(e);}

                }
        );


    }
    public void REFRESH()
    {
        cbDP.setItems(transactionDAO.LlenarDepartamentos());
        cbPP.setItems(transactionDAO.LlenarProveedores());
    }
    EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Alert alert;
            if(event.getSource()==btnNP)
            {
                VBAP.setVisible(true);
                VBBP.setVisible(false);
                VBDE.setVisible(false);
                VBPO.setVisible(false);
                AM=false;
            }
            if (event.getSource()==btnMP)//AC con falso para entrar a editar
            {
                lblTi.setText("Modificar Producto");
                AC=false;
                VBBP.setVisible(true);
                VBDE.setVisible(false);
                VBPO.setVisible(false);
                VBAP.setVisible(false);

            }if (event.getSource()==btnEP){
                AC=true;
                lblTi.setText("Eliminar Producto");
                VBBP.setVisible(true);
                VBPO.setVisible(false);
                VBDE.setVisible(false);
                VBAP.setVisible(false);
            }
            if(event.getSource()==btnDE){ //DEPTO
                VBDE.setVisible(true);
                VBPO.setVisible(false);
                VBPR.setVisible(false);
                LlenarDepto();
            }
            if(event.getSource()==btnPR){ //PROMOCION
                VBPR.setVisible(true);
                VBDE.setVisible(false);
                VBPO.setVisible(false);
                LlenarPromo();
            }
            if(event.getSource()==btnPO){ //PROVEDOR
                VBDE.setVisible(false);
                VBPO.setVisible(true);
                VBPR.setVisible(false);
                LlenarProvee();

            }if(event.getSource()==btnSeC){
                if(lvProductos.getSelectionModel().getSelectedIndex()>0)
                {
                    Codig=lvProductos.getSelectionModel().getSelectedItem();
                    System.out.println("odigo  "+Codig);
                    if(AC) //Verdadero es eliminar producto
                    {
                            if(transactionDAO.eliPRO(Codig))
                            {
                                alert=new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Se eliminó con éxito el producto");
                                alert.show();
                            }
                    }
                    else //Falso es modificar produCto
                    {
                        datProductos=transactionDAO.dtProductos(Codig);
                        MP(datProductos);
                        lblNP.setText("Modificar Producto");
                        VBAP.setVisible(true);
                        VBBP.setVisible(false);
                        VBDE.setVisible(false);
                        VBPO.setVisible(false);
                        txtCP.setEditable(false);
                        AM=true;//para guardar datos correctamente con el mismo botón
                    }
                }
                else {
                    alert=new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Debe seleccionar un registro");
                    alert.show();
                }
            }
            if(event.getSource()==btnAD){
                VBDE.setVisible(true);
                VBPO.setVisible(false);
                LlenarDepto();
            }if(event.getSource()==btnAP){
                VBDE.setVisible(false);
                VBPO.setVisible(true);
                LlenarProvee();
            }
            if (event.getSource()==btnGP){
                if(AM) //Si es true es modificacion
                {
                    String CP,DP,UmP = null;
                    double PrC,PrV,PrM,CaP,CmP;
                    String DeP,PrP;
                    try {
                        PrC=Double.parseDouble(txtPCP.getText().toString());
                        PrV=Double.parseDouble(txtPVP.getText().toString());
                        PrM=Double.parseDouble(txtPMP.getText().toString());

                        CaP=Double.parseDouble(txtIP.getText().toString());
                        CmP=Double.parseDouble(txtMP.getText().toString());
                        CP=txtCP.getText().toString();
                        DP=txtDP.getText().toString();
                        if(rdAG.isSelected())
                            UmP="G";
                        else if (rdUP.isSelected())
                            UmP="U";
                        DeP=(cbDP.getValue().toString());
                        PrP=(cbPP.getValue().toString());
                        datProductos=new datProductos(CP,DP,PrC,PrV,PrM,CaP,CmP,0,UmP,DeP,PrP);
                        if (transactionDAO.insPRO(datProductos))
                        {
                            AM=false;
                            txtCP.setEditable(true);
                            lblNP.setText("Nuevo Producto");
                            CLEAR_GUI();
                        }
                    }
                    catch (NumberFormatException Ne){
                        alert=new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Los valores en precio, inventario o minimo NO son correctos");
                        alert.show();
                        System.out.println(Ne);
                    }
                    catch (NullPointerException Pe)
                    {
                        alert=new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Algunos campos están vacíos...");
                        alert.show();
                        System.out.println(Pe);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else //si es false es un nuevo registro
                    {
                        NP();
                    }
            }
            if(event.getSource()==btnCP){
                if(AM)
                {
                    lblNP.setText("Nuevo Producto");

                }
                else {
                    lblNP.setText("Modificar Producto");
                }
                CLEAR_GUI();
            }
            if(event.getSource()==btnID) //AGREGAR EL DEPTO A TABLA
            {
                String ND;
                ND=txtND.getText().toString();
                if(ND.trim().length()>0){
                    if(!ND.equalsIgnoreCase(transactionDAO.exiDepto(ND))){
                        if(transactionDAO.insDepartamento(ND))
                        {
                            alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Departamento agregado");
                            alert.show();
                            txtND.clear();
                            LlenarDepto();
                        }
                    }
                    else {
                        alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("El departamento ya existe, modifiquelo");
                        alert.show();
                    }
                }
                else {
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("El nombre de departamento NO puede estar vacío");
                    alert.show();
                }

            }
            if(event.getSource()==btnIP){
                String NP,DP,TP;
                NP=txtNP.getText().toString();
                DP=txtDeP.getText().toString();
                TP=txtTP.getText().toString();
                if(NP.trim().length()>0)
                {
                    if(!NP.equalsIgnoreCase(transactionDAO.exiProvee(NP)))
                    {
                        if(transactionDAO.insProveedores(NP,TP,DP)){
                            alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Proveedor agregado");
                            alert.show();
                            txtNP.clear();
                            txtDeP.clear();
                            txtTP.clear();
                            LlenarPromo();
                        }
                    }
                    else {
                        alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("El proveedor ya existe, modifiquelo");
                        alert.show();
                    }

                }
                else {
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("El nombre de proveedor NO puede estar vacío");
                    alert.show();
                }
            }
            if(event.getSource()==btnIPr){
                String CPr,NPr;
                double Cmin,Cmax,PrU;
                CPr=txtCoP.getText().toString();
                NPr=txtNoP.getText().toString();
                try{
                    Cmin=Double.parseDouble(txtCiP.getText().toString());
                    Cmax=Double.parseDouble(txtCaP.getText().toString());
                    PrU=Double.parseDouble(txtPP.getText().toString());
                    if(CPr.trim().length()>0 && NPr.trim().length()>0)
                    {
                        datPromociones datPromociones=new datPromociones(CPr,NPr,Cmin,Cmax,PrU);
                            if(transactionDAO.insPROMOCION(datPromociones))
                            {
                                txtCoP.clear();
                                txtNoP.clear();
                                txtCiP.clear();
                                txtCaP.clear();
                                txtPP.clear();
                                LlenarPromo();
                            }
                    }
                    else
                        {
                            alert=new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("El código y nombre de promoción NO puede estar vacío");
                            alert.show();
                        }
                }
                catch (Exception e){
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("El código y nombre de promoción NO puede estar vacío");
                    alert.show();
                }
            }
        }
    };
    private void LlenarDepto(){
        tblDepartamento.setItems(transactionDAO.LlenarTblDepto());
    }
    private void LlenarProvee(){
        tblProveedor.setItems(transactionDAO.LlenarTblProvee());
    }
    private void LlenarPromo(){
        tblPromocion.setItems(transactionDAO.LlenarTblPromo());
    }
    private void closeStage( )
    {
        Stage stage = (Stage) BPP.getScene().getWindow();
        stage.close();
    }
    private void NP(){
        Alert alert;
        String CP,DP,UmP = null;
        double PrC,PrV,PrM,CaP,CmP;
        String DeP,PrP;
        try {
                PrC=Double.parseDouble(txtPCP.getText().toString());
                PrV=Double.parseDouble(txtPVP.getText().toString());
                PrM=Double.parseDouble(txtPMP.getText().toString());

                CaP=Double.parseDouble(txtIP.getText().toString());
                CmP=Double.parseDouble(txtMP.getText().toString());
                CP=txtCP.getText().toString();
                DP=txtDP.getText().toString();
                if(rdAG.isSelected())
                    UmP="G";
                else if (rdUP.isSelected())
                    UmP="U";
                DeP=(cbDP.getValue().toString());
                PrP=(cbPP.getValue().toString());
                datProductos=new datProductos(CP,DP,PrC,PrV,PrM,CaP,CmP,0,UmP,DeP,PrP);
                if (transactionDAO.insPRO(datProductos))
                {
                    CLEAR_GUI();
                }
        }

        catch (NumberFormatException Ne){
            alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Los valores en precio, inventario o minimo NO son correctos");
            alert.show();
            System.out.println(Ne);
        }
        catch (NullPointerException Pe)
        {
            alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Algunos campos están vacíos...");
            alert.show();
            System.out.println(Pe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void MP(datProductos dP){
        txtCP.setText(dP.getCodigo());
        txtCP.setEditable(true); //RECORDAR CAMBIAR ESTE VALOR
        txtDP.setText(dP.getDescripcion());
        txtPCP.setText(dP.getPreCosto()+"");
        txtPVP.setText(dP.getPreVenta()+"");
        txtPMP.setText(dP.getPreMayoreo()+"");
        txtIP.setText(dP.getNoExistencia()+"");
        txtMP.setText(dP.getExiMinima()+"");
        cbDP.setValue(dP.getIdDepartamento()+"");//Checar dato
        cbPP.setValue(dP.getIdProv()+"");//checar
        if(dP.getIdUniMed().equalsIgnoreCase("G"))
            rdAG.setSelected(true);
        else
            rdUP.setSelected(true);
    }
        private void CLEAR_GUI(){
            txtCP.clear();
            txtDP.clear();
            txtPCP.clear();
            txtPVP.clear();
            txtPMP.clear();
            txtIP.clear();
            txtMP.clear();
            rdUP.setSelected(true);
            cbDP.getSelectionModel().clearSelection();
            cbPP.getSelectionModel().clearSelection();
        }
}
