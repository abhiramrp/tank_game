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

            Resources.images.put("floor", ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/images/floor.png")));

            Resources.images.put("bullet", ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/images/bullet.png")));

            Resources.images.put("wall", ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/images/wall.png")));
            Resources.images.put("cactus", ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/images/cactus.png")));

            Resources.images.put("addLife", ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/images/powerups/addLife.png")));
            Resources.images.put("addSpeed", ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/images/powerups/addSpeed.png")));
            Resources.images.put("resetHealth", ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/images/powerups/resetHealth.png")));
            Resources.images.put("slowRotate", ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/images/powerups/slowRotate.png")));




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void initClips() {
        try {
            AudioInputStream as;
            Clip clip;

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("resources/sounds/bullet.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.clips.put("bullet", clip);

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("resources/sounds/sand.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.clips.put("sand", clip);

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("resources/sounds/shoot_tank.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.clips.put("shoot_tank", clip);

            as = AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("resources/sounds/powerup.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.clips.put("powerup", clip);

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


    /*
    public static void main(String[] args) {
        Resources.initImages();
        Resources.initClips();

    }

    */





}
