package gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * <h3>The {@code Button} Class</h3>
 * All buttons with bold texts should use this class.
 * @author FrankYang0610
 */
public class BoldButton extends JButton {
    public BoldButton(String text, ActionListener actionListener) {
        super(text);
        setFont(new Font(this.getFont().getName(), Font.BOLD, this.getFont().getSize()));
        setAlignmentX(CENTER_ALIGNMENT);
        this.addActionListener(actionListener);
    }
}