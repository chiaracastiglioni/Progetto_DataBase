package it.unibo.JavaFX.Save;

import java.io.IOException;

import it.unibo.ControllerTable;
import it.unibo.Utils;
import it.unibo.DB.model.Veterinary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VetSave {

    ControllerTable controllerTable = new ControllerTable();

    @FXML
    private Button back_bt;

    @FXML
    private TextField cf_tx;

    @FXML
    private TextField day_tx;

    @FXML
    private TextField email_tx;

    @FXML
    private TextField month_tx;

    @FXML
    private TextField name_tx;

    @FXML
    private TextField number_tx;

    @FXML
    private Button save_bt;

    @FXML
    private TextField spec_tx;

    @FXML
    private TextField surname_tx;

    @FXML
    private TextField year_tx;

    @FXML
    void saveVet(ActionEvent event) throws IOException {
        String id = cf_tx.getText();
        String name = name_tx.getText();
        String surname = surname_tx.getText();
        String email = email_tx.getText();
        String spec = spec_tx.getText();
        String number = number_tx.getText();
        int day = Integer.parseInt(day_tx.getText() == "" ? "-1" : day_tx.getText());
        int month = Integer.parseInt(month_tx.getText() == "" ? "-1" : month_tx.getText());
        int year = Integer.parseInt(year_tx.getText() == "" ? "-1" : year_tx.getText());
        var date = Utils.buildDateSimple(day, month, year);

        if (name.isEmpty() || surname.isEmpty() || number.isEmpty() || spec.isEmpty() || email.isEmpty() || id.isEmpty()
                || day < 0 || day > 31 || month <= 0 || month > 12 || year < 1900) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (controllerTable.findMasterByCF(id)) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorCF.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            boolean execute = controllerTable
                    .saveVet(new Veterinary(id, name, surname, number, email, date.get(), spec));
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

    @FXML
    void switchPane1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Start.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
