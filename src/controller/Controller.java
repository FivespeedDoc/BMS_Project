package controller;

import globalexceptions.BMS_Exception;
import gui.LoginWindow;
import model.ModelException;
import model.database.Connection;
import model.entities.Banquet;
import service.managers.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h2>The {@code Controller} Class</h2>
 * @author FrankYang0610
 * @author jimyang
 */
public class Controller {
    private final Connection connection;

    private final AdministratorsManager administratorsManager;

    private final AttendeeAccountsManager attendeeAccountsManager;

    private final BanquetsManager banquetsManager;

    private final MealsManager mealsManager;

    private final RegistrationManager registrationManager;

    public Controller() throws BMS_Exception {
        try {
            /* The Model */
            // The database connection
            this.connection = new Connection();
            // The entities
            this.administratorsManager = new AdministratorsManager(connection);
            this.attendeeAccountsManager = new AttendeeAccountsManager(connection);
            this.banquetsManager = new BanquetsManager(connection);
            this.mealsManager = new MealsManager(connection);
            this.registrationManager = new RegistrationManager(connection);

            /* The View */
            SwingUtilities.invokeLater(() -> {
                LoginWindow loginWindow = new LoginWindow(this);
                loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            });
        } catch (ModelException e) {
            throw new BMS_Exception("The application cannot be initialized: " + e.getMessage());
        }
    }

    public List<Banquet> getAllBanquets() {
        try {
            return banquetsManager.getAllBanquets();
        } catch (ModelException e) {
            return new ArrayList<>();
        }
    }

    public Banquet getBanquet(long BIN) {
        try {
            return banquetsManager.getBanquet(BIN);
        } catch (ModelException ignored) {}
        return null;
    }

    /**
     * @return if the operation is executed successfully.
     */
    public boolean updateBanquet(long BIN, String attribute, String newValue) {
        try {
            banquetsManager.updateBanquet(BIN, attribute, newValue);
            return true;
        } catch (ModelException e) {
            return false;
        }
    }

    public boolean isUser(String ID, char[] password) {
        try {
            return Arrays.equals(password, attendeeAccountsManager.getAttendee(ID).getPassword().toCharArray()); // this is safe enough, because attendeeAccountsManager.getAttendee(ID).getPassword().toCharArray() is a temporary variable.
        } catch (ModelException e) { // user not found.
            return false;
        }
    }

    public boolean isAdmin(String ID, char[] password) { // no throw
        try {
            return Arrays.equals(password, administratorsManager.getAdministrator(ID).getPassword().toCharArray()); // this is safe enough, because administratorsManager.getAdministrator(ID).getPassword() is a temporary variable.
        } catch (ModelException e) { // administrator not found.
            return false;
        }
    }
}
