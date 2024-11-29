package model.database;
import model.ModelException;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.*;

/**
 * <h3>The Database {@code Connection} Class</h3>
 * This class is the core part of the BMS. It is an abstraction of the entire DBMS. Any event that requires a database request must go through this class. This class also hides low-level operations like database connections for the higher-level structure.
 * <p>
 * Currently we use SQLite.
 * @author jimyang, FrankYang0610
 */
public final class Connection {
    private final String DBName = "identifier.sqlite";

    private final java.sql.Connection con;

    /**
     * Create a new database connection.
     */
    public Connection() throws ModelException {
        try {
            Class.forName("org.sqlite.JDBC");

            String DBPath = getDBPath();
            File DBFile = new File(DBPath);
            boolean exists = DBFile.exists();

            con = DriverManager.getConnection("jdbc:sqlite:" + DBPath);

            if (!exists) {
                executeDDL();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ModelException("Database Initialization Error: " + e.getMessage());
        }
    }

    /**
     * <h4>For {@code .jar}</h4>
     * This method get the path of the database file.
     * <p>
     * In {@code .jar} packaging, we assume the database file {@code identifier.sqlite} is in the same directory of the {@code .jar} file.
     */
    private String getDBPath() throws ModelException {
        String path = "";
        try {
            path = Paths.get(Connection.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .getParent().toString();
        } catch (URISyntaxException e) {
            throw new ModelException("URI Syntax Error: " + e.getMessage());
        }
        return Paths.get(path, DBName).toString();
    }

    /**
     * This method create the tables if the database file does not exist.
     */
    private void executeDDL() throws ModelException {
        String DDL = """
                PRAGMA foreign_keys = ON;
                
                CREATE TABLE IF NOT EXISTS BANQUETS (
                    BIN INTEGER PRIMARY KEY AUTOINCREMENT,
                    Name VARCHAR(255) NOT NULL,
                    DateTime DATETIME NOT NULL, -- This will then be TEXT.
                    Address VARCHAR(255) NOT NULL,
                    Location VARCHAR(255) NOT NULL,
                    ContactStaffName VARCHAR(255) NOT NULL,
                    Available CHAR(1) NOT NULL CHECK ( Available IN ('Y', 'N') ),
                    Quota INTEGER NOT NULL CHECK ( Quota >= 0 )
                );
                
                CREATE TABLE IF NOT EXISTS MEALS (
                    BIN INTEGER NOT NULL,
                    ID INTEGER NOT NULL,
                    Name VARCHAR(255) NOT NULL,
                    Type VARCHAR(255) NOT NULL,
                    Price DECIMAL(5,2) NOT NULL CHECK ( Price >= 0 ),
                    SpecialCuisine VARCHAR(255),
                    CONSTRAINT MEALS_PK PRIMARY KEY (BIN, ID),
                    CONSTRAINT MEALS_FK FOREIGN KEY (BIN) REFERENCES BANQUETS(BIN) ON DELETE CASCADE -- ON UPDATE CASCADE;
                );
                
                CREATE TABLE IF NOT EXISTS ATTENDEE_ACCOUNTS (
                    ID VARCHAR(255) PRIMARY KEY CHECK ( ID LIKE '_%@_%._%' ),
                    HashedPassword VARCHAR(255) NOT NULL,
                    HashedSalt VARCHAR(255) NOT NULL,
                    Name VARCHAR(255) NOT NULL, -- the check are assigned to the Model part
                    Address VARCHAR(255),
                    Type VARCHAR(255) NOT NULL,
                    MobileNo INTEGER NOT NULL CHECK ( LENGTH ( CAST ( MobileNo AS TEXT ) ) = 8 ),
                    Organization VARCHAR(255)
                );
                
                CREATE TABLE IF NOT EXISTS REGISTRATIONS (
                    ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    AttendeeID VARCHAR(255),
                    GuestName VARCHAR(255),
                    BIN INTEGER NOT NULL,
                    MealID INTEGER NOT NULL,
                    Drink VARCHAR(255) NOT NULL,
                    Seat VARCHAR(255),
                    FOREIGN KEY (AttendeeID) REFERENCES ATTENDEE_ACCOUNTS(ID) ON DELETE CASCADE,
                    FOREIGN KEY (BIN) REFERENCES BANQUETS(BIN) ON DELETE CASCADE,
                    FOREIGN KEY (BIN, MealID) REFERENCES MEALS(BIN, ID) ON DELETE CASCADE,
                    CONSTRAINT IDENTIFIER CHECK ( AttendeeID IS NOT NULL OR GuestName IS NOT NULL )
                );
                
                CREATE TABLE IF NOT EXISTS ADMINISTRATORS (
                    ID VARCHAR(255) PRIMARY KEY,
                    HashedPassword VARCHAR(255) NOT NULL,
                    HashedSalt VARCHAR(255) NOT NULL
                );
                
                """;

        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(DDL);
        } catch (SQLException e) {
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
