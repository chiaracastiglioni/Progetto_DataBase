package it.unibo.JavaFX.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AllergyTableView implements Initializable{

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TableView<Allergy> allergyTableView;

    @FXML
    private Button back_bt;

    @FXML
    private TableColumn<Allergy, String> cause_col;

    @FXML
    private TableColumn<Allergy, String> id_col;

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
        allergyTableView.setItems(listA);

    }

}

