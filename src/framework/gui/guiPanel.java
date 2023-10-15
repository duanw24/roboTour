package framework.gui;

import framework.map.Map;
import framework.map.Tile;
import framework.map.Wall;
import framework.map.wallType;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

import javax.swing.*;

public class guiPanel extends JPanel {
    private final Map theMap;

    /*public static void main(String[] f) {
        JFrame frame = new JFrame("Track Editor Alpha 0.1");
        frame.getContentPane().add(new guiPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
        new controlPanel();
    }*/

    private int width = 800, height = 800;

    public guiPanel(Map theMap) {
        this.theMap = theMap;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse pressed at: " + e.getX() + ", " + e.getY());
                if(e.getX()>=3);
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
        g2.setColor(Color.GRAY);
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

        g2.setStroke(new BasicStroke(10));
        Wall N,E,S,W;
        Tile tempTile;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tempTile = theMap.getTileMap()[i][j];
                N = tempTile.getN();
                E = tempTile.getE();
                S = tempTile.getS();
                W = tempTile.getW();

                g2.setStroke(new BasicStroke(10));

                g2.setColor(processWall(N.getType()));
                g2.drawLine(N.getxLit(),N.getyLit(),N.getxLit()+150,N.getyLit());

                g2.setColor(processWall(E.getType()));
                g2.drawLine(E.getxLit(),E.getyLit(),E.getxLit(),E.getyLit()+150);

                g2.setColor(processWall(S.getType()));
                g2.drawLine(S.getxLit(),S.getyLit(),S.getxLit()+150,S.getyLit());


                g2.setColor(processWall(W.getType()));
                g2.drawLine(W.getxLit(),W.getyLit(),W.getxLit(),W.getyLit()+150);

            }
        }

        //Draws robot
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(5));
    }

    private Color processWall(wallType w) {
        return switch (w) {
            case EMPTY -> Color.WHITE;
            case GRIDLINE -> Color.GRAY;
            case WOOD -> Color.ORANGE;
            case BOUNDARY -> Color.RED;
            case GATE -> Color.GREEN;
            case OBSTACLE -> Color.BLACK;
            default -> Color.MAGENTA;
        };
    }
}