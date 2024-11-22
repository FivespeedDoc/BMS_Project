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

    private final JTable banquetTable;

    private long selectedRowBIN;

    private String selectedRowName; // for convenience

    private static final String[] banquetAttributes = {"BIN", "Name", "Date & Time", "Address", "Location", "Contact Staff", "Available? (Y/N)", "Quota"};

    private List<Banquet> banquets;

    public AdminWindow(Controller controller, String adminID) {
        super("Administrator: " + adminID);
        this.controller = controller;
        this.adminID = adminID;
        this.selectedRowBIN = -1;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));


        /* Banquet table panel */
        JPanel banquetTablePanel = new JPanel();
        banquetTablePanel.setLayout(new BoxLayout(banquetTablePanel, BoxLayout.Y_AXIS));

        /* Banquet title */
        TitleLabel titleLabel = new TitleLabel("Banquets");
        banquetTablePanel.add(titleLabel);

        /* Banquet table */
        banquets = controller.getAllBanquets();
        banquetTable = new Table(new DefaultTableModel(
                BanquetsManager.banquetListToObjectArray(banquets),
                banquetAttributes));
        JScrollPane tableScrollPane = new JScrollPane(banquetTable);
        banquetTablePanel.add(tableScrollPane);

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
                    selectedRowBIN = Long.parseLong(banquetTable.getValueAt(selectedRow, 0).toString());
                    selectedRowName = banquetTable.getValueAt(selectedRow, 1).toString();
                    selectedBanquet.setText("Selected " + selectedRowBIN + ": " + selectedRowName);

                    if (e.getClickCount() == 2) {
                        editBanquet();
                    }
                } else {
                    banquetTable.clearSelection();
                    selectedRowBIN = -1;
                    selectedRowName = "";
                    selectedBanquet.setText("No banquet selected");
                }
            }
        });


        /* Finally */
        panel.add(banquetTablePanel);
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
        banquets = controller.getAllBanquets();
        banquetTable.setModel(new DefaultTableModel(
                BanquetsManager.banquetListToObjectArray(banquets),
                banquetAttributes
        ));

        banquetTable.clearSelection();
        selectedRowBIN = -1;
        selectedRowName = "";
    }

    private void editBanquet() {
        if (selectedRowBIN != -1) {
            new EditBanquetWindow(controller, AdminWindow.this, selectedRowBIN);
            banquets = controller.getAllBanquets();
            banquetTable.setModel(new DefaultTableModel(
                    BanquetsManager.banquetListToObjectArray(banquets),
                    banquetAttributes
            ));

            banquetTable.clearSelection();
            selectedRowBIN = -1;
            selectedRowName = "";
        } else {
            showNoSelectionDialog();
        }
    }

    private void deleteBanquet() {
        if (selectedRowBIN != -1) {
            int confirm = JOptionPane.showConfirmDialog(
                    AdminWindow.this,
                    "Are you sure to delete this banquet: " + selectedRowBIN + ": " + selectedRowName + "? " + "This operation cannot be undone!",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean result = controller.deleteBanquet(selectedRowBIN);

                if (!result) {
                    JOptionPane.showMessageDialog(
                            AdminWindow.this,
                            "Cannot delete the banquet.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

                banquets = controller.getAllBanquets();
                banquetTable.setModel(new DefaultTableModel(
                        BanquetsManager.banquetListToObjectArray(banquets),
                        banquetAttributes
                ));
            }

            banquetTable.clearSelection();
            selectedRowBIN = -1;
            selectedRowName = "";
        } else {
            showNoSelectionDialog();
        }
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
