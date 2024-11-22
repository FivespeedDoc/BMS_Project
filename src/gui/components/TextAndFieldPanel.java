package gui.components;

import java.awt.*;

/**
 * <h3>The {@code UsernamePanel} Class</h3>
 * All panels related to entering a username should use this class.
 * @author FrankYang0610
 */
public class TextAndFieldPanel extends XPanel {
    public TextAndFieldPanel(String text, TextField textField) {
        super();
        RegularLabel IDLabel = new RegularLabel(text);
        IDLabel.setPreferredSize(new Dimension(100, 25));
        IDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(IDLabel);
        this.add(textField);
    }

    public TextAndFieldPanel(PasswordField passwordField) {
        super();
        RegularLabel IDLabel = new RegularLabel("Password");
        IDLabel.setPreferredSize(new Dimension(100, 25));
        IDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(IDLabel);
        this.add(passwordField);
    }
}
