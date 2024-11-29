package controller;

import globalexceptions.BMS_Exception;
import gui.LoginWindow;
import model.ModelException;
import model.database.Connection;
import model.entities.AttendeeAccount;
import model.entities.Banquet;
import model.entities.Meal;
import model.entities.Registration;
import service.managers.*;
import service.utilities.DateTimeFormatter;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h2>The {@code Controller} Class</h2>
 * @author FrankYang0610
 * @author jimyang
 */
public final class Controller {
    private final Connection connection;

    private final AdministratorsManager administratorsManager;

    private final AttendeeAccountsManager attendeeAccountsManager;

    private final BanquetsManager banquetsManager;

    private final MealsManager mealsManager;

    private final RegistrationManager registrationManager;

    private final PasswordManager passwordManager;

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
            this.passwordManager = new PasswordManager();

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

    public List<Banquet> getAvailableBanquets(String attendeeID) {
        try {
            return banquetsManager.getAllAvailableBanquets(attendeeID);
        } catch (ModelException e) {
            return new ArrayList<>();
        }
    }

    public Banquet getBanquet(long BIN) {
        try {
            return banquetsManager.getBanquet(BIN);
        } catch (ModelException e) {
            return null;
        }
    }

    /**
     * @implNote The newly-created banquets are always unavailable.
     */
    public boolean addBanquet(String name, String dateTime, String address, String location, String contactStaffName, String quota) {
        try {
            if (name.isEmpty() || dateTime.isEmpty() || address.isEmpty() || location.isEmpty() || contactStaffName.isEmpty() || quota.isEmpty()) {
                return false;
            }

            Banquet banquet = new Banquet(-1,
                    name,
                    DateTimeFormatter.parse(dateTime),
                    address,
                    location,
                    contactStaffName,
                    'N',
                    Integer.parseInt(quota));
            banquetsManager.addBanquet(banquet);
            return true;
        } catch (ModelException | ParseException | NumberFormatException e) {
            return false;
        }
    }

    /**
     * <h4>Allowed {@code attribute} types</h4>
     * <ul>
     *     <li>{@code "Name"}</li>
     *     <li>{@code "DateTime"}</li>
     *     <li>{@code "Address"}</li>
     *     <li>{@code "Location"}</li>
     *     <li>{@code "ContactStaffName"}</li>
     *     <li>{@code "Available"}</li>
     *     <li>{@code "Quota"}</li>
     * </ul>
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

    public boolean deleteBanquet(long BIN) {
        try {
            banquetsManager.deleteBanquet(BIN);
            return true;
        } catch (ModelException e) {
            return false;
        }
    }

    public String[][] banquetListToObjectArray(List<Banquet> banquets) {
        return banquetsManager.banquetListToObjectArray(banquets);
    }

    public String[][] banquetListToSortedObjectArray(List<Banquet> banquets) {
        return banquetsManager.getSortedBanquetListObjectArray(banquets);
    }

    public List<Meal> getAllBanquetMeals() {
        try {
            return mealsManager.getAllBanquetMeals();
        } catch (ModelException e) {
            return new ArrayList<>();
        }
    }

    public List<Meal> getAllBanquetMeals(long BIN) {
        try {
            return mealsManager.getAllBanquetMeals(BIN);
        } catch (ModelException e) {
            return new ArrayList<>();
        }
    }

    public Meal getBanquetMeal(long BIN, long ID) {
        try {
            return mealsManager.getBanquetMeal(BIN, ID);
        } catch (ModelException e) {
            return null;
        }
    }

    public boolean addMeal(long BIN, String ID, String name, String type, String price, String specialCuisine) {
        try {
            if (ID.isEmpty() || name.isEmpty() || type.isEmpty() || price.isEmpty()) {
                return false;
            }

            Meal meal = new Meal(BIN,
                    Long.parseLong(ID),
                    name,
                    type,
                    Double.parseDouble(price),
                    specialCuisine);

            mealsManager.addMeal(meal);
            return true;
        } catch (ModelException | NumberFormatException e) {
            return false;
        }
    }

    /**
     * <h4>Allowed {@code attribute} types</h4>
     * <ul>
     *     <li>{@code "Name"}</li>
     *     <li>{@code "Type"}</li>
     *     <li>{@code "Price"}</li>
     *     <li>{@code "SpecialCuisine"}</li>
     * </ul>
     * @return if the operation is executed successfully.
     */
    public boolean updateMeal(long BIN, long ID, String attribute, String newValue) {
        try {
            mealsManager.updateMeal(BIN, ID, attribute, newValue);
            return true;
        } catch (ModelException e) {
            return false;
        }
    }

    public boolean deleteMeal(long BIN, long ID) {
        try {
            mealsManager.deleteMeal(BIN, ID);
            return true;
        } catch (ModelException e) {
            return false;
        }
    }

    public List<String[]> getRankedMeals() {
        return registrationManager.getRankedMeals();
    }

    public List<String[]> getRankedDrinks() {
        return registrationManager.getRankedDrinks();
    }

    public List<String[]> getRankedSeats() {
        return registrationManager.getRankedSeats();
    }

    public String[][] rankedEntityListToObjectArray(List<String[]> sortedList) {
        String[][] result = new String[sortedList.size()][2];

        for (int i = 0; i < sortedList.size(); i++) {
            result[i][0] = !sortedList.get(i)[0].isEmpty() ? sortedList.get(i)[0] : "Not specified";
            result[i][1] = sortedList.get(i)[1];
        }

        return result;
    }

    public String[][] mealListToObjectArray(List<Meal> meals) {
        return MealsManager.mealListToObjectArray(meals);
    }

    public boolean isUser(String ID, char[] password) {
        try {
            return passwordManager.verifyPassword(password, attendeeAccountsManager.getAttendee(ID).getHashedPasswordAndSalt());
        } catch (ModelException e) { // user not found.
            return false;
        }
    }

    public boolean isAdmin(String ID, char[] password) { // no throw
        try {
            return passwordManager.verifyPassword(password, administratorsManager.getAdministrator(ID).getHashedPassword());
        } catch (ModelException e) { // administrator not found.
            return false;
        }
    }

    public boolean userSignUp(String ID, char[] password, String name, String address, String type, String mobileNo, String organization) {
        try {
            AttendeeAccount account = new AttendeeAccount(ID,
                    passwordManager.generateHashedPassword(password),
                    name,
                    address,
                    type,
                    Long.parseLong(mobileNo),
                    organization);
            attendeeAccountsManager.addAttendeeAccount(account);
            return true;
        } catch (ModelException | NumberFormatException e) {
            return false;
        }
    }

    /**
     * <h4>Allowed {@code attribute} types</h4>
     * <ul>
     *     <li>{@code "ID"} - this is added according to the instruction document.</li>
     *     <li>{@code "Name"}</li>
     *     <li>{@code "Address"}</li>
     *     <li>{@code "Type"}</li>
     *     <li>{@code "MobileNo"}</li>
     *     <li>{@code "Organization"}</li>
     * </ul>
     */
    public boolean changeUserInformation(String ID, String attribute, String newValue) {
        try {
            attendeeAccountsManager.updateAttendeeInformation(ID, attribute, newValue);
            return true;
        } catch (ModelException e) {
            return false;
        }
    }

    public boolean changeUserPassword(String ID, char[] originalPassword, char[] newPassword) {
        try {
            attendeeAccountsManager.updateAttendeePassword(ID, originalPassword, passwordManager.generateHashedPassword(newPassword));
            return true;
        } catch (ModelException e) {
            return false;
        }
    }

    public List<Registration> getRegistrations(String attendeeID) {
        return registrationManager.getRegistrations("AttendeeID", attendeeID);
    }

    public String[][] registrationListToObjectArray(List<Registration> registrations) {
        return RegistrationManager.registrationListToObjectArray(registrations, connection);
    }

    public AttendeeAccount getAccount(String userID) {
        try {
            return attendeeAccountsManager.getAttendee(userID);
        } catch (ModelException e) {
            return null;
        }
    }

    public boolean registerBanquet(String attendeeID, long BIN, long mealID, String drink, String seat) {
        try {
            Registration registration = new Registration(-1,
                    attendeeID,
                    "",
                    BIN,
                    mealID,
                    drink,
                    (!seat.equals("Optional") ? seat : ""));
            registrationManager.addRegistration(registration);
            return true;
        } catch (ModelException e) {
            return false;
        }
    }

    public boolean deleteRegistration(long ID) {
        try {
            registrationManager.deleteRegistration(ID);
            return true;
        } catch (ModelException e) {
            return false;
        }
    }

    public List<Registration> applyFilter(List<Registration> registrations, String nameFilter, String dateTimeFilter, String addressFilter, String mealFilter, String drinkFilter, String seatFilter) {
        return registrationManager.filterRegistrations(registrations, nameFilter, dateTimeFilter, addressFilter, mealFilter, drinkFilter, seatFilter);
    }

    public boolean exportHTML(String[] tableTitles, JTable[] tables) {
        if (tableTitles.length != tables.length) {
            return false;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save HTML File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        fileChooser.setSelectedFile(new File("analysis_report.html"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".html")) {
                filePath += ".html";
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                // HTML header
                writer.write("<html><head><title>Multiple Tables Export</title></head><body>\n");

                writer.write("<h2>" + "BMS Analysis Report" + " " + "(" + "export time: " + currentTime + ")" + "</h2>\n");

                for (int tableIndex = 0; tableIndex < tables.length; tableIndex++) {
                    String tableTitle = tableTitles[tableIndex];

                    JTable table = tables[tableIndex];

                    writer.write("<h2>" + tableTitle + "</h2>\n");

                    writer.write("<table border='1'>\n");

                    TableModel model = table.getModel();
                    writer.write("<tr>");
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        writer.write("<th>" + model.getColumnName(col) + "</th>");
                    }
                    writer.write("</tr>\n");

                    for (int row = 0; row < model.getRowCount(); row++) {
                        writer.write("<tr>");
                        for (int col = 0; col < model.getColumnCount(); col++) {
                            writer.write("<td>" + model.getValueAt(row, col) + "</td>");
                        }
                        writer.write("</tr>\n");
                    }

                    writer.write("</table>\n<br>\n");
                }

                writer.write("</body></html>");
                writer.flush();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return true;
        }
    }
}
