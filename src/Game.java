import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class Game extends JFrame implements Runnable{
    public static void main(String[] args) {
        new Game().run();
    }
    
    public void run() {
        JFrame frame = new JFrame("Game");
        frame.setSize(1280, 720);

        AudioManager audioManager = AudioManager.getInstance("assets/audio/audio.properties");
        audioManager.playMusic("menu");


        List<Point> playerList = Arrays.asList(new Point(5,5), new Point(5,4), new Point(5,3), new Point(4,3), new Point(3,3));
        
        Renderer panel = new Renderer("assets/maps/level1.csv", playerList);

        frame.add(panel);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
       
    }
}