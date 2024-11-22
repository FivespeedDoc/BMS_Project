package gui;

import controller.Controller;
import gui.components.Button;
import gui.components.TextAndFieldPanel;
import gui.components.TextField;
import gui.components.TitleLabel;
import gui.components.XPanel;
import model.entities.Banquet;

import javax.swing.*;
import java.awt.*;

public class EditBanquetWindow extends JDialog {
    private final Controller controller;

    private final long BIN;

    private final Banquet banquet;

    public EditBanquetWindow(Controller controller, JFrame adminWindow, long BIN) {
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
        TextField nameField = new TextField(banquet.getName());
        TextAndFieldPanel namePanel = new TextAndFieldPanel("Name", nameField);
        panel.add(namePanel);
        TextField dateTimeField = new TextField(banquet.getDateTime().toString());
        TextAndFieldPanel dateTimePanel = new TextAndFieldPanel("Date & Time", dateTimeField);
        panel.add(dateTimePanel);
        TextField addressField = new TextField(banquet.getAddress());
        TextAndFieldPanel addressPanel = new TextAndFieldPanel("Address", addressField);
        panel.add(addressPanel);
        TextField locationField = new TextField(banquet.getLocation());
        TextAndFieldPanel locationPanel = new TextAndFieldPanel("Location", locationField);
        panel.add(locationPanel);
        TextField contactStaffNameField = new TextField(banquet.getContactStaffName());
        TextAndFieldPanel contactStaffNamePanel = new TextAndFieldPanel("Contact Staff", contactStaffNameField);
        panel.add(contactStaffNamePanel);
        TextField availableField = new TextField(Character.toString(banquet.isAvailable())); // this should be changed later.
        TextAndFieldPanel availablePanel = new TextAndFieldPanel("Available?", availableField);
        panel.add(availablePanel);
        TextField quotaField = new TextField(Integer.toString(banquet.getQuota()));
        TextAndFieldPanel quotaPanel = new TextAndFieldPanel("Quota", quotaField);
        panel.add(quotaPanel);

        /* Buttons */
        XPanel buttons = new XPanel();
        Button cancel = new Button("Cancel", _ -> { dispose(); });
        buttons.add(cancel);
        buttons.add(Box.createHorizontalGlue());
        Button confirm = new Button("Confirm", null);
        buttons.add(confirm);
        getRootPane().setDefaultButton(confirm);
        SwingUtilities.invokeLater(confirm::requestFocusInWindow);
        panel.add(buttons);

        add(panel);
        setVisible(true);
    }
}
