/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.MaterialController;
import controller.ReservationController;
import domein.Material;
import domein.Reservation;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.persistence.EntityNotFoundException;

/**
 * FXML Controller class
 *
 * @author Elise Lapeirre
 */
public class ReservatiesController extends AnchorPane implements Initializable {

    @FXML
    private TableColumn<Reservation, String> ontlener;
    @FXML
    private TableColumn<Reservation, String> materiaalID;
    @FXML
    private TableColumn<Reservation, String> materialName;
    @FXML
    private TableColumn<Reservation, Number> amount;
    @FXML
    private TableColumn<Reservation, String> conflict;
    @FXML
    private TableColumn<Reservation, String> conflictOntlener;
    @FXML
    private TableColumn<Reservation, String> returnConflict;
    @FXML
    TableColumn<Reservation, Number> amountConflict;
    @FXML
    private DatePicker ophaalmoment;
    @FXML
    private DatePicker indienmoment;
    @FXML
    private TextField aantalUitgeleend;
    @FXML
    private TextField aantalTerug;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Reservation> table;
    @FXML
    private ChoiceBox weeks;
    @FXML
    private Button btnSave;
    @FXML
    private Label conflictLabel;
    @FXML
    private TableView<Reservation> tableConflict;
    @FXML
    private TextField emailLoaner;
    @FXML
    private TextField materialNameNew;
    @FXML
    private Label uitErr;
    @FXML
    private Label terugErr;
    @FXML
    private Label datumErr;
    @FXML
    private Label naamErr;
    @FXML
    private GridPane addNewGrid;
    private ReservationController rc;
    private MaterialController mc = new MaterialController();
    @FXML
    private AnchorPane rechts;

    public ReservatiesController(ReservationController rc) {
        this.rc = rc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Reservaties.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        updateData();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rechts.setVisible(false);
        ontlener.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
        materialName.setCellValueFactory(cellData -> cellData.getValue().getMaterial().getNameProperty());
        amount.setCellValueFactory(cellData -> cellData.getValue().getAmountProperty());
        conflict.setCellValueFactory(cellData -> cellData.getValue().getConflictProperty());

        btnDelete.setDisable(true);
        //Weken in choicebox 
        List<String> weekjes = giveWeeks();
        weekjes.stream().forEach((weekje) -> {
            weeks.getItems().add(weekje);
        });

        Calendar cal;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        cal = new GregorianCalendar(year, month, day);

        int i = 0;
        while (cal.after(giveStartOfWeek(weekjes.get(i)))) {

            i++;
        }

        weeks.getSelectionModel().select(i - 1);
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setReservation(newValue));
        Calendar pff = giveStartOfWeek(weeks.getSelectionModel().getSelectedItem() + "");

        table.setItems(rc.getReservationsOfSelectedWeek(pff));

        weeks.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> setWeek(newValue + ""));
//        List<Reservation>resa=table.getItems();  
//        List<Reservation>c = new ArrayList<>();
//        for (Reservation res : resa) {
//            if(res.isConflict()){
//            c.add(res);
//            }
//        }
//      FXCollections.observableArrayList(c);
// conflictOntlener.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
// amountConflict.setCellValueFactory(cellData -> cellData.getValue().getAmountProperty());
// returnConflict.setCellValueFactory(cellData -> cellData.getValue().getEndDateProperty());
// 
//tableConflict.setItems(    FXCollections.observableArrayList(c));
        uitErr.setVisible(false);
        terugErr.setVisible(false);
        datumErr.setVisible(false);
        naamErr.setVisible(false);

    }

    @FXML
    void addReservation(ActionEvent event) {
//
//        try {
//
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addReservationScreen.fxml"));
//            Parent root1 = (Parent) fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root1));
//            stage.show();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
rechts.setVisible(true);
        btnSave.setVisible(false);
        conflictLabel.setVisible(false);
        tableConflict.setVisible(false);
        addNewGrid.setVisible(true);

        setReservation(null);

    }

    @FXML
    private void rowClick(MouseEvent event) {
        rechts.setVisible(true);
        addNewGrid.setVisible(false);
        btnSave.setVisible(true);
        btnDelete.setDisable(false);
        updateData();
    }

    @FXML
    void cancelNew(ActionEvent e) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nieuwe Reservatie");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je de wijzigingen niet wilt opslaan?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            addNewGrid.setVisible(false);
            btnSave.setVisible(true);
            //conflictLabel.setVisible(true);
            //tableConflict.setVisible(true);
            rechts.setVisible(false);
            uitErr.setVisible(false);
            terugErr.setVisible(false);
            naamErr.setVisible(false);
            datumErr.setVisible(false);
            setReservation(null);
        }

    }

    @FXML
    void saveNew(ActionEvent e) {
        boolean correcteInfo = true;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nieuwe Reservatie");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je de reservatie wilt toevoegen?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            Reservation r = new Reservation();
            try {
                r.setAmount(Integer.parseInt(aantalUitgeleend.getText()));
            } catch (NumberFormatException nfe) {
                uitErr.setText("Moet een getal zijn");
                uitErr.setVisible(true);
                correcteInfo = false;
            }
            
            try {
                r.setAmountReturned(Integer.parseInt(aantalTerug.getText()));
            } catch (NumberFormatException nf) {
                terugErr.setText("Moet een getal zijn");
                uitErr.setVisible(true);
                correcteInfo = false;
            }
            if (r.getAmountReturned() > r.getAmount()) {
                terugErr.setText("Groter dan aantal");
                uitErr.setVisible(true);
                correcteInfo = false;
            }

            r.setEmail(emailLoaner.getText());
            try {
                r.setMaterial(mc.findByName(materialNameNew.getText()));
                if (r.getAmount() > r.getMaterial().getAmount()) {
                    uitErr.setText("Zoveel items zijn er niet");
                    uitErr.setVisible(true);
                    correcteInfo = false;
                }
            } catch (EntityNotFoundException enf) {
                naamErr.setText("Ongeldig materiaal");
                naamErr.setVisible(true);
                correcteInfo = false;
            }
            LocalDate ophaal = ophaalmoment.getValue();
            Instant instant = ophaal.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date res = Date.from(instant);
            GregorianCalendar begin = new GregorianCalendar();
            begin.setTime(res);
            r.setStartDate(begin);

            LocalDate terug = indienmoment.getValue();
            Instant instand = terug.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date ult = Date.from(instand);
            GregorianCalendar einde = new GregorianCalendar();
            einde.setTime(ult);
            r.setEndDate(einde);
            if (einde.before(begin)) {
                datumErr.setVisible(true);
                correcteInfo = false;
            }

            List<Reservation> conflictTester = rc.getReservedOfMaterial(r.getMaterial());
            if (correcteInfo) {
                for (Reservation conflict : conflictTester) {
                    int available = conflict.getAmount() - conflict.getAmountReturned();
                    if (available < r.getAmount()) {
                        if (r.getStartDate().after(conflict.getStartDate())) {
                            r.setConflict(true);
                        }

                    } else if (r.getStartDate().before(conflict.getStartDate())) {
                        conflict.setConflict(true);
                    } else {

                        r.setConflict(true);

                    }

                }

                rc.addReservation(r);
                addNewGrid.setVisible(false);
                btnSave.setVisible(true);
            //conflictLabel.setVisible(true);
                //tableConflict.setVisible(true);
                rechts.setVisible(false);
                setReservation(null);
                updateData();
            }

        }
    }

    @FXML
    private void saveReservation(ActionEvent event) {

        //nog code 
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reservatie bewereken");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je de reservatie wilt aanpassen?");
        boolean correcteInfo = true;
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Reservation r = rc.getCurrentReservation();
            try {
                r.setAmount(Integer.parseInt(aantalUitgeleend.getText()));
            } catch (NumberFormatException nfe) {
                uitErr.setText("Moet een getal zijn");
                uitErr.setVisible(true);
                correcteInfo = false;
            }
            if (r.getAmount() > r.getMaterial().getAmount()) {
                uitErr.setText("Zoveel items zijn er niet");
                uitErr.setVisible(true);
                correcteInfo = false;
            }
            try {
                r.setAmountReturned(Integer.parseInt(aantalTerug.getText()));
            } catch (NumberFormatException nf) {
                terugErr.setText("Moet een getal zijn");
                uitErr.setVisible(true);
                correcteInfo = false;
            }
            if (r.getAmountReturned() > r.getAmount()) {
                terugErr.setText("Groter dan aantal");
                uitErr.setVisible(true);
                correcteInfo = false;
            }
            LocalDate ophaal = ophaalmoment.getValue();
            Instant instant = ophaal.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date res = Date.from(instant);
            GregorianCalendar begin = new GregorianCalendar();
            begin.setTime(res);
            r.setStartDate(begin);
            LocalDate terug = indienmoment.getValue();
            Instant instand = terug.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date ult = Date.from(instand);
            GregorianCalendar einde = new GregorianCalendar();
            einde.setTime(ult);
            r.setEndDate(einde);

            if (einde.before(begin)) {
                datumErr.setVisible(true);
                correcteInfo = false;
            }

            if (correcteInfo) {
                rc.updateReservation(r);
                setReservation(null);
                rechts.setVisible(false);
                updateData();
            }

        }
    }

    @FXML
    private void deleteReservation(ActionEvent event) {
        //nog code
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reservatie verwijderen");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je de reservatie wil verwijderen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            rc.removeReservation(rc.getCurrentReservation());

            setReservation(null);
            rechts.setVisible(false);
            updateData();
        }

    }

    private void updateData() {
        Calendar pff = giveStartOfWeek(weeks.getSelectionModel().getSelectedItem() + "");

        table.setItems(rc.getReservationsOfSelectedWeek(pff));

        table.getColumns().get(0).setVisible(false);
        table.getColumns().get(0).setVisible(true);

//          List<Reservation>resa=table.getItems();  
//        List<Reservation>c = new ArrayList<>();
//        for (Reservation res : resa) {
//            if(res.isConflict()){
//            c.add(res);
//            }
//        }
//     
//      tableConflict.setItems( FXCollections.observableArrayList(c));
//        table.refresh();
    }

    private void setReservation(Reservation r) {

        rc.setCurrentReservation(r);

        if (r != null) {
            Date date = r.getStartDate();
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDate res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            Date eind = r.getEndDate();
            Instant instantje = Instant.ofEpochMilli(eind.getTime());
            LocalDate einde = LocalDateTime.ofInstant(instantje, ZoneId.systemDefault()).toLocalDate();

            ophaalmoment.setValue(res);
            indienmoment.setValue(einde);
            aantalUitgeleend.setText(r.getAmount() + "");
            aantalTerug.setText(r.getAmountReturned() + "");

        } else {
            ophaalmoment.setValue(null);
            indienmoment.setValue(null);
            aantalUitgeleend.setText("");
            aantalTerug.setText("");
        }

    }

    private void setWeek(String w) {
        if (w != null) {
            Calendar pff = giveStartOfWeek(weeks.getSelectionModel().getSelectedItem() + "");

            table.setItems(rc.getReservationsOfSelectedWeek(pff));

        }

    }

    private List<String> giveWeeks() {

        List<String> weeks = new ArrayList<>();
        String dt = "2008-01-01";  // Start date
        String de = "2008-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        if (month < 8) {
            year = year - 1;
        }
        Calendar start = new GregorianCalendar(year, 7, 1);
        Calendar end = new GregorianCalendar(year + 1, 6, 31);
        while (start.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {

            start.add(Calendar.DAY_OF_WEEK, 1);
        }

        dt = sdf.format(start.getTime());
        Calendar hulp = start;
        hulp.add(Calendar.DAY_OF_MONTH, 7);
        int weekNr = 1;

        de = sdf.format(hulp.getTime());
        String weekje = "Week: " + weekNr + " van " + dt + " tot " + de;
        weeks.add(weekje);
        while (start.before(end)) {
            dt = sdf.format(start.getTime());

            hulp.add(Calendar.DAY_OF_MONTH, 7);

            de = sdf.format(hulp.getTime());
            weekNr++;
            weekje = "Week: " + weekNr + " van " + dt + " tot " + de;
            weeks.add(weekje);
            start = hulp;
        }
        return weeks;

    }

    private Calendar giveStartOfWeek(String line) {
        String start = "";
        String[] woorden = line.split(" ");
        start = woorden[3];
        String[] date = start.split("-");
        Calendar thisDay = new GregorianCalendar(Integer.parseInt(date[0]), (Integer.parseInt(date[1])) - 1, Integer.parseInt(date[2]));
        return thisDay;

    }

}
