package service.managers;

import model.ModelException;
import model.database.Connection;
import model.entities.Banquet;
import model.entities.Meal;
import service.utilities.DateTimeFormatter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <h2> Meals Class </h2>
 * </p> This class provides functionality to interact with the meals table in the database.
 * It also initializes the meals table.
 *
 * Implements basic operations {@code addMeal}, {@code getAllMeals}, {@code getMeal}, {@code updateMeal}, {@code deleteMeal}</p>
 *
 * @author jimyang
 * @author FrankYang0610
 */
public class MealsManager {
    private final Connection con;

    public MealsManager(Connection con) throws ModelException {
        this.con = con;
    }

    private void initializeMeals() throws ModelException {
        /* String stmt = """
            CREATE TABLE IF NOT EXISTS MEALS (
                BIN INTEGER NOT NULL,
                ID INTEGER NOT NULL,
                Name VARCHAR(255) NOT NULL,
                Type VARCHAR(255) NOT NULL,
                Price DOUBLE NOT NULL,
                SpecialCuisine VARCHAR(255) NOT NULL,
                CONSTRAINT MEALS_PK PRIMARY KEY (BIN, ID)
            )""";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new ModelException("Cannot initialize the database table.");
        } */
    }

    /** Retrieves all meal records from the database.
     * <p>This method executes a SQL query to select all entries from the {@code meals}
     * table and constructs a list of {@code Meal} objects representing each record.</p>
     * @return A {@code List} containing all {@code Meal} objects from the database.
     * @throws ModelException if any errors encountered.
     */
    public List<Meal> getAllMeals() throws ModelException {
        String selectAllSQL = "SELECT * FROM MEALS";

        List<Meal> meals = new ArrayList<>();

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(selectAllSQL)) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                meals.add(new Meal(resultSet.getLong(1),
                        resultSet.getLong(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6)));
            }

            return meals;
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a {@code meal} object from the database based on the provided BIN and ID.
     *
     * @param BIN the unique identifier of the meal to retrieve.
     * @return the {@code Meal} object corresponding to the provided ID.
     * @throws ModelException if any errors encountered.
     */
    public List<Meal> getBanquetMeals(long BIN) throws ModelException {
        String selectSQL = "SELECT * FROM MEALS WHERE BIN = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(selectSQL)) {
            pstmt.setLong(1, BIN);

            ResultSet resultSet = pstmt.executeQuery();
            List<Meal> meals = new ArrayList<>();

            while (resultSet.next()) {
                meals.add(new Meal(
                        resultSet.getLong(1),
                        resultSet.getLong(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6)));
            }

            return meals;
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of {@code Meal} objects from the database based on a specified column and value.
     * <p>
     * This method executes a SQL query to select all entries from the {@code meals}
     * table where the specified column matches the provided value.
     *
     * <h4>Usage examples</h4>
     * <blockquote><pre>
     *     List<Meal> testMeals = getMeal("Name", "TestMeal");
     * </pre></blockquote>
     *
     * @param attribute the column name to filter the meals by. Must be one of the allowed columns.
     * @param value the value to match in the specified column.
     * @return a {@code List} containing {@code Meal} objects that match the criteria.
     * @throws ModelException if any errors encountered.
     */
    public List<Meal> getMeal(String attribute, String value) throws ModelException {
        String stmt = "SELECT * FROM MEALS WHERE ? = ?";

        List<Meal> meals = new ArrayList<>();

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setString(1, attribute);
            pstmt.setString(2, value);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                meals.add(new Meal(
                        resultSet.getLong(1),
                        resultSet.getLong(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6)));
            }
            return meals;
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Updates a specific field of a {@code Meal} record in the database.
     *
     * <p>This method updates the value of a specified column for the meal identified by
     * the provided meal ID.</p>
     *
     * @param BIN the unique identifier of the meal to update.
     * @param ID the unique identifier of the meal to update.
     * @param attribute the column name to update. Must be one of the allowed columns.
     * @param newValue the new value to set for the specified column.
     * @throws ModelException if any errors encountered.
     */
    public void updateMeal(int BIN, int ID, String attribute, String newValue) throws ModelException { // This method should be improved later.
        String stmt = "UPDATE MEALS SET " + attribute + " = ? WHERE BIN = ? AND ID = ?"; // should this be adopted?

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setString(1, newValue);
            pstmt.setLong(2, BIN);
            pstmt.setLong(3, ID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new NoSuchElementException("Meal with BIN " + BIN + " and ID " + ID +" not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    public int getBanquetMealCount(long BIN) throws ModelException {
        String stmt = "SELECT COUNT(*) FROM MEALS WHERE BIN = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setLong(1, BIN);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new ModelException("Meal with BIN " + BIN + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Methods for adding a meal
     * @param meal a meal object (all fields in the meal object must not be null)
     * @throws ModelException if any errors encountered.
     */
    public void addMeal(Meal meal) throws ModelException {
        String stmt = "INSERT INTO MEALS (BIN, ID, Name, Type, Price, SpecialCuisine) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            if (getBanquetMealCount(meal.getBIN()) >= 4) {
                throw new ModelException("There are already four meals in the banquet. It is not allowed to add more meals.");
            }

            pstmt.setLong(1, meal.getBIN());
            pstmt.setLong(2, meal.getID());
            pstmt.setString(3, meal.getName());
            pstmt.setString(4, meal.getType());
            pstmt.setDouble(5, meal.getPrice());
            pstmt.setString(6, meal.getSpecialCuisine());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new ModelException("Cannot add Meal.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Deletes a {@code Meal} record from the database based on the provided meal ID.
     *
     * @param BIN the unique identifier of the meal to delete.
     * @param ID the uniqure identifier of the meal to delete
     * @throws ModelException if any errors encountered.
     */
    public void deleteMeal(int BIN, int ID) throws ModelException {
        String stmt = "DELETE FROM MEALS WHERE BIN = ? AND ID = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setLong(1, BIN);
            pstmt.setLong(2, ID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new ModelException("Meal with BIN " + BIN + " and ID " + ID +" not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }


    /**
     * This method converts a {@code List<Banquet>} object to a {@code String[][]} object.
     * <b>This method will omit the {@code BIN} attribute!</b>
     */
    public static String[][] mealListToObjectArray(List<Meal> meals) {
        String[][] result = new String[meals.size()][5];

        for (int i = 0; i < meals.size(); i++) {
            result[i][0] = String.valueOf(meals.get(i).getID());
            result[i][1] = meals.get(i).getName();
            result[i][2] = meals.get(i).getType();
            result[i][3] = Double.toString(meals.get(i).getPrice());
            result[i][4] = meals.get(i).getSpecialCuisine();
        }

        return result;
    }
}
