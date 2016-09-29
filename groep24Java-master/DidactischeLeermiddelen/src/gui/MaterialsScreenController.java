/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Material;
import controller.MaterialController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javax.persistence.EntityNotFoundException;
import org.controlsfx.control.CheckComboBox;

/**
 * FXML Controller class
 *
 * @author Axel
 */
public class MaterialsScreenController extends SplitPane implements Initializable {

    @FXML
    private TableView<Material> tblMaterials;
    @FXML
    private TableColumn<Material, ImageView> clmImage;
    @FXML
    private TableColumn<Material, String> clmName;
    @FXML
    private TableColumn<Material, String> clmDescription;
    @FXML
    private ImageView imvPicture;
    @FXML
    private Button btnImage;
    @FXML
    private TextField txfName;
    @FXML
    private TextArea txaDescription;
    @FXML
    private Button btnAddCurriculum;
    @FXML
    private CheckComboBox<String> cmbCurricular;
    @FXML
    private Button btnAddTargetAudience;
    @FXML
    private CheckComboBox<String> cmbTargetAudience;
    @FXML
    private ComboBox<String> cmbFirm;
    @FXML
    private TextField txfItemNumber;
    @FXML
    private TextField txfPrice;
    @FXML
    private TextField txfAmount;
    @FXML
    private TextField txfAmountUnavailable;
    @FXML
    private TextField txfLocation;
    @FXML
    private CheckBox cbxLendable;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnUpdate;
    @FXML
    private GridPane detailMaterial;
    @FXML
    private Button btnAddForReal;
    @FXML
    private TextField filter;
    @FXML
    private Label nameErr;
    @FXML
    private Label leerErr;
    @FXML
    private Label doelErr;
    @FXML
    private Label prijsErr;
    @FXML
    private Label aantalErr;
    @FXML
    private Label aantalOnErr;
    @FXML
    private TextField txfFirmMail;

    private MaterialController mc;
    private Alert alert;

    public MaterialsScreenController(MaterialController mc) {
        this.mc = mc;
        alert = new Alert(AlertType.INFORMATION);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MaterialsScreen.fxml"));
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
        filter.textProperty().addListener((observable, oldValue, newValue) -> setFilter(newValue));

        detailMaterial.setVisible(false);
        btnAddForReal.setVisible(false);
        btnRemove.setDisable(true);
        clmImage.setCellValueFactory(cellData -> new SimpleObjectProperty<>(getTableImageView(cellData.getValue().getImagepath())));
        clmName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        clmDescription.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());

        tblMaterials.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setMaterial(newValue));

        cmbCurricular.getItems().addAll(mc.getCurriculars().stream().map(c -> c.getName()).collect(Collectors.toList()));
        cmbTargetAudience.getItems().addAll(mc.getTargetAudiences().stream().map(c -> c.getName()).collect(Collectors.toList()));
        cmbFirm.setItems(mc.getFirms());

        setMaterial(null);
        nameErr.setVisible(false);
        leerErr.setVisible(false);
        doelErr.setVisible(false);
        prijsErr.setVisible(false);
        aantalErr.setVisible(false);
        aantalOnErr.setVisible(false);
    }

    public ImageView getTableImageView(String imagePath) {
        ImageView imvPicture = new ImageView();
        imvPicture.setFitWidth(100);
        imvPicture.setFitHeight(100);
        if (imagePath != null) {
            imvPicture.setImage(new Image(imagePath));
        }
        return imvPicture;
    }

    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toURL().toString());
                imvPicture.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*FileChooser fc = new FileChooser();
         fc.setTitle("Selecteer afbeelding");
         fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
         File file = fc.showOpenDialog(null);*/
    }

    @FXML
    private void addCurriculum(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nieuwe leergebied");
        dialog.setHeaderText(null);
        dialog.setContentText("Leergebied:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(curricular -> {
            cmbCurricular.getItems().add(curricular.trim());
            cmbCurricular.getCheckModel().check(curricular.trim());
        });
    }

    @FXML
    private void addTargetAudience(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nieuwe doelgroep");
        dialog.setHeaderText(null);
        dialog.setContentText("Doelgroep:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(targetAudience -> {
            cmbTargetAudience.getItems().add(targetAudience.trim());
            cmbTargetAudience.getCheckModel().check(targetAudience.trim());
        });
    }

    public void setMaterial(Material material) {
        mc.setCurrentMaterial(material);
        nameErr.setVisible(false);
        leerErr.setVisible(false);
        doelErr.setVisible(false);
        prijsErr.setVisible(false);
        aantalErr.setVisible(false);
        aantalOnErr.setVisible(false);
        if (material != null) {
            if (material.getImagepath() == null) {
                imvPicture.setImage(null);
            } else {
                imvPicture.setImage(new Image(material.getImagepath()));
            }
            txfName.setText(material.getName());
            txaDescription.setText(material.getDescription());
            cmbCurricular.getCheckModel().clearChecks();
            material.getCurriculars().stream().map(c -> c.getName()).forEach((curricular) -> {
                cmbCurricular.getCheckModel().check(curricular);
            });
            cmbTargetAudience.getCheckModel().clearChecks();
            material.getTargetAudiences().stream().map(c -> c.getName()).forEach((targetAudience) -> {
                cmbTargetAudience.getCheckModel().check(targetAudience);
            });
            cmbFirm.setValue(material.getFirm());
            txfItemNumber.setText(material.getItemNumber());
            txfPrice.setText(String.valueOf(material.getPrice()));
            txfLocation.setText(material.getLocation());
            txfAmount.setText(String.valueOf(material.getAmount()));
            txfAmountUnavailable.setText(String.valueOf(material.getBroken()));
            cbxLendable.setSelected(material.getLendable());
            txfFirmMail.setText(material.getEmailContact());
        } else {
            imvPicture.setImage(null);
            txfName.setText("");
            txaDescription.setText("");
            cmbCurricular.getCheckModel().clearChecks();
            cmbTargetAudience.getCheckModel().clearChecks();
            cmbFirm.setValue(null);
            txfItemNumber.setText("");
            txfPrice.setText("");
            txfAmount.setText("");
            txfAmountUnavailable.setText("");
            txfLocation.setText("");
            cbxLendable.setSelected(false);
        }
    }

    @FXML
    private void rowClick(MouseEvent event) {
        btnUpdate.setVisible(true);
        detailMaterial.setVisible(true);
        btnAddForReal.setVisible(false);
        btnRemove.setDisable(false);
    }

    @FXML
    private void cancel(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Materiaal bewerken");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je de wijzigingen niet wilt opslaan?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Material m = mc.getCurrentMaterial();
            setMaterial(m);
            nameErr.setVisible(false);
            leerErr.setVisible(false);
            doelErr.setVisible(false);
            prijsErr.setVisible(false);
            aantalErr.setVisible(false);
            aantalOnErr.setVisible(false);
        }
        // detailMaterial.setVisible(false);
    }

    @FXML
    private void updateMaterial(ActionEvent event) {
        alert.setTitle("Materiaal bewereken");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je het materiaal wil aanpassen?");
        nameErr.setVisible(false);
        boolean errname = true;
        leerErr.setVisible(false);
        boolean errleer = true;
        doelErr.setVisible(false);
        boolean errdoel = true;
        prijsErr.setVisible(false);
        boolean errprijs = true;
        aantalErr.setVisible(false);
        boolean erraantal = true;
        aantalOnErr.setVisible(false);
        boolean erraantalOn = true;
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Material material = mc.getCurrentMaterial();
            if (imvPicture.getImage() != null) {
                material.setImagepath(imvPicture.getImage().impl_getUrl());
            }
            if (!material.getName().equals(txfName.getText())) {
                try {
                    mc.findBy(txfName.getText());
                    nameErr.setText("Er bestaat reeds een materiaal met deze naam");
                    nameErr.setVisible(true);
                    errname = false;
                } catch (EntityNotFoundException e) {
                    material.setName(txfName.getText());
                }
            }
            if (txfName.getText().trim().equals("")) {
                nameErr.setText("Gelieve een naam in te geven.");
                nameErr.setVisible(true);
                errname = false;
            }
            material.setDescription(txaDescription.getText());
            cmbCurricular.getCheckModel().getCheckedItems().stream().forEach((curricular) -> {
                material.addCurricular(mc.findCurricular(curricular));
            });
            if (cmbCurricular.getCheckModel().getCheckedItems().isEmpty()) {
                leerErr.setText("Selecteer een leergebied");
                leerErr.setVisible(true);
                errleer = false;
            }
            cmbTargetAudience.getCheckModel().getCheckedItems().stream().forEach((targetAudience) -> {
                material.addTargetAudience(mc.findTargetAudience(targetAudience));
            });
            if (cmbTargetAudience.getCheckModel().getCheckedItems().isEmpty()) {
                doelErr.setText("Selecteer een doelgroep");
                doelErr.setVisible(true);
                errdoel = false;
            }
            material.setFirm(cmbFirm.getEditor().getText());
            material.setFirmMail(txfFirmMail.getText());
            material.setItemNumber(txfItemNumber.getText());

            try {
                material.setPrice(Double.parseDouble(txfPrice.getText()));
                if (Double.parseDouble(txfPrice.getText()) < 0) {
                    prijsErr.setText("Prijs kan niet negatief zijn");
                    prijsErr.setVisible(true);
                    errprijs = false;
                }
            } catch (NumberFormatException e) {
                prijsErr.setText("Prijs moet een getal zijn.");
                prijsErr.setVisible(true);
                errprijs = false;
            }

            try {
                material.setAmount(Integer.parseInt(txfAmount.getText()));
                if (Integer.parseInt(txfAmount.getText()) < 1) {
                    aantalErr.setText("Aantal moet groter dan nul zijn.");
                    aantalErr.setVisible(true);
                    erraantal = false;
                }
            } catch (NumberFormatException e) {
                aantalErr.setText("Aantal moet een getal zijn.");
                aantalErr.setVisible(true);
                erraantal = false;
            }

            try {

                material.setBroken(Integer.parseInt(txfAmountUnavailable.getText()));
                if (Integer.parseInt(txfAmountUnavailable.getText()) > Integer.parseInt(txfAmount.getText())) {
                    aantalOnErr.setText("Aantal onbeschikbaar moet kleiner zijn dan aantal");
                    aantalOnErr.setVisible(true);
                    erraantalOn = false;
                } else if (Integer.parseInt(txfAmountUnavailable.getText()) < 0) {
                    aantalOnErr.setText("Aantal mag niet negatief zijn.");
                    aantalOnErr.setVisible(true);
                    erraantalOn = false;
                }

            } catch (NumberFormatException e) {
                aantalOnErr.setText("Aantal moet een getal zijn.");
                aantalOnErr.setVisible(true);
                erraantalOn = false;
            }
            material.setLocation(txfLocation.getText());
            material.setLendable(cbxLendable.isSelected());
            if (erraantal && erraantalOn && errdoel && errleer && errname && errprijs) {
                mc.updateMaterial(material);
                setMaterial(null);
                updateData();
                detailMaterial.setVisible(false);
            }
        }
    }

    @FXML
    private void removeMaterial(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Materiaal verwijderen");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je het materiaal wil verwijderen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            mc.removeMaterial(mc.getCurrentMaterial());
            setMaterial(null);
            updateData();
        }
    }

    @FXML
    private void addMaterial(ActionEvent event) {
        setMaterial(null);
        btnAddForReal.setVisible(true);
        detailMaterial.setVisible(true);

//     
//        Material material = new Material();
//        if (material.getImagepath() == null) {
//            imvPicture.setImage(null);
//        } else {
//            imvPicture.setImage(new Image(material.getImagepath()));
//        }
//        material.setName(txfName.getText());
//        material.setDescription(txaDescription.getText());
//        cmbCurricular.getCheckModel().getCheckedItems().stream().forEach((curricular) -> {
//            material.addCurricular(mc.findCurricular(curricular));
//        });
//        cmbTargetAudience.getCheckModel().getCheckedItems().stream().forEach((targetAudience) -> {
//            material.addTargetAudience(mc.findTargetAudience(targetAudience));
//        });
//        material.setFirm(cmbFirm.getEditor().getText());
//        material.setItemNumber(txfItemNumber.getText());
//        if (txfPrice.getText().isEmpty()) {
//            material.setPrice(0);
//        } else {
//            material.setPrice(Double.parseDouble(txfPrice.getText()));
//        }
//
//        if (txfAmount.getText().isEmpty()) {
//            material.setPrice(0);
//        } else {
//            material.setAmount(Integer.parseInt(txfAmount.getText()));
//        }
//
//        if (txfAmountUnavailable.getText().isEmpty()) {
//            material.setPrice(0);
//        } else {
//            material.setBroken(Integer.parseInt(txfAmountUnavailable.getText()));
//        }
//
//        material.setLocation(txfLocation.getText());
//        material.setLendable(cbxLendable.isSelected());
//        mc.addMaterial(material);
//        setMaterial(null);
//        updateData();
    }

    @FXML
    private void addForReal(ActionEvent event) {
        alert = null;
        nameErr.setVisible(false);
        boolean errname = true;
        leerErr.setVisible(false);
        boolean errleer = true;
        doelErr.setVisible(false);
        boolean errdoel = true;
        prijsErr.setVisible(false);
        boolean errprijs = true;
        aantalErr.setVisible(false);
        boolean erraantal = true;
        aantalOnErr.setVisible(false);
        boolean erraantalOn = true;
        Material material = new Material();
        if (material.getImagepath() == null) {
            imvPicture.setImage(null);
        } else {
            imvPicture.setImage(new Image(material.getImagepath()));
        }
        try {
            mc.findBy(txfName.getText());
            nameErr.setText("Er bestaat reeds een materiaal met deze naam");
            nameErr.setVisible(true);
            errname = false;
        } catch (EntityNotFoundException e) {
            material.setName(txfName.getText());
        }
        if (txfName.getText().trim().equals("")) {
            nameErr.setText("Gelieve een naam in te geven.");
            nameErr.setVisible(true);
            errname = false;
        }
        material.setDescription(txaDescription.getText());
        if (cmbCurricular.getCheckModel().getCheckedItems().isEmpty()) {
            leerErr.setText("Selecteer een leergebied");
            leerErr.setVisible(true);
            errleer = false;
        }
        cmbTargetAudience.getCheckModel().getCheckedItems().stream().forEach((targetAudience) -> {
            material.addTargetAudience(mc.findTargetAudience(targetAudience));
        });
        if (cmbTargetAudience.getCheckModel().getCheckedItems().isEmpty()) {
            doelErr.setText("Selecteer een doelgroep");
            doelErr.setVisible(true);
            errdoel = false;
        }
        material.setFirm(cmbFirm.getEditor().getText());
        material.setFirmMail(txfFirmMail.getText());
        material.setItemNumber(txfItemNumber.getText());
        if (txfPrice.getText().isEmpty()) {
            material.setPrice(0);
        } else {
            try {
                material.setPrice(Double.parseDouble(txfPrice.getText()));
                if (Double.parseDouble(txfPrice.getText()) < 0) {
                    prijsErr.setText("Prijs kan niet negatief zijn");
                    prijsErr.setVisible(true);
                    errprijs = false;
                }
            } catch (NumberFormatException e) {
                prijsErr.setText("Prijs moet een getal zijn.");
                prijsErr.setVisible(true);
                errprijs = false;
            }
        }

        try {
            material.setAmount(Integer.parseInt(txfAmount.getText()));
            if (Integer.parseInt(txfAmount.getText()) < 1) {
                aantalErr.setText("Aantal moet groter dan nul zijn.");
                aantalErr.setVisible(true);
                erraantal = false;
            }
        } catch (NumberFormatException e) {
            aantalErr.setText("Aantal moet een getal zijn.");
            aantalErr.setVisible(true);
            erraantal = false;
        }

        try {

            material.setBroken(Integer.parseInt(txfAmountUnavailable.getText()));
            if (Integer.parseInt(txfAmountUnavailable.getText()) > Integer.parseInt(txfAmount.getText())) {
                aantalOnErr.setText("Aantal onbeschikbaar moet kleiner zijn dan aantal");
                aantalOnErr.setVisible(true);
                erraantalOn = false;
            } else if (Integer.parseInt(txfAmountUnavailable.getText()) < 0) {
                aantalOnErr.setText("Aantal mag niet negatief zijn.");
                aantalOnErr.setVisible(true);
                erraantalOn = false;
            }

        } catch (NumberFormatException e) {
            aantalOnErr.setText("Aantal moet een getal zijn.");
            aantalOnErr.setVisible(true);
            erraantalOn = false;
        }

        material.setLocation(txfLocation.getText());
        material.setLendable(cbxLendable.isSelected());
        if (erraantal && erraantalOn && errdoel && errleer && errname && errprijs) {
            mc.addMaterial(material);
            setMaterial(null);
            updateData();
            detailMaterial.setVisible(false);
            btnAddForReal.setVisible(false);
        }
    }

    private void updateData() {
        cmbFirm.setItems(mc.getFirms());
        SortedList sortedMaterials = new SortedList<>(mc.getMaterials());
        sortedMaterials.comparatorProperty().bind(tblMaterials.comparatorProperty());
        tblMaterials.setItems(sortedMaterials);
        tblMaterials.getColumns().get(0).setVisible(false);
        tblMaterials.getColumns().get(0).setVisible(true);

        //tblMaterials.refresh();
    }

    private void setFilter(String newValue) {
        SortedList sortedMaterials;
        if (newValue != "") {
            sortedMaterials = new SortedList<>(mc.getFilteredMaterials(newValue));
            sortedMaterials.comparatorProperty().bind(tblMaterials.comparatorProperty());
            tblMaterials.setItems(sortedMaterials);

        }
    }
}
