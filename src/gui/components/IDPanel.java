package gui.components;

import java.awt.*;

/**
 * <h3>The {@code UsernamePanel} Class</h3>
 * All panels related to entering a username should use this class.
 * @author FrankYang0610
 */
public class IDPanel extends XPanel {
    public IDPanel(TextField IDField) {
        super();
        RegularLabel IDLabel = new RegularLabel("Account ID:");
        IDLabel.setPreferredSize(new Dimension(100, 25));
        IDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(IDLabel);
        this.add(IDField);
    }
}
