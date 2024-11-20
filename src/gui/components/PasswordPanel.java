package gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * <h3>The {@code PasswordPanel} Class</h3>
 * All panels related to entering a password should use this class.
 * @author FrankYang0610
 */
public class PasswordPanel extends XPanel {
    public PasswordPanel(PasswordField passwordField) {
        super();
        add(new RegularLabel("Password:"));
        add(passwordField);
    }
}
