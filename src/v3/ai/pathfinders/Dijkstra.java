package v3.ai.pathfinders;

import javafx.util.Pair;
import lombok.Getter;
import v3.ai.Graph;
import v3.ai.Node;
import v3.gui.guiPanel;

public interface Dijkstra {
    String id = "";
    String getId();

   // guiPanel guiPanel = null;

    //void setGui(guiPanel guiPanel);

    int checks = 0;

    int getChecks();

    Pair<Double, Node[]> dijkstra(Graph g, Node start, Node end);
}
