package headless;

import headless.ai.Direction;
import headless.ai.Graph;
import headless.ai.Node;
import headless.ai.pathfinders.Dijkstra;
import headless.gui.guiFrame;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Pathfinder {
    private float VERSION = 3.0f;
    private Point startpos;
    private Point endpos;

    private int mx;
    private int my;
    private int range;

    @Getter
    private Dijkstra d;

    @Getter
    private ArrayList<Pair<Point, Direction>> walls = new ArrayList<>();
    private guiFrame mainframe = guiFrame.getInstance();

    @Getter
    private Node[] finalPath;

    @Getter@Setter
    private boolean done=false;



    public Pathfinder(Dijkstra d, int range) {
        this.range=range;
        this.d = d;
        System.out.println("Pathfinder initialized");
    }

    public void run(Point startpos, Point endpos, ArrayList<Pair<Point, Direction>> walls, int mx, int my) {
        this.startpos=startpos;
        this.endpos=endpos;
        this.mx=mx;
        this.my=my;
        this.walls = walls;

        mainframe.init(this, mx,my);
        mainframe.updateGraph(startpos, -3);
        //////////////////////
        Graph theGraph = new Graph(mx,mx,range);
        for(Pair<Point, Direction> p : walls) {
            theGraph.addWall(p.getKey(), p.getValue());
            //mainframe.addWall(p.getKey(), p.getValue());
        }
        System.out.println(startpos);
        System.out.println(endpos);
        Node start = theGraph.getNode(startpos.x,startpos.y);
        Node end = theGraph.getNode(endpos.x,endpos.y);

        long t1 = System.currentTimeMillis();

        Pair<Double, Node[]> p = d.dijkstra(theGraph, start, end);
        System.out.println("Path length: "+p.getKey());
        System.out.println("Path: "+ Arrays.toString(p.getValue()));
        long t2=System.currentTimeMillis()-t1;
        System.out.println("Time: "+t2+"ms");

        this.finalPath = p.getValue();
        this.setDone(true);
        Arrays.stream(finalPath).forEach(n -> mainframe.updateGraph(new Point(n.getX(),n.getY()), -2));
        mainframe.updateCurrent(null);
        mainframe.updateGraph(startpos, -3);

        StringBuilder summary = new StringBuilder();
        summary.append("Pathfinder Version: v").append(this.VERSION)
                .append("\nAlgorithm: ").append(d.id)
                .append("\nPath length: ").append(p.getKey() / 100).append("m")
                .append("\nTime: ").append(System.currentTimeMillis()-t1).append("ms")
                .append("\nStart: ").append(startpos)
                .append("\nEnd: ").append(endpos)
                .append("\nResolution: ").append(mx).append("x").append(my);

        summary.append("\nWalls: \n");
        for(Pair<Point, Direction> pair: walls) {
            summary.append("("+pair.getKey().x+","+pair.getKey().y+":"+pair.getValue()+")\n");
        }

        summary.append("\nOperations: ").append(d.getChecks());
        summary.append("\nOps/Sec: ").append(d.getChecks()/((double)t2/1000));
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
