package tankgame;

import tankgame.game.GameWorld;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Resources {
    private static Map<String, BufferedImage> images = new HashMap<>();
    private static Map<String, Clip> clips = new HashMap<>();

    public static BufferedImage getImage(String key) {
        return Resources.images.get(key);
    }

    public static Clip getClip(String key) {
        return Resources.clips.get(key);
    }

    public static void initImages() {
        try {
            Resources.images.put("tank1img", ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/images/tank1.png")));
            Resources.images.put("tank2img", ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/images/tank2.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void initClips() {
        try {
            AudioInputStream as;
            Clip clip;

            System.out.println("bef");

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("resources/sounds/bullet.mp3"));

            System.out.println("adf");

            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.clips.put("", clip);

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("resources/sounds/shoot_tank.mp3"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.clips.put("shot_tank", clip);

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.err.println(e);
            e.printStackTrace();
            System.exit(-2);
        }

    }

    public static void initResources() {
        Resources.initImages();
        Resources.initClips();
    }


    public static void main(String[] args) {
        Resources.initImages();
        Resources.initClips();

    }




}
