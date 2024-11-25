package gui;

import javax.swing.*;
import java.awt.*;

@Deprecated
public class ChangeUserInformationWindow extends JFrame {

    public ChangeUserInformationWindow() {
        setTitle("Change User Information");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 10, 10)); // 9 rows, 2 columns

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();

        JLabel attendeeTypeLabel = new JLabel("Attendee Type:");
        JComboBox<String> attendeeTypeCombo = new JComboBox<>(new String[]{"Staff", "Student", "Alumni", "Guest"});

        JLabel emailLabel = new JLabel("E-mail Address:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel mobileNumberLabel = new JLabel("Mobile Number:");
        JTextField mobileNumberField = new JTextField();

        JLabel affiliatedOrgLabel = new JLabel("Affiliated Organization:");
        JComboBox<String> affiliatedOrgCombo = new JComboBox<>(new String[]{"PolyU", "SPEED", "HKCC", "Others"});

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> saveUserInfo());
        cancelButton.addActionListener(e -> dispose());


        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(attendeeTypeLabel);
        panel.add(attendeeTypeCombo);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(mobileNumberLabel);
        panel.add(mobileNumberField);
        panel.add(affiliatedOrgLabel);
        panel.add(affiliatedOrgCombo);

        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);
    }

    private void saveUserInfo() {

        JOptionPane.showMessageDialog(this, "User Information Saved!", "Info", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
