package v3.ai;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node {
    private double distance;

    private int x,y,id;
    private Point2D.Double center;
    public ArrayList<Node> prev = new ArrayList<Node>();
    public Node(int x, int y, int id) {
        this.center = new Point2D.Double(x + 0.5, y + 0.5);
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getId() {
        return id;
    }
    public Point2D.Double where() {
        return center;
    }

    public static ArrayList<Node> prevPath(Node n, Node start) {
        ArrayList<Node> path = new ArrayList<>();
        Node current = n;
        while (current != start) {
            path.add(current);
            try {
                current = current.prev.get(0);
            } catch (Exception e) {
                System.out.println("No path found");
                return null;
            }
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    @Override
    public String toString() {
        return "NODE("+id+","+x+":"+y+")";
    }


}