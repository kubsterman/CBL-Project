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

    private List<List<String>> interactableLayer;

    private BufferedImage backgroundCache;
    private BufferedImage collisionCache;

    private ArrayList<Point> playerList;
    private double scale = 4.0;

    public Renderer(MapData mapData, ArrayList<Point> playerList) {

        this.playerList = playerList;

        backgroundCache = renderLayerToImage(mapData.getBackgroundLayer());
        collisionCache = renderLayerToImage(mapData.getCollisionLayer());
        interactableLayer = mapData.getInteractableLayer();
        GameManager.getInstance().loadInteractableLayer(mapData.getInteractableLayer());
        GameManager.getInstance().points = playerList;

        setPreferredSize(new java.awt.Dimension((int) (160 * scale), (int) (160 * scale)));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        setBackground(new Color(43, 34, 42));

        // Calculate the size of the scaled map
        int scaledMapWidth = (int) (160 * scale);
        int scaledMapHeight = (int) (160 * scale);
        
        // Calculate the offset to center the map
        int offsetX = (getWidth() - scaledMapWidth) / 2;
        int offsetY = (getHeight() - scaledMapHeight) / 2;
        
        // Translate to center position
        g2d.translate(offsetX, offsetY);
        
        g2d.scale(scale, scale);

        g2d.drawImage(backgroundCache, 0, 0, null);

        // Render interactable layer
        drawInteractableLayer(g2d);

        g2d.drawImage(collisionCache, 0, 0, null);

        drawPlayer(g2d);
    }

    private void drawInteractableLayer(Graphics2D g2d) {
        if (interactableLayer == null)
            return;

        GameManager gameManager = GameManager.getInstance();

        // iterate through each tile in the interactable layer
        for (int i = 0; i < interactableLayer.size(); i++) {
            for (int j = 0; j < interactableLayer.get(i).size(); j++) {
                String spriteName = interactableLayer.get(i).get(j);

                // skip empty tiles
                if (spriteName.isEmpty() || spriteName.equals(""))
                    continue;

                Point tilePos = new Point(j, i);

                // check if button is pressed and use different sprite
                if (spriteName.equals("button")) {
                    if (gameManager.isButtonPressed(tilePos)) {
                        spriteName = "buttonPressed";
                    }
                }

                BufferedImage sprite = TextureLoader.GetSubImage(spriteName);
                if (sprite != null) {
                    g2d.drawImage(sprite, j * 16, i * 16, 16, 16, null);
                }
            }
        }
    }

    private void drawPlayer(Graphics2D g2d) {
        double prevDir = 0;
        if (playerList.size() == 1) {
            Point currentPoint = playerList.get(0);
            BufferedImage sprite = TextureLoader.GetSubImage("stub");
            g2d.drawImage(sprite, currentPoint.x * 16, currentPoint.y * 16, 16, 16, null);
        } else {
            for (int i = 0; i < playerList.size() - 1; i++) {

                Point currentPoint = playerList.get(i);
                Point nextPoint = playerList.get(i + 1);
                int xDiff = currentPoint.x - nextPoint.x;
                int yDiff = currentPoint.y - nextPoint.y;
                double dir = Math.atan2(yDiff, xDiff);
                String spriteName = "body";

                if (i == 0) {
                    spriteName = "head";
                } else if (Math.abs(dir - prevDir) > 0 && Math.abs(dir - prevDir) <= 2 * Math.PI) {
                    if (dir - prevDir == -Math.PI / 2 || dir - prevDir == Math.PI * 1.5) {
                        spriteName = "curveOut";
                    } else if (dir - prevDir == Math.PI / 2 || dir - prevDir != Math.PI * 1.5) {
                        spriteName = "curveIn";
                    }

                }

                BufferedImage sprite = TextureLoader.GetSubImage(spriteName);
                if (sprite == null)
                    continue;

                // rotate the sprite image
                BufferedImage rotatedSprite = rotateImage(sprite, dir + Math.PI / 2);
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
                    BufferedImage rotatedSprite = rotateImage(sprite, dir - Math.PI / 2);
                    g2d.drawImage(rotatedSprite, lastPoint.x * 16, lastPoint.y * 16, 16, 16, null);
                }
            }
        }

    }

    private BufferedImage renderLayerToImage(List<List<String>> layer) {
        BufferedImage image = new BufferedImage(160, 160, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        drawLayer(g2d, layer);
        g2d.dispose();
        return image;
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
        if (layer == null)
            return;

        // iterate through each tile in the layer
        for (int i = 0; i < layer.size(); i++) {
            for (int j = 0; j < layer.get(i).size(); j++) {
                String spriteName = layer.get(i).get(j);

                // skip empty tiles
                if (spriteName.isEmpty() || spriteName.equals(""))
                    continue;

                // load and draw the sprite at the correct position
                BufferedImage sprite = TextureLoader.GetSubImage(spriteName);
                if (sprite != null) {
                    g2d.drawImage(sprite, j * 16, i * 16, 16, 16, null);
                }
            }
        }
    }

}