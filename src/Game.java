import javax.swing.*;

/**Starts the game. */
public class Game extends JFrame implements Runnable {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Game().run();
        });
    }
    
    public void run() {
        // set up the main frame
        setTitle("Worm Game");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // initialize audio manager
        AudioManager audioManager = AudioManager.getInstance("assets/audio/audio.properties");
        audioManager.playMusic("menu");
        
        // create and show main menu
        MainMenu mainMenu = new MainMenu(this);
        add(mainMenu);
        
        setVisible(true);
    }
}