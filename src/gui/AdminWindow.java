package gui;

import controller.Controller;
import gui.components.Table;
import gui.components.TitleLabel;
import model.entities.Banquet;
import service.managers.BanquetsManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    private static final String[] banquetAttributes = {"BIN", "Name", "Date & Time", "Address", "Location", "Name of the Contact Staff", "Available?", "Quota"};
    private List<Banquet> banquets;

    public AdminWindow(Controller controller, String ID) {
        super("Administrator: " + ID);
        this.controller = controller;
        this.ID = ID;
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

        /* Test Data */
        banquets = new ArrayList<>();
        banquets.add(new Banquet(1,
                "Good",
                new Timestamp(timestamp.getTime()),
                "Hung Hom",
                "Hong Kong",
                "Not a Freerider!",
                false,
                100));
        banquets.add(new Banquet(2,
                "Bad",
                new Timestamp(timestamp.getTime()),
                "Hung Hom",
                "Hong Kong",
                "Freerider!",
                false,
                100));

        /* Banquet title */
        TitleLabel titleLabel = new TitleLabel("Banquets");
        panel.add(titleLabel);

        /* Banquet table */
        Table banquetTable = new Table(new DefaultTableModel(
                BanquetsManager.banquetListToObjectArray(banquets),
                banquetAttributes));
        JScrollPane tableScrollPane = new JScrollPane(banquetTable);
        // tableScrollPane.setPreferredSize(new Dimension(?, ?)); // how to
        panel.add(tableScrollPane);

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
        setVisible(true);
    }
}
