package tankgame.game;

import tankgame.Resources;
import tankgame.Sound;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Powerup implements Collidible {

    private float y, x;
    private BufferedImage img;

    protected boolean visible;

    protected Rectangle hitbox;

    public Powerup(float y, float x, BufferedImage img) {
        this.y = y;
        this.x = x;
        this.img = img;
        this.visible = true;
        this.hitbox = new Rectangle((int)x, (int) y, this.img.getWidth(), this.img.getHeight());
    }

    void drawImage(Graphics g) {
        if (this.visible) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(img, (int) x, (int) y, null);
        }

    }

    // abstract method
    public abstract void doPowerUp(Tank t);

    public abstract void setVisible(boolean b);

    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void handleCollision(Collidible with) {
        if(with instanceof Tank) {
            (new Sound(Resources.getClip("powerup"))).playSound();
            this.doPowerUp((Tank) with);
            this.setVisible(false);
        }
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }




}
