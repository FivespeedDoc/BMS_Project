package gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * <h3>The {@code UsernamePanel} Class</h3>
 * All panels related to entering a username should use this class.
 * @author FrankYang0610
 */
public class TextAndFieldPanel extends JPanel {
    public TextAndFieldPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
    }

    public TextAndFieldPanel(String text, TextField textField) {
        this();
        RegularLabel IDLabel = new RegularLabel(text);
        IDLabel.setPreferredSize(new Dimension(100, 25));
        IDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(IDLabel);
        this.add(textField);
    }

    public TextAndFieldPanel(PasswordField passwordField) {
        this();
        RegularLabel IDLabel = new RegularLabel("Password");
        IDLabel.setPreferredSize(new Dimension(100, 25));
        IDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(IDLabel);
        this.add(passwordField);
    }
}
