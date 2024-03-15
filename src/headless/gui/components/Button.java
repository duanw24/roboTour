package headless.gui.components;

import headless.util.guiUtils;

import javax.swing.*;

public class Button extends JButton {
    public Button(String text) {
        super(text);
        setBackground(new java.awt.Color(17, 23, 41));
        setForeground(new java.awt.Color(205, 205, 205));
        setFont(guiUtils.customFont(20f));
        setBorder(BorderFactory.createLineBorder(new java.awt.Color(17, 23, 41), 2));
    }

}
