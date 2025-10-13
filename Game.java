import javax.swing.*;

public class Game extends JFrame implements Runnable{
    public static void main(String[] args) {
        new Game().run();
    }
    
    public void run() {
        JFrame frame = new JFrame("Game");
        frame.setSize(1280, 720);


        TileMapRenderer panel = new TileMapRenderer("Resources/TileMaps/TileMaps.csv");
        frame.add(panel);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
       
    }
}