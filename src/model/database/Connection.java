package model.database;
import model.ModelException;

import java.sql.*;

/**
 * <h3>The {@code Database} Class</h3>
 * This class is the core part of the BMS. It is an abstraction of the entire DBMS. Any event that requires a database request must go through this class. This class also hides low-level operations like database connections for the higher-level structure.
 * @author jimyang, FrankYang0610
 */
public final class Connection {
    private final java.sql.Connection con;

    //currently using SQLite, might need to switch to oracle SQLPlus

    private final String URL = "jdbc:sqlite:identifier.sqlite";
    // private final String USER = "root"; // no need in SQLite
    // private final String PASSWORD = ""; // no need in SQLite

    /**
     * Create a new database connection.
     */
    public Connection() throws ModelException {
        try {
            Class.forName("org.sqlite.JDBC"); // Corresponding Database Driver
            con = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ModelException("Cannot connect to the database: " + e.getMessage());
        }
    }

    /**
     * Get the connection object.
     */
    public java.sql.Connection getConnection() {
        return con;
    }
}
