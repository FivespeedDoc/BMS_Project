import controller.Controller;
import globalexceptions.BMS_Exception;

/**
 * <h2>The {@code Application} Class</h2>
 * This is the main class (including the entry point) of the CVFS application.
 * <p>
 * Please run this class to start the application.
 *
 * @author FrankYang0610
 */
public final class Application {
    public static void main(String[] args) {
        try {
            System.setProperty("apple.awt.application.name", "Banquet Management System"); // is this available in Windows?
            Controller controller = new Controller();
        } catch (BMS_Exception e) {
            throw new RuntimeException(e);
        }
    }
}
