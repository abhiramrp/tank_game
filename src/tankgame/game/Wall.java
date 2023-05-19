package tankgame.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Wall implements Collidible {

    protected float y, x;
    protected BufferedImage img;

    protected boolean visible;

    protected Rectangle hitbox;

    public Wall(float y, float x, BufferedImage img) {
        this.y = y;
        this.x = x;
        this.img = img;

        this.hitbox = new Rectangle((int) x, (int)y, this.img.getWidth(), this.img.getHeight());
        this.visible = true;
    }

    @Override
    public String toString() {
        return "Wall-> x: " + x + " y: " + y;
    }

    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, (int)x, (int)y, null);

        /*
        // Test to see hitbox
        g2d.setColor(Color.black);
        g2d.drawRect(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height);

         */

    }


    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void handleCollision(Collidible with) {

    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }
}
