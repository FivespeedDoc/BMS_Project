package gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * <h3>The {@code TextField} Class</h3>
 * All text fields should use this class.
 * @author FrankYang0610
 */
public class TextField extends JTextField {
    public TextField() {
        super();
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }

    public TextField(String str) {
        super(str);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }
}
