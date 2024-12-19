import java.util.*;

public class Dijkstra {

    public Node startNode;
    public Node endNode;

    public ArrayList<Edge> path;

    public Dijkstra() {
        startNode = null;
        endNode = null;
        path = new ArrayList<>();
    }

    public ArrayList<Edge> getPath() {
        return path;
    }

    public void makePath(Graph graph) {
        path = new ArrayList<>();

        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(nd -> nd.distance));
        HashMap<Node, Integer> distances = new HashMap<>();
        HashMap<Node, Node> previous = new HashMap<>();
        for (Node node : graph.nodes) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(startNode, 0);
        priorityQueue.add(new NodeDistance(startNode, 0));

        while (!priorityQueue.isEmpty()) {
            NodeDistance current = priorityQueue.poll();
            Node currentNode = current.node;

            if (currentNode == endNode) {
                break;
            }

            if (graph.adjacentList.containsKey(currentNode)) {
                for (Node neighbor : graph.adjacentList.get(currentNode)) {
                    int newDistance = distances.get(currentNode) + calculateEdgeWeight(currentNode, neighbor);
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previous.put(neighbor, currentNode);
                        priorityQueue.add(new NodeDistance(neighbor, newDistance));
                    }
                }
            }
        }

        Node currentNode = endNode;
        while (previous.containsKey(currentNode)) {
            Node parent = previous.get(currentNode);
            path.add(findEdge(graph,parent, currentNode));
            currentNode = parent;
        }
        Collections.reverse(path);
    }

    private int calculateEdgeWeight(Node source, Node target) {
        int dx = source.x - target.x;
        int dy = source.y - target.y;
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    private Edge findEdge(Graph graph, Node source, Node target) {
        for (Edge edge : graph.edges) {
            if ((edge.startNode == source && edge.endNode == target) ||
                    (edge.startNode == target && edge.endNode == source)) {
                return edge;
            }
        }
        return null;
    }

    private static class NodeDistance {
        Node node;
        int distance;

        NodeDistance(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}
