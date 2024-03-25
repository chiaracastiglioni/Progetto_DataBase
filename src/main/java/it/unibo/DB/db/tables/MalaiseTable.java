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
import it.unibo.DB.model.Malaise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class MalaiseTable {
    public static final String TABLE_NAME = "malessere";
    private final Connection connection;

    public MalaiseTable(final Connection connection) {
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
                            "Sigla_Malattia CHAR(40) NOT NULL REFERENCES malattie(Sigla)," +
                            "Data_fine_malattia DATE," +
                            "Data_inizio_malattia DATE NOT NULL," +
                            "Sintomo CHAR(40) NOT NULL," +
                            "Codice_microchip CHAR(40) NOT NULL," +
                            ")");
            return true;
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
            return false;
        }
    }

    public Optional<Malaise> findByPrimaryKey(final String id, final Date date, final String microchip) {
        final var query = "SELECT * FROM " + TABLE_NAME
                + " WHERE  Sigla_Malattia = ? AND Data_inizio_malattia = ? AND Codice_microchip = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setDate(2, Utils.dateToSqlDate(date));
            statement.setString(3, microchip);
            final var resultSet = statement.executeQuery();
            return readMalaiseFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    public ObservableList<Malaise> findByMicro(final String microchip) {
        ObservableList<Malaise> list = FXCollections.observableArrayList();
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_microchip = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, microchip);
            final var rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Malaise(rs.getString("Sigla_Malattia"), rs.getDate("Data_inizio_malattia"),
                        rs.getDate("Data_fine_malattia") == null ? null : Optional.of(rs.getDate("Data_fine_malattia")), rs.getString("Sintomo"),
                        rs.getString("Codice_microchip")));
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
    private List<Malaise> readMalaiseFromResultSet(final ResultSet resultSet) {
        final List<Malaise> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id_m = resultSet.getString("Sigla_Malattia");
                var dateS = resultSet.getDate("Data_inizio_malattia");
                var dateF = resultSet.getDate("Data_fine_malattia");
                var microchip = resultSet.getString("Codice_microchip");
                var symptom = resultSet.getString("Sintomo");
                if (dateF == null) {
                    Malaise malaise = new Malaise(id_m, dateS, Optional.empty(), symptom, microchip);
                    list.add(malaise);
                } else {
                    Malaise malaise = new Malaise(id_m, dateS, Optional.of(dateF), symptom, microchip);
                    list.add(malaise);
                }

            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    public List<Malaise> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readMalaiseFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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

    public boolean save(final Malaise malaise) {
        if (findByPrimaryKey(malaise.getId(), malaise.getDate_start(), malaise.getMicrochip()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME
                    + " (Sigla_Malattia, Data_fine_malattia, Data_inizio_malattia, Sintomo, Codice_microchip) VALUES (?, ?, ? ,?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, malaise.getId());
                var dateF = malaise.getOptionalDate_finish();
                statement.setDate(2, dateF.isPresent() ? Utils.dateToSqlDate(dateF.get()) : null);
                var dateS = malaise.getDate_start();
                statement.setDate(3, Utils.dateToSqlDate(dateS));
                statement.setString(4, malaise.getSymptom());
                statement.setString(5, malaise.getMicrochip());
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        }

    }

    public boolean delete(final String id, Date date, final String microchip) {
        if (findByPrimaryKey(id, date, microchip).isPresent()) {
            final var query = "DELETE FROM " + TABLE_NAME
                    + " WHERE Sigla_Malattia = ? AND Data_inizio_malattia = ? AND Codice_microchip = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, id);
                statement.setDate(2, Utils.dateToSqlDate(date));
                statement.setString(3, microchip);
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean update(final Malaise malaise) {
        if (findByPrimaryKey(malaise.getId(), malaise.getDate_start(), malaise.getMicrochip()).isPresent()) {
            final var query = "UPDATE " + TABLE_NAME + " SET " + "Data_fine_malattia = ?," + "Sintomo = ?"
                    + "WHERE Sigla_Malattia = ? AND Data_inizio_malattia = ?  AND Codice_microchip = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                var dateF = malaise.getOptionalDate_finish();
                statement.setDate(1, dateF.isPresent() ? Utils.dateToSqlDate(dateF.get()) : null);
                statement.setString(2, malaise.getSymptom());
                statement.setString(5, malaise.getMicrochip());
                statement.setString(3, malaise.getId());
                var dateS = malaise.getDate_start();
                statement.setDate(4, Utils.dateToSqlDate(dateS));
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                e.printStackTrace();
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

    public static ObservableList<Malaise> getDataMeet() {

        ObservableList<Malaise> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Malaise(rs.getString("Sigla_Malattia"), rs.getDate("Data_inizio_malattia"),
                        Optional.of(rs.getDate("Data_fine_malattia")), rs.getString("Sintomo"),
                        rs.getString("Codice_microchip")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}