package service.managers;

import model.ModelException;
import model.database.Connection;
import model.entities.AttendeeAccount;
import model.entities.HashedPasswordAndSalt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

/**
 * <h2> Attemdee_Accounts Class </h2>
 * This class provides functionality to interact with the attendees table in the database.
 * It also initializes the attendees table.
 *
 * Implements basic operations {@code getAllAttendees}, {@code getAttendee}, {@code updateAttendee}, {@code deleteAttendee}
 *
 * @author jimyang
 * @author FrankYang0610
*/
public final class AttendeeAccountsManager {
    private final Connection con;

    public AttendeeAccountsManager(Connection con) throws ModelException {
        this.con = con;
    }

    private void initializeAttendees() throws ModelException { // Initilizes the table for attendees
        /* String stmt = """
            CREATE TABLE IF NOT EXISTS ATTENDEE_ACCOUNTS (
                ID VARCHAR(255) PRIMARY KEY,
                Password VARCHAR(255) NOT NULL,
                Salt VARCHAR(255) NOT NULL,
                Name VARCHAR(255) NOT NULL,
                Type VARCHAR(255) NOT NULL,
                MobileNumber INTEGER NOT NULL,
                Organization VARCHAR(255) NOT NULL,
            )
        """;

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new ModelException("Cannot initialize the database table.");
        } */
    }

    /** Retrieves all attendee records from the database.
     * <p>This method executes a SQL query to select all entries from the {@code attendee_accounts}
     * table and constructs a list of {@code Attendee_Accounts} objects representing each record.</p>
     * @return A {@code List} containing all {@code Attendee_Accounts} objects from the database.
     * @throws ModelException if any errors encountered.
     */
    public List<AttendeeAccount> getAllAttendees() throws ModelException {
        String selectAllSQL = "SELECT * FROM ATTENDEE_ACCOUNTS";

        List<AttendeeAccount> attendees = new ArrayList<>();

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(selectAllSQL)) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                attendees.add(new AttendeeAccount(resultSet.getString(1),
                        new HashedPasswordAndSalt(resultSet.getString(2), resultSet.getString(3)),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getLong(7),
                        resultSet.getString(8)));
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
    public AttendeeAccount getAttendee(String ID) throws ModelException {
        String selectSQL = "SELECT * FROM ATTENDEE_ACCOUNTS WHERE ID = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(selectSQL)) {
            pstmt.setString(1, ID);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return new AttendeeAccount(resultSet.getString(1),
                        new HashedPasswordAndSalt(resultSet.getString(2), resultSet.getString(3)),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getLong(7),
                        resultSet.getString(8));
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
    public List<AttendeeAccount> getAttendee(String attribute, String value) throws ModelException {
        String stmt = "SELECT * FROM ATTENDEE_ACCOUNTS WHERE ? = ?";

        List<AttendeeAccount> attendees = new ArrayList<>();

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setString(1, attribute);
            pstmt.setString(2, value);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                attendees.add(new AttendeeAccount(resultSet.getString(1),
                        new HashedPasswordAndSalt(resultSet.getString(2), resultSet.getString(3)),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getLong(7),
                        resultSet.getString(8)));
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
    public void updateAttendeeInformation(String ID, String attribute, String newValue) throws ModelException {
        if (attribute.equals("Name") && !checkNameValidity(newValue)) {
            throw new ModelException("Invalid name!");
        }

        String stmt = "UPDATE ATTENDEE_ACCOUNTS SET " + attribute + " = ? WHERE ID = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setString(1, newValue);
            pstmt.setString(2, ID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new NoSuchElementException("Attendee with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    public void updateAttendeePassword(String ID, char[] originalPassword, HashedPasswordAndSalt newHashedPasswordAndSalt) throws ModelException {
        if (!verifyAttendeePassword(ID, originalPassword)) { // alters REGEXP in SQL
            throw new ModelException("Attendee name is not valid.");
        }

        String stmt = "UPDATE ATTENDEE_ACCOUNTS SET HashedPassword = ?, HashedSalt = ? WHERE ID = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setString(1, newHashedPasswordAndSalt.getHashedPassword());
            pstmt.setString(2, newHashedPasswordAndSalt.getHashedSalt());
            pstmt.setString(3, ID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new NoSuchElementException("Attendee with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    private boolean verifyAttendeePassword(String ID, char[] originalPassword) throws ModelException {
        String stmt = "SELECT * FROM ATTENDEE_ACCOUNTS WHERE ID = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setString(1, ID);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                HashedPasswordAndSalt hashedPasswordAndSalt = new HashedPasswordAndSalt(resultSet.getString(2), resultSet.getString(3));
                return new PasswordManager().verifyPassword(originalPassword, hashedPasswordAndSalt);
            } else {
                throw new NoSuchElementException("Attendee with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     *
     * @param attendeeAccount an attendeeAccount object (All fields in the object must not be null)
     * @throws ModelException
     */
    public void addAttendeeAccount(AttendeeAccount attendeeAccount) throws ModelException {
        if (!checkNameValidity(attendeeAccount.getName())) { // alters REGEXP in SQL
            throw new ModelException("Attendee name is not valid.");
        }
        
        if(!checkIDvalidity(attendeeAccount.getID())){
            throw new ModelException("Attendee ID is not valid.");
        }

        String stmt = "INSERT INTO ATTENDEE_ACCOUNTS (ID, HashedPassword, HashedSalt, Name, Address, Type, MobileNo, Organization) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setString(1, attendeeAccount.getID());
            pstmt.setString(2, attendeeAccount.getHashedPasswordAndSalt().getHashedPassword());
            pstmt.setString(3, attendeeAccount.getHashedPasswordAndSalt().getHashedSalt());
            pstmt.setString(4, attendeeAccount.getName());
            pstmt.setString(5, attendeeAccount.getAddress());
            pstmt.setString(6, attendeeAccount.getType());
            pstmt.setLong(7, attendeeAccount.getMobileNo());
            pstmt.setString(8, attendeeAccount.getOrganization());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new ModelException("Cannot add Attendee account.");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Deletes an {@code Attendee} record from the database based on the provided Attende ID.
     *
     * @param ID the unique identifier of the Attendee to delete.
     * @throws ModelException if any errors encountered.
     */
    public void deleteAttendee(String ID) throws ModelException {
        String stmt = "DELETE FROM ATTENDEE_ACCOUNTS WHERE ID = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setString(1, ID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new ModelException("Attendee with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    private boolean checkNameValidity(String name) {
        return Pattern.matches("^[A-Za-z -]+$", name);
    }

    private boolean checkIDvalidity(String ID){
        return Pattern.matches("^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9-]+)\\.([a-zA-Z0-9-]+\\.[a-zA-Z]{2,})$",ID);
    }
}
