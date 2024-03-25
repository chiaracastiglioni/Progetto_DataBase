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
import it.unibo.DB.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public final class PersonTable implements Table<Person, String> {
    public static final String TABLE_NAME = "proprietario";
    private final Connection connection;

    public PersonTable(final Connection connection) {
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
                            "Codice_Fiscale CHAR(40) NOT NULL PRIMARY KEY," +
                            "Nome CHAR(40) NOT NULL," +
                            "Cognome CHAR(40) NOT NULL," +
                            "Numero_di_telefono CHAR(40) NOT NULL," +
                            "Email CHAR(40) NOT NULL" +
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
    public Optional<Person> findByPrimaryKey(final String id) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_Fiscale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id); /* Imposta a id il primo ? */
            final var resultSet = statement.executeQuery();
            return readStudentsFromResultSet(resultSet).stream().findFirst();
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
    private List<Person> readStudentsFromResultSet(final ResultSet resultSet) {
        final List<Person> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id = resultSet.getString("Codice_Fiscale");
                var name = resultSet.getString("Nome");
                var lastName = resultSet.getString("Cognome");
                var number = resultSet.getString("Numero_di_telefono");
                var email = resultSet.getString("Email");
                Person person = new Person(id, name, lastName, number, email);
                list.add(person);


            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    @Override
    public List<Person> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readStudentsFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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
    public boolean save(final Person person) {
        if (findByPrimaryKey(person.getId()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME + " (Nome, Cognome, Numero_di_telefono, Codice_Fiscale, Email) VALUES (?, ?, ?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, person.getFirstName());
                statement.setString(2, person.getLastName());
                statement.setString(3, person.getNumber());
                statement.setString(4, person.getId());
                statement.setString(5, person.getEmail());
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        }

    }

    @Override
    public boolean delete(final String id) {
        if (findByPrimaryKey(id).isPresent()) {
            final var query = "DELETE FROM " + TABLE_NAME + " WHERE Codice_Fiscale = ?";
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
    public boolean update(final Person person) {
        if (findByPrimaryKey(person.getId()).isPresent()) {
            final var query = "UPDATE " + TABLE_NAME + " SET " + "Nome = ?" + "Cognome = ?" + "Numero_di_telefono = ?" + "Email = ?"
                    + "WHERE Codice_Fiscale = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, person.getFirstName());
                statement.setString(2, person.getLastName());
                statement.setString(3, person.getNumber());
                statement.setString(4, person.getEmail());
                statement.setString(6, person.getId());
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

    public static ObservableList<Person> getDataClient() {
       
        ObservableList<Person> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Person(rs.getString("Codice_Fiscale"), rs.getString("Nome"), rs.getString("Cognome"),rs.getString("Numero_di_telefono"), rs.getString("Email")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}
