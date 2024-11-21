package gui.components;

/**
 * <h3>The {@code UsernamePanel} Class</h3>
 * All panels related to entering a username should use this class.
 * @author FrankYang0610
 */
public class IDPanel extends XPanel {
    public IDPanel(TextField usernameField) {
        super();
        this.add(new RegularLabel("Account ID:"));
        this.add(usernameField);
    }
}
