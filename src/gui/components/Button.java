package gui.components;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * <h3>The {@code Button} Class</h3>
 * All buttons should use this class.
 * @author FrankYang0610
 */
public class Button extends JButton {
    public Button(String text, ActionListener actionListener) {
        super(text);
        setAlignmentX(CENTER_ALIGNMENT);
        this.addActionListener(actionListener);
    }
}
