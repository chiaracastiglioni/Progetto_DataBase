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

import it.unibo.DB.db.ConnectionProvider;
import it.unibo.DB.db.Table;
import it.unibo.DB.model.Illnes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public final class IllnesTable implements Table<Illnes, String> {
    public static final String TABLE_NAME = "malattia";
    private final Connection connection;

    public IllnesTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean createTable() {
        // 1. Create the statement from the open connection inside a try-with-resources
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            statement.executeUpdate(
                    "CREATE TABLE " + TABLE_NAME + " (" +
                            "Sigla CHAR(40) NOT NULL PRIMARY KEY," +
                            "Nome CHAR(40) NOT NULL," +
                            "Sintomo_comune CHAR(40) NOT NULL" +
                            ") ");
            return true;
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
            return false;
        }
    }
    


    /*
     * Studente che presenta quella chiave, ritorniamo un optional perché non è
     * detto che ci sia uno studente con quella chiave
     */
    @Override
    public Optional<Illnes> findByPrimaryKey(final String id) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Sigla = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id); /* Imposta a id il primo ? */
            final var resultSet = statement.executeQuery();
            return readIllnesFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    /**
     * Given a ResultSet read all the students in it and collects them in a List
     * 
     * @param resultSet a ResultSet from which the Student(s) will be extracted
     * @return a List of all the students in the ResultSet
     */
    private List<Illnes> readIllnesFromResultSet(final ResultSet resultSet) {
        final List<Illnes> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id = resultSet.getString("Sigla");
                var name = resultSet.getString("Nome");
                var common_s = resultSet.getString("Sintomo_comune");
                Illnes illnes = new Illnes(id, name, common_s);
                list.add(illnes);
            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    @Override
    public List<Illnes> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readIllnesFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
            return List.of();
        }
    }

    @Override
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

    @Override
    public boolean save(final Illnes illnes) {
        if (findByPrimaryKey(illnes.getId()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME + " (Sigla, Nome, Sintomo_comune) VALUES (?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, illnes.getId());
                statement.setString(2, illnes.getName());
                statement.setString(3, illnes.getCommon_symptom());
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        }

    }

    @Override
    public boolean delete(final String id) {
        if (findByPrimaryKey(id).isPresent()) {
            final var query = "DELETE FROM " + TABLE_NAME + " WHERE Sigla = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, id);
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean update(final Illnes illnes) {
        if (findByPrimaryKey(illnes.getId()).isPresent()) {
            final var query = "UPDATE " + TABLE_NAME + " SET " + "Nome = ?" + "Sintomo_comune = ?" 
                    + "WHERE Sigla = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, illnes.getName());
                statement.setString(2, illnes.getCommon_symptom());
                statement.setString(3, illnes.getId());
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

    public static ObservableList<Illnes> getDataIllnes() {
       
        ObservableList<Illnes> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Illnes(rs.getString("Sigla"), rs.getString("Nome"), rs.getString("Sintomo_comune")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}


