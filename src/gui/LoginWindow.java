package gui;

import controller.Controller;
import gui.components.*;
import gui.components.Button;
import gui.components.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * <h3>The Login Window</h3>
 * @author FrankYang0610
 */
public final class LoginWindow extends JFrame {
    private final Controller controller;

    private final TextField IDField; // account ID for admin and users

    private final PasswordField passwordField;

    public LoginWindow(Controller controller) {
        super("Login");
        this.controller = controller;
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        /* The panel */
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /* The title and the project name */
        TitleLabel title = new TitleLabel("Welcome to the Banquet Management System (BMS)");
        panel.add(title, BorderLayout.CENTER);
        RegularLabel projectName = new RegularLabel("Project Spix Macaw");
            projectName.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(projectName, BorderLayout.CENTER);

        panel.add(Box.createVerticalStrut(20));

        /* Authors */
        panel.add(new RegularLabel("YANG Xikun, YANG Jinkun, REN Yixiao"));
        panel.add(new RegularLabel("Arda EREN, MIAO Xutao"));

        panel.add(Box.createVerticalStrut(5));

        Button viewContributionDistribution = new Button("How the contribution is distributed???", _ -> {
            JOptionPane.showMessageDialog(this,
                            "YANG Xikun designed and coded the application.\n" +
                            "YANG Jinkun designed the Model part and maintained the repo.\n" +
                            "REN Yixiao designed the database schema and configured the database.\n\n" +

                            "The following are all objective statistical data:\n\n" +

                            "Github commit records (YANG Jinkun's master branch, ~ Nov 26):\n" +
                            "YANG Xikun (@FrankYang0610): Added lines: 4679, Removed lines: 2017, Total lines changed: 2662\n" +
                            "YANG Jinkun (@jimyang, @FivespeedDoc): Added lines: 1881, Removed lines: 82, Total lines changed: 1799\n" +
                            "REN Yixiao (@ZacharyRE): Added lines: 603, Removed lines: 452, Total lines changed: 151\n" +
                            "Arda EREN (@LesPoar): Added lines: 241, Removed lines: 30, Total lines changed: 211\n" +
                            "MIAO Xutao (@WenceXT): Added lines: 0, Removed lines: 0, Total lines changed: 0\n\n" +

                            "The first stage started on Nov 23.\n" +
                            "On Oct 23, YANG Xikun reminded the deadline was Nov 2, and encouraged to started working.\n" +
                            "By Oct 27, no one said anything.\n" +
                            "By Nov 2, REN Yixiao, YANG Xikun, YANG Jinkun, and Arda EREN proposed the finalized report.\n\n" +

                            "The second stage started on Nov 2.\n" +
                            "By Nov 7, no one said anything.\n" +
                            "On Nov 7, REN Yixiao created a repo for collaborative coding.\n" +
                            "On Nov 11, YANG Xikun proposed using javax.swing to develop the frontend.\n" +
                            "By Nov 16, No one coded anything.\n" +
                            "On Nov 16, YANG Xikun started encouraging the group to contribute to this project.\n" +
                            "By Nov 19, Only YANG Jinkun and YANG Xikun pushed code to REN Yixiao's repo.\n" +
                            "On Nov 19, YANG Jinkun created the final repo for this project.\n" +
                            "By Nov 26, all group members could to push their code to the master branch without any restrictions.\n\n" +

                            "From Nov 26, the master branch was protected for assignment submission. While others could still pull requests.\n\n" +

                            "YANG Xikun explicitly encouraged collaborative work on Oct 23, Oct 27, Nov 11, Nov 16, Nov 18, Nov 19, Nov 20.\n" +
                            "While, he is not the 'leader' of the group.\n" +
                            "During the span of a month, anyone could start the project, and made contributions at any time.",
                    "How the contribution is distributed???",
                    JOptionPane.INFORMATION_MESSAGE);

            JOptionPane.showMessageDialog(this,
                            "YANG Xikun designed and coded the application.\n" +
                            "YANG Jinkun designed the Model part and maintained the repo.\n" +
                            "REN Yixiao designed the database schema and configured the database.\n\n" +

                            "The following are all objective statistical data:\n\n" +

                            "Github commit records (YANG Jinkun's master branch, ~ Nov 26):\n" +
                            "YANG Xikun (@FrankYang0610): Added lines: 4679, Removed lines: 2017, Total lines changed: 2662\n" +
                            "YANG Jinkun (@jimyang, @FivespeedDoc): Added lines: 1881, Removed lines: 82, Total lines changed: 1799\n" +
                            "REN Yixiao (@ZacharyRE): Added lines: 603, Removed lines: 452, Total lines changed: 151\n" +
                            "Arda EREN (@LesPoar): Added lines: 241, Removed lines: 30, Total lines changed: 211\n" +
                            "MIAO Xutao (@WenceXT): Added lines: 0, Removed lines: 0, Total lines changed: 0\n\n" +

                            "The first stage started on Nov 23.\n" +
                            "On Oct 23, YANG Xikun reminded the deadline was Nov 2, and encouraged to started working.\n" +
                            "By Oct 27, no one said anything.\n" +
                            "By Nov 2, REN Yixiao, YANG Xikun, YANG Jinkun, and Arda EREN proposed the finalized report.\n\n" +

                            "The second stage started on Nov 2.\n" +
                            "By Nov 7, no one said anything.\n" +
                            "On Nov 7, REN Yixiao created a repo for collaborative coding.\n" +
                            "On Nov 11, YANG Xikun proposed using javax.swing to develop the frontend.\n" +
                            "By Nov 16, No one coded anything.\n" +
                            "On Nov 16, YANG Xikun started encouraging the group to contribute to this project.\n" +
                            "By Nov 19, Only YANG Jinkun and YANG Xikun pushed code to REN Yixiao's repo.\n" +
                            "On Nov 19, YANG Jinkun created the final repo for this project.\n" +
                            "By Nov 26, all group members could to push their code to the master branch without any restrictions.\n\n" +

                            "From Nov 26, the master branch was protected for assignment submission. While others could still pull requests.\n\n" +

                            "YANG Xikun explicitly encouraged collaborative work on Oct 23, Oct 27, Nov 11, Nov 16, Nov 18, Nov 19, Nov 20.\n" +
                            "While, he is not the 'leader' of the group.\n" +
                            "During the span of a month, anyone could start the project, and made contributions at any time.",
                    "How the contribution is distributed???",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(viewContributionDistribution);

        panel.add(Box.createVerticalStrut(20));

        /* Input boxes */
        IDField = new TextField();
        passwordField = new PasswordField();
        panel.add(new XPanel("Account ID", IDField));
        panel.add(new XPanel(passwordField, "REG"));

        panel.add(Box.createVerticalStrut(20));

        /* Buttons */
        ButtonsPanel buttons = new ButtonsPanel();
        ///
        Button exitButton = new Button("Exit", _ -> System.exit(0));
            exitButton.setForeground(Color.RED);
            buttons.add(exitButton);
        ///
        Button adminLoginButton = new Button("Admin Login", this::adminLogin);
            buttons.add(adminLoginButton);
        ///
        Button userLoginButton = new Button("User Login", this::userLogin);
            buttons.add(userLoginButton);
        ///
        buttons.add(Box.createHorizontalGlue());
        ///
        Button userSignupButton = new Button("Signup", _ -> new SignupWindow(controller, this));
            buttons.add(userSignupButton);
        ///
        getRootPane().setDefaultButton(userLoginButton);
        SwingUtilities.invokeLater(userLoginButton::requestFocusInWindow);
        panel.add(buttons);

        /* Finally */
        add(panel);
        setUndecorated(true);
        setVisible(true);
    }

    private void userLogin(ActionEvent e) {
        String userID = IDField.getText();
        char[] password = passwordField.getPassword();

        if (controller.isUser(userID, password)) {
            userLoginSuccessfulDialog(userID);
            new UserWindow(controller, userID);
            dispose();
        } else {
            showWrongLoginInfoDialog();
        }
    }

    private void adminLogin(ActionEvent e) {
        String adminID = IDField.getText();
        char[] password = passwordField.getPassword();

        if (controller.isAdmin(adminID, password)) {
            adminLoginSuccessfulDialog(adminID);
            new AdminWindow(controller, adminID);
            dispose();
        } else {
            showWrongLoginInfoDialog();
        }
    }

    private void userLoginSuccessfulDialog(String userID) {
        JOptionPane.showMessageDialog(this, "You will be logged in as: " + userID, "User Login Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void adminLoginSuccessfulDialog(String adminID) {
        JOptionPane.showMessageDialog(this, "You will be logged in as: " + adminID, "Admin Login Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showWrongLoginInfoDialog() {
        JOptionPane.showMessageDialog(this, "Cannot login! Wrong account ID or password.", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
