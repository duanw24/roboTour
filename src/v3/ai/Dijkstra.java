package v3.ai;

import javafx.util.Pair;
import v3.gui.guiFrame;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Dijkstra {

    private static guiFrame guiFrame;
    public static void setGui(guiFrame guiFrame) {
        Dijkstra.guiFrame = guiFrame;
    }

    private int maxDist;

    public static Pair<Double, Node[]> dijkstra(Graph g, Node start, Node end) {
        //HashSet <Node> visited = new HashSet<Node>();
        HashSet <Node> unvisited = new HashSet<>();
        PriorityQueue<Pair<Node, Double>> queue = new PriorityQueue<>(Comparator.comparingDouble(Pair::getValue));

        for(Node n: g.nodeSet) {
            if(n!=start)
                n.setDistance(Double.POSITIVE_INFINITY);
            queue.add(new Pair<Node, Double>(n,n.getDistance()));
            n.prev.clear();
            unvisited.add(n);
        }

        while(!queue.isEmpty()) {
            Node temp = queue.remove().getKey();
            if (temp == end) {
                System.out.println("Found " + temp);
                List<Node> path = new ArrayList<>();
                Node current = end;
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
                return new Pair<Double, Node[]>(end.getDistance(), path.toArray(new Node[0]));
            }

            unvisited.remove(temp);
            for (Node n : g.getNeighbors(temp)) {
                if (unvisited.contains(n)) {
                    double alt = temp.getDistance() + g.distance(temp, n);
                    if (alt < n.getDistance()) {
                        n.setDistance(alt);
                        n.prev.add(temp);
                        queue.remove(new Pair<>(n, n.getDistance()));
                        queue.add(new Pair<>(n, n.getDistance()));

                        if(guiFrame!=null) {
                            //System.out.println("Updating graph @ "+n.getX()+","+n.getY()+" to "+(int)(alt));
                            //n.prev.forEach(p -> guiFrame.updateGraph(new Point(p.getX(), p.getY()), -2));

                            //get path taken to current node
                            guiFrame.currentPath(Objects.requireNonNull(Node.prevPath(n, start)));
                            guiFrame.updateGraph(new Point(n.getX(), n.getY()), (int) Point.distance(end.getX(), end.getY(), n.getX(), n.getY()));

                        } else {
                            System.out.println("guiFrame not found");
                        }
                    }
                }
            }
        }
        return null;
    }
}
