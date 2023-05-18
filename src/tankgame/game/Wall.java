package tankgame.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Wall {
    protected float y, x;
    protected BufferedImage img;

    public Wall(float y, float x, BufferedImage img) {
        this.y = y;
        this.x = x;
        this.img = img;
    }

    @Override
    public String toString() {
        return "Wall-> x: " + x + " y: " + y;
    }

    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, (int)x, (int)y, null);

    }
}
