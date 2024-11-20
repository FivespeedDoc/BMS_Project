package model.managers;

import model.ModelException;
import model.database.Connection;
import model.entities.Administrator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * <h2> Administrators Class </h2>
 * @author jimyang
 * This class provides functionality to interact with the administrators table in the database.
 * The administrators tables are read-only, cannot be alterd by the model or the program
 * The administrator table have three fields, {@code ID} as primary key, {@code Password} stores hashed password in base64
 * and {@code Salt} stores salt in base64.
 *
 * Implements basic operations {@code getAdmin}
 */

public class AdministratorsManager {
    private final Connection con;
    public AdministratorsManager(Connection con) {
        this.con = con;
    }

    public Administrator getAdministrator(int ID) throws ModelException {
        String stmt = "SELECT * FROM Administrator WHERE ID = " + ID;
        try(PreparedStatement pstmt = con.getConnection().prepareStatement(stmt)) {
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()) {
                return new Administrator(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
            else {
                throw new ModelException("Administrator not found");
            }

        }
        catch (SQLException e) {
            throw new ModelException(e.getMessage());
        }
    }




}
