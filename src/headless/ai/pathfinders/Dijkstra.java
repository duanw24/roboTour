package headless.ai.pathfinders;

import headless.ai.Graph;
import headless.ai.Node;
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
