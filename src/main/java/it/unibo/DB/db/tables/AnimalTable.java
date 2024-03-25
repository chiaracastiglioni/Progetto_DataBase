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
import it.unibo.DB.model.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;




public final class AnimalTable implements Table<Animal, String> {
    public static final String TABLE_NAME = "animale";
    private final Connection connection;

    public AnimalTable(final Connection connection) {
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
                            "Codice_microchip CHAR(40) NOT NULL PRIMARY KEY," +
                            "Nome CHAR(40) NOT NULL," +
                            "Razza CHAR(40) NOT NULL," +
                            "Specie_animale CHAR(40) NOT NULL," +
                            "Data_di_nascita DATE NOT NULL," +
                            "Sesso CHAR(1) NOT NULL," +
                            "ID_Padrone CHAR(40) NOT NULL," +
                            "CONSTRAINT animale_FKEY FOREIGN KEY(ID_Padrone) REFERENCES cliente(ID_Padrone)" + 
                            ")");
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
    public Optional<Animal> findByPrimaryKey(final String id) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_microchip = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id); /* Imposta a id il primo ? */
            final var resultSet = statement.executeQuery();
            return readAnimalFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    public ObservableList<Animal> findByName(final String name) {
        ObservableList<Animal> list = FXCollections.observableArrayList();
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Nome = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, name);
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(new Animal(rs.getString("Codice_microchip"), rs.getString("Nome"), rs.getString("Razza"), rs.getString("Specie_animale"), rs.getString("Sesso"), rs.getString("ID_Padrone"), rs.getDate("Data_di_nascita")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;

    }



    /**
     * Given a ResultSet read all the students in it and collects them in a List
     * 
     * @param resultSet a ResultSet from which the Student(s) will be extracted
     * @return a List of all the students in the ResultSet
     */
    private List<Animal> readAnimalFromResultSet(final ResultSet resultSet) {
        final List<Animal> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id = resultSet.getString("Codice_microchip");
                var name = resultSet.getString("Nome");
                var race = resultSet.getString("Razza");
                var specie_animale = resultSet.getString("Specie_animale");
                var gender = resultSet.getString("Sesso");
                var master = resultSet.getString("ID_Padrone");
                var birthDate = resultSet.getDate("Data_di_nascita");
                Animal animal = new Animal(id, name, race, specie_animale, gender, master, birthDate);
                list.add(animal);

            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    @Override
    public List<Animal> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readAnimalFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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
    public boolean save(final Animal animal) {
        if (findByPrimaryKey(animal.getId()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME + " (Codice_microchip, Nome, Razza, Specie_animale, Sesso, ID_Padrone, Data_di_nascita) VALUES (?, ?, ? ,?, ?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, animal.getId());
                statement.setString(2, animal.getName());
                statement.setString(3, animal.getRace());
                statement.setString(4, animal.getAnimal_species());
                statement.setString(5, animal.getGender());
                statement.setString(6, animal.getId_m());
                var birthDate = animal.getBirthday();
                statement.setDate(7, Utils.dateToSqlDate(birthDate));
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        }

    }

    public int findByMaster(final String id_master) {

        final var query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE ID_Padrone = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id_master);
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery();
            rs.next();
            int num = rs.getInt(1);
            return num;
        } catch (final SQLException e) {
            return -1;
        }
        
    }

    @Override
    public boolean delete(final String id) {
        if (findByPrimaryKey(id).isPresent()) {
            final var query = "DELETE FROM " + TABLE_NAME + " WHERE Codice_microchip = ?";
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
    public boolean update(final Animal animal) {
        if (findByPrimaryKey(animal.getId()).isPresent()) {
            final var query = "UPDATE " + TABLE_NAME + " SET " + "Nome = ?," + "Razza = ?," + "Specie_animale = ? " + "Sesso = ?" 
                    + "WHERE Codice_microchip = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(5, animal.getId());
                statement.setString(1, animal.getName());
                statement.setString(2, animal.getRace());
                statement.setString(3, animal.getAnimal_species());
                statement.setString(4, animal.getGender());
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

    public static ObservableList<Animal> getDataAnimal() {
       
        ObservableList<Animal> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Animal(rs.getString("Codice_microchip"), rs.getString("Nome"), rs.getString("Razza"), rs.getString("Specie_animale"), rs.getString("Sesso"), rs.getString("ID_Padrone"), rs.getDate("Data_di_nascita")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

    public Optional<Animal> findByMasterAnimalName(String id_master, String animal_name) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Padrone = ? AND Nome = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id_master); 
            statement.setString(2, animal_name);
            final var resultSet = statement.executeQuery();
            return readAnimalFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }


}