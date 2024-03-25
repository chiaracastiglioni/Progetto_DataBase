package it.unibo.JavaFX.Save;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
import it.unibo.Utils;
import it.unibo.DB.model.Allergies;
import it.unibo.DB.model.Allergy;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AllergiesSave implements Initializable{
    
    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TableView<Allergy> allergiesTableView;

    @FXML
    private TextField anti_tx;

    @FXML
    private Button cancel_btn;

    @FXML
    private TableColumn<Allergy, String> cause_col;

    @FXML
    private TextField day_tx;

    @FXML
    private TextField gravity_tx;

    @FXML
    private TableColumn<Allergy, String> id_col;

    @FXML
    private TextField id_tx;

    @FXML
    private TextField microchip_tx;

    @FXML
    private TextField month_tx;

    @FXML
    private TextField period_tx;

    @FXML
    private Button save_btn;

    @FXML
    private TextField symptom_tx;

    @FXML
    private TextField year_tx;

    @FXML
    void saveAllergies(ActionEvent event) throws IOException {
        String id = id_tx.getText();
        String antihistamine = anti_tx.getText();
        String period = period_tx.getText();
        int level_gravity = Integer.parseInt(gravity_tx.getText() == "" ? "-1" : gravity_tx.getText());
        String sympton = symptom_tx.getText();
        String microchip = microchip_tx.getText();
        int day = Integer.parseInt(day_tx.getText() == "" ? "-1" : day_tx.getText());
        int month = Integer.parseInt(month_tx.getText() == "" ? "-1" : month_tx.getText());
        int year = Integer.parseInt(year_tx.getText() == "" ? "-1" : year_tx.getText());
        var date = Utils.buildDateSimple(day, month, year);
        if(id.isEmpty() || period.isEmpty() || level_gravity == -1 || sympton.isEmpty() || microchip.isEmpty()
                || day <= 0 || day > 31 ||  month <= 0 || month > 12 || year < 1900) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            boolean execute = controllerTable.saveAllergies(new Allergies(id, anti_tx.getText().isEmpty() ? Optional.empty() : Optional.of(antihistamine), period, level_gravity, sympton, microchip, date.get()));
            if (!execute) {
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

    ObservableList<Allergy> listA;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_col.setCellValueFactory(new PropertyValueFactory<Allergy,String>("id"));
        cause_col.setCellValueFactory(new PropertyValueFactory<Allergy,String>("cause"));
        
        listA = controllerTable.getAllergy(); 
        allergiesTableView.setItems(listA);
    }

}
