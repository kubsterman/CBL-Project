import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private JFrame parentFrame;
    private MainMenu mainMenu;
    private Worm worm;
    private Renderer renderer;
    private Timer gameTimer;
    private boolean gameOver = false;

    public GamePanel(JFrame frame, String levelPath, MainMenu menu) {
        this.parentFrame = frame;
        this.mainMenu = menu;

        setLayout(new BorderLayout());
        setBackground(new Color(43, 34, 42));

        // initialize game objects
        MapData mapData = new MapData(levelPath);
        worm = new Worm(mapData);
        renderer = new Renderer(mapData, worm.points);

        // create info panel at top
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(43, 34, 42));

        add(infoPanel, BorderLayout.NORTH);
        add(renderer, BorderLayout.CENTER);

        // set up key listener
        setFocusable(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver)
                    return;

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    returnToMenu();
                    return;
                }

                boolean moved = false;
                if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                    worm.moveUp();
                    moved = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    worm.moveLeft();
                    moved = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    worm.moveDown();
                    moved = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    worm.moveRight();
                    moved = true;
                }

                if (moved) {
                    renderer.repaint();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void returnToMenu() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        mainMenu.showMenu();
    }
}