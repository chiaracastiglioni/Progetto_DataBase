package it.unibo.DB.db.tables;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


import it.unibo.Utils;
import it.unibo.DB.db.ConnectionProvider;
import it.unibo.DB.model.Meet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;




public final class MeetTable {
    public static final String TABLE_NAME = "appuntamento";
    private final Connection connection;

    public MeetTable(final Connection connection) {
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
                            "ID_Padrone CHAR(40) NOT NULL REFERENCES cliente(ID_Padrone)," +
                            "Nome_animale CHAR(40) NOT NULL," +
                            "Data_Appuntamento DATE NOT NULL," +
                            "Orario DOUBLE NOT NULL," +
                            "Prestazione_Effettuata CHAR(2) NOT NULL," +
                            "ID_Veterinario CHAR(40) NOT NULL," +
                            "CONSTRAINT appuntamento_FKEY FOREIGN KEY(ID_Veterinario) REFERENCES veterinario(ID_Veterinario)," +
                            "CONSTRAINT appuntamento_PKEY PRIMARY KEY(ID_Padrone, Nome_animale, Data_Appuntamento) " + 
                            ")");
            return true;
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
            return false;
        }
    }

  
    public Optional<Meet> findByPrimaryKey(final String id, final String animal_name, final Date date) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Padrone = ? AND Nome_animale = ? AND Data_Appuntamento = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id); 
            statement.setString(2, animal_name);
            statement.setDate(3, Utils.dateToSqlDate(date));
            final var resultSet = statement.executeQuery();
            return readMeetFromResultSet(resultSet).stream().findFirst();
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
    private List<Meet> readMeetFromResultSet(final ResultSet resultSet) {
        final List<Meet> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                var id_master = resultSet.getString("ID_Padrone");
                var name_animal = resultSet.getString("Nome_animale");
                var date = resultSet.getDate("Data_Appuntamento");
                var hours = resultSet.getDouble("Orario");
                var performance_e = resultSet.getString("Prestazione_Effettuata");
                var id_vet = resultSet.getString("ID_Veterinario");
                Meet meet = new Meet(id_master, name_animal, hours, performance_e, id_vet, date);
                list.add(meet);

            }
        } catch (final SQLException e) {
            return List.of();
        }
        return list;
    }
    
    public Optional<Meet> findDublicate(String id_vet, double hour, Date date) {
        final var query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Veterinario = ? AND Orario = ? AND Data_Appuntamento = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id_vet); 
            statement.setDouble(2, hour);
            statement.setDate(3, Utils.dateToSqlDate(date));
            final var resultSet = statement.executeQuery();
            return readMeetFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }


    public List<Meet> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            // 2. Execute the statement with the given query
            return readMeetFromResultSet(statement.executeQuery("SELECT * FROM " + TABLE_NAME));
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


    public boolean save(final Meet meet) {
        if (findByPrimaryKey(meet.getId_master(), meet.getAnimalName(), meet.getDate()).isPresent()) {
            return false;
        } else {
            final var query = "INSERT INTO " + TABLE_NAME + " (ID_Padrone, Nome_animale, Data_Appuntamento, Orario, Prestazione_Effettuata, ID_Veterinario) VALUES (?, ?, ? ,?, ?, ?)";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, meet.getId_master());
                statement.setString(2, meet.getAnimalName());
                var date = meet.getDate();
                statement.setDate(3, Utils.dateToSqlDate(date));
                statement.setDouble(4, meet.getHours());
                statement.setString(5, meet.getPerformance_performed());
                statement.setString(6, meet.getId_vet());
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        }

    }


    public boolean delete(final String id, final String animal_name, Date date) {
        if (findByPrimaryKey(id, animal_name, date).isPresent()) {
            final var query = "DELETE FROM " + TABLE_NAME + " WHERE ID_Padrone = ? AND Nome_animale = ? AND Data_Appuntamento = ?";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, id);
                statement.setString(2, animal_name);;
                statement.setDate(3, Utils.dateToSqlDate(date));
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }


    public boolean update(final Meet meet) {
        if (findByPrimaryKey(meet.getId_master(), meet.getAnimalName(), meet.getDate()).isPresent()) {
            final var query = "UPDATE " + TABLE_NAME + " SET " + "Prestazione_Effettuata = ?, " + "Orario = ?," + "ID_Veterinario = ?"  
                    + "WHERE ID_Padrone = ? AND Nome_animale = ? AND Data_Appuntamento = ? ";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, "SI");
                statement.setDouble(2, meet.getHours());
                statement.setString(3, meet.getId_vet());
                statement.setString(4, meet.getId_master());
                statement.setString(5, meet.getAnimalName());
                var date = meet.getDate();
                statement.setDate(6, Utils.dateToSqlDate(date));
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean deleteUpdate(final Meet meet) {
        if (findByPrimaryKey(meet.getId_master(), meet.getAnimalName(), meet.getDate()).isPresent()) {
            final var query = "UPDATE " + TABLE_NAME + " SET " + "Prestazione_Effettuata = ?, " + "Orario = ?," + "ID_Veterinario = ?"  
                    + "WHERE ID_Padrone = ? AND Nome_animale = ? AND Data_Appuntamento = ? ";
            try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, "NO");
                statement.setDouble(2, meet.getHours());
                statement.setString(3, meet.getId_vet());
                statement.setString(4, meet.getId_master());
                statement.setString(5, meet.getAnimalName());
                var date = meet.getDate();
                statement.setDate(6, Utils.dateToSqlDate(date));
                return statement.executeUpdate() > 0;
            } catch (final SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public ObservableList<Meet> getNextMeet(final String id_vet) {
        ObservableList<Meet> list = FXCollections.observableArrayList();
        final var query = "SELECT * FROM appuntamento, proprietario WHERE proprietario.Codice_Fiscale = appuntamento.ID_Padrone AND ID_Veterinario = ?  AND Data_Appuntamento >= ? AND Orario >= ? AND Prestazione_effettuata = ? ORDER BY Data_Appuntamento, Orario LIMIT 1";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            // 2. Execute the statement with the given query
            
            statement.setString(1, id_vet);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf2 = new SimpleDateFormat("YYYY");
            SimpleDateFormat sdf3 = new SimpleDateFormat("HH");
            SimpleDateFormat sdf4 = new SimpleDateFormat("mm");
            String hours = sdf3.format(date) + "." + sdf4.format(date);
            Optional<Date> d = Utils.buildDateSimple(Integer.parseInt(sdf.format(date)), Integer.parseInt(sdf1.format(date)), Integer.parseInt(sdf2.format(date)));
            statement.setDate(2, Utils.dateToSqlDate(d.get()));
            statement.setDouble(3, Double.parseDouble(hours));
            statement.setString(4, "NO");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Meet meet = new Meet(rs.getString("ID_Padrone"), rs.getString("Nome_animale"), rs.getDouble("Orario"), rs.getString("Prestazione_Effettuata"), rs.getString("ID_Veterinario"), rs.getDate("Data_Appuntamento"));
                if(rs.getDouble("Orario") >= 10.0) {
                    String o = String.valueOf(rs.getDouble("Orario"));
                    meet.setHoursString(o);
                }else {
                    String h = "0" + String.valueOf(rs.getDouble("Orario"));
                    meet.setHoursString(h);
                }
                
                list.add(meet);
                return list;
            }
            return getNextMeet1(id_vet);
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

    public ObservableList<Meet> getNextMeet1(final String id_vet) {
        ObservableList<Meet> list = FXCollections.observableArrayList();
        final var query = "SELECT * FROM appuntamento, proprietario WHERE proprietario.Codice_Fiscale = appuntamento.ID_Padrone AND ID_Veterinario = ?  AND Data_Appuntamento > ? AND Prestazione_effettuata = ? ORDER BY Data_Appuntamento, Orario LIMIT 1";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            // 2. Execute the statement with the given query
            
            statement.setString(1, id_vet);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf2 = new SimpleDateFormat("YYYY");
            Optional<Date> d = Utils.buildDateSimple(Integer.parseInt(sdf.format(date)), Integer.parseInt(sdf1.format(date)), Integer.parseInt(sdf2.format(date)));
            statement.setDate(2, Utils.dateToSqlDate(d.get()));
            statement.setString(3, "NO");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Meet meet = new Meet(rs.getString("ID_Padrone"), rs.getString("Nome_animale"), rs.getDouble("Orario"), rs.getString("Prestazione_Effettuata"), rs.getString("ID_Veterinario"), rs.getDate("Data_Appuntamento"));
                if(rs.getDouble("Orario") >= 10.0) {
                    String o = String.valueOf(rs.getDouble("Orario"));
                    meet.setHoursString(o);
                }else {
                    String h = "0" + String.valueOf(rs.getDouble("Orario"));
                    meet.setHoursString(h);
                }
                
                list.add(meet);
                return list;
                
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }
    

    public static Connection connectionDB() {
        String username = "root";
        String password = "";
        String dbName = "clinica_veterinaria";
        
        final ConnectionProvider connectionProvider = new ConnectionProvider(username, password, dbName);
        Connection con = connectionProvider.getMySQLConnection();
        return con;
    }
/*Da modificare */
    public static ObservableList<Meet> getDataMeet() {
       
        ObservableList<Meet> list = FXCollections.observableArrayList();
        try (final Statement statement = connectionDB().createStatement()) {
            // 2. Execute the statement with the given query
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);

            while (rs.next()) {
                list.add(new Meet(rs.getString("ID_Padrone"), rs.getString("Nome_animale"), rs.getDouble("Orario"), rs.getString("Prestazione_Effettuata"), rs.getString("ID_Veterinario"), rs.getDate("Data_Appuntamento")));
            }
        } catch (final SQLException e) {
            // 3. Handle possible SQLExceptions
        }
        return list;
    }

    


}