package gui.components;

import java.awt.*;

/**
 * <h3>The {@code UsernamePanel} Class</h3>
 * All panels related to entering a username should use this class.
 * @author FrankYang0610
 */
public class UsernamePanel extends XPanel {
    public UsernamePanel(TextField usernameField) {
        super();
        this.add(new RegularLabel("Username:"));
        this.add(usernameField);
    }
}
