package it.unibo.JavaFX.TableView;

import java.io.IOException;

import it.unibo.ControllerTable;
import it.unibo.DB.model.Recovery;
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

public class FindRecovery {

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private Button back_bt;

    @FXML
    private TableColumn<Recovery, String> dateF_col;

    @FXML
    private TableColumn<Recovery, String> date_col;

    @FXML
    private Button findRecoveryByMicro_bt;

    @FXML
    private TextField microchip_tx;

    @FXML
    private TableColumn<Recovery, Integer> rangeF_col;

    @FXML
    private TableColumn<Recovery, Integer> rangeI_col;

    @FXML
    private TableView<Recovery> recoveryTable;

    ObservableList<Recovery> listR;

    @FXML
    void findRecoveryByMicro(ActionEvent event) throws IOException {
        date_col.setCellValueFactory(new PropertyValueFactory<Recovery, String>("date_Start"));
        dateF_col.setCellValueFactory(new PropertyValueFactory<Recovery, String>("date_Stop"));
        rangeI_col.setCellValueFactory(new PropertyValueFactory<Recovery, Integer>("range_Start"));
        rangeF_col.setCellValueFactory(new PropertyValueFactory<Recovery, Integer>("range_Stop"));

        if (microchip_tx.getText().isEmpty()) {
            final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorFind.fxml"));
            final Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            listR = controllerTable.findRecoveryByMicro(microchip_tx.getText());
            recoveryTable.setItems(listR);
            
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

