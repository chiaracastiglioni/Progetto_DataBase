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
import it.unibo.DB.model.Vax;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public final class VaxTable implements Table<Vax, String> {
    public static final String TABLE_NAME = "vaccino";
    private final Connection connection;

    public VaxTable(final Connection connection) {
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
                            "Periodo_di_richiamo CHAR(40) NOT NULL," +
                            "Obbligatorietà CHAR(2) NOT NULL," +
                            "Specie_animale CHAR(40) NOT NULL," +
                            "Malattia CHAR(40) NOT NULL" +
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
    public Optional<Vax> findByPrimaryKey(final String id) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Sigla = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id); /* Imposta a id il primo ? */
            final var resultSet = statement.executeQuery();
            return readVaxFromResultSet(resultSet).stream().findFirst();
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
    private List<Vax> readVaxFromResultSet(final ResultSet resultSet) {
        final List<Vax> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id = resultSet.getString("Sigla");
                var recall = resultSet.getString("Periodo_di_richiamo");
                var mandatory = resultSet.getString("Obbligatorietà");
                var animal_species = resultSet.getString("Specie_animale");
                var illness = resultSet.getString("Malattia");
                Vax vax = new Vax(id, recall, mandatory, animal_species, illness);
                list.add(vax);


            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    @Override
    public List<Vax> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readVaxFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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
    public boolean save(final Vax vax) {
        if (findByPrimaryKey(vax.getId()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME + " (Sigla, Periodo_di_richiamo, Obbligatorietà, Specie_animale, Malattia) VALUES (?, ?, ?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, vax.getId());
                statement.setString(2, vax.getRecall());
                statement.setString(3, vax.getMandatory());
                statement.setString(4, vax.getAnimal_species());
                statement.setString(5, vax.getIllness());
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
    public boolean update(final Vax vax) {
        if (findByPrimaryKey(vax.getId()).isPresent()) {
            final var query = "UPDATE " + TABLE_NAME + " SET " + "Periodo_di_richiamo = ?" + "Obbligatorietà = ?" + "Specie_animale = ?" + "Malattia = ?"
                    + "WHERE Sigla = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, vax.getRecall());
                statement.setString(2, vax.getMandatory());
                statement.setString(3, vax.getAnimal_species());
                statement.setString(4, vax.getIllness());
                statement.setString(5, vax.getId());
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

    public static ObservableList<Vax> getDataVax() {
       
        ObservableList<Vax> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Vax(rs.getString("Sigla"), rs.getString("Periodo_di_richiamo"), rs.getString("Obbligatorietà"),rs.getString("Specie_animale"), rs.getString("Malattia")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}
