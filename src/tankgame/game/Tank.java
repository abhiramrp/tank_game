package tankgame.game;

import tankgame.GameConstants;
import tankgame.Resources;
import tankgame.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Tank implements Collidible{
    private float x;
    private float y;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private float vx;
    private float vy;
    private float angle;

    private Rectangle hitbox;

    private int screen_x;
    private int screen_y;

    private float R = 4f;
    private float ROTATIONSPEED = 3.0f;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    private Bullet b;
    List<Bullet> ammo = new ArrayList<>();


    float fireDelay = 120f;
    float coolDown = 0f;
    float rateOfFire = 1f;


    private int health = 100;
    private int lives = 5;

    public Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;

        this.hitbox = new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
    }


    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }


    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }

        if (this.ShootPressed && this.coolDown >= this.fireDelay) {
            this.coolDown = 0;
            b = new Bullet(x, y, angle, Resources.getImage("bullet"));
            // System.out.println(b.toString());
            (new Sound(Resources.getClip("bullet"))).playSound();
            ammo.add(b);
        }

        this.coolDown += this.rateOfFire;

        this.ammo.forEach(b -> b.update());

    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        this.checkBorder();
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        this.checkBorder();
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

        this.hitbox.setLocation((int)this.x, (int)this.y);
        check_screen();
    }

    public void check_screen() {
        this.screen_x = (int)this.getX() - GameConstants.GAME_SCREEN_WIDTH / 4;
        this.screen_y = (int)this.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2;

        if (this.screen_x < 0 ) {
            screen_x = 0;
        }

        if (this.screen_y < 0) {
            screen_y = 0;
        }

        if (this.screen_x > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2) {
            this.screen_x = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2;
        }

        if (this.screen_y > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT) {
            this.screen_y = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }
    }

    public int getScreen_x() {
        return this.screen_x;
    }

    public int getScreen_y() {
        return this.screen_y;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;

        if (b!= null) b.drawImage(g2d);
        this.ammo.forEach(b -> b.drawImage(g2d));

        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.RED);

        g2d.setColor(Color.cyan);
        g2d.drawRect((int)x,(int)y+30, 100, 25 );

        if(this.health >= 70) {
            g2d.setColor(Color.GREEN);
        } else if (this.health >= 40) {
            g2d.setColor(Color.yellow);
        } else {
            g2d.setColor(Color.RED);
        }



        g2d.fillRect((int)x,(int)y+30, 100, 25 );

    }

    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void handleCollision(Collidible with) {

    }

    @Override
    public boolean isCollidle() {
        return false;
    }
}
