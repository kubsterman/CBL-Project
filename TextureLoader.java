import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TextureLoader {
    
    public static BufferedImage GetSubImage(String elementId){
        try {
            // load the xml atlas definition and sprite sheet image
            File file = new File("Resources/Atlas/textureatlas.xml");
            BufferedImage spriteAtlas = ImageIO.read(new File("Resources/Atlas/Texture Atlas.png"));
           
            // parse the xml document
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
           
            // get all sprite elements from the xml
            NodeList spriteList = document.getElementsByTagName("sprite");
           
            // search for the sprite with the matching id
            Element subTextureElement = null;
            for (int i = 0; i < spriteList.getLength(); i++) {
                Element sprite = (Element) spriteList.item(i);
                if (sprite.getAttribute("id").equals(elementId)) {
                    subTextureElement = sprite;
                    break;
                }
            }
           
            // return null if sprite not found
            if (subTextureElement == null) {
                System.err.println("Sprite with id '" + elementId + "' not found");
                return null;
            }
           
            // take sprite coordinates and dimensions from xml
            int x = Integer.parseInt(subTextureElement.getAttribute("x"));
            int y = Integer.parseInt(subTextureElement.getAttribute("y"));
            int width = Integer.parseInt(subTextureElement.getAttribute("w"));
            int height = Integer.parseInt(subTextureElement.getAttribute("h"));
           
            // take the sprite region from the atlas
            BufferedImage sprite = spriteAtlas.getSubimage(x, y, width, height);
            return sprite;
           
        } catch (Exception e) {
            System.err.println("XML Parsing exception: " + e);
            e.printStackTrace();
            return null;
        }
    }
}