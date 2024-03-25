package it.unibo.DB.db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.Utils;
import it.unibo.DB.db.ConnectionProvider;
import it.unibo.DB.model.Examination;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class ExaminationTable {
    public static final String TABLE_NAME = "esaminazione";
    private final Connection connection;

    public ExaminationTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public boolean createTable() {
        // 1. Create the statement from the open connection inside a try-with-resources
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            statement.executeUpdate(
                    "CREATE TABLE " + TABLE_NAME + " (" +
                            "Codice_Identificativo INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                            "ID_Padrone CHAR(40) NOT NULL," +
                            "Nome_animale CHAR(40) NOT NULL," +
                            "Data_Appuntamento DATE NOT NULL," +
                            "Codice_microchip CHAR(40) NOT NULL," +
                            "Sigla_Esame CHAR(40) NOT NULL," +
                            "Ambulatorio INT NOT NULL," +
                            "Motivazione_dell_esame CHAR(40) NOT NULL," +
                            "Esito CHAR(40) NOT NULL," +
                            ")");
            return true;
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
            return false;
        }
    }

    public Optional<Examination> findByPrimaryKey(final Integer id) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_Identificativo = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, id);
            final var resultSet = statement.executeQuery();
            return readExaminationFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    public ObservableList<Examination> findByMicro(final String microchip) {
        ObservableList<Examination> list = FXCollections.observableArrayList();
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_microchip = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, microchip);
            final var rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Examination(rs.getInt("Codice_Identificativo"), rs.getString("ID_Padrone"), rs.getString("Nome_animale"),
                        rs.getDate("Data_Appuntamento"), rs.getString("Codice_microchip"), rs.getString("Sigla_Esame"),
                        rs.getInt("Ambulatorio"), rs.getString("Esito"), rs.getString("Motivazione_dell_esame")));
            }
        } catch (final SQLException e) {
        }
        return list;
    }

    /**
     * Given a ResultSet read all the students in it and collects them in a List
     * 
     * @param resultSet a ResultSet from which the Student(s) will be extracted
     * @return a List of all the students in the ResultSet
     */
    private List<Examination> readExaminationFromResultSet(final ResultSet resultSet) {
        final List<Examination> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id = resultSet.getInt("Codice_Identificativo");
                var id_master = resultSet.getString("ID_Padrone");
                var name_animal = resultSet.getString("Nome_animale");
                var date = resultSet.getDate("Data_Appuntamento");
                var microchip = resultSet.getString("Codice_microchip");
                var id_e = resultSet.getString("Sigla_Esame");
                var clinic = resultSet.getInt("Ambulatorio");
                var exam_motivation = resultSet.getString("Motivazione_dell_esame");
                var result = resultSet.getString("Esito");
                Examination examination = new Examination(id, id_master, name_animal, date, microchip, id_e, clinic,
                        result, exam_motivation);
                list.add(examination);

            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    public List<Examination> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readExaminationFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
            return List.of();
        }
    }

    public boolean dropTable() {
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(("DROP TABLE " + TABLE_NAME));
            return true;
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
            e.printStackTrace();
            return false;
        }
    }

    public boolean save(final Examination examination) {
        if (findByPrimaryKey(examination.getId()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME
                    + " (ID_Padrone, Nome_animale, Data_Appuntamento, Codice_microchip, Sigla_Esame, Ambulatorio, Esito, Motivazione_dell_esame) VALUES (?, ?, ? ,?, ?, ?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, examination.getId_master());
                statement.setString(2, examination.getAnimal_name());
                var date = examination.getDate();
                statement.setDate(3, Utils.dateToSqlDate(date));
                statement.setString(4, examination.getMicrochip());
                statement.setString(5, examination.getId_exam());
                statement.setInt(6, examination.getClinic());
                statement.setString(7, examination.getResult());
                statement.setString(8, examination.getExam_motivation());
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        }

    }

    public boolean delete(final Integer id) {
        if (findByPrimaryKey(id).isPresent()) {
            final var query = "DELETE FROM " + TABLE_NAME + " WHERE Codice_Identificativo = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setInt(1, id);
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static Connection connectionDB() {
        String username = "root";
        String password = "";
        String dbName = "clinica_veterinaria";

        final ConnectionProvider connectionProvider = new ConnectionProvider(username, password, dbName);
        Connection con = connectionProvider.getMySQLConnection();
        return con;
    }

    /* Da modificare */
    public static ObservableList<Examination> getDataMeet() {

        ObservableList<Examination> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Examination(rs.getInt("Codice_Identificativo"), rs.getString("ID_Padrone"), rs.getString("Nome_animale"),
                        rs.getDate("Data_Appuntamento"), rs.getString("Codice_microchip"), rs.getString("Sigla_Esame"),
                        rs.getInt("Ambulatorio"), rs.getString("Esito"), rs.getString("Motivazione_dell_esame")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}
