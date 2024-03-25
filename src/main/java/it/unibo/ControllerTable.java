package it.unibo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

import it.unibo.DB.db.ConnectionProvider;
import it.unibo.DB.db.tables.AllergiesTable;
import it.unibo.DB.db.tables.AllergyTable;
import it.unibo.DB.db.tables.AnimalTable;
import it.unibo.DB.db.tables.DrugTable;
import it.unibo.DB.db.tables.ExamTable;
import it.unibo.DB.db.tables.ExaminationTable;
import it.unibo.DB.db.tables.IllnesTable;
import it.unibo.DB.db.tables.InterventionTable;
import it.unibo.DB.db.tables.ListRTable;
import it.unibo.DB.db.tables.MalaiseTable;
import it.unibo.DB.db.tables.MeetTable;
import it.unibo.DB.db.tables.PersonTable;
import it.unibo.DB.db.tables.RecipeTable;
import it.unibo.DB.db.tables.RecoveryTable;
import it.unibo.DB.db.tables.SurgeryTable;
import it.unibo.DB.db.tables.VacinationTable;
import it.unibo.DB.db.tables.VaxTable;
import it.unibo.DB.db.tables.VeterinaryTable;
import it.unibo.DB.model.Allergies;
import it.unibo.DB.model.Allergy;
import it.unibo.DB.model.Animal;
import it.unibo.DB.model.Drug;
import it.unibo.DB.model.Exam;
import it.unibo.DB.model.Examination;
import it.unibo.DB.model.Frequence;
import it.unibo.DB.model.Illnes;
import it.unibo.DB.model.Intervention;
import it.unibo.DB.model.ListR;
import it.unibo.DB.model.Malaise;
import it.unibo.DB.model.Meet;
import it.unibo.DB.model.Person;
import it.unibo.DB.model.Recipe;
import it.unibo.DB.model.Recovery;
import it.unibo.DB.model.Surgery;
import it.unibo.DB.model.Vacination;
import it.unibo.DB.model.Vax;
import it.unibo.DB.model.Veterinary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControllerTable {
    final static String username = "root";
    final static String password = "";
    final static String dbName = "clinica_veterinaria";

    final static ConnectionProvider connectionProvider = new ConnectionProvider(username, password, dbName);
    final static AnimalTable animalTable = new AnimalTable(connectionProvider.getMySQLConnection());
    final static PersonTable personTable = new PersonTable(connectionProvider.getMySQLConnection());
    final static VeterinaryTable veterinaryTable = new VeterinaryTable(connectionProvider.getMySQLConnection());
    final static VaxTable vaxTable = new VaxTable(connectionProvider.getMySQLConnection());
    final static ExamTable examTable = new ExamTable(connectionProvider.getMySQLConnection());
    final static SurgeryTable surgeryTable = new SurgeryTable(connectionProvider.getMySQLConnection());
    final static AllergyTable allergyTable = new AllergyTable(connectionProvider.getMySQLConnection());
    final static IllnesTable illnesTable = new IllnesTable(connectionProvider.getMySQLConnection());
    final static MeetTable meetTable = new MeetTable(connectionProvider.getMySQLConnection());
    final static AllergiesTable allergiesTable = new AllergiesTable(connectionProvider.getMySQLConnection());
    final static MalaiseTable malaiseTable = new MalaiseTable(connectionProvider.getMySQLConnection());
    final static VacinationTable vacinationTable = new VacinationTable(connectionProvider.getMySQLConnection());
    final static DrugTable drugTable = new DrugTable(connectionProvider.getMySQLConnection());
    final static ListRTable listRTable = new ListRTable(connectionProvider.getMySQLConnection());
    final static RecoveryTable recoveryTable = new RecoveryTable(connectionProvider.getMySQLConnection());
    final static ExaminationTable examinationTable = new ExaminationTable(connectionProvider.getMySQLConnection());
    final static InterventionTable interventionTable = new InterventionTable(connectionProvider.getMySQLConnection());
    final static RecipeTable recipeTable = new RecipeTable(connectionProvider.getMySQLConnection());

    public ObservableList<Animal> getAnimal() {
        return AnimalTable.getDataAnimal();
    }

    public ObservableList<Person> getPerson() {
        return PersonTable.getDataClient();
    }

    public ObservableList<Veterinary> getVet() {
        return VeterinaryTable.getDataVeterinary();
    }

    public ObservableList<Vax> getVax() {
        return VaxTable.getDataVax();
    }

    public ObservableList<Exam> getExam() {
        return ExamTable.getDataExam();
    }

    public ObservableList<Surgery> getSurgery() {
        return SurgeryTable.getDataSurgery();
    }

    public ObservableList<Allergy> getAllergy() {
        return AllergyTable.getDataAllergy();
    }

    public ObservableList<Illnes> getIllnes() {
        return IllnesTable.getDataIllnes();
    }

    public ObservableList<Drug> getDrug() {
        return DrugTable.getDataDrug();
    }

    public ObservableList<ListR> getListR() {
        return ListRTable.getDataListR();
    }

    public boolean saveAnimal(final Animal animal) {
        return animalTable.save(animal);
    }

    public boolean saveClient(final Person person) {
        return personTable.save(person);
    }

    public boolean saveVet(final Veterinary vet) {
        return veterinaryTable.save(vet);
    }

    public boolean saveVax(final Vax vax) {
        return vaxTable.save(vax);
    }

    public boolean saveExam(final Exam exam) {
        return examTable.save(exam);
    }

    public boolean saveSurgery(final Surgery surgery) {
        return surgeryTable.save(surgery);
    }

    public boolean saveAllergy(final Allergy allergy) {
        return allergyTable.save(allergy);
    }

    public boolean saveIllnes(final Illnes illnes) {
        return illnesTable.save(illnes);
    }

    public boolean saveMeet(final Meet meet) {
        return meetTable.save(meet);
    }

    public boolean saveAllergies(final Allergies allergies) {
        return allergiesTable.save(allergies);
    }

    public boolean saveMalaise(final Malaise malaise) {
        return malaiseTable.save(malaise);
    }

    public boolean saveDrug(final Drug drug) {
        return drugTable.save(drug);
    }

    public boolean saveListR(final ListR listR) {
        return listRTable.save(listR);
    }

    public boolean saveVacination(final Vacination vacination) {
        return vacinationTable.save(vacination);
    }

    public boolean saveExamination(final Examination examination) {
        return examinationTable.save(examination);
    }

    public boolean saveIntervention(final Intervention intervention) {
        return interventionTable.save(intervention);
    }

    public boolean saveRecipe(final Recipe recipe) {
        return recipeTable.save(recipe);
    }

    public boolean saveRecovery(final Recovery recovery) {
        return recoveryTable.save(recovery);
    }

    public boolean updateMalaise(final String id, final String microchip, final Date date, final Date dateF) {
        var malaise = malaiseTable.findByPrimaryKey(id, date, microchip);
        if (malaise.isPresent() && malaise.get().getOptionalDate_finish().isEmpty()) {
            malaise.get().setDate_finish(Optional.of(dateF));
            return malaiseTable.update(malaise.get());
        } else {
            return false;
        }
    }

    public boolean findVetByCF(final String id) {
        var vet = veterinaryTable.findByPrimaryKey(id);
        if (vet.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean findMasterByCF(final String id) {
        var master = personTable.findByPrimaryKey(id);
        if (master.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public void deletePerson(final String id) {
        personTable.delete(id);
    }

    public boolean updateMeet(final String id_master, final String animal_name, final Date date) {

        var meet = meetTable.findByPrimaryKey(id_master, animal_name, date);
        if (meet.isPresent()) {
            return meetTable.update(meet.get());
        } else {
            return false;
        }

    }

    public boolean duplicateMeet(final double hour, final String id_vet, final Date date) {
        var meet = meetTable.findDublicate(id_vet, hour, date);
        if (meet.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean recoveryDateNotValid(final String date, final String microchip) {
        var recovery = recoveryTable.recoveryDateValid(date, microchip);
        if (recovery.isPresent()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean recoveryDateNotValidRange(final String date, final String microchip, final String dateF) {
        var recovery = recoveryTable.recoveryDateValidRange(date, microchip, dateF);
        if (recovery.isPresent()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean notExistAnimalByPerson(final String id_master, final String animal_name) {
        var animal = animalTable.findByMasterAnimalName(id_master, animal_name);
        if (animal.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkAnimal(final String microchip, final String name) {
        var animal = animalTable.findByPrimaryKey(microchip);
        if (animal.isPresent()) {
            if (animal.get().getName().equals(name)) {
                return false;
            }
            return true;
        } else {
            return true;
        }
    }

    public String checkMeet(final String id_master, final String animal_name, final Date date) {
        var meet = meetTable.findByPrimaryKey(id_master, animal_name, date);
        if (meet.isPresent()) {
            if (meet.get().getPerformance_performed().equals("NO")) {
                if (this.updateMeet(id_master, animal_name, date)) {
                    return "Update Completato";
                }
            } else {
                return "Prestazione già effettuata";
            }
        }

        return "Appuntamento non trovato";
    }

    public void deleteUpdateMeet(final String id_master, final String animal_name, final Date date) {
        var meet = meetTable.findByPrimaryKey(id_master, animal_name, date);
        if (meet.isPresent()) {
            if (meet.get().getPerformance_performed().equals("SI")) {
                meetTable.deleteUpdate(meet.get());
            }
        }
    }

    public Pair<Integer, Integer> getRangePair(final String date, final String dateF) {
        String dS = date;
        String dF = dateF;
        final var query = "SELECT TIMESTAMPDIFF(hour" + "," + "'" + dS + "'," + "'" + dF + "'" + ") AS hour";
        try (final PreparedStatement statement = ControllerTable.connectionProvider.getMySQLConnection()
                .prepareStatement(query)) {
            final var resultSet = statement.executeQuery();
            resultSet.next();
            int hour = resultSet.getInt("hour");
            final var query1 = "SELECT Range_Inizio, Range_Fine FROM listino_ricovero WHERE Range_Inizio <= ? AND Range_Fine > ? LIMIT 1";
            try (final PreparedStatement statement1 = ControllerTable.connectionProvider.getMySQLConnection()
                    .prepareStatement(query1)) {
                statement1.setInt(1, hour);
                statement1.setInt(2, hour);
                final var resultSet1 = statement1.executeQuery();
                resultSet1.next();
                int rangeS = resultSet1.getInt("Range_Inizio");
                int rangeF = resultSet1.getInt("Range_Fine");
                return new Pair<Integer, Integer>(rangeS, rangeF);

            } catch (final SQLException e) {
                e.printStackTrace();
            }

        } catch (final SQLException e) {
        }
        return null;
    }

    public int getRecoveryMax() {
        final var query = "SELECT MAX(TIMESTAMPDIFF(hour, Data_d_inizio , Data_di_fine)) AS hour FROM ricovero";
        try (final PreparedStatement statement = ControllerTable.connectionProvider.getMySQLConnection()
                .prepareStatement(query)) {
            final var resultSet = statement.executeQuery();
            resultSet.next();
            int hour = resultSet.getInt("hour");
            return hour;
        } catch (final SQLException e) {
        }
        return -1;
    }

    public Optional<Animal> findbyMicro(final String micro) {
        return animalTable.findByPrimaryKey(micro);
    }

    public ObservableList<Vacination> findVacinationByMicro(final String microchip) {
        return vacinationTable.findByMicro(microchip);
    }

    public ObservableList<Intervention> findInterventionByMicro(final String microchip) {
        return interventionTable.findByMicro(microchip);
    }

    public ObservableList<Recipe> findRecipeByMicro(final String microchip) {
        return recipeTable.findByMicro(microchip);
    }

    public ObservableList<Recovery> findRecoveryByMicro(final String microchip) {
        return recoveryTable.findByMicro(microchip);
    }

    public ObservableList<Examination> findExaminationByMicro(final String microchip) {
        return examinationTable.findByMicro(microchip);
    }

    public ObservableList<Malaise> findMalaiseByMicro(final String microchip) {
        return malaiseTable.findByMicro(microchip);
    }

    public ObservableList<Allergies> findAllergiesByMicro(final String microchip) {
        return allergiesTable.findByMicro(microchip);
    }

    public ObservableList<Animal> getAnimalByName(final String name) {
        return animalTable.findByName(name);
    }

    public int getNumberAnimal(final String idPerson) {
        Optional<Person> person = personTable.findByPrimaryKey(idPerson);
        if (person.isPresent()) {
            int numberAnimal = animalTable.findByMaster(idPerson);
            if (numberAnimal > 0) {
                return numberAnimal;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public ObservableList<Meet> getNextMeet(final String id_vet) {
        return meetTable.getNextMeet(id_vet);
    }

    public ObservableList<Frequence> getIllFrequences() {
        ObservableList<Frequence> list = FXCollections.observableArrayList();
        final var query = "SELECT COUNT(*) AS Frequenza, Sigla_Malattia FROM malessere GROUP BY Sigla_Malattia HAVING COUNT(*) = (SELECT COUNT(*) FROM malessere GROUP BY Sigla_Malattia ORDER BY COUNT(*) DESC LIMIT 1)";
        try (final PreparedStatement statement = ControllerTable.connectionProvider.getMySQLConnection()
                .prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Frequence frequence = new Frequence(rs.getString("Sigla_Malattia"), rs.getInt("Frequenza"));
                list.add(frequence);
            }
        } catch (final SQLException e) {
        }
        return list;
    }

}
