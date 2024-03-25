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
import it.unibo.DB.model.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class RecipeTable {
    public static final String TABLE_NAME = "ricetta";
    private final Connection connection;

    public RecipeTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public Optional<Recipe> findByPrimaryKey(final Integer id) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_Identificativo = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, id);
            final var resultSet = statement.executeQuery();
            return readExaminationFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    public ObservableList<Recipe> findByMicro(final String microchip) {
        ObservableList<Recipe> list = FXCollections.observableArrayList();
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice_microchip = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, microchip);
            final var rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Recipe(rs.getInt("Codice_Identificativo"), rs.getString("ID_Padrone"),
                        rs.getString("Nome_animale"), rs.getDate("Data_Appuntamento"), rs.getString("Codice_microchip"),
                        rs.getString("Sigla_Farmaco"), rs.getInt("Ambulatorio"), rs.getString("Durata_terapia"),
                        rs.getString("Dose_giornaliera"), rs.getDate("Data_di_scadenza")));
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
    private List<Recipe> readExaminationFromResultSet(final ResultSet resultSet) {
        final List<Recipe> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id = resultSet.getInt("Codice_Identificativo");
                var id_master = resultSet.getString("ID_Padrone");
                var name_animal = resultSet.getString("Nome_animale");
                var date = resultSet.getDate("Data_Appuntamento");
                var microchip = resultSet.getString("Codice_microchip");
                var id_e = resultSet.getString("Sigla_Farmaco");
                var clinic = resultSet.getInt("Ambulatorio");
                var dose = resultSet.getString("Dose_giornaliera");
                var duration = resultSet.getString("Durata_terapia");
                var dateF = resultSet.getDate("Data_di_scadenza");
                Recipe recipe = new Recipe(id, id_master, name_animal, date, microchip, id_e, clinic, duration, dose,
                        dateF);
                list.add(recipe);

            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }

    public List<Recipe> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readExaminationFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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

    public boolean save(final Recipe recipe) {
        if (findByPrimaryKey(recipe.getId()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME
                    + " (ID_Padrone, Nome_animale, Data_Appuntamento, Codice_microchip, Sigla_Farmaco, Ambulatorio, Dose_giornaliera, Durata_terapia, Data_di_scadenza) VALUES (?, ?, ? ,?, ?, ?, ?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, recipe.getId_master());
                statement.setString(2, recipe.getAnimal_name());
                var date = recipe.getDateA();
                statement.setDate(3, Utils.dateToSqlDate(date));
                statement.setString(4, recipe.getMicrochip());
                statement.setString(5, recipe.getId_drug());
                statement.setInt(6, recipe.getClinic());
                statement.setString(7, recipe.getDose());
                statement.setString(8, recipe.getDuration());
                var dateF = recipe.getDateS();
                statement.setDate(9, Utils.dateToSqlDate(dateF));
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
    public static ObservableList<Recipe> getDataMeet() {

        ObservableList<Recipe> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Recipe(rs.getInt("Codice_Identificativo"), rs.getString("ID_Padrone"),
                        rs.getString("Nome_animale"), rs.getDate("Data_Appuntamento"), rs.getString("Codice_microchip"),
                        rs.getString("Sigla_Farmaco"), rs.getInt("Ambulatorio"), rs.getString("Durata_terapia"),
                        rs.getString("Dose_giornaliera"), rs.getDate("Data_di_scadenza")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}
