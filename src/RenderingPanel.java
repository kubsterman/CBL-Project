import java.awt.*;
import javax.swing.*;

public class RenderingPanel extends JPanel {
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.scale(2.0, 2.0);
        
        g2d.setColor(Color.RED);
        g2d.fillRect(10, 10, 50, 50);
    }
}