package service.managers;

import model.ModelException;
import model.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <h2> Banquets-Meals Joint Manager </h2>
 *
 *<ul>
 *     <li>{@code BanquetsManager} depends on {@code BanquetsMealsJointManager}</li>
 *     <li>{@code MealsManager} depends on both {@code BanquetsManager} and {@code BanquetsMealsJointManager}</li>
 *</ul>
 * @author FrankYang0610
 */
public final class BanquetsMealsJointManager {
    private final Connection con;

    public BanquetsMealsJointManager(Connection con) {
        this.con = con;
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
}
