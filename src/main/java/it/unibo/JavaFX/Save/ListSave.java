package it.unibo.JavaFX.Save;

import java.io.IOException;

import it.unibo.ControllerTable;
import it.unibo.DB.model.ListR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ListSave {

    ControllerTable controllerTable = new ControllerTable();

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField start_tx;

    @FXML
    private TextField stop_tx;

    @FXML
    private TextField price_tx;

    @FXML
    private Button save_btn;

    @FXML
    void saveList(ActionEvent event) throws IOException {
        int start = Integer.parseInt(start_tx.getText() == "" ? "-1" : start_tx.getText());
        int stop = Integer.parseInt(stop_tx.getText() == "" ? "-1" : stop_tx.getText());
        String price = price_tx.getText();
        if(start== -1 || stop == -1 || price.isEmpty()) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            boolean execute = controllerTable.saveListR(new ListR(start, stop, price));
            if(!execute) {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SaveCompleteAnimal.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
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
