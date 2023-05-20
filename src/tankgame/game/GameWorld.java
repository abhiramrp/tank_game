package tankgame.game;

import tankgame.Launcher;
import tankgame.GameConstants;
import tankgame.Resources;
import tankgame.Sound;
import tankgame.game.powerup.AddLife;
import tankgame.game.powerup.AddSpeed;
import tankgame.game.powerup.ResetHealth;
import tankgame.game.powerup.SlowRotate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;

    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;

    private Sound sand;

    List<Wall> walls = new ArrayList<Wall>();
    List<Powerup> powerups = new ArrayList<Powerup>();

    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            this.resetGame();
            sand.run();

            while (true) {
                this.tick++;

                this.t1.update(t2);
                this.t2.update(t1);


                for (int i=0; i < walls.size(); i++) {
                    Wall w = this.walls.get(i);
                    if(w.getHitBox().intersects(this.t1.getHitBox())) {
                        t1.handleCollision(w);
                        // System.out.println("t1 touched wall");
                    }

                    if(w.getHitBox().intersects(this.t2.getHitBox())) {
                        t2.handleCollision(w);
                        // System.out.println("t2 touched wall");
                    }

                    t1.shootOther(w);
                    t2.shootOther(w);


                }


                for (int i=0; i < powerups.size(); i++) {
                    Powerup p = this.powerups.get(i);
                    if(p.getHitBox().intersects(this.t1.getHitBox())) {
                        p.handleCollision(t1);
                        powerups.remove(p);
                    }

                    if(p.getHitBox().intersects(this.t2.getHitBox())) {
                        p.handleCollision(t2);
                        powerups.remove(p);
                    }
                }

                if (this.t1.getIsDead() || this.t2.getIsDead()) {
                    sand.stopSound();

                    if(t1.getIsDead()) {
                        lf.setWinner(false);
                    }

                    if(t2.getIsDead()) {
                        lf.setWinner(true);
                    }


                    this.lf.setFrame("end");
                    return;
                }

                this.repaint();

                Thread.sleep( 1000 / 144);

            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
            System.out.println(ignored);
        }

    }

    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
        this.t2.setX(800);
        this.t2.setY(800);
    }

    public void InitializeGame() {

        Resources.initResources();

        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        this.sand = new Sound(Resources.getClip("sand"));

        t1 = new Tank(300, 300, 0, 0, (short) 0, Resources.getImage("tank1img"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);

        t2 = new Tank(800, 800, 0, 0, (short) 0, Resources.getImage("tank2img"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.lf.getJf().addKeyListener(tc2);

        try (BufferedReader mapReader = new BufferedReader(new InputStreamReader(GameWorld.class.getClassLoader().getResourceAsStream("resources/map.txt")))) {
            String[] size = mapReader.readLine().split(",");

            int numRows = Integer.parseInt(size[0]);
            int numCols = Integer.parseInt(size[1]);

            for(int i=0; mapReader.ready(); i++) {
                String[] items = mapReader.readLine().split("");

                for (int j=0; j < items.length; j++) {
                    switch (items[j]) {
                        case "3", "9" -> {
                            Wall w = new Wall(i * 30, j * 30, Resources.getImage("wall"));
                            walls.add(w);
                        }
                        case "2" -> {
                            Cactus c = new Cactus(i * 30, j * 30, Resources.getImage("cactus"));
                            walls.add(c);
                        }

                        case "5" -> {
                            AddLife a = new AddLife(i * 30, j * 30, Resources.getImage("addLife"));
                            powerups.add(a);
                        }

                        case "6" -> {
                            AddSpeed a = new AddSpeed(i * 30, j * 30, Resources.getImage("addSpeed"));
                            powerups.add(a);
                        }

                        case "7" -> {
                            ResetHealth r = new ResetHealth(i * 30, j * 30, Resources.getImage("resetHealth"));
                            powerups.add(r);
                        }

                        case "8" -> {
                            SlowRotate s = new SlowRotate(i * 30, j * 30, Resources.getImage("slowRotate"));
                            powerups.add(s);
                        }


                    }
                }

            }


        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(-2);
        }


    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);

        for(int i=0; i < GameConstants.WORLD_WIDTH; i+= 320){
            for(int j=0; j < GameConstants.WORLD_HEIGHT; j+= 240) {
                buffer.drawImage(Resources.getImage("floor"), i, j, null);
            }
        }



        for (Iterator<Wall> it = walls.iterator(); it.hasNext(); ) {
            Wall nextWall = it.next();
            if (!nextWall.isVisible()) {
                it.remove();
            }
        }

        walls.forEach(w -> w.drawImage(buffer));

        powerups.forEach(p -> p.drawImage(buffer));


        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        g2.drawImage(world, 0, 0, null);

        this.drawSplitScreen(world, g2);

        this.drawMiniMap(world, g2);

    }

    private void drawSplitScreen(BufferedImage world, Graphics2D g2) {
        BufferedImage lh = world.getSubimage(t1.getScreen_x(), t1.getScreen_y(), GameConstants.GAME_SCREEN_WIDTH/ 2, GameConstants.GAME_SCREEN_HEIGHT);
        g2.drawImage(lh, 0, 0, null);

        BufferedImage rh = world.getSubimage(t2.getScreen_x(), t2.getScreen_y(), GameConstants.GAME_SCREEN_WIDTH/ 2, GameConstants.GAME_SCREEN_HEIGHT);
        g2.drawImage(rh, GameConstants.GAME_SCREEN_WIDTH/2, 0, null);

        g2.setColor(Color.black);
        g2.drawRect(GameConstants.GAME_SCREEN_WIDTH/2 -2, 0, 4, GameConstants.GAME_SCREEN_HEIGHT);
        g2.fillRect(GameConstants.GAME_SCREEN_WIDTH/2 -2, 0, 4, GameConstants.GAME_SCREEN_HEIGHT);
    }

    private void drawMiniMap(BufferedImage world, Graphics2D g2) {
        BufferedImage minimap = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);

        AffineTransform at = new AffineTransform();
        at.translate(GameConstants.GAME_SCREEN_WIDTH/2f - (GameConstants.WORLD_WIDTH*0.2)/2f,
                GameConstants.GAME_SCREEN_HEIGHT - GameConstants.WORLD_HEIGHT*0.3);
        at.scale(.2, .2);
        g2.drawImage(minimap, at, null);

    }
}
