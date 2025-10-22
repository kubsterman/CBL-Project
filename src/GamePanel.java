import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class GamePanel extends JPanel {
    private JFrame parentFrame;
    private MainMenu mainMenu;
    private Worm worm;
    private Renderer renderer;
    private Timer gameTimer;
    private boolean gameOver = false;
    private String levelPath;
    private Timer restartTimer;
    private boolean canRestart = true;
    private static final int RESTART_COOLDOWN = 250; 
    private String[] levels = {
        "assets/maps/level1.csv",
        "assets/maps/level2.csv",
        "assets/maps/level3.csv"
    };

    public GamePanel(JFrame frame, String levelPath, MainMenu menu) {
        this.parentFrame = frame;
        this.mainMenu = menu;
        this.levelPath = levelPath;
        initiateGame();
        initiateKeyListener();
    }

    private void initiateGame(){
        setLayout(new BorderLayout());
        setBackground(new Color(43, 34, 42));
        
        // initialize game objects
        MapData mapData = new MapData(levelPath);
        worm = new Worm(mapData);
        renderer = new Renderer(mapData, worm.points);
        
        GameManager.getInstance().setLevelCompleteListener(() -> {
            SwingUtilities.invokeLater(() -> showWinScreen());
        });

        // create info panel at top
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(43, 34, 42));
        add(infoPanel, BorderLayout.NORTH);
        add(renderer, BorderLayout.CENTER);
       
    }

    private void initiateKeyListener(){
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
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    if (canRestart) {
                        canRestart = false;
                        restart();
                        
                        // Start cooldown timer
                        if (restartTimer != null && restartTimer.isRunning()) {
                            restartTimer.stop();
                        }
                        restartTimer = new Timer(RESTART_COOLDOWN, evt -> {
                            canRestart = true;
                            restartTimer.stop();
                        });
                        restartTimer.setRepeats(false);
                        restartTimer.start();
                    }
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

    public void showWinScreen() {
        gameOver = true;
        
        // Remove all existing components
        removeAll();
        setLayout(new BorderLayout());
        
        // Create overlay panel
        JPanel winPanel = new JPanel();
        winPanel.setLayout(new BoxLayout(winPanel, BoxLayout.Y_AXIS));
        winPanel.setBackground(new Color(43, 34, 42));
        winPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        
        // Title
        JLabel titleLabel = new JLabel("LEVEL COMPLETE!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        winPanel.add(titleLabel);
        winPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        // Get current level index
        int currentLevelIndex = -1;
        for (int i = 0; i < levels.length; i++) {
            if (levels[i].equals(levelPath)) {
                currentLevelIndex = i;
                break;
            }
        }
        
        final int nextLevelIndex = currentLevelIndex + 1;
        
        if (nextLevelIndex < levels.length) {
            JButton nextLevelButton = UIManager.createImageButton("level" + nextLevelIndex + "_button", 10);
            nextLevelButton.addActionListener(e -> loadLevel(levels[nextLevelIndex]));
            winPanel.add(nextLevelButton);
            winPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        // Menu button
        JButton menuButton = UIManager.createImageButton("menu_button", 10);
        menuButton.addActionListener(e -> returnToMenu());
        winPanel.add(menuButton);
        winPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Quit button
        JButton quitButton = UIManager.createImageButton("quit_button", 10);
        quitButton.addActionListener(e -> System.exit(0));
        winPanel.add(quitButton);
        
        // Add win panel
        add(winPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void loadLevel(String newLevelPath) {
        AudioManager audioManager = AudioManager.getInstance();
        audioManager.playMusic("game");
        
        this.levelPath = newLevelPath;
        this.gameOver = false;
        this.canRestart = true;
        
        parentFrame.getContentPane().removeAll();
        GamePanel newGamePanel = new GamePanel(parentFrame, newLevelPath, mainMenu);
        parentFrame.add(newGamePanel);
        parentFrame.revalidate();
        parentFrame.repaint();
        newGamePanel.requestFocusInWindow();
    }
    private void restart(){
        AudioManager audioManager = AudioManager.getInstance();
        audioManager.playMusic("game");
        initiateGame();
        parentFrame.getContentPane().removeAll();
        parentFrame.add(this);
        parentFrame.revalidate();
        parentFrame.repaint();
        this.requestFocusInWindow();
    }

    private void returnToMenu() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        if (restartTimer != null) {
            restartTimer.stop();
        }
        mainMenu.showMenu();
    }
}