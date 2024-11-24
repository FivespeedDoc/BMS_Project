package gui;

import controller.Controller;
import gui.components.Button;
import gui.components.XPanel;
import gui.components.TextField;
import gui.components.ButtonsPanel;
import model.entities.Banquet;
import service.utilities.DateTimeFormatter;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * <h3>The Edit Banquet Window</h3>
 * @author FrankYang0610
 */
public final class EditBanquetWindow extends JDialog {
    private final Controller controller;

    private final long BIN;

    private final Banquet banquet;

    private final TextField nameField;

    private final TextField dateTimeField;

    private final TextField addressField;

    private final TextField locationField;

    private final TextField contactStaffNameField;

    private final TextField availableField;

    private final TextField quotaField;

    public EditBanquetWindow(Controller controller, JFrame adminWindow, long BIN) { // it is guaranteed the BIN always exists in the db
        super(adminWindow, "Edit Banquet", true);
        this.controller = controller;
        this.BIN = BIN;
        this.banquet = controller.getBanquet(BIN); assert banquet != null;
        setSize(500, 375);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /* Text and field panels */
        TextField BINField = new TextField(Long.toString(BIN)); BINField.setEnabled(false);
        XPanel BINPanel = new XPanel("BIN", BINField);
        panel.add(BINPanel);
        ///
        nameField = new TextField(banquet.getName());
        XPanel namePanel = new XPanel("Name", nameField);
        panel.add(namePanel);
        ///
        dateTimeField = new TextField(DateTimeFormatter.format(banquet.getDateTime()));
        XPanel dateTimePanel = new XPanel("Date & Time", dateTimeField);
        panel.add(dateTimePanel);
        ///
        addressField = new TextField(banquet.getAddress());
        XPanel addressPanel = new XPanel("Address", addressField);
        panel.add(addressPanel);
        ///
        locationField = new TextField(banquet.getLocation());
        XPanel locationPanel = new XPanel("Location", locationField);
        panel.add(locationPanel);
        ///
        contactStaffNameField = new TextField(banquet.getContactStaffName());
        XPanel contactStaffNamePanel = new XPanel("Contact Staff", contactStaffNameField);
        panel.add(contactStaffNamePanel);
        ///
        availableField = new TextField(Character.toString(banquet.isAvailable())); // this should be changed later.
        XPanel availablePanel = new XPanel("Available?", availableField);
        panel.add(availablePanel);
        ///
        quotaField = new TextField(Integer.toString(banquet.getQuota()));
        XPanel quotaPanel = new XPanel("Quota", quotaField);
        panel.add(quotaPanel);

        /* Buttons */
        ButtonsPanel buttons = new ButtonsPanel();
        ///
        Button cancel = new Button("Cancel", _ -> dispose());
        buttons.add(cancel);
        ///
        buttons.add(Box.createHorizontalGlue());
        ///
        Button confirmChange = new Button("Confirm Change", this::applyChanges);
        buttons.add(confirmChange);
        ///
        getRootPane().setDefaultButton(confirmChange);
        SwingUtilities.invokeLater(confirmChange::requestFocusInWindow);
        panel.add(buttons);

        add(panel);
        setVisible(true);
    }

    private void applyChanges(ActionEvent e) {
        boolean success = true;

        if (!banquet.getName().equals(nameField.getText())) {
            success &= controller.updateBanquet(BIN, "Name", nameField.getText());
        }

        if (!DateTimeFormatter.format(banquet.getDateTime()).equals(dateTimeField.getText())) { // this should be changed later.
            success &= controller.updateBanquet(BIN, "DateTime", dateTimeField.getText());
        }

        if (!banquet.getAddress().equals(addressField.getText())) {
            success &= controller.updateBanquet(BIN, "Address", addressField.getText());
        }

        if (!banquet.getLocation().equals(locationField.getText())) {
            success &= controller.updateBanquet(BIN, "Location", locationField.getText());
        }

        if (!banquet.getContactStaffName().equals(contactStaffNameField.getText())) {
            success &= controller.updateBanquet(BIN, "ContactStaffName", contactStaffNameField.getText());
        }

        if (!Character.toString(banquet.isAvailable()).equals(availableField.getText())) {
            success &= controller.updateBanquet(BIN, "Available", availableField.getText());
        }

        if (!Integer.toString(banquet.getQuota()).equals(quotaField.getText())) {
            success &= controller.updateBanquet(BIN, "Quota", quotaField.getText());
        }

        if (success) {
            dispose();
        } else {
            showErrorDialog();
        }
    }

    private void showErrorDialog() {
        JOptionPane.showMessageDialog(this, "Error! Some changes cannot be applied.", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
