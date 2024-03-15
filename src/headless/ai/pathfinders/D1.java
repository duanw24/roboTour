package headless.ai.pathfinders;

import headless.ai.Graph;
import headless.ai.Node;
import headless.gui.guiFrame;
import javafx.util.Pair;
import lombok.Getter;

import java.awt.*;
import java.util.List;
import java.util.*;

@Getter
public class D1 implements Dijkstra {
    public String id = "D1";

    /*private static guiPanel guiPanel;
    public void setGui(guiPanel guiPanel) {
        System.out.println("Setting guiPanel");
        D1.guiPanel = guiPanel;
    }*/

    private static final guiFrame frame= guiFrame.getInstance();

    public int checks = 0;

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
                return new Pair<>(end.getDistance(), path.toArray(new Node[0]));
            }

            unvisited.remove(temp);
            for (Node n : g.getNeighbors(temp)) {
                checks++;
                if (unvisited.contains(n)) {
                    double alt = temp.getDistance() + Graph.distance(temp, n) ;
                    if (alt < n.getDistance()) {
                        n.setDistance(alt);
                        n.prev=temp;
                        queue.remove(new Pair<>(n, n.getDistance()));
                        queue.add(new Pair<>(n, n.getDistance()));

                        //nullcheck
                        if(n.prev!=null)
                            frame.updateCurrent(Node.prevPath(n, start));

                        frame.updateGraph(new Point(n.getX(), n.getY()), (int) Point.distance(end.getX(), end.getY(), n.getX(), n.getY()));
                    }
                }
            }
        }
        return null;
    }
}
