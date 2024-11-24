package gui;

import controller.Controller;
import gui.components.*;
import model.entities.Banquet;
import service.utilities.DateTimeFormatter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private char selectedAvailableState;

    private final TextField quotaField;

    public EditBanquetWindow(Controller controller, JFrame adminWindow, long BIN) { // it is guaranteed the BIN always exists in the db
        super(adminWindow, "Edit Banquet", true);
        this.controller = controller;
        this.BIN = BIN;
        this.banquet = controller.getBanquet(BIN); assert banquet != null;
        this.selectedAvailableState = this.banquet.isAvailable();
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
        JRadioButton YButton = new JRadioButton("Y"); YButton.setSelected(selectedAvailableState == 'Y');
        JRadioButton NButton = new JRadioButton("N"); NButton.setSelected(selectedAvailableState == 'N');
        XPanel availablePanel = new XPanel("Available", new ButtonGroup(), YButton, NButton);
        availablePanel.add(Box.createHorizontalGlue());
        RegularLabel unavailableLabel = new RegularLabel("Only with 4 meals can be set available."); unavailableLabel.setEnabled(false);
        availablePanel.add(unavailableLabel);
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

        YButton.addActionListener(_ -> selectedAvailableState = 'Y');
        NButton.addActionListener(_ -> selectedAvailableState = 'N');

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

        if (selectedAvailableState != banquet.isAvailable()) {
            success &= controller.updateBanquet(BIN, "Available", Character.toString(selectedAvailableState));
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
