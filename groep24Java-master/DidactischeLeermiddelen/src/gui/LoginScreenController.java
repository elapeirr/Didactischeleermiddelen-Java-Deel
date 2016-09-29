/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.AdministratorController;
import controller.LoginController;
import controller.MaterialController;
import controller.ReservationController;
import controller.SystemconfigurationController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Axel
 */
public class LoginScreenController extends AnchorPane implements Initializable {

    private LoginController lc;

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txfUsername;
    @FXML
    private PasswordField pwfPassword;

    public LoginScreenController(LoginController lc) {
        this.lc = lc;
  FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
   loader.setRoot(this);
       loader.setController(this);
try {
            loader.load();
       } catch (IOException ex) {
            throw new RuntimeException(ex);
       }
        
        
      

    }

    @FXML
    private void Login(ActionEvent event) throws IOException {        
   
        if(lc.loginCorrect(txfUsername.getText(), pwfPassword.getText())){
    Group root = new Group();
//        Scene scene = new Scene(root, 1200, 800);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        BorderPane mainPane = new BorderPane();

        Tab tabMaterial = new Tab();
        tabMaterial.setText("Materialen");
        tabMaterial.setContent(new MaterialsScreenController(new MaterialController()));
        tabPane.getTabs().add(tabMaterial);

        Tab tabReservation = new Tab();
        tabReservation.setText("Reservaties");
        tabReservation.setContent(new ReservatiesController(new ReservationController()));
        tabPane.getTabs().add(tabReservation);
        
        if(lc.isHeadAdministratror(txfUsername.getText())){
            Tab tabBeheerders = new Tab();
            tabBeheerders.setText("Beheerders");
            tabBeheerders.setContent(new ManageAdministratorScreenController(new AdministratorController()));
            tabPane.getTabs().add(tabBeheerders);
        }

        Tab tabSysteemconfig = new Tab();
        tabSysteemconfig.setText("Systeemconfiguratie");
        tabSysteemconfig.setContent(new SystemConfigurationScreenController(new SystemconfigurationController()));
        tabPane.getTabs().add(tabSysteemconfig);
        
        mainPane.setCenter(tabPane);

       // mainPane.prefHeightProperty().bind(scene.heightProperty());
        //mainPane.prefWidthProperty().bind(scene.widthProperty());

        root.getChildren().add(mainPane);
        
        Scene sc = new Scene(root);
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        st.hide();
        st.setScene(sc);
        st.show(); 
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login mislukt");
            alert.setHeaderText(null);
            alert.setContentText("Uw e-mail/paswoord is verkeerd.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
