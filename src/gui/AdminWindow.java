package gui;

import controller.Controller;
import gui.components.Button;
import gui.components.RegularLabel;
import gui.components.Table;
import gui.components.TitleLabel;
import model.entities.Banquet;
import service.managers.BanquetsManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static javax.swing.JOptionPane.*;

/**
 * <h3>The Admin Window</h3>
 * <h4>Admins can manage all banquets here.</h4>
 * @author FrankYang0610
 */
public class AdminWindow extends JFrame {
    private final Controller controller;

    private final String adminID;

    /* Banquets */
    private static final String[] banquetAttributes = {"BIN", "Name", "Date & Time", "Address", "Location", "Contact Staff", "Available? (Y/N)", "Quota"};
    private final JTable banquetTable;
    private long selectedBanquetBIN;
    private String selectedBanquetName; // for convenience
    private List<Banquet> banquets;

    /* Meals */
    private static final String[] mealsAttributes = {"ID", "Name", "Type", "Price", "Special Cuisine"};
    private final JTable mealTable;
    private long selectedMealID;
    private List<String> banquetMeals;

    /* Drinks */
    private static final String[] banquetDrinksTitle = {"Drink 1", "Drink 2", "Drink 3", "Drink 4", "Extra Drink!"};
    private static final String[][] banquetDrinks = {{"Coke", "Tea", "Coffee", "Lemon Tea", "Dark Drink (Don't try!)"}};

    public AdminWindow(Controller controller, String adminID) {
        super("Administrator: " + adminID);
        this.controller = controller;
        this.adminID = adminID;
        this.selectedBanquetBIN = -1;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));


        /* Banquet table panel */
        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));

        /* Banquet title */
        TitleLabel banquetTableTitle = new TitleLabel("Banquets");
        tablesPanel.add(banquetTableTitle);

        /* Banquet table */
        banquets = controller.getAllBanquets();
        banquetTable = new Table(new DefaultTableModel(
                BanquetsManager.banquetListToObjectArray(banquets),
                banquetAttributes));
        JScrollPane banquetTableScrollPane = new JScrollPane(banquetTable);
        tablesPanel.add(banquetTableScrollPane);

        /* Meal title */
        TitleLabel mealTableTitle = new TitleLabel("Meals");
        tablesPanel.add(mealTableTitle);

        /* Meal table */
        mealTable = new Table(new DefaultTableModel(new Object[][]{}, mealsAttributes));
        mealTable.setRowSelectionAllowed(false);
        mealTable.setColumnSelectionAllowed(false);
        mealTable.setCellSelectionEnabled(false);
        JScrollPane mealTableScrollPane = new JScrollPane(mealTable);
        mealTableScrollPane.setPreferredSize(new Dimension(mealTableScrollPane.getPreferredSize().width, 200));
        mealTableScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        tablesPanel.add(mealTableScrollPane);

        /* Drink title */
        TitleLabel drinkTableTitle = new TitleLabel("Drinks (Fixed Supply)");
        tablesPanel.add(drinkTableTitle);

        /* Drink table */
        Table drinkTable = new Table(new DefaultTableModel(banquetDrinks, banquetDrinksTitle));
        drinkTable.setRowSelectionAllowed(false);
        drinkTable.setColumnSelectionAllowed(false);
        drinkTable.setCellSelectionEnabled(false);
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
        RegularLabel selectedBanquet = new RegularLabel("No banquet selected");
        selectedBanquet.setFont(new Font("Arial", Font.PLAIN, 16));
        menuPanel.add(selectedBanquet);

        menuPanel.add(Box.createVerticalStrut(20));

        /* Menu */
        Dimension buttonSize = new Dimension(200, 50);
        Button newBanquet = new Button("New Banquet", _ -> newBanquet());
            newBanquet.setMinimumSize(buttonSize); newBanquet.setMaximumSize(buttonSize); newBanquet.setPreferredSize(buttonSize);
            menuPanel.add(newBanquet);
        Button editBanquet = new Button("Edit Banquet", _ -> editBanquet());
            editBanquet.setMinimumSize(buttonSize); editBanquet.setMaximumSize(buttonSize); editBanquet.setPreferredSize(buttonSize);
            menuPanel.add(editBanquet);
        Button deleteBanquet = new Button("Delete Banquet", _ -> deleteBanquet());
            deleteBanquet.setMinimumSize(buttonSize); deleteBanquet.setMaximumSize(buttonSize); deleteBanquet.setPreferredSize(buttonSize);
            menuPanel.add(deleteBanquet);
        menuPanel.add(Box.createVerticalStrut(20));
        Button newMeal = new Button("New Meal", null);
            newMeal.setMinimumSize(buttonSize); newMeal.setMaximumSize(buttonSize); newMeal.setPreferredSize(buttonSize);
            menuPanel.add(newMeal);
        Button editMeal = new Button("Edit Meal", null);
            editMeal.setMinimumSize(buttonSize); editMeal.setMaximumSize(buttonSize); editMeal.setPreferredSize(buttonSize);
            menuPanel.add(editMeal);
        Button deleteMeal = new Button("Delete Meal", null);
            deleteMeal.setMinimumSize(buttonSize); deleteMeal.setMaximumSize(buttonSize); deleteMeal.setPreferredSize(buttonSize);
            menuPanel.add(deleteMeal);
        menuPanel.add(Box.createVerticalStrut(20));
        Button refreshTables = new Button("Refresh All Tables", _ -> {
            banquetTable.clearSelection();
            selectedBanquet.setText("No banquet selected");
            selectedBanquetBIN = -1;
            selectedBanquetName = "";
            banquets = controller.getAllBanquets();
            banquetTable.setModel(new DefaultTableModel(
                    BanquetsManager.banquetListToObjectArray(banquets),
                    banquetAttributes
            ));
        });
            refreshTables.setMinimumSize(buttonSize); refreshTables.setMaximumSize(buttonSize); refreshTables.setPreferredSize(buttonSize);
        menuPanel.add(refreshTables);
        menuPanel.add(Box.createVerticalGlue());
        Button logout = new Button("Logout", _ -> {
            int confirm = showConfirmDialog(
                    AdminWindow.this,
                    "This operation will log you out.",
                    "Log out?",
                    YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                AdminWindow.this.dispose();
                new LoginWindow(controller);
            }
        });
            logout.setForeground(Color.RED);
            logout.setMinimumSize(buttonSize); logout.setMaximumSize(buttonSize); logout.setPreferredSize(buttonSize);
            menuPanel.add(logout);


        /* Banquet table selection */
        banquetTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = banquetTable.rowAtPoint(e.getPoint());
                if (selectedRow != -1) {
                    selectedBanquetBIN = Long.parseLong(banquetTable.getValueAt(selectedRow, 0).toString());
                    selectedBanquetName = banquetTable.getValueAt(selectedRow, 1).toString();
                    selectedBanquet.setText("Selected " + selectedBanquetBIN + ": " + selectedBanquetName);

                    if (e.getClickCount() == 2) {
                        editBanquet();
                    }
                } else {
                    selectedBanquet.setText("No banquet selected");
                    clearTableSelections();
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
                        AdminWindow.this,
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

    private void newBanquet() {
        new NewBanquetWindow(controller, AdminWindow.this);
        clearTableSelections();
        refreshTables();
    }

    private void editBanquet() {
        if (selectedBanquetBIN != -1) {
            new EditBanquetWindow(controller, AdminWindow.this, selectedBanquetBIN);
            clearTableSelections();
            refreshTables();
        } else {
            showNoSelectionDialog();
        }
    }

    private void deleteBanquet() {
        if (selectedBanquetBIN != -1) {
            int confirm = JOptionPane.showConfirmDialog(
                    AdminWindow.this,
                    "Are you sure to delete this banquet: " + selectedBanquetBIN + ": " + selectedBanquetName + "? " + "This operation cannot be undone!",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean result = controller.deleteBanquet(selectedBanquetBIN);

                if (!result) {
                    JOptionPane.showMessageDialog(
                            AdminWindow.this,
                            "Cannot delete the banquet.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

                refreshTables();
            }

            clearTableSelections();
        } else {
            showNoSelectionDialog();
        }
    }

    private void clearTableSelections() {
        banquetTable.clearSelection();
        selectedBanquetBIN = -1;
        selectedBanquetName = "";
    }

    private void refreshTables() {
        banquets = controller.getAllBanquets();
        banquetTable.setModel(new DefaultTableModel(
                BanquetsManager.banquetListToObjectArray(banquets),
                banquetAttributes
        ));
    }

    private void showNoSelectionDialog() {
        JOptionPane.showMessageDialog(
                AdminWindow.this,
                "Please select a banquet to manage.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
