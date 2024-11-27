package gui;

import controller.Controller;
import gui.components.*;
import gui.components.Button;
import gui.components.TextField;
import model.entities.AttendeeAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;


public class ContactWindow extends JDialog{

    private final Controller controller;

    private final JFrame currentWindow;

    private final TextField subjectField;
    private final TextField messageField;
    private final JButton sendButton;
    private final JButton cancelButton;


    public ContactWindow(Controller controller, JFrame currentWindow) {
        super(currentWindow, "Contact Staff", true);
        this.controller = controller;
        this.currentWindow = currentWindow;

        // Setup window
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(currentWindow);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // Subject and message fields
        subjectField = new TextField();
        XPanel subjectPanel = new XPanel("Subject", subjectField);
        panel.add(subjectPanel);


        messageField = new TextField();
        XPanel messagePanel = new XPanel("Message", messageField);
        panel.add(messagePanel);

        // Buttons panel with Cancel and Send buttons
        ButtonsPanel buttons = new ButtonsPanel();
        cancelButton = new Button("Cancel", _ -> dispose());
        buttons.add(cancelButton);
        buttons.add(Box.createHorizontalGlue());
        sendButton = new Button("Send Email", this::sendMassEmail);
        buttons.add(sendButton);
        getRootPane().setDefaultButton(sendButton);
        SwingUtilities.invokeLater(sendButton::requestFocusInWindow);
        panel.add(buttons);

        // ESC key to close window
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "closeDialog");
        getRootPane().getActionMap().put("closeDialog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(panel);
        setVisible(true);
    }

  
  public ContactWindow(Controller controller, JFrame currentWindow, boolean isUser) {
        super(currentWindow, "Contact Staff", true);
        this.controller = controller;
        this.currentWindow = currentWindow;

        // Setup window
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(currentWindow);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // Subject and message fields
        subjectField = new TextField();
        XPanel subjectPanel = new XPanel("Subject", subjectField);
        panel.add(subjectPanel);

        messageField = new TextField();
        XPanel messagePanel = new XPanel("Message", messageField);
        panel.add(messagePanel);

        // Buttons panel with Cancel and Send buttons
        ButtonsPanel buttons = new ButtonsPanel();
        cancelButton = new Button("Cancel", _ -> dispose());
        buttons.add(cancelButton);
        buttons.add(Box.createHorizontalGlue());
        sendButton = new Button("Send Email", this::sendEmailToStaff);
        buttons.add(sendButton);
        getRootPane().setDefaultButton(sendButton);
        SwingUtilities.invokeLater(sendButton::requestFocusInWindow);
        panel.add(buttons);

        // ESC key to close window
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("ESCAPE"), "closeDialog");
        getRootPane().getActionMap().put("closeDialog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void sendMassEmail(ActionEvent e) {
        String subject = subjectField.getText();
        String message = messageField.getText();

        // Check if the mail is empty
        if (subject.isEmpty() || message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Subject and Message cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<AttendeeAccount> attendees = controller.getAllAttendees();
        boolean success = controller.sendMassEmail(attendees, subject, message);

        if (success) {
            JOptionPane.showMessageDialog(this, "Mass email sent successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to send the email. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendEmailToStaff(ActionEvent e) {
        String subject = subjectField.getText();
        String message = messageField.getText();

        if (subject.isEmpty() || message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Subject and Message cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //String contactStaffEmail = controller.getContactStaffEmail(); to be added
        String contactStaffEmail = "test@test.com";
        boolean success = controller.sendEmailToStaff(contactStaffEmail, subject, message);

        if (success) {
            JOptionPane.showMessageDialog(this, "Email sent to contact staff successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to send the email. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
