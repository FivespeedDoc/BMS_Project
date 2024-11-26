package gui;

import controller.Controller;
import gui.components.Button;
import gui.components.RegularLabel;
import gui.components.Table;
import gui.components.TitleLabel;
import model.entities.AttendeeAccount;
import model.entities.Registration;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;

/**
 * <h3>The Change User Information Window</h3>
 * @author FrankYang0610
 * @author ZacharyRE (the original version)
 */
public class UserWindow extends JFrame {
    private final Controller controller;

    private final String userID;

    private final RegularLabel nameLabel = new RegularLabel("");
    private final RegularLabel addressLabel = new RegularLabel("");
    private final RegularLabel typeLabel = new RegularLabel("");
    private final RegularLabel mobileNoLabel = new RegularLabel("");
    private final RegularLabel organizationLabel = new RegularLabel("");

    /* Registrations */
    private final RegularLabel selectedRegistrationLabel;
    private static final String[] registrationTableAttributes = {"ID", "Banquet BIN", "Banquet Name", "Meal ID", "Meal Name", "Drink", "Seat"};
    private final JTable registrationTable;
    private long selectedRegistrationID = -1;
    private List<Registration> registrations = new ArrayList<>(); // for safety

    public UserWindow(Controller controller, String userID) {
        super("Attendee User: " + userID);
        this.controller = controller;
        this.userID = userID;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        /* Registration table panel */
        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));

        /* Banquet title */
        TitleLabel registrationTableTitle = new TitleLabel("My Registrations");
        tablesPanel.add(registrationTableTitle);

        /* Banquet table */
        registrations = controller.getRegistrations(userID);
        registrationTable = new Table(new DefaultTableModel(
                controller.registrationListToObjectArray(registrations),
                registrationTableAttributes));
        JScrollPane registrationTableScrollPane = new JScrollPane(registrationTable);
        tablesPanel.add(registrationTableScrollPane);

        /* Menu panel */
        JPanel menuPanel = new JPanel();
        menuPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setMinimumSize(new Dimension(230, 0));
        menuPanel.setMaximumSize(new Dimension(230, Integer.MAX_VALUE));
        menuPanel.setPreferredSize(new Dimension(230, menuPanel.getPreferredSize().height));

        /* Account info title */
        RegularLabel accountInfoTitle = new RegularLabel("Account Info");
        accountInfoTitle.setFont(new Font("Arial", Font.PLAIN, 16));
        menuPanel.add(accountInfoTitle);

        menuPanel.add(Box.createVerticalStrut(10));

        /* Account Info */
        refreshAccountInformation();
        menuPanel.add(nameLabel);
        menuPanel.add(addressLabel);
        menuPanel.add(typeLabel);
        menuPanel.add(mobileNoLabel);
        menuPanel.add(organizationLabel);

        menuPanel.add(Box.createVerticalStrut(20));

        /* Registration selection */
        selectedRegistrationLabel = new RegularLabel("No registration selected");
        selectedRegistrationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        menuPanel.add(selectedRegistrationLabel);

        menuPanel.add(Box.createVerticalStrut(10));

        /* Menu */
        Dimension buttonSize = new Dimension(200, 50);
        ///
        Button newRegistrationButton = new Button("New Registration", null);
        newRegistrationButton.setMinimumSize(buttonSize); newRegistrationButton.setMaximumSize(buttonSize); newRegistrationButton.setPreferredSize(buttonSize);
        menuPanel.add(newRegistrationButton);
        ///
        Button editRegistrationButton = new Button("Edit Registration", null);
        editRegistrationButton.setMinimumSize(buttonSize); editRegistrationButton.setMaximumSize(buttonSize); editRegistrationButton.setPreferredSize(buttonSize);
        menuPanel.add(editRegistrationButton);
        ///
        Button deleteRegistrationButton = new Button("Delete Registration", null);
        deleteRegistrationButton.setMinimumSize(buttonSize); deleteRegistrationButton.setMaximumSize(buttonSize); deleteRegistrationButton.setPreferredSize(buttonSize);
        menuPanel.add(deleteRegistrationButton);
        ///
        menuPanel.add(Box.createVerticalStrut(20));
        ///
        Button refreshRegistrationTableButton = new Button("Refresh Registration Table", null);
        refreshRegistrationTableButton.setMinimumSize(buttonSize); refreshRegistrationTableButton.setMaximumSize(buttonSize); refreshRegistrationTableButton.setPreferredSize(buttonSize);
        menuPanel.add(refreshRegistrationTableButton);
        ///
        menuPanel.add(Box.createVerticalStrut(20));
        ///
        Button changeAccountInformation = new Button("Change Account Info", _ -> {
            new ChangeAccountInformationWindow(controller, this, controller.getAccount(userID));
            refreshAccountInformation();
        });
        changeAccountInformation.setMinimumSize(buttonSize); changeAccountInformation.setMaximumSize(buttonSize); changeAccountInformation.setPreferredSize(buttonSize);
        menuPanel.add(changeAccountInformation);
        ///
        menuPanel.add(Box.createVerticalGlue());
        ///
        Button logoutButton = new Button("Logout", _ -> {
            int confirm = showConfirmDialog(
                    UserWindow.this,
                    "This operation will log you out.",
                    "Log out?",
                    YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                UserWindow.this.dispose();
                new LoginWindow(controller);
            }
        });
        logoutButton.setForeground(Color.RED);
        logoutButton.setMinimumSize(buttonSize); logoutButton.setMaximumSize(buttonSize); logoutButton.setPreferredSize(buttonSize);
        menuPanel.add(logoutButton);


        /* Finally */
        panel.add(tablesPanel);
        panel.add(menuPanel);
        add(panel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = showConfirmDialog(
                        UserWindow.this,
                        "Closing this window will log you out!",
                        "Log out?",
                        YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    new LoginWindow(controller);
                }
            }
        });

        pack();
        setVisible(true);
    }

    private void refreshAccountInformation() {
        AttendeeAccount account = controller.getAccount(userID);

        assert account != null;
        SwingUtilities.invokeLater(() -> nameLabel.setText(account.getName()));
        SwingUtilities.invokeLater(() -> addressLabel.setText(account.getAddress()));
        SwingUtilities.invokeLater(() -> typeLabel.setText(account.getType()));
        SwingUtilities.invokeLater(() -> mobileNoLabel.setText(Long.toString(account.getMobileNo())));
        SwingUtilities.invokeLater(() -> organizationLabel.setText(account.getOrganization()));

        repaint();
    }

    private void showNoBanquetSelectionDialog() {
        JOptionPane.showMessageDialog(
                UserWindow.this,
                "Please select a banquet to manage.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
