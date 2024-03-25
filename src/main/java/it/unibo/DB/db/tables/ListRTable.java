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
import it.unibo.DB.model.ListR;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public final class ListRTable {
    public static final String TABLE_NAME = "listino_ricovero";
    private final Connection connection;

    public ListRTable(final Connection connection) {
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
                            "Range_Inizio INT NOT NULL," +
                            "Range_Fine INT NOT NULL," +
                            "Prezzo CHAR(40) NOT NULL," +
                            "CONSTRAINT listino_ricovero_PKEY PRIMARY KEY(Range_Inizio, Range_Fine) " + 
                            ") ");
            return true;
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
            return false;
        }
    }
    


    

    public Optional<ListR> findByPrimaryKey(final Integer range_Start, final Integer range_Stop) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE Range_Inizio = ? AND Range_Fine = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, range_Start); 
            statement.setInt(2, range_Stop);
            final var resultSet = statement.executeQuery();
            return readListFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    
    private List<ListR> readListFromResultSet(final ResultSet resultSet) {
        final List<ListR> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var range_Start = resultSet.getInt("Range_Inizio");
                var range_Stop = resultSet.getInt("Range_Fine");
                var price = resultSet.getString("Prezzo");
                ListR listR = new ListR(range_Start, range_Stop, price);
                list.add(listR);
            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }


    public List<ListR> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readListFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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


    public boolean save(final ListR listR) {
        if (findByPrimaryKey(listR.getRange_Start(), listR.getRange_Stop()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME + " (Range_Inizio, Range_Fine, Prezzo) VALUES (?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setInt(1, listR.getRange_Start());
                statement.setInt(2, listR.getRange_Stop());
                statement.setString(3, listR.getPrice());
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        }

    }


    public boolean delete(final Integer range_Start, final Integer range_Stop) {
        if (findByPrimaryKey(range_Start, range_Stop).isPresent()) {
            final var query = "DELETE FROM " + TABLE_NAME + " WHERE Range_Inizio = ? AND Range_Fine = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setInt(1, range_Start); 
                statement.setInt(2, range_Stop);
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

    public static ObservableList<ListR> getDataListR() {
       
        ObservableList<ListR> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new ListR(rs.getInt("Range_Inizio"), rs.getInt("Range_Fine"), rs.getString("Prezzo")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

}
