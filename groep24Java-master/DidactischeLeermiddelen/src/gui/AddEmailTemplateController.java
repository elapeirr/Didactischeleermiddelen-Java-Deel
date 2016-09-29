/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.EmailController;
import domein.Email;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elise Lapeirre
 */
public class AddEmailTemplateController implements Initializable {

    @FXML
    private TextArea txtContent;
    @FXML
    private TextField txfSubject;

    private EmailController ec;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    public AddEmailTemplateController() {
      ec = new EmailController();
      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmailTemplate.fxml"));
     
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
   

    @FXML
    private void addEmail(ActionEvent event) {
        
        String inhoud = txtContent.getText();
        String onderwerp = txfSubject.getText();
        
         Email em = new Email(onderwerp,inhoud);
        ec.addEmail(em);
        
        
  
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
      
        stage.close();
    }

    @FXML
    private void cancelEmail(ActionEvent event) {      final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
