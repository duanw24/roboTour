package v1.ai;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import v1.util.Utils;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
public class Graph {
    public List<Pair<Point, Direction>> walls = new ArrayList<Pair<Point, Direction>>();
    private int mx,my;
    private int mtx,mty;

    //matrix of nodes, 1cm=1 unit
    private Node[][] nodes;

    private HashSet<Node> nodeSet;

    private int resolution = 2;

    @Getter@Setter
    private ArrayList<Pair<Point,Boolean>> zones = new ArrayList<>();

    static int hitboxInc = 3;
    ///////////////////////////
    static int zoneThreshold = 0;

    public void setZones(ArrayList<Point> zones) {
        for(Point p:zones) {
            this.zones.add(new Pair<>(p,false));
        }
    }

    public int zonesLeft() {
        return (int) zones.stream().filter(n->!n.getValue()).count();
    }

    public boolean checkZones(int x, int y) {
        if(zones==null)
            return false;
        for(int i=0;i<zones.size();i++) {
            Pair<Point,Boolean> zone = zones.get(i);
            if(zone.getValue()) {
                continue;
            }
            Point cache = Utils.cScale(zone.getKey(),mtx);
            if(
                    cache.x+mtx-zoneThreshold>x
                    &&cache.x+zoneThreshold<x
                    &&cache.y+mty-zoneThreshold>y
                    &&cache.y+zoneThreshold<y
            ) {
                System.out.println("zone "+i+" visited");
                zones.set(i,new Pair<>(zone.getKey(), true));
                return true;
            }
        }
        return false;
    }

    public ArrayList<Point> getZones() {
        ArrayList<Point> temp = new ArrayList<>();
        for(Pair<Point,Boolean> zone : zones) {
            if(zone.getValue()) {
                temp.add(zone.getKey());
            }
        }
        return temp;
    }

    public Graph(int mx, int my, int resolution) {
        this.resolution=resolution;
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
    ///////
    public ArrayList<Node> getNeighbors(Node n) {
        ArrayList<Node> neighbors = new ArrayList<>();
        for(Node node : nodeSet) {
            if(node==n||!nodeRaycheck(n.where(),node.where()))
                continue;
            ///////////////////////////
            if(distance(node, n)<resolution) {
                neighbors.add(node);
            }
        }
        return neighbors;
    }

    public static double distance(Node a, Node b) {
        return a.where().distance(b.where());
    }

    //UPDATE 3.0B INCREASED WALL HITBOXES
    public boolean nodeRaycheck(Point2D a, Point2D b) {
        //checks whether a line drawn between the two points will intersect any of the walls
        Line2D line = new Line2D.Double(a,b);
        for(Pair<Point, Direction> wall : walls) {
            //scale walls to pathfinder resolution
            Point p = new Point(wall.getKey().x*mtx,wall.getKey().y*mty);

            Direction d = wall.getValue();
            if(d==Direction.NORTH) {
                if(line.intersectsLine(p.x-hitboxInc,(p.y+mty-hitboxInc),(p.x+mtx+hitboxInc),(p.y+mty-hitboxInc))
                        ||line.intersectsLine(p.x-hitboxInc,(p.y+mty+hitboxInc),(p.x+mtx+hitboxInc),(p.y+mty+hitboxInc))
                        ||line.intersectsLine(p.x-hitboxInc,p.y+mty-hitboxInc,p.x-hitboxInc,p.y+mty+hitboxInc)
                        ||line.intersectsLine(p.x+mtx+hitboxInc,p.y+mty-hitboxInc,p.x+mtx+hitboxInc,p.y+mty+hitboxInc)) {//System.out.println("NORTH");
                    return false;}
            }
            if(d==Direction.SOUTH) {
                if(line.intersectsLine(p.x-hitboxInc,p.y-hitboxInc,(p.x+mtx+hitboxInc),p.y-hitboxInc)
                        ||line.intersectsLine(p.x-hitboxInc,p.y+hitboxInc,(p.x+mtx+hitboxInc),p.y+hitboxInc)
                        ||line.intersectsLine(p.x-hitboxInc,p.y-hitboxInc,p.x-hitboxInc,p.y+hitboxInc)
                        ||line.intersectsLine(p.x+mtx+hitboxInc,p.y-hitboxInc,p.x+mtx+hitboxInc,p.y+hitboxInc)
                ){//System.out.println("SOUTH");
                    return false;}
            }
            if(d==Direction.EAST) {
                if(line.intersectsLine((p.x+mtx)-hitboxInc,(p.y+mty+hitboxInc),(p.x+mtx)-hitboxInc,p.y-hitboxInc)
                        ||line.intersectsLine((p.x+mtx)+hitboxInc,(p.y+mty+hitboxInc),(p.x+mtx)+hitboxInc,p.y-hitboxInc)
                        ||line.intersectsLine(p.x+mtx-hitboxInc,p.y-hitboxInc,p.x+mtx+hitboxInc,p.y-hitboxInc)
                        ||line.intersectsLine(p.x+mtx-hitboxInc,p.y+mty+hitboxInc,p.x+mtx+hitboxInc,p.y+mty+hitboxInc)
                ){//System.out.println("EAST");
                    return false;}
            }
            if(d==Direction.WEST) {
                if(line.intersectsLine(p.x-hitboxInc,p.y-hitboxInc,p.x-hitboxInc,(p.y+mty+hitboxInc))
                        ||line.intersectsLine(p.x+hitboxInc,p.y-hitboxInc,p.x+hitboxInc,(p.y+mty+hitboxInc))
                        ||line.intersectsLine(p.x-hitboxInc,p.y-hitboxInc,p.x+hitboxInc,p.y-hitboxInc)
                        ||line.intersectsLine(p.x-hitboxInc,p.y+mty+hitboxInc,p.x+hitboxInc,p.y+mty+hitboxInc)
                ){//System.out.println("WEST");
                    return false;}
            }
        }
        return true;
    }


}