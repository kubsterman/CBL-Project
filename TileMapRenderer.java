import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class TileMapRenderer extends JPanel {
   
    private List<List<String>> collisionLayer;
    private List<List<String>> backgroundLayer;
    private double scale = 4.0;
   
    public TileMapRenderer(String mapPath) {
        // parse the csv file into a 2d list
        List<List<String>> allRows = ParseCSV(mapPath);
        
        // first 10 rows are the collision layer
        collisionLayer = allRows.subList(0, 10);
       
        // next 10 rows are the background layer
        backgroundLayer = allRows.subList(10, 20);
       
        // set panel size based on 10x10 tiles at 16px each, scaled up
        setPreferredSize(new java.awt.Dimension((int)(160 * scale), (int)(160 * scale)));
    }
   
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        setBackground(new Color(43,34,42));

        g2d.scale(scale, scale);

        drawLayer(g2d, backgroundLayer);
        drawLayer(g2d, collisionLayer);
    }
   
    private void drawLayer(Graphics2D g2d, List<List<String>> layer) {
        if(layer == null) return;
       
        // iterate through each tile in the layer
        for(int i = 0; i < layer.size(); i++){
            for(int j = 0; j < layer.get(i).size(); j++){
                String spriteName = layer.get(i).get(j);
                
                // skip empty tiles
                if(spriteName.isEmpty() || spriteName.equals("")) continue;
               
                // load and draw the sprite at the correct position
                BufferedImage sprite = TextureLoader.GetSubImage(spriteName);
                if(sprite != null) {
                    g2d.drawImage(sprite, j*16, i*16, 16, 16, null);
                }
            }
        }
    }
   
    public static List<List<String>> ParseCSV(String mapPath){
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(mapPath))) {
            return reader.lines()
                .map(line -> line.replace("\uFEFF", "")) // remove byte order mark
                .map(line -> Arrays.asList(line.split(";"))) // split by semicolon
                .collect(Collectors.toList());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}