package gui;

import controller.Controller;
import gui.components.*;
import gui.components.Button;
import gui.components.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * <h3>The Add Banquet Window</h3>
 * @author FrankYang0610
 */
public final class NewBanquetWindow extends JDialog {
    private final Controller controller;

    private final TextField nameField;

    private final TextField dateTimeField;

    private final TextField addressField;

    private final TextField locationField;

    private final TextField contactStaffNameField;

    private final TextField quotaField;

    public NewBanquetWindow(Controller controller, JFrame adminWindow) { // it is guaranteed the BIN always exists
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
        XPanel BINPanel = new XPanel("BIN", BINField);
        panel.add(BINPanel);
        ///
        nameField = new TextField("Name of the banquet");
        XPanel namePanel = new XPanel("Name", nameField);
        panel.add(namePanel);
        ///
        dateTimeField = new TextField("Must be in dd/MM/yyyy HH:mm format");
        XPanel dateTimePanel = new XPanel("Date & Time", dateTimeField);
        panel.add(dateTimePanel);
        ///
        addressField = new TextField("Where to have the banquet?");
        XPanel addressPanel = new XPanel("Address", addressField);
        panel.add(addressPanel);
        ///
        locationField = new TextField("Hong Kong");
        XPanel locationPanel = new XPanel("Location", locationField);
        panel.add(locationPanel);
        ///
        contactStaffNameField = new TextField("Contact Frank!");
        XPanel contactStaffNamePanel = new XPanel("Contact Staff", contactStaffNameField);
        panel.add(contactStaffNamePanel);
        ///
        JRadioButton YButton = new JRadioButton("Y"); YButton.setEnabled(false);
        JRadioButton NButton = new JRadioButton("N"); NButton.setSelected(true); NButton.setEnabled(false);
        XPanel availablePanel = new XPanel("Available", new ButtonGroup(), YButton, NButton);
        availablePanel.add(Box.createHorizontalGlue());
        RegularLabel unavailableLabel = new RegularLabel("Newly created banquets are unavailable."); unavailableLabel.setEnabled(false);
        availablePanel.add(unavailableLabel);
        panel.add(availablePanel);
        ///
        quotaField = new TextField("100? or more?");
        XPanel quotaPanel = new XPanel("Quota", quotaField);
        panel.add(quotaPanel);

        /* Buttons */
        ButtonsPanel buttons = new ButtonsPanel();
        Button cancel = new Button("Cancel", _ -> dispose());
        buttons.add(cancel);
        ///
        buttons.add(Box.createHorizontalGlue());
        ///
        Button confirmChange = new Button("Confirm Add", this::confirmAdd);
        buttons.add(confirmChange);
        ///
        getRootPane().setDefaultButton(confirmChange);
        SwingUtilities.invokeLater(confirmChange::requestFocusInWindow);
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

    private void confirmAdd(ActionEvent e) {
        if (controller.addBanquet(nameField.getText(),
                dateTimeField.getText(),
                addressField.getText(),
                locationField.getText(),
                contactStaffNameField.getText(),
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
