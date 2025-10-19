import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    private JFrame parentFrame;
    private String[] levels = {
        "assets/maps/level1.csv",
        "assets/maps/level2.csv",
        "assets/maps/level3.csv"
    };
    
    public MainMenu(JFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());
        setBackground(new Color(43, 34, 42));
        
        // title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(43, 34, 42));
        JLabel titleLabel = new JLabel("WORM GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(43, 34, 42));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));
        
        // level buttons
        JLabel selectLabel = new JLabel("Select Level:");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 24));
        selectLabel.setForeground(Color.WHITE);
        selectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(selectLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        for (int i = 0; i < levels.length; i++) {
            final int levelIndex = i;
            JButton levelButton = createStyledButton("Level " + (i + 1));
            levelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame(levels[levelIndex]);
                }
            });
            menuPanel.add(levelButton);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // exit button
        JButton exitButton = createStyledButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuPanel.add(exitButton);
        
        add(titlePanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 50));
        button.setPreferredSize(new Dimension(300, 50));
        button.setFocusPainted(false);
        button.setBackground(new Color(100, 80, 100));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(new Color(150, 130, 150), 2));
        
        // hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(130, 110, 130));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 80, 100));
            }
        });
        
        return button;
    }
    
    private void startGame(String levelPath) {
        // play game music
        AudioManager audioManager = AudioManager.getInstance();
        audioManager.playMusic("game");
        
        // create game panel
        GamePanel gamePanel = new GamePanel(parentFrame, levelPath, this);
        
        // switch to game panel
        parentFrame.getContentPane().removeAll();
        parentFrame.add(gamePanel);
        parentFrame.revalidate();
        parentFrame.repaint();
        gamePanel.requestFocusInWindow();
    }
    
    public void showMenu() {
        // play menu music
        AudioManager audioManager = AudioManager.getInstance();
        audioManager.playMusic("menu");
        
        // switch back to menu
        parentFrame.getContentPane().removeAll();
        parentFrame.add(this);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}