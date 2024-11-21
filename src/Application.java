import controller.Controller;
import gui.LoginWindow;

import javax.swing.*;

/**
 * <h2>The {@code Application} Class</h2>
 * This is the main class (including the entry point) of the CVFS application.
 * <p>
 * Please run this class to start the application.
 *
 * @author FrankYang0610
 */
public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
