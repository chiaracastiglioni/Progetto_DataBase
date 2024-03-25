package it.unibo.JavaFX.Save;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
import it.unibo.Utils;
import it.unibo.DB.model.Vacination;
import it.unibo.DB.model.Vax;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class VacinationSave implements Initializable {

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TextField animal_name_tx;

    @FXML
    private TableColumn<Vax, String> animal_species_col;

    @FXML
    private Button cancel_btn;

    @FXML
    private ComboBox<String> clinic_box;

    @FXML
    private TextField dayR_tx;

    @FXML
    private TableColumn<Vax, String> id_col;

    @FXML
    private TextField id_master_tx;

    @FXML
    private TextField id_tx;

    @FXML
    private TableColumn<Vax, String> illness_col;

    @FXML
    private TableColumn<Vax, String> mandatory_col;

    @FXML
    private TextField microchip_tx;

    @FXML
    private TextField monthR_tx;

    @FXML
    private TableColumn<Vax, String> recall_col;

    @FXML
    private Button save_btn;

    @FXML
    private TableView<Vax> vaxTableView;

    @FXML
    private TextField yearR_tx;


    @FXML
    void saveVacination(ActionEvent event) throws IOException {
        String animalName = animal_name_tx.getText();
        String id_master = id_master_tx.getText();
        String microchip = microchip_tx.getText();
        String id_vax = id_tx.getText();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        SimpleDateFormat sdf2 = new SimpleDateFormat("YYYY");
        Optional<Date> date = Utils.buildDateSimple(Integer.parseInt(sdf.format(d)), Integer.parseInt(sdf1.format(d)), Integer.parseInt(sdf2.format(d)));
        int dayR = Integer.parseInt(dayR_tx.getText() == "" ? "-1" : dayR_tx.getText());
        int monthR = Integer.parseInt(monthR_tx.getText() == "" ? "-1" : monthR_tx.getText());
        int yearR = Integer.parseInt(yearR_tx.getText() == "" ? "-1" : yearR_tx.getText());
        var dateR = Utils.buildDateSimple(dayR, monthR, yearR);
        int clinic = Integer.parseInt(clinic_box.getValue() == null ? "-1" : clinic_box.getValue());
        if (animalName.isEmpty() || id_master.isEmpty() || microchip.isEmpty() || id_vax.isEmpty() || clinic == -1
                || dayR <= 0 || dayR > 31 || monthR <= 0 || monthR > 12 || yearR < 1900) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            if (controllerTable.checkMeet(id_master, animalName, date.get()) == "Prestazione già effettuata") {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorUpdate.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else if (controllerTable.checkMeet(id_master, animalName, date.get()) == "Appuntamento non trovato") {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorFindMeet.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else if(controllerTable.checkAnimal(microchip, animalName)) {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                controllerTable.deleteUpdateMeet(id_master, animalName, date.get()); 
            } else {
                boolean execute = controllerTable.saveVacination(new Vacination(id_master, animalName, date.get(), microchip, id_vax, clinic, dateR.get()));
                if(!execute) {
                    Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    controllerTable.deleteUpdateMeet(id_master, animalName, date.get());
                }
                else {
                    Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SaveCompleteAnimal.fxml"));
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
    }

    @FXML
    void switchPane1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Start.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ObservableList<Vax> listV;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("1", "2", "3", "4", "5");
        id_col.setCellValueFactory(new PropertyValueFactory<Vax, String>("id"));
        recall_col.setCellValueFactory(new PropertyValueFactory<Vax, String>("recall"));
        mandatory_col.setCellValueFactory(new PropertyValueFactory<Vax, String>("mandatory"));
        animal_species_col.setCellValueFactory(new PropertyValueFactory<Vax, String>("animal_species"));
        illness_col.setCellValueFactory(new PropertyValueFactory<Vax, String>("illness"));

        listV = controllerTable.getVax();
        vaxTableView.setItems(listV);
        clinic_box.setItems(list);
    }

}
