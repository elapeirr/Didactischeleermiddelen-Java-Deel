/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.AdministratorController;
import domein.Administrator;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author UGent
 */
public class ManageAdministratorScreenController extends SplitPane implements Initializable {

    @FXML
    private TableView<Administrator> tblAdministrators;
    @FXML
    private TableColumn<Administrator, String> clmName;
    @FXML
    private TableColumn<Administrator, String> clmEmail;
    @FXML
    private TableColumn<Administrator, String> clmHeadAdmin;
    @FXML
    private TextField txfName;
    @FXML
    private TextField txfEmail;
    @FXML
    private PasswordField pwfPassword;
    @FXML
    private PasswordField pwfConfirmPassword;
    @FXML 
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnAddForReal;
    @FXML
    private Button btnRemove;
    @FXML
    private GridPane detailAdministrator;
    
    private AdministratorController ac;
    
    public ManageAdministratorScreenController(AdministratorController ac) {
        this.ac = ac;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageAdministratorScreen.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        detailAdministrator.setVisible(false);
        btnAddForReal.setVisible(false);
        btnRemove.setDisable(true);
        clmName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        clmEmail.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
        clmHeadAdmin.setCellValueFactory(cellData -> cellData.getValue().getHeadAdminProperty());
        
        tblAdministrators.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setAdministrator(newValue));
        
        setAdministrator(null);
        updateData();
    } 
    
    @FXML
    private void addAdministrator(ActionEvent event){
        setAdministrator(null);
        detailAdministrator.setVisible(true);
        btnAddForReal.setVisible(true);
        btnUpdate.setVisible(false);
    }
    @FXML
    private void rowClick(MouseEvent event){
        detailAdministrator.setVisible(true);
        btnUpdate.setVisible(true);
        btnAddForReal.setVisible(false);
        btnRemove.setDisable(false);
    }
//    @FXML
//    private void addMaterial(ActionEvent event){
//    
//    //nog code
//    }
//    
//    @FXML
//    private void removeMaterial(ActionEvent event){
//    //nog code 
//    }
//    
//    @FXML
//    private void addCurriculum(ActionEvent event){
//    //geen idee wrm deze hier moet?
//    }
//    @FXML 
//    private void addTargetAudience(ActionEvent event){
//    //geen idee...
//    }
//    @FXML
//    private void selectImage(ActionEvent event){
//    //geen idee , erft die over van mijn andere scherm fzo wtf
//    }
//    
//    @FXML
//    private void updateMaterial(ActionEvent event){
//    
//    //zzzzzZZZZZzz
//    }
    @FXML
    private void addForReal(ActionEvent event) {

        Administrator admin = new Administrator();
        if ((ac.findByMail(txfEmail.getText()))==null && checkPassword()){
                admin.setName(txfName.getText());
                admin.setEmail(txfEmail.getText());
                admin.setPassword(pwfPassword.getText());
                ac.addAdministrator(admin);
                setAdministrator(null);
                updateData();
                detailAdministrator.setVisible(false);
                btnAddForReal.setVisible(false);            
        }
        else if(!checkPassword()){
            
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Foutmelding");
                alert.setHeaderText(null);
                alert.setContentText("Paswoorden zijn niet hetzelfde.");
                alert.showAndWait();      
        }
        else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Foutmelding");
            alert.setHeaderText(null);
            alert.setContentText("Er bestaat al een beheerder met dit e-mailadres.");
            alert.showAndWait();
        }
        
        
        
    }
    
    @FXML
    private void updateAdministrator(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Beheerder bewerken");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je de beheerder wil aanpassen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Administrator admin = ac.getCurrentAdmin();
            
            admin.setName(txfName.getText());
            admin.setEmail(txfEmail.getText());
            admin.setPassword(pwfPassword.getText());
            ac.updateAdministrator(admin);
            setAdministrator(null);
            updateData();
            detailAdministrator.setVisible(false);
        }
    }
    
    @FXML
    private void removeAdministrator(ActionEvent event){
        if(ac.getCurrentAdmin().isHeadAdministrator()){
            Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Foutmelding");
                alert.setHeaderText(null);
                alert.setContentText("De hoofdbeheerder kan niet verwijderd worden.");
                alert.showAndWait();  
        }
        else{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Beheerder verwijderen");
            alert.setHeaderText(null);
            alert.setContentText("Ben je zeker dat je de beheerder wil verwijderen?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                ac.removeAdministrator(ac.getCurrentAdmin());
                setAdministrator(null);
                updateData();
            }
        }
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Beheerder bewerken");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je de wijzigingen niet wilt opslaan?");
         Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Administrator m = ac.getCurrentAdmin();
        setAdministrator(m);
        }
        detailAdministrator.setVisible(false);
       
    }
    
    public void setAdministrator(Administrator admin){
        
        ac.setCurrentAdmin(admin);
        if(admin !=null){  
            txfName.setText(admin.getName());
            txfEmail.setText(admin.getEmail());
            pwfPassword.setText(admin.getPassword());
            pwfConfirmPassword.setText(admin.getPassword()); 
        }
        else{
            txfName.setText("");
            txfEmail.setText("");
            pwfPassword.setText("");
            pwfConfirmPassword.setText(""); 
        }
    }
    
    public boolean checkPassword(){
        return pwfPassword.getText().equals(pwfConfirmPassword.getText());
    }
    
    public void updateData(){
        tblAdministrators.setItems(ac.getAdministrators());
        tblAdministrators.getColumns().get(0).setVisible(false);
        tblAdministrators.getColumns().get(0).setVisible(true);
    }
    
    
}
