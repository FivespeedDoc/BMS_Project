package service.managers;

import model.ModelException;
import model.database.Connection;
import model.entities.Banquet;
import service.utilities.DateTimeFormatter;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h2> Banquets Class </h2>
 * This class provides functionality to interact with the banquets table in the database.
 * It also initializes the banquets table.
 *
 * Implements basic operations, {@code getAllBanquets}, {@code getBanquet}, {@code updateBanquet}, {@code deleteBanquet}
 * @author jimyang
 * @author FrankYang0610
*/
public final class BanquetsManager {
    private final Connection con;

    public BanquetsManager(Connection con) throws ModelException {
        this.con = con;
        initializeBanquets();
    }

    private void initializeBanquets() throws ModelException {
        /* String stmt = """
            CREATE TABLE IF NOT EXISTS BANQUETS (
                BIN INTEGER AUTO_INCREMENT PRIMARY KEY,
                Name VARCHAR(255) NOT NULL,
                DateTime DATETIME NOT NULL,
                Address VARCHAR(255) NOT NULL,
                Location VARCHAR(255) NOT NULL,
                ContactStaffName VARCHAR(255) NOT NULL,
                Available BOOLEAN NOT NULL,
                Quota INTEGER NOT NULL
            )""";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new ModelException("Cannot initialize the database table.");
        } */
    }

    /** Retrieves all banquet records from the database.
     * <p>This method executes a SQL query to select all entries from the {@code banquets}
     * table and constructs a list of {@code Banquet} objects representing each record.</p>
     * @return A {@code List} containing all {@code Banquet} objects from the database.
     * @throws ModelException if any errors encountered.
     */
    public List<Banquet> getAllBanquets() throws ModelException {
        String selectAllSQL = "SELECT * FROM BANQUETS";

        List<Banquet> banquets = new ArrayList<>();

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(selectAllSQL)) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                banquets.add(new Banquet(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getTimestamp(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7).charAt(0),
                        resultSet.getInt(8)));
            }

            return banquets;
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    public List<Banquet> getAllAvailableBanquets(String attendeeID) throws ModelException {
        RegistrationManager registrationManager = new RegistrationManager(con);
        List <Banquet> allBanquets = getAllBanquets();
        allBanquets.removeIf(banquet -> banquet.isAvailable() == 'N');
        allBanquets.removeIf(banquet -> banquet.getQuota() - registrationManager.getRegisteredCount(banquet.getBIN()) <= 0);
        allBanquets.removeIf(banquet -> !registrationManager.getRegistrations(banquet.getBIN(), attendeeID).isEmpty());
        return allBanquets;
    }

    /**
     * Add a new banquet into the db
     * @param banquet the {@code Banquet} object (Any of fields of the banquet object must not be null, except BIN, which will not be taken into consideration)
     * @throws ModelException if any errors encountered.
     */
    public void addBanquet(Banquet banquet) throws ModelException {
        String insertSQL = "INSERT INTO BANQUETS (Name, DateTime, Address, Location, ContactStaffName, Available, Quota) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(insertSQL)) {
            pstmt.setString(1, banquet.getName());
            pstmt.setTimestamp(2, banquet.getDateTime());
            pstmt.setString(3, banquet.getAddress());
            pstmt.setString(4, banquet.getLocation());
            pstmt.setString(5, banquet.getContactStaffName());
            pstmt.setString(6, "N"); // the newly-created banquet are always unavailable.
            pstmt.setInt(7, banquet.getQuota());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new ModelException("Cannot create a new banquet.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a {@code Banquet} object from the database based on the provided banquet ID.
     *
     * @param BIN the unique identifier of the banquet to retrieve.
     * @return the {@code Banquet} object corresponding to the provided ID.
     * @throws ModelException if any errors encountered.
     */
    public Banquet getBanquet(long BIN) throws ModelException {
        String selectSQL = "SELECT * FROM BANQUETS WHERE BIN = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(selectSQL)) {
            pstmt.setLong(1, BIN);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return new Banquet(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getTimestamp(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7).charAt(0),
                        resultSet.getInt(8));
            } else {
                throw new ModelException("Banquet with ID " + BIN + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of {@code Banquet} objects from the database based on a specified column and value.
     * <p>
     * This method executes a SQL query to select all entries from the {@code banquets}
     * table where the specified column matches the provided value.
     *
     * <h4>Usage examples</h4>
     * <blockquote><pre>
     *     List<Banquet> testBanquets = getBanquet("Name", "TestBanquet");
     * </pre></blockquote>
     *
     * @param attribute the column name to filter the banquets by. Must be one of the allowed columns.
     * @param value the value to match in the specified column.
     * @return a {@code List} containing {@code Banquet} objects that match the criteria.
     * @throws ModelException if any errors encountered.
     */
    @Deprecated
    public List<Banquet> getBanquet(String attribute, String value) throws ModelException {
        String stmt = "SELECT * FROM BANQUETS WHERE " + attribute + " = ?";

        List<Banquet> banquets = new ArrayList<>();

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setString(1, value);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                banquets.add(new Banquet(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getTimestamp(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7).charAt(0),
                        resultSet.getInt(8)));
            }
            return banquets;
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Updates a specific field of a {@code Banquet} record in the database.
     *
     * <p>This method updates the value of a specified column for the banquet identified by
     * the provided banquet ID.</p>
     *
     * <h4>Allowed {@code attribute} types</h4>
     * <ul>
     *     <li>{@code "Name"}</li>
     *     <li>{@code "DateTime"}</li>
     *     <li>{@code "Address"}</li>
     *     <li>{@code "Location"}</li>
     *     <li>{@code "ContactStaffName"}</li>
     *     <li>{@code "Available"}</li>
     *     <li>{@code "Quota"}</li>
     * </ul>
     *
     * @param BIN the unique identifier of the banquet to update.
     * @param attribute    the column name to update. Must be one of the allowed columns.
     * @param newValue     the new value to set for the specified column.
     * @throws ModelException if any errors encountered.
     * @implNote This seems not compatible with the MVC design pattern, but since we have the Stage I report and this is a small system, this is acceptable. Related regulations will also be presented in {@code Controller}.
     */
    public void updateBanquet(long BIN, String attribute, String newValue) throws ModelException {
        String stmt = "UPDATE BANQUETS SET " + attribute + " = ? WHERE BIN = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            switch (attribute) {
                case "DateTime": {
                    Timestamp parsedNewValue = DateTimeFormatter.parse(newValue);
                    pstmt.setTimestamp(1, parsedNewValue);
                    break;
                }
                case "Available": {
                    if (newValue.equals("Y") && new BanquetsMealsJointManager(con).getBanquetMealCount(BIN) != 4) {
                        throw new ModelException("Cannot change the available state. Meals is not 4.");
                    }
                    pstmt.setString(1, newValue);
                    break;
                }
                case "Quota": {
                    if (new RegistrationManager(con).getRegisteredCount(BIN) > Integer.parseInt(newValue)) {
                        throw new ModelException("Cannot change the quota. New quota is smaller than the number of registered attendees.");
                    }
                    pstmt.setInt(1, Integer.parseInt(newValue));
                    break;
                }
                default: {
                    pstmt.setString(1, newValue);
                    break;
                }
            }

            pstmt.setLong(2, BIN);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new ModelException("Banquet with BIN " + BIN + " not found.");
            }
        } catch (SQLException | ParseException e) {
            throw new ModelException("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a {@code Banquet} record from the database based on the provided banquet ID.
     *
     * @param BIN the unique identifier of the banquet to delete.
     * @throws ModelException if any errors encountered.
     */
    public void deleteBanquet(long BIN) throws ModelException {
        String stmt = "DELETE FROM BANQUETS WHERE BIN = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setLong(1, BIN);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new ModelException("Banquet with BIN " + BIN + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * This method converts a {@code List<Banquet>} object to a {@code String[][]} object.
     */
    public String[][] banquetListToObjectArray(List<Banquet> banquets) {
        String[][] result = new String[banquets.size()][9];

        for (int i = 0; i < banquets.size(); i++) {
            result[i][0] = String.valueOf(banquets.get(i).getBIN());
            result[i][1] = String.valueOf(banquets.get(i).getName());
            result[i][2] = DateTimeFormatter.format(banquets.get(i).getDateTime());
            result[i][3] = String.valueOf(banquets.get(i).getAddress());
            result[i][4] = String.valueOf(banquets.get(i).getLocation());
            result[i][5] = String.valueOf(banquets.get(i).getContactStaffName());
            result[i][6] = String.valueOf(banquets.get(i).isAvailable());
            result[i][7] = String.valueOf(banquets.get(i).getQuota());
            result[i][8] = String.valueOf(new RegistrationManager(con).getRegisteredCount(banquets.get(i).getBIN()));
        }

        return result;
    }

    public String[][] getSortedBanquetListObjectArray(List<Banquet> banquets) {
        RegistrationManager registrationManager = new RegistrationManager(con);
        banquets.sort((o1, o2) -> (registrationManager.getRegisteredCount(o1.getBIN()) < registrationManager.getRegisteredCount(o2.getBIN())) ? 1 : -1);
        return banquetListToObjectArray(banquets);
    }
}
