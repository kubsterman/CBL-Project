import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class UIManager {
        
    public static JButton createImageButton(String spriteId, int scale) {
            ImageIcon icon = TextureLoader.GetIcon(spriteId, scale);
            if (icon == null) {
                return null;
            }
            
            // Try to load hover icon
            ImageIcon hoverIcon = TextureLoader.GetIcon(spriteId + "_hover", scale);
            
            JButton button = new JButton(icon);
            if (hoverIcon != null) {
                button.setRolloverIcon(hoverIcon);
                button.setRolloverEnabled(true);
            }
            
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            int scaledWidth = icon.getIconWidth();
            int scaledHeight = icon.getIconHeight();
            button.setMaximumSize(new Dimension(scaledWidth, scaledHeight));
            button.setPreferredSize(new Dimension(scaledWidth, scaledHeight));
            
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
            
            return button;
    }
}