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
import it.unibo.DB.model.Recovery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class RecoveryTable {
    public static final String TABLE_NAME = "ricovero";
    private final Connection connection;

    public RecoveryTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public Optional<Recovery> findByPrimaryKey(final String microchip, final String date) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE  Codice_microchip = ? AND Data_d_inizio = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, microchip);
            statement.setString(2, date);
            final var resultSet = statement.executeQuery();
            return readRecoveryFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    public Optional<Recovery> recoveryDateValid(final String date, final String microchip) {
        final var query = "SELECT * FROM ricovero WHERE Codice_microchip = ? AND Data_d_inizio <= ? AND Data_di_fine >= ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, microchip);
            statement.setString(2, date);
            statement.setString(3, date);
            final var resultSet = statement.executeQuery();
            return readRecoveryFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    public Optional<Recovery> recoveryDateValidRange(final String date, final String microchip, final String dateF) {
        final var query = "SELECT * FROM ricovero WHERE Codice_microchip = ? AND Data_d_inizio >= ? AND Data_di_fine <= ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, microchip);
            statement.setString(2, date);
            statement.setString(3, dateF);
            final var resultSet = statement.executeQuery();
            return readRecoveryFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    public ObservableList<Recovery> findByMicro(final String microchip) {
        ObservableList<Recovery> list = FXCollections.observableArrayList();
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE  Codice_microchip = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, microchip);
            final var rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Recovery(rs.getString("Codice_microchip"), rs.getString("Data_d_inizio"),
                        rs.getString("Data_di_fine"), rs.getInt("Range_Inizio"),
                        rs.getInt("Range_Fine")));
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
    private List<Recovery> readRecoveryFromResultSet(final ResultSet resultSet) {
        final List<Recovery> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var microchip = resultSet.getString("Codice_microchip");
                var dateS = resultSet.getString("Data_d_inizio");
                var dateF = resultSet.getString("Data_di_fine");
                var range_start = resultSet.getInt("Range_Inizio");
                var range_stop = resultSet.getInt("Range_Fine");
                Recovery recovery = new Recovery(microchip, dateS, dateF, range_start, range_stop);
                list.add(recovery);

            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    public List<Recovery> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readRecoveryFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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

    public boolean save(final Recovery recovery) {
        if (findByPrimaryKey(recovery.getMicrochip(), recovery.getDate_Start()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME
                    + " (Codice_microchip, Data_d_inizio, Data_di_fine, Range_Inizio, Range_Fine) VALUES (?, ?, ? ,?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, recovery.getMicrochip());
                statement.setString(2, recovery.getDate_Start());
                var dateF = recovery.getDate_Stop();
                statement.setString(3, dateF);
                statement.setInt(4, recovery.getRange_Start());
                statement.setInt(5, recovery.getRange_Stop());
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        }

    }

    public boolean delete(final String microchip, final String date) {
        if (findByPrimaryKey(microchip, date).isPresent()) {
            final var query = "DELETE FROM " + TABLE_NAME + " WHERE Codice_microchip = ? AND Data_d_inizio = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, microchip);
                statement.setTimestamp(2, Utils.dateToSqlDateHours(Utils.buildDate(date).get()));
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean update(final Recovery recovery) {
        if (findByPrimaryKey(recovery.getMicrochip(), recovery.getDate_Start()).isPresent()) {
            final var query = "UPDATE " + TABLE_NAME + " SET " + "Data_di_fine = ?," + "Range_Inizio = ?"
                    + "Range_Fine = ?"
                    + "WHERE Codice_microchip = ? AND Data_d_inizio = ? ";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(4, recovery.getMicrochip());
                statement.setString(5, recovery.getDate_Start());
                statement.setString(1, recovery.getDate_Stop());
                statement.setInt(2, recovery.getRange_Start());
                statement.setInt(3, recovery.getRange_Stop());
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

    public static ObservableList<Recovery> getDataMeet() {

        ObservableList<Recovery> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Recovery(rs.getString("Codice_microchip"), rs.getString("Data_d_inizio"),
                        rs.getString("Data_di_fine"), rs.getInt("Range_Inizio"),
                        rs.getInt("Range_Fine")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}