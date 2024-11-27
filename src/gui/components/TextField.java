package gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * <h3>The {@code TextField} Class</h3>
 * All text fields should use this class.
 * @author FrankYang0610
 */
public class TextField extends JTextField {
    private final String placeholder;

    public TextField(String placeholder) {
        this.placeholder = placeholder;
        setPreferredSize(new Dimension(200, 30));
    }

    public TextField() {
        this("");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty()) {
            g.setColor(Color.GRAY);
            FontMetrics fm = g.getFontMetrics();
            int x = 5;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(placeholder, x, y);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 30);
    }
}
