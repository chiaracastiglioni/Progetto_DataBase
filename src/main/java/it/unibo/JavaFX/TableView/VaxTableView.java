package it.unibo.JavaFX.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.DB.model.Vax;
import it.unibo.ControllerTable;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class VaxTableView implements Initializable{

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TableColumn<Vax, String> animal_species_col;

    @FXML
    private Button back_bt;

    @FXML
    private TableColumn<Vax, String> id_col;

    @FXML
    private TableColumn<Vax, String> illness_col;

    @FXML
    private TableColumn<Vax, String> mandatory_col;

    @FXML
    private TableColumn<Vax, String> recall_col;

    @FXML
    private TableView<Vax> vaxTableView;

    @FXML
    void switchPane1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Start.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ObservableList<Vax> listV;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_col.setCellValueFactory(new PropertyValueFactory<Vax,String>("id"));
        recall_col.setCellValueFactory(new PropertyValueFactory<Vax,String>("recall"));
        mandatory_col.setCellValueFactory(new PropertyValueFactory<Vax,String>("mandatory"));
        animal_species_col.setCellValueFactory(new PropertyValueFactory<Vax,String>("animal_species"));
        illness_col.setCellValueFactory(new PropertyValueFactory<Vax,String>("illness"));
        
        listV = controllerTable.getVax(); 
        vaxTableView.setItems(listV);

    }

}

