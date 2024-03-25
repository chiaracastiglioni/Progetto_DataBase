package it.unibo.JavaFX.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
import it.unibo.DB.model.Drug;
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

public class DrugTableView implements Initializable{

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private Button back_bt;

    @FXML
    private TableView<Drug> drugTableView;

    @FXML
    private TableColumn<Drug, String> id_col;

    @FXML
    private TableColumn<Drug, String> name_col;

    @FXML
    private TableColumn<Drug, String> price_col;

    @FXML
    private TableColumn<Drug, String> description_col;

    @FXML
    void switchPane1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Start.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ObservableList<Drug> listD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_col.setCellValueFactory(new PropertyValueFactory<Drug,String>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<Drug,String>("name"));
        price_col.setCellValueFactory(new PropertyValueFactory<Drug,String>("price"));
        description_col.setCellValueFactory(new PropertyValueFactory<Drug,String>("description"));
        
        listD = controllerTable.getDrug(); 
        drugTableView.setItems(listD);

    }
}
