package v3.gui;

import javafx.util.Pair;
import v3.Pathfinder;
import v3.ai.Dijkstra;
import v3.ai.Direction;
import v3.ai.Node;
import v3.util.guiUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class guiPanel extends JPanel {
    private int mx,my;
    private int scaledx,scaledy;
    private int maxDist;
    static int[][] mapData;
    private Pathfinder pathfinder;
    public guiPanel(int mx, int my, Pathfinder pathfinder, guiFrame guiFrame) {
        mapData=new int[mx][my];
        this.pathfinder = pathfinder;
        Dijkstra.setGui(this);

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

        this.setSize(800, 900);
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
                    g2d.setColor(guiUtils.gradientGen(mapData[i][j],maxDist));

                }
                g2d.fillRect(i*scaledx,j*scaledy,scaledx,scaledy);

            }
        }

        for(Pair<Point,Direction> p:pathfinder.getWalls()) {
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
                g2d.fillRect(p.x*scaledx,p.y*scaledy,scaledx*2,scaledy*2);
            }
        }
    }

    public void currentPath(ArrayList<Node> current) {
        if(current==null) {
            this.current=null;
            return;
        }
        this.current=(ArrayList<Point>) current.stream().map(n -> new Point(n.getX(),n.getY())).collect(Collectors.toList());
    }

    public void updateGraph(Point p, int i) {
        mapData[p.x][p.y]=i;
        repaint();
    }

}
