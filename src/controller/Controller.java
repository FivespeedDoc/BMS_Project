package controller;

import model.ModelException;
import model.database.Connection;
import model.managers.*;

/**
 * <h2>The {@code Controller} Class</h2>
 * @author FrankYang0610
 */
public class Controller {
    private final Connection connection;

    public AdministratorsManager administratorsManager;

    public AttendeeAccountsManager attendeeAccountsManager;

    public BanquetsManager banquetsManager;

    public MealsManager mealsManager;

    public RegistrationManager registrationManager;

    public Controller() throws WrongApplicationStateException {
        try {
            this.connection = new Connection();
            this.administratorsManager = new AdministratorsManager(connection);
            this.attendeeAccountsManager = new AttendeeAccountsManager(connection);
            this.banquetsManager = new BanquetsManager(connection);
            this.mealsManager = new MealsManager(connection);
            this.registrationManager = new RegistrationManager(connection);
        } catch (ModelException e) {
            throw new WrongApplicationStateException("The application cannot be initialized: " + e.getMessage());
        }
    }
}
