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

    private void userLogin(ActionEvent e) {
        String userIDstr = ID.getText();
        String passwordstr = Arrays.toString(password.getPassword());

        JOptionPane.showMessageDialog(this, "Account ID: " + userIDstr + "\nPassword: " + passwordstr, "Login Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void adminLogin(ActionEvent e) {
        String adminIDstr = ID.getText();
        String passwordstr = Arrays.toString(password.getPassword());

        if (!(adminIDstr.equals("frank") && passwordstr.equals("[1, 2, 3, 4, 5, 6]"))) {
            errorAlert();
            return;
        }//Login information of Frank Yang

        if (!(adminIDstr.equals("YixiaoREN") && passwordstr.equals("[1,3,5,7,9]"))) {
            errorAlert();
            return;
        }//Login information of Yixiao REN

        if (!(adminIDstr.equals("JinkunYang") && passwordstr.equals("[6,5,4,3,2,1]"))) {
            errorAlert();
            return;
        }//Login information of Jinkun YANG

        new AdminWindow(adminIDstr);
        dispose();
    }

    private void errorAlert() {
        JOptionPane.showMessageDialog(this, "Sorry, an error occur!", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
