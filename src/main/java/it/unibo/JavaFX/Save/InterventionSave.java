package it.unibo.JavaFX.Save;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
import it.unibo.Utils;
import it.unibo.DB.model.Intervention;
import it.unibo.DB.model.Surgery;
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

public class InterventionSave implements Initializable{

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TextField animal_name_tx;

    @FXML
    private TableColumn<Surgery, String> body_col;

    @FXML
    private Button cancel_btn;

    @FXML
    private TableColumn<Surgery, String> description_col;

    @FXML
    private TableColumn<Surgery, String> id_col;

    @FXML
    private TextField id_master_tx;

    @FXML
    private TextField id_o_tx;

    @FXML
    private TextField microchip_tx;

    @FXML
    private ComboBox<String> clinic_box;

    @FXML
    private TextField motivation_tx;

    @FXML
    private Button save_btn;

    @FXML
    private TableView<Surgery> surgeryTableView;


    @FXML
    void saveIntervention(ActionEvent event) throws IOException {
        String id = id_o_tx.getText();
        String id_master = id_master_tx.getText();
        String motivation = motivation_tx.getText();
        String animalName = animal_name_tx.getText();

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        SimpleDateFormat sdf2 = new SimpleDateFormat("YYYY");
        Optional<Date> date = Utils.buildDateSimple(Integer.parseInt(sdf.format(d)), Integer.parseInt(sdf1.format(d)), Integer.parseInt(sdf2.format(d)));
        String microchip = microchip_tx.getText();
        int clinic = Integer.parseInt(clinic_box.getValue() == null ? "-1" : clinic_box.getValue());
        if (id.isEmpty() || id_master.isEmpty() || motivation.isEmpty() || animalName.isEmpty() || microchip.isEmpty()
                || clinic == -1) {
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
                boolean execute = controllerTable.saveIntervention(new Intervention(id_master, animalName, date.get(), microchip, id, clinic, motivation));
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

    ObservableList<Surgery> listV;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("1", "2", "3", "4", "5");
        id_col.setCellValueFactory(new PropertyValueFactory<Surgery,String>("id"));
        body_col.setCellValueFactory(new PropertyValueFactory<Surgery,String>("body"));
        description_col.setCellValueFactory(new PropertyValueFactory<Surgery,String>("description"));
        
        listV = controllerTable.getSurgery(); 
        surgeryTableView.setItems(listV);
        clinic_box.setItems(list);
    }
}
