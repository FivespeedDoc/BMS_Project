package gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * <h3>The {@code XLabel} Class</h3>
 * All containers for arranging components along the X-axis should use this class.
 * @author FrankYang0610
 */
public class XPanel extends JPanel {
    public XPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
    }
}
