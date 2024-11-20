package gui.components;

import java.awt.*;

public class UsernamePanel extends XPanel {
    public UsernamePanel(TextField usernameField) {
        super();
        this.add(new RegularLabel("Username:"));
        this.add(usernameField);
    }
}
