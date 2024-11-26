package gui;

import controller.Controller;
import gui.components.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * <h3>The Signup Window</h3>
 * @author FrankYang0610
 */
public final class SignupWindow extends JDialog {
    private final Controller controller;

    private final TextField userIDField;

    private final PasswordField passwordField;

    private final PasswordField rePasswordField;

    private final TextField nameField;

    private final TextField addressField;

    private final TextField typeField;

    private final TextField mobileNoField;

    private final TextField organizationField;

    public SignupWindow(Controller controller, JFrame loginWindow) {
        super(loginWindow, "Sign Up", true);
        this.controller = controller;
        setSize(500, 375);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /* Text and field panels */
        userIDField = new TextField("Must be an email address.");
        XPanel userIDPanel = new XPanel("Account ID", userIDField);
        panel.add(userIDPanel);
        ///
        passwordField = new PasswordField();
        XPanel passwordPanel = new XPanel(passwordField, "REG");
        panel.add(passwordPanel);
        ///
        rePasswordField = new PasswordField();
        XPanel rePasswordPanel = new XPanel(rePasswordField, "RE");
        panel.add(rePasswordPanel);
        ///
        nameField = new TextField("Your name");
        XPanel namePanel = new XPanel("Name", nameField);
        panel.add(namePanel);
        ///
        addressField = new TextField("Your address");
        XPanel addressPanel = new XPanel("Address", addressField);
        panel.add(addressPanel);
        ///
        typeField = new TextField("One of 'Staff', 'Student', 'Alumni' and 'Guest'"); // this should be changed later
        XPanel typePanel = new XPanel("Type", typeField);
        panel.add(typePanel);
        ///
        mobileNoField = new TextField("Your phone number");
        XPanel mobileNoPanel = new XPanel("Phone Number", mobileNoField);
        panel.add(mobileNoPanel);
        ///
        organizationField = new TextField("Your affiliated organization");
        XPanel organizationPanel = new XPanel("Organization", organizationField);
        panel.add(organizationPanel);

        /* Buttons */
        ButtonsPanel buttons = new ButtonsPanel();
        ///
        Button cancel = new Button("Cancel", _ -> dispose());
        buttons.add(cancel);
        ///
        buttons.add(Box.createHorizontalGlue());
        ///
        Button confirmChange = new Button("Sign Up", this::signUp);
        buttons.add(confirmChange);
        ///
        getRootPane().setDefaultButton(confirmChange);
        SwingUtilities.invokeLater(confirmChange::requestFocusInWindow);
        panel.add(buttons);

        /* Press ESC to dispose */
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("ESCAPE"), "closeDialog");
        getRootPane().getActionMap().put("closeDialog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void signUp(ActionEvent e) {
        if (!Arrays.equals(passwordField.getPassword(), rePasswordField.getPassword())) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (controller.userSignUp(userIDField.getText(),
                passwordField.getPassword(),
                nameField.getText(),
                addressField.getText(),
                typeField.getText(),
                mobileNoField.getText(),
                organizationField.getText())) {
            signupSuccessfulDialog();
            dispose();
        } else {
            showErrorDialog();
        }
    }

    private void signupSuccessfulDialog() {
        JOptionPane.showMessageDialog(this, "You have signed up successfully! Please log in to manage your banquets!", "Signup Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorDialog() {
        JOptionPane.showMessageDialog(this, "Signup failed! Some of the provided information may be invalid, or you have already signed up.", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
