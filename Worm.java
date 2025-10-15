import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Worm {
    int wormLength;
    ArrayList<Point> points = new ArrayList<>();
    Gridlayout gridlayout = new Gridlayout();

    // Initializes a grid for which we base the coordinates of the worm
    public class Gridlayout {
        String[][] grid = new String[100][100];
    }

    // creates a point based on input
    class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void Snakepoints() {
        points.add(new Point(2, 54));
        points.add(new Point(2, 53));
        points.add(new Point(2, 52));
        points.add(new Point(3, 52));
        points.add(new Point(4, 52));
    }

    public void WormToArray() {
        for (Point p : points) {
            gridlayout.grid[p.x][p.y] = "worm";
        }
    }

    void moveUp() {
        int i = points.size();
        int s = i - 1;
        for (int n = i - 2; n > 1; n--) {
            points.set(n, points.get((n - 1)));
        }
        int r = points.get(0).y;
        points.get(0).y = r + 1;
        gridlayout.grid[s.x][s.y] = " ";
    }

    void moveLeft() {

    }

    void moveRight() {

    }

    void moveDown() {

    }

    public class MyKeyListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'w') {
                moveUp();
            } else if (e.getKeyChar() == 'a') {
                moveLeft();
            } else if (e.getKeyChar() == 'd') {
                moveRight();
            } else if (e.getKeyChar() == 's') {
                moveDown();
            }
        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }

    }

    public static void main(String[] args) {
        Worm s = new Worm();
        s.Snakepoints();
        s.WormToArray();
        System.out.println(s.gridlayout.grid[2][54]); // should print "worm"
    }
}
