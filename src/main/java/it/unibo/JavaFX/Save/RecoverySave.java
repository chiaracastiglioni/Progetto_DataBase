package it.unibo.JavaFX.Save;

import java.io.IOException;

import it.unibo.ControllerTable;
import it.unibo.DB.model.Recovery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecoverySave {

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField dayF_tx;

    @FXML
    private TextField hourF_tx;

    @FXML
    private TextField minutF_tx;

    @FXML
    private TextField monthF_tx;

    @FXML
    private TextField yearF_tx;

    @FXML
    private TextField day_tx;

    @FXML
    private TextField hour_tx;

    @FXML
    private TextField microchip_tx;

    @FXML
    private TextField minut_tx;

    @FXML
    private TextField month_tx;

    @FXML
    private Button save_btn;

    @FXML
    private TextField year_tx;

    @FXML
    void saverecovery(ActionEvent event) throws IOException {
        String microchip = microchip_tx.getText();
        String hour = hour_tx.getText() + ":" + minut_tx.getText();
        String date = year_tx.getText() + "-" + month_tx.getText() + "-" + day_tx.getText() + " " + hour;
        String hourF = hourF_tx.getText() + ":" + minutF_tx.getText();
        String dateF = yearF_tx.getText() + "-" + monthF_tx.getText() + "-" + dayF_tx.getText() + " " + hourF;
        int dayF = Integer.parseInt(dayF_tx.getText() == "" ? "-1" : dayF_tx.getText());
        int monthF = Integer.parseInt(monthF_tx.getText() == "" ? "-1" : monthF_tx.getText());
        int yearF = Integer.parseInt(yearF_tx.getText() == "" ? "-1" : yearF_tx.getText());
        int day = Integer.parseInt(day_tx.getText() == "" ? "-1" : day_tx.getText());
        int month = Integer.parseInt(month_tx.getText() == "" ? "-1" : month_tx.getText());
        int year = Integer.parseInt(year_tx.getText() == "" ? "-1" : year_tx.getText());
        double h = Double.parseDouble(hour_tx.getText() + "." + minut_tx.getText());
        double hF = Double.parseDouble(hourF_tx.getText() + "." + minutF_tx.getText());
        if (microchip.isEmpty() || date.isEmpty() || hour.isEmpty() || day <= 0 || day > 31 || month <= 0 || month > 12
                || year < 1900 || h > 25.00 || h < 0.00
                || hF > 25.00 || hF < 0.00 || dayF <= 0 || dayF > 31 || monthF <= 0 || monthF > 12
                || yearF < 1900) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (controllerTable.recoveryDateNotValid(date, microchip)
                || controllerTable.recoveryDateNotValid(dateF, microchip)
                || controllerTable.recoveryDateNotValidRange(date, microchip, dateF)) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            var range = controllerTable.getRangePair(date, dateF);
            boolean execute = controllerTable
                    .saveRecovery(new Recovery(microchip, date, dateF, range.getX(), range.getY()));
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
