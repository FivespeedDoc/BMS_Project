package gui;

import controller.Controller;
import gui.components.Button;
import gui.components.TextAndFieldPanel;
import gui.components.TextField;
import gui.components.TitleLabel;
import gui.components.XPanel;
import model.entities.Banquet;
import service.managers.BanquetsManager;
import service.utilities.DateTimeFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * <h3>The Edit Banquet Window</h3>
 * @author FrankYang0610
 */
public class EditBanquetWindow extends JDialog {
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

    public EditBanquetWindow(Controller controller, JFrame adminWindow, long BIN) { // it is guaranteed the BIN always exists
        super(adminWindow, "Edit Banquet", true);
        this.controller = controller;
        this.BIN = BIN;
        this.banquet = controller.getBanquet(BIN);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /* The title */
        TitleLabel title = new TitleLabel("Editing the banquet: " + BIN);
        panel.add(title, BorderLayout.CENTER);

        /* Text and field panels */
        TextField BINField = new TextField(Long.toString(BIN)); BINField.setEnabled(false);
        TextAndFieldPanel BINPanel = new TextAndFieldPanel("BIN", BINField);
        panel.add(BINPanel);
        nameField = new TextField(banquet.getName());
        TextAndFieldPanel namePanel = new TextAndFieldPanel("Name", nameField);
        panel.add(namePanel);
        dateTimeField = new TextField(DateTimeFormatter.format(banquet.getDateTime()));
        TextAndFieldPanel dateTimePanel = new TextAndFieldPanel("Date & Time", dateTimeField);
        panel.add(dateTimePanel);
        addressField = new TextField(banquet.getAddress());
        TextAndFieldPanel addressPanel = new TextAndFieldPanel("Address", addressField);
        panel.add(addressPanel);
        locationField = new TextField(banquet.getLocation());
        TextAndFieldPanel locationPanel = new TextAndFieldPanel("Location", locationField);
        panel.add(locationPanel);
        contactStaffNameField = new TextField(banquet.getContactStaffName());
        TextAndFieldPanel contactStaffNamePanel = new TextAndFieldPanel("Contact Staff", contactStaffNameField);
        panel.add(contactStaffNamePanel);
        availableField = new TextField(Character.toString(banquet.isAvailable())); // this should be changed later.
        TextAndFieldPanel availablePanel = new TextAndFieldPanel("Available?", availableField);
        panel.add(availablePanel);
        quotaField = new TextField(Integer.toString(banquet.getQuota()));
        TextAndFieldPanel quotaPanel = new TextAndFieldPanel("Quota", quotaField);
        panel.add(quotaPanel);

        /* Buttons */
        XPanel buttons = new XPanel();
        Button cancel = new Button("Cancel", _ -> { dispose(); });
        buttons.add(cancel);
        buttons.add(Box.createHorizontalGlue());
        Button confirm = new Button("Confirm", this::applyChanges);
        buttons.add(confirm);
        getRootPane().setDefaultButton(confirm);
        SwingUtilities.invokeLater(confirm::requestFocusInWindow);
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
            success &= controller.updateBanquet(BIN, "Available?", availableField.getText());
        }

        if (!Integer.toString(banquet.getQuota()).equals(quotaField.getText())) {
            success &= controller.updateBanquet(BIN, "Quota", quotaField.getText());
        }

        if (!success) {
            showWrongLoginInfoDialog();
        } else {
            dispose();
        }
    }

    private void showWrongLoginInfoDialog() {
        JOptionPane.showMessageDialog(this, "Error! Some changes cannot be applied.", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
