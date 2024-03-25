package it.unibo.DB.db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.Utils;
import it.unibo.DB.db.ConnectionProvider;
import it.unibo.DB.model.Allergies;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class AllergiesTable {
    public static final String TABLE_NAME = "allergie";
    private final Connection connection;

    public AllergiesTable(final Connection connection) {
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
                            "Sigla_Allergia CHAR(40) NOT NULL REFERENCES allergie(Sigla)," +
                            "Antistaminico CHAR(40)," +
                            "Data_inizio DATE NOT NULL," +
                            "Periodo_dell_anno CHAR(40) NOT NULL," +
                            "Livello_di_gravità CHAR(1) NOT NULL," +
                            "Sintomo CHAR(40) NOT NULL," +
                            "Codice_microchip CHAR(40) NOT NULL," +
                            "CONSTRAINT allergia_FKEY FOREIGN KEY(Codice_microchip) REFERENCES animale(ID)," +
                            "CONSTRAINT allergia_PKEY PRIMARY KEY(Sigla_Allergia, Data_inizio) " +
                            ")");
            return true;
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
            return false;
        }
    }

    public Optional<Allergies> findByPrimaryKey(final String id, final Date date, final String microchip) {
        final var query = "SELECT * FROM " + TABLE_NAME
                + " WHERE  Sigla_Allergia = ? AND Data_inizio = ? AND Codice_microchip = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setDate(2, Utils.dateToSqlDate(date));
            statement.setString(3, microchip);
            final var resultSet = statement.executeQuery();
            return readAllergiesFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    public ObservableList<Allergies> findByMicro(final String microchip) {
        ObservableList<Allergies> list = FXCollections.observableArrayList();
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_microchip = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, microchip);
            final var rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Allergies(rs.getString("Sigla_Allergia"), rs.getString("Antistaminico") == null ? null : Optional.of(rs.getString("Antistaminico")),
                        rs.getString("Periodo_dell_anno"), rs.getInt("Livello_di_gravità"), rs.getString("Sintomo"),
                        rs.getString("Codice_microchip"), rs.getDate("Data_inizio")));
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
    private List<Allergies> readAllergiesFromResultSet(final ResultSet resultSet) {
        final List<Allergies> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id_a = resultSet.getString("Sigla_Allergia");
                var date = resultSet.getDate("Data_inizio");
                var antihistamine = resultSet.getString("Antistaminico");
                var level = resultSet.getInt("Livello_di_gravità");
                var year = resultSet.getString("Periodo_dell_anno");
                var microchip = resultSet.getString("Codice_microchip");
                var symptom = resultSet.getString("Sintomo");
                Allergies allergies = new Allergies(id_a, Optional.of(antihistamine), year, level, symptom, microchip,
                        date);
                list.add(allergies);

            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    public List<Allergies> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readAllergiesFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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

    public boolean save(final Allergies allergies) {
        if (findByPrimaryKey(allergies.getId(), allergies.getDate(), allergies.getMicrochip()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME
                    + " (Sigla_Allergia, Antistaminico, Data_inizio, Periodo_dell_anno, Livello_di_gravità, Sintomo, Codice_microchip) VALUES (?, ?, ? ,?, ?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, allergies.getId());
                statement.setString(2,
                        allergies.getAntihistamine().isPresent() ? allergies.getAntihistamine().get() : null);
                var date = allergies.getDate();
                statement.setDate(3, Utils.dateToSqlDate(date));
                statement.setString(4, allergies.getYear());
                statement.setInt(5, allergies.getLevel_gravity());
                statement.setString(6, allergies.getSympton());
                statement.setString(7, allergies.getMicrochip());
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        }

    }

    public boolean delete(final String id, Date date, final String microchip) {
        if (findByPrimaryKey(id, date, microchip).isPresent()) {
            final var query = "DELETE FROM " + TABLE_NAME
                    + " WHERE Sigla_Allergia = ? AND Data_inizio = ? AND Codice_microchip";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, id);
                statement.setDate(2, Utils.dateToSqlDate(date));
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean update(final Allergies allergies) {
        if (findByPrimaryKey(allergies.getId(), allergies.getDate(), allergies.getMicrochip()).isPresent()) {
            final var query = "UPDATE " + TABLE_NAME + " SET " + "Antistaminico = ?, " + "Periodo_dell_anno = ?,"
                    + "Livello_di_gravità = ?" + "Sintomo = ?" + "Codice_microchip = ?"
                    + "WHERE Sigla_Allergia = ? AND Datanizio = ? ";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1,
                        allergies.getAntihistamine().isPresent() ? allergies.getAntihistamine().get() : null);
                statement.setString(2, allergies.getYear());
                statement.setInt(3, allergies.getLevel_gravity());
                statement.setString(4, allergies.getSympton());
                statement.setString(5, allergies.getMicrochip());
                statement.setString(6, allergies.getId());
                var date = allergies.getDate();
                statement.setDate(7, Utils.dateToSqlDate(date));
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

    public static ObservableList<Allergies> getDataMeet() {

        ObservableList<Allergies> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Allergies(rs.getString("Sigla_Allergia"), Optional.of(rs.getString("Antistaminico")),
                        rs.getString("Periodo_dell_anno"), rs.getInt("Livello_di_gravità"), rs.getString("Sintomo"),
                        rs.getString("Codice_microchip"), rs.getDate("Data_inizio")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}