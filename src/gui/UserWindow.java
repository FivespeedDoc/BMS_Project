package gui;


import javax.swing.*;
import java.awt.*;

public class UserWindow extends JFrame {

    public UserWindow() {
        setTitle("User Login Window");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2, 1, 15));

        JLabel firstNameLabel = new JLabel("  First Name:");
        JTextField firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("  Last Name:");
        JTextField lastNameField = new JTextField();

        JLabel addressLabel = new JLabel("  Address:");
        JTextField addressField = new JTextField();

        JLabel attendeeTypeLabel = new JLabel("  Attendee Type:");
        JComboBox<String> attendeeTypeCombo = new JComboBox<>(new String[]{"Staff", "Student", "Alumni", "Guest"});

        JLabel emailLabel = new JLabel("  E-mail Address:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("  Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel mobileLabel = new JLabel("  Mobile Number:");
        JTextField mobileField = new JTextField();

        JLabel organizationLabel = new JLabel("  Affiliated Organization:");
        JComboBox<String> organizationCombo = new JComboBox<>(new String[]{"PolyU", "SPEED", "HKCC", "Others"});


        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String address = addressField.getText();
            String attendeeType = (String) attendeeTypeCombo.getSelectedItem();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String mobile = mobileField.getText();
            String organization = (String) organizationCombo.getSelectedItem();

            if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || email.isEmpty() ||
                    password.isEmpty() || mobile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Invalid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!mobile.matches("\\d{8}")) {
                JOptionPane.showMessageDialog(this, "Mobile number must be 8 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            JOptionPane.showMessageDialog(this, "Login information submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);


        });

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

        panel.add(mobileLabel);
        panel.add(mobileField);

        panel.add(organizationLabel);
        panel.add(organizationCombo);

        panel.add(new JLabel());
        panel.add(submitButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserWindow::new);
    }
}