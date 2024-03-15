package headless.gui;

import headless.Pathfinder;
import headless.ai.Direction;
import headless.ai.Node;
import headless.util.guiUtils;
import javafx.util.Pair;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

@Getter

public class guiPanel extends JPanel {
    private int mx,my;
    private int scaledx,scaledy;
    private int maxDist;

    private int[][] mapData;
    private Pathfinder pathfinder;
    public guiPanel(int mx, int my, Pathfinder pathfinder, guiFrame guiFrame) {
        mapData=new int[mx][my];
        this.pathfinder = pathfinder;
        //pathfinder.getD().setGui(this);

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

        this.setSize(800, 800);
    }


    private ArrayList<Point> current=new ArrayList<>();
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
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

        for(Pair<Point, Direction> p:pathfinder.getWalls()) {
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
        if(!current.isEmpty()) {
            for(int i=0;i<current.size()-1;i++) {
                g2d.setColor(Color.GREEN);
                g2d.setStroke(new BasicStroke(7));
                g2d.drawLine(current.get(i).x*scaledx,current.get(i).y*scaledy,current.get(i+1).x*scaledx,current.get(i+1).y*scaledy);
            }
        }
        if(pathfinder.isDone()) {
            System.out.println("Pathfinder is done");
            Node[] temp = pathfinder.getFinalPath();
            System.out.println(Arrays.toString(temp));
            System.out.println(Arrays.toString(temp));
            System.out.println(Arrays.toString(temp));
            System.out.println(Arrays.toString(temp));
            for(int i=0;i<temp.length-1;i++) {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(7));
                g2d.drawLine(temp[i].getX()*scaledx,temp[i].getY()*scaledy,temp[i+1].getX()*scaledx,temp[i+1].getY()*scaledy);
            }
        }
    }
}
