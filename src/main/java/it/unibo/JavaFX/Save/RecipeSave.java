package it.unibo.JavaFX.Save;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
import it.unibo.Utils;
import it.unibo.DB.model.Drug;
import it.unibo.DB.model.Recipe;
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

public class RecipeSave implements Initializable {

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TextField animal_name_tx;

    @FXML
    private Button cancel_btn;

    @FXML
    private ComboBox<String> clinic_box;

    @FXML
    private TextField dayF_tx;

    @FXML
    private TableColumn<Drug, String> description_col;

    @FXML
    private TextField dose_tx;

    @FXML
    private TableView<Drug> drugTableView;

    @FXML
    private TextField duration_tx;

    @FXML
    private TableColumn<Drug, String> id_col;

    @FXML
    private TextField id_master_tx;

    @FXML
    private TextField id_tx;

    @FXML
    private TextField microchip_tx;

    @FXML
    private TextField monthF_tx;

    @FXML
    private TableColumn<Drug, String> name_col;

    @FXML
    private TableColumn<Drug, String> price_col;

    @FXML
    private Button save_btn;

    @FXML
    private TextField yearF_tx;


    @FXML
    void saveRecipe(ActionEvent event) throws IOException {
        String animalName = animal_name_tx.getText();
        String id_master = id_master_tx.getText();
        String microchip = microchip_tx.getText();
        String id_drug = id_tx.getText();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        SimpleDateFormat sdf2 = new SimpleDateFormat("YYYY");
        Optional<Date> date = Utils.buildDateSimple(Integer.parseInt(sdf.format(d)), Integer.parseInt(sdf1.format(d)), Integer.parseInt(sdf2.format(d)));
        int dayF = Integer.parseInt(dayF_tx.getText() == "" ? "-1" : dayF_tx.getText());
        int monthF = Integer.parseInt(monthF_tx.getText() == "" ? "-1" : monthF_tx.getText());
        int yearF = Integer.parseInt(yearF_tx.getText() == "" ? "-1" : yearF_tx.getText());
        var dateF = Utils.buildDateSimple(dayF, monthF, yearF);
        int clinic = Integer.parseInt(clinic_box.getValue() == null ? "-1" : clinic_box.getValue());
        String dose = dose_tx.getText();
        String duration = duration_tx.getText();
        if (animalName.isEmpty() || id_master.isEmpty() || microchip.isEmpty() || id_drug.isEmpty() || clinic == -1
                || dose.isEmpty() || duration.isEmpty()
                ||  dayF <= 0 || dayF > 31 || monthF <= 0 || monthF > 12 || yearF < 1900) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
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
                boolean execute = controllerTable.saveRecipe(new Recipe(id_master, animalName, date.get(), microchip, id_drug, clinic, duration, dose, dateF.get()));
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

    ObservableList<Drug> listD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("1", "2", "3", "4", "5");
        id_col.setCellValueFactory(new PropertyValueFactory<Drug, String>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<Drug, String>("name"));
        price_col.setCellValueFactory(new PropertyValueFactory<Drug, String>("price"));
        description_col.setCellValueFactory(new PropertyValueFactory<Drug, String>("description"));

        listD = controllerTable.getDrug();
        drugTableView.setItems(listD);
        clinic_box.setItems(list);
    }
}
