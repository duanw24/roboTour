package v3.gui;

import javafx.util.Pair;
import v3.ai.Dijkstra;
import v3.ai.Direction;
import v3.ai.Graph;
import v3.ai.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static v3.Main.run;

public class guiFrame extends JFrame {

    public int[][] mapData;
    private int mx,my;
    private int scaledx,scaledy;
    private int maxDist;
    public ArrayList<Pair<Point, Direction>> walls = new ArrayList<>();

    public guiFrame(int mx, int my) {
        Dijkstra.setGui(this);
        mapData=new int[mx][my];
        this.mx=mx;
        this.my=my;
        this.maxDist= (int)(mx*1.4);
        scaledx=800/mx;
        scaledy=800/my;
        for(int i=0;i<mx;i++) {
            for(int j=0;j<my;j++) {
                mapData[i][j]=-1;
            }
        }




        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 900);
        this.setVisible(true);
    }


    ArrayList<Point> current;
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        this.setBackground(new Color(17,23,41));
        //scales on dist function
        for(int i=0;i<mx;i++) {
            for(int j=0;j<my;j++) {
                if(mapData[i][j]==-1) {
                    g2d.setColor(Color.BLACK);
                } else if(mapData[i][j]==-2) {
                   g2d.setColor(Color.BLUE);
                } else {
                    if(mapData[i][j]>255)
                        mapData[i][j]=255;
                    g2d.setColor(gradientGen(mapData[i][j],0,maxDist));

                }
                g2d.fillRect(i*scaledx,j*scaledy,scaledx,scaledy);

            }
        }

        for(Pair<Point,Direction> p:walls) {
            g2d.setColor(Color.BLACK);
            Point point = p.getKey();
            switch(p.getValue()) {
                case NORTH -> {
                    g2d.fillRect(point.x*mx/4*scaledx,(point.y+1)*my/4*scaledy,mx/4*scaledx,scaledy/2);
                    return;
                }
                case EAST -> {
                    g2d.fillRect((point.x+1)*mx/4*scaledx,point.y*my/4*scaledy,scaledx/2,my/4*scaledy);
                }
                case SOUTH -> {
                    g2d.fillRect(point.x*mx/4*scaledx,point.y*my/4*scaledy,mx/4*scaledx,scaledy/2);
                }
                case WEST -> {
                    g2d.fillRect(point.x*mx/4*scaledx,point.y*my/4*scaledy,scaledx/2,my/4*scaledy);
                }
            }
        }
        if(current!=null) {
            for(Point p:current) {
                g2d.setColor(Color.GREEN);
                g2d.fillRect(p.x*scaledx,p.y*scaledy,scaledx,scaledy);
            }
        }
    }

    public void updateGraph(Point p, int i) {
        mapData[p.x][p.y]=i;
        repaint();
    }

    public void addWall(Point pt,Direction dir) {
        walls.add(new Pair<>(pt,dir));
    }

    public void currentPath(ArrayList<Node> current) {
        if(current==null) {
            this.current=null;
            return;
        }
       this.current=(ArrayList<Point>) current.stream().map(n -> new Point(n.getX(),n.getY())).collect(Collectors.toList());
    }

    private Color gradientGen(int value, int min, int max) {
        //normalize
        //log bad
        Color c1 = new Color(96, 239, 255);
        Color c2 = new Color(0, 97, 255);

        //linear gradient between c1 and c2 using maxDist as max
        int r = (int) ((c2.getRed() - c1.getRed()) * ((double) value / max) + c1.getRed());
        int g = (int) ((c2.getGreen() - c1.getGreen()) * ((double) value / max) + c1.getGreen());
        int b = (int) ((c2.getBlue() - c1.getBlue()) * ((double) value / max) + c1.getBlue());
        return new Color(r, g, b);
    }
}
