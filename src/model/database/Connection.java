package model.database;
import model.ModelException;
import java.sql.*;

/**
 * <h3>The Database {@code Connection} Class</h3>
 * This class is the core part of the BMS. It is an abstraction of the entire DBMS. Any event that requires a database request must go through this class. This class also hides low-level operations like database connections for the higher-level structure.
 * @author jimyang, FrankYang0610
 */
public final class Connection {
    private final java.sql.Connection con;

    private final String URL = "jdbc:sqlite:identifier.sqlite";

    /**
     * Create a new database connection.
     */
    public Connection() throws ModelException {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ModelException("Database Initialization Error: " + e.getMessage());
        }
    }

    /**
     * Get the connection object.
     */
    public java.sql.Connection getConnection() {
        return con;
    }
}
