package tankgame.game;

import tankgame.GameConstants;
import tankgame.Resources;
import tankgame.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet implements Collidible{
    private float x;
    private float y;

    private float angle;

    private float R = 6;

    private BufferedImage img;

    private Rectangle hitbox;

    private boolean visible;


    public Bullet(float x, float y, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.img = img;

        this.visible = true;
        this.hitbox = new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
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
        if ((x < 30) || (x >= GameConstants.WORLD_WIDTH - 88)) {
            this.visible = false;
        }
        if ((y < 30) || (y >= GameConstants.WORLD_HEIGHT - 88)) {
            this.visible = false;
        }

        this.updateHitBox((int) x, (int) y);
    }

    private void updateHitBox(int x, int y) {
        this.hitbox.x = x;
        this.hitbox.y = y;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    void drawImage(Graphics g) {

        if(visible) {

            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);

            /*
            g2d.setColor(Color.RED);
            //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
            g2d.drawRect((int) x, (int) y, this.img.getWidth(), this.img.getHeight());

             */
        }

    }

    public void setVisible(boolean b) {
        this.visible = b;
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void handleCollision(Collidible with) {
        if(with instanceof Cactus) {
            ((Cactus) with).setVisible(false);
            this.visible = false;
        }

        if(with instanceof Wall) {
            this.visible = false;
        }

        if(with instanceof Tank) {

            ((Tank) with).getShot();
            (new Sound(Resources.getClip("shoot_tank"))).playSound();
            this.visible = false;
        }

    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }
}
