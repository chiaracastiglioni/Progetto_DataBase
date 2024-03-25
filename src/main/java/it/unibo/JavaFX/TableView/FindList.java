package it.unibo.JavaFX.TableView;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import it.unibo.ControllerTable;
import it.unibo.DB.model.Allergies;
import it.unibo.DB.model.Animal;
import it.unibo.DB.model.Examination;
import it.unibo.DB.model.Intervention;
import it.unibo.DB.model.Malaise;
import it.unibo.DB.model.Recipe;
import it.unibo.DB.model.Vacination;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FindList {

    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TableView<Allergies> allergiesTableView;

    @FXML
    private Label animalName_label;

    @FXML
    private TableColumn<Allergies, String> antiA_col;

    @FXML
    private Button back_bt;

    @FXML
    private TableColumn<Examination, Integer> clincE_tx;

    @FXML
    private TableColumn<Intervention, Integer> clinicI_col;

    @FXML
    private TableColumn<Recipe, Integer> clinicR_col;

    @FXML
    private TableColumn<Vacination, Integer> clinicV_col;

    @FXML
    private TableColumn<Examination, Date> dateE_col;

    @FXML
    private TableColumn<Malaise, Date> dateFM_col;

    @FXML
    private TableColumn<Intervention, Date> dateI_col;

    @FXML
    private TableColumn<Malaise, Date> dateM_col;

    @FXML
    private TableColumn<Vacination, Date> dateRV_col;

    @FXML
    private TableColumn<Recipe, Date> dateR_col;

    @FXML
    private TableColumn<Allergies, Date> dateSA_col;

    @FXML
    private TableColumn<Recipe, Date> dateSR_col;

    @FXML
    private TableColumn<Vacination, Date> dateV_col;

    @FXML
    private TableColumn<Recipe, String> doseR_col;

    @FXML
    private TableColumn<Recipe, String> durationR_col;

    @FXML
    private TableView<Examination> examinationTableView;

    @FXML
    private Button findByMicro_bt;

    @FXML
    private TableColumn<Allergies, String> id_allergy_col;

    @FXML
    private TableColumn<Recipe, String> id_drug_col;

    @FXML
    private TableColumn<Examination, String> id_exam_col;

    @FXML
    private TableColumn<Malaise, String> id_ill_col;

    @FXML
    private Label id_master_label;

    @FXML
    private TableColumn<Intervention, String> id_surgery_col;

    @FXML
    private TableColumn<Vacination, String> id_vax_col;

    @FXML
    private TableView<Intervention> interventionTableView;

    @FXML
    private TableColumn<Allergies, String> levelA_col;

    @FXML
    private TableView<Malaise> maliseTableView;

    @FXML
    private TextField microchip_tx;

    @FXML
    private TableColumn<Examination, String> motivationE_col;

    @FXML
    private TableColumn<Intervention, String> motivationI_col;

    @FXML
    private TableColumn<Allergies, String> periodA_col;

    @FXML
    private TableView<Recipe> recipeTableView;

    @FXML
    private TableColumn<Examination, String> resultE_col;

    @FXML
    private TableColumn<Allergies, String> symA_col;

    @FXML
    private TableColumn<Malaise, String> symM_col;

    @FXML
    private TableView<Vacination> vacinationTableView;

    @FXML
    void findListByMicrochip(ActionEvent event) throws IOException {
        ObservableList<Vacination> listV;
        ObservableList<Examination> listE;
        ObservableList<Recipe> listR;
        ObservableList<Malaise> listM;
        ObservableList<Allergies> listA;
        ObservableList<Intervention> listI;

        id_vax_col.setCellValueFactory(new PropertyValueFactory<Vacination, String>("id_vax"));
        clinicV_col.setCellValueFactory(new PropertyValueFactory<Vacination, Integer>("clinic"));
        dateV_col.setCellValueFactory(new PropertyValueFactory<Vacination, Date>("date"));
        dateRV_col.setCellValueFactory(new PropertyValueFactory<Vacination, Date>("dateR"));

        id_surgery_col.setCellValueFactory(new PropertyValueFactory<Intervention, String>("id_operation"));
        dateI_col.setCellValueFactory(new PropertyValueFactory<Intervention, Date>("date"));
        motivationI_col.setCellValueFactory(new PropertyValueFactory<Intervention, String>("motivation"));
        clinicI_col.setCellValueFactory(new PropertyValueFactory<Intervention, Integer>("clinic"));

        id_exam_col.setCellValueFactory(new PropertyValueFactory<Examination, String>("id_exam"));
        dateE_col.setCellValueFactory(new PropertyValueFactory<Examination, Date>("date"));
        motivationE_col.setCellValueFactory(new PropertyValueFactory<Examination, String>("exam_motivation"));
        clincE_tx.setCellValueFactory(new PropertyValueFactory<Examination, Integer>("clinic"));
        resultE_col.setCellValueFactory(new PropertyValueFactory<Examination, String>("result"));

        id_ill_col.setCellValueFactory(new PropertyValueFactory<Malaise, String>("id"));
        dateM_col.setCellValueFactory(new PropertyValueFactory<Malaise, Date>("date_start"));
        dateFM_col.setCellValueFactory(new PropertyValueFactory<Malaise, Date>("date_finish"));
        symM_col.setCellValueFactory(new PropertyValueFactory<Malaise, String>("symptom"));

        id_drug_col.setCellValueFactory(new PropertyValueFactory<Recipe, String>("id_drug"));
        dateR_col.setCellValueFactory(new PropertyValueFactory<Recipe, Date>("dateA"));
        durationR_col.setCellValueFactory(new PropertyValueFactory<Recipe, String>("duration"));
        doseR_col.setCellValueFactory(new PropertyValueFactory<Recipe, String>("dose"));
        dateSR_col.setCellValueFactory(new PropertyValueFactory<Recipe, Date>("dateS"));
        clinicR_col.setCellValueFactory(new PropertyValueFactory<Recipe, Integer>("clinic"));

        id_allergy_col.setCellValueFactory(new PropertyValueFactory<Allergies, String>("id"));
        antiA_col.setCellValueFactory(new PropertyValueFactory<Allergies, String>("antihistamine"));
        dateSA_col.setCellValueFactory(new PropertyValueFactory<Allergies, Date>("date"));
        periodA_col.setCellValueFactory(new PropertyValueFactory<Allergies, String>("year"));
        levelA_col.setCellValueFactory(new PropertyValueFactory<Allergies, String>("level_gravity"));
        symA_col.setCellValueFactory(new PropertyValueFactory<Allergies, String>("sympton"));
        
        if (microchip_tx.getText().isEmpty()) {
            final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorFind.fxml"));
            final Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            listV = controllerTable.findVacinationByMicro(microchip_tx.getText());
            listA = controllerTable.findAllergiesByMicro(microchip_tx.getText());
            listE = controllerTable.findExaminationByMicro(microchip_tx.getText());
            listI = controllerTable.findInterventionByMicro(microchip_tx.getText());
            listM = controllerTable.findMalaiseByMicro(microchip_tx.getText());
            listR = controllerTable.findRecipeByMicro(microchip_tx.getText());
            vacinationTableView.setItems(listV);
            allergiesTableView.setItems(listA);
            examinationTableView.setItems(listE);
            interventionTableView.setItems(listI);
            maliseTableView.setItems(listM);
            recipeTableView.setItems(listR);
            Optional<Animal> animal = controllerTable.findbyMicro(microchip_tx.getText());
            if(animal.isPresent()) {
                animalName_label.setText(animal.get().getName());
                id_master_label.setText(animal.get().getId_m());
            }
            else {
                final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorFind.fxml"));
                final Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

