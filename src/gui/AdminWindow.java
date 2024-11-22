package gui;

import controller.Controller;
import gui.components.RegularLabel;
import gui.components.Table;
import gui.components.TitleLabel;
import model.entities.Banquet;
import service.managers.BanquetsManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static javax.swing.JOptionPane.*;

/**
 * <h3>The Admin Window</h3>
 * <h4>Admins can manage all banquets here.</h4>
 * @author FrankYang0610
 */
public class AdminWindow extends JFrame {
    private final Controller controller;

    private final String ID;

    private long selectedRowBIN;

    private static final String[] banquetAttributes = {"BIN", "Name", "Date & Time", "Address", "Location", "Contact Staff", "Available? (Y/N)", "Quota"};
    private List<Banquet> banquets;

    public AdminWindow(Controller controller, String adminID) {
        super("Administrator: " + adminID);
        this.controller = controller;
        this.ID = adminID;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /* Calendar, should be deleted later */
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10); // Hour
        calendar.set(Calendar.MINUTE, 0); // Minute
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

        /* Banquet title */
        TitleLabel titleLabel = new TitleLabel("Banquets");
        panel.add(titleLabel);

        /* Banquet table */
        banquets = controller.getAllBanquets();
        Table banquetTable = new Table(new DefaultTableModel(
                BanquetsManager.banquetListToObjectArray(banquets),
                banquetAttributes));
        JScrollPane tableScrollPane = new JScrollPane(banquetTable);
        // tableScrollPane.setPreferredSize(new Dimension(?, ?)); // how to
        panel.add(tableScrollPane);

        /* Banquet table selection */
        RegularLabel selectedBanquet = new RegularLabel("No banquet selected");
            selectedBanquet.setFont(new Font("Arial", Font.BOLD, 16));
        ListSelectionModel rowSelectionModel = banquetTable.getSelectionModel();
        rowSelectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Make sure this is triggered when the selection is finished
                int selectedRow = banquetTable.getSelectedRow();
                if (selectedRow != -1) {
                    String rowBIN = banquetTable.getValueAt(selectedRow, 0).toString();
                    selectedRowBIN = Long.parseLong(rowBIN);
                    String rowName = banquetTable.getValueAt(selectedRow, 1).toString();
                    selectedBanquet.setText("Selected banquet: " + rowBIN + ", " + rowName);
                } else {
                    selectedBanquet.setText("No banquet selected");
                }
            }
        });

        /* Banquet editing selection */
        banquetTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // double click
                    int row = banquetTable.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        new EditBanquetWindow(controller, AdminWindow.this, selectedRowBIN);
                    }
                }
            }
        });

        /* Banquet table clear selection */
        banquetTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = banquetTable.rowAtPoint(e.getPoint());
                if (row == -1) {
                    banquetTable.clearSelection();
                }
            }
        });
        panel.add(selectedBanquet);

        /* Finally */
        add(panel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = showConfirmDialog(
                        AdminWindow.this,
                        "Close this window will log you out!",
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

    private void showInfoDialog(long BIN) {
        JOptionPane.showMessageDialog(this, "You're going to edit: " + BIN, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
