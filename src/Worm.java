import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Worm {
    private int wormLength = 15;
    private MapData mapData;
    private GameManager gameManager;
    public ArrayList<Point> points = new ArrayList<>();
    public ArrayList<Point> walls = new ArrayList<>();
    private AudioManager audioManager = AudioManager.getInstance();

    public Worm(MapData mapData, int LevelIndex) {
        gameManager = GameManager.getInstance();
        loadLevel(LevelIndex);
        this.mapData = mapData;
        loadWalls();
    }

    public void loadLevel(int i) {
        if (i == 0) {
            wormLength = 5;
            wormPoints(new Point(1, 5));
        }
        if (i == 1) {
            wormLength = 3;
            wormPoints(new Point(1, 1));
        }
        if (i == 2) {
            wormLength = 50;
            wormPoints(new Point(2, 3));
        }
    }

    /**
     * loads the collision layer into walls, and adds the points of walls into an
     * arraylist.
     */
    public void loadWalls() {
        List<List<String>> collisionLayer = mapData.getCollisionLayer();
        List<List<String>> interactableLayer = mapData.getInteractableLayer();

        for (int i = 0; i < collisionLayer.size(); i++) {
            for (int j = 0; j < collisionLayer.get(i).size(); j++) {
                String tile = collisionLayer.get(i).get(j);
                if (tile.startsWith("wall")) {
                    walls.add(new Point(j, i));
                }
            }
        }
        for (int i = 0; i < interactableLayer.size(); i++) {
            for (int j = 0; j < interactableLayer.get(i).size(); j++) {
                String tile = interactableLayer.get(i).get(j);
                if (tile.startsWith("gate")) {
                    walls.add(new Point(j, i));
                }
            }
        }
    }

    /** Initializes the points of the worm. */
    public void wormPoints(Point x) {
        points.add(new Point(x.x, x.y));
    }

    /** moves the worm and switches the coords over. */
    void move() {
        int i = points.size();
        if (points.size() < wormLength) {
            Point tail = points.get(points.size() - 1);
            points.add(new Point(tail.x, tail.y));
        }

        for (int n = i - 1; n >= 1; n--) {
            points.set(n, new Point(points.get(n - 1).x, points.get(n - 1).y));
        }

    }

    /** checks if the worm collides while moving. */
    boolean checkForCollission(Point x) {
        for (int n = 1; n < points.size(); n++) {
            Point body = points.get(n);
            if (body.x == x.x && body.y == x.y) {
                audioManager.playSFX("hit");
                return true;
            }
        }
        for (int n = 0; n < walls.size(); n++) {
            Point wall = walls.get(n);
            if (wall.x == x.x && wall.y == x.y) {
                audioManager.playSFX("hit");
                return true;
            }
        }
        return false;
    }

    /** moves the worm up. */
    void moveUp() {
        Point head = points.get(0);
        Point newHead = new Point(head.x, head.y - 1);
        if (!checkForCollission(newHead)) {
            move();
            points.set(0, newHead);
        }
        gameManager.onPlayerMove(points.get(0), this);
    }

    /** moves the worm to the left. */
    void moveLeft() {
        Point head = points.get(0);
        Point newHead = new Point(head.x - 1, head.y);
        if (!checkForCollission(newHead)) {
            move();
            points.set(0, newHead);
        }
        gameManager.onPlayerMove(points.get(0), this);
    }

    /** moves the worm to the right. */
    void moveRight() {
        Point head = points.get(0);
        Point newHead = new Point(head.x + 1, head.y);
        if (!checkForCollission(newHead)) {
            move();
            points.set(0, newHead);
        }
        gameManager.onPlayerMove(points.get(0), this);
    }

    /** moves the worm down. */
    void moveDown() {
        Point head = points.get(0);
        Point newHead = new Point(head.x, head.y + 1);
        if (!checkForCollission(newHead)) {
            move();
            points.set(0, newHead);
        }
        gameManager.onPlayerMove(points.get(0), this);
    }

}