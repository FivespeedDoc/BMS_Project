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

    private final TextField ID; // account ID for admin and users

    private final PasswordField password;

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
        ID = new TextField();
        password = new PasswordField();
        panel.add(new UsernamePanel(ID));
        panel.add(new PasswordPanel(password));

        // panel.add(Box.createVerticalStrut(10));

        /* Buttons */
        XPanel loginWindowButtons = new XPanel();
        Button exit = new Button("Exit", _ -> System.exit(0));
            exit.setForeground(Color.RED);
            loginWindowButtons.add(exit);
        Button adminLogin = new Button("Admin Login", this::adminLogin);
            loginWindowButtons.add(adminLogin);
        BoldButton login = new BoldButton("Login", this::userLogin);
            loginWindowButtons.add(login);
        getRootPane().setDefaultButton(login);
        SwingUtilities.invokeLater(login::requestFocusInWindow);
        panel.add(loginWindowButtons);

        /* Finally */
        add(panel);
        setUndecorated(true);
        setVisible(true);
    }

    private void userLogin(ActionEvent e) { // this need to be rewritten
        String userIDstr = ID.getText();
        String passwordstr = Arrays.toString(password.getPassword());

        JOptionPane.showMessageDialog(this, "Account ID: " + userIDstr + "\nPassword: " + passwordstr, "Login Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void adminLogin(ActionEvent e) {
        String adminIDstr = ID.getText();
        char[] passwordstr = password.getPassword(); // use char for safety

        if (controller.isAnAdmin(adminIDstr, passwordstr)) {
            loginSuccessfulDialog(adminIDstr);
            new AdminWindow(controller, adminIDstr);
            dispose();
        } else {
            showWrongLoginInfoDialog();
        }
    }

    private void loginSuccessfulDialog(String adminIDstr) {
        JOptionPane.showMessageDialog(this, "You will be logged in as: " + adminIDstr, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showWrongLoginInfoDialog() {
        JOptionPane.showMessageDialog(this, "Cannot login! Wrong account ID or password.", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
