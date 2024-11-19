package model.managers;

import model.ModelException;
import model.entities.Attendee_Accounts;
import model.entities.HashedPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <h2> Attemdee_Accounts Class </h2>
 * @author jimyang
 * This class provides functionality to interact with the attendees table in the database.
 * It also initializes the attendees table.
 *
 * Implements basic operations {@code getAllAttendees}, {@code getAttendee}, {@code updateAttendee}, {@code deleteAttendee}
*/
public class Attendee_AccountsManager {
    private final Connection con;

    public Attendee_AccountsManager(Connection con) throws ModelException {
        this.con = con;
        initializeAttendees();
    }

    private void initializeAttendees() throws ModelException { // Initilizes the table for attendees
        String stmt = """
            CREATE TABLE IF NOT EXISTS ATTENDEE_ACCOUNTS (
                ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                Password VARCHAR(255) NOT NULL,
                Salt VARCHAR(255) NOT NULL,
                Name VARCHAR(255) NOT NULL,
                Type VARCHAR(255) NOT NULL,
                MobileNumber INTEGER NOT NULL,
                Organization VARCHAR(255) NOT NULL,
            )
        """;

        try (PreparedStatement pstmt = con.prepareStatement(stmt)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new ModelException("Cannot initialize the database table.");
        }
    }

    /** Retrieves all attendee records from the database.
     * <p>This method executes a SQL query to select all entries from the {@code attendee_accounts}
     * table and constructs a list of {@code Attendee_Accounts} objects representing each record.</p>
     * @return A {@code List} containing all {@code Attendee_Accounts} objects from the database.
     * @throws ModelException if any errors encountered.
     */
    public List<Attendee_Accounts> getAllAttendees() throws ModelException {
        String selectAllSQL = "SELECT * FROM ATTENDEE_ACCOUNTS";

        List<Attendee_Accounts> attendees = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(selectAllSQL)) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                attendees.add(new Attendee_Accounts(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7)));
            }

            return attendees;
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a {@code Attendee_Accounts} object from the database based on the provided Attendee ID.
     *
     * @param ID the unique identifier of the Attendee to retrieve.
     * @return the {@code Attendee_Accounts} object corresponding to the provided ID.
     * @throws ModelException if any errors encountered.
     */
    public Attendee_Accounts getAttendee(int ID) throws ModelException {
        String selectSQL = "SELECT * FROM ATTENDEES WHERE ID = " + ID;

        try (PreparedStatement pstmt = con.prepareStatement(selectSQL)) {
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new Attendee_Accounts(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7));
            } else {
                throw new ModelException("Attendee_Account with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of {@code Attendee_Accounts} objects from the database based on a specified column and value.
     * <p>
     * This method executes a SQL query to select all entries from the {@code Attendee_Accounts}
     * table where the specified column matches the provided value.
     *
     * <h4>Usage examples</h4>
     * <blockquote><pre>
     *     List<Attendee> testAttendees = getAttendee("Name", "TestAttendee");
     * </pre></blockquote>
     *
     * @param attribute the column name to filter the Attendees by. Must be one of the allowed columns.
     * @param value the value to match in the specified column.
     * @return a {@code List} containing {@code Attendee_Accounts} objects that match the criteria.
     * @throws ModelException if any errors encountered.
     */
    public List<Attendee_Accounts> getAttendee(String attribute, String value) throws ModelException {
        String stmt = "SELECT * FROM ATTENDEES WHERE " + attribute + " = " + value;

        List<Attendee_Accounts> attendees = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(stmt)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                attendees.add(new Attendee_Accounts(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7)));
            }
            return attendees;
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Updates a specific field of an {@code Attendee} record in the database.
     *
     * <p>This method updates the value of a specified column for the attendee identified by
     * the provided attendee ID.</p>
     *
     * @param ID the unique identifier of the Attendee to update.
     * @param attribute    the column name to update. Must be one of the allowed columns.
     * @param newValue     the new value to set for the specified column.
     * @throws ModelException if any errors encountered.
     */
    public void updateAttendee(int ID, String attribute, String newValue) throws ModelException { // This method should be improved later.
        String stmt = "UPDATE ATTENDEES SET " + attribute + " = " + newValue + " WHERE ID = " + ID; // should this be adopted?

        try (PreparedStatement pstmt = con.prepareStatement(stmt)) {
            if (/* affectedRowCnt = */ pstmt.executeUpdate() == 0) {
                throw new NoSuchElementException("Attendee with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Deletes an {@code Attendee} record from the database based on the provided Attende ID.
     *
     * @param ID the unique identifier of the Attendee to delete.
     * @throws ModelException if any errors encountered.
     */
    public void deleteAttendee(int ID) throws ModelException {
        String stmt = "DELETE FROM ATTENDEES WHERE ID = " + ID;

        try (PreparedStatement pstmt = con.prepareStatement(stmt)) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new ModelException("Attendee with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }
}
