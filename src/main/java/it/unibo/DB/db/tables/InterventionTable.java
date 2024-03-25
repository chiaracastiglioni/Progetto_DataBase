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
import it.unibo.DB.model.Intervention;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class InterventionTable {
    public static final String TABLE_NAME = "intervento";
    private final Connection connection;

    public InterventionTable(final Connection connection) {
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
                            "Sigla_Operazione CHAR(40) NOT NULL," +
                            "Ambulatorio INT NOT NULL," +
                            "Motivazione CHAR(40) NOT NULL," +
                            ")");
            return true;
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
            return false;
        }
    }

    public Optional<Intervention> findByPrimaryKey(final Integer id) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_Identificativo = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, id);
            final var resultSet = statement.executeQuery();
            return readInterventionFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    public ObservableList<Intervention> findByMicro(final String microchip) {
        ObservableList<Intervention> list = FXCollections.observableArrayList();
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_microchip = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, microchip);
            final var rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Intervention(rs.getInt("Codice_Identificativo"), rs.getString("ID_Padrone"), rs.getString("Nome_animale"),
                        rs.getDate("Data_Appuntamento"), rs.getString("Codice_microchip"), rs.getString("Sigla_Operazione"),
                        rs.getInt("Ambulatorio"), rs.getString("Motivazione")));
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
    private List<Intervention> readInterventionFromResultSet(final ResultSet resultSet) {
        final List<Intervention> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id = resultSet.getInt("Codice_Identificativo");
                var id_master = resultSet.getString("ID_Padrone");
                var name_animal = resultSet.getString("Nome_animale");
                var date = resultSet.getDate("Data_Appuntamento");
                var microchip = resultSet.getString("Codice_microchip");
                var id_o = resultSet.getString("Sigla_Operazione");
                var clinic = resultSet.getInt("Ambulatorio");
                var motivation = resultSet.getString("Motivazione");
                Intervention intervention = new Intervention(id, id_master, name_animal, date, microchip, id_o, clinic, motivation);
                list.add(intervention);

            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    public List<Intervention> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readInterventionFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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

    public boolean save(final Intervention intervention) {
        if (findByPrimaryKey(intervention.getId()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME
                    + " (ID_Padrone, Nome_animale, Data_Appuntamento, Codice_microchip, Sigla_Operazione, Ambulatorio, Motivazione) VALUES (?, ?, ? ,?, ?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, intervention.getId_master());
                statement.setString(2, intervention.getAnimal_name());
                var date = intervention.getDate();
                statement.setDate(3, Utils.dateToSqlDate(date));
                statement.setString(4, intervention.getMicrochip());
                statement.setString(5, intervention.getId_operation());
                statement.setInt(6, intervention.getClinic());
                statement.setString(7, intervention.getMotivation());
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
    public static ObservableList<Intervention> getDataMeet() {

        ObservableList<Intervention> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Intervention(rs.getInt("Codice_Identificativo"), rs.getString("ID_Padrone"), rs.getString("Nome_animale"),
                        rs.getDate("Data_Appuntamento"), rs.getString("Codice_microchip"), rs.getString("Sigla_Operazione"),
                        rs.getInt("Ambulatorio"), rs.getString("Motivazione")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}

