package gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * <h3>The {@code UsernamePanel} Class</h3>
 * All panels related to JPanel with {@code BoxLayout.X_AXIS} should use this class. This is a <b>factory class</b>, and there are various constructors. This class is designed for convenience.
 * @author FrankYang0610
 */
public class XPanel extends JPanel {
    public XPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
    }

    public XPanel(String text, TextField textField) {
        this();
        RegularLabel textLabel = new RegularLabel(text);
        textLabel.setPreferredSize(new Dimension(100, 25));
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(textLabel);
        this.add(textField);
    }

    /**
     * Different kinds of password fields
     * <p>
     * <b>Different choices of {@code type}:</b>
     * <ul>
     *     <li>{@code REG} - regular password</li>
     *     <li>{@code RE} - re-password</li>
     *     <li>{@code OLD} - old password</li>
     * </ul>
     */
    public XPanel(PasswordField passwordField, String type) {
        this();
        RegularLabel passwordLabel = switch (type) {
            case "REG" -> new RegularLabel("Password");
            case "OLD" -> new RegularLabel("Old Password");
            case "NEW" -> new RegularLabel("New Password");
            case "RE" -> new RegularLabel("Re-Password");
            default -> null;
        };

        assert passwordLabel != null;
        passwordLabel.setPreferredSize(new Dimension(100, 25));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(passwordLabel);
        this.add(passwordField);
    }

    public XPanel(String text, ButtonGroup buttonGroup, JRadioButton... buttons) {
        this();
        RegularLabel textLabel = new RegularLabel(text);
        textLabel.setPreferredSize(new Dimension(100, 25));
        textLabel.setMinimumSize(new Dimension(100, 25));
        textLabel.setMaximumSize(new Dimension(100, 25));
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(textLabel);

        for (JRadioButton button : buttons) {
            buttonGroup.add(button);
            this.add(button);
        }
    }
}
