package v3;

import javafx.util.Pair;
import v3.ai.Dijkstra;
import v3.ai.Graph;
import v3.ai.Node;
import v3.gui.guiFrame;
import v3.ai.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] balls) {
        //3996ms avg 10runs 100res, walls->worstcase, path rendering
        //3839ms without path rendering
        ArrayList<Pair<Point,Direction>> walls = new ArrayList<>();
        walls.add(new Pair<>(new Point(0,0), Direction.EAST));
        walls.add(new Pair<>(new Point(0,1), Direction.EAST));
        walls.add(new Pair<>(new Point(0,2), Direction.EAST));
        walls.add(new Pair<>(new Point(1,3), Direction.EAST));
        walls.add(new Pair<>(new Point(1,2), Direction.EAST));
        walls.add(new Pair<>(new Point(1,1), Direction.EAST));
        walls.add(new Pair<>(new Point(2,0), Direction.EAST));
        walls.add(new Pair<>(new Point(2,1), Direction.EAST));
        walls.add(new Pair<>(new Point(2,2), Direction.EAST));

        run(new Point(0, 0), new Point(99, 99), walls, 100);
    }


    public static long run(Point startpos, Point endpos, ArrayList<Pair<Point,Direction>> walls, int resolution) {
        guiFrame frame = new guiFrame(resolution,resolution);

        Graph theGraph = new Graph(resolution,resolution);
        Node start = theGraph.getNode(startpos.x,startpos.y);
        Node end = theGraph.getNode(endpos.x,endpos.y);

        for(Pair<Point,Direction> p: walls) {
            theGraph.addWall(p.getKey(), p.getValue());
            frame.addWall(p.getKey(), p.getValue());
        }

        long t1 = System.currentTimeMillis();

        Pair<Double,Node[]> p = Dijkstra.dijkstra(theGraph, start, end);
        System.out.println("Path length: "+p.getKey());
        System.out.println("Path: "+ Arrays.toString(p.getValue()));
        System.out.println("Time: "+(System.currentTimeMillis()-t1)+"ms");
        System.out.println(Arrays.deepToString(theGraph.render()));

        frame.currentPath(null);
        for(Node n: p.getValue()) {
            frame.updateGraph(new Point(n.getX(),n.getY()), -2);
        }
        return System.currentTimeMillis()-t1;
    }

   /* public static void main(String[] balls) {
        //has to be multiple of 4
        int mx=8;
        int my=8;

        guiFrame frame = new guiFrame(mx,my);

        //res=100x100, 2cm=1u
        Graph theGraph = new Graph(mx,my);
        Node start = theGraph.getNode(0,0);
        Node end = theGraph.getNode(mx-1,my-1);

        ArrayList<Pair<Point,Direction>> walls = new ArrayList<>();
        walls.add(new Pair<>(new Point(0,0), Direction.EAST));
        walls.add(new Pair<>(new Point(0,1), Direction.EAST));
        walls.add(new Pair<>(new Point(0,2), Direction.EAST));
        walls.add(new Pair<>(new Point(1,3), Direction.EAST));
        walls.add(new Pair<>(new Point(1,2), Direction.EAST));
        walls.add(new Pair<>(new Point(1,1), Direction.EAST));
        walls.add(new Pair<>(new Point(2,0), Direction.EAST));
        walls.add(new Pair<>(new Point(2,1), Direction.EAST));
        walls.add(new Pair<>(new Point(2,2), Direction.EAST));

        for(Pair<Point,Direction> p: walls) {
            theGraph.addWall(p.getKey(), p.getValue());
            frame.addWall(p.getKey(), p.getValue());
        }

       // walls.add(new Pair<>(new Point(2,3), Direction.EAST);


        //walls.add(new Pair<>(new Point(1,1), Direction.SOUTH);)

        long t1 = System.currentTimeMillis();

        Pair<Double,Node[]> p = Dijkstra.dijkstra(theGraph, start, end);
        System.out.println("Path length: "+p.getKey());
        System.out.println("Path: "+ Arrays.toString(p.getValue()));
        System.out.println("Time: "+(System.currentTimeMillis()-t1)+"ms");
        System.out.println(Arrays.deepToString(theGraph.render()));

for(Node n: p.getValue()) {
            frame.updateGraph(new Point(n.getX(),n.getY()), -2);
        }
    }*/
}
