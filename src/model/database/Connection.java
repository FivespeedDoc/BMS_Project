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

    // currently using SQLite, might need to switch to oracle SQLPlus

    // Uncomment the following line and comment(replace) the current one if were using JAR to pack, package configuration already set per IDEA
    // private final String URL = "jdbc:sqlite::resource:";

    private final String URL = "jdbc:sqlite:identifier.sqlite";

    /**
     * Create a new database connection.
     */
    public Connection() throws ModelException {
        try {
            Class.forName("org.sqlite.JDBC");

            /*
             * Access database from within JAR
             * Uncomment the following line and comment(replace) the current line if were using JAR to pack, package configuration already set per IDEA
             * Note that there will need to add a catch exception
             * catch (URISyntaxException e) {
             *             throw new RuntimeException("SQL Lite Error"+e);
             *         }
             */
            // String dbPath = Objects.requireNonNull(getClass().getResource("/identifier.sqlite")).toURI().toString();
            // con = DriverManager.getConnection("jdbc:sqlite::resource:" + dbPath);

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
