package it.unibo.JavaFX.Save;

import java.io.IOException;

import it.unibo.ControllerTable;
import it.unibo.DB.model.Drug;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DrugSave {

    ControllerTable controllerTable = new ControllerTable();

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField id_tx;

    @FXML
    private TextField name_tx;

    @FXML
    private TextField price_tx;

    @FXML
    private Button save_btn;

    @FXML
    private TextField description_tx;

    @FXML
    void saveExam(ActionEvent event) throws IOException {
        String id = id_tx.getText();
        String name = name_tx.getText();
        String price = price_tx.getText();
        String description = description_tx.getText();

        if(id.isEmpty() || name.isEmpty() || price.isEmpty() || description.isEmpty()) {
            Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            boolean execute = controllerTable.saveDrug(new Drug(id, name, description, price));
            if (!execute) {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorSave.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else {
                Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SaveComplete.fxml"));
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

