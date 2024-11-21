package gui;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UserWindow extends JFrame {

    public UserWindow() {
        setTitle("User Main Window");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();


        JPanel registrationPanel = createRegistrationPanel();
        tabbedPane.add("Registration", registrationPanel);


        JPanel banquetRegistrationPanel = createBanquetRegistrationPanel();
        tabbedPane.add("Banquet Registration", banquetRegistrationPanel);


        JPanel withdrawBanquetPanel = createWithdrawBanquetPanel();
        tabbedPane.add("Withdraw a Banquet", withdrawBanquetPanel);


        JPanel checkRegistrationPanel = createCheckRegistrationPanel();
        tabbedPane.add("Check My Registration", checkRegistrationPanel);

        add(tabbedPane);
    }

    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 7, 20));

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField addressField = new JTextField();
        JComboBox<String> attendeeTypeCombo = new JComboBox<>(new String[]{"Staff", "Student", "Alumni", "Guest"});
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField mobileField = new JTextField();
        JTextField organizationField = new JTextField();
        JButton registerButton = new JButton("Register");

        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Attendee Type:"));
        panel.add(attendeeTypeCombo);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Mobile Number:"));
        panel.add(mobileField);
        panel.add(new JLabel("Organization:"));
        panel.add(organizationField);
        panel.add(new JLabel());
        panel.add(registerButton);

        registerButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String address = addressField.getText();
            String attendeeType = (String) attendeeTypeCombo.getSelectedItem();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String mobile = mobileField.getText();
            String organization = organizationField.getText();

            try {
                String response = sendPostRequest("http://your-backend-url.com/api/register", String.format(
                        "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"address\":\"%s\",\"attendeeType\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"mobile\":\"%s\",\"organization\":\"%s\"}",
                        firstName, lastName, address, attendeeType, email, password, mobile, organization));

                JOptionPane.showMessageDialog(this, response, "Registration", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to register: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createBanquetRegistrationPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextField banquetIdField = new JTextField();
        JButton registerButton = new JButton("Register for Banquet");

        panel.add(new JLabel("Enter Banquet ID:"), BorderLayout.NORTH);
        panel.add(banquetIdField, BorderLayout.CENTER);
        panel.add(registerButton, BorderLayout.SOUTH);

        registerButton.addActionListener(e -> {
            String banquetId = banquetIdField.getText();
            try {
                String response = sendPostRequest("http://your-backend-url.com/api/banquet/register", String.format(
                        "{\"banquetId\":\"%s\"}", banquetId));

                JOptionPane.showMessageDialog(this, response, "Banquet Registration", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to register for banquet: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createWithdrawBanquetPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextField banquetIdField = new JTextField();
        JButton withdrawButton = new JButton("Withdraw from Banquet");

        panel.add(new JLabel("Enter Banquet ID to Withdraw:"), BorderLayout.NORTH);
        panel.add(banquetIdField, BorderLayout.CENTER);
        panel.add(withdrawButton, BorderLayout.SOUTH);

        withdrawButton.addActionListener(e -> {
            String banquetId = banquetIdField.getText();
            try {
                String response = sendPostRequest("http://your-backend-url.com/api/banquet/withdraw", String.format(
                        "{\"banquetId\":\"%s\"}", banquetId));

                JOptionPane.showMessageDialog(this, response, "Withdraw Banquet", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to withdraw from banquet: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createCheckRegistrationPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea registrationDetails = new JTextArea();
        registrationDetails.setEditable(false);
        JButton refreshButton = new JButton("Refresh");

        panel.add(new JScrollPane(registrationDetails), BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> {
            try {
                String response = sendGetRequest("http://your-backend-url.com/api/registration/details");
                registrationDetails.setText(response);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to fetch registration details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private String sendPostRequest(String urlString, String jsonData) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonData.getBytes());
            os.flush();
        }

        Scanner scanner = new Scanner(connection.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();
        return response;
    }

    private String sendGetRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        Scanner scanner = new Scanner(connection.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();
        return response;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserWindow::new);
    }

}