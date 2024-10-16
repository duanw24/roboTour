package v1.ai.pathfinders;

import v1.ai.Graph;
import v1.ai.Node;
import javafx.util.Pair;

public interface Dijkstra {
    String id = "";
    String getId();

   // guiPanel guiPanel = null;

    //void setGui(guiPanel guiPanel);

    int checks = 0;

    int getChecks();

    Pair<Double, Node[]> dijkstra(Graph g, Node start, Node end);
}
