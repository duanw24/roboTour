package framework.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Integer.parseInt;

public class controlPanel {
    public static int redInput = 0;
    public static int blueInput = 0;
    public static int greenInput = 0;
    public static boolean stoggled = false;

    public controlPanel() {
        JFrame f = new JFrame("non-working control panel");
        JButton b = new JButton("reset track");
        JButton line = new JButton("\uD83D\uDC80eval tracj\uD83D\uDC80");
        JTextField red,blue,green;
        red=new JTextField("Replace with RED[0,255]");
        red.setBounds(0,100, 200,30);

        blue=new JTextField("Replace with BLUE[0,255]");
        blue.setBounds(0,150, 200,30);

        green=new JTextField("Replace with GREEN[0,255]");
        green.setBounds(0,200, 200,30);

        b.setBounds(0,250,200,50);
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                redInput = parseInt(red.getText().toString());
                blueInput = parseInt(blue.getText().toString());
                greenInput = parseInt(green.getText().toString());
                /*
                if(redInput >= 255) {red.setText("Please input a value between 0 and 255");}
                if(blueInput >= 255) {blue.setText("Please input a value between 0 and 255");}
                if(greenInput >= 255) {green.setText("Please input a value between 0 and 255");}
                */
                System.out.println("R: " + redInput + ", G: " + blueInput + ", B: " + greenInput);
            }
        });

        line.setBounds(0,350,200,50);
        line.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //🤓wtf is a ternary operator🤓
                if (stoggled) {
                    stoggled = false;
                    line.setText(line.getText()+"h");
                } else {
                    stoggled = true;
                    line.setText(line.getText()+"h");
                }
            }
        });

        f.add(b);
        f.add(line);
        f.setSize(200,500);
        f.setLayout(null);
        f.setVisible(true);
    }


}