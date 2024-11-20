package gui.components;

import javax.swing.*;
import java.awt.*;

public class PasswordPanel extends XPanel {
    public PasswordPanel(PasswordField passwordField) {
        super();
        add(new RegularLabel("Password:"));
        add(passwordField);
    }
}
