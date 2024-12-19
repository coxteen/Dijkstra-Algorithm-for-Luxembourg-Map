import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JPanel implements MouseListener {

    public final Graph graph = new Graph("Luxembourg.xml");

    Dijkstra dijkstra = new Dijkstra();

    public Window() {
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Draw.draw(g2d, graph, dijkstra.startNode, dijkstra.endNode, dijkstra.getPath());
    }

    private void left_click_action(MouseEvent e) {
        Node node = graph.selectClosest(e.getX(), e.getY());
        if (graph.selectedNode == null) {
            graph.selectedNode = node;
            dijkstra.startNode = node;
            dijkstra.endNode = null;
        } else {
            dijkstra.endNode = node;
            dijkstra.makePath(graph);
            graph.selectedNode = null;
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            left_click_action(e);
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
