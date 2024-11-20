package gui.components;

import javax.swing.*;
import java.awt.*;

public class PasswordField extends JPasswordField {
    public PasswordField() {
        super();
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }
}
