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
import it.unibo.DB.model.Vacination;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class VacinationTable {
    public static final String TABLE_NAME = "vaccinazione";
    private final Connection connection;

    public VacinationTable(final Connection connection) {
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
                            "ID_Padrone CHAR(40) NOT NULL REFERENCES appuntamento(ID_Padrone)," +
                            "Nome_animale CHAR(40) NOT NULL REFERENCES appuntamento(Nome_animale)," +
                            "Data_Appuntamento DATE NOT NULL REFERENCES appuntamento(Data_Appuntamento)," +
                            "Codice_microchip CHAR(40) NOT NULL REFERENCES animale(Codice_Identificativo)," +
                            "Sigla_Vaccino CHAR(40) NOT NULL REFERENCES vaccini(Sigla)," +
                            "Ambulatorio INT NOT NULL," +
                            "Data_di_richiamo DATE," +
                            "CONSTRAINT vaccinazione_FKEY FOREIGN KEY(ID_Padrone, Nome_animale, Data_Appuntamento) REFERENCES appuntamento(ID_Padrone, Nome_animale, Data_Appuntamento),"
                            +
                            "CONSTRAINT vaccinazione_FKEY FOREIGN KEY(Sigla_Vaccino) REFERENCES vaccini(Sigla)," +
                            "CONSTRAINT vaccinazione_FKEY FOREIGN KEY(Codice_microchip) REFERENCES animale(Codice_Identificativo)"
                            +
                            ")");
            return true;
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Vacination> findByPrimaryKey(final Integer id) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_Identificativo = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, id);
            final var resultSet = statement.executeQuery();
            return readVacinationFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    public ObservableList<Vacination> findByMicro(final String microchip) {
        ObservableList<Vacination> list = FXCollections.observableArrayList();
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_microchip = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, microchip);
            final var rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Vacination(rs.getString("ID_Padrone"), rs.getString("Nome_animale"),
                        rs.getDate("Data_Appuntamento"), rs.getString("Codice_microchip"),
                        rs.getString("Sigla_Vaccino"), rs.getInt("Ambulatorio"), rs.getDate("Data_di_richiamo")));
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
    private List<Vacination> readVacinationFromResultSet(final ResultSet resultSet) {
        final List<Vacination> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id = resultSet.getInt("Codice_Identificativo");
                var id_master = resultSet.getString("ID_Padrone");
                var name_animal = resultSet.getString("Nome_animale");
                var date = resultSet.getDate("Data_Appuntamento");
                var microchip = resultSet.getString("Codice_microchip");
                var id_v = resultSet.getString("Sigla_Vaccino");
                var clinic = resultSet.getInt("Ambulatorio");
                var dateR = resultSet.getDate("Data_di_richiamo");
                Vacination vacination = new Vacination(id, id_master, name_animal, date, microchip, id_v, clinic,
                        dateR);
                list.add(vacination);

            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    public List<Vacination> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readVacinationFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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

    public boolean save(final Vacination vacination) {
        if (findByPrimaryKey(vacination.getId()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME
                    + " (ID_Padrone, Nome_animale, Data_Appuntamento, Codice_microchip, Sigla_Vaccino, Ambulatorio, Data_di_Richiamo) VALUES (?, ?, ? ,?, ?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, vacination.getId_master());
                statement.setString(2, vacination.getAnimal_name());
                var date = vacination.getDate();
                statement.setDate(3, Utils.dateToSqlDate(date));
                statement.setString(4, vacination.getMicrochip());
                statement.setString(5, vacination.getId_vax());
                statement.setInt(6, vacination.getClinic());
                var dateR = vacination.getDateR();
                statement.setDate(7, Utils.dateToSqlDate(dateR));
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
    public static ObservableList<Vacination> getDataMeet() {

        ObservableList<Vacination> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Vacination(rs.getInt("Codice_Identificativo"), rs.getString("ID_Padrone"),
                        rs.getString("Nome_animale"),
                        rs.getDate("Data_Appuntamento"), rs.getString("Codice_microchip"),
                        rs.getString("Sigla_Vaccino"),
                        rs.getInt("Ambulatorio"), rs.getDate("Data_di_richiamo")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}
