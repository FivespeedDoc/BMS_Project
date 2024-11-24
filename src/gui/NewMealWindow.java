package gui;

import controller.Controller;
import gui.components.*;
import gui.components.Button;
import gui.components.TextField;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * <h3>The Add Meal Window</h3>
 * @author FrankYang0610
 */
public final class NewMealWindow extends JDialog {
    private final Controller controller;

    private final long BIN;

    private final TextField IDField;

    private final TextField nameField;

    private final TextField typeField;

    private final TextField priceField;

    private final TextField specialCuisineField;

    public NewMealWindow(Controller controller, JFrame adminWindow, long BIN) { // it is guaranteed the BIN always exists
        super(adminWindow, "Add Banquet", true);
        this.controller = controller;
        this.BIN = BIN;
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /* Text and field panels */
        TextField BINField = new TextField(Long.toString(this.BIN)); BINField.setEnabled(false);
        XPanel BINPanel = new XPanel("BIN", BINField);
        panel.add(BINPanel);
        ///
        IDField = new TextField("Recommended: 1 ~ 4");
        XPanel IDPanel = new XPanel("Meal ID", IDField);
        panel.add(IDPanel);
        ///
        nameField = new TextField("Peking Roast Duck?");
        XPanel namePanel = new XPanel("Name", this.nameField);
        panel.add(namePanel);
        ///
        typeField = new TextField("Duck?");
        XPanel typePanel = new XPanel("Type", typeField);
        panel.add(typePanel);
        ///
        priceField = new TextField("Don't be too expensive!");
        XPanel pricePanel = new XPanel("Price", priceField);
        panel.add(pricePanel);
        ///
        specialCuisineField = new TextField("Spicy? Or?");
        XPanel specialCuisinePanel = new XPanel("Special Cuisine", specialCuisineField);
        panel.add(specialCuisinePanel);

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
        if (controller.addMeal(BIN,
                IDField.getText(),
                nameField.getText(),
                typeField.getText(),
                priceField.getText(),
                specialCuisineField.getText())) {
            dispose();
        } else {
            showErrorDialog();
        }
    }

    private void showErrorDialog() {
        JOptionPane.showMessageDialog(this, "Error! Some changes cannot be applied.", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
