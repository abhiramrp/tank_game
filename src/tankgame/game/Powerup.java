package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Powerup {

    private float y, x;
    private BufferedImage img;
    public Powerup(float y, float x, BufferedImage img) {
        this.y = y;
        this.x = x;
        this.img = img;
    }

    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, (int)x, (int)y, null);

    }

    // abstract method
    public abstract void doPowerUp();

}
