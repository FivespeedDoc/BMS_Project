package gui;

import controller.Controller;
import gui.components.*;
import gui.components.Button;
import gui.components.TextField;
import model.entities.AttendeeAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * <h3>The Change User Information Window</h3>
 * @author FrankYang0610
 * @author Arda Eren (the original version)
 */
public final class ChangeUserInformationWindow extends JDialog {
    private final Controller controller;

    private final AttendeeAccount account;

    private final TextField userIDField;

    private final PasswordField oldPasswordField;

    private final PasswordField newPasswordField;

    private final PasswordField rePasswordField;

    private final TextField nameField;

    private final TextField addressField;

    private final TextField typeField;

    private final TextField mobileNoField;

    private final TextField organizationField;

    public ChangeUserInformationWindow(Controller controller, JFrame loginWindow, AttendeeAccount account) {
        super(loginWindow, "Sign Up", true);
        this.controller = controller;
        this.account = account;
        setSize(500, 375);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /* Text and field panels */
        userIDField = new TextField(this.account.getID()); userIDField.setEnabled(false);
        XPanel userIDPanel = new XPanel("Account ID", userIDField);
        panel.add(userIDPanel);
        ///
        nameField = new TextField(this.account.getName());
        XPanel namePanel = new XPanel("Name", nameField);
        panel.add(namePanel);
        ///
        addressField = new TextField(this.account.getAddress());
        XPanel addressPanel = new XPanel("Address", addressField);
        panel.add(addressPanel);
        ///
        typeField = new TextField(this.account.getType()); // this should be changed later
        XPanel typePanel = new XPanel("Type", typeField);
        panel.add(typePanel);
        ///
        mobileNoField = new TextField(Long.toString(this.account.getMobileNo()));
        XPanel mobileNoPanel = new XPanel("Phone Number", mobileNoField);
        panel.add(mobileNoPanel);
        ///
        organizationField = new TextField(this.account.getOrganization());
        XPanel organizationPanel = new XPanel("Organization", organizationField);
        panel.add(organizationPanel);
        ///
        panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
        ///
        RegularLabel changePasswordLabel = new RegularLabel("Change Password");
        changePasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(changePasswordLabel);
        ///
        oldPasswordField = new PasswordField();
        XPanel oldPasswordPanel = new XPanel(oldPasswordField, "OLD");
        panel.add(oldPasswordPanel);
        ///
        newPasswordField = new PasswordField();
        XPanel newPasswordPanel = new XPanel(newPasswordField, "NEW");
        panel.add(newPasswordPanel);
        ///
        rePasswordField = new PasswordField();
        XPanel rePasswordPanel = new XPanel(rePasswordField, "RE");
        panel.add(rePasswordPanel);

        /* Buttons */
        ButtonsPanel buttons = new ButtonsPanel();
        ///
        Button cancel = new Button("Cancel", _ -> dispose());
        buttons.add(cancel);
        ///
        buttons.add(Box.createHorizontalGlue());
        ///
        Button confirmChange = new Button("Confirm Change", this::confirmChange);
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

    private void confirmChange(ActionEvent e) {
        if (!Arrays.equals(newPasswordField.getPassword(), rePasswordField.getPassword())) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = false;

        if (oldPasswordField.getPassword().length != 0) { // change password
            controller.changeUserPassword(account.getID(), oldPasswordField.getPassword(), newPasswordField.getPassword());
        }

        if (!account.getName().equals(nameField.getText())) {
            controller.changeUserInformation(account.getID(), "Name", nameField.getText());
        }

        if (!account.getAddress().equals(addressField.getText())) {
            controller.changeUserInformation(account.getID(), "Address", addressField.getText());
        }

        if (!account.getType().equals(typeField.getText())) {
            controller.changeUserInformation(account.getID(), "Type", typeField.getText());
        }

        if (account.getMobileNo() != Long.parseLong(mobileNoField.getText())) {
            controller.changeUserInformation(account.getID(), "MobileNo", Long.toString(account.getMobileNo()));
        }

        if (!account.getOrganization().equals(organizationField.getText())) {
            controller.changeUserInformation(account.getID(), "Organization", organizationField.getText());
        }

        if (success) {
            changeAppliedDialog();
            dispose();
        } else {
            showErrorDialog();
        }
    }

    private void changeAppliedDialog() {
        JOptionPane.showMessageDialog(this, "Your changes have applied!", "Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorDialog() {
        JOptionPane.showMessageDialog(this, "Cannot change your personal information. Please check if your changes align the requirements and try again.", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
