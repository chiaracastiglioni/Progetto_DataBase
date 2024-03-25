package it.unibo.JavaFX.Save;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VaxSave implements Initializable{

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TextField animalSpecies_tx;

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField id_tx;

    @FXML
    private TextField illness_tx;

    @FXML
    private ComboBox<String> mandatory_box;

    @FXML
    private TextField recall_tx;

    @FXML
    private Button save_btn;

    @FXML
    void saveVax(ActionEvent event) throws IOException {
        String id = id_tx.getText();
        String recall = recall_tx.getText();
        String mandatory = mandatory_box.getValue() == null ? "" : mandatory_box.getValue();
        String animal_species = animalSpecies_tx.getText();
        String illness = illness_tx.getText();
        if (id.isEmpty() || recall.isEmpty() || mandatory.isEmpty() || animal_species.isEmpty() || illness.isEmpty()) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            boolean execute = controllerTable.saveVax(new Vax(id, recall, mandatory, animal_species, illness));
            if(!execute) {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
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

    @FXML
    void switchPane1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Start.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("SI", "NO");
        mandatory_box.setItems(list);
    }

}
