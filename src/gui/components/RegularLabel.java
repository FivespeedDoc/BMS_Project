package gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * <h3>The {@code RegularLabel} Class</h3>
 * All regular labels should use this class.
 * @author FrankYang0610
 */
public class RegularLabel extends JLabel {
    public RegularLabel(String title) {
        super(title, SwingConstants.CENTER);
        setFont(new Font("Arial", Font.PLAIN, 12));
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
