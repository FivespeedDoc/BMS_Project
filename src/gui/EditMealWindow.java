package gui;

import controller.Controller;
import gui.components.Button;
import gui.components.XPanel;
import gui.components.TextField;
import gui.components.ButtonsPanel;
import model.entities.Meal;
import service.utilities.DateTimeFormatter;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * <h3>The Edit Meal Window</h3>
 * @author FrankYang0610
 */
public final class EditMealWindow extends JDialog {
    private final Controller controller;

    private final long BIN;

    private final long ID;

    private final Meal meal;

    private final TextField nameField;

    private final TextField typeField;

    private final TextField priceField;

    private final TextField specialCuisineField;

    public EditMealWindow(Controller controller, JFrame adminWindow, long BIN, long ID) { // it is guaranteed the BIN, ID always exists in the db
        super(adminWindow, "Edit Meal", true);
        this.controller = controller;
        this.BIN = BIN;
        this.ID = ID;
        this.meal = controller.getBanquetMeal(BIN, ID); assert meal != null;
        setSize(500, 300);
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
        TextField IDField = new TextField(Long.toString(BIN)); BINField.setEnabled(false);
        XPanel IDPanel = new XPanel("ID", IDField);
        panel.add(IDPanel);
        ///
        nameField = new TextField(meal.getName());
        XPanel addressPanel = new XPanel("Name", nameField);
        panel.add(addressPanel);
        ///
        typeField = new TextField(meal.getType());
        XPanel locationPanel = new XPanel("Type", typeField);
        panel.add(locationPanel);
        ///
        priceField = new TextField(Double.toString(meal.getPrice()));
        XPanel contactStaffNamePanel = new XPanel("Price", priceField);
        panel.add(contactStaffNamePanel);
        ///
        specialCuisineField = new TextField(meal.getSpecialCuisine()); // this should be changed later.
        XPanel availablePanel = new XPanel("Special Cuisine", specialCuisineField);
        panel.add(availablePanel);

        /* Buttons */
        ButtonsPanel buttons = new ButtonsPanel();
        ///
        Button cancel = new Button("Cancel", e -> dispose());
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

    private void applyChanges(ActionEvent e) {
        boolean success = true;

        if (!meal.getName().equals(nameField.getText())) {
            success &= controller.updateMeal(BIN, ID, "Name", nameField.getText());
        }

        if (!meal.getType().equals(typeField.getText())) {
            success &= controller.updateMeal(BIN, ID, "Type", typeField.getText());
        }

        if (!Double.toString(meal.getPrice()).equals(priceField.getText())) {
            success &= controller.updateMeal(BIN, ID, "Price", priceField.getText());
        }

        if (!meal.getSpecialCuisine().equals(specialCuisineField.getText())) {
            success &= controller.updateMeal(BIN, ID, "SpecialCuisine", specialCuisineField.getText());
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
