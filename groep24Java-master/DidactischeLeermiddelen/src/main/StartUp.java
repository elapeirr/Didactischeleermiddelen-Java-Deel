/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controller.AdministratorController;
import controller.LoginController;
import controller.MaterialController;
import controller.ReservationController;
import controller.SystemconfigurationController;
import domein.PopulateDB;
import gui.LoginScreenController;
import gui.ManageAdministratorScreenController;
import gui.MaterialsScreenController;
import gui.ReservatiesController;
import gui.SystemConfigurationScreenController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Axel
 */
public class StartUp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
          new PopulateDB().run();
        
        primaryStage.setTitle("Didactische Leermiddelen");
        Group root = new Group();
        Scene scene = new Scene(new LoginScreenController(new LoginController()));

//        TabPane tabPane = new TabPane();
//        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
//        BorderPane mainPane = new BorderPane();
//
//        Tab tabMaterial = new Tab();
//        tabMaterial.setText("Materialen");
//        tabMaterial.setContent(new MaterialsScreenController(new MaterialController()));
//        tabPane.getTabs().add(tabMaterial);
//
//        Tab tabReservation = new Tab();
//        tabReservation.setText("Reservaties");
//        tabReservation.setContent(new ReservatiesController(new ReservationController()));
//        tabPane.getTabs().add(tabReservation);
//
//
//        Tab tabAdmins = new Tab();
//        tabAdmins.setText("Beheerders");
//        tabAdmins.setContent(new ManageAdministratorScreenController(new AdministratorController()));
//        tabPane.getTabs().add(tabAdmins);
//        
//        Tab tabSysteemconfig = new Tab();
//        tabSysteemconfig.setText("Systeemconfiguratie");
//        tabSysteemconfig.setContent(new SystemConfigurationScreenController(new SystemconfigurationController()));
//        tabPane.getTabs().add(tabSysteemconfig);
//
//        mainPane.setCenter(tabPane);
//
//        mainPane.prefHeightProperty().bind(scene.heightProperty());
//        mainPane.prefWidthProperty().bind(scene.widthProperty());

       // root.getChildren().add(mainPane);
       
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
