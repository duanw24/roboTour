package v3;

import javafx.util.Pair;
import lombok.Getter;
import v3.ai.Dijkstra;
import v3.ai.Direction;
import v3.ai.Graph;
import v3.ai.Node;
import v3.gui.controlPanel;
import v3.gui.guiFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Pathfinder {
    private float VERSION = 3.0f;
    private Point startpos;
    private Point endpos;

    private int mx;
    private int my;

    @Getter
    private ArrayList<Pair<Point,Direction>> walls = new ArrayList<>();

    public Pathfinder() {
        System.out.println("Pathfinder initialized");
    }

    public void run(Point startpos, Point endpos, ArrayList<Pair<Point,Direction>> walls, int mx, int my) {
        this.startpos=startpos;
        this.endpos=endpos;
        this.mx=mx;
        this.my=my;
        this.walls = walls;

        guiFrame mainframe = new guiFrame(this, mx,my);
        Graph theGraph = new Graph(mx,mx);
        for(Pair<Point,Direction> p : walls) {
            theGraph.addWall(p.getKey(), p.getValue());
            //mainframe.addWall(p.getKey(), p.getValue());
        }
        System.out.println(startpos);
        System.out.println(endpos);
        Node start = theGraph.getNode(startpos.x,startpos.y);
        Node end = theGraph.getNode(endpos.x,endpos.y);

        long t1 = System.currentTimeMillis();

        Pair<Double,Node[]> p = Dijkstra.dijkstra(theGraph, start, end);
        System.out.println("Path length: "+p.getKey());
        System.out.println("Path: "+ Arrays.toString(p.getValue()));
        long t2=System.currentTimeMillis()-t1;
        System.out.println("Time: "+t2+"ms");

        mainframe.updatePath(p.getValue());
        StringBuilder summary = new StringBuilder();
        summary.append("Pathfinder Version: v").append(this.VERSION)
                .append("\nPath length: ").append(p.getKey()/100+"m")
                .append("\nTime: ").append(System.currentTimeMillis()-t1).append("ms")
                .append("\nStart: ").append(startpos)
                .append("\nEnd: ").append(endpos)
                .append("\nResolution: ").append(mx).append("x").append(my);

        summary.append("\nWalls: \n");
        for(Pair<Point,Direction> pair: walls) {
            summary.append("("+pair.getKey().x+","+pair.getKey().y+":"+pair.getValue()+")\n");
        }

        summary.append("\nOperations: ").append(Dijkstra.checks);
        summary.append("\nOps/Sec: ").append(Dijkstra.checks/((double)t2/1000));
       /* for(Node n: p.getValue()) {
            summary.append("("+n.getX()+":"+n.getY()+")");
        }*/

        mainframe.stats.setText(summary.toString());

    }

    public void rerun() {
        System.out.println("rerun called");
        System.exit(73);
    }



  //im going to fucking end it if i have to make another gui  public static String summary(Pathfinder pathfinder, Point startpos,Point endpos, ArrayList<Pair<Point,Direction>> walls,) {}

}
