package it.unibo.JavaFX.Save;

import java.io.IOException;

import it.unibo.ControllerTable;
import it.unibo.DB.model.Surgery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SurgerySave {

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TextField body_tx;

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField id_tx;

    @FXML
    private Button save_btn;

    @FXML
    private TextField description_tx;

    @FXML
    void saveSurgery(ActionEvent event) throws IOException {
        String id = id_tx.getText();
        String body = body_tx.getText();
        String description = description_tx.getText();
        if(id.isEmpty() || body.isEmpty() || description.isEmpty()) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            boolean execute = controllerTable.saveSurgery(new Surgery(id, body, description));
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
