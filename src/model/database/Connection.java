package model.database;
import model.ModelException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Objects;

/**
 * <h3>The Database {@code Connection} Class</h3>
 * This class is the core part of the BMS. It is an abstraction of the entire DBMS. Any event that requires a database request must go through this class. This class also hides low-level operations like database connections for the higher-level structure.
 * @author jimyang, FrankYang0610
 */
public final class Connection {
    private final java.sql.Connection con;

    //currently using SQLite, might need to switch to oracle SQLPlus

    // private final String URL = "jdbc:sqlite::resource:";
    // private final String USER = "root"; // no need in SQLite
    // private final String PASSWORD = ""; // no need in SQLite

    /**
     * Create a new database connection.
     */
    public Connection() throws ModelException {
        try {
            Class.forName("org.sqlite.JDBC");
            // Access database from within JAR
            String dbPath = Objects.requireNonNull(getClass().getResource("/identifier.sqlite")).toURI().toString();
            con = DriverManager.getConnection("jdbc:sqlite::resource:" + dbPath);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ModelException("Database Initialization Error: " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException("SQL Lite Error"+e);
        }
    }

    /**
     * Get the connection object.
     */
    public java.sql.Connection getConnection() {
        return con;
    }
}
