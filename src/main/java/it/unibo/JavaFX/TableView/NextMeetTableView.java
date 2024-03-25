package it.unibo.JavaFX.TableView;

import java.io.IOException;
import java.util.Date;

import it.unibo.ControllerTable;
import it.unibo.DB.model.Meet;
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

public class NextMeetTableView {

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private Button back_btn;

    @FXML
    private TextField cf_tx;

    @FXML
    private TableColumn<Meet, Date> dateA_col;

    @FXML
    private Button find_bt;

    @FXML
    private TableColumn<Meet, String> hoursA_col;

    @FXML
    private TableColumn<Meet, String> idA_col;

    @FXML
    private TableColumn<Meet, String> nameAnimaA_col;

    @FXML
    private TableView<Meet> nextMeetTableView;

    ObservableList<Meet> listM;

    @FXML
    void nextMeet(ActionEvent event) throws IOException{
        idA_col.setCellValueFactory(new PropertyValueFactory<Meet,String>("id_master"));
        nameAnimaA_col.setCellValueFactory(new PropertyValueFactory<Meet,String>("animalName"));
        dateA_col.setCellValueFactory(new PropertyValueFactory<Meet,Date>("date"));
        hoursA_col.setCellValueFactory(new PropertyValueFactory<Meet,String>("hoursString"));
        var cf = cf_tx.getText();
        if (cf.isEmpty()) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            listM = controllerTable.getNextMeet(cf);
            nextMeetTableView.setItems(listM);
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

}

