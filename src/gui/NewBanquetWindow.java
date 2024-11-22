package gui;

import controller.Controller;
import gui.components.Button;
import gui.components.TextAndFieldPanel;
import gui.components.TextField;
import gui.components.ButtonsPanel;
import model.entities.Banquet;
import service.utilities.DateTimeFormatter;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * <h3>The Add Banquet Window</h3>
 * @author FrankYang0610
 */
public class NewBanquetWindow extends JDialog {
    private final Controller controller;

    private final TextField nameField;

    private final TextField dateTimeField;

    private final TextField addressField;

    private final TextField locationField;

    private final TextField contactStaffNameField;

    private final TextField availableField;

    private final TextField quotaField;

    public NewBanquetWindow(Controller controller, JFrame adminWindow, long BIN) { // it is guaranteed the BIN always exists
        super(adminWindow, "Add Banquet", true);
        this.controller = controller;
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /* Text and field panels */
        TextField BINField = new TextField("Will be assigned"); BINField.setEnabled(false);
        TextAndFieldPanel BINPanel = new TextAndFieldPanel("BIN", BINField);
        panel.add(BINPanel);
        nameField = new TextField("Name of the banquet");
        TextAndFieldPanel namePanel = new TextAndFieldPanel("Name", nameField);
        panel.add(namePanel);
        dateTimeField = new TextField("Must be in dd/MM/yyyy HH:mm format");
        TextAndFieldPanel dateTimePanel = new TextAndFieldPanel("Date & Time", dateTimeField);
        panel.add(dateTimePanel);
        addressField = new TextField("Where to have the banquet?");
        TextAndFieldPanel addressPanel = new TextAndFieldPanel("Address", addressField);
        panel.add(addressPanel);
        locationField = new TextField("Hong Kong");
        TextAndFieldPanel locationPanel = new TextAndFieldPanel("Location", locationField);
        panel.add(locationPanel);
        contactStaffNameField = new TextField("Contact Frank!");
        TextAndFieldPanel contactStaffNamePanel = new TextAndFieldPanel("Contact Staff", contactStaffNameField);
        panel.add(contactStaffNamePanel);
        availableField = new TextField("Must be Y or N"); // this should be changed later.
        TextAndFieldPanel availablePanel = new TextAndFieldPanel("Available?", availableField);
        panel.add(availablePanel);
        quotaField = new TextField("100? or more?");
        TextAndFieldPanel quotaPanel = new TextAndFieldPanel("Quota", quotaField);
        panel.add(quotaPanel);

        /* Buttons */
        ButtonsPanel buttons = new ButtonsPanel();
        Button cancel = new Button("Cancel", _ -> dispose());
        buttons.add(cancel);
        buttons.add(Box.createHorizontalGlue());
        Button confirmChange = new Button("Confirm Add", this::confirmAdd);
        buttons.add(confirmChange);
        getRootPane().setDefaultButton(confirmChange);
        SwingUtilities.invokeLater(confirmChange::requestFocusInWindow);
        panel.add(buttons);

        add(panel);
        setVisible(true);
    }

    private void confirmAdd(ActionEvent e) {
        if (controller.newBanquet(nameField.getText(),
                dateTimeField.getText(),
                addressField.getText(),
                locationField.getText(),
                contactStaffNameField.getText(),
                availableField.getText(),
                quotaField.getText())) {
            dispose();
        } else {
            showErrorDialog();
        }
    }

    private void showErrorDialog() {
        JOptionPane.showMessageDialog(this, "Error! Some changes cannot be applied.", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
