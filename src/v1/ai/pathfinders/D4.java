package v1.ai.pathfinders;

import v1.ai.Graph;
import v1.ai.Node;
import v1.gui.guiFrame;
import v1.util.pathUtils;
import javafx.util.Pair;
import lombok.Getter;

import java.awt.*;
import java.util.List;
import java.util.*;

@Getter
public class D4 implements Dijkstra {
    public String id = "D4";
/*
    static guiPanel guiPanel;
    public void setGui(guiPanel guiPanel) {
        D3.guiPanel = guiPanel;
    }*/

    public int checks = 0;

    private Pair<Double, Node[]> bestPath = null;
    private static final guiFrame frame= guiFrame.getInstance();


    public Pair<Double, Node[]> dijkstra(Graph g, Node start, Node end) {
        //HashSet <Node> visited = new HashSet<Node>();
        HashSet <Node> unvisited = new HashSet<>();
        PriorityQueue<Pair<Node, Double>> queue = new PriorityQueue<>(Comparator.comparingDouble(Pair::getValue));
        for(Node n: g.getNodeSet()) {
            if(n!=start)
                n.setDistance(Double.POSITIVE_INFINITY);
            queue.add(new Pair<Node, Double>(n,n.getDistance()));
            n.prev=null;
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
                        current = current.prev;
                    } catch (Exception e) {
                        System.out.println("No path found");
                        return null;
                    }
                }
                path.add(start);
                Collections.reverse(path);
              /*  if(bestPath == null || bestPath.getKey()>end.getDistance()) {
                    System.out.println("New best path found: "+end.getDistance());
                    bestPath = new Pair<Double, Node[]>(end.getDistance(), path.toArray(new Node[0]));
                    Arrays.stream(bestPath.getValue()).forEach(n->frame.updateGraph(new Point(n.getX(), n.getY()),-2));
                }*/
                return new Pair<>(end.getDistance(), path.toArray(new Node[0]));
            }

            unvisited.remove(temp);
            for (Node n : g.getNeighbors(temp)) {
                checks++;
                if (unvisited.contains(n)) {
                    double alt = Graph.distance(temp, n);
                    if (alt < n.getDistance()) {
                        n.setDistance(alt);
                        n.prev=temp;
                        //why the fuck does pure greedy actually kinda work
                        queue.remove(new Pair<>(n, mDist(n, end)));
                        queue.add(new Pair<>(n, mDist(n, end)));

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

    //shitty heuristic
    public static double mDist(Node node, Node end) {
        return Graph.distance(node, end);
    }

    public static double wallCost() {
        return 0;
    }

}
