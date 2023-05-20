package tankgame.game;

import tankgame.GameConstants;
import tankgame.Resources;
import tankgame.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank implements Collidible{
    private float x;
    private float y;

    private boolean isReverse;

    public float getX() {
        return x;
    }

    public void setX(float x) {

        this.x = x;
        this.updateHitBox((int) this.x, (int) this.y);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        this.updateHitBox((int) this.x, (int) this.y);
    }


    private float vx;
    private float vy;
    private float angle;

    private Rectangle hitbox;

    private int screen_x;
    private int screen_y;



    private float speed = 4f;
    private float rotationSpeed = 3.0f;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    private Bullet b;

    ArrayList<Bullet> ammo = new ArrayList<>();


    float fireDelay = 120f;
    float coolDown = 0f;
    float rateOfFire = 1f;


    private int health = 100;
    private int lives = 5;

    private boolean isDead;

    public Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;

        this.isReverse = false;
        this.isDead = false;
        this.hitbox = new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
    }


    // Movement

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

    void update(Tank other) {

        if (this.UpPressed) {
            this.moveForwards();
            this.isReverse = false;
        }

        if (this.DownPressed) {
            this.moveBackwards();
            this.isReverse = true;
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
            this.ammo.add(b);
            // System.out.println(b.toString());
            (new Sound(Resources.getClip("bullet"))).playSound();

        }

        // So Tanks can't overlap
        if (this.getHitBox().intersects(other.getHitBox())) {
            handleCollision(other);
        }

        this.coolDown += this.rateOfFire;

        this.ammo.forEach(b -> b.update());

        this.updateHitBox((int) x, (int) y);

        this.shootOther(other);

    }

    public void shootOther(Collidible other) {
        for(int i=0; i < this.ammo.size(); i++) {
            Bullet b = this.ammo.get(i);

            if(b.getHitBox().intersects(other.getHitBox())){
                b.handleCollision(other);
                this.ammo.remove(b);
            }
        }

    }


    private void updateHitBox(int x, int y) {
        this.hitbox.x = x;
        this.hitbox.y = y;
    }

    private void rotateLeft() { this.angle -= this.rotationSpeed;}

    private void rotateRight() {this.angle += this.rotationSpeed;}

    private void moveBackwards() {
        vx =  Math.round(this.speed * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(this.speed * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        this.checkBorder();
    }

    private void moveForwards() {
        vx = Math.round(this.speed * Math.cos(Math.toRadians(angle)));
        vy = Math.round(this.speed * Math.sin(Math.toRadians(angle)));
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

    /**
     * Bullets
     */


    public ArrayList<Bullet> getAmmo() {
        return this.ammo;
    }

    public void getShot() {
        this.health -= 11;

        if (this.health <= 0) {
            this.resetHealth();
            this.lives -= 1;
        }

        if(this.lives <= 0) {
            this.isDead = true;
        }

    }

    public boolean getIsDead() {
        return this.isDead;
    }

    /**
     * Powerups
     */

    public void addLife() {this.lives += 1;}
    public void addSpeed() {this.speed += 2f;}

    public void resetHealth() { this.health = 100; }

    public void slowRotate() { this.rotationSpeed -= 0.25; }

    /**
     * Display
     */

    public int getScreen_x() {
        return this.screen_x;
    }

    public int getScreen_y() {
        return this.screen_y;
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

        /*
        // Test to see hitbox
        g2d.setColor(Color.black);
        g2d.drawRect(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height);
         */


        // health
        if(this.health >= 70) {
            g2d.setColor(Color.GREEN);
        } else if (this.health >= 40) {
            g2d.setColor(Color.ORANGE);
        } else {
            g2d.setColor(Color.RED);
        }

        g2d.drawRect((int)x-25,(int)y-30, 100, 25 );
        g2d.fillRect((int)x-25,(int)y-30, this.health, 25 );

        // lives
        for (int i=0; i < this.lives; i++) {
            g2d.drawOval((int)(x-25) + (i*20), (int)y + 55, 15, 15);
            g2d.fillOval((int)(x-25) + (i*20), (int)y + 55, 15, 15);
        }

    }

    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void handleCollision(Collidible with) {
        if(with instanceof Wall) {
            if(isReverse) {
                this.moveForwards();
            } else {
                this.moveBackwards();
            }
        }

        if(with instanceof Tank) {
            if(isReverse) {
                this.moveForwards();
            } else {
                this.moveBackwards();
            }

        }

    }

    @Override
    public boolean isVisible() {
        if(this.lives <= 0) {
            return false;
        }

        return true;
    }



}
