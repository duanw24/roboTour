package framework.gui;

import framework.map.*;
import framework.map.tileType;
import framework.mapUtils;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

import javax.swing.*;

public class guiPanel extends JPanel {

 /*   public static void main(String[] guiTests) {
        System.out.println(processXYt(new Point(90,90)));
    }*/

    /*
    * Tile 0: 90,90
    * Tile 1: 240,90
    * Tile 2: 390,90
    * Tile 3: 540,90
    * Tile 4: 90,240
    * Tile 5: 240,240
    * Tile 6: 390,240
    * Tile 7: 540,240
    * Tile 8: 90,390
    * Tile 9: 240,390
    * Tile 10: 390,390
    * Tile 11: 540,390
    * Tile 12: 90,540
    * Tile 13: 240,540
    * Tile 14: 390,540
    * Tile 15: 540,540
    */

    private final Map theMap;

    public static void main(String[] f) {
        JFrame frame = new JFrame("Track Editor Alpha 0.1");
        frame.getContentPane().add(new guiPanel(null));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
       // new controlPanel();
    }

    private int width = 800, height = 800;

    public guiPanel(Map theMap) {
        this.theMap = theMap;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int tileID = processXYt(e.getPoint());
                if(tileID==-1) {
                    System.out.println("out of bounds dumbass");
                    return;
                }
                System.out.println("changing "+tileID+":"+mapUtils.getTile(tileID).getTileType());
                mapUtils.nextTile(tileID);
                System.out.println("changed to "+mapUtils.getTile(tileID).getTileType());
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

    private Point PixelProcess(Point point) {
        return new Point(95+150*(point.x),95+150*(point.y));
    }

    private static int processXYt(Point point) {
        /*
         * Tile 0: 95,95
         * Tile 1: 245,95
         * Tile 2: 395,95
         * Tile 3: 545,95
         * Tile 4: 95,245
         * Tile 5: 245,245
         * Tile 6: 395,245
         * Tile 7: 545,245
         * Tile 8: 95,395
         * Tile 9: 245,395
         * Tile 10: 395,395
         * Tile 11: 545,395
         * Tile 12: 95,545
         * Tile 13: 245,545
         * Tile 14: 395,545
         * Tile 15: 545,545
         */
        if(point.x<=95||point.x>=690||point.y<=95||point.y>=690) {
            return -1;
        }
        int dx = Math.round((point.x-95)/150);
        int dy = Math.round((point.y-95)/150);
        return dx+dy*4;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g2.drawString("Robot Tour Track Editor Alpha 0.1", 70, 50);
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(10));
        g2.drawRect(90, 90, 600, 600);

        /*//vertical gridlines
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(240, 90, 240, 690);
        g2.drawLine(390, 90, 390, 690);
        g2.drawLine(540, 90, 540, 690);

        //horizontal gridlines
        g2.drawLine(90, 240, 690, 240);
        g2.drawLine(90, 390, 690, 390);
        g2.drawLine(90, 540, 690, 540);
       */

        //draw borders of tiles
        ArrayList<Tile> deferredTiles = new ArrayList<Tile>();
        g2.setStroke(new BasicStroke(10));
        for (int i=0;i<16;i++) {
                Tile temp = mapUtils.getTile(i);

                //draws all special tiles first, defers empty tiles to not cover outlines
                if(temp.getTileType()!=tileType.EMPTY) {
                    deferredTiles.add(temp);
                    continue;
                }
                Point tempP = mapUtils.idToPoint(temp.getTileID());
                drawTile(temp,g2);

            }

        //draw deferred tiles
        deferredTiles.forEach(t->{
            drawTile(t,g2);
        });

        g2.setColor(Color.BLACK);

        //draws the tile counter
        int counter = 0;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++,counter++) {
                g2.drawString(String.valueOf(counter), 120 + 150 * x + 50, 120 + 150 * y + 50);
            }
        }

        //Draws robot
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(5));
    }

    private Color processWall(wallType w) {
        return switch (w) {
            case EMPTY -> Color.GRAY;
            case GRIDLINE -> Color.BLACK;
            case WOOD -> Color.ORANGE;
            case BOUNDARY -> Color.RED;
            case GATE -> Color.GREEN;
            case OBSTACLE -> new Color(150,75,0);
            default -> Color.MAGENTA;
        };
    }

    public void drawTile(Tile tile, Graphics2D g2) {
        Wall N = tile.getN();
        Wall E = tile.getE();
        Wall S = tile.getS();
        Wall W = tile.getW();

        g2.setStroke(new BasicStroke(10));

        int x1 = tile.getX()*150+90;
        int y1 = tile.getY()*150+90;

        g2.setColor(processWall(N.getType()));
        g2.drawLine(x1,y1,x1+150,y1);

        g2.setColor(processWall(E.getType()));
        g2.drawLine(x1,y1,x1,y1+150);

        g2.setColor(processWall(S.getType()));
        g2.drawLine(x1+150,y1+150,x1+150,y1);

        g2.setColor(processWall(W.getType()));
        g2.drawLine(x1+150,y1+150,x1,y1+150);

        switch(tile.getTileType()) {
            case EMPTY -> g2.setColor(new Color(211,211,211));
            case START -> g2.setColor(Color.GREEN);
            case TARGET -> g2.setColor(new Color(0, 162, 255));
            case GATE -> g2.setColor(new Color(255, 0, 221));
        }
        g2.fillRect(95+150*tile.getX(),95+150*tile.getY(),140,140);

    }
}