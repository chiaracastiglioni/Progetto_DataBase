package it.unibo.JavaFX.TableView;

import java.io.IOException;
import java.util.Date;

import it.unibo.ControllerTable;
import it.unibo.DB.model.Vacination;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FindVacination {

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TableColumn<Vacination, String> animal_name_col;

    @FXML
    private Button back_bt;

    @FXML
    private TableColumn<Vacination, Integer> clinic_col;

    @FXML
    private TableColumn<Vacination, Date> dateR_col;

    @FXML
    private TableColumn<Vacination, Date> date_col;

    @FXML
    private Button findVaxByMicrochip_bt;

    @FXML
    private TableColumn<Vacination, String> id_master_col;

    @FXML
    private TableColumn<Vacination, String> id_vax_col;

    @FXML
    private TextField microchip_tx;

    @FXML
    private TableView<Vacination> vacinationTableView;

    ObservableList<Vacination> listV;

    @FXML
    void findVaxByMicrochip(ActionEvent event) throws IOException {
        animal_name_col.setCellValueFactory(new PropertyValueFactory<Vacination, String>("animal_name"));
        id_master_col.setCellValueFactory(new PropertyValueFactory<Vacination, String>("id_master"));
        id_vax_col.setCellValueFactory(new PropertyValueFactory<Vacination, String>("id_vax"));
        clinic_col.setCellValueFactory(new PropertyValueFactory<Vacination, Integer>("clinic"));
        date_col.setCellValueFactory(new PropertyValueFactory<Vacination, Date>("date"));
        dateR_col.setCellValueFactory(new PropertyValueFactory<Vacination, Date>("dateR"));
        if (microchip_tx.getText().isEmpty()) {
            final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorFind.fxml"));
            final Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            listV = controllerTable.findVacinationByMicro(microchip_tx.getText());
            vacinationTableView.setItems(listV);
            
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

}
