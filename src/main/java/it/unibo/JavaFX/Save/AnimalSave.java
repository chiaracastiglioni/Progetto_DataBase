package it.unibo.JavaFX.Save;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
import it.unibo.Utils;
import it.unibo.DB.model.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class AnimalSave implements Initializable{

    ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TextField animalSpecies_tx;

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField day_tx;

    @FXML
    private ComboBox<String> gender_box;

    @FXML
    private TextField master_tx;

    @FXML
    private TextField microchip_tx;

    @FXML
    private TextField month_tx;

    @FXML
    private TextField name_tx;

    @FXML
    private TextField race_tx;

    @FXML
    private Button save_btn;

    @FXML
    private TextField year_tx;

    @FXML
    
    void saveAnimal(ActionEvent event) throws IOException {
        String name = name_tx.getText();
        String race = race_tx.getText();
        String microchip = microchip_tx.getText();
        String space_animal = animalSpecies_tx.getText();
        String id_master = master_tx.getText();
        String gender = gender_box.getValue() == null ? "" : gender_box.getValue();
        int day = Integer.parseInt(day_tx.getText() == "" ? "-1" : day_tx.getText());
        int month = Integer.parseInt(month_tx.getText() == "" ? "-1" : month_tx.getText());
        int year = Integer.parseInt(year_tx.getText() == "" ? "-1" : year_tx.getText());
        var date = Utils.buildDateSimple(day, month, year);
        if(name.isEmpty() || race.isEmpty() || space_animal.isEmpty() || id_master.isEmpty() || gender.isEmpty() || microchip.isEmpty()
                || day <= 0 || day > 31 ||  month <= 0 || month > 12 || year < 1900) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            boolean execute = controllerTable.saveAnimal(new Animal(microchip, name, race, space_animal, gender, id_master, date.get()));
            if(!execute) {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
            
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SaveCompleteAnimal.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        
        
    }


    @FXML
    void backPane1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Start.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("M", "F");
        gender_box.setItems(list);
    }

}
