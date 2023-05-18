package tankgame.game;

import tankgame.GameConstants;
import tankgame.Resources;
import tankgame.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet {
    private float x;
    private float y;

    private float angle;

    private float R = 6;

    private BufferedImage img;


    public Bullet(float x, float y, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.img = img;
    }

    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}

    void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    void update() {
        this.moveForwards();
    }

    private void moveForwards() {
        x += Math.round(R * Math.cos(Math.toRadians(angle)));
        y += Math.round(R * Math.sin(Math.toRadians(angle)));
        checkBorder();
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    void drawImage(Graphics g) {

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.RED);
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());

    }
}
