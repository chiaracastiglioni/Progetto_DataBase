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
import it.unibo.DB.model.Surgery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public final class SurgeryTable implements Table<Surgery, String> {
    public static final String TABLE_NAME = "operazione";
    private final Connection connection;

    public SurgeryTable(final Connection connection) {
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
                            "Descrizione CHAR(40) NOT NULL," +
                            "Parte_del_corpo CHAR(40) NOT NULL" +
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
    public Optional<Surgery> findByPrimaryKey(final String id) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Sigla = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id); /* Imposta a id il primo ? */
            final var resultSet = statement.executeQuery();
            return readSurgeryFromResultSet(resultSet).stream().findFirst();
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
    private List<Surgery> readSurgeryFromResultSet(final ResultSet resultSet) {
        final List<Surgery> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id = resultSet.getString("Sigla");
                var body = resultSet.getString("Parte_del_corpo");
                var description = resultSet.getString("Descrizione");
                Surgery surgery = new Surgery(id, body, description);
                list.add(surgery);
            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    @Override
    public List<Surgery> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readSurgeryFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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
    public boolean save(final Surgery surgery) {
        if (findByPrimaryKey(surgery.getId()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME + " (Sigla, Parte_del_corpo, Descrizione) VALUES (?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, surgery.getId());
                statement.setString(2, surgery.getBody());
                statement.setString(3, surgery.getDescription());
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
    public boolean update(final Surgery surgery) {
        if (findByPrimaryKey(surgery.getId()).isPresent()) {
            final var query = "UPDATE " + TABLE_NAME + " SET " + "Parte_del_corpo = ?" + "Descrizione = ?" 
                    + "WHERE Sigla = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, surgery.getBody());
                statement.setString(2, surgery.getDescription());
                statement.setString(3, surgery.getId());
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

    public static ObservableList<Surgery> getDataSurgery() {
       
        ObservableList<Surgery> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Surgery(rs.getString("Sigla"), rs.getString("Parte_del_corpo"), rs.getString("Descrizione")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}

