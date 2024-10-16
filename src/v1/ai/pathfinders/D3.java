package v1.ai.pathfinders;

import v1.ai.Graph;
import v1.ai.Node;
import v1.gui.guiFrame;
import javafx.util.Pair;
import lombok.Getter;
import v1.util.pathUtils;

import java.awt.*;
import java.util.List;
import java.util.*;

@Getter
public class D3 implements Dijkstra {
    public String id = "D3";

    /*private static guiPanel guiPanel;
    public void setGui(guiPanel guiPanel) {
        System.out.println("Setting guiPanel");
        D1.guiPanel = guiPanel;
    }*/

    private static Pair<Double,Node[]> bestPath = null;

    private static final guiFrame frame= guiFrame.getInstance();

    public int checks = 0;

    public Pair<Double, Node[]> dijkstra(Graph g, Node start, Node end) {
        //HashSet <Node> visited = new HashSet<Node>();
        HashSet <Node> unvisited = new HashSet<>();
        PriorityQueue<Pair<Node, Double>> queue = new PriorityQueue<>(Comparator.comparingDouble(Pair::getValue));

        for(Node n: g.getNodeSet()) {
            if(n!=start)
                n.setDistance(Double.POSITIVE_INFINITY);
            queue.add(new Pair<>(n, n.getDistance()));
            n.prev=null;
            unvisited.add(n);
        }

        while(!unvisited.isEmpty()) {
            System.out.println("Queue: "+queue.size());
            Node temp = queue.remove().getKey();

            unvisited.remove(temp);
            for (Node n : g.getNeighbors(temp)) {
                checks++;

                if (n == end) {
                    System.out.println("Found " + n);
                    List<Node> path = new ArrayList<>();
                    Node current = temp;
                    while (current != start) {
                        path.add(current);
                        try {
                            current = current.prev;
                        } catch (Exception e) {
                            System.out.println("No path found");
                            return null;
                        }
                    }
                    path.add(start);
                    Collections.reverse(path);
                    if(bestPath == null || bestPath.getKey()>end.getDistance()) {
                        System.out.println("New best path found: "+end.getDistance());
                        bestPath = new Pair<Double, Node[]>(end.getDistance(), path.toArray(new Node[0]));
                        Arrays.stream(bestPath.getValue()).forEach(node->frame.updateGraph(new Point(node.getX(), node.getY()),-2));
                    }
                    bestPath= new Pair<>(pathUtils.pDist(path.toArray(new Node[0]))*2, path.toArray(new Node[0]));
                }

                if (unvisited.contains(n)) {
                    double alt = (temp.getDistance() + Graph.distance(temp, n));

                    if (alt < n.getDistance()) {
                        n.setDistance(alt);
                        n.prev=temp;

                        queue.removeIf(pair -> pair.getKey() == n);
                        queue.add(new Pair<>(n, n.getDistance()));


                        //nullcheck
                        if(n.prev!=null)
                            frame.updateCurrent(Node.prevPath(n, start));

                        frame.updateGraph(new Point(n.getX(), n.getY()), (int) Point.distance(end.getX(), end.getY(), n.getX(), n.getY()));
                    }
                }
            }
        }
        return bestPath;
    }
}
