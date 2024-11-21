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
        RegularLabel passwordLabel = new RegularLabel("Password:");
        passwordLabel.setPreferredSize(new Dimension(100, 25));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(passwordLabel);
        this.add(passwordField);
    }
}
