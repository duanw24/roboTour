package v1;

import v1.ai.Direction;
import v1.ai.pathfinders.D1;
import v1.ai.pathfinders.D2;
import v1.ai.pathfinders.D3;
import v1.ai.pathfinders.D4;
import v1.util.pathUtils;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] balls) {
        ArrayList<Pair<Point, Direction>> walls = new ArrayList<>();
        //#TODO rendering (3,y) NORTH doesn't work with multiple x=3 cases.... oddly specific case
        //#TODO TEST NEW CASES FOR GREEDY FIX D3

      /*  walls.add(new Pair<>(new Point(1,1), Direction.SOUTH));
        walls.add(new Pair<>(new Point(1,0), Direction.WEST));
        walls.add(new Pair<>(new Point(2,1), Direction.SOUTH));
        walls.add(new Pair<>(new Point(2,0), Direction.EAST));*/


        ArrayList<Point> zones = new ArrayList<>();
       /* zones.add(new Point((int) Math.floor(Math.random()*4),(int) Math.floor(Math.random()*4)));
        zones.add(new Point((int) Math.floor(Math.random()*4),(int) Math.floor(Math.random()*4)));
        zones.add(new Point((int) Math.floor(Math.random()*4),(int) Math.floor(Math.random()*4)));*/
      /*  zones.add(new Point(1,3));
        zones.add(new Point(3,3));
        zones.add(new Point(3,0));*/

        walls.add(new Pair<>(new Point(1,1), Direction.SOUTH));
        //walls.add(new Pair<>(new Point(1,0), Direction.EAST));
        walls.add(new Pair<>(new Point(1,1), Direction.EAST));
//        walls.add(new Pair<>(new Point(0,2), Direction.SOUTH));
        walls.add(new Pair<>(new Point(1,3), Direction.SOUTH));
//        walls.add(new Pair<>(new Point(1,2), Direction.EAST));

        walls.add(new Pair<>(new Point(2,1), Direction.EAST));
//        walls.add(new Pair<>(new Point(2,2), Direction.EAST));
        walls.add(new Pair<>(new Point(2,3), Direction.EAST));


   /* walls.add(new Pair<>(new Point(0,0), Direction.EAST));
        walls.add(new Pair<>(new Point(0,1), Direction.EAST));
        walls.add(new Pair<>(new Point(0,2), Direction.EAST));
        walls.add(new Pair<>(new Point(1,3), Direction.EAST));
        walls.add(new Pair<>(new Point(1,2), Direction.EAST));
        walls.add(new Pair<>(new Point(1,1), Direction.EAST));
        walls.add(new Pair<>(new Point(2,0), Direction.EAST));
        walls.add(new Pair<>(new Point(2,1), Direction.EAST));
        walls.add(new Pair<>(new Point(2,2), Direction.EAST));*/




       /* String wall = "1:SOUTH,1:EAST,2:EAST,4:SOUTH,6:SOUTH,9:SOUTH,9:EAST,10:NORTH,11:SOUTH";
        String[] wallArr = wall.split(",");
        Arrays.stream(wallArr).forEach(n->{
            String[] temp = n.split(":");
            System.out.println(Arrays.toString(temp));
            walls.add(new Pair<>(guiUtils.idToPos(temp[0]),guiUtils.balls(temp[1])));
        });*/

        //System.out.println(cliUtils.getBoard());

        Point startpos=new Point(0,0);
        Point endpos=new Point(99,99);
        Pathfinder pathfinder = new Pathfinder(new D3(),2);
        pathfinder.run(startpos, endpos, walls, zones,100, 100);
        //ArrayList <Pair<Double,Double>> pPath = pathUtils.polarCoords(pathfinder.getFinalPath());
        //String path = pathUtils.sketchPath(pPath);
        //System.out.println(pPath);
        //System.out.println(path);
//        cliUtils.runBot(path);
    }
       /* public static void main(String[] balls) {
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
    }*/


    public static long run(Point startpos, Point endpos, ArrayList<Pair<Point, Direction>> walls, int resolution) {
        //guiFrame mainframe = new guiFrame(resolution,resolution);
       /* guiPanel frame = new guiPanel(resolution,resolution);

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
        return System.currentTimeMillis()-t1;*/
        return 3;
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
