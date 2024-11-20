package gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * <h3>The {@code TitleLabel} Class</h3>
 * All titles should use this class.
 * @author FrankYang0610
 */
public class TitleLabel extends JLabel {
    public TitleLabel(String title) {
        super(title, SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 16));
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
