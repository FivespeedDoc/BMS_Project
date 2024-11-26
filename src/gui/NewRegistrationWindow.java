package gui;

import controller.Controller;
import gui.components.*;
import gui.components.Button;
import gui.components.TextField;
import model.entities.Banquet;
import model.entities.Meal;
import model.entities.Registration;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.*;

/**
 * <h3>The New Registration Window</h3>
 * @author FrankYang0610
 */
public final class NewRegistrationWindow extends JDialog {
    private final Controller controller;

    private final String attendeeID;

    /* Banquets */
    private final RegularLabel selectedBanquetLabel;
    private static final String[] banquetTableAttributes = {"BIN", "Name", "Date & Time", "Address", "Location", "Contact Staff", "Available? (Y/N)", "Quota", "Registered"};
    private final JTable availableBanquetTable;
    private long selectedBanquetBIN = -1;
    private String selectedBanquetName = ""; // for convenience
    private List<Banquet> availableBanquets = new ArrayList<>(); // for safety

    /* Meals */
    private final RegularLabel selectedMealLabel;
    private static final String[] mealTableAttributes = {"ID", "Name", "Type", "Price", "Special Cuisine"};
    private final JTable mealTable;
    private long selectedMealID = -1;
    private String selectedMealName = ""; // for convenience
    private List<Meal> banquetMeals = new ArrayList<>(); // for safety

    /* Drinks */
    private final RegularLabel selectedDrinkLabel;
    private static final String[] banquetDrinkTitles = {"Drink 1", "Drink 2", "Drink 3", "Drink 4", "Extra Drink!"};
    private static final String[][] banquetDrinks = {{"Coke", "Tea", "Coffee", "Lemon Tea", "Dark Drink (Don't try!)"}};
    private final Table drinkTable;
    private String selectedDrinkName = "";

    /* Seat */
    private final TextField seatField;

    public NewRegistrationWindow(Controller controller, JFrame userWindow, String attendeeID) {
        super(userWindow, "Register a banquet", true);
        this.controller = controller;
        this.attendeeID = attendeeID;
        this.selectedBanquetBIN = -1;
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        /* Tables panel */
        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));

        /* Banquet title */
        TitleLabel banquetTableTitle = new TitleLabel("Available Banquets");
        tablesPanel.add(banquetTableTitle);

        /* Banquet table */
        availableBanquets = controller.getAvailableBanquets(attendeeID);
        availableBanquetTable = new Table(new DefaultTableModel(
                controller.banquetListToObjectArray(availableBanquets),
                banquetTableAttributes));
        JScrollPane banquetTableScrollPane = new JScrollPane(availableBanquetTable);
        tablesPanel.add(banquetTableScrollPane);

        /* Meal title */
        TitleLabel mealTableTitle = new TitleLabel("Meals");
        tablesPanel.add(mealTableTitle);

        /* Meal table */
        mealTable = new Table(new DefaultTableModel(new Object[][]{}, mealTableAttributes));
        JScrollPane mealTableScrollPane = new JScrollPane(mealTable);
        mealTableScrollPane.setPreferredSize(new Dimension(mealTableScrollPane.getPreferredSize().width, 200));
        mealTableScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        tablesPanel.add(mealTableScrollPane);

        /* Drink title */
        TitleLabel drinkTableTitle = new TitleLabel("Drinks (Fixed Supply)");
        tablesPanel.add(drinkTableTitle);

        /* Drink table */
        drinkTable = new Table(new DefaultTableModel(new Object[][]{}, banquetDrinkTitles));
        drinkTable.setRowSelectionAllowed(false);
        drinkTable.setColumnSelectionAllowed(false);
        drinkTable.setCellSelectionEnabled(true);
        JScrollPane drinkTableScrollPane = new JScrollPane(drinkTable);
        drinkTableScrollPane.setPreferredSize(new Dimension(drinkTableScrollPane.getPreferredSize().width, 100));
        drinkTableScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        tablesPanel.add(drinkTableScrollPane);

        /* Menu panel */
        JPanel menuPanel = new JPanel();
        menuPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setMinimumSize(new Dimension(230, 0));
        menuPanel.setMaximumSize(new Dimension(230, Integer.MAX_VALUE));
        menuPanel.setPreferredSize(new Dimension(230, menuPanel.getPreferredSize().height));

        /* Banquet table selection */
        selectedBanquetLabel = new RegularLabel("No banquet selected");
        selectedBanquetLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        menuPanel.add(selectedBanquetLabel);

        /* Meal selection */
        selectedMealLabel = new RegularLabel("No meal selected");
        selectedMealLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        menuPanel.add(selectedMealLabel);

        /* Drink selection */
        selectedDrinkLabel = new RegularLabel("No drink selected");
        selectedDrinkLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        menuPanel.add(selectedDrinkLabel);

        menuPanel.add(Box.createVerticalStrut(20));

        /* Menu */
        seatField = new TextField("Optional");
        menuPanel.add(new XPanel("Choose a seat", seatField));
        ///
        menuPanel.add(Box.createVerticalStrut(20));
        ///
        Dimension buttonSize = new Dimension(200, 50);
        ///
        Button confirmRegisterButton = new Button("Confirm Register", _ -> confirmRegister());
        confirmRegisterButton.setMinimumSize(buttonSize); confirmRegisterButton.setMaximumSize(buttonSize); confirmRegisterButton.setPreferredSize(buttonSize);
        menuPanel.add(confirmRegisterButton);
        ///
        Button refreshTablesButton = new Button("Refresh Tables", _ ->refreshAllTables());
        refreshTablesButton.setMinimumSize(buttonSize); refreshTablesButton.setMaximumSize(buttonSize); refreshTablesButton.setPreferredSize(buttonSize);
        menuPanel.add(refreshTablesButton);
        ///
        menuPanel.add(Box.createVerticalGlue());
        ///
        Button cancelButton = new Button("Cancel", _ -> dispose());
        cancelButton.setForeground(Color.RED);
        cancelButton.setMinimumSize(buttonSize); cancelButton.setMaximumSize(buttonSize); cancelButton.setPreferredSize(buttonSize);
        menuPanel.add(cancelButton);

        /* Banquet table selection */
        availableBanquetTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedBanquetRow = availableBanquetTable.rowAtPoint(e.getPoint());
                if (selectedBanquetRow != -1) {
                    selectedBanquetBIN = Long.parseLong(availableBanquetTable.getValueAt(selectedBanquetRow, 0).toString());
                    selectedBanquetName = availableBanquetTable.getValueAt(selectedBanquetRow, 1).toString();
                    selectedBanquetLabel.setText("Banquet " + selectedBanquetBIN + ": " + selectedBanquetName);
                    refreshMealTable();
                    refreshDrinkTable();
                } else {
                    clearAllTableSelections();
                }
            }
        });

        /* Meal table selection */
        mealTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedMealRow = mealTable.rowAtPoint(e.getPoint());
                if (selectedMealRow != -1) {
                    selectedMealID = Long.parseLong(mealTable.getValueAt(selectedMealRow, 0).toString());
                    selectedMealName = mealTable.getValueAt(selectedMealRow, 1).toString();
                    selectedMealLabel.setText("Meal " + selectedMealID + ": " + selectedMealName);
                } else {
                    clearMealTableSelection();
                }
            }
        });

        /* Drink table selection */
        drinkTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedMealRow = drinkTable.getSelectedRow();
                int selectedMealCol = drinkTable.getSelectedColumn();

                if (selectedMealRow != -1 && selectedMealCol != -1) {
                    selectedDrinkName = drinkTable.getValueAt(selectedMealRow, selectedMealCol).toString();
                    selectedDrinkLabel.setText("Drink: " + selectedDrinkName);
                } else {
                    clearDrinkTableSelection();
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
                        NewRegistrationWindow.this,
                        "Closing this window will discard all your choices",
                        "Cancel?",
                        YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    new LoginWindow(controller);
                }
            }
        });

        setVisible(true);
    }

    private void confirmRegister() {
        if (selectedBanquetBIN == -1) {
            showNoBanquetSelectionDialog();
        } else if (selectedMealID == -1) {
            showNoMealSelectionDialog();
        } else if (selectedDrinkName.isEmpty()) {
            showNoDrinkSelectionDialog();
        } else {
            if (controller.registerBanquet(attendeeID, selectedBanquetBIN, selectedMealID, selectedDrinkName, seatField.getText())) {
                JOptionPane.showMessageDialog(this, "You have registered!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                showErrorDialog();
            }
        }
    }

    /**
     * The following method will clear the selection on the drink table and update the data of the drink table.
     */
    private void refreshDrinkTable() {
        clearDrinkTableSelection();
        drinkTable.setModel(new DefaultTableModel(banquetDrinks, banquetDrinkTitles));
    }

    /**
     * The following method will clear the selection on the meal table and update the data of the drink table.
     */
    private void refreshMealTable() {
        clearMealTableSelection();
        banquetMeals = controller.getAllBanquetMeals(selectedBanquetBIN);
        mealTable.setModel(new DefaultTableModel(
                controller.mealListToObjectArray(banquetMeals),
                mealTableAttributes));
    }

    /**
     * The following method will clear all selections and refresh all data. The meal table and the drink table will be cleared.
     */
    private void refreshAllTables() {
        clearAllTableSelections();
        availableBanquets = controller.getAvailableBanquets(attendeeID);
        availableBanquetTable.setModel(new DefaultTableModel(
                controller.banquetListToObjectArray(availableBanquets),
                banquetTableAttributes
        ));
    }

    /**
     * The following method will only clear the selection on the drink table.
     */
    private void clearDrinkTableSelection() {
        selectedDrinkLabel.setText("No drink selected");
        selectedDrinkName = "";
        drinkTable.clearSelection();
    }

    /**
     * The following method will only clear the selection on the meal table.
     */
    private void clearMealTableSelection() {
        selectedMealLabel.setText("No meal selected");
        mealTable.clearSelection();
        selectedMealID = -1;
    }

    /**
     * The following method will clear the selections on all tables. The meal table will be cleared.
     */
    private void clearAllTableSelections() {
        clearDrinkTableSelection();
        clearMealTableSelection();

        banquetMeals.clear();

        drinkTable.setModel(new DefaultTableModel(new Object[][]{}, banquetDrinkTitles));
        mealTable.setModel(new DefaultTableModel(
                controller.mealListToObjectArray(banquetMeals),
                mealTableAttributes
        ));

        selectedBanquetLabel.setText("No banquet selected");
        availableBanquetTable.clearSelection();
        selectedBanquetBIN = -1;
        selectedBanquetName = "";
    }

    private void showNoBanquetSelectionDialog() {
        JOptionPane.showMessageDialog(
                NewRegistrationWindow.this,
                "Please select a banquet.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showNoMealSelectionDialog() {
        JOptionPane.showMessageDialog(
                NewRegistrationWindow.this,
                "Please select a meal.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showNoDrinkSelectionDialog() {
        JOptionPane.showMessageDialog(
                NewRegistrationWindow.this,
                "Please select a drink.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showErrorDialog() {
        JOptionPane.showMessageDialog(this, "Cannot register. Please check again.", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
