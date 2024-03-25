package it.unibo.JavaFX.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
import it.unibo.DB.model.ListR;
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

public class ListRTableView implements Initializable{

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private Button back_bt;

    @FXML
    private TableColumn<ListR, Integer> start_col;

    @FXML
    private TableColumn<ListR, Integer> stop_col;

    @FXML
    private TableView<ListR> listRViewTable;

    @FXML
    private TableColumn<ListR, String> price_col;

    @FXML
    void switchPane1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Start.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ObservableList<ListR> listL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start_col.setCellValueFactory(new PropertyValueFactory<ListR,Integer>("range_Start"));
        stop_col.setCellValueFactory(new PropertyValueFactory<ListR,Integer>("range_Stop"));
        price_col.setCellValueFactory(new PropertyValueFactory<ListR,String>("price"));
        
        listL = controllerTable.getListR(); 
        listRViewTable.setItems(listL);

    }
}
