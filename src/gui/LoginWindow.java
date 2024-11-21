package gui;

import gui.components.*;
import gui.components.Button;
import gui.components.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * <h3>The Login Window</h3>
 * @author FrankYang0610
 */
public class LoginWindow extends JFrame {
    private final TextField ID; // account ID for admin and users

    private final PasswordField password;

    public LoginWindow() {
        super("Login");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /* The title */
        TitleLabel title = new TitleLabel("Welcome to the Banquet Management System (BMS)");
        panel.add(title, BorderLayout.CENTER);

        /* Authors */
        // RegularLabel authors = new RegularLabel("Authors:");
        // add(authors, BorderLayout.CENTER);

        //panel.add(Box.createVerticalStrut(10));

        /* Input boxes */
        ID = new TextField();
        password = new PasswordField();
        panel.add(new UsernamePanel(ID));
        panel.add(new PasswordPanel(password));

        // panel.add(Box.createVerticalStrut(10));

        /* Buttons */
        XPanel loginWindowButtons = new XPanel();
        Button exit = new Button("Exit", _ -> System.exit(0)); loginWindowButtons.add(exit);
        Button adminLogin = new Button("Admin Login", this::adminLogin); loginWindowButtons.add(adminLogin);
        BoldButton login = new BoldButton("Login", this::userLogin); loginWindowButtons.add(login);
        getRootPane().setDefaultButton(login);
        SwingUtilities.invokeLater(login::requestFocusInWindow);
        panel.add(loginWindowButtons);

        /* Finally */
        add(panel);
        setUndecorated(true);
        setVisible(true);
    }

    private void userLogin(ActionEvent e) {
        String user = ID.getText();
        String pass = Arrays.toString(password.getPassword());

        JOptionPane.showMessageDialog(this, "Account ID: " + user + "\nPassword: " + pass, "Login Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void adminLogin(ActionEvent e) {
        String adminID = ID.getText();
        String pass = Arrays.toString(password.getPassword());
        new AdminWindow(adminID);
        dispose();
    }

    private void errorAlert(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Sorry, the application is still under development!", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
