import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class Graph {

    public ArrayList<Node> nodes;
    public ArrayList<Edge> edges;

    public HashMap<Node, ArrayList<Node>> adjacentList;

    public Node selectedNode;

    private int minLat = 99999999;
    private int minLong = 99999999;
    private int maxLat = 0;
    private int maxLong = 0;

    Graph(String filePath) {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        adjacentList = new HashMap<>();
        this.selectedNode = null;
        readGraphFromXML(filePath);
    }

    public void readGraphFromXML(String filePath) {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("node");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                int id = Integer.parseInt(element.getAttribute("id"));
                int latitude = Integer.parseInt(element.getAttribute("latitude"));
                int longitude = Integer.parseInt(element.getAttribute("longitude"));

                setMaxMinLatLong(longitude, latitude);

                addNode(id, longitude, latitude);
            }

            NodeList edgeList = document.getElementsByTagName("edge");
            for (int i = 0; i < edgeList.getLength(); i++) {
                Element element = (Element) edgeList.item(i);
                int source = Integer.parseInt(element.getAttribute("source"));
                int target = Integer.parseInt(element.getAttribute("target"));
                addEdge(source, target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        repositionNodes();
    }

    private void repositionNodes() {
        int canvasWidth = 1366;
        int canvasHeight = 768;

        int canvasWidthFactor = (maxLong - minLong) / canvasWidth;
        int canvasHeightFactor = (maxLat - minLat) / canvasHeight;

        for (Node node : nodes) {
            int x = (node.x - minLong) / canvasWidthFactor + 100;
            int y = (node.y - minLat) / canvasHeightFactor + 30;
            node.setPosition(x, y);
        }
    }


    private void setMaxMinLatLong(int longitude, int latitude) {
        if(latitude < minLat) {
            minLat = latitude;
        }
        if(latitude > maxLat) {
            maxLat = latitude;
        }
        if(longitude < minLong) {
            minLong = longitude;
        }
        if(longitude > maxLong) {
            maxLong = longitude;
        }
    }

    private void addNode(int id, int longitude, int latitude) {
        nodes.add(new Node(id, longitude, latitude));
    }

    private Node findNodeById(int id) {
        for (Node node : nodes) {
            if (node.id == id) {
                return node;
            }
        }
        return null;
    }

    private void addEdge(int source, int target) {
        Node sourceNode = findNodeById(source);
        Node targetNode = findNodeById(target);
        if (sourceNode != null && targetNode != null) {
            edges.add(new Edge(sourceNode, targetNode));
            adjacentList.computeIfAbsent(sourceNode, k -> new ArrayList<>()).add(targetNode);
            adjacentList.computeIfAbsent(targetNode, k -> new ArrayList<>()).add(sourceNode);
            System.out.println(source + " -> " + target);
            System.out.println(edges.size());
        }
    }


    public Node selectClosest(int x, int y) {
        Node closestNode = null;
        int minDistanceSquared = Integer.MAX_VALUE;
        for (Node node : nodes) {
            int dx = node.x - x;
            int dy = node.y - y;
            int distanceSquared = dx * dx + dy * dy;
            if (distanceSquared < minDistanceSquared) {
                minDistanceSquared = distanceSquared;
                closestNode = node;
            }
        }
        return closestNode;
    }
}
