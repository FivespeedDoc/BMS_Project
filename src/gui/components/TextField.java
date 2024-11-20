package gui.components;

import javax.swing.*;
import java.awt.*;

public class TextField extends JTextField {
    public TextField() {
        super();
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }
}
