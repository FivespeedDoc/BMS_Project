package gui;

import controller.Controller;
import gui.components.Button;
import gui.components.Table;
import gui.components.TitleLabel;
import model.entities.Banquet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>The Analysis Window</h3>
 * @author FrankYang0610
 */
public final class AnalysisWindow extends JFrame {
    private final Controller controller;

    /* Banquets */
    private static final String[] banquetTableAttributes = {"BIN", "Name", "Date & Time", "Address", "Location", "Contact Staff", "Available? (Y/N)", "Quota", "Registered"};
    private final JTable banquetTable;
    private List<Banquet> banquets = new ArrayList<>(); // for safety

    /* Meals */
    private static final String[] mealTableAttributes = {"Name", "Count"};
    private final JTable mealTable;
    private List<String[]> rankedMeals = new ArrayList<>(); // for safety

    /* Drinks */
    private static final String[] drinkTableAttributes = {"Name", "Count"};
    private final JTable drinkTable;
    private List<String[]> rankedDrinks = new ArrayList<>(); // for safety

    /* Seats */
    private static final String[] seatTableAttributes = {"Seat", "Count"};
    private final JTable seatsTable;
    private List<String[]> rankedSeats = new ArrayList<>(); // for safety

    public AnalysisWindow(Controller controller) {
        super("BMS Analysis");
        this.controller = controller;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        /* Tables panel */
        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));

        /* Banquet table title */
        TitleLabel banquetTableTitle = new TitleLabel("Banquets (ranked by number of registered attendees)");
        tablesPanel.add(banquetTableTitle);

        /* Banquet table */
        banquetTable = new Table(new DefaultTableModel(new Object[][]{}, banquetTableAttributes));
        banquetTable.setRowSelectionAllowed(false);
        banquetTable.setColumnSelectionAllowed(false);
        banquetTable.setCellSelectionEnabled(false);
        JScrollPane banquetTableScrollPane = new JScrollPane(banquetTable);
        tablesPanel.add(banquetTableScrollPane);

        /* Meal table title */
        TitleLabel mealTableTitle = new TitleLabel("Meals");
        tablesPanel.add(mealTableTitle);

        /* Meal table */
        mealTable = new Table(new DefaultTableModel(new Object[][]{}, mealTableAttributes));
        mealTable.setRowSelectionAllowed(false);
        mealTable.setColumnSelectionAllowed(false);
        mealTable.setCellSelectionEnabled(false);
        JScrollPane mealTableScrollPane = new JScrollPane(mealTable);
        tablesPanel.add(mealTableScrollPane);

        /* Drink table title */
        TitleLabel drinkTableTitle = new TitleLabel("Drinks");
        tablesPanel.add(drinkTableTitle);

        /* Drink table */
        drinkTable = new Table(new DefaultTableModel(new Object[][]{}, drinkTableAttributes));
        drinkTable.setRowSelectionAllowed(false);
        drinkTable.setColumnSelectionAllowed(false);
        drinkTable.setCellSelectionEnabled(false);
        JScrollPane drinkTableScrollPane = new JScrollPane(drinkTable);
        tablesPanel.add(drinkTableScrollPane);

        /* Seat table title */
        TitleLabel seatTableTitle = new TitleLabel("Seats");
        tablesPanel.add(seatTableTitle);

        /* Seat table */
        seatsTable = new Table(new DefaultTableModel(new Object[][]{}, seatTableAttributes));
        seatsTable.setRowSelectionAllowed(false);
        seatsTable.setColumnSelectionAllowed(false);
        seatsTable.setCellSelectionEnabled(false);
        JScrollPane seatsTableScrollPane = new JScrollPane(seatsTable);
        tablesPanel.add(seatsTableScrollPane);

        /* Tables initialization */
        refreshAllTables();

        /* Menu panel */
        JPanel menuPanel = new JPanel();
        menuPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setMinimumSize(new Dimension(230, 0));
        menuPanel.setMaximumSize(new Dimension(230, Integer.MAX_VALUE));
        menuPanel.setPreferredSize(new Dimension(230, menuPanel.getPreferredSize().height));

        /* Menu */
        Dimension buttonSize = new Dimension(200, 50);
        ///
        Button refreshAllTablesButton = new Button("Refresh All Tables", _ -> refreshAllTables());
        refreshAllTablesButton.setMinimumSize(buttonSize); refreshAllTablesButton.setMaximumSize(buttonSize); refreshAllTablesButton.setPreferredSize(buttonSize);
        menuPanel.add(refreshAllTablesButton);
        ///
        Button exportToPDFButton = new Button("Export to PDF", null); exportToPDFButton.setEnabled(false);
        exportToPDFButton.setMinimumSize(buttonSize); exportToPDFButton.setMaximumSize(buttonSize); exportToPDFButton.setPreferredSize(buttonSize);
        menuPanel.add(exportToPDFButton);
        ///
        menuPanel.add(Box.createVerticalGlue());
        ///
        Button leaveButton = new Button("Leave", _ -> dispose());
        leaveButton.setMinimumSize(buttonSize); leaveButton.setMaximumSize(buttonSize); leaveButton.setPreferredSize(buttonSize);
        menuPanel.add(leaveButton);

        /* Finally */
        panel.add(tablesPanel);
        panel.add(menuPanel);
        add(panel);
        add(panel);
        pack();
        setVisible(true);
    }

    void refreshAllTables() {
        banquets = controller.getAllBanquets();
        rankedMeals = controller.getRankedMeals();
        rankedDrinks = controller.getRankedDrinks();
        rankedSeats = controller.getRankedSeats();

        banquetTable.setModel(new DefaultTableModel(
                controller.banquetListToSortedObjectArray(banquets),
                banquetTableAttributes));
        mealTable.setModel(new DefaultTableModel(controller.rankedEntityListToObjectArray(rankedMeals), mealTableAttributes));
        drinkTable.setModel(new DefaultTableModel(controller.rankedEntityListToObjectArray(rankedDrinks), drinkTableAttributes));
        seatsTable.setModel(new DefaultTableModel(controller.rankedEntityListToObjectArray(rankedSeats), seatTableAttributes));
    }
}
