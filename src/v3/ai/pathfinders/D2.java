package v3.ai.pathfinders;

import javafx.util.Pair;
import lombok.Getter;
import v3.ai.Graph;
import v3.ai.Node;
import v3.gui.guiFrame;

import java.awt.*;
import java.util.*;
import java.util.List;

@Getter
public class D2 implements Dijkstra {
    private final float hWeight = 0.9f;

    public String id = "D2";

   /* static guiPanel guiPanel;
    public void setGui(guiPanel guiPanel) {
        D2.guiPanel = guiPanel;
    }
*/

    private static final guiFrame frame= v3.gui.guiFrame.getInstance();

    @Getter
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
                return new Pair<Double, Node[]>(end.getDistance(), path.toArray(new Node[0]));
            }

            unvisited.remove(temp);
            for (Node n : g.getNeighbors(temp)) {
                checks++;
                if (unvisited.contains(n)) {
                    double alt = temp.getDistance() + Graph.distance(temp, n);
                    if (alt < n.getDistance()) {
                        n.setDistance(alt);
                        n.prev=temp;
                        queue.remove(new Pair<>(n, n.getDistance()+hWeight*mDist(n, end)));
                        queue.add(new Pair<>(n, n.getDistance()+hWeight*mDist(n, end)));

                            //System.out.println("Updating graph @ "+n.getX()+","+n.getY()+" to "+(int)(alt));
                            //n.prev.forEach(p -> guiFrame.updateGraph(new Point(p.getX(), p.getY()), -2));


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

    //shitty heuristic
    public static double mDist(Node node, Node end) {
        return Graph.distance(node, end);
    }
}
