package gui;

import controller.Controller;
import gui.components.Button;
import gui.components.RegularLabel;
import gui.components.Table;
import gui.components.TitleLabel;
import model.entities.Banquet;
import model.entities.Meal;
import service.managers.BanquetsManager;
import service.managers.MealsManager;

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
public final class AdminWindow extends JFrame {
    private final Controller controller;

    private final String adminID;

    /* Banquets */
    private static final String[] banquetAttributes = {"BIN", "Name", "Date & Time", "Address", "Location", "Contact Staff", "Available? (Y/N)", "Quota"};
    private final JTable banquetTable;
    private long selectedBanquetBIN = -1;
    private String selectedBanquetName; // for convenience
    private List<Banquet> banquets;

    /* Meals */
    private static final String[] mealsAttributes = {"ID", "Name", "Type", "Price", "Special Cuisine"};
    private final JTable mealTable;
    private long selectedMealID = -1;
    private String selectedMealName; // for convenience
    private List<Meal> banquetMeals;

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
        RegularLabel selectedBanquetLabel = new RegularLabel("No banquet selected");
        selectedBanquetLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        menuPanel.add(selectedBanquetLabel);

        /* Meal selection */
        RegularLabel selectedMealLabel = new RegularLabel("No meal selected");
        selectedMealLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        menuPanel.add(selectedMealLabel);

        menuPanel.add(Box.createVerticalStrut(20));

        /* Menu */
        Dimension buttonSize = new Dimension(200, 50);
        ///
        Button newBanquetButton = new Button("New Banquet", _ -> newBanquet());
            newBanquetButton.setMinimumSize(buttonSize); newBanquetButton.setMaximumSize(buttonSize); newBanquetButton.setPreferredSize(buttonSize);
            menuPanel.add(newBanquetButton);
        ///
        Button editBanquetButton = new Button("Edit Banquet", _ -> editBanquet());
            editBanquetButton.setMinimumSize(buttonSize); editBanquetButton.setMaximumSize(buttonSize); editBanquetButton.setPreferredSize(buttonSize);
            menuPanel.add(editBanquetButton);
        ///
        Button deleteBanquetButton = new Button("Delete Banquet", _ -> deleteBanquet());
            deleteBanquetButton.setMinimumSize(buttonSize); deleteBanquetButton.setMaximumSize(buttonSize); deleteBanquetButton.setPreferredSize(buttonSize);
            menuPanel.add(deleteBanquetButton);
        ///
        menuPanel.add(Box.createVerticalStrut(20));
        ///
        Button newMealButton = new Button("New Meal", _ -> newMeal());
            newMealButton.setMinimumSize(buttonSize); newMealButton.setMaximumSize(buttonSize); newMealButton.setPreferredSize(buttonSize);
            menuPanel.add(newMealButton);
        ///
        Button editMealButton = new Button("Edit Meal", _ -> editMeal());
            editMealButton.setMinimumSize(buttonSize); editMealButton.setMaximumSize(buttonSize); editMealButton.setPreferredSize(buttonSize);
            menuPanel.add(editMealButton);
        ///
        Button deleteMealButton = new Button("Delete Meal", null);
            deleteMealButton.setMinimumSize(buttonSize); deleteMealButton.setMaximumSize(buttonSize); deleteMealButton.setPreferredSize(buttonSize);
            menuPanel.add(deleteMealButton);
        ///
        menuPanel.add(Box.createVerticalStrut(20));
        ///
        Button refreshTablesButton = new Button("Refresh All Tables", _ -> {
            clearAllTableSelections();
            selectedBanquetLabel.setText("No banquet selected");
            selectedBanquetBIN = -1;
            selectedBanquetName = "";
            banquets = controller.getAllBanquets();
            banquetTable.setModel(new DefaultTableModel(
                    BanquetsManager.banquetListToObjectArray(banquets),
                    banquetAttributes
            ));
        });
            refreshTablesButton.setMinimumSize(buttonSize); refreshTablesButton.setMaximumSize(buttonSize); refreshTablesButton.setPreferredSize(buttonSize);
        menuPanel.add(refreshTablesButton);
        ///
        menuPanel.add(Box.createVerticalStrut(20));
        ///
        Button viewRegistrationsButton = new Button("View Registrations", null);
            viewRegistrationsButton.setMinimumSize(buttonSize); viewRegistrationsButton.setMaximumSize(buttonSize); viewRegistrationsButton.setPreferredSize(buttonSize);
        menuPanel.add(viewRegistrationsButton);
        ///
        menuPanel.add(Box.createVerticalGlue());
        ///
        Button logoutButton = new Button("Logout", _ -> {
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
                    selectedBanquetLabel.setText("No banquet selected");
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
                    mealTable.clearSelection();
                    selectedMealLabel.setText("No meal selected");
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
        refreshTables();
    }

    private void editBanquet() {
        if (selectedBanquetBIN != -1) {
            new EditBanquetWindow(controller, AdminWindow.this, selectedBanquetBIN);
            refreshTables();
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

                refreshTables();
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

    private void refreshMealTable() {
        banquetMeals = controller.getAllBanquetMeals(selectedBanquetBIN);
        mealTable.setModel(new DefaultTableModel(
                MealsManager.mealListToObjectArray(banquetMeals),
                mealsAttributes));
    }

    private void refreshTables() {
        clearAllTableSelections();
        banquets = controller.getAllBanquets();
        banquetTable.setModel(new DefaultTableModel(
                BanquetsManager.banquetListToObjectArray(banquets),
                banquetAttributes
        ));
    }

    private void clearAllTableSelections() {
        mealTable.clearSelection();
        selectedMealID = -1;
        mealTable.setModel(new DefaultTableModel(new String[][]{}, mealsAttributes));
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
