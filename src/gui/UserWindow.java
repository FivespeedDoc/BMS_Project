package gui;

import javax.swing.*;
import java.awt.*;

import controller.Controller;
import service.managers.AttendeeAccountsManager;

//@Deprecated
public class UserWindow extends JFrame {


    private final String userID;

    private final Controller controller;

    public UserWindow(Controller controller, String userID) {
        this.userID = userID;
        this.controller = controller;
        //AttendeeAccountsManager AAM = new AttendeeAccountsManager();

        setTitle("User Window");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 20));

        JButton banquetRegistrationButton = new JButton("  Banquet Registration");
        JButton banquetWithdrawButton = new JButton("  Banquet Withdraw");
        JButton banquetChangeButton = new JButton("  Banquet Change");
        JButton editUserInfoButton = new JButton("  Edit User Information");
        JButton exitButton = new JButton("  Deregister");

        banquetRegistrationButton.addActionListener(e -> showBanquetRegistrationWindow());
        banquetWithdrawButton.addActionListener(e -> showBanquetWithdrawWindow());
        banquetChangeButton.addActionListener(e -> showBanquetChangeWindow());
        editUserInfoButton.addActionListener(e -> showEditUserInfoWindow());
        exitButton.addActionListener(e -> showRemoveRegistrationWindow());

        panel.add(banquetRegistrationButton);
        panel.add(banquetWithdrawButton);
        panel.add(banquetChangeButton);
        panel.add(editUserInfoButton);
        panel.add(exitButton);

        add(panel);
    }

    private void showBanquetRegistrationWindow() {
        JOptionPane.showMessageDialog(this, "Banquet Registration Window", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showBanquetWithdrawWindow() {
        JOptionPane.showMessageDialog(this, "Banquet Withdraw Window", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showBanquetChangeWindow() {
        JOptionPane.showMessageDialog(this, "Banquet Change Window", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showEditUserInfoWindow() {

        new ChangeUserInformationWindow(controller, this,controller.getAttendee(userID));
    }

    private void showRemoveRegistrationWindow() {
        JOptionPane.showMessageDialog(this, "Remove Registration Window", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    
    // Causes an error
    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(UserWindow::new);
    //}
}
