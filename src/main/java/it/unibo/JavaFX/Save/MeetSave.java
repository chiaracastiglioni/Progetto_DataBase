package it.unibo.JavaFX.Save;

import java.io.IOException;

import it.unibo.ControllerTable;
import it.unibo.Utils;
import it.unibo.DB.model.Meet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MeetSave {

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField clientId_tx;

    @FXML
    private TextField day_tx;

    @FXML
    private TextField hours_tx;

    @FXML
    private TextField month_tx;

    @FXML
    private TextField name_tx;

    @FXML
    private Button save_btn;

    @FXML
    private TextField vetId_tx;

    @FXML
    private TextField year_tx;

    @FXML
    void saveMeet(ActionEvent event) throws IOException {
        String id_master = clientId_tx.getText();
        String name_animal = name_tx.getText();
        String id_vet = vetId_tx.getText();
        String performance = "NO";
        Double hours = Double.parseDouble(hours_tx.getText() == "" ? "-1.0" : hours_tx.getText());
        int day = Integer.parseInt(day_tx.getText() == "" ? "-1" : day_tx.getText());
        int month = Integer.parseInt(month_tx.getText() == "" ? "-1" : month_tx.getText());
        int year = Integer.parseInt(year_tx.getText() == "" ? "-1" : year_tx.getText());
        var date = Utils.buildDateSimple(day, month, year);

        if (id_master.isEmpty() || name_animal.isEmpty() || id_vet.isEmpty() || hours == -1.0 || hours > 25.00
                || hours < 0.00 ||
                day <= 0 || day > 31 || month <= 0 || month > 12 || year < 1900) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            if (controllerTable.duplicateMeet(hours, id_vet, date.get()) || controllerTable.notExistAnimalByPerson(id_master, name_animal)) {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                boolean execute = controllerTable.saveMeet(new Meet(id_master, name_animal, hours, performance, id_vet, date.get()));
                if (!execute) {
                    Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SaveCompleteAnimal.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

}
