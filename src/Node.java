import java.awt.*;

public class Node {
    public int id;
    public int x, y;

    public static int radius = 1;
    public static Color color = Color.BLUE;

    public Node(int id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public void setPosition(int newX, int newY) {
        x = newX;
        y = newY;
    }
}
