import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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



        Worm worm = new Worm(); 
        
        Renderer panel = Renderer.getInstance("assets/maps/level1.csv", worm.points);


        


        frame.add(panel);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        frame.addKeyListener(new KeyListener(){
             @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_W){
                        worm.moveUp();
                        panel.repaint();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_A){
                        worm.moveLeft();
                        panel.repaint();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_S){
                        worm.moveDown();
                        panel.repaint();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_D){
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
