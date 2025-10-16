
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class Renderer extends JPanel {
   
    private List<List<String>> collisionLayer;
    private List<List<String>> backgroundLayer;
    private ArrayList<Point> playerList;
    private double scale = 4.0;

    public static Renderer instance;

    private boolean initialized = true;

    public Renderer(String mapPath, ArrayList<Point> playerList) {
        List<List<String>> allRows = ParseCSV(mapPath);
        this.playerList = playerList;

        collisionLayer = allRows.subList(0, 10);
        backgroundLayer = allRows.subList(10, 20);

        setPreferredSize(new java.awt.Dimension((int)(160 * scale), (int)(160 * scale)));
    }

    public static Renderer getInstance(String mapPath, ArrayList<Point> playerList) {
        if (instance == null) {
            instance = new Renderer(mapPath, playerList);
        }
        return instance;
    }

    public static Renderer getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Renderer not initialized.");
        }
        return instance;
    }

    public void setPlayerList(ArrayList<Point> playerList){
        this.playerList = playerList;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        setBackground(new Color(43,34,42));

        g2d.scale(scale, scale);

        drawLayer(g2d, backgroundLayer);
        drawLayer(g2d, collisionLayer);

        drawPlayer(g2d);
    }
    private void drawPlayer(Graphics2D g2d){
        double prevDir = 0;
        for (int i = 0; i < playerList.size()-1; i++){
            
            Point currentPoint = playerList.get(i);
            Point nextPoint = playerList.get(i+1);
            int xDiff = currentPoint.x - nextPoint.x;
            int yDiff = currentPoint.y - nextPoint.y;
            double dir = Math.atan2(yDiff, xDiff);
            String spriteName = "body";

            if (i == 0){
                spriteName = "head";
            } else if (Math.abs(dir - prevDir) > 0 && Math.abs(dir - prevDir) <= 2*Math.PI){
                if (dir-prevDir == -Math.PI/2 || dir-prevDir == Math.PI*1.5){
                    spriteName = "curveOut";
                }else if (dir-prevDir == Math.PI/2 || dir-prevDir != Math.PI*1.5){
                    spriteName = "curveIn";
                }
               
            }
            
            BufferedImage sprite = TextureLoader.GetSubImage(spriteName);
            if (sprite == null) continue;
            
            // rotate the sprite image
            BufferedImage rotatedSprite = rotateImage(sprite, dir + Math.PI/2);
            System.out.println(dir - prevDir);
            g2d.drawImage(rotatedSprite, currentPoint.x * 16, currentPoint.y * 16, 16, 16, null);
            
            prevDir = dir;
        }
        
        // draw the tail
        if (playerList.size() > 1) {
            Point lastPoint = playerList.get(playerList.size() - 1);
            Point secondToLast = playerList.get(playerList.size() - 2);
            int xDiff = lastPoint.x - secondToLast.x;
            int yDiff = lastPoint.y - secondToLast.y;
            double dir = Math.atan2(yDiff, xDiff);
            
            BufferedImage sprite = TextureLoader.GetSubImage("tail");
            if (sprite != null) {
                BufferedImage rotatedSprite = rotateImage(sprite, dir - Math.PI/2);
                g2d.drawImage(rotatedSprite, lastPoint.x * 16, lastPoint.y * 16, 16, 16, null);
            }
        }
    }
    
    double normalizeAngle(double angle){
        angle = Math.atan2(Math.sin(angle), Math.cos(angle));
        return angle;
    }

    private BufferedImage rotateImage(BufferedImage img, double angle) {
        int w = img.getWidth();
        int h = img.getHeight();
        
        BufferedImage rotated = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        
        // rotate around center
        g2d.rotate(angle, w / 2.0, h / 2.0);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        
        return rotated;
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