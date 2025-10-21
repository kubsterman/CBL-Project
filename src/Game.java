import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

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

        Renderer panel = Renderer.getInstance("assets/maps/level1.csv", new ArrayList<>());
        Worm worm = new Worm();
        panel.setPlayerList(worm.points); // update player list after worm is created

        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    worm.moveUp();
                    panel.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    worm.moveLeft();
                    panel.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    worm.moveDown();
                    panel.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    worm.moveRight();
                    panel.repaint();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        frame.setVisible(true);
    }
}