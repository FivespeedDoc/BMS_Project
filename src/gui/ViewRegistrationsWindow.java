package gui;

import controller.Controller;

import javax.swing.*;

public final class ViewRegistrationsWindow extends JFrame {
    private final Controller controller;

    public ViewRegistrationsWindow(Controller controller) {
        super("All Registrations");
        this.controller = controller;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        /* Finally */
        add(panel);
        pack();
        setVisible(true);
    }
}
