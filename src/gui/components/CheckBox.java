package gui.components;

import javax.swing.*;

public class CheckBox extends JCheckBox {
    public CheckBox(String text){
        super(text);
        setAlignmentX(CENTER_ALIGNMENT);
        this.addActionListener(actionListener);

    }
}
