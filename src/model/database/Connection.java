package model.database;
import model.ModelException;

import java.sql.*;

/**
 * <h3>The {@code Database} Class</h3>
 * This class is the core part of the BMS. It is an abstraction of the entire DBMS. Any event that requires a database request must go through this class. This class also hides low-level operations like database connections for the higher-level structure.
 * @author jimyang, FrankYang0610
 */
public class Connection {

    private final java.sql.Connection con;

    /**
     * Create a new database connection.
     */
    public Connection(String URL, String USER, String PASSWORD) throws ModelException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Corresponding Database Driver
            con = DriverManager.getConnection(URL, USER, PASSWORD);
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
