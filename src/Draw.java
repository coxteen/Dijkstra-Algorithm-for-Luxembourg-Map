import java.awt.*;
import java.util.ArrayList;

public class Draw {

    public static void drawStartEndNode(Graphics2D g2d, Node startNode, Node endNode) {
        g2d.setColor(Node.color);
        if (startNode != null) {
            g2d.fillOval(startNode.x - Node.radius / 2, startNode.y - Node.radius / 2, Node.radius, Node.radius);
        }
        if (endNode != null) {
            g2d.fillOval(endNode.x - Node.radius / 2, endNode.y - Node.radius / 2, Node.radius, Node.radius);
        }
    }

    public static void drawEdge(Graphics2D g2d, Edge edge, Color color, int lineWidth) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(lineWidth));
        g2d.drawLine(edge.startNode.x, edge.startNode.y, edge.endNode.x, edge.endNode.y);
    }

    public static void draw(Graphics2D g2d, Graph graph, Node startNode, Node endNode, ArrayList<Edge> path) {
        for (Edge edge : graph.edges) {
            drawEdge(g2d, edge, edge.edgeColor, edge.lineWidth);
        }
        for (Edge edge : path) {
            drawEdge(g2d, edge, edge.pathEdgeColor, edge.pathLineWidth);
        }
        drawStartEndNode(g2d, startNode, endNode);
        for (Node node : graph.nodes) {
            g2d.setColor(Node.color);
            g2d.fillOval(node.x - Node.radius / 2, node.y - Node.radius / 2, Node.radius, Node.radius);
        }
    }

}
