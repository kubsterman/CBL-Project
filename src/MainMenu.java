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
        
        // menu panel with buttons
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(43, 34, 42));
        
        // level buttons
        JLabel selectLabel = new JLabel("Select Level:");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 24));
        selectLabel.setForeground(Color.WHITE);
        selectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(selectLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        for (int i = 0; i < levels.length; i++) {
            final int levelIndex = i;
            JButton levelButton = createImageButton("level" + (i+1) + "_button");
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
        JButton exitButton = createImageButton("quit_button");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuPanel.add(exitButton);
        
        // wrapper panel to center the menu
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(new Color(43, 34, 42));
        centerWrapper.add(menuPanel);
        
        add(titlePanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
    }

    private JButton createImageButton(String buttonId) {
        ImageIcon icon = TextureLoader.GetIcon(buttonId, 6);
        ImageIcon hoverIcon = TextureLoader.GetIcon(buttonId + "_hover", 6);
        ImageIcon pressedIcon = TextureLoader.GetIcon(buttonId + "_press", 6);
        
        JButton button = new JButton(icon);
        button.setRolloverIcon(hoverIcon);  // hover state
        button.setPressedIcon(pressedIcon);  // pressed state
        
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        
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