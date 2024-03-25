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
import it.unibo.DB.db.Table;
import it.unibo.DB.model.Veterinary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public final class VeterinaryTable implements Table<Veterinary, String> {
    public static final String TABLE_NAME = "medico";
    private final Connection connection;

    public VeterinaryTable(final Connection connection) {
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
                            "Specializzazione CHAR(40) NOT NULL," +
                            "Data_di_assunzione DATE NOT NULL," +
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
    public Optional<Veterinary> findByPrimaryKey(final String id) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_Fiscale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id); /* Imposta a id il primo ? */
            final var resultSet = statement.executeQuery();
            return readVeterinaryFromResultSet(resultSet).stream().findFirst();
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
    private List<Veterinary> readVeterinaryFromResultSet(final ResultSet resultSet) {
        final List<Veterinary> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id = resultSet.getString("Codice_Fiscale");
                var name = resultSet.getString("Nome");
                var lastName = resultSet.getString("Cognome");
                var number = resultSet.getString("Numero_di_telefono");
                var email = resultSet.getString("Email");
                var specialization = resultSet.getString("Specializzazione");
                var date = resultSet.getDate("Data_di_assunzione");
                Veterinary vet = new Veterinary(id, name, lastName, number, email, date, specialization);
                list.add(vet);


            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    @Override
    public List<Veterinary> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readVeterinaryFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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
    public boolean save(final Veterinary vet) {
        if (findByPrimaryKey(vet.getId()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME + " (Nome, Cognome, Numero_di_telefono, Codice_Fiscale, Email, Data_di_assunzione, Specializzazione) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1,vet.getFirstName());
                statement.setString(2,vet.getLastName());
                statement.setString(3,vet.getNumber());
                statement.setString(4,vet.getId());
                statement.setString(5,vet.getEmail());
                var hireDate = vet.getHireDate();
                statement.setDate(6, Utils.dateToSqlDate(hireDate));
                statement.setString(7, vet.getSpecialization());
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
    /*Non sistemato */
    @Override
    public boolean update(final Veterinary vet) {
        if (findByPrimaryKey(vet.getId()).isPresent()) {
            final var query = "UPDATE " + TABLE_NAME + " SET " + "Nome = ?" + "Cognome = ?" + "Numero_di_telefono = ?" + "Email = ?" + "Specializzazione = ?"
                    + "WHERE ID_Padrone = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1,vet.getFirstName());
                statement.setString(2,vet.getLastName());
                statement.setString(3,vet.getNumber());
                statement.setString(4,vet.getEmail());
                statement.setString(5, vet.getSpecialization());
                statement.setString(6,vet.getId());
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

    public static ObservableList<Veterinary> getDataVeterinary() {
       
        ObservableList<Veterinary> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Veterinary(rs.getString("Codice_Fiscale"), rs.getString("Nome"), rs.getString("Cognome"),rs.getString("Numero_di_telefono"), rs.getString("Email"), rs.getDate("Data_di_assunzione"), rs.getString("Specializzazione")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

    
}

