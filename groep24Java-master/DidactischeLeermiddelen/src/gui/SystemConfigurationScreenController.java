/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.SystemconfigurationController;
import domein.Email;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Elise Lapeirre
 */
public class SystemConfigurationScreenController extends StackPane implements Initializable {

    @FXML
    private TextField reservationperiod;
    @FXML
    private ChoiceBox<String> pickUp;
    @FXML
    private ChoiceBox<String> returnDay;
    @FXML
    private TextField maxLendable;
    @FXML
    private ComboBox<String> targetAudiences;
    @FXML
    private ComboBox<String> curriculars;
    @FXML
    private ComboBox<String> locations;
    @FXML
    private Button addNewEmail;
    @FXML
    private ComboBox<Email> emailTemplate;
    @FXML
    private Button btnSave;
    @FXML
    private Button cancel;

    private SystemconfigurationController sc;

    List<String> days = Arrays.asList("Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag");

    public SystemConfigurationScreenController(SystemconfigurationController sc) {
        this.sc = sc;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SystemConfigurationScreen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    private void addNewEmail(ActionEvent event) {
        Optional<Pair<String, String>> result = showAddMessageDialog();
        
        result.ifPresent(email -> {
            sc.addEmail(new Email(email.getKey(), email.getValue()));
            emailTemplate.getItems().setAll(sc.getEmails());
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reservationperiod.setText(String.valueOf(sc.getReservationperiod()));

        targetAudiences.getItems().setAll(sc.getTargetAudiences().stream().map(c -> c.getName()).collect(Collectors.toList()));
        curriculars.getItems().setAll(sc.getCurriculars().stream().map(c -> c.getName()).collect(Collectors.toList()));

        pickUp.getItems().setAll(days);
        pickUp.setValue(days.get(sc.getPickUpDay()));
        returnDay.getItems().setAll(days);
        returnDay.setValue(days.get(sc.getReturnDay()));

        locations.getItems().setAll(sc.getLocations());

        maxLendable.setText(String.valueOf(sc.getMaxProlongation()));
    }

    @FXML
    private void addTargetAudience(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nieuwe doelgroep");
        dialog.setHeaderText(null);
        dialog.setContentText("Doelgroep:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(targetAudience -> {
            sc.addTargetAudience(targetAudience);
            targetAudiences.getItems().add(targetAudience.trim());
        });
    }

    @FXML
    private void addCurricular(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nieuwe leergebied");
        dialog.setHeaderText(null);
        dialog.setContentText("Leergebied:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(curricular -> {
            sc.addCurricular(curricular);
            curriculars.getItems().add(curricular.trim());
        });
    }

    private Optional<Pair<String, String>> showAddMessageDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Nieuw e-mail sjabloon");

        ButtonType addButtonType = new ButtonType("Toevoegen", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField title = new TextField();
        TextArea message = new TextArea();

        grid.add(new Label("Titel:"), 0, 0);
        grid.add(title, 1, 0);
        grid.add(new Label("Bericht:"), 0, 1);
        grid.add(message, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Pair<>(title.getText(), message.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        
        return result;
    }

    @FXML
    private void save() {
        int reservationPeriod = Integer.valueOf(reservationperiod.getText());
        int pickup = days.indexOf(pickUp.getValue());
        int returnday = days.indexOf(returnDay.getValue());
        int maxLendable = Integer.valueOf(this.maxLendable.getText());
        sc.save(reservationPeriod, pickup, returnday, maxLendable);
    }

    @FXML
    private void addLocation(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nieuwe locatie");
        dialog.setHeaderText(null);
        dialog.setContentText("Locatie:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(location -> {
            sc.addLocation(location);
            locations.getItems().add(location.trim());
        });
    }
}
