package gui;

import controller.Controller;
import gui.components.*;
import model.ModelException;
import model.entities.AttendeeAccount;
import service.managers.AttendeeAccountsManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * <h3>The Change User Information Window</h3>
 * @author FrankYang0610
 */

public final class ChangeUserInformationWindow extends JDialog {
    private final Controller controller;

    private final AttendeeAccount account;

    private final TextField userIDField;

    private final PasswordField passwordField;

    private final PasswordField rePasswordField;

    private final TextField nameField;

    private final TextField addressField;

    private final JComboBox typeComboBox;

    private final TextField mobileNoField;

    private final JComboBox organizationComboBox;


    public ChangeUserInformationWindow(Controller controller, JFrame loginWindow, AttendeeAccount account){
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
        userIDField = new TextField(this.account.getID());
        XPanel userIDPanel = new XPanel("Account ID", userIDField);
        panel.add(userIDPanel);
        ///
        passwordField = new PasswordField();
        XPanel passwordPanel = new XPanel(passwordField, false);
        panel.add(passwordPanel);
        ///
        rePasswordField = new PasswordField();
        XPanel rePasswordPanel = new XPanel(rePasswordField, true);
        panel.add(rePasswordPanel);
        ///
        nameField = new TextField(this.account.getName());
        XPanel namePanel = new XPanel("Name", nameField);
        panel.add(namePanel);
        ///
        addressField = new TextField(this.account.getAddress());
        XPanel addressPanel = new XPanel("Address", addressField);
        panel.add(addressPanel);
        ///

        String[] types = {"Student","Alumni","Staff","Guest"};
        typeComboBox = new JComboBox<String>(types);
        for(int i = 0; i < types.length; i++){
            if(account.getOrganization() == types[i].toString()){
                typeComboBox.setSelectedIndex(i);
            }
        }

        XPanel typePanel = new XPanel("Type", typeComboBox);
        panel.add(typePanel);
        ///
        mobileNoField = new TextField(Long.toString(this.account.getMobileNo()));
        XPanel mobileNoPanel = new XPanel("Phone Number", mobileNoField);
        panel.add(mobileNoPanel);
        ///
        String[] organizations = {"PolyU", "Student", "HKCC", "Others"};
        organizationComboBox = new JComboBox<String>(organizations);
        for(int i = 0; i < organizations.length; i++){
            if(account.getOrganization() == organizations[i].toString()){
                organizationComboBox.setSelectedIndex(i);
                break;
            }
        }

        XPanel organizationPanel = new XPanel("Organization", organizationComboBox);
        panel.add(organizationPanel);

        /* Buttons */
        ButtonsPanel buttons = new ButtonsPanel();
        ///
        Button cancel = new Button("Cancel", _ -> dispose());
        buttons.add(cancel);
        ///
        buttons.add(Box.createHorizontalGlue());
        ///

        //The bottom line does not compile for some reason?

        CheckBox confirm = new CheckBox("Confirm");
        //Button confirmChange = new Button("Confirm Change", this::confirmChange);
        buttons.add(confirm);
        ///
        //getRootPane().setDefaultButton(confirmChange);
        //SwingUtilities.invokeLater(confirmChange::requestFocusInWindow);
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

    private void confirmChange(ActionEvent e) throws ModelException {
        if (!Arrays.equals(passwordField.getPassword(), rePasswordField.getPassword())) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = false;

        if (!account.getName().equals(nameField.getText())) {
            // need to fill
            controller.updateAttendee(account.getID(),"Name",nameField.getText());
        }

        if (!account.getAddress().equals(addressField.getText())) {
            controller.updateAttendee(account.getID(),"Adr",addressField.getText());
        }

        if (!account.getType().equals(typeComboBox.getSelectedItem().toString())) {
            controller.updateAttendee(account.getID(),"Type",typeComboBox.getSelectedItem().toString());
        }

        if (account.getMobileNo() != Long.parseLong(mobileNoField.getText())) {
            controller.updateAttendee(account.getID(),"MobileNo",mobileNoField.getText());
        }

        if (!account.getOrganization().equals(organizationComboBox.getSelectedItem().toString())) {
            controller.updateAttendee(account.getID(),"Organization",organizationComboBox.getSelectedItem().toString());
        }

        if (success) {
            changeAppliedDialog();
            dispose();
        } else {
            showErrorDialog();
        }

        if (controller.userSignUp(userIDField.getText(),
                passwordField.getPassword(),
                nameField.getText(),
                addressField.getText(),
                typeComboBox.getSelectedItem().toString(),
                mobileNoField.getText(),
                organizationComboBox.getSelectedItem().toString())) {
        }
    }

    private void changeAppliedDialog() {
        JOptionPane.showMessageDialog(this, "Your changes have applied!", "Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorDialog() {
        JOptionPane.showMessageDialog(this, "Cannot change your personal information. Please check if your changes align the requirements and try again.", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
