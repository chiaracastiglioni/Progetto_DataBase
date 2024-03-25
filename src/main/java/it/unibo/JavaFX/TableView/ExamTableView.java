package it.unibo.JavaFX.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
import it.unibo.DB.model.Exam;
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

public class ExamTableView implements Initializable{

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private Button back_bt;

    @FXML
    private TableView<Exam> examViewTable;

    @FXML
    private TableColumn<Exam, String> id_col;

    @FXML
    private TableColumn<Exam, String> name_col;

    @FXML
    void switchPane1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Start.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ObservableList<Exam> listE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_col.setCellValueFactory(new PropertyValueFactory<Exam,String>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<Exam,String>("name"));
        
        listE = controllerTable.getExam(); 
        examViewTable.setItems(listE);

    }

}
