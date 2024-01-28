package v2;

import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

class Main {
    public static void main(String[] args) {
        Graph theGraph = new Graph(100,100);
        Node start = theGraph.getNode(0,0);
        Node end = theGraph.getNode(99,99);

        //#TEST for CASE:nopath
        theGraph.addWall(0,0,Direction.EAST);
        theGraph.addWall(0,1,Direction.EAST);
        theGraph.addWall(0,2,Direction.EAST);

        //#TODO
        /* whadafuck 199 throws err, prolly +49 render in render()
        * Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 199 out of bounds for length 199
	at Graph.render(Graph.java:130)
	at Main.main(Main.java:26)

Process finished with exit code 1*/
        //theGraph.addWall(1,3,Direction.EAST); THROWS ERROR FIX
        //theGraph.addWall(0,3,Direction.EAST);

        long t1 = System.currentTimeMillis();
        Pair<Double,Node[]> p = Dijkstra.dijkstra(theGraph, start, end);
        System.out.println("Path length: "+p.getKey());
        System.out.println("Path: "+ Arrays.toString(p.getValue()));
        System.out.println("Time: "+(System.currentTimeMillis()-t1)+"ms");
        theGraph.render(p.getValue());
    }
}
