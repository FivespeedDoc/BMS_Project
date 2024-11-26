package service.managers;

import model.ModelException;
import model.database.Connection;
import model.entities.Banquet;
import model.entities.Meal;
import model.entities.Registration;
import service.utilities.DateTimeFormatter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public final class RegistrationManager {
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
         } catch (SQLException e) {
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
            if (resultSet.next()) {
                return new Registration(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getLong(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
            } else {
                throw new ModelException("Registration with ID" + ID + "Registration not found");
            }
         } catch (SQLException e) {
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
     public List<Registration> getRegistration(String attribute, String value) {
         String stmt = "SELECT * FROM REGISTRATIONS WHERE " + attribute + " = ?";

         List<Registration> registrations = new ArrayList<>();

         try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
             pstmt.setString(1, value);

             ResultSet resultSet = pstmt.executeQuery();

             while (resultSet.next()) {
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
         } catch (SQLException e) {
             return new ArrayList<>(); // for CONVENIENCE
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
    public void updateRegistration(long ID, String attribute, String newValue) throws ModelException { // This method should be improved later.
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
        String stmt = "INSERT INTO REGISTRATIONS (AttendeeID, GuestName, BIN, MealID, Drink, Seat) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            // check quota
            int quota = new BanquetsManager(con).getBanquet(registration.getBIN()).getQuota();
            int registeredCount = getRegisteredCount(registration.getBIN());
            if (quota - registeredCount <= 0) {
                throw new ModelException("No more quota for this banquet.");
            }

            // check seat first
            if (!isSeatAvailable(registration.getBIN(), registration.getSeat())) {
                throw new ModelException("Seat already unavailable for this banquet.");
            }

            pstmt.setString(1, registration.getAttendeeID());
            pstmt.setString(2, registration.getGuestName());
            pstmt.setLong(3, registration.getBIN());
            pstmt.setLong(4, registration.getMealID());
            pstmt.setString(5, registration.getDrink());
            pstmt.setString(6, registration.getSeat());
            int affectedRows = pstmt.executeUpdate();


            if (affectedRows == 0) {
                throw new ModelException("Cannot add Registration.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    public int getRegisteredCount(long BIN) {
        String stmt = "SELECT COUNT(*) FROM REGISTRATIONS WHERE BIN = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setLong(1, BIN);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            return Integer.MAX_VALUE; // for safety reasons
        }
    }

    private boolean isSeatAvailable(long BIN, String seat) {
        if (seat.isEmpty()) {
            return true;
        }

        String stmt = "SELECT * FROM REGISTRATIONS WHERE BIN = ? AND SEAT = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setLong(1, BIN);
            pstmt.setString(2, seat);

            ResultSet resultSet = pstmt.executeQuery();

            return !resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Deletes an {@code registration} record from the database based on the provided Register ID.
     *
     * @param ID the unique identifier of the registration to delete.
     * @throws ModelException if any errors encountered.
     */
    public void deleteRegistration(long ID) throws ModelException {
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

    public List<String[]> getRankedMeals() {
        String stmt = "SELECT MEALS.Name AS MealName, COUNT(*) AS Count " +
                "FROM MEALS JOIN REGISTRATIONS ON MEALS.BIN = REGISTRATIONS.BIN AND MEALS.ID = REGISTRATIONS.MealID " +
                "GROUP BY MEALS.Name " +
                "ORDER BY Count DESC;";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            List<String[]> rankedMeals = new ArrayList<>();

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                rankedMeals.add(new String[]{resultSet.getString(1), resultSet.getString(2)});
            }

            return rankedMeals;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public List<String[]> getRankedDrinks() {
        String stmt = "SELECT Drink, COUNT(*) AS Count FROM REGISTRATIONS GROUP BY Drink ORDER BY Count DESC;";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            List<String[]> rankedDrinks = new ArrayList<>();

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                rankedDrinks.add(new String[]{resultSet.getString(1), resultSet.getString(2)});
            }

            return rankedDrinks;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public List<String[]> getRankedSeats() {
        String stmt = "SELECT Seat, COUNT(*) AS Count FROM REGISTRATIONS GROUP BY Seat ORDER BY Count DESC;";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            List<String[]> rankedSeats = new ArrayList<>();

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                rankedSeats.add(new String[]{resultSet.getString(1), resultSet.getString(2)});
            }

            return rankedSeats;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }


    /**
     * <h4>The columns are</h4>
     * <ul>
     *     <li>{@code "ID"}</li>
     *     <li>{@code "Banquet BIN"}</li>
     *     <li>{@code "Banquet Name"}</li>
     *     <li>{@code "Banquet Date"}</li>
     *     <li>{@code "Banquet Address"}</li>
     *     <li>{@code "Meal Name"}</li>
     *     <li>{@code "Drink"}</li>
     *     <li>{@code "Seat"}</li>
     * </ul>
     */
    public static String[][] registrationListToObjectArray(List<Registration> registrations, Connection con) {
        String[][] result = new String[registrations.size()][8];

        try {
            for (int i = 0; i < registrations.size(); i++) {
                BanquetsManager banquetsManager = new BanquetsManager(con);
                Registration registration = registrations.get(i);

                result[i][0] = String.valueOf(registration.getID());
                result[i][1] = String.valueOf(registration.getBIN());
                result[i][2] = banquetsManager.getBanquet(registration.getBIN()).getName();
                result[i][3] = DateTimeFormatter.format(banquetsManager.getBanquet(registration.getBIN()).getDateTime());
                result[i][4] = banquetsManager.getBanquet(registration.getBIN()).getAddress();
                result[i][5] = String.valueOf(new MealsManager(con).getBanquetMeal(registration.getBIN(), registration.getMealID()).getName());
                result[i][6] = String.valueOf(registration.getDrink());
                result[i][7] = String.valueOf(registration.getSeat());
            }
        } catch (ModelException ignored) {}

        return result;
    }

    public List<Registration> filterRegistrations(List<Registration> registrations, String nameFilter, String dateTimeFilter, String addressFilter, String mealFilter, String drinkFilter, String seatFilter) {
        try {
            BanquetsManager banquetsManager = new BanquetsManager(con);

            MealsManager mealsManager = new MealsManager(con);

            List<Registration> filteredRegistrations = new ArrayList<>();

            for (Registration registration : registrations) {
                Banquet banquet = banquetsManager.getBanquet(registration.getBIN());

                if (banquet.getName().contains(nameFilter) &&
                        DateTimeFormatter.format(banquet.getDateTime()).contains(dateTimeFilter) &&
                        banquet.getAddress().contains(addressFilter) && mealsManager.getBanquetMeal(registration.getBIN(), registration.getMealID()).getName().contains(mealFilter) && registration.getDrink().contains(drinkFilter) && registration.getSeat().contains(seatFilter)) {
                    filteredRegistrations.add(registration);
                }
            }

            return filteredRegistrations;
        } catch (ModelException e) {
            return new ArrayList<>();
        }
    }
}
