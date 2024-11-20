package gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * <h3>The {@code PasswordField} Class</h3>
 * All password fields should use this class.
 * @author FrankYang0610
 */
public class PasswordField extends JPasswordField {
    public PasswordField() {
        super();
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }
}
