package service.managers;

import model.ModelException;
import model.database.Connection;
import model.entities.Administrator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * <h2> Administrators Class </h2>
 * This class provides functionality to interact with the administrators table in the database.
 * The administrators tables are read-only, cannot be alterd by the model or the program
 * The administrator table have three fields, {@code ID} as primary key, {@code Password} stores hashed password in base64
 * and {@code Salt} stores salt in base64.
 *
 * Implements basic operations {@code getAdmin}
 * @author jimyang
 * @author FrankYang0610
 */
public final class AdministratorsManager {
    private final Connection con;

    public AdministratorsManager(Connection con) {
        this.con = con;
    }

    public Administrator getAdministrator(String ID) throws ModelException {
        String stmt = "SELECT * FROM ADMINISTRATORS WHERE ID = ?";

        try (PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            pstmt.setString(1, ID);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return new Administrator(resultSet.getString(1), resultSet.getString(2));
            } else {
                throw new ModelException("Administrator not found");
            }
        }  catch (SQLException e) {
            throw new ModelException(e.getMessage());
        }
    }
}
