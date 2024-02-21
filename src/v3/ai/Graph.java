package v3.ai;

import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Graph {
    public List<Pair<Point, Direction>> walls = new ArrayList<Pair<Point, Direction>>();
    private int mx,my;
    private int mtx,mty;

    //matrix of nodes, 1cm=1 unit
    private Node[][] nodes;

    HashSet<Node> nodeSet;
    public Graph(int mx, int my) {
        this.mx=mx;
        this.my=my;
        this.mtx=mx/4;
        this.mty=my/4;
        nodes = new Node[mx][my];
        nodeSet = new HashSet();
        for(int dx=0;dx<mx; dx++) {
            for(int dy=0; dy<my; dy++) {
                Node temp = new Node(dx, dy,dx*mx+dy);
                nodeSet.add(temp);
                nodes[dy][dx] = temp;
            }
        }
    }

    public void addWall(Point p, Direction d) {
        walls.add(new Pair<Point, Direction>(p,d));
    }

    public Node getNode(int x, int y) {
        return nodes[y][x];
    }


    //the more "range" used, the more precise the pathfinding will be, but is slower
    //test 100x100:
    //range<2: 2652ms,
    //range<3: 2826ms,
    //range infinite: 5076ms.
    public ArrayList<Node> getNeighbors(Node n) {
        ArrayList<Node> neighbors = new ArrayList<>();
        for(Node node : nodeSet) {
            if(node==n||!nodeRaycheck(n.where(),node.where()))
                continue;
            if(distance(node, n)<2) {
                neighbors.add(node);
            }
        }
        return neighbors;
    }

    public double distance(Node a, Node b) {
        return Point.distance(a.getX(), a.getY(), b.getX(), b.getY());
    }
    public boolean nodeRaycheck(Point2D a, Point2D b) {
        //checks whether a line drawn between the two points will intersect any of the walls
        Line2D line = new Line2D.Double(a,b);
        for(Pair<Point, Direction> wall : walls) {
            //scale walls to pathfinder resolution
            Point p = new Point(wall.getKey().x*mtx,wall.getKey().y*mty);

            Direction d = wall.getValue();
            if(d==Direction.NORTH) {
                if(line.intersectsLine(p.x,(p.y+mty),(p.x+mtx),(p.y+mty))) {//System.out.println("NORTH");
                    return false;}
            }
            if(d==Direction.SOUTH) {
                if(line.intersectsLine(p.x,p.y,(p.x+mtx),p.y)){//System.out.println("SOUTH");
                    return false;}
            }
            if(d==Direction.EAST) {
                if(line.intersectsLine((p.x+mtx),(p.y+mty),(p.x+mtx),p.y)){//System.out.println("EAST");
                    return false;}
            }
            if(d==Direction.WEST) {
                if(line.intersectsLine(p.x,p.y,p.x,(p.y+mty))){//System.out.println("WEST");
                    return false;}
            }
        }
        return true;
    }


}