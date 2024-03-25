package it.unibo.JavaFX;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import it.unibo.ControllerTable;
import it.unibo.DB.model.Animal;
import it.unibo.DB.model.Frequence;
import it.unibo.DB.model.Person;
import it.unibo.DB.model.Veterinary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;

public class StartController implements Initializable{
    
    static ControllerTable controllerTable = new ControllerTable();

    @FXML
    private TableView<Animal> animalTableView;

    @FXML
    private Button back1_bt;

    @FXML
    private Button addExam_bt; /*Aggiungere esami disponibili alla clinica */

    @FXML
    private TableColumn<Animal, String> animal_species_col;

    @FXML
    private TextField findName_tx;

    @FXML
    private Button findname_bt;

    @FXML
    private TableColumn<Person, String> cfC_col;
    
    @FXML
    private TableColumn<Veterinary, String> cfVet_col;

    @FXML
    private Button addVet_bt; /*Aggiunta veterinario */

    @FXML
    private Button clientAdd_btn;

    @FXML
    private TableColumn<Animal, Date> date_col;

    @FXML
    private TableColumn<Veterinary, String> emailVet_col; 

    @FXML
    private TableColumn<Person, String> email_col;

    @FXML
    private Button findmicro_bt; /*Bottone per trovare animale con microchip */

    @FXML
    private Button addSurgery_bt; /*Bottone per inserire un nuovo intervento disponibile */

    @FXML
    private Button surgeryView_bt; /*Bottone per visualizzare interventi disponibili */

    @FXML
    private Button allergyView_bt; /*Bottone per visualizzare allergie disponibili */

    @FXML
    private Button addAllergy_bt; /*Bottone per aggiungere allergie disponibili */

    @FXML
    private TableColumn<Animal, String> gender_col;

    @FXML
    private TableColumn<Veterinary, Date> hireDate_col; 

    @FXML
    private TableColumn<Animal, String> id_col;

    @FXML
    private TableColumn<Animal, String> master_col;

    @FXML
    private TableColumn<Person, String> nameClient_col;

    @FXML
    private TextField micro_tx;

    @FXML
    private TableColumn<Veterinary, String> nameVet_col; 

    @FXML
    private TableColumn<Animal, String> name_col;

    @FXML
    private TableColumn<Veterinary, String> numberVet_col; 

    @FXML
    private TableColumn<Person, String> number_col;

    @FXML
    private Button patientAdd_btn;

    @FXML
    private Button examView_bt; /*Per visualizzare gli esami disponibili */

    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Animal, String> race_col;

    @FXML
    private Button backPane_bt; /*Ritorna al pannello principale */

    @FXML
    private Button numAnimal_bt; /*Numero di animali di una persona */

    @FXML
    private Label numberA_label; /*Label dove appare il numero di animali di una persona */

    @FXML
    private Button illnesView_bt; /*Per visualizzare le malattie disponibili */

    @FXML
    private Button addIllnes_bt; /*Per aggiungere una malattia disponibile */

    @FXML
    private Button addMeet; /*Aggiungere appuntamento */
    
    @FXML
    private Button addAllergies_bt; /*Aggiungere allergia ad un animale */

    @FXML
    private Button addMalaise_bt; /*Aggiungere malattia ad un animale */

    @FXML
    private Button addDrug_bt; /*Aggiungere farmaco disponibile alla clinica */

    @FXML
    private Button drugView_bt; /*Visualizzare i farmaci disponibili */

    @FXML
    private TableColumn<Veterinary, String> specialization_col;

    @FXML
    private TableColumn<Veterinary, String> surnameVet_col; 

    @FXML
    private Button back_bt;

    @FXML
    private TableColumn<Person, String> surname_col;

    @FXML
    private TextField cfClient_tx; /*Inserire il CF per vedree il numero di animali posseduti */

    @FXML
    private TextField cfVet_tx; /*CF per sapere prossimo appuntamento */

    @FXML
    private Button nextMeet_bt; /*Prossimo appuntamento */

    @FXML
    private TableView<Veterinary> vetTableView; 

    @FXML
    private Button addExamination_bt; /*Aggiungere esaminazione */

    @FXML
    private Button addVax_bt; /*Aggiungere Vaccino disponibile alla clinica */

    @FXML
    private Button addListR; /*Modificare listino prezzi ricovero */

    @FXML
    private Button viewListR_bt; /*Visualizzare listino prezzi per ricovero */

    @FXML
    private Button modifiesdMalaise_bt; /*Modificare il malessere aggiungendo data fine */

    @FXML
    private Button addVacination_bt; /*Aggiungere vaccinazione */

    @FXML
    private Button updatedMalaise_bt; /*Aggiungere data fine malattia */ 

    @FXML
    private Button addIntervention_bt; /*Aggiungere un intervento ad un'animale */

    @FXML
    private Button addRecipe_bt; /*Aggiungere una ricetta ad un animale */

    @FXML
    private Button vacinationTable_bt; /*Visualizzare tabella vaccinazioni di un animale */

    @FXML
    private Button listView_bt; /*Visualizzare libretto di un animale */

    @FXML
    private Button illFrequenze_bt; /*Trovare malattia più frequente*/

    @FXML
    private TableColumn<Frequence, String> id_ill_col;

    @FXML
    private TableColumn<Frequence, Integer> frequenze_col;

    @FXML
    private TableView<Frequence> fequenceTableView;

    @FXML
    private Button addRecovery_bt;

    @FXML
    private Button maxRecovery_bt;

    @FXML
    private Label maxRecovery_label;

    @FXML
    private Button recoveryView_bt;


    @FXML
    void addPatientPane(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/AnimalSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addVax(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/VaxSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addExam(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ExamSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addSurgery(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SurgerySave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addAllergy(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/AllergySave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addIllnes(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/IllnesSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addAllergies(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/AllergiesSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addMalaise(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/MalaiseSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addDrug(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/DrugSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addListR(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ListRSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addVacination(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SaveVacination.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addExamination(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ExaminationSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void addIntervention(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/InterventionSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addRecipe(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/RecipeSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addRecovery(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/RecoverySave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    ObservableList<Animal> microAnimal = FXCollections.observableArrayList();
    @FXML
    void findAnimalMicrochip(ActionEvent event) throws IOException {
        id_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("name"));
        race_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("race"));
        gender_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
        animal_species_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("animal_species"));
        master_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("id_m"));
        date_col.setCellValueFactory(new PropertyValueFactory<Animal, Date>("birthday"));
        var animal = controllerTable.findbyMicro(micro_tx.getText());
        if (animal.isPresent()) {
            microAnimal.add(animal.get());
            animalTableView.setItems(microAnimal);
        }
        else {
            final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorFind.fxml"));
            final Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }   
    }

    @FXML
    void maxRecovery(ActionEvent event) throws IOException {
        int duration = controllerTable.getRecoveryMax();
        if(duration > -1) {
            maxRecovery_label.setText(Integer.toString(duration));
        }
        else {
            final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorFind.fxml"));
            final Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void findIdIll(ActionEvent event) {
        ObservableList<Frequence> list = controllerTable.getIllFrequences();
        id_ill_col.setCellValueFactory(new PropertyValueFactory<Frequence, String>("id_ill"));
        frequenze_col.setCellValueFactory(new PropertyValueFactory<Frequence, Integer>("frequence"));
        fequenceTableView.setItems(list);
    }

    @FXML
    void findVacinationByMicro(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/VacinationTableMicrochip.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void findListView(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/FindList.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void nextMeet(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/NextMeet.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void updateMalaise(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/UpdateMalaise.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    
    @FXML
    void findByName(ActionEvent event) {
        ObservableList<Animal> nameAnimals = FXCollections.observableArrayList();
        id_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("name"));
        race_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("race"));
        gender_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
        animal_species_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("animal_species"));
        master_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("id_m"));
        date_col.setCellValueFactory(new PropertyValueFactory<Animal, Date>("birthday"));
        nameAnimals = controllerTable.getAnimalByName(findName_tx.getText());
        animalTableView.setItems(nameAnimals);
    }

    @FXML
    void findAnimalNumber(ActionEvent event) throws IOException {
        int number = controllerTable.getNumberAnimal(cfClient_tx.getText());
        if(number > 0) {
            numberA_label.setText(Integer.toString(number));
        }
        else {
            final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ErrorFind.fxml"));
            final Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void deleteResearch(ActionEvent event) {
        cfClient_tx.setText("");
        numberA_label.setText("");
    }

    @FXML
    void saveClient(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SaveClientAnimal.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void saveVet(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SaveVet.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void saveMeet(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/MeetSave.fxml"));
        final Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchPane1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Start.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

   
    @FXML
    void viewVax(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/VaxTable.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } 

    @FXML
    void viewExam(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ExamView.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void viewSurgery(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/SurgeryTable.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void viewAllergy(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/AllergyTable.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void viewIllnes(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/IllnesTable.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void viewDrug(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/DrugTable.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void viewListR(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/ListRTable.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void viewRecovery(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/RecoveryTable.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ObservableList<Animal> listA;
    ObservableList<Person> listC;
    ObservableList<Veterinary> listV;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("name"));
        race_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("race"));
        gender_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
        animal_species_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("animal_species"));
        master_col.setCellValueFactory(new PropertyValueFactory<Animal,String>("id_m"));
        date_col.setCellValueFactory(new PropertyValueFactory<Animal, Date>("birthday"));
        
        listA = controllerTable.getAnimal(); 
        animalTableView.setItems(listA);

        cfC_col.setCellValueFactory(new PropertyValueFactory<Person,String>("id"));
        nameClient_col.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
        surname_col.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));
        number_col.setCellValueFactory(new PropertyValueFactory<Person,String>("number"));
        email_col.setCellValueFactory(new PropertyValueFactory<Person,String>("email"));

        listC = controllerTable.getPerson();
        personTableView.setItems(listC);

        cfVet_col.setCellValueFactory(new PropertyValueFactory<Veterinary,String>("id"));
        nameVet_col.setCellValueFactory(new PropertyValueFactory<Veterinary,String>("firstName"));
        surnameVet_col.setCellValueFactory(new PropertyValueFactory<Veterinary,String>("lastName"));
        numberVet_col.setCellValueFactory(new PropertyValueFactory<Veterinary,String>("number"));
        emailVet_col.setCellValueFactory(new PropertyValueFactory<Veterinary,String>("email"));
        hireDate_col.setCellValueFactory(new PropertyValueFactory<Veterinary, Date>("hireDate"));
        specialization_col.setCellValueFactory(new PropertyValueFactory<Veterinary, String>("specialization"));

        listV = controllerTable.getVet();
        vetTableView.setItems(listV);
    }

}