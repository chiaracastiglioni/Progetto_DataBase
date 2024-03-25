package it.unibo.JavaFX.Save;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
import it.unibo.Utils;
import it.unibo.DB.model.Illnes;
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

public class MalaiseUpdate implements Initializable{

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private Button cancel_btn;

    @FXML
    private TableColumn<Illnes, String> common_s_col;

    @FXML
    private TextField dayF_tx;

    @FXML
    private TableColumn<Illnes, String> id_col;

    @FXML
    private TextField id_tx;

    @FXML
    private TextField day_tx;

    @FXML
    private TextField month_tx;

    @FXML
    private TextField year_tx;


    @FXML
    private TableView<Illnes> illnesTableView;

    @FXML
    private TextField microchip_tx;

    @FXML
    private TextField monthF_tx;

    @FXML
    private TableColumn<Illnes, String> name_col;

    @FXML
    private Button save_btn;

    @FXML
    private TextField yearF_tx;

    @FXML
    void switchPane1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Start.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void updateMalaise(ActionEvent event) throws IOException {
        String id = id_tx.getText();
        String microchip = microchip_tx.getText();
        int day = Integer.parseInt(day_tx.getText() == "" ? "-1" : day_tx.getText());
        int month = Integer.parseInt(month_tx.getText() == "" ? "-1" : month_tx.getText());
        int year = Integer.parseInt(year_tx.getText() == "" ? "-1" : year_tx.getText());
        var date = Utils.buildDateSimple(day, month, year);
        int dayF = Integer.parseInt(dayF_tx.getText() == "" ? "-1" : dayF_tx.getText());
        int monthF = Integer.parseInt(monthF_tx.getText() == "" ? "-1" : monthF_tx.getText());
        int yearF = Integer.parseInt(yearF_tx.getText() == "" ? "-1" : yearF_tx.getText());
        var dateF = Utils.buildDateSimple(dayF, monthF, yearF);
        if (id.isEmpty() || microchip.isEmpty() || day <= 0 || day > 31 || month <= 0 || month > 12 || year < 1900
        || dayF <= 0 || dayF > 31 || monthF <= 0 || monthF > 12 || yearF < 1900) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            if(controllerTable.updateMalaise(id, microchip, date.get(), dateF.get())) {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SaveCompleteAnimal.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    ObservableList<Illnes> listA;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_col.setCellValueFactory(new PropertyValueFactory<Illnes, String>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<Illnes, String>("name"));
        common_s_col.setCellValueFactory(new PropertyValueFactory<Illnes, String>("common_symptom"));

        listA = controllerTable.getIllnes();
        illnesTableView.setItems(listA);

    }

}

