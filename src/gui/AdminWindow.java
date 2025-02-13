package gui;

import controller.Controller;
import gui.components.Button;
import gui.components.RegularLabel;
import gui.components.Table;
import gui.components.TitleLabel;
import model.entities.Banquet;
import model.entities.Meal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.*;

/**
 * <h3>The Admin Window</h3>
 * <h4>Admins can manage all banquets here.</h4>
 * @author FrankYang0610
 */
public final class AdminWindow extends JFrame {
    private final Controller controller;

    private final String adminID;

    /* Banquets */
    private final RegularLabel selectedBanquetLabel;
    private static final String[] banquetTableAttributes = {"BIN", "Name", "Date & Time", "Address", "Location", "Contact Staff", "Available? (Y/N)", "Quota", "Registered"};
    private final JTable banquetTable;
    private long selectedBanquetBIN = -1;
    private String selectedBanquetName = ""; // for convenience
    private List<Banquet> banquets = new ArrayList<>(); // for safety

    /* Meals */
    private final RegularLabel selectedMealLabel;
    private static final String[] mealTableAttributes = {"ID", "Name", "Type", "Price", "Special Cuisine"};
    private final JTable mealTable;
    private long selectedMealID = -1;
    private String selectedMealName = ""; // for convenience
    private List<Meal> banquetMeals = new ArrayList<>(); // for safety

    /* Drinks */
    private static final String[] banquetDrinkTitles = {"Drink 1", "Drink 2", "Drink 3", "Drink 4", "Extra Drink!"};
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


        /* Tables panel */
        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));

        /* Banquet table title */
        TitleLabel banquetTableTitle = new TitleLabel("Banquets");
        tablesPanel.add(banquetTableTitle);

        /* Banquet table */
        banquets = controller.getAllBanquets();
        banquetTable = new Table(new DefaultTableModel(
                controller.banquetListToObjectArray(banquets),
                banquetTableAttributes));
        JScrollPane banquetTableScrollPane = new JScrollPane(banquetTable);
        tablesPanel.add(banquetTableScrollPane);

        /* Meal table title */
        TitleLabel mealTableTitle = new TitleLabel("Meals");
        tablesPanel.add(mealTableTitle);

        /* Meal table */
        mealTable = new Table(new DefaultTableModel(new Object[][]{}, mealTableAttributes));
        JScrollPane mealTableScrollPane = new JScrollPane(mealTable);
        mealTableScrollPane.setPreferredSize(new Dimension(mealTableScrollPane.getPreferredSize().width, 200));
        mealTableScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        tablesPanel.add(mealTableScrollPane);

        /* Drink table title */
        TitleLabel drinkTableTitle = new TitleLabel("Drinks (Fixed Supply)");
        tablesPanel.add(drinkTableTitle);

        /* Drink table */
        Table drinkTable = new Table(new DefaultTableModel(banquetDrinks, banquetDrinkTitles));
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
        selectedBanquetLabel = new RegularLabel("No banquet selected");
        selectedBanquetLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        menuPanel.add(selectedBanquetLabel);

        /* Meal selection */
        selectedMealLabel = new RegularLabel("No meal selected");
        selectedMealLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        menuPanel.add(selectedMealLabel);

        menuPanel.add(Box.createVerticalStrut(20));

        /* Menu */
        Dimension buttonSize = new Dimension(200, 50);
        ///
        Button newBanquetButton = new Button("New Banquet", e -> newBanquet());
            newBanquetButton.setMinimumSize(buttonSize); newBanquetButton.setMaximumSize(buttonSize); newBanquetButton.setPreferredSize(buttonSize);
            menuPanel.add(newBanquetButton);
        ///
        Button editBanquetButton = new Button("Edit Banquet", e -> editBanquet());
            editBanquetButton.setMinimumSize(buttonSize); editBanquetButton.setMaximumSize(buttonSize); editBanquetButton.setPreferredSize(buttonSize);
            menuPanel.add(editBanquetButton);
        ///
        Button deleteBanquetButton = new Button("Delete Banquet", e -> deleteBanquet());
            deleteBanquetButton.setMinimumSize(buttonSize); deleteBanquetButton.setMaximumSize(buttonSize); deleteBanquetButton.setPreferredSize(buttonSize);
            menuPanel.add(deleteBanquetButton);
        ///
        menuPanel.add(Box.createVerticalStrut(20));
        ///
        Button newMealButton = new Button("New Meal", e -> newMeal());
            newMealButton.setMinimumSize(buttonSize); newMealButton.setMaximumSize(buttonSize); newMealButton.setPreferredSize(buttonSize);
            menuPanel.add(newMealButton);
        ///
        Button editMealButton = new Button("Edit Meal", e -> editMeal());
            editMealButton.setMinimumSize(buttonSize); editMealButton.setMaximumSize(buttonSize); editMealButton.setPreferredSize(buttonSize);
            menuPanel.add(editMealButton);
        ///
        Button deleteMealButton = new Button("Delete Meal", e -> deleteMeal());
            deleteMealButton.setMinimumSize(buttonSize); deleteMealButton.setMaximumSize(buttonSize); deleteMealButton.setPreferredSize(buttonSize);
            menuPanel.add(deleteMealButton);
        ///
        menuPanel.add(Box.createVerticalStrut(20));
        ///
        Button refreshTablesButton = new Button("Refresh All Tables", e -> refreshAllTables());
            refreshTablesButton.setMinimumSize(buttonSize); refreshTablesButton.setMaximumSize(buttonSize); refreshTablesButton.setPreferredSize(buttonSize);
        menuPanel.add(refreshTablesButton);
        ///
        menuPanel.add(Box.createVerticalStrut(20));
        ///
        Button analysis = new Button("Analysis", e -> new AnalysisWindow(controller));
            analysis.setMinimumSize(buttonSize); analysis.setMaximumSize(buttonSize); analysis.setPreferredSize(buttonSize);
        menuPanel.add(analysis);
        ///
        menuPanel.add(Box.createVerticalGlue());
        ///
        Button logoutButton = new Button("Logout", e -> {
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
        logoutButton.setForeground(Color.RED);
        logoutButton.setMinimumSize(buttonSize); logoutButton.setMaximumSize(buttonSize); logoutButton.setPreferredSize(buttonSize);
        menuPanel.add(logoutButton);


        /* Banquet table selection */
        banquetTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedBanquetRow = banquetTable.rowAtPoint(e.getPoint());
                if (selectedBanquetRow != -1) {
                    selectedBanquetBIN = Long.parseLong(banquetTable.getValueAt(selectedBanquetRow, 0).toString());
                    selectedBanquetName = banquetTable.getValueAt(selectedBanquetRow, 1).toString();
                    selectedBanquetLabel.setText("Banquet " + selectedBanquetBIN + ": " + selectedBanquetName);
                    refreshMealTable();

                    // double-click a banquet
                    if (e.getClickCount() == 2) {
                        editBanquet();
                    }
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

                    // double-click a meal
                    if (e.getClickCount() == 2) {
                        editMeal();
                    }
                } else {
                    clearMealTableSelection();
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
        refreshAllTables();
    }

    private void editBanquet() {
        if (selectedBanquetBIN != -1) {
            new EditBanquetWindow(controller, AdminWindow.this, selectedBanquetBIN);
            refreshAllTables();
        } else {
            showNoBanquetSelectionDialog();
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
                refreshAllTables();
            }
        } else {
            showNoBanquetSelectionDialog();
        }
    }

    private void newMeal() {
        if (selectedBanquetBIN != -1) {
            if (banquetMeals.size() == 4) {
                showNoMoreMealToAddDialog();
                return;
            }
            new NewMealWindow(controller, this, selectedBanquetBIN);
            refreshMealTable();
        } else {
            showNoBanquetSelectionDialog();
        }
    }

    private void editMeal() {
        if (selectedBanquetBIN == -1) {
            showNoBanquetSelectionDialog();
        } else if (selectedMealID == -1) {
            showNoMealSelectionDialog();
        } else {
            new EditMealWindow(controller, this, selectedBanquetBIN, selectedMealID);
            refreshMealTable();
        }
    }

    private void deleteMeal() {
        if (selectedBanquetBIN == -1) {
            showNoBanquetSelectionDialog();
        } else if (selectedMealID == -1) {
            showNoMealSelectionDialog();
        } else {
            int confirm = JOptionPane.showConfirmDialog(
                    AdminWindow.this,
                    "Are you sure to delete this meal: " + selectedMealID + ": " + selectedMealName + "? " + "This operation cannot be undone!",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean result = controller.deleteMeal(selectedBanquetBIN, selectedMealID);
                if (!result) {
                    JOptionPane.showMessageDialog(
                            AdminWindow.this,
                            "Cannot delete the meal.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                refreshAllTables();
            }
        }
    }

    /**
     * The following method will clear the selection on the meal table and update the data of the table.
     */
    private void refreshMealTable() {
        clearMealTableSelection();
        banquetMeals = controller.getAllBanquetMeals(selectedBanquetBIN);
        mealTable.setModel(new DefaultTableModel(
                controller.mealListToObjectArray(banquetMeals),
                mealTableAttributes));
    }

    /**
     * The following method will clear all selections and refresh all data. The meal table will be cleared.
     */
    private void refreshAllTables() {
        clearAllTableSelections();
        banquets = controller.getAllBanquets();
        banquetTable.setModel(new DefaultTableModel(
                controller.banquetListToObjectArray(banquets),
                banquetTableAttributes
        ));
    }

    /**
     * The following method will only clear the selection on the meal table.
     */
    private void clearMealTableSelection() {
        selectedMealLabel.setText("No meal selected");
        mealTable.clearSelection();
        selectedMealID = -1;
        selectedMealName = "";
    }

    /**
     * The following method will clear the selections on all tables. The meal table will be cleared.
     */
    private void clearAllTableSelections() {
        clearMealTableSelection();
        banquetMeals.clear();
        mealTable.setModel(new DefaultTableModel(
                controller.mealListToObjectArray(banquetMeals),
                mealTableAttributes
        ));
        selectedBanquetLabel.setText("No banquet selected");
        banquetTable.clearSelection();
        selectedBanquetBIN = -1;
        selectedBanquetName = "";
    }

    private void showNoBanquetSelectionDialog() {
        JOptionPane.showMessageDialog(
                AdminWindow.this,
                "Please select a banquet to manage.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showNoMealSelectionDialog() {
        JOptionPane.showMessageDialog(
                AdminWindow.this,
                "Please select a meal to manage.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showNoMoreMealToAddDialog() {
        JOptionPane.showMessageDialog(
                AdminWindow.this,
                "It is only allowed for four meals in each banquet.",
                "Can't add a new meal",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
