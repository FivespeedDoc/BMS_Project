package controller;
import model.*;
import model.database.Connection;
import model.entities.Administrator;
import model.entities.AttendeeAccount;
import model.managers.*;

import java.security.NoSuchAlgorithmException;


/**
 * @author jimyang
 * This class provides certain methods for the frontend to use
 */

public class Services {
    private final String URL = ""; // need to be filled later
    private final String USER = ""; // need to be filled later
    private final String PASSWORD = ""; // need to be filled later
    AdministratorsManager administratorsManager;
    AttendeeAccountsManager attendeeAccountsManager;
    BanquetsManager banquetsManager;
    MealsManager mealsManager;
    RegistrationManager registrationManager;
    Connection connection;
    public Services() throws ModelException {
        this.connection = new Connection(URL, USER, PASSWORD);
        this.administratorsManager = new AdministratorsManager(connection);
        this.attendeeAccountsManager = new AttendeeAccountsManager(connection);
        this.banquetsManager = new BanquetsManager(connection);
        this.mealsManager = new MealsManager(connection);
        this.registrationManager = new RegistrationManager(connection);
    }
    public boolean userLogin(int username, String password)
            throws NoSuchAlgorithmException, IllegalArgumentException, ModelException {
                AttendeeAccount attendee = attendeeAccountsManager.getAttendee(username);

                if (attendee == null) {
                    throw new IllegalArgumentException("User not found");
                }

        return PasswordManager.verifyPassword(password, attendee.getPassword(), attendee.getSalt());
    }

    public boolean adminLogin(int username, String password)
        throws NoSuchAlgorithmException, IllegalArgumentException, ModelException {
            Administrator administrator = administratorsManager.getAdministrator(username);

            if (administrator == null) {
                throw new IllegalArgumentException("User not found");
            }

        return PasswordManager.verifyPassword(password, administrator.getPassword(), administrator.getSalt());
            
    }



}
