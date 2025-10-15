import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class AudioManager {
    private static AudioManager instance;
    private Properties audioConfig;
    private Clip currentMusic;
    
    private AudioManager(String propertiesPath) {
        audioConfig = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            audioConfig.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static AudioManager getInstance(String propertiesPath) {
        if (instance == null) {
            instance = new AudioManager(propertiesPath);
        }
        return instance;
    }
    
    public static AudioManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("AudioManager not initialized.");
        }
        return instance;
    }
    
    public void playMusic(String name) {
        playMusic(name, true);
    }
    
    public void playMusic(String name, boolean loop) {
        try {
            // stop current music if playing
            if (currentMusic != null && currentMusic.isRunning()) {
                currentMusic.stop();
                currentMusic.close();
            }
            
            String path = audioConfig.getProperty("music." + name);
            if (path == null) {
                System.err.println("Music not found: " + name);
                return;
            }
            
            File audioFile = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            currentMusic = AudioSystem.getClip();
            currentMusic.open(audioStream);
            
            if (loop) {
                currentMusic.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                currentMusic.start();
            }
        } catch (Exception e) {
            System.err.println("Error playing music: " + name);
            e.printStackTrace();
        }
    }
    
    public void stopMusic() {
        if (currentMusic != null && currentMusic.isRunning()) {
            currentMusic.stop();
            currentMusic.close();
            currentMusic = null;
        }
    }
    
    public void playSFX(String name) {
        try {
            String path = audioConfig.getProperty("sfx." + name);
            if (path == null) {
                System.err.println("SFX not found: " + name);
                return;
            }
            
            File audioFile = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            // clean up when done
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            
            clip.start();
            
        } catch (Exception e) {
            System.err.println("Error playing SFX: " + name);
            e.printStackTrace();
        }
    }
    
    public void setMusicVolume(float volume) {
        if (currentMusic != null) {
            setVolume(currentMusic, volume);
        }
    }
    
    private void setVolume(Clip clip, float volume) {
        try {
            volume = Math.max(0.0f, Math.min(1.0f, volume));
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}