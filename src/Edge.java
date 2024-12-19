import java.awt.Color;

public class Edge {

    public Node startNode, endNode;

    public int lineWidth = 1;
    public Color edgeColor = Color.BLACK;

    public int pathLineWidth = 3;
    public Color pathEdgeColor = Color.CYAN;

    public Edge(Node start, Node end) {
        this.startNode = start;
        this.endNode = end;
    }
}
