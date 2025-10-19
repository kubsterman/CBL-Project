
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Worm {
    int wormLength;
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Point> walls = new ArrayList<>();

    public Worm() {
        wormPoints();
        loadWalls();
    }

    /**
     * loads the collision layer into walls, and adds the points of walls into an
     * arraylist.
     */
    public void loadWalls() {
        Renderer renderer = Renderer.getInstance();
        List<List<String>> collisionlayer = Renderer.getInstance().getCollisionLayer();

        for (int i = 0; i < collisionlayer.size(); i++) {
            for (int j = 0; j < collisionlayer.get(i).size(); j++) {
                if (collisionlayer.get(i).get(j).equals("wallS")
                        || collisionlayer.get(i).get(j).equals("wallN")
                        || collisionlayer.get(i).get(j).equals("wallE")
                        || collisionlayer.get(i).get(j).equals("wallW")) {
                    walls.add(new Point(j, i));
                }

            }
        }
    }

    /** Initializes the points of the worm. */
    public void wormPoints() {
        points.add(new Point(2, 4));
        points.add(new Point(2, 3));
        points.add(new Point(2, 2));
        points.add(new Point(3, 2));
        points.add(new Point(4, 2));
    }

    /** moves the worm and switches the coords over. */
    void move() {
        int i = points.size();
        for (int n = i - 1; n >= 1; n--) {
            points.set(n, new Point(points.get(n - 1).x, points.get(n - 1).y));
        }
    }

    /** checks if the worm collides while moving. */
    boolean checkForWormCollission(Point x) {
        for (int n = 1; n < points.size(); n++) {
            Point body = points.get(n);
            if (body.x == x.x && body.y == x.y) {
                return true;
            }
        }
        for (int n = 0; n < walls.size(); n++) {
            Point wall = walls.get(n);
            if (wall.x == x.x && wall.y == x.y) {
                return true;
            }
        }
        return false;
    }

    /** moves the worm up. */
    void moveUp() {
        Point head = points.get(0);
        Point newHead = new Point(head.x, head.y - 1);
        if (!checkForWormCollission(newHead)) {
            move();
            points.set(0, newHead);
        }
    }

    /** moves the worm to the left. */
    void moveLeft() {
        Point head = points.get(0);
        Point newHead = new Point(head.x - 1, head.y);
        if (!checkForWormCollission(newHead)) {
            move();
            points.set(0, newHead);
        }
    }

    /** moves the worm to the right. */
    void moveRight() {
        Point head = points.get(0);
        Point newHead = new Point(head.x + 1, head.y);
        if (!checkForWormCollission(newHead)) {
            move();
            points.set(0, newHead);
        }
    }

    /** moves the worm down. */
    void moveDown() {
        Point head = points.get(0);
        Point newHead = new Point(head.x, head.y + 1);
        if (!checkForWormCollission(newHead)) {
            move();
            points.set(0, newHead);
        }
    }

    // public static void main(String[] args) {
    // Worm s = new Worm();
    // s.Snakepoints();
    // s.WormToArray();
    // System.out.println(s.gridlayout.grid[4][52]); // should print "worm"
    // }
}
