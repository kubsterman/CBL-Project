
import java.awt.Point;
import java.util.ArrayList;

public class Worm {
    int wormLength;
    ArrayList<Point> points = new ArrayList<>();
    Gridlayout gridlayout = new Gridlayout();

    public Worm(){
        Snakepoints();
        WormToArray();
    }

    // Initializes a grid for which we base the coordinates of the worm
    public class Gridlayout {
        String[][] grid = new String[100][100];
    }

    public void Snakepoints() {
        points.add(new Point(2, 4));
        points.add(new Point(2, 3));
        points.add(new Point(2, 2));
        points.add(new Point(3, 2));
        points.add(new Point(4, 2));
    }

    public void WormToArray() {
        for (Point p : points) {
            gridlayout.grid[p.x][p.y] = "worm";
        }
    }

    void moveUp() {
        int i = points.size();
        int s = i - 1;
        for (int n = i - 1; n >= 1; n--) {
            points.set(n, new Point(points.get(n - 1).x, points.get(n - 1).y));
        }
        Point head = points.get(0);
        points.set(0, new Point(head.x, head.y - 1));
        Point newHead = points.get(0);
        gridlayout.grid[newHead.x][newHead.y] = "worm";
        int tailx = points.get(s).x;
        int taily = points.get(s).y;
        gridlayout.grid[tailx][taily] = " ";

    }

    void moveLeft() {
        int i = points.size();
        int s = i - 1;
        for (int n = i - 1; n >= 1; n--) {
            points.set(n, new Point(points.get(n - 1).x, points.get(n - 1).y));
        }
        Point head = points.get(0);
        points.set(0, new Point(head.x - 1, head.y));
        Point newHead = points.get(0);
        gridlayout.grid[newHead.x][newHead.y] = "worm";
        int tailx = points.get(s).x;
        int taily = points.get(s).y;
        gridlayout.grid[tailx][taily] = " ";
    }

    void moveRight() {
        int i = points.size();
        int s = i - 1;
        for (int n = i - 1; n >= 1; n--) {
            points.set(n, new Point(points.get(n - 1).x, points.get(n - 1).y));
        }
        Point head = points.get(0);
        points.set(0, new Point(head.x + 1, head.y));
        Point newHead = points.get(0);
        gridlayout.grid[newHead.x][newHead.y] = "worm";
        int tailx = points.get(s).x;
        int taily = points.get(s).y;
        gridlayout.grid[tailx][taily] = " ";
    }

    void moveDown() {
        
        int i = points.size();
        int s = i - 1;
        for (int n = i - 1; n >= 1; n--) {
            points.set(n, new Point(points.get(n - 1).x, points.get(n - 1).y));
        }
        Point head = points.get(0);
        points.set(0, new Point(head.x, head.y + 1));
        Point newHead = points.get(0);
        gridlayout.grid[newHead.x][newHead.y] = "worm";
        int tailx = points.get(s).x;
        int taily = points.get(s).y;
        gridlayout.grid[tailx][taily] = " ";
    }

    

    // public static void main(String[] args) {
    //     Worm s = new Worm();
    //     s.Snakepoints();
    //     s.WormToArray();
    //     System.out.println(s.gridlayout.grid[4][52]); // should print "worm"
    // }
}
