package gui;

import gui.components.Button;
import gui.components.XButtonPanel;
import gui.components.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * <h3>The Login Window</h3>
 * @author FrankYang0610
 */
public class LoginWindow extends JFrame {
    public LoginWindow() {
        super("Login");


        /* Window properties */

        setSize(500, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));


        add(Box.createVerticalGlue()); // as top spacer


        /* The title */

        TitleLabel title = new TitleLabel("Welcome to the Banquet Management System (BMS)");
        add(title, BorderLayout.CENTER);


        add(Box.createVerticalGlue());


        /* Authors */

        // RegularLabel authors = new RegularLabel("Authors:");
        // add(authors, BorderLayout.CENTER);


        add(Box.createVerticalGlue()); // as spacer


        /* Buttons */

        XButtonPanel loginWindowButtons = new XButtonPanel();

        Button exit = new Button("Exit", _ -> System.exit(0)); loginWindowButtons.add(exit);
        Button login = new Button("Login", this::showAlert); loginWindowButtons.add(login);
        SwingUtilities.invokeLater(login::requestFocusInWindow);

        add(loginWindowButtons);


        add(Box.createVerticalGlue()); // as bottom spacer


        /* Finally */

        setUndecorated(true);
        setVisible(true);
    }

    private void showAlert(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Sorry, the application is still under development!", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
