package gui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.JOptionPane.*;

public class AdminWindow extends JFrame {
    public AdminWindow(String adminID) {
        super("Administrator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = showConfirmDialog(
                        AdminWindow.this,
                        "This will log you out.",
                        "Confirm",
                        YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    new LoginWindow();
                }
            }
        });

        setVisible(true);
    }
}
