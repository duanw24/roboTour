package framework.gui;

import framework.map.Map;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Integer.parseInt;

public class controlPanel {
    private Map theMap;

    public controlPanel(Map theMap) {
        this.theMap = theMap;
        JFrame f = new JFrame("non-working control panel");
        JButton resetButton = new JButton("reset track");
        JButton line = new JButton("\uD83D\uDC80eval tracj\uD83D\uDC80");
        JTextField trackInput;
        trackInput=new JTextField("TRACK INPUT");
        trackInput.setBounds(0,100, 200,30);
        trackInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                trackInput.setText("");
            }
        });

        trackInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==10) {
                    theMap.readMap(trackInput.getText());
                }
            }
        });

        resetButton.setBounds(0,250,200,50);
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    }
        });

        line.setBounds(0,350,200,50);
        line.addMouseListener(new MouseAdapter() {
        });

        f.add(resetButton);
        f.add(line);
        f.add(trackInput);
        f.setSize(300,500);
        f.setLayout(null);
        f.setVisible(true);
    }


}