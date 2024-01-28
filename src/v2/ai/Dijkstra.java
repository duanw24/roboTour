package v2.ai;

import javafx.util.Pair;

import java.util.*;

public class Dijkstra {
    public static Pair<Double,Node[]> dijkstra(Graph g, Node start, Node end) {
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
                        queue.remove(new Pair<Node, Double>(n,n.getDistance()));
                        queue.add(new Pair<Node, Double>(n,n.getDistance()));
                    }
                }
            }
        }
        return null;
    }
}
