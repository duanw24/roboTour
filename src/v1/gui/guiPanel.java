package v1.gui;

import v1.Pathfinder;
import v1.ai.Direction;
import v1.ai.Node;
import v1.util.guiUtils;
import javafx.util.Pair;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

@Getter

public class guiPanel extends JPanel {
    private int mx,my;
    private int scaledx,scaledy;
    private int maxDist;

    private int[][] mapData;
    private Pathfinder pathfinder;

    //cache
    ArrayList<Point> zones;
    public guiPanel(int mx, int my, Pathfinder pathfinder, guiFrame guiFrame) {
        mapData=new int[mx][my];
        this.pathfinder = pathfinder;
        //pathfinder.getD().setGui(this);

        this.mx=mx;
        this.my=my;
        this.maxDist= (int)(mx*1.4);
        scaledx=800/mx;
        scaledy=800/my;

        this.zones=pathfinder.getZones();

        for(int i=0;i<mx;i++) {
            for(int j=0;j<my;j++) {
                mapData[i][j]=-1;
            }
        }

        this.setSize(800, 800);
    }


    private ArrayList<Point> current=new ArrayList<>();
    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Insets insets = getInsets();
        // int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;

        AffineTransform oldAT = g2d.getTransform();
        try {
            //Move the origin to bottom-left, flip y axis
            g2d.scale(1.0, -1.0);
            g2d.translate(0, -h - insets.top);

            this.setBackground(new Color(17,23,41));
            g2d.setStroke(new BasicStroke(5));

            g2d.setColor(Color.BLACK);
            //scales on dist function
            for(int i=0;i<mx;i++) {
                for(int j=0;j<my;j++) {
                    if(mapData[i][j]==-1) {
                        g2d.setColor(Color.BLACK);
                    } else if(mapData[i][j]==-2) {
                        g2d.setColor(Color.BLUE);
                    } else if(mapData[i][j]==-3) {
                        g2d.setColor(Color.RED);
                    }else {
                        if(mapData[i][j]>255)
                            mapData[i][j]=255;
                        g2d.setColor(guiUtils.gradientGen(mapData[i][j],maxDist));
                    }
                    g2d.fillRect(i*scaledx,j*scaledy,scaledx,scaledy);
                }
            }


            if(!current.isEmpty()) {
                for(int i=0;i<current.size()-1;i++) {
                    g2d.setColor(Color.GREEN);
                    g2d.setStroke(new BasicStroke(7));
                    g2d.drawLine(current.get(i).x*scaledx,current.get(i).y*scaledy,current.get(i+1).x*scaledx,current.get(i+1).y*scaledy);
                }
            }
            for(int i=1;i<5;i++) {
                for(int j=1;j<5;j++) {
                    g2d.setColor(Color.GRAY);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawLine(i*mx/4*scaledx,0,i*mx/4*scaledx,800);
                    g2d.drawLine(0,j*my/4*scaledy,800,j*my/4*scaledy);
                }
            }
            if(pathfinder.getWalls()!=null) {
                for (Pair<Point, Direction> p : pathfinder.getWalls()) {
                    g2d.setColor(Color.MAGENTA);
                    g2d.setStroke(new BasicStroke(7));
                    Point point = p.getKey();
                    switch (p.getValue()) {
                        case NORTH -> {
                            g2d.fillRect(point.x * mx / 4 * scaledx, (point.y + 1) * my / 4 * scaledy, mx / 4 * scaledx, scaledy / 2);
                            return;
                        }
                        case EAST -> {
                            g2d.fillRect((point.x + 1) * mx / 4 * scaledx, point.y * my / 4 * scaledy, scaledx / 2, my / 4 * scaledy);
                        }
                        case SOUTH -> {
                            g2d.fillRect(point.x * mx / 4 * scaledx, point.y * my / 4 * scaledy, mx / 4 * scaledx, scaledy / 2);
                        }
                        case WEST -> {
                            g2d.fillRect(point.x * mx / 4 * scaledx, point.y * my / 4 * scaledy, scaledx / 2, my / 4 * scaledy);
                        }
                    }
                }
            }

            for(Point p : zones) {
                g2d.setColor(Color.ORANGE);
                g2d.setStroke(new BasicStroke(6));
                g2d.drawRect(p.x*mx/4*scaledx+5,p.y*my/4*scaledy+5,mx/4*scaledx-10,my/4*scaledy-10);
            }


            if(pathfinder.isDone()) {
                System.out.println("Pathfinder is done, rendering finalpath...");
                Node[] temp = pathfinder.getFinalPath();
                for(int i=0;i<temp.length-1;i++) {
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(7));
                    g2d.drawLine(temp[i].getX()*scaledx,temp[i].getY()*scaledy,temp[i+1].getX()*scaledx,temp[i+1].getY()*scaledy);
                }
            }

        } finally {
            //restore
            g2d.setTransform(oldAT);
        }
    }

    protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            Insets insets = getInsets();
            // int w = getWidth() - insets.left - insets.right;
            int h = getHeight() - insets.top - insets.bottom;

            AffineTransform oldAT = g2.getTransform();
            try {
                //Move the origin to bottom-left, flip y axis
                g2.scale(1.0, -1.0);
                g2.translate(0, -h - insets.top);


            }
            finally {
                //restore
                g2.setTransform(oldAT);
            }
        }
    }
