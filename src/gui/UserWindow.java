package gui;

import controller.Controller;
import gui.components.*;
import gui.components.Button;
import gui.components.TextField;
import model.entities.AttendeeAccount;
import model.entities.Registration;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public final class UserWindow extends JFrame {
    private final Controller controller;

    private final String userID;

    private final RegularLabel nameLabel = new RegularLabel("");
    private final RegularLabel addressLabel = new RegularLabel("");
    private final RegularLabel typeLabel = new RegularLabel("");
    private final RegularLabel mobileNoLabel = new RegularLabel("");
    private final RegularLabel organizationLabel = new RegularLabel("");

    /* Registrations */
    private final RegularLabel selectedRegistrationLabel;
    private static final String[] registrationTableAttributes = {"ID", "Banquet BIN", "Banquet Name", "Banquet Date", "Banquet Address", "Meal Name", "Drink", "Seat"};
    private final JTable registrationTable;
    private long selectedRegistrationID = -1;
    private List<Registration> registrations = new ArrayList<>(); // for safety

    /* Filters */
    private final TextField nameFilterTextField;
    private final TextField dateTimeFilterTextField;
    private final TextField addressFilterTextField;
    private final TextField mealFilterTextField; // meal name
    private final TextField drinkFilterTextField; // drink name
    private final TextField seatFilterTextField;

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

        /* Registration table title */
        TitleLabel registrationTableTitle = new TitleLabel("My Registrations");
        tablesPanel.add(registrationTableTitle);

        /* Registration table */
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

        menuPanel.add(Box.createVerticalStrut(40));

        /* Registration selection */
        selectedRegistrationLabel = new RegularLabel("No registration selected");
        selectedRegistrationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        menuPanel.add(selectedRegistrationLabel);

        menuPanel.add(Box.createVerticalStrut(40));

        /* Menu */
        Dimension buttonSize = new Dimension(200, 50);
        ///
        Button newRegistrationButton = new Button("New Registration", e -> {
            new NewRegistrationWindow(controller, this, userID);
            refreshRegistrations();
        });
        newRegistrationButton.setMinimumSize(buttonSize); newRegistrationButton.setMaximumSize(buttonSize); newRegistrationButton.setPreferredSize(buttonSize);
        menuPanel.add(newRegistrationButton);
        ///
        Button cancelRegistrationButton = new Button("Cancel Registration", e -> cancelRegistration());
        cancelRegistrationButton.setMinimumSize(buttonSize); cancelRegistrationButton.setMaximumSize(buttonSize); cancelRegistrationButton.setPreferredSize(buttonSize);
        menuPanel.add(cancelRegistrationButton);
        ///
        menuPanel.add(Box.createVerticalStrut(20));
        ///
        Button refreshRegistrationTableButton = new Button("Refresh Registration Table", e -> refreshRegistrations());
        refreshRegistrationTableButton.setMinimumSize(buttonSize); refreshRegistrationTableButton.setMaximumSize(buttonSize); refreshRegistrationTableButton.setPreferredSize(buttonSize);
        menuPanel.add(refreshRegistrationTableButton);
        ///
        menuPanel.add(Box.createVerticalStrut(40));
        ///
        menuPanel.add(new TitleLabel("Filter"));
        menuPanel.add(new RegularLabel("Refresh the table to cancel filtering"));
        ///
        nameFilterTextField = new TextField();
        menuPanel.add(new XPanel("Name", nameFilterTextField));
        ///
        dateTimeFilterTextField = new TextField();
        menuPanel.add(new XPanel("Date", dateTimeFilterTextField));
        ///
        addressFilterTextField = new TextField();
        menuPanel.add(new XPanel("Address", addressFilterTextField));
        ///
        mealFilterTextField = new TextField();
        menuPanel.add(new XPanel("Meal", mealFilterTextField));
        ///
        drinkFilterTextField = new TextField();
        menuPanel.add(new XPanel("Drink", drinkFilterTextField));
        ///
        seatFilterTextField = new TextField();
        menuPanel.add(new XPanel("Seat", seatFilterTextField));
        ///
        menuPanel.add(Box.createVerticalStrut(10));
        ///
        Dimension smallButtonSize = new Dimension(200, 25);
        Button applyFilterButton = new Button("Apply the Filter", e -> applyFilter());
        applyFilterButton.setMinimumSize(smallButtonSize); applyFilterButton.setMaximumSize(smallButtonSize); applyFilterButton.setPreferredSize(smallButtonSize);
        menuPanel.add(applyFilterButton);
        ///
        menuPanel.add(Box.createVerticalGlue());
        ///
        Button changeAccountInformationButton = new Button("Change Account Info", e -> {
            new ChangeAccountInformationWindow(controller, this, controller.getAccount(userID));
            refreshAccountInformation();
        });
        changeAccountInformationButton.setMinimumSize(buttonSize); changeAccountInformationButton.setMaximumSize(buttonSize); changeAccountInformationButton.setPreferredSize(buttonSize);
        menuPanel.add(changeAccountInformationButton);
        ///
        Button logoutButton = new Button("Logout", e -> {
            int confirm = showConfirmDialog(
                    UserWindow.this,
                    "This operation will log you out.",
                    "Log out?",
                    YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginWindow(controller);
            }
        });
        logoutButton.setForeground(Color.RED);
        logoutButton.setMinimumSize(buttonSize); logoutButton.setMaximumSize(buttonSize); logoutButton.setPreferredSize(buttonSize);
        menuPanel.add(logoutButton);

        /* Registration table selection */
        registrationTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedBanquetRow = registrationTable.rowAtPoint(e.getPoint());
                if (selectedBanquetRow != -1) {
                    selectedRegistrationID = Long.parseLong(registrationTable.getValueAt(selectedBanquetRow, 0).toString());
                    selectedRegistrationLabel.setText("Selected registration: " + selectedRegistrationID);
                } else {
                    clearRegistrationTableSelection();
                }
            }
        });

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

    private void cancelRegistration() {
        if (selectedRegistrationID == -1) {
            showNoRegistrationSelectionDialog();
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                UserWindow.this,
                "Are you sure to cancel this registration: " + selectedRegistrationID + "? " + "This operation cannot be undone!",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean result = controller.deleteRegistration(selectedRegistrationID);
            if (!result) {
                JOptionPane.showMessageDialog(
                        UserWindow.this,
                        "Cannot cancel the registration.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
            }
            refreshRegistrations();
        }
    }

    private void applyFilter() {
        clearRegistrationTableSelection();
        registrationTable.setModel(
                new DefaultTableModel(
                        controller.registrationListToObjectArray(controller.applyFilter(controller.getRegistrations(userID), nameFilterTextField.getText(), dateTimeFilterTextField.getText(), addressFilterTextField.getText(), mealFilterTextField.getText(), drinkFilterTextField.getText(), seatFilterTextField.getText())),
                registrationTableAttributes));
    }

    private void clearRegistrationTableSelection() {
        registrationTable.clearSelection();
        selectedRegistrationID = -1;
        selectedRegistrationLabel.setText("No registration selected");
    }

    private void refreshRegistrations() {
        clearRegistrationTableSelection();
        registrations = controller.getRegistrations(userID);
        registrationTable.clearSelection();
        registrationTable.setModel(new DefaultTableModel(
                controller.registrationListToObjectArray(registrations),
                registrationTableAttributes));
    }

    private void refreshAccountInformation() {
        AttendeeAccount account = controller.getAccount(userID);

        if (account == null) {
            dispose();
            return;
        }

        nameLabel.setText(account.getName());
        addressLabel.setText(account.getAddress());
        typeLabel.setText(account.getType());
        mobileNoLabel.setText(Long.toString(account.getMobileNo()));
        organizationLabel.setText(account.getOrganization());
    }

    private void showNoRegistrationSelectionDialog() {
        JOptionPane.showMessageDialog(
                UserWindow.this,
                "Please select a registration to manage.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
