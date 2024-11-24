package service.managers;

import model.ModelException;
import model.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BanquetsMealsJointManager {
    private Connection con;

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
