package service.managers;

import model.ModelException;
import model.database.Connection;
import model.entities.Registration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <h2> Registration Class </h2>
 * This class provides functionality to interact with the registrations table in the database.
 * It also initializes the registrations table.
 *
 * Implements basic operations {@code getAllRegistrations}, {@code getRegistration}, {@code updateRegistration}, {@code deleteRegistration}
 * @author jimyang
 * @author FrankYang0610
 */

public class RegistrationManager {
    private final Connection con;
    public RegistrationManager(Connection con) {
        this.con = con;
    }

    private void initializeRegistrations() throws ModelException {
        /* String stmt = """
           CREATE TABLE IF NOT EXISTS REGISTRATIONS (
                ID INTEGER NOT NULL PRIMARY KEY,
                AttendeeID VARCHAR(255) NOT NULL,
                GuestName VARCHAR(255) NOT NULL,
                BIN INTEGER NOT NULL,
                MealID VARCHAR(255) NOT NULL,
                Drink VARCHAR(255) NOT NULL,
                Seat VARCHAR(255) NOT NULL,
           )""";
        try(PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new ModelException("Cannot initialize database table");
        } */
    }

     /** Retrieves all the Registration records from the database.
     * <p>This method executes a SQL query to select all entries from the {@code Registrations}
     * table and constructs a list of {@code Registrations} objects representing each record.</p>
     * @return A {@code List} containing all {@code Registrations} objects from the database.
     * @throws ModelException if any errors encountered.
     * */
     public List<Registration> getAllRegistrations() throws ModelException {
         String selectAllSQL = "SELECT * FROM REGISTRATIONS";

         List<Registration> registrations = new ArrayList<>();

         try (PreparedStatement pstmt = con.getConnection().prepareStatement(selectAllSQL)){
             ResultSet resultSet = pstmt.executeQuery();

             while (resultSet.next()) {
                 registrations.add(new Registration(resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getLong(4),
                                resultSet.getLong(5),
                                resultSet.getString(6),
                                resultSet.getString(7)
                         ));
             }

            return registrations;
         }

         catch(SQLException e) {
             throw new ModelException("Database error: " + e.getMessage());
         }
     }
    /**
     * Retrieves a {@code Registration} object from the database based on the provided Registration ID.
     *
     * @param ID the unique identifier of the Registration to retrieve.
     * @return the {@code Registration} object corresponding to the provided ID.
     * @throws ModelException if any errors encountered.
     */
     public Registration getRegistration(long ID) throws ModelException {
         String selectSQL = "SELECT * FROM REGISTRATIONS WHERE ID = ?";

         try (PreparedStatement pstmt = con.getConnection().prepareStatement(selectSQL)) {
             pstmt.setLong(1, ID);

            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()) {
                return new Registration(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getLong(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
            }
            else {
                throw new ModelException("Registration with ID" + ID + "Registration not found");
            }
         }

         catch (SQLException e) {
             throw new ModelException("Database error: " + e.getMessage());
         }

     }
    /**
     * Retrieves a list of {@code Registration} objects from the database based on a specified column and value.
     * <p>
     * This method executes a SQL query to select all entries from the {@code Registration}
     * table where the specified column matches the provided value.
     *
     * <h4>Usage examples</h4>
     * <blockquote><pre>
     *     List<Registration> testRegistration = getRegistration("Name", "TesteRegistration");
     * </pre></blockquote>
     *
     * @param attribute the column name to filter the Registrations by. Must be one of the allowed columns.
     * @param value the value to match in the specified column.
     * @return a {@code List} containing {@code Registration} objects that match the criteria.
     * @throws ModelException if any errors encountered.
     */

     public List<Registration> getRegistrations(String attribute, String value) throws ModelException {
         String stmt = "SELECT * FROM REGISTRATIONS WHERE ? = ?";

         List<Registration> registrations = new ArrayList<>();

         try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
             pstmt.setString(1, attribute);
             pstmt.setString(2, value);

             ResultSet resultSet = pstmt.executeQuery();
             while(resultSet.next()) {
                 registrations.add(new Registration(resultSet.getInt(1),
                         resultSet.getString(2),
                         resultSet.getString(3),
                         resultSet.getInt(4),
                         resultSet.getLong(5),
                         resultSet.getString(6),
                         resultSet.getString(7)
                 ));
             }
             return registrations;
         }

         catch(SQLException e) {
             throw new ModelException("Database error: " + e.getMessage());
         }
     }

    /**
     * Updates a specific field of an {@code Registration} record in the database.
     *
     * <p>This method updates the value of a specified column for the registration identified by
     * the provided registration ID.</p>
     *
     * @param ID the unique identifier of the Registration to update.
     * @param attribute    the column name to update. Must be one of the allowed columns.
     * @param newValue     the new value to set for the specified column.
     * @throws ModelException if any errors encountered.
     */
    public void updateRegistration(int ID, String attribute, String newValue) throws ModelException { // This method should be improved later.
        String stmt = "UPDATE REGISTRATIONS SET " + attribute + " = ? WHERE ID = ?"; // should this be adopted?

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setString(1, newValue);
            pstmt.setLong(2, ID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new NoSuchElementException("Registration with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }
    public void addRegistration(Registration registration) throws ModelException {
        String stmt = "INSERT INTO REGISTRATIONS (ID, AttendeeID, GuestName, Bin, MealID, Drink, Seat) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setLong(1, registration.getID());
            pstmt.setString(2, registration.getAttendeeID());
            pstmt.setString(3, registration.getGuestName());
            pstmt.setLong(4, registration.getBIN());
            pstmt.setLong(5, registration.getMealID());
            pstmt.setString(6, registration.getDrink());
            pstmt.setString(7, registration.getSeat());
            pstmt.executeUpdate();

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new ModelException("Cannot add Registration.");
            }

        }
        catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Deletes an {@code registration} record from the database based on the provided Register ID.
     *
     * @param ID the unique identifier of the registration to delete.
     * @throws ModelException if any errors encountered.
     */
    public void deleteRegistration(int ID) throws ModelException {
        String stmt = "DELETE FROM REGISTRATIONS WHERE ID = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setLong(1, ID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new ModelException("Registration with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }
}
