import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static GameManager instance;
    private List<List<String>> interactableLayer;
    private ArrayList<Point> pressedButtons = new ArrayList<>();
    private ArrayList<Point> buttonPositions = new ArrayList<>();
    private Point puddlePosition;
    private boolean levelComplete = false;
    private boolean puddleUnlocked = false;
    
    private GameManager() {
    }
    
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    
    public static void resetInstance() {
        instance = new GameManager();
    }
    
    public void loadInteractableLayer(List<List<String>> layer) {
        this.interactableLayer = layer;
        this.pressedButtons.clear();
        this.buttonPositions.clear();
        this.puddlePosition = null;
        this.levelComplete = false;
        this.puddleUnlocked = false;
        
        // find all button, puddle
        if (layer != null) {
            for (int i = 0; i < layer.size(); i++) {
                for (int j = 0; j < layer.get(i).size(); j++) {
                    String tile = layer.get(i).get(j);
                    if (tile.equals("button")) {
                        buttonPositions.add(new Point(j, i));
                    } else if (tile.equals("puddle")) {
                        puddlePosition = new Point(j, i);
                    }
                }
            }
        }
    }
    
    private void unlockPuddle() {
        puddleUnlocked = true;
    }
    
    private void triggerWinCondition() {
        if (!levelComplete) {
            levelComplete = true;
        }
    }
    
    private void onButtonPressed(Point buttonPosition) {
    }
    
    
    public boolean isLevelComplete() {
        return levelComplete;
    }
    
    public boolean isPuddleUnlocked() {
        return puddleUnlocked;
    }
    
    public boolean isButtonPressed(Point position) {
        return pressedButtons.contains(position);
    }
    

    public List<List<String>> getInteractableLayer() {
        return interactableLayer;
    }
    
    public ArrayList<Point> getPressedButtons() {
        return pressedButtons;
    }
    
    public ArrayList<Point> getButtonPositions() {
        return buttonPositions;
    }
    
    public Point getPuddlePosition() {
        return puddlePosition;
    }

    public int getPressedButtonCount() {
        return pressedButtons.size();
    }
    
    public int getTotalButtonCount() {
        return buttonPositions.size();
    }
    

}