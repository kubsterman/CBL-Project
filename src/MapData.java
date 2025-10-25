import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapData {
    private List<List<String>> collisionLayer;
    private List<List<String>> backgroundLayer;
    private List<List<String>> interactableLayer;
    private int wormLength;

    private BufferedImage backgroundCache;
    private BufferedImage collisionCache;
    private BufferedImage interactableCache;

    public MapData(String mapPath) {
        List<List<String>> allRows = ParseCSV(mapPath);

        collisionLayer = allRows.subList(0, 10);
        backgroundLayer = allRows.subList(10, 20);

        // load interactable layer
        if (allRows.size() >= 30) {
            interactableLayer = allRows.subList(20, 30);
        } else {
            interactableLayer = null;
        }
        
        // Set worm length based on level
        if (mapPath.contains("level1")) {
            wormLength = 31;
        } else if (mapPath.contains("level2")) {
            wormLength = 29;
        } else if (mapPath.contains("level3")) {
            wormLength = 35;
        } else {
            wormLength = 15; // default
        }
    }

    public static List<List<String>> ParseCSV(String mapPath) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(mapPath))) {
            return reader.lines()
                    .map(line -> line.replace("\uFEFF", "")) // remove byte order mark
                    .map(line -> Arrays.asList(line.split(";"))) // split by semicolon
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<List<String>> getBackgroundLayer() {
        return backgroundLayer;
    }

    public List<List<String>> getCollisionLayer() {
        return collisionLayer;
    }

    public List<List<String>> getInteractableLayer() {
        return interactableLayer;
    }
    
    public int getWormLength() {
        return wormLength;
    }
}