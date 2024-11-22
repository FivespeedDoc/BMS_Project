package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveRegisrationWindow extends JFrame {

    public RemoveRegisrationWindow() {
        setTitle("Remove Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));


        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JLabel emailLabel = new JLabel("E-mail Address:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmLabel = new JLabel("<html>Are you sure you want to delete your account?<br>This action cannot be undone!</html>");

        JButton confirmButton = new JButton("Confirm Delete");
        JButton cancelButton = new JButton("Cancel");

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(RemoveRegisrationWindow.this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 模拟调用后端 API 执行删除操作
                boolean success = removeAccount(email, password);
                if (success) {
                    JOptionPane.showMessageDialog(RemoveRegisrationWindow.this, "Account deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0); // 退出程序
                } else {
                    JOptionPane.showMessageDialog(RemoveRegisrationWindow.this, "Failed to delete account. Please check your credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Account deletion cancelled.", "Info", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // 关闭当前窗口
        });

        // 添加组件到面板
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmLabel);
        panel.add(confirmButton);
        panel.add(cancelButton);

        add(panel);
    }

    private boolean removeAccount(String email, String password) {

        return email.equals("test@example.com") && password.equals("password123");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RemoveRegisrationWindow::new);
    }
}
