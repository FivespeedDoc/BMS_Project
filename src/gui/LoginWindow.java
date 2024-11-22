package gui;

import controller.Controller;
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
    private final Controller controller;

    private final TextField IDField; // account ID for admin and users

    private final PasswordField passwordField;

    public LoginWindow(Controller controller) {
        super("Login");
        this.controller = controller;
        setSize(500, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /* The title and the project name */
        TitleLabel title = new TitleLabel("Welcome to the Banquet Management System (BMS)");
        panel.add(title, BorderLayout.CENTER);
        RegularLabel projectName = new RegularLabel("Project Spix Macaw");
            projectName.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(projectName, BorderLayout.CENTER);


        /* Authors */
        // RegularLabel authors = new RegularLabel("Authors:");
        // add(authors, BorderLayout.CENTER);

        //panel.add(Box.createVerticalStrut(10));

        /* Input boxes */
        IDField = new TextField();
        passwordField = new PasswordField();
        panel.add(new TextAndFieldPanel("Account ID", IDField));
        panel.add(new TextAndFieldPanel(passwordField));

        // panel.add(Box.createVerticalStrut(10));

        /* Buttons */
        ButtonsPanel buttons = new ButtonsPanel();
        Button exit = new Button("Exit", _ -> System.exit(0));
            exit.setForeground(Color.RED);
            buttons.add(exit);
        Button adminLogin = new Button("Admin Login", this::adminLogin);
            buttons.add(adminLogin);
        Button userLogin = new Button("User Login", this::userLogin);
            buttons.add(userLogin);
        buttons.add(Box.createHorizontalGlue());
        Button userSignup = new Button("Signup", _ -> new SignupWindow(controller, this));
            buttons.add(userSignup);
        getRootPane().setDefaultButton(userLogin);
        SwingUtilities.invokeLater(userLogin::requestFocusInWindow);
        panel.add(buttons);

        /* Finally */
        add(panel);
        setUndecorated(true);
        setVisible(true);
    }

    private void userLogin(ActionEvent e) { // this need to be rewritten
        String userID = IDField.getText();
        String password = Arrays.toString(this.passwordField.getPassword());

        // JOptionPane.showMessageDialog(this, "Account ID: " + userIDstr + "\nPassword: " + passwordstr, "Login Info", JOptionPane.INFORMATION_MESSAGE); // for test only
        showWrongLoginInfoDialog();
    }

    private void adminLogin(ActionEvent e) {
        String adminID = IDField.getText();
        char[] password = passwordField.getPassword(); // use char for safety

        if (controller.isAdmin(adminID, password)) {
            adminLoginSuccessfulDialog(adminID);
            new AdminWindow(controller, adminID);
            dispose();
        } else {
            showWrongLoginInfoDialog();
        }
    }

    private void userLoginSuccessfulDialog(String userID) {
        JOptionPane.showMessageDialog(this, "You will be logged in as: " + userID, "User Login Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void adminLoginSuccessfulDialog(String adminID) {
        JOptionPane.showMessageDialog(this, "You will be logged in as: " + adminID, "Admin Login Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showWrongLoginInfoDialog() {
        JOptionPane.showMessageDialog(this, "Cannot login! Wrong account ID or password.", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
