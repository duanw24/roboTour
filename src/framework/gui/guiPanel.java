package framework.gui;

import framework.map.Map;
import framework.map.wallType;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

import javax.swing.*;

public class guiPanel extends JPanel {

    public static void main(String[] f) {
        JFrame frame = new JFrame("Track Editor Alpha 0.1");
        frame.getContentPane().add(new guiPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
        new controlPanel();
    }

    private int width = 800, height = 800;

    public guiPanel() {
        Map.getMap().reset();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                repaint();
            }
        });
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g2.drawString("Robot Tour Track Editor Alpha 0.1", 70, 50);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(10));
        g2.drawRect(90, 90, 600, 600);

        //vertical gridlines
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(240, 90, 240, 690);
        g2.drawLine(390, 90, 390, 690);
        g2.drawLine(540, 90, 540, 690);

        //horizontal gridlines
        g2.drawLine(90, 240, 690, 240);
        g2.drawLine(90, 390, 690, 390);
        g2.drawLine(90, 540, 690, 540);

//draws the tiles
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                g2.drawString(String.valueOf(counter), 120 + 150 * j + 50, 120 + 150 * i + 50);
                counter++;
            }
        }
    }

    private Color processTile(wallType w) {
        switch (w) {
            case EMPTY:
                return Color.WHITE;
            case GRIDLINE:
                return Color.GRAY;
            case WOOD:
                return Color.ORANGE;
            case BOUNDARY:
                return Color.RED;
                case SPECIAL:
                    return Color.GREEN;
            default:
                return Color.WHITE;
        }
    }
}