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

public class MapData {
    private List<List<String>> collisionLayer;
    private List<List<String>> backgroundLayer;
    private List<List<String>> interactableLayer;

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
            // initialize GameManager with the interactable layer

        } else {
            interactableLayer = null;
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
}